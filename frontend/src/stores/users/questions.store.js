import { defineStore } from 'pinia'
import { fetchWrapper } from '@/helpers'
// import { useAuthStore } from '@/stores'
import router from '@/router'

const baseUrl = `${import.meta.env.VITE_API_URL}/questions`

export const useQuestionsStore = defineStore({
  id: 'questions',
  state: () => ({
    questions: {},
    question: {},
  }),
  actions: {
    async create(question) {
      await fetchWrapper.post(`${baseUrl}/create`, question)
      // baseUrl/users/register/{requestBody} // success/fail로 응답받음
    },
    async getAll() {
      // this.questions = { loading: true }
      // try {
      //   this.questions = await fetchWrapper.get(baseUrl)
      // } catch (error) {
      //   this.questions = { error }
      // }
      // 테스트용
      this.questions = [
        { id: 1, title: 'test-title1' },
        { id: 2, title: 'test-title2' },
      ]
    },
    async getById(id) {
      this.question = { loading: true }
      try {
        this.question = await fetchWrapper.get(`${baseUrl}/${id}`)
      } catch (error) {
        this.question = { error }
      }
    },
    // async update(id, params) {
    //   await fetchWrapper.put(`${baseUrl}/${id}`, params)

    //   // update stored user if the logged in user updated their own record
    //   const authStore = useAuthStore()
    //   if (id === authStore.user.id) {
    //     // update local storage
    //     const user = { ...authStore.user, ...params }
    //     localStorage.setItem('user', JSON.stringify(user))

    //     // update auth user in pinia state
    //     authStore.user = user
    //   }
    // },
    // async delete(id) {
    //   // add isDeleting prop to user being deleted
    //   this.users.find((x) => x.id === id).isDeleting = true

    //   await fetchWrapper.delete(`${baseUrl}/${id}`)

    //   // remove user from list after deleted
    //   this.users = this.users.filter((x) => x.id !== id)

    //   // auto logout if the logged in user deleted their own record
    //   const authStore = useAuthStore()
    //   if (id === authStore.user.id) {
    //     authStore.logout()
    //   }
    // },
  },
})
