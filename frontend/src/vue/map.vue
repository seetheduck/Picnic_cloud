<template>
  <div id="app">
    <!-- 헤더 -->
    <header id="topBar">
     <TopBar />
    </header>
    <main>
    <!-- 검색 바 및 지도 컨테이너 -->
    <div class="search-container">
      <div class="sidebar">
        <input type="text" v-model="searchQuery" placeholder="지역 검색" @keyup.enter="searchPlaces">
        <button @click="searchPlaces">검색</button>
        <div>
          <h3>분류</h3>
          <button @click="filterByCategory('전시/공연')">전시/공연</button>
          <button @click="filterByCategory('문화관광/명소')">문화관광/명소</button>
        </div>
        <div>
          <h3>시설 필터링</h3>
          <button @click="toggleFilter('kidsZone');">키즈존</button>
          <button @click="toggleFilter('freeParking');">무료 주차</button>
          <button @click="toggleFilter('chargedParking');">유료 주차</button>
          <button @click="toggleFilter('nursingRoom');">수유실</button>
          <button @click="toggleFilter('strollerRental');">유모차 대여</button>
        </div>
      </div>
      <div id="map" class="map-container">
        <!-- 로딩 스피너 -->
        <div v-if="loading" class="spinner-container">
          <div class="spinner"></div>
          <div class="spinner-text">Loading...</div>
        </div>
        <!-- 내 위치 보기 버튼 -->
        <button id="current-location-button" @click="goToCurrentLocation">
          <img alt="내 위치" src="../images/myLocbtn.png" width="13px" height="12px">
        </button>
      </div>
    </div>
  </main>
  <footer id="footerBox">
    <FooterBox />
  </footer>
  </div>
</template>

<script>
import axios from 'axios';
import { createApp } from 'vue';

