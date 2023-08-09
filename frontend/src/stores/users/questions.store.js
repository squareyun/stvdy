import { defineStore } from 'pinia'
import {
  listQuestion,
  getQuestion,
  isLikeQuestion,
  likesQuestion,
  getAnswers,
} from '@/api/question'
import { ref } from 'vue'

export const useQuestionStore = defineStore('questions', () => {
  const question = ref([])
  const questions = ref([])
  const answers = ref([])

  const getList = async (cond) => {
    await listQuestion(
      cond,
      (res) => {
        questions.value = res.data.question.content
        var now = new Date()
        for (var q in questions.value) {
          if (questions.value[q].questionScore == null)
            questions.value[q].questionScore = 0

          var wrote = new Date(questions.value[q].regist_time)

          var dDay = now.getDay() - wrote.getDay()
          if (dDay > 0) {
            var year = wrote.getFullYear()
            var month = ('0' + (wrote.getMonth() + 1)).slice(-2)
            var day = ('0' + wrote.getDate()).slice(-2)

            questions.value[q].regist_time = year + '-' + month + '-' + day
            continue
          }
          var dHour = now.getHours() - wrote.getHours()
          if (dHour > 0) {
            questions.value[q].regist_time = dHour + '시간 전'
            continue
          }
          var dMinutes = now.getMinutes() - wrote.getMinutes()
          if (dMinutes > 0) {
            questions.value[q].regist_time = dMinutes + '분 전'
            continue
          }
          var dSeconds = now.getSeconds() - wrote.getSeconds()
          if (dSeconds > 0) {
            questions.value[q].regist_time = dSeconds + '초 전'
            continue
          }
        }
      },
      (fail) => {
        console.log(fail)
      },
    )
  }

  const getQuestionById = async (id) => {
    question.value = {}
    await getQuestion(
      id,
      (res) => {
        question.value = res.data.question
        question.value.regist_time =
          question.value.regist_time.replaceAll('T', ' ') + ' ·'

        getAnswers(
          question.value.id,
          (res) => {
            answers.value = res.data.answers
            for (var a in answers.value) {
              answers.value[a].regist_time =
                answers.value[a].regist_time.replaceAll('T', ' ') + ' ·'
            }
          },
          (fail) => console.log(fail),
        )
      },
      (fail) => {
        console.log(fail)
      },
    )
  }

  // const getAnswerList = async () => {
  //   await getAnswers(
  //     question.value.id,
  //     (res) => console.log(res),
  //     (fail) => console.log(fail),
  //   )
  // }

  return { questions, question, answers, getList, getQuestionById }
})
