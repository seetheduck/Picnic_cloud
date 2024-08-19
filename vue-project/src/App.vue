<template>
  <div id="app">
    <Topbar />
    <router-view></router-view> <!-- 라우터를 사용하는 경우 -->
  </div>
</template>

<script>
import Topbar from './components/Topbar.vue';

export default {
  components: {
    Topbar
  }
};
</script>