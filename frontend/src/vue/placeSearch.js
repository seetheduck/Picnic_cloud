const {createApp} = Vue;

createApp({
    data(){
        return{//초기화
            places:[],
            keyword:''
        }
    },
    methods: {
        axiosData(keyword=''){//keyword는 기본값인 빈 문자열 ''이 되어, 카테고리만을 기준으로 데이터를 요청

           //현재 페이지의 URL에서 쿼리스트링 부분을 가져옴(동적 데이터로딩)
            const urlParams= new URLSearchParams(window.location.search); //파싱할수있는객체생성. //?pCategory=waterpark
            const category = urlParams.get('pCategory');//객체에서 매개변수 값 추출 //waterpark

            //카테고리 없을때. 사용자가 잘못된 URL로 접근했을 때 문제를 방지
            if (!category) {
                console.error('카테고리 정보가 없습니다.');
                return;
            }

            //키워드가 있으면 쿼리스트링에 추가
            let query = `http://localhost/places?pCategory=${category}`;
            if(keyword) {
                query += `&keyword=${keyword}`;
            }

            // pCategory에 따른 데이터 요청
            axios.get(query)
            .then(res => {
                this.places = res.data;
            })
            .catch(err => {
                console.error('장소출력 오류: ', err);
                //alert('장소 데이터를 가져오는 데 문제가 발생했습니다.');
            });
        },
        searchFunc(){ // 키워드 값 입력후 버튼 눌렀을때 실행
            this.axiosData(this.keyword);

        // 현재 URL을 가져와서 검색어를 반영해 URL을 갱신
        const urlParams = new URLSearchParams(window.location.search);
        urlParams.set('keyword', this.keyword);
        const newUrl = `${window.location.pathname}?${urlParams.toString()}`;
        window.history.pushState({path: newUrl}, '', newUrl);
        },
        getImageUrl(imagePath){
            //서버의 url과 이미지 경로를 조합하여  전체 URL을 만듦.
            return `http://localhost:80${imagePath}`;
        }  
    },
    mounted() {
    this.axiosData(); // 페이지가 로드될 때 axiosData()를 호출(버튼x.)
    }
}).mount('#app');