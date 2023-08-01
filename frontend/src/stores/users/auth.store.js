import { defineStore } from 'pinia'
import { fetchWrapper } from '@/helpers'
import { useAlertStore } from '@/stores'
import { hello } from '@/api/user'
import router from '@/router'

const baseUrl = `${import.meta.env.VITE_API_URL}/users`

export const useAuthStore = defineStore({
  id: 'auth',
  state: () => ({
    // 유저가 로그인 상태를 유지할 수 있도록 로컬스토리지로부터 상태를 초기화
    isLogin: false,
    isValidToken: false,
    user: JSON.parse(localStorage.getItem('user')),
    returnUrl: null,
  }),
  actions: {
    async gethello() {
      hello(
        ({ data }) => {
          console.log(data)
        },
        (error) => {
          console.log(error)
        },
      )
    },
    async login(email, password, keeplog) {
      try {
        // baseUrl/users/login/{requestBody}
        const user = await fetchWrapper.post(`${baseUrl}/authenticate`, {
          email,
          password,
          keeplog,
        })

        // update pinia state
        this.user = user

        // 페이지 새로고침을해도 유저가 로그인을 유지하도록 유저 디테일과 jwt를 로컬스토리지에 저장 -> 일단은 로그인유지를 클릭시에만 가능
        if (keeplog === true) {
          localStorage.setItem('user', JSON.stringify(user))
        }
        // 기본 페이지로 리다이렉트
        router.push('/')
      } catch (error) {
        const alertStore = useAlertStore()
        alertStore.error(error)
      }
    },
    async passwordreset(username, email) {
      try {
        console.log('>>>>암호 리셋함수 도착<<<<')
        // baseUrl/users/pwdreset/{requestBody}
      } catch (error) {
        const alertStore = useAlertStore()
        alertStore.error(error)
      }
      router.push(this.returnUrl || '/')
    },
    async deactivate() {
      try {
        console.log('>>>>비활성화 함수 도착<<<<')
        console.log(this.user)
        // baseUrl/users/{id}
        this.user = null
        localStorage.removeItem('user')
        router.push('/about')
      } catch (error) {
        const alertStore = useAlertStore()
        alertStore.error(error)
      }
    },
    logout() {
      this.user = null
      localStorage.removeItem('user')
      router.push('/about')
    },
  },
})
