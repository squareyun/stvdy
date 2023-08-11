<script setup>
import { Form, Field } from "vee-validate";
import { useAiAssist } from "@/stores"

const useOpenAiStore = useAiAssist()
let qnaQuery = ''
let aiPersona = ''

async function onSubmit(values) {
  try {
    if (values.type !== aiPersona) {
      qnaQuery = ''
    }
    aiPersona = values.type
    const chatList = document.getElementById('aiChatList')
    const newMessage = document.createElement("li")
    newMessage.innerText = '나: ' + values.question
    chatList.appendChild(newMessage)
    values.question = qnaQuery + values.question

    const loadingImg = document.createElement("img")
    loadingImg.setAttribute('src', '/loading.gif')
    loadingImg.setAttribute('id', 'loading-wheel')
    chatList.appendChild(loadingImg)

    const questionBox = document.getElementById('ai-question')
    questionBox.value = ''

    await useOpenAiStore.qna(values)

    chatList.removeChild(loadingImg)
    const aiReply = document.createElement("li")
    aiReply.innerText = 'AI: ' + useOpenAiStore.answer
    chatList.appendChild(aiReply)
    qnaQuery += `[Previous Question: ${values.question}, Previous Answer: ${useOpenAiStore.answer}], New Question: `
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

</script>

<template>
  <div id="aiChatBox">
    Test function - AI Assist
    <ul id="aiChatList">
    </ul>
    <Form @submit="onSubmit">
      <Field name="type" as="select">
        <option value="" disabled selected>답변해줄 인물을 고르세요.</option>
        <option value="You are a good assistant">기본 AI</option>
        <option value="You are Marv, a chatbot with a soul of nasty sarcasm">가명_까칠이</option>
        <option value="You are Emily, a chatbot with a soul of my teenage bestie girl who is always cheerful">가명_베프
        </option>
        <option value="You are Doctor Muller, a chatbot with a soul of super serious professor">가명_교수님
        </option>
      </Field>
      <br>
      <Field id="ai-question" name="question" type="text" placeholder="질문" />
      <button type="submit">물어보기</button>
    </Form>
    <!-- <Form @submit="onSubmit2">
      <Field name="question" type="text" placeholder="자소서 질문" />
      <Field name="answer" type="text" placeholder="자소서 답변" />
      <button type="submit">물어보기</button>
    </Form>
    <span id="answerField"></span>
    <Form @submit="onSubmit1">
      <Field name="text" type="text" placeholder="규정 위반 감지" />
      <button type="submit">테스트하기</button>
    </Form> -->
  </div>
</template>

<style>
#aiChatBox {
  position: absolute;
  bottom: 10px;
  right: 10px;
  color: white;
  max-width: 400px;
}

#loading-wheel {
  height: 50px;
  width: 50px;
}
</style>
