import { defineStore } from 'pinia'

import { fetchWrapper } from '@/helpers'
import router from '@/router'
import { useAlertStore } from '@/stores'

const baseUrl = `${import.meta.env.VITE_API_URL}/users`


export const useAuthStore = defineStore({
  id: 'auth',
  state: () => ({
    // 유저가 로그인 상태를 유지할 수 있도록 로컬스토리지로부터 상태를 초기화
    user: JSON.parse(localStorage.getItem('user')),
    returnUrl: null,
  }),
  actions: {
    async login(username, password) {
      try {
        console.log('로그인 함수 도착')
        const user = await fetchWrapper.post(`${baseUrl}/authenticate`, {
          username,
          password,
        })
        console.log(user);


        // update pinia state
        this.user = user

        // store user details and jwt in local storage to keep user logged in between page refreshes
        localStorage.setItem('user', JSON.stringify(user))

        // redirect to previous url or default to home page
        router.push(this.returnUrl || '/')
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
