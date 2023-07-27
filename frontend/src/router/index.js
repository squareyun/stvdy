import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import { useAuthStore, useAlertStore } from '@/stores'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      children: [
        {
          path: '/mypage',
          name: 'mypage',
          component: () => import('../components/profile/MyPage.vue'),
          children: [
            {
              path: '/deactivate',
              name: 'deactivate',
              component: () => import('../components/profile/Deactivate.vue'),
            },
            {
              path: '/changepwd',
              name: 'changepwd',
              component: () => import('../components/profile/ChangePwd.vue'),
            },
            {
              path: '/changeusername',
              name: 'changeusername',
              component: () =>
                import('../components/profile/ChangeUsername.vue'),
            },
          ],
        },
      ],
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue'),
      children: [
        {
          path: '',
          name: 'login',
          component: () => import('../components/ent/LoginBox.vue'),
        },
        {
          path: '/regist',
          name: 'regist',
          component: () => import('../components/ent/RegisterBox.vue'),
        },
        {
          path: '/passwordReset',
          name: 'passwordReset',
          component: () => import('../components/ent/PasswordReset.vue'),
        },
      ],
    },
  ],
})

router.beforeEach(async (to) => {
  // 테스트용 색상 팔레트 저장
  const colorPalette = {
    backgroundUp: '#f8f8f2',
    topbarBasic: '#282a3620',
    topbarTime: '#202020',
    hlLight: '#282a36',
    font100: '#f8f8f2',
    font80: '#f8f8f2cc',
    font50: '#f8f8f280',
    font30: '#f8f8f24d',
    font25: '#f8f8f240',
    font20: '#f8f8f233',
    font10: '#f8f8f21a',
  }

  localStorage.setItem('colorPalette', JSON.stringify(colorPalette))

  // 컬러 팔레트를 로컬 스토리지에서 찾는다.
  const obj = localStorage.getItem('colorPalette')

  // 컬러 팔레트에 설정 된 값만 변경하고 나머지는 그대로 둔다.
  if (obj) {
    const colorPalette = JSON.parse(obj)
    for (const color in colorPalette) {
      document.documentElement.style.setProperty(
        '--' + color.replace(/([a-z])([A-Z])/g, '$1-$2').toLowerCase(),
        colorPalette[color],
      )
    }
  }

  // 유저 스토어 접근 후 경고를 알리는 스토어
  const alertStore = useAlertStore()
  alertStore.clear()

  // 로그인 없이도 접근 가능한 라우터
  // 접근 하려는 라우터가 public 인지 확인
  const publicPages = ['/about', '/regist', '/passwordReset']
  const authRequired = !publicPages.includes(to.path)

  // 로컬 스토리지의 유저 로그인 정보가 있는지 받아오는 스토어
  const authStore = useAuthStore()
  // public 라우터이면 유저 정보를 확인하지 않아 무한 반복 방지
  // 없으면 끝도 없이 유저 정보 검사후 이동을 반복
  if (authRequired && !authStore.user) {
    console.log('no user')
    console.log(authRequired)
    console.log(to.path)
    authStore.returnUrl = to.fullPath

    // 로그인 페이지로 넘김
    return '/about'
  }
})

export default router
