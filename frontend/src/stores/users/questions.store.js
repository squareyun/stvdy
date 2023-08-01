import { defineStore } from 'pinia'
import { fetchWrapper } from '@/helpers'
import router from '@/router'

const baseUrl = `${import.meta.env.VITE_API_URL}/questions`

export const useQuestionsStore = defineStore({
  id: 'questions',
  state: () => ({
    questions: {},
    question: {},
    pickedQtn: {},
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
        { id: 1, title: 'test-title1', user_id: 1 },
        { id: 2, title: 'test-title2', user_id: 2 },
      ]
      console.log('baseUrl/question/getAll')
    },
    async getById(id) {
      // 테스트용 코드 below
      this.question = {
        id: 2,
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

    async update(id, params) {
      console.log('baseUrl/question/{params}')
    },
    async delete(id) {
      console.log('baseUrl/question/{id}')
    },
  },
})
