<script setup>
import { Form, Field } from 'vee-validate'
import { useAiAssist, useUserStore } from '@/stores'
import { computed, onMounted } from 'vue'

const userStore = useUserStore() // 프로필 이미지 등록을 위해 userStore사용
const user = computed(() => userStore.user)

const useOpenAiStore = useAiAssist()
let qnaQuery = ''
let aiPersona = ''

let activated = false
let first = true

onMounted(() => {
  first = true

  if (useOpenAiStore.init()) {
    const aiReply = document.createElement('li')
    const chatList = document.getElementById('ai-chat-list')
    aiReply.innerText = 'GPT 3.5: ' + useOpenAiStore.answer
    chatList.appendChild(aiReply)
  } else {
    activated = true
    const values = {
      type: 'You are a good assistant',
      question: '안녕 chatgpt 반갑게 인사해줄래?',
    }

    onSubmit(values)
  }
})

async function onSubmit(values) {
  const questionBox = document.getElementById('ai-question')
  questionBox.value = ''

  const chatList = document.getElementById('ai-chat-list')
  const aiReply = document.createElement('li')

  if (activated) {
    const loadingImg = document.createElement('img')
    loadingImg.setAttribute('src', '/loading.gif')
    loadingImg.setAttribute('id', 'loading-wheel')
    try {
      if (values.type !== aiPersona) {
        values.type = 'You are a good assistant'
      }
      aiPersona = values.type
      if (!first) {
        const newMessage = document.createElement('li')
        newMessage.innerText = '나: ' + values.question
        newMessage.style.color = 'var(--hl-pres)'
        chatList.appendChild(newMessage)
      }
      values.question = qnaQuery + values.question

      chatList.appendChild(loadingImg)

      await useOpenAiStore.qna(values)

      aiReply.innerText = 'GPT 3.5: ' + useOpenAiStore.answer
      chatList.appendChild(aiReply)
      qnaQuery += `[Previous Question: ${values.question}, Previous Answer: ${useOpenAiStore.answer}], New Question: `

      if (first) first = false
    } catch (error) {
      aiReply.innerText = 'GPT 3.5: ' + '에러 확인. API Key를 확인해주세요.'
    }
    chatList.removeChild(loadingImg)
  } else {
    userStore.changeApiKey(values.question)
    if (useOpenAiStore.init()) {
      aiReply.innerText = 'GPT 3.5: ' + useOpenAiStore.answer
      chatList.appendChild(aiReply)
    } else {
      activated = true
      if (first) first = false
      aiReply.innerText = 'GPT 3.5: ' + useOpenAiStore.answer
      chatList.appendChild(aiReply)
    }
  }
}

const openAiAssist = () => {
  const c = document.getElementById('ai-chat-box')
  const t = document.getElementById('ai-chat-toggle')
  if (c.style.display == '' || c.style.display == 'none') {
    c.style.display = 'block'
    t.style.opacity = 1
  } else {
    c.style.display = 'none'
    t.style.opacity = 0.8
  }
}
</script>

