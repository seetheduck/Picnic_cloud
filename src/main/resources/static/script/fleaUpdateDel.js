const {createApp} = Vue;

createApp({
    data() {
        return {
            datas: [],
            mno : null
        }
    },
    mounted() {
        this.updateData();
    },
    methods: {
        async updateData(){
            const url = new URLSearchParams(window.location.search);
            this.mno = url.get('mno');
            //400 err
            //요청이 이상한 경우, get으로 가져와서 버튼 클릭시 put해야하는데
            //put으로 요청해서 발생한 에러였다.
            await axios.get(`http://localhost/fleaMarket/${this.mno}`)
            .then(response =>{
                // console.log(response.data);
                this.datas = response.data;
            })
            .catch(err =>{
                console.log("fleaUpdate Axios Fail : " +err );
            })
        },
        update(){
            axios.put(`http://localhost/fleaMarket/${this.mno}`, this.datas)
                .then(response => {
                    confirm("수정하시겠습니까?");
                    window.location.href = `fleamarketDetail.html?mno=${this.mno}`;
                })
                .catch(err => {
                    alert("failed" + err);
                });
        },
        cancel(){
            window.location.href = `fleamarketDetail.html?mno=${this.mno}`;
        }
    },
}).mount("#update");