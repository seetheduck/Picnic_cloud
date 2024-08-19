const { createApp } = Vue;

createApp({
    data() {
        return {
            facilities: [],
            searchQuery: '',
            selectedCategory: '카테고리 없음',
            selectedFacility: '시설 없음',
            map: null,
            infoWindow: null,
            infoWindowTimer: null, // InfoWindow 닫기 지연을 위한 타이머
            loading: true // 로딩 상태를 추가
        };
    },
    mounted() {
        // Geolocation API를 사용하여 현재 위치 가져오기
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                async position => {
                    const latitude = position.coords.latitude;
                    const longitude = position.coords.longitude;

                    if (typeof kakao !== 'undefined') {
                        await this.initializeMap(latitude, longitude);
                        await this.fetchFacilities();
                        this.addMarkersToMap();
                        this.loading = false; // 지도와 마커가 모두 로드된 후 로딩 상태를 false로 설정
                    } else {
                        console.error("Kakao 객체를 찾을 수 없습니다. Kakao 지도 API가 올바르게 로드되었는지 확인하세요.");
                        this.loading = false;
                    }
                },
                error => {
                    console.error("위치 정보를 가져오지 못했습니다.", error);
                    this.initializeMapWithDefaultLocation();
                    this.loading = false; // 로딩 상태를 false로 설정
                }
            );
        } else {
            console.error("이 브라우저에서는 Geolocation이 지원되지 않습니다.");
            this.initializeMapWithDefaultLocation();
            this.loading = false; // 로딩 상태를 false로 설정
        }
    },
    methods: {
        async fetchFacilities() {
            try {
                const response = await axios.get('/api/facilities');
                this.facilities = response.data;
            } catch (error) {
                console.error("시설 정보를 불러오는 데 실패했습니다.", error);
            }
        },
        initializeMap(latitude, longitude) {
            const container = document.getElementById('map');
            const options = {
                center: new kakao.maps.LatLng(latitude, longitude),
                level: 3
            };
            this.map = new kakao.maps.Map(container, options);

            // InfoWindow 객체 생성 (초기화)
            this.infoWindow = new kakao.maps.InfoWindow({
                zIndex: 1 // InfoWindow가 다른 요소 위에 표시되도록 설정
            });
        },
        initializeMapWithDefaultLocation() {
            this.initializeMap(33.450701, 126.570667); // 기본 위치 (제주도)
        },
        addMarkersToMap() {
            if (!this.map) {
                return;
            }

            this.facilities.forEach(facility => {
                const markerPosition = new kakao.maps.LatLng(facility.latitude, facility.longitude);
                const marker = new kakao.maps.Marker({
                    position: markerPosition,
                    title: facility.name
                });
                marker.setMap(this.map);

                // 마커 클릭 이벤트 추가
                kakao.maps.event.addListener(marker, 'click', () => {
                    let homepageLink = facility.homepageUrl ? `<p><strong>홈페이지:</strong> <a href="${facility.homepageUrl}" target="_blank" style="color:#007BFF; text-decoration:none;">${facility.homepageUrl}</a></p>` : '';

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

                    // InfoWindow의 내용을 업데이트하고, 새 위치에 표시
                    this.infoWindow.setContent(content);
                    this.infoWindow.open(this.map, marker);

                    // InfoWindow가 렌더링된 후 이벤트 리스너 추가
                    setTimeout(() => {
                        const infoWindowElement = document.getElementById('info-window-content');
                        if (infoWindowElement) {
                            infoWindowElement.addEventListener('mouseover', () => {
                                if (this.infoWindowTimer) {
                                    clearTimeout(this.infoWindowTimer); // 마우스가 InfoWindow로 이동하면 타이머 취소
                                }
                            });
                            infoWindowElement.addEventListener('mouseout', () => {
                                this.infoWindowTimer = setTimeout(() => {
                                    this.infoWindow.close();
                                }, 200); // InfoWindow에서 마우스가 벗어났을 때 창 닫기
                            });
                        }
                    }, 0); // DOM이 갱신될 시간을 주기 위해 setTimeout 사용
                });

                // 마우스 아웃 이벤트 추가: 마우스가 마커에서 벗어나면 창 닫기
                kakao.maps.event.addListener(marker, 'mouseout', () => {
                    this.infoWindowTimer = setTimeout(() => {
                        this.infoWindow.close();
                    }, 200); // 마우스가 InfoWindow로 이동할 시간을 줌
                });
            });
        }
    }
}).mount('#app');
