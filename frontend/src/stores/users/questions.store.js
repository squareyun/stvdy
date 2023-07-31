import { defineStore } from 'pinia'
import { fetchWrapper } from '@/helpers'
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
      // await fetchWrapper.post(`${baseUrl}/create`, question)
      console.log(
        'baseUrl/question/create/{requestBody} // success/fail로 응답받음',
      )
    },
    async getAll() {
      // test codes below
      this.questions = [
        { id: 1, title: 'test-title1', user_id: 2 },
        { id: 2, title: 'test-title2', user_id: 1 },
      ]
      console.log('baseUrl/question/getAll')
    },
    async getById(id) {
      // 테스트용 코드 below
      this.question = {
        id: 1,
        title: 'test-title1',
        detail: 'test detail!!! ',
        user_id: id,
      }
      console.log('baseUrl/question/{id}')
    },
    async sortMostRecent() {
      console.log('baseUrl/question/sortMostRecent')
    },
    async sortNoAnswer() {
      console.log('baseUrl/question/sortNoAnswer')
    },
    async sortActivated() {
      console.log('baseUrl/question/sortActivated')
    },
    async getMyQtn() {
      console.log('baseUrl/question/getMyQtn')
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
