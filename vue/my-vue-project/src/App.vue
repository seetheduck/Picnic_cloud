<template>
  <div id="topBar">
    <TopBar />
    <router-view></router-view> 
  </div>
  <main>
    <div id="contentArea">
      <div id="eventBanner">
        이벤트 배너 페이지
      </div>
      <div id="body1">
        
      </div>
      <div id="body2">
        
      </div>
      <div id="body3">
        
      </div>
    </div>
  </main>
  <div id="footerbox">
    <FooterBox />
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
#contentArea {
  border: 1px solid black;
  font-weight: bold;
  margin: 30px 0px; 

}
</style>