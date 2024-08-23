const { createApp } = Vue;
// TypeError: Cannot read properties of undefined (reading '_withMods')
// withModifiers 함수는 이벤트 핸들러에 수정자를 적용할 때 사용
// 즉, 이벤트 핸들러 오류
createApp({
    data() {
        return {
            content: [],  // 데이터(리스트)
            category: "전체",
            search: "",
            page: {       // 페이지네이션 정보
                size: 0,
                number: 0,
                totalElements: 0,
                totalPages: 0
            },
            pageNumber: 0,  // 현재 페이지 번호
            size: 9         // 페이지당 아이템 수
        };
    },
    mounted() {
        this.fetchData();
    },
    computed: {
        paginationPages() {
            const range = 2;  // 현재 페이지를 기준으로 보여줄 페이지 번호 범위
            const start = Math.max(0, this.page.number - range);
            const end = Math.min(this.page.totalPages - 1, this.page.number + range);
            const pages = [];
            for (let i = start; i <= end; i++) {
                pages.push(i);
            }
            console.log("Pagination Pages:", pages);
            return pages;
        }
    },
    methods: {
        fetchData() {
            // const url = `fleaMarket?page=${this.pageNumber}&size=${this.size}&category=${encodeURIComponent(this.category)}&search=${encodeURIComponent(this.search)}`;
            // const url = 'http://localhost:80/fleaMarket?page=0&size=9&category=전체&search=';
            
            const url = `http://localhost:80/fleaMarket?page=${this.pageNumber}&size=${this.size}&category=${encodeURIComponent(this.category)}&search=${encodeURIComponent(this.search)}`;
            fetch(url)
                .then(response => {
                    console.log(response)
                    if (!response.ok) {
                        throw new Error("Ajax error: " + response.statusText);
                    }
                    return response.json();
                })
                .then(data => {
                    this.content = data.content;
                    this.page = data.page;
                    console.log(this.page);
                })
                .catch(err => {
                    console.error("fleaMain Fetch error: ", err);
                });
        },
        changePage(pageNumber) {//번호 들어온 페이지
            this.pageNumber = pageNumber;
            this.fetchData(); //메서드 재실행
        },
        getFirstFilePath(mfilePath) {
            // mfilePath(배열) 첫 번째 요소 반환
            return Array.isArray(mfilePath) ? mfilePath[0] : mfilePath;
        },
        goToDetail(mno) {
            // restful하게 만들고 싶은데
            // window.location.href = `fleamarketDetail/${mno}`;
            // window.location.href = `FleaMarket/${mno}`;
            window.location.href = `http://localhost:80/fleamarketDetail.html?mno=${mno}`;
        },
        create(){
            window.location.href=`http://localhost:80/fleamarketCreate.html`;
        },
        searchbtn() {
            this.page = 0; // 검색 시 첫 페이지로 초기화
            this.fetchData();
        }
    }
}).mount("#app");
