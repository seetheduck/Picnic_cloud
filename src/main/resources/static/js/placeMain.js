const {createApp} = Vue;

createApp({
    data(){
        return{//초기화
            fiveDayWeather: [] // 5일간의 날씨 정보를 저장할 배열
        }
    },
    methods: {
        formatDate(dateString) { // 날짜형식변환 메소드
            const date = new Date(dateString);

            const month = date.getMonth() + 1; // 월은 0부터 시작하므로 +1
            const day = date.getDate();
            const days = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'];
            const dayOfWeek = days[date.getDay()];

            return `${month}월 ${day}일 ${dayOfWeek}`;
        },
        fetchWeather(){ //open api. 3시간마다 5일치의 날씨 데이터. 위치= 서울 도시. 섭씨c, 한국어로 변환.
            fetch('https://api.openweathermap.org/data/2.5/forecast?q=Seoul&appid=0697f7fdd534f182fef062a7ea42aead&units=metric&lang=kr')
            .then(
                response => response.json()
            )
            .then(data => {
                //5일간 날씨 정보 처리
                const days = {};// 날짜별 날씨 정보를 저장할 객체

                data.list.forEach(entry => {
                    const entryDate = entry.dt_txt.split(' ')[0]; //2024-08-15
                    // 날짜가 객체에 없으면 초기화
                    //days 객체에 날짜별로 날씨 정보를 저장하기 위해 사용
                    if(!days[entryDate]){ 
                        days[entryDate] = {
                            date: entryDate,
                            temps: [], // 온도 배열. 온도는 하루내내의 온도값들을 받아야하기에, 배열로.
                            iconUrl: '', // 아이콘 URL. 값1개 저장. 
                            description: '', // 날씨 설명. 값1개 저장.
                            closestTimeDiff: Number.MAX_VALUE // 가장 가까운 시간 차이
                        };
                    }
                    // 온도 배열에 추가
                    days[entryDate].temps.push(entry.main.temp);

                    const timeDiff = Math.abs(new Date(entry.dt_txt) - new Date()); // 현재 시간과의 차이 계산
                    
                    //낮 12시의 정보만 아이콘과 설명에 저장
                    if (entry.dt_txt.endsWith('12:00:00')) {
                        days[entryDate].description = entry.weather[0].description;
                        days[entryDate].iconUrl = `http://openweathermap.org/img/wn/${entry.weather[0].icon}@2x.png`;
                        days[entryDate].closestTimeDiff = timeDiff; // 낮 12시라면 시간 차이를 갱신    
                    }else if (timeDiff < days[entryDate].closestTimeDiff){
                    // 만약 오늘 날짜이고 낮 12시를 지나갔다면, 가장 가까운 시간으로 아이콘과 설명을 설정
                        days[entryDate].description = entry.weather[0].description;
                        days[entryDate].iconUrl = `http://openweathermap.org/img/wn/${entry.weather[0].icon}@2x.png`;
                        days[entryDate].closestTimeDiff = timeDiff;
                    }
                });

                
                //map() 사용하여 각 날짜별 정보를 fiveDayWeather 배열의 형태로 변환
                // slice(0, 5): days에 담긴 "5일치 정보만"을, 배열로 변환
                this.fiveDayWeather = Object.values(days).slice(0, 5).map(day => {
                    return {
                        date: this.formatDate(day.date),
                        iconUrl: day.iconUrl,
                        description: day.description,
                        //온도 계산(평균, 최고, 최저)
                        avg: day.temps.reduce((a, b) => a + b, 0) / day.temps.length,
                        max: Math.max(...day.temps),
                        min: Math.min(...day.temps)
                    };
                });
            })
            .catch(err => {
                console.log('날씨 api 오류:', err)
            });
        }
    },
    mounted() {
        this.fetchWeather(); // 페이지가 로드될 때 fetchWeather를 호출(버튼X)
    }
}).mount('#app');
