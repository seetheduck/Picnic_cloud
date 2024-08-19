<template>
    <header>
    <div id="upper-nav">
  <div id="logo"><router-link to="/">Picnic_Cloud</router-link></div>
  <nav>
    <ul>
      <li v-if="!isLoggedIn"><router-link to="/login">
        login
      </router-link></li>
      <li v-if="isLoggedIn">
        <router-link to="/mypage">MyPage</router-link>
        <span @click="goToChat" class="notification-icon">🔔</span>
      </li>
    </ul>
  </nav>  
  </div>
  <nav class="bottom-nav">
      <ul>
        <li><router-link to="" >테마 추천</router-link></li>
        <li><router-link to="/about">중고거래</router-link></li>
        <li><router-link to="/services">지도</router-link></li>
        <li><router-link to="/contact">행사일정</router-link></li>
      </ul>
    </nav>
  </header>
</template>

<script>
//import { computed } from 'vue';
// import { isLoggedIn, login, logout, goToChat} from '../store/auth';

export default {
  setup() {
    return {
      // isLoggedIn: computed(() => isLoggedIn.value),
      // login,
      // logout,
      // goToChat
    };
  }
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
}
body {
  background-color: #ffff;
}
header {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  height: 80px;
  background-color: #87ceeb;
  padding: 0 10px;
}
#upper-nav {
  display: flex;
  width: 100%;
  height: 60%;
}
#logo{
  flex: 75%;
}
nav ul {
  list-style-type: none;
  display: flex;
  flex: 25%;
  justify-content: flex-end;
}

nav ul li {
  margin-right: 15px;
}

.notification-icon {
  cursor: pointer;
  margin-left: 10px;
}

/* 하단 네비게이션 스타일 */
.bottom-nav {
  margin-top: 20px;
  width: 100%;
  height: 2;
  background-color: #ffff;
}

.bottom-nav ul {
  list-style-type: none;
  display: flex;
  justify-content: center;
  width: 100%;
  
}

.bottom-nav ul li {
  margin-right: 20px;
}

/* 현재 활성화된 링크에 밑줄 추가 */
.bottom-nav .router-link-active {
  text-decoration: underline;
}
</style>
