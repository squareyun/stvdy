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
    answers: [
      { id: 1, user_id: 1, question_id: 1, detail: 'Sample Answer1' },
      { id: 2, user_id: 1, question_id: 1, detail: 'Sample Answer2' },
      { id: 3, user_id: 1, question_id: 2, detail: 'Sample Answer3' },
      { id: 4, user_id: 1, question_id: 2, detail: 'Sample Answer4' },
      { id: 5, user_id: 2, question_id: 1, detail: 'Sample Answer5' },
      { id: 6, user_id: 2, question_id: 1, detail: 'Sample Answer6' },
      { id: 7, user_id: 2, question_id: 2, detail: 'Sample Answer7' },
      { id: 8, user_id: 2, question_id: 2, detail: 'Sample Answer8' },
    ],
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
        { id: 3, title: 'test-title3', user_id: 1 },
        { id: 4, title: 'test-title4', user_id: 2 },
        { id: 5, title: 'test-title5', user_id: 1 },
        { id: 6, title: 'test-title6', user_id: 2 },
      ]
      console.log('baseUrl/question/getAll')
    },
    async getById(qtnId) {
      // 테스트용 코드 below
      for (let i = 0; i < this.questions.length; i++) {
        if (this.questions[i].id == qtnId) {
          this.question = this.questions[i]
        }
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

    async createAnswer(answer) {
      console.log('baseUrl/answer/{answer}')
    },
  },
})
