<template>
  <div id="chatContainer">
    <div id="chatWindow">
      <ul id="chatHistory">
        <li v-for="(message, index) in messages" :key="index">
            <img :src="imagePath" :alt="message.username[1] + '의 프로필'">
            <!-- <strong>{{ message.username[0] }}:</strong> {{ message.message }} -->
            <strong>{{ message.type === 'forced_exit' ? message.username[0] : '시스템' }}:</strong> {{ message.message }}
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
  import { ref, onMounted, onBeforeUnmount, onUnmounted } from 'vue'
  import { usewebRtcStore } from "@/stores"

  const webrtcstore = usewebRtcStore()

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
    },
    chatUserNo:{
      type: Number,
      required: false,
    }
  });

  const { messages, session, myUserName, chatUserNo, } = props;
  // const userNo = ref(usewebRtcStore.userNo)
  const inputMessage = ref("");

  const profileImagePath = ref(webrtcstore.profileImagePath)

  function sendMessage() {
    if (inputMessage.value.trim()) {
      // 다른 참가원에게 메시지 전송
      session.signal({
        // data: JSON.stringify({username: session.myUserName, message: inputMessage.value}), // 메시지 데이터를 문자열로 변환해서 전송
        // data: JSON.stringify({username: myUserName, message: inputMessage.value}), // 메시지 데이터를 문자열로 변환해서 전송
        // userNo는 받는 곳에서 못받음.
        // data: JSON.stringify({username: myUserName, message: inputMessage.value + '!!!' + chatUserNo}), // 메시지 데이터를 문자열로 변환해서 전송
        data: JSON.stringify({username: [myUserName, chatUserNo], message: inputMessage.value}), // 메시지 데이터를 문자열로 변환해서 전송
        type: 'chat', // 신호 타입을 'chat'으로 설정
      });
      inputMessage.value = '';
    }
  }
  // function sendMessageToChat(message) {
  function sendMessageToChat(message,type='chat') { // type을 추가해줬음.
    session.signal({
      // data: JSON.stringify({ username: [myUserName, chatUserNo], message }), // 메시지 데이터를 문자열로 변환해서 전송
      data: JSON.stringify({ username: ['시스템', chatUserNo], message, type }), // 메시지 데이터를 문자열로 변환해서 전송
      type: 'chat', // 신호 타입을 'chat'으로 설정
    });
  }

  onMounted(() => {
    setTimeout(() => {
      // 컴포넌트가 마운트된 후에 실행되는 코드
      if (!webrtcstore.isWelcome && (chatUserNo && myUserName)) {
        // const joinMessage = `${myUserName} 님이 참여했습니다.\n방 규칙을 잘 지켜주세요.\n${webrtcstore.rule}`;
        const joinMessage = `${myUserName} 님이 참여했습니다. 방 규칙을 잘 지켜주세요.`;
        sendMessageToChat(joinMessage)
        updateisWelcome(true)              // 참여 메세지를 보냈음을 보여줌
      }  
    }, 400)
  })
  // onBeforeUnmount(() => {  // 오작동함.
  //   // 컴포넌트가 마운트된 후에 실행되는 코드
  //   // if (chatUserNo && myUserName) {
  //   //   const exitmessage = `${myUserName} 님이 퇴장했습니다.`;
  //   //   sendMessageToChat(exitmessage)
  //   // }
  //   const exitmessage = `${myUserName} 님이 퇴장했습니다.`;
  //   sendMessageToChat(exitmessage)
  // })
</script>
