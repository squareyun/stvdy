<script setup>
import { Configuration, OpenAIApi } from "openai";
import { Form, Field } from "vee-validate";
import { useAiAssist } from "@/stores"

const useOpenAiStore = useAiAssist()

async function onSubmit(values) {
  try {
    await useOpenAiStore.qna(values)
  } catch (error) {
    console.log(error)
  }
}

async function onSubmit1(value) {
  try {
    await useOpenAiStore.violationDetect(value)
  } catch (error) {
    console.log(error)
  }
}

async function onSubmit2(values) {
  try {
    await useOpenAiStore.coverLetterAnalyze(values)
  } catch (error) {
    console.log(error)
  }
}

// const configuration = new Configuration({
//   // 아래의 organization과 key는 절대 git에 commit 금지!!
//   organization: "",
//   apiKey: "",
//   // 위의 organization과 key는 절대 git에 commit 금지!!
//   // commit 전 공란으로 두기 - 차후 별도의 파일로 관리 예정
// });

// delete configuration.baseOptions.headers['User-Agent'];

// const openai = new OpenAIApi(configuration);

// async function onSubmit(values) {
//   const sampleQuestion = await openai.createChatCompletion({
//     model: "gpt-3.5-turbo",
//     messages: [
//       { "role": "system", "content": values.type },
//       { "role": "user", "content": values.question }
//     ]
//   })
//   const answerField = document.getElementById("answerField");
//   answerField.innerText = sampleQuestion.data.choices[0].message.content

// }

// const violationAI = await openai.createModeration({
//   input: "I want to fuck your mother!"
// })
// console.log(violationAI)




// const sampleQuestion = await openai.createChatCompletion({
//   model: "gpt-3.5-turbo",
//   messages: [
//     { "role": "system", "content": "You are a helpful assistant." },
//     { "role": "user", "content": "Who wrote Hamlet and Macbeth?" }
//   ]
// }
// )

// console.log(sampleQuestion.data.choices[0].message.content)

</script>

<template>
  <div style="color:white">
    Test function - AI Assist
    <Form @submit="onSubmit">
      <Field name="type" type="text" placeholder="답변 스타일" />
      <Field name="question" type="text" placeholder="질문" />
      <button type="submit">물어보기</button>
    </Form>
    <Form @submit="onSubmit2">
      <Field name="question" type="text" placeholder="자소서 질문" />
      <Field name="answer" type="text" placeholder="자소서 답변" />
      <button type="submit">물어보기</button>
    </Form>
    <span id="answerField"></span>
    <Form @submit="onSubmit1">
      <Field name="text" type="text" placeholder="규정 위반 감지" />
      <button type="submit">테스트하기</button>
    </Form>
  </div>
</template>

<style></style>
