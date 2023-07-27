<template>
  <div id="main-container">
    <!-- session이 false일때! 즉, 방에 들어가지 않았을때 -->
    <div id="join" v-if="!session">
      <div id="img-div">
        <!-- <img src="resources/images/openvidu_grey_bg_transp_cropped.png" alt="오픈비두 로고 이미지임"> --><!-- 이미지부분 -->
      </div>
      <div id="join-dialog">
        <h1>Join a video session</h1>
        <div>
          <p>
            <label>Participant</label>
            <input v-model="myUserName" type="text" required />
          </p>
          <p>
            <label>Session</label>
            <input v-model="mySessionId" type="text" required />
          </p>
          <p>
            <button @click="joinSession">
              Join!
            </button>
          </p>
        </div>
      </div>
    </div>
    <!-- session이 true일때! 즉, 방에 들어갔을 때 -->
    <div id="session" v-if="session">
      <div id="session-header">
        <h1 id="session-title">{{ mySessionId }}</h1>
        <input
          type="button"
          id="buttonLeaveSession"
          @click="leaveSession"
          value="Leave session"
        />
      </div>
      <div id="main-video">
        <user-video :stream-manager="mainStreamManager" />
      </div>
      <div id="video-container">
        <user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)" />
        <user-video
          v-for="sub in subscribers"
          :key="sub.stream.connection.connectionId"
          :stream-manager="sub"
          @click.native="updateMainVideoStreamManager(sub)"
        />
      </div>
      <!-- 방에 들어갔을 때 같이 보이게 될 채팅창 -->
      <!-- 나중에 <chat-winow />로 넘길수 있도록 해보자. -->
      <div id="chat-container">
        <div id="chat-window">
          <ul id="chat-history">
            <li v-for="(message, index) in messages" :key="index">
              <strong>{{message.username}}:</strong> {{message.message}}
            </li>
          </ul>
        </div>
        <div id="chat-write">
          <input type="text" placeholder="전달할 내용을 입력하세요." v-model="message">
          <button @click="sendMessage">전송</button>
        </div>
      </div>
    </div>
  </div>
