const { createApp } = Vue;

createApp({
  data() {
    return {
      //초기화
      place: null,
      activeTab: "tab1", // 현재 활성화된 탭을 관리하는 상태 변수 추가
    };
  },
  methods: {
    showInfo() {
      //현재 페이지의 URL에서 쿼리스트링 부분을 가져옴(동적 데이터로딩)
      const urlParams = new URLSearchParams(window.location.search); //파싱할수있는객체생성. //?pNo=3
      const num = urlParams.get("pNo"); //객체에서 매개변수 값 추출 //3

      axios
        .get(`http://localhost/places/${num}`)
        .then((res) => {
          this.place = res.data;
        })
        .catch((err) => {
          console.log("장소 정보: ", err);
        });
    },
    getImageUrl(imagePath) {
      //서버의 url과 이미지 경로를 조합하여  전체 URL을 만듦.
      return `http://localhost${imagePath}`;
    },
  },
  mounted() {
    this.showInfo(); //페이지가 로드될때 showInfo()를 호출(버튼x)
  },
}).mount("#app");
