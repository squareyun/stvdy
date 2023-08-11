<template>
  <div id="chatContainer">
    <div id="chatWindow">
      <ul id="chatHistory">
        <li v-for="(message, index) in messages" :key="index">
          <strong>{{ message.username }}:</strong> {{ message.message }}
        </li>
      </ul>
    </div>
    <form id="chat-write">
      <input type="text" placeholder="전달할 내용을 입력하세요." v-model="inputMessage">
      <button @click.prevent="sendMessage">전송</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { usewebRtcStore } from "@/stores"


const props = defineProps({
  messages: {
    type: Array,
    required: true,
  },
  session: {
    type: Object,
    required: true,
  },
  myUserName:{
    type: String,
    required: true,
  }
});

const { messages, session, myUserName } = props;
const userNo = ref(usewebRtcStore.userNo)
const inputMessage = ref("");

function sendMessage() {
  if (inputMessage.value.trim()) {
    // 다른 참가원에게 메시지 전송
    session.signal({
      // data: JSON.stringify({username: session.myUserName, message: inputMessage.value}), // 메시지 데이터를 문자열로 변환해서 전송
      // data: JSON.stringify({username: myUserName, message: inputMessage.value}), // 메시지 데이터를 문자열로 변환해서 전송
      // userNo는 받는 곳에서 못받음.
      data: JSON.stringify({username: myUserName, message: inputMessage.value, userNo:userNo.value}), // 메시지 데이터를 문자열로 변환해서 전송
      type: 'chat', // 신호 타입을 'chat'으로 설정
    });
    inputMessage.value = '';
  }
}
</script>
