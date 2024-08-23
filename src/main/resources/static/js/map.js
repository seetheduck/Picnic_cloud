const { createApp } = Vue;

createApp({
  // data 함수는 Vue 인스턴스가 사용할 데이터를 정의합니다.
  data() {
    return {
      facilities: [], // 모든 시설 정보를 저장하는 배열
      filteredFacilities: [], // 필터링된 시설 정보를 저장하는 배열
      searchQuery: '', // 사용자가 입력한 검색어를 저장
      selectedCategory: '', // 선택된 카테고리를 저장 (단일 선택을 위해 문자열로 설정)
      map: null, // Kakao 지도를 저장
      geocoder: null, // Kakao 지도의 지오코더(주소 변환)를 저장
      infoWindow: null, // 현재 열려있는 정보 창을 저장
      loading: true, // 데이터 로딩 상태를 나타내는 플래그
      markers: [], // 지도에 표시된 마커들을 저장
      clusterer: null, // 마커 클러스터러를 저장
      markerColors: { // 각 카테고리별 마커 색상을 지정
        '전시/공연': '#FF0000',
        '문화관광/명소': '#0000FF'
      },
      activeMarker: null, // 현재 활성화된 마커를 저장
      infoWindowTimer: null, // 정보 창을 자동으로 닫기 위한 타이머
      isMouseOverInfoWindow: false, // 정보 창에 마우스가 위치했는지를 확인하는 플래그
      filters: { // 시설 필터링 옵션들을 저장
        kidsZone: false,
        freeParking: false,
        chargedParking: false,
        nursingRoom: false,
        strollerRental: false
      }
    };
  },

  // mounted 함수는 Vue 인스턴스가 DOM에 마운트된 후 실행
  async mounted() {
    // Kakao 지도 API가 올바르게 로드되었는지 확인
    if (typeof kakao !== 'undefined' && kakao.maps && kakao.maps.services) {
      this.geocoder = new kakao.maps.services.Geocoder(); // 지오코더 초기화
    } else {
      console.error("Kakao 지도 API가 올바르게 로드되지 않았습니다.");
      return;
    }

    // 사용자 위치를 가져오고 지도를 초기화
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        async position => {
          const latitude = position.coords.latitude;
          const longitude = position.coords.longitude;

          // 지도 초기화 및 마커 클러스터러 초기화, 그리고 시설 정보 가져오기
          await this.initializeMap(latitude, longitude);
          this.initializeMarkerClusterer();
          await this.fetchFacilities();
          this.addMapEventListeners();
        },
        () => {
          // 위치 정보를 가져오지 못했을 때 기본 위치로 지도를 초기화
          this.initializeMapWithDefaultLocation();
        }
      );
    } else {
      // 브라우저에서 지오로케이션을 지원하지 않을 때 기본 위치로 지도 초기화
      this.initializeMapWithDefaultLocation();
    }
  },

  methods: {
    // 시설 정보를 서버로부터 가져오는 함수
    async fetchFacilities() {
      this.loading = true; // 로딩 상태를 true로 설정
      try {
        const response = await axios.get('/api/facilities'); // 서버로부터 시설 정보 요청
        this.facilities = response.data; // 가져온 데이터를 facilities 배열에 저장
        this.updateFilteredFacilities(); // 필터링된 시설 목록 업데이트
      } catch (error) {
        console.error("시설 정보를 불러오는 데 실패했습니다.", error);
      } finally {
        this.loading = false; // 로딩 상태를 false로 설정
        this.updateMarkers(); // 마커 업데이트
      }
    },

    // 지도를 초기화하는 함수
    initializeMap(latitude = 33.450701, longitude = 126.570667) {
      const container = document.getElementById('map'); // 지도 컨테이너를 가져옴
      const options = {
        center: new kakao.maps.LatLng(latitude, longitude), // 지도의 중심 좌표 설정
        level: 3 // 지도의 확대 레벨 설정
      };
      this.map = new kakao.maps.Map(container, options); // 지도 생성

      const zoomControl = new kakao.maps.ZoomControl(); // 확대/축소 컨트롤 생성
      this.map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT); // 컨트롤을 지도에 추가

      this.infoWindow = new kakao.maps.InfoWindow({ zIndex: 1 }); // 정보 창 초기화
    },

    // 기본 위치로 지도를 초기화하는 함수
    initializeMapWithDefaultLocation() {
      this.initializeMap(); // 기본 좌표로 지도를 초기화
    },

    // 마커 클러스터러를 초기화하는 함수
    initializeMarkerClusterer() {
      this.clusterer = new kakao.maps.MarkerClusterer({
        map: this.map, // 클러스터러를 사용할 지도
        averageCenter: true, // 클러스터 마커의 중심을 클러스터 내 평균 좌표로 설정
        minLevel: 7, // 클러스터링 할 최소 지도 레벨
        disableClickZoom: true, // 클러스터 클릭 시 확대 기능 비활성화
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

    // 지도에 이벤트 리스너를 추가하는 함수
    addMapEventListeners() {
      kakao.maps.event.addListener(this.map, 'bounds_changed', this.updateMarkers); // 지도의 경계가 변경될 때 마커 업데이트
    },

    // 마커 이미지를 생성하는 함수
    getMarkerImage(color) {
      const markerSVG = `
        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="${color}" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-map-pin">
          <path d="M21 10c0 7-9 13-9 13S3 17 3 10a9 9 0 1 1 18 0z"/>
          <circle cx="12" cy="10" r="3"/>
        </svg>`;
      // SVG를 Base64로 인코딩하여 마커 이미지로 사용
      return new kakao.maps.MarkerImage(`data:image/svg+xml;base64,${btoa(markerSVG)}`, new kakao.maps.Size(32, 32));
    },

    // 마커를 업데이트하는 함수
    updateMarkers() {
      if (!this.clusterer) {
        console.error("클러스터러가 초기화되지 않았습니다.");
        return;
      }

      const bounds = this.map.getBounds(); // 현재 지도 범위를 가져옴
      this.clearMarkers(); // 기존 마커들을 제거

      const markers = this.filteredFacilities.map(facility => {
        const markerPosition = new kakao.maps.LatLng(facility.latitude, facility.longitude);
        if (bounds.contain(markerPosition)) {
          return this.createMarker(facility, markerPosition); // 범위 내에 있는 시설에 대해 마커 생성
        }
      }).filter(marker => marker !== undefined); // 정의된 마커만 필터링

      this.clusterer.addMarkers(markers); // 클러스터러에 마커 추가
    },

    // 마커를 생성하는 함수
    createMarker(facility, position) {
      const markerColor = this.markerColors[facility.category] || '#000000'; // 카테고리에 따른 마커 색상 설정
      const markerImage = this.getMarkerImage(markerColor); // 마커 이미지 생성

      const marker = new kakao.maps.Marker({
        position,
        title: facility.name,
        image: markerImage
      });

      // 마커를 클릭했을 때 정보 창을 표시
      kakao.maps.event.addListener(marker, 'click', () => {
        this.displayInfoWindow(facility, marker);
      });

      // 마커에서 마우스가 벗어났을 때 정보 창을 닫음
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

    // 정보 창을 표시하는 함수
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

      this.infoWindow.setContent(content); // 정보 창의 내용을 설정
      this.infoWindow.open(this.map, marker); // 정보 창을 열음
      this.activeMarker = marker; // 활성화된 마커를 저장

      const infoWindowElement = document.getElementById('info-window-content');
      if (infoWindowElement) {
        infoWindowElement.addEventListener('mouseover', () => {
          if (this.infoWindowTimer) {
            clearTimeout(this.infoWindowTimer);
          }
          this.isMouseOverInfoWindow = true; // 마우스가 정보 창에 들어갔음을 표시
        });

        infoWindowElement.addEventListener('mouseout', () => {
          this.isMouseOverInfoWindow = false; // 마우스가 정보 창에서 벗어났음을 표시
          this.infoWindowTimer = setTimeout(() => {
            this.infoWindow.close(); // 정보 창을 닫음
          }, 200);
        });
      }
    },

    // 기존 마커들을 제거하는 함수
    clearMarkers() {
      if (this.clusterer) {
        this.clusterer.clear(); // 클러스터러에서 모든 마커 제거
      } else {
        console.warn("클러스터러가 아직 초기화되지 않았습니다.");
      }
    },

    // 카테고리 필터링을 처리하는 함수
    filterByCategory(category) {
      if (this.selectedCategory === category) {
        this.selectedCategory = '';  // 이미 선택된 카테고리를 클릭하면 해제
      } else {
        this.selectedCategory = category;  // 새로운 카테고리를 클릭하면 선택
      }

      this.updateFilteredFacilities();  // 필터링된 시설 목록을 업데이트
    },

    // 시설 필터링 옵션을 토글하는 함수
    toggleFilter(filterName) {
      this.filters[filterName] = !this.filters[filterName];
      this.updateFilteredFacilities();
    },

    // 필터링된 시설 목록을 업데이트하는 함수
    updateFilteredFacilities() {
      this.filteredFacilities = this.facilities.filter(facility => {
        const matchesCategory = this.selectedCategory === '' || facility.category === this.selectedCategory;
        const matchesFilters = Object.keys(this.filters).every(filter => {
          if (this.filters[filter]) {
            return facility[filter];
          }
          return true;
        });

        return matchesCategory && matchesFilters;
      });

      this.updateMarkers(); // 마커를 업데이트
    },

    // 사용자가 입력한 검색어로 시설을 검색하는 함수
    searchPlaces() {
      const query = this.searchQuery.trim(); // 검색어의 공백을 제거
      this.filteredFacilities = query
        ? this.facilities.filter(facility =>
            facility.name.includes(query) ||
            facility.address.includes(query) ||
            facility.description.includes(query))
        : this.facilities;

      this.updateMarkers(); // 마커를 업데이트
    },

    // 현재 위치로 지도를 이동시키는 함수
    goToCurrentLocation() {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          position => {
            const latitude = position.coords.latitude;
            const longitude = position.coords.longitude;
            const locPosition = new kakao.maps.LatLng(latitude, longitude);

            this.map.setCenter(locPosition); // 지도의 중심을 현재 위치로 설정

            const marker = new kakao.maps.Marker({
              position: locPosition
            });
            marker.setMap(this.map); // 현재 위치에 마커를 표시
          },
          error => {
            console.error("현재 위치를 가져오지 못했습니다.", error);
          }
        );
      } else {
        console.error("이 브라우저에서는 Geolocation이 지원되지 않습니다.");
      }
    },

    // 엔터 키를 눌렀을 때 검색을 실행하는 함수
    handleKeydown(event) {
      if (event.key === 'Enter') {
        this.searchPlaces(); // 엔터 키가 눌리면 검색 실행
      }
    }
  }
}).mount('#app');