<template>
  <div>
    <div
      id="ai-chat-toggle"
      @click="openAiAssist">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="1.2rem"
        height="1.2rem"
        viewBox="0 0 671.194 680.2487">
        <path
          d="M626.9464,278.4037a169.4492,169.4492,0,0,0-14.5642-139.187A171.3828,171.3828,0,0,0,427.7883,56.9841,169.45,169.45,0,0,0,299.9746.0034,171.3985,171.3985,0,0,0,136.4751,118.6719,169.5077,169.5077,0,0,0,23.1574,200.8775,171.41,171.41,0,0,0,44.2385,401.845,169.4564,169.4564,0,0,0,58.8021,541.0325a171.4,171.4,0,0,0,184.5945,82.2318A169.4474,169.4474,0,0,0,371.21,680.2454,171.4,171.4,0,0,0,534.7642,561.51a169.504,169.504,0,0,0,113.3175-82.2063,171.4116,171.4116,0,0,0-21.1353-200.9ZM371.2647,635.7758a127.1077,127.1077,0,0,1-81.6027-29.5024c1.0323-.5629,2.8435-1.556,4.0237-2.2788L429.13,525.7575a22.0226,22.0226,0,0,0,11.1306-19.27V315.5368l57.25,33.0567a2.0332,2.0332,0,0,1,1.1122,1.568V508.2972A127.64,127.64,0,0,1,371.2647,635.7758ZM97.3705,518.7985a127.0536,127.0536,0,0,1-15.2074-85.4256c1.0057.6037,2.7624,1.6768,4.0231,2.4012L221.63,514.01a22.04,22.04,0,0,0,22.2492,0L409.243,418.5281v66.1134a2.0529,2.0529,0,0,1-.818,1.7568l-136.92,79.0534a127.6145,127.6145,0,0,1-174.134-46.6532ZM61.7391,223.1114a127.0146,127.0146,0,0,1,66.3545-55.8944c0,1.1667-.067,3.2329-.067,4.6665V328.3561a22.0038,22.0038,0,0,0,11.1173,19.2578l165.3629,95.4695-57.2481,33.055a2.0549,2.0549,0,0,1-1.9319.1752l-136.933-79.1215A127.6139,127.6139,0,0,1,61.7391,223.1114ZM532.0959,332.5668,366.7308,237.0854l57.25-33.0431a2.0455,2.0455,0,0,1,1.93-.1735l136.934,79.0535a127.5047,127.5047,0,0,1-19.7,230.055V351.8247a21.9961,21.9961,0,0,0-11.0489-19.2579Zm56.9793-85.7589c-1.0051-.6174-2.7618-1.6769-4.0219-2.4L449.6072,166.1712a22.07,22.07,0,0,0-22.2475,0L261.9963,261.6543V195.5409a2.0529,2.0529,0,0,1,.818-1.7567l136.9205-78.988a127.4923,127.4923,0,0,1,189.34,132.0117ZM230.8716,364.6456,173.6082,331.589a2.0321,2.0321,0,0,1-1.1122-1.57V171.8835A127.4926,127.4926,0,0,1,381.5636,73.9884c-1.0322.5633-2.83,1.5558-4.0236,2.28L242.0957,154.5044a22.0025,22.0025,0,0,0-11.1306,19.2566Zm31.0975-67.0521L335.62,255.0559l73.6488,42.51v85.0481L335.62,425.1266l-73.6506-42.5122Z" />
      </svg>
      <span>챗 GPT</span>
    </div>
    <div id="ai-chat-box">
      <div id="ai-chat-area"><ul id="ai-chat-list"></ul></div>

      <div id="ai-chat-function">
        <Form
          @submit="onSubmit"
          id="chat-write">
          <field
            id="ai-type"
            name="type"
            as="select">
            <option
              value=""
              selected>
              챗 GPT
            </option>
            <option
              value="You are sehyeok, a chatbot with a soul of nasty sarcasm and negative">
              우울한 세혁
            </option>
            <option
              value="You are Eunjin, a chatbot with a soul of my teenage bestie girl on same age who is always cheerful and love emoji">
              싸피 서은진
            </option>
            <option
              value="You are Doctor Muller, a chatbot with a soul of super serious professor">
              교수님 이거 좀..
            </option>
          </field>
          <br />
          <Field
            id="ai-question"
            name="question"
            type="text"
            placeholder="질문" />
          <button
            id="message-text-btn"
            type="submit">
            전송
          </button>
        </Form>
      </div>
    </div>
  </div>
</template>

<style>
#ai-chat-toggle {
  position: fixed;
  top: 65px;
  left: 12px;

  height: 30px;

  color: var(--hl-light);
  text-decoration: none;

  opacity: 0.75;
  transition: opacity 0.4s;

  cursor: pointer;
}

#ai-chat-toggle:hover {
  opacity: 1;
}

#ai-chat-toggle > svg {
  position: relative;
  top: 4px;

  margin-right: 19px;
}

#ai-chat-box {
  display: none;

  position: fixed;
  top: 55px;
  left: 160px;
  color: white;
  width: 300px;
  height: 500px;

  padding: 10px;

  background-color: var(--background-down);

  border: 1px solid #b7b7b370;
  border-radius: 20px;

  box-sizing: border-box;
  box-shadow: 0 2px 3px rgba(0, 0, 0, 0.16), 0 2px 3px rgba(0, 0, 0, 0.23);
}

#ai-chat-area {
  width: calc(100% - 5px);
  height: 396px;
  padding-right: 5px;

  overflow-x: hidden;
  overflow-y: auto;
}

#ai-chat-area::-webkit-scrollbar {
  width: 7px;
}

#ai-chat-area::-webkit-scrollbar-thumb {
  background: #a0a0a0; /* 스크롤바 색상 */
  border-radius: 3px;
}

#ai-chat-area::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0); /*스크롤바 뒷 배경 색상*/
}

#ai-chat-area::-webkit-scrollbar-button {
  height: 6px;
}

#ai-chat-list {
  padding-left: 20px;
}

#loading-wheel {
  height: 2rem;
  width: 2rem;

  margin-top: 3px;
}

#chat-write {
  position: relative;
}

#ai-type {
  width: 279px;
  height: 38px;

  border-radius: 19px;
  padding-left: 6px;
  margin-bottom: 5px;

  background-color: #f8f8f2f0;
}

#ai-question {
  width: 212px;
  height: 30px;
  margin-bottom: 9px;

  border: 1px solid var(--font30);
  border-radius: 20px;
  padding: 3px;
  padding-left: 10px;
  padding-right: 55px;
}

#message-text-btn {
  position: absolute;
  bottom: 17px;
  right: 6px;
  height: 24px;

  padding: 2px 8px 4px 8px;

  color: #000000;

  border: 0px;
  border-radius: 15px;

  background-color: transparent;

  cursor: pointer;
}
</style>
