// router.js

const routes = [
    { path: '/', component: MainPage },
	{ 
	    path: '/map', 
	    beforeEnter() {
	      window.location.href = '/map.html';  // 특정 HTML 파일로 리다이렉트
	    }
	  },
  ];
  
  const router = VueRouter.createRouter({
    history: VueRouter.createWebHashHistory(),
    routes
  });
  