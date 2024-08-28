const {createApp} = Vue;

createApp({
    data() {
        return {
            id:"user01",
            title:"",
            price:"",
            contents:"",
            category:"",
            filePath : null,
            errors: {},
        }
    },
    methods: {
        validateForm() {
            this.errors = {};

            if (!this.title) {
                this.errors.title = '제목을 입력해 주세요.';
            }
            if (!this.price) {
                this.errors.price = '가격을 입력해 주세요.';
            }
            if (!this.contents) {
                this.errors.contents = '설명을 입력해 주세요.';
            }
            if (!this.category) {
                this.errors.category = '카테고리를 선택해 주세요.';
            }
            if (!this.filePath) {
                this.errors.filePath = '사진을 선택해 주세요.';
            }

            return Object.keys(this.errors).length === 0;
        },
        async fleamarketInput(){
            //폼 검증
            if (!this.validateForm()) {
                return;
            }
            const formData = new FormData(); //파일과 함께 넘길려면
            
            // JSON 형태로 변환할 dto 객체
            const dto = {
                id: this.id,
                title: this.title,
                price: this.price,
                contents: this.contents,
                category: this.category
            };
            
             // dto를 JSON 문자열로 변환하여 FormData에 추가
             formData.append("dto", JSON.stringify(dto));c
            
             if (this.FilePath) { //파일이 첨부된 경우
                 formData.append("file", this.filePath);
             }

            await fetch("http://localhost:80/fleaMarket",{
                method:"POST",
                // headers:{"multipart/form-data":"application/x-www-form-urlencoded;charset=utf=8"},
                // 415 Unsupported Media Type (err)
                // 클라이언트가 서버에 전송한 데이터의 MIME 타입이 서버가 처리할 수 있는 형식이 아닐 때 발생
                // FormData는 application/json 설정을 원한다.
                body:formData
            })
            .then(data =>{
                alert('성공');
                window.location.href = 'fleamarket.html';
            })
            .catch(err => {
                //const errorData = response.json();
                console.error('Error:', err);
            });
        },
        handleFileUpload(event) {
            // 단일 파일을 선택한 경우, 파일 객체를 this.mFilePath에 저장
            this.FilePath = event.target.files[0]; 
        },
        back(){
            window.location.href = 'fleamarket.html';
        }
    },
}).mount('#create');