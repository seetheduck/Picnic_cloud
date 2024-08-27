const { createApp } = Vue;

createApp({
    data() {
        return {
            items : [],
            no : null
        }
    },
    // TypeError: Cannot read properties of undefined (reading 'mfilePath')
    // at Proxy.render (eval at compileToFunction .. 에러 발생
    // mount되기 전에 값을 가져와야한다.> 하지만 마운트될때 코드 오타있는지 확인하기
     mounted() {
        this.detailOne();
    },
    methods: {
        detailOne(){
            //window.location (Location 객체) 가져오기
            // ""                   window.location.port
            // "/325"               window.location.pathname 
            // ?category=764998"    window.location.search
            const url = new URLSearchParams(window.location.search);
            // const mno = url.get('mno'); > 이 메서드에서만 사용할게 아니기에 전역으로 넘김
            this.no = url.get('no');

            if(this.no){
                axios.get(`http://localhost/fleaMarket/${this.no}`)
                .then(response => {
                    this.items = response.data;
                })
                .catch(err => {
                    console.log("fleaDetail Axios error: ", err);
                });
            }else{
                console.log("url에 번호 없음");
            }
        },
        getFirstFilePath(filePath) {
            return Array.isArray(filePath) ? filePath[0] : filePath;
        },
        fleaUpdate(){ //수정 버튼
            window.location.href=`fleamarketUpdate.html?no=${this.no}`
        },
        fleaDelete(){ //삭제 버튼
            res = confirm('다시 되돌릴 수 없습니다. 삭제하시겠습니까?');
            if(!res){
                console.log('취소하였습니다.');
            }else{
                axios.delete(`http://localhost/fleaMarket/${this.no}`)
                .then(response=>{
                    console.log(response);
                    alert('삭제되었습니다.');
                    window.location.href = `http://localhost/fleamarket.html`;
                })
                .catch(err =>{
                    alert('삭제에 실패했습니다.');
                    window.location.href = `http://localhost/fleamarket.html`;
                });
            }
        },
        list(){
            window.location.href = `fleamarket.html`;
        }
    },
}).mount("#detail");