</template>
    
  
<script setup>
  import { ref, computed, onMounted, onUnmounted } from 'vue'
  import axios from 'axios'
  import { OpenVidu } from "openvidu-browser";
  import UserVideo from "@/components/UserVideo.vue";

  axios.defaults.headers.post["Content-Type"] = "application/json";

  const APPLICATION_SERVER_URL = process.env.NODE_ENV === 'production' ? '' : 'http://localhost:5000/';

  // OpenVidu objects
  const OV = ref(undefined)
  const session = ref(undefined)
  // const mainStreamManager = ref(undefined)
  let mainStreamManager = ref(undefined)
  const publisher = ref(undefined)
  const subscribers = ref([])

  // Join form
  const mySessionId = ref("SessionA")
  const myUserName = ref("Participant" + Math.floor(Math.random() * 100))
  
  //채팅창을 위한 부분임. ///////////////////
  const inputMessage = ref("")
  const messages = ref([])
  ///////////////////


  // vue2에서의 methods 부분을 vue3화 시키기
  function joinSession() {
    // --- 1) Get an OpenVidu object ---
    OV.value = new OpenVidu()
    
    // --- 2) Init a session ---
    session.value = OV.value.initSession()

    // --- 3) Specify the actions when events take place in the session ---
    // On every new Stream received...
    session.value.on("streamCreated", ( {stream} )=> {
      // const subscriber = session.subscribe(stream)
      const subscriber = session.value.subscribe(stream)
      subscribers.value.push(subscriber)
    })

    // On every Stream destroyed...
    session.value.on("streamDestroyed", ( {stream} ) => {
      const index = subscribers.value.indexOf(stream.streamManager, 0)
      if(index >= 0){
        subscribers.value.splice(index, 1)
      }
    })

    // On every asynchronous exception...
    session.value.on("exception", ({ exception }) => {
      console.warn(exception);
    });

    // --- 4) Connect to the session with a valid user token ---
    // Get a token from the OpenVidu deployment
    console.log('이건 getToken 들어가기 전')
    console.log('이건 세션', session)
    console.log('이건 세션밸류', session.value)
    console.log('내 세션아이디',mySessionId.value)
    // getToken(mySessionId).then((token) => {
    getToken(mySessionId.value).then((token) => {
      console.log('이건 getToken 들어간 후')
      console.log('이건토큰',token)
      // First param is the token. Second param can be retrieved by every user on event
      // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
      // session.value.connect(token, {clientData: myUserName})
      session.value.connect(token, {clientData: myUserName.value})
      .then(() => {
          // --- 5) Get your own camera stream with the desired properties ---

          // Init a publisher passing undefined as targetElement (we don't want OpenVidu to insert a video
          // element: we will manage it on our own) and with the desired properties
          let publisher_tmp = OV.value.initPublisher(undefined, {
            audioSource: undefined, // The source of audio. If undefined default microphone
            videoSource: undefined, // The source of video. If undefined default webcam
            publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
            publishVideo: true, // Whether you want to start publishing with your video enabled or not
            resolution: "640x480", // The resolution of your video
            frameRate: 30, // The frame rate of your video
            insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
            mirror: false, // Whether to mirror your local video or not
          });

          // Set the main video in the page to display our webcam and store our Publisher
          console.log('이것이바로 publisher_tmp',publisher_tmp)
          console.log('이것은 mainStreamManager',mainStreamManager.value)
          mainStreamManager.value = publisher_tmp
          publisher.value = publisher_tmp

          // --- 6) Publish your stream ---
          // session.publish(publisher)
          session.value.publish(publisher.value)
        })
        .catch((error) => {
          console.log("There was an error connecting to the session:", error.code, error.message);
        })
    })

    window.addEventListener("beforeunload", leaveSession)
  }

  function leaveSession(){
    // --- 7) Leave the session by calling 'disconnect' method over the Session object ---
    if(session.value) session.value.disconnect()
    
    // Empty all properties...
    session.value = undefined;
    mainStreamManager.value = undefined;
    publisher.value = undefined;
    subscribers.value = [];
    OV.value = undefined;

    // Remove beforeunload listener
    window.removeEventListener("beforeunload", leaveSession)

  }

  function updateMainVideoStreamManager(stream) {
    if (mainStreamManager.value === stream) return
    mainStreamManager.value = stream
  }

  /**
  * --------------------------------------------
  * GETTING A TOKEN FROM YOUR APPLICATION SERVER
  * --------------------------------------------
  */
  async function getToken(mySessionId) {
    console.log('이건 getToken 함수 들어옴 mySessionId', mySessionId)
    const sessionId = await createSession(mySessionId);
    console.log('createSession 함수 빠져나옴', sessionId)
    return await createToken(sessionId);
  }

  async function createSession(sessionId) {
    console.log('이건 createSession 함수 들어옴',APPLICATION_SERVER_URL+" / "+sessionId)
    const response = await axios.post(APPLICATION_SERVER_URL + 'api/sessions', { customSessionId: sessionId }, {
      headers: { 'Content-Type': 'application/json', },
    }, { userNo: 53}, {endHour: 1}, {endMinute: 30}, {quota: 16}, {isPrivacy: false});
    console.log('이건 createSession 함수 빠져나가는중 response', response)
    return response.data; // The sessionId
  }

  async function createToken(sessionId) {
    const response = await axios.post(APPLICATION_SERVER_URL + 'api/sessions/' + sessionId + '/connections', {}, {
      headers: { 'Content-Type': 'application/json', },
    });
    console.log('이건 createToken으로 생성된 response.data', response.data)
    return response.data; // The token
  }

  // 채팅창 구현을 위한 함수 제작
  ///////////////////////////
  function sendMessage() {
    if (inputMessage.value.trim()){
      session.value.signal({
        type:"chat",
        data: JSON.stringify({
          username: sessionOptions.value.user,
          message: inputMessage.value,
        }),
      });
      inputMessage.value = "";  // 메시지 전송 후 입력란 초기화
    }
  }

  const onMessageReceived = (event) => {
    const parsedMessage = JSON.parse(event.data);
    messages.value.push(parsedMessage);
  };

  onMounted(() => {
    session.value.on("signal:chat", onMessageReceived);
  });

  onUnmounted(() => {
    session.value.off("signal:chat", onMessageReceived);
  });

</script>