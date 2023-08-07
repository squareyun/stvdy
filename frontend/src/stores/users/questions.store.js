import { defineStore } from 'pinia'

const baseUrl = `${import.meta.env.VITE_API_URL}/questions`

export const useQuestionsStore = defineStore({
  id: 'questions',
  state: () => ({
    questions: [
      {
        id: 1,
        title: 'test-title1',
        user_id: 1,
        detail: 'Open Your Eyes',
        best_answer: null,
      },
      {
        id: 2,
        title: 'test-title2',
        user_id: 2,
        detail: 'You are welcome',
        best_answer: null,
      },
      {
        id: 3,
        title: 'test-title3',
        user_id: 1,
        detail: 'It is ok it is',
        best_answer: null,
      },
      {
        id: 4,
        title: 'test-title4',
        user_id: 2,
        detail: 'When the night got cold',
        best_answer: null,
      },
      {
        id: 5,
        title: 'test-title5',
        user_id: 1,
        detail: 'So what can I say',
        best_answer: null,
      },
      {
        id: 6,
        title: 'test-title6',
        user_id: 2,
        detail: 'There is no need to prey',
        best_answer: null,
      },
    ],
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
    category: 'sort-new',
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
    sortNew() {
      this.category = 'sort-new'
    },
    sortActive() {
      this.category = 'sort-active'
    },
    sortNone() {
      this.category = 'sort-none'
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
    async deleteAnswer(answer) {
      console.log('baseUrl/answer/{answer}')
    },
    async awardAnswer(id) {
      // this.question.id와 매개변수로 받은 id를 같이 보내야함
      console.log('baseUrl/question/award/{param}')
    },
  },
})
