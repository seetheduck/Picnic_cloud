$(document).ready(async function() {
    let calendarEl = document.getElementById('calendar');
    let calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        headerToolbar: {
            left: 'prev,next',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
		locale: 'ko', // 달력 UI를 한국어로 설정
		buttonText: {
		       month: '월',    // month 버튼 텍스트
		       week: '주',     // week 버튼 텍스트
		       day: '일'       // day 버튼 텍스트
		   },
        events: [] // 초기 로딩 시 일정 없이 빈 캘린더를 표시
    });

    // 초기 화면에 일정 없는 캘린더 렌더링
    calendar.render();

    // 이벤트가 추가된 후 툴팁 기능 추가
    $(document).on('mouseenter', '.fc-event', function() {
        let event = calendar.getEventById($(this).data('event-id'));

        $(this).tooltip({
            title: event.extendedProps.description,
            placement: 'top',
            trigger: 'hover',
            container: 'body'
        }).tooltip('show');
    });

    // 전시회 데이터를 비동기적으로 가져오는 함수
    async function fetchExhibitEvents() {
        try {
            const response = await $.ajax({
                url: '/getExhibitEvents', // 서버의 엔드포인트
                method: 'GET',
                dataType: 'json'
            });
            return response;
        } catch (error) {
            console.error("Error loading events:", error);
            return [];
        }
    }

    // 공연 데이터를 비동기적으로 가져오는 함수
    async function fetchPerformEvents() {
        try {
            const response = await $.ajax({
                url: '/getPerformEvents', // 서버의 엔드포인트
                method: 'GET',
                dataType: 'json'
            });
            return response;
        } catch (error) {
            console.error("Error loading events:", error);
            return [];
        }
    }

    // 교육 데이터를 비동기적으로 가져오는 함수
    async function fetchEduEvents() {
        try {
            const response = await $.ajax({
                url: '/getEduEvents', // 서버의 엔드포인트
                method: 'GET',
                dataType: 'json'
            });
            return response;
        } catch (error) {
            console.error("Error loading events:", error);
            return [];
        }
    }

    // 메뉴에서 "전시" 클릭 시 일정 데이터를 로드하고 전체 화면으로 캘린더를 확장
    $('#exhibitButton').on('click', async function() {
        // 전시회 리스트 숨기기
        $('#eventList').addClass('hidden');
        // 달력 전체 화면으로 확장
        $('#calendar').addClass('fullscreen');
        // 캘린더의 기존 이벤트 제거
        calendar.getEvents().forEach(event => event.remove());

        // 비동기적으로 이벤트를 불러오고 캘린더에 추가
        const events = await fetchExhibitEvents();
        console.log("Events received from server:", events);

        // 기존 이벤트 제거 없이 새로운 이벤트만 추가
        events.forEach((eventData, index) => {
            let colors = ['#FFB3BA', '#FFFFBA', '#BAE1FF', '#BAFFC9', '#E3B7FF', '#FFDFBA', '#D4A4FF', '#BFFCC6'];
            let color = colors[index % colors.length];

            calendar.addEvent({
                title: eventData.title,
                start: eventData.start,
                end: eventData.end,
                url: eventData.url,
                description: eventData.description, // 툴팁에 표시할 정보
                allDay: true,
				backgroundColor: color, // 파스텔 색상 적용
				borderColor: color, // 이벤트 경계선 색상도 동일하게 적용
				textColor: 'black' // 텍스트 색상을 검은색으로 변경
            });

            console.log(`Added event with color ${color}:`, eventData);
        });
    });

    // 공연 버튼 클릭 시
    $('#performanceButton').on('click', async function() {
        // 전시회 리스트 숨기기
        $('#eventList').addClass('hidden');
        // 달력 전체 화면으로 확장
        $('#calendar').addClass('fullscreen');
        // 캘린더의 기존 이벤트 제거
        calendar.getEvents().forEach(event => event.remove());

        // 비동기적으로 이벤트를 불러오고 캘린더에 추가
        const events = await fetchPerformEvents();
        console.log("Events received from server:", events);

        events.forEach((eventData, index) => {
            let colors = ['#FFB3BA', '#FFFFBA', '#BAE1FF', '#BAFFC9', '#E3B7FF', '#FFDFBA', '#D4A4FF', '#BFFCC6'];
            let color = colors[index % colors.length];

            calendar.addEvent({
                title: eventData.title,
                start: eventData.start,
                end: eventData.end,
                url: eventData.url,
                allDay: true,
				backgroundColor: color, // 파스텔 색상 적용
				borderColor: color, // 이벤트 경계선 색상도 동일하게 적용
				textColor: 'black' // 텍스트 색상을 검은색으로 변경
            });

            console.log(`Added event with color ${color}:`, eventData);
        });
    });

    // 교육 버튼 클릭 시
    $('#educationButton').on('click', async function() {
        // 전시회 리스트 숨기기
        $('#eventList').addClass('hidden');
        // 달력 전체 화면으로 확장
        $('#calendar').addClass('fullscreen');
        // 캘린더의 기존 이벤트 제거
        calendar.getEvents().forEach(event => event.remove());

        // 비동기적으로 이벤트를 불러오고 캘린더에 추가
        const events = await fetchEduEvents();
        console.log("Events received from server:", events);

        events.forEach((eventData, index) => {
            let colors = ['#FFB3BA', '#FFFFBA', '#BAE1FF', '#BAFFC9', '#E3B7FF', '#FFDFBA', '#D4A4FF', '#BFFCC6'];
            let color = colors[index % colors.length];

            calendar.addEvent({
                title: eventData.title,
                start: eventData.start,
                end: eventData.end,
                url: eventData.url,
                allDay: true,
				backgroundColor: color, // 파스텔 색상 적용
				borderColor: color, // 이벤트 경계선 색상도 동일하게 적용
				textColor: 'black' // 텍스트 색상을 검은색으로 변경
            });

            console.log(`Added event with color ${color}:`, eventData);
        });
    });

    // 페이지 로드 시 전시회 데이터를 가져와 전시회 리스트에 추가
    const initialEvents = await fetchExhibitEvents();
    console.log("Events received from server:", initialEvents);

    // 전시회 정보를 리스트에 추가 (이미지 포함)
    initialEvents.forEach((eventData) => {
        let eventItem = `
            <div class="event-item">
                <img src="${eventData.imageUrl}" alt="${eventData.title}">
                <h4>${eventData.title}</h4>
                <p>${eventData.start} ~ ${eventData.end}</p>
                <a href="${eventData.url}" target="_blank">More Info</a>
            </div>
        `;
        $('#eventList').append(eventItem); // 전시회 정보 영역에 추가
    });

    // 슬라이더 초기화
    $('#eventList').slick({
        infinite: true,
        slidesToShow: 3,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 3000,
        arrows: true,
    });

	
	calendar.on('eventClick', function(info) {
	       info.jsEvent.preventDefault(); // 링크의 기본 동작 방지
	       if (info.event.url) {
	           window.open(info.event.url, '_blank'); // 새 탭에서 링크 열기
	       }
	   });
});
