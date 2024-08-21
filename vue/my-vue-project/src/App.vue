<template>
  <div id="topBar">
    <TopBar />
    <router-view></router-view> 
    </div>
    <div id="mainBanner">
     메인배너 자리에요
    </div>
  <div id="contentArea">
    <div id="body1">
      <h3>지금이 딱! 야외 활동 추천</h3> 
      <div class="images">
        <div class="image"></div>
        <div class="image"></div>
      </div>
    </div>
    <div id="body2">
      <h3> 이달의 전시, 공연 일정</h3> 
      <div class="calendar">
        <div class="thismonth">캘린더 들어갈 자리</div>
        <div class="calendarinfo">상세 내용 들어갈 자리</div>
        <div class="calendarinfo">상세 내용 들어갈 자리</div>
      </div>
    </div>
    <div id="body3">
      <h3>올 가을에는 마음을 살찌워 보아요.</h3> 
      <div class="recommend">
        <div class="book"></div>
        <div class="book"></div>
        <div class="book"></div>
        <div class="book"></div>
      </div>
    </div>
  </div>
  <div id="footerBox">
    <FooterBox />
    <router-view></router-view> 
  </div>
</template>

<script>
import FooterBox from './components/FooterBox.vue';
import TopBar from './components/TopBar.vue'

export default {
  components: {
    TopBar,
    FooterBox
  }
};
</script>

<style>
/*이벤트 배너 css */
#mainBanner { 
  width: 100%;
  height: 420px;
  padding: 0 66px;
  background-color: grey;
}
/*content내부의 body들의 공통 css */
#contentArea > div {
  flex: 1;
  margin: 52px;
  padding: 0 75.5px;
  display: flex;
  flex-direction: column;
  align-items: center;
  border: 1px solid black;
  width: 100%;
}
/*body1의 이미지 css */
.images {
  display: flex;
  justify-content: center; 
  gap: 17px; 
  margin-top: 24px; 
  width: 100%;
}

.image{
  width: 47%;
  height: 298px;
  border: 1px solid black;
}
/*body2의 캘린더 css */
#body2{
  display: flex;
  
  margin-top: 24px; 
}
.calendar{
  width: 100%;
  display: flex;
  justify-content: center;
  margin-top: 24px; 
  border: 1px solid black;
}
.thismonth{
  width: 60%;
}
.calendarinfo{
  width: 20%;
  height: 417px;
  border: 1px solid red;
}
/*body3의 책추천 css */
.recommend{
  width: 100%;
  height: 353px;
  display: flex;
  justify-content: center;
  margin-top: 24px; 
  gap: 43px; 
  border: 1px solid black;
}

.book{
  width: 20%;
  height: 230px;
  border-radius: 50%;
  background-color: aqua;
}
</style>