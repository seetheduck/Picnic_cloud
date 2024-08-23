/* eslint-disable no-unused-vars */
const TopBar = {
    template: `
      <header>
        <div id="upper-nav">
          <div id="logo"><router-link to="/">Picnic_Cloud</router-link></div>
          <nav>
            <ul>
              <li v-if="!isLoggedIn"><router-link to="/login">login</router-link></li>
              <li v-if="isLoggedIn">
                <router-link to="/mypage">MyPage</router-link>
                <span @click="goToChat" class="notification-icon">🔔</span>
              </li>
            </ul>
          </nav>  
        </div>
        <nav class="bottom-nav">
          <ul>
            <li><a href="/placeMain.html">테마추천</a></li>
            <li><router-link to="/market">중고거래</router-link></li>
            <li><a href="/map.html">지도</a></li>
            <li><router-link to="/calendar">행사일정</router-link></li>
            <li><router-link to="/book">이달의 책</router-link></li>
          </ul>
        </nav>
      </header>
    `,
    data() {
      return {
        isLoggedIn: false, // 임시로 false로 설정. 실제 값은 로그인 로직에 따라 변경되어야 함
      };
    },
    methods: {
      goToChat() {
        alert('Go to chat!'); // 실제로는 라우팅 또는 다른 로직이 필요
      }
    }
  };