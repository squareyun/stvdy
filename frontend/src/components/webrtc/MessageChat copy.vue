<script setup>

  // 채팅창 구현을 위한 함수 제작
  ///////////////////////////
  function sendMessage(event) {
    event.preventDefault();
    if(inputMessage.value.trim()){
      // messages.value.push({username : myUserName.value, message : inputMessage.value})
      // 다른 참가원에게 메시지 전송하기
      session.value.signal({
        data: JSON.stringify({username: myUserName.value, message: inputMessage.value}), // 메시지 데이터를 문자열로 변환해서 전송
        type: 'chat' // 신호 타입을 'chat'으로 설정
      });
      inputMessage.value = '';
    }
  }
</script>

<template>
  <div v-if="activeFuncTab === 1">
    <div id="chatWindow">
      <ul id="chatHistory">
        <li v-for="(message, index) in messages" :key="index">
          <strong>{{ message.username }}:</strong> {{ message.message }}
        </li>
      </ul>
    </div>
    <form id="chat-write">
      <input type="text" placeholder="전달할 내용을 입력하세요." v-model="inputMessage">
      <button @click="sendMessage">전송</button>
    </form>
  </div>
</template>

<style scoped>

</style>