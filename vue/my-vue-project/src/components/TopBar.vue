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
import './TopBar.css';

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

