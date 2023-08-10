import { defineStore } from 'pinia'
import { Configuration, OpenAIApi } from 'openai'

const configuration = new Configuration({
  // 아래의 organization과 key는 절대 git에 commit 금지!!
  organization: import.meta.env.VITE_organization_key,
  apiKey: import.meta.env.VITE_openAI_key,
  // 위의 organization과 key는 절대 git에 commit 금지!!
  // commit 전 공란으로 두기 - 차후 별도의 파일로 관리 예정
})

delete configuration.baseOptions.headers['User-Agent']

const openai = new OpenAIApi(configuration)

export const useAiAssist = defineStore({
  id: 'aiAssist',
  state: () => ({
    answer: {},
  }),
  actions: {
    // 아래 기능은 질문에 답변 하는 기능
    // { type : 'string', question: 'string'} 형태의 매개변수를 가짐
    // string 형태로 답변자의 정체성을 설정할 수 있음
    // 정체성에 따른 다양한 답변 스펙트럼을 가짐
    async qna(values) {
      console.log('배포시에는 본 콘솔출력문을 삭제하시오. qna activated')
      const sampleQuestion = await openai.createChatCompletion({
        model: 'gpt-3.5-turbo',
        messages: [
          { role: 'system', content: values.type },
          { role: 'user', content: values.question },
        ],
      })
      let result = sampleQuestion.data.choices[0].message.content
      console.log(`배포시에는 본 콘솔출력문을 삭제하시오. ${result}`)
      this.answer = result
    },

    // 아래기능은 규정위반을 감지하는 기능
    // string 형태의 텍스트를 매개변수로 받으면 해당 텍스트의 폭력성, 선정성, 자해가능성
    // 등을 감지하여 각 카테고리 True/False 및 0~1 사이의 수치로도 결과를 반환함
    // 영어 이외의 언어는 감지하지 못하여 위의 qna기능을 사용해 영어로 번역 후에 텍스트를 검사함
    async violationDetect(value) {
      console.log(
        '배포시에는 본 콘솔출력문을 삭제하시오. violation detection activated',
      )
      let values = {
        type: 'You have to translate my message into English',
        question: value.text,
      }
      await this.qna(values)
      const violationAI = await openai.createModeration({
        input: this.answer,
      })
      let result = violationAI.data.results
      console.log(`배포시에는 본 콘솔출력문을 삭제하시오. ${result}`)
      this.answer = result
    },

    // 아래기능은 자기소개서 답변을 분석하는 기능
    // question과 answer를 담은 values를 매개변수로 보내면 질문에 따른 답변을 분석하고
    // 개선 방향을 제시해줌
    async coverLetterAnalyze(values) {
      console.log('function activated')
      const coverLetter = await openai.createChatCompletion({
        model: 'gpt-3.5-turbo',
        messages: [
          {
            role: 'system',
            content:
              '자기소개서 질문과 답변을 줄거야. 답변을 분석해서 어떻게 개선할 수 있을지 알려줘',
          },
          {
            role: 'user',
            content: `질문: ${values.question} 답변: ${values.answer}`,
          },
        ],
      })
      let result = coverLetter.data.choices[0].message.content
      console.log(result)
    },
  },
  persist: {
    enable: true,
  },
})
