import { Configuration, OpenAIApi } from 'openai'

const configuration = new Configuration({
  // 아래의 organization과 key는 절대 git에 commit 금지!!
  organization: '',
  apiKey: '',
  // 위의 organization과 key는 절대 git에 commit 금지!!
  // commit 전 공란으로 두기 - 차후 별도의 파일로 관리 예정
})

delete configuration.baseOptions.headers['User-Agent']

const openai = new OpenAIApi(configuration)

async function onSubmit(values) {
  const sampleQuestion = await openai.createChatCompletion({
    model: 'gpt-3.5-turbo',
    messages: [
      { role: 'system', content: values.type },
      { role: 'user', content: values.question },
    ],
  })
  const answerField = document.getElementById('answerField')
  answerField.innerText = sampleQuestion.data.choices[0].message.content
}

const violationAI = await openai.createModeration({
  input: 'I want to fuck your mother!',
})
console.log(violationAI)
