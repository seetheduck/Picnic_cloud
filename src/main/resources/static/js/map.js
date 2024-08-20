const { createApp } = Vue;

createApp({
  data() {
    return {
      facilities: [],
      filteredFacilities: [],
      searchQuery: '',
      selectedCategory: '카테고리 없음',
      selectedFacility: '시설 없음',
      map: null,
      infoWindow: null,
      loading: true,
      markers: [],
      markerColors: {
        '전시/공연': '#FF0000',
        '문화관광/명소': '#0000FF'
      },
      activeMarker: null, // 현재 활성화된 마커를 추적
      infoWindowTimer: null, // 인포윈도우 타이머를 위한 변수
    };
  },

  async mounted() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        async position => {
          const latitude = position.coords.latitude;
          const longitude = position.coords.longitude;

          if (typeof kakao !== 'undefined') {
            await this.initializeMap(latitude, longitude);
            await this.fetchFacilities();
            this.addMapEventListeners();
          } else {
            console.error("Kakao 지도 API가 올바르게 로드되지 않았습니다.");
          }
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
        this.filteredFacilities = this.facilities;
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
      this.initializeMap(); // 기본 위치로 지도를 초기화합니다.
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
      const bounds = this.map.getBounds();
      this.clearMarkers();

      this.filteredFacilities.forEach(facility => {
        const markerPosition = new kakao.maps.LatLng(facility.latitude, facility.longitude);
        if (bounds.contain(markerPosition)) {
          this.createMarker(facility, markerPosition);
        }
      });
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

      // 마우스가 마커에서 벗어나면 일정 시간 후에 인포윈도우를 닫습니다. 
      kakao.maps.event.addListener(marker, 'mouseout', () => {
        if (this.infoWindowTimer) {
          clearTimeout(this.infoWindowTimer);
        }
        this.infoWindowTimer = setTimeout(() => {
          if (!this.isMouseOverInfoWindow) {
            this.infoWindow.close();
          }
        }, 200); // 200ms 딜레이를 줘서 인포윈도우로 마우스가 이동할 시간을 줌
      });

      this.markers.push(marker);
      marker.setMap(this.map);
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
        // 마우스가 인포윈도우에 있을 때 창이 닫히지 않도록 처리
        infoWindowElement.addEventListener('mouseover', () => {
          if (this.infoWindowTimer) {
            clearTimeout(this.infoWindowTimer);
          }
          this.isMouseOverInfoWindow = true;
        });

        // 마우스가 인포윈도우를 벗어날 때 창을 닫습니다.
        infoWindowElement.addEventListener('mouseout', () => {
          this.isMouseOverInfoWindow = false;
          this.infoWindowTimer = setTimeout(() => {
            this.infoWindow.close();
          }, 200); // 200ms 딜레이 후 인포윈도우를 닫음
        });
      }
    },

    clearMarkers() {
      this.markers.forEach(marker => marker.setMap(null));
      this.markers = [];
    },

    filterByCategory(category) {
      this.selectedCategory = this.selectedCategory === category ? '카테고리 없음' : category;
      this.filteredFacilities = this.selectedCategory === '카테고리 없음'
        ? this.facilities
        : this.facilities.filter(facility => facility.category === this.selectedCategory);

      this.updateMarkers();
    },

    searchPlaces() {
      const query = this.searchQuery.trim();
      this.filteredFacilities = query
        ? this.facilities.filter(facility =>
            facility.name.includes(query) ||
            facility.address.includes(query) ||
            facility.description.includes(query))
        : this.facilities;

      this.updateMarkers();
    },

    goToCurrentLocation() {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          position => {
            const latitude = position.coords.latitude;
            const longitude = position.coords.longitude;
            const locPosition = new kakao.maps.LatLng(latitude, longitude);

            this.map.setCenter(locPosition);

            // 현재 위치에 대한 마커 추가 (기본 스타일)
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
    }
  }
}).mount('#app');
