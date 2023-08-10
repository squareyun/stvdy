import { defineStore } from 'pinia'
import { listQuestion, getQuestion, getAnswers } from '@/api/question'
import { ref } from 'vue'

export const useQuestionStore = defineStore('questions', () => {
  const question = ref([])
  const questions = ref([])
  const answers = ref([])
  const totalAmount = ref([])

  const getList = async (cond) => {
    await listQuestion(
      cond,
      (res) => {
        questions.value = res.data.question.content
        console.log(questions.value)
        totalAmount.value = res.data.question.totalElements
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
      {
        id: 2,
        title: 'test-title2',
        user_id: 2,
        detail: '국회나 그 위원회의 요구가 있을 때에는 국무총리·국무위원 또는 정부위원은 출석·답변하여야 하며, 국무총리 또는 국무위원이 출석요구를 받은 때에는 국무위원 또는 정부위원으로 하여금 출석·답변하게 할 수 있다. 대통령은 헌법과 법률이 정하는 바에 의하여 국군을 통수한다. 국무총리는 국무위원의 해임을 대통령에게 건의할 수 있다. 모든 국민은 신속한 재판을 받을 권리를 가진다. 형사피고인은 상당한 이유가 없는 한 지체없이 공개재판을 받을 권리를 가진다. 대통령은 국민의 보통·평등·직접·비밀선거에 의하여 선출한다. 환경권의 내용과 행사에 관하여는 법률로 정한다. 국회에 제출된 법률안 기타의 의안은 회기중에 의결되지 못한 이유로 폐기되지 아니한다. 다만, 국회의원의 임기가 만료된 때에는 그러하지 아니하다.',
        best_answer: null,
      },
    )
  }

  const getQuestionById = async (id) => {
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
      {
        id: 4,
        title: 'test-title4',
        user_id: 2,
        detail: '찬미를 무엇이 소담스러운 것은 아니다. 거친 있을 보는 얼마나 있는 이상, 인생을 더운지라 봄바람이다. 대고, 만물은 석가는 듣는다. 뜨고, 지혜는 예수는 청춘 꾸며 봄바람을 가치를 앞이 이것이다. 풀이 살 얼마나 옷을 그것을 실현에 가슴이 쓸쓸하랴? 두손을 수 살았으며, 보이는 거친 이것은 구하지 그리하였는가? 풀밭에 스며들어 힘차게 봄바람이다. 그들의 심장은 보이는 낙원을 힘있다. 스며들어 듣기만 군영과 품에 가치를 부패뿐이다. 별과 피가 아니한 뭇 듣기만 피가 풍부하게 봄바람이다.',
        best_answer: null,
      },
    )
  }

  const clearState = () => {
    question.value = {}
    questions.value = {}
    answers.value = {}
    totalAmount.value = {}
  }

  // const getLikes = async (id) => {
  //   question.value = {}
  //   await getQuestion(
  //     id,
  //     (res) => {
  //       question.value = res.data.question
  //       question.value.regist_time =
  //         question.value.regist_time.replaceAll('T', ' ') + ' ·'

  //       getAnswers(
  //         question.value.id,
  //         (res) => {
  //           answers.value = res.data.answers
  //           for (var a in answers.value) {
  //             answers.value[a].regist_time =
  //               answers.value[a].regist_time.replaceAll('T', ' ') + ' ·'
  //           }
  //         },
  //         (fail) => console.log(fail),
  //       )
  //     },
  //     (fail) => {
  //       console.log(fail)
  //     },
  //   )
  // }

  // const getAnswerList = async () => {
  //   await getAnswers(
  //     question.value.id,
  //     (res) => console.log(res),
  //     (fail) => console.log(fail),
  //   )
  // }

  return {
    questions,
    question,
    answers,
    totalAmount,
    getList,
    getQuestionById,
    clearState,
  }
})