export default {
  data() {
    return {
      facilities: [],
      filteredFacilities: [],
      searchQuery: '',
      selectedCategory: '',
      map: null,
      geocoder: null,
      infoWindow: null,
      loading: true,
      markers: [],
      clusterer: null,
      markerColors: {
        '전시/공연': '#FF0000',
        '문화관광/명소': '#0000FF'
      },
      activeMarker: null,
      infoWindowTimer: null,
      isMouseOverInfoWindow: false,
      filters: {
        kidsZone: false,
        freeParking: false,
        chargedParking: false,
        nursingRoom: false,
        strollerRental: false
      }
    };
  },
  mounted() {
    if (typeof kakao !== 'undefined' && kakao.maps && kakao.maps.services) {
      this.geocoder = new kakao.maps.services.Geocoder();
    } else {
      console.error("Kakao 지도 API가 올바르게 로드되지 않았습니다.");
      return;
    }

    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        async position => {
          const latitude = position.coords.latitude;
          const longitude = position.coords.longitude;

          await this.initializeMap(latitude, longitude);
          this.initializeMarkerClusterer();
          await this.fetchFacilities();
          this.addMapEventListeners();
        },
        () => {
          this.initializeMapWithDefaultLocation();
        }
      );
    } else {
      this.initializeMapWithDefaultLocation();
    }
  },
  methods: {
    async fetchFacilities() {
      this.loading = true;
      try {
        const response = await axios.get('/api/facilities');
        this.facilities = response.data;
        this.updateFilteredFacilities();
      } catch (error) {
        console.error("시설 정보를 불러오는 데 실패했습니다.", error);
      } finally {
        this.loading = false;
        this.updateMarkers();
      }
    },
    initializeMap(latitude = 33.450701, longitude = 126.570667) {
      const container = document.getElementById('map');
      const options = {
        center: new kakao.maps.LatLng(latitude, longitude),
        level: 3
      };
      this.map = new kakao.maps.Map(container, options);

      const zoomControl = new kakao.maps.ZoomControl();
      this.map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

      this.infoWindow = new kakao.maps.InfoWindow({ zIndex: 1 });
    },
    initializeMapWithDefaultLocation() {
      this.initializeMap();
    },
    initializeMarkerClusterer() {
      this.clusterer = new kakao.maps.MarkerClusterer({
        map: this.map,
        averageCenter: true,
        minLevel: 7,
        disableClickZoom: true,
        styles: [{ 
          width: '53px', 
          height: '52px', 
          background: 'rgba(255, 255, 255, 0.8)', 
          borderRadius: '26px', 
          textAlign: 'center', 
          lineHeight: '52px', 
          color: '#000', 
          fontSize: '18px' 
        }]
      });
    },
    addMapEventListeners() {
      kakao.maps.event.addListener(this.map, 'bounds_changed', this.updateMarkers);
    },
    getMarkerImage(color) {
      const markerSVG = `
        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="${color}" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-map-pin">
          <path d="M21 10c0 7-9 13-9 13S3 17 3 10a9 9 0 1 1 18 0z"/>
          <circle cx="12" cy="10" r="3"/>
        </svg>`;
      return new kakao.maps.MarkerImage(`data:image/svg+xml;base64,${btoa(markerSVG)}`, new kakao.maps.Size(32, 32));
    },
    updateMarkers() {
      if (!this.clusterer) {
        console.error("클러스터러가 초기화되지 않았습니다.");
        return;
      }

      const bounds = this.map.getBounds();
      this.clearMarkers();

      const markers = this.filteredFacilities.map(facility => {
        const markerPosition = new kakao.maps.LatLng(facility.latitude, facility.longitude);
        if (bounds.contain(markerPosition)) {
          return this.createMarker(facility, markerPosition);
        }
      }).filter(marker => marker !== undefined);

      this.clusterer.addMarkers(markers);
    },
    createMarker(facility, position) {
      const markerColor = this.markerColors[facility.category] || '#000000';
      const markerImage = this.getMarkerImage(markerColor);

      const marker = new kakao.maps.Marker({
        position,
        title: facility.name,
        image: markerImage
      });

      kakao.maps.event.addListener(marker, 'click', () => {
        this.displayInfoWindow(facility, marker);
      });

      kakao.maps.event.addListener(marker, 'mouseout', () => {
        if (this.infoWindowTimer) {
          clearTimeout(this.infoWindowTimer);
        }
        this.infoWindowTimer = setTimeout(() => {
          if (!this.isMouseOverInfoWindow) {
            this.infoWindow.close();
          }
        }, 200);
      });

      return marker;
    },
    displayInfoWindow(facility, marker) {
      const homepageLink = facility.homepageUrl ? `<p><strong>홈페이지:</strong> <a href="${facility.homepageUrl}" target="_blank" style="color:#007BFF; text-decoration:none;">${facility.homepageUrl}</a></p>` : '';
      const content = `
        <div id="info-window-content" style="padding:10px;">
          <h4>${facility.name}</h4>
          <p><strong>주소:</strong> ${facility.address}</p>
          <p><strong>전화번호:</strong> ${facility.telephone}</p>
          <p><strong>운영 시간:</strong> ${facility.operationTime}</p>
          ${homepageLink}
          <p><strong>설명:</strong> ${facility.description}</p>
        </div>
      `;

      this.infoWindow.setContent(content);
      this.infoWindow.open(this.map, marker);
      this.activeMarker = marker;

      const infoWindowElement = document.getElementById('info-window-content');
      if (infoWindowElement) {
        infoWindowElement.addEventListener('mouseover', () => {
          if (this.infoWindowTimer) {
            clearTimeout(this.infoWindowTimer);
          }
          this.isMouseOverInfoWindow = true;
        });

        infoWindowElement.addEventListener('mouseout', () => {
          this.isMouseOverInfoWindow = false;
          this.infoWindowTimer = setTimeout(() => {
            this.infoWindow.close();
          }, 200);
        });
      }
    },
    clearMarkers() {
      if (this.clusterer) {
        this.clusterer.clear();
      } else {
        console.warn("클러스터러가 아직 초기화되지 않았습니다.");
      }
    },
    filterByCategory(category) {
      if (this.selectedCategory === category) {
        this.selectedCategory = '';
      } else {
        this.selectedCategory = category;
      }