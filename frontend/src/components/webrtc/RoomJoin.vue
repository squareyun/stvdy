<script setup>
  import { ref, computed, onBeforeMount, watch, onMounted, onBeforeUnmount, } from 'vue'
  import axios from 'axios'
  import { OpenVidu } from "openvidu-browser";
  import UserVideo from "@/components/webrtc/UserVideo.vue";
  import { useRouter } from "vue-router"
  import { usewebRtcStore } from "@/stores"

  const store = usewebRtcStore()
  const router = useRouter()

  ///////////////////////////////////////////
  // 새로고침 방지!
  document.addEventListener('keydown', preventRefresh)
  function preventRefresh(event) {
    const key_f5 = 116; // 116 = F5
    const key_r = 82; // 82 = R
    if ((key_f5 === event.keyCode) || (event.ctrlKey && key_r === event.keyCode)) {
      event.preventDefault();
      // alert('이 페이지는 F5키와 Ctrl + R로 새로 고침되지 않습니다.');
      alert('이 페이지는 새로 고침되지 않습니다.');
      return false;
    }
  }
  ///////////////////////////////////////////



  axios.defaults.headers.post["Content-Type"] = "application/json;charset=utf-8";
  // 추후 배포와 관련해서 이부분에 대해서 설정을 할 필요가 있게 될것.
  // const APPLICATION_SERVER_URL = process.env.NODE_ENV === 'production' ? '' : 'http://localhost:5000/';
  const APPLICATION_SERVER_URL = process.env.NODE_ENV === 'production' ? '' : 'http://localhost:8080/';
  // const APPLICATION_SERVER_URL = process.env.NODE_ENV === 'production' ? '' : 'http://54.180.9.43:8080/';

  // OpenVidu objects
  const OV = ref(undefined)
  const session = ref(undefined)
  // const mainStreamManager = ref(undefined)
  let mainStreamManager = ref(undefined)
  const publisher = ref(undefined)
  const subscribers = ref([]) //

  // Join form
  const mySessionId = ref(decodeURIComponent(store.mySessionId))  // 인코딩값을 디코딩한 해줘서 받아야만 작동가능함
  // const mySessionId = ref(store.mySessionId)
  // 만약 인코딩해서 받은 값이 아니라면, 디코딩하지 않은 상태로 받기 위함.
  if (store.mySessionId === encodeURIComponent(store.mySessionId)) {
    mySessionId.value = store.mySessionId
  }
  const myUserName = ref(store.myUserName)

  const roomId = ref(store.roomId)
  
  // 방에서 최대 인원수
  const quota = ref(store.quota)

  /////////////////////채팅창을 위한 부분임.
  const inputMessage = ref("")
  const messages = ref([])
  // const isChatContainer = ref(true)   /// 채팅창!!!!!
  ///////////////////
  ///////////////////카메라 및 오디오 설정을 위한 부분임
  const muted = ref(false)       // 기본은 음소거 비활성화
  const camerOff = ref(false)    // 기본 카메라 활성화
  const selectedCamera = ref("")  // 카메라 변경시 사용할 변수 
  const selectedAudio  = ref("")  // 오디오 변경시 사용할 변수
  ////다시그려내기 위해 computed 작성
  const mainStreamManagerComputed = computed(() => mainStreamManager.value);
  const publisherComputed = computed(() => publisher.value);
  const subscribersComputed = computed(() => subscribers.value);
  ////
  ///////////////////
  // 방 탈퇴를 위한 변수들
  // const peopleNo = ref(1)
  const peopleNo = ref(store.peopleNo)
  const peopleNoComputed = computed(() => (peopleNo.value));
  ///////////////////


  onBeforeMount(() => {
    window.addEventListener("beforeunload",leaveSession)
    // mySessionId가 없으면 잘못된 접근이므로 
    if(mySessionId.value !== null && mySessionId.value !== undefined && mySessionId.value !== 'null'){
      joinSession()
      console.log('people 넘버2',peopleNo.value)
    }
    else{
      alert('잘못된 접근으로 방에서 나갑니다.')
      leaveSession()
    }
  })

  //// 페이지 벗어나면 
  onBeforeUnmount(()=>{
    if(peopleNoComputed.value == 1){  // 사람 수가 1이면 결국 혼자인거니까.
      // store.shutDownRoom(roomId.value)
      shutDownRoom(roomId.value)
    }
    else{
      leaveSession()
      store.roomExit(roomId.value)  // 방나가면 방나갔음을 백엔드로 전송.
      console.log('roomExit 되는건가')
    }
    /////////////////////////////////////////////
    // 방을 나가는데 사람이 없으면 방을 폐쇄할 거임.
    // peopleNo.value 를 subscriber를 이용하게된다면 너무나 위험하군....
    /////////////////////////////////////////////
  })

  // vue2에서의 methods 부분을 vue3화 handleUnload
  function joinSession() {
    // --- 1) Openvdiu object 가져오기
    OV.value = new OpenVidu()
    
    // --- 2) session 값 집어넣기
    session.value = OV.value.initSession()
    console.log('이게뭐람',session.value)

    // --- 3) 세션에서 이벤트가 발생할때 수행할 작업 지정
    // new Stream 을 수신할 때마다 수행...
    session.value.on("streamCreated", ( {stream} )=> {
      // const subscriber = session.subscribe(stream)
      const subscriber = session.value.subscribe(stream)
      subscribers.value.push(subscriber)
      console.log('이게바로 섭스크라이버들',subscribers.value)
      console.log('이게바로 섭스크라이버들',subscribersComputed.value)
      console.log('people 넘버1',peopleNo.value)
    })

    // 모든 스트림 파괴...
    session.value.on("streamDestroyed", ( {stream} ) => {
      const index = subscribers.value.indexOf(stream.streamManager, 0)
      if(index >= 0){
        subscribers.value.splice(index, 1)
      }
    })

    // 모두 asynchronous exception 처리 할거임...
    session.value.on("exception", ({ exception }) => {
      console.warn(exception);
    });

    // 채팅 이벤트 수신 처리 함. session.on이 addEventListenr 역할인듯.
    session.value.on('signal:chat', (event) => { // event.from.connectionId === session.value.connection.connectionId 이건 나와 보낸이가 같으면임
      const messageData = JSON.parse(event.data);
      if(event.from.connectionId === session.value.connection.connectionId){
        messageData['username'] = '나'
      }
      messages.value.push(messageData);
    });

    // createToken(mySessionId.value).then((token) => {
    createToken(mySessionId.value, roomId.value).then((token) => {
      console.log('createToken들어옴')
      console.log('토큰임',token)
      console.log(myUserName.value)
      
      console.log('여긴가1')
      session.value.connect(token, {clientData: myUserName.value})
      .then(() => {
        console.log('방생성 완료된거나 다름업슴!')
          let publisherTmp = OV.value.initPublisher(undefined, {
            audioSource: undefined, // The source of audio. If undefined default microphone
            videoSource: undefined, // The source of video. If undefined default webcam
            publishAudio: !muted.value, // Whether you want to start publishing with your audio unmuted or not
            publishVideo: !camerOff.value, // Whether you want to start publishing with your video enabled or not
            // resolution: "640x480", // The resolution of your video
            resolution: "160x120", // The resolution of your video
            frameRate: 30, // The frame rate of your video
            insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
            mirror: false, // Whether to mirror your local video or not
          });
          mainStreamManager.value = publisherTmp
          publisher.value = publisherTmp

          session.value.publish(publisher.value)
          getMedia()  // 세션이 만들어졌을때 미디어 불러옴
          console.log('방생성 완료!!!')
        })
        .catch((error) => {
          console.log("session 커넥팅에 문제생김(createToken):", error.code, error.message);
        })
    })
    // window.addEventListener("beforeunload",leaveSession);
  }

  function leaveSession(){

    // const confirmLeave = confirm("이 페이지를 떠나시겠습니까? 회의가 종료됩니다.")
    // console.log('나갈거야!!!!!',confirmLeave)
    // if(session.value && confirmLeave) session.value.disconnect()
    if(session.value) session.value.disconnect()
    
    // Empty all properties...
    session.value = undefined;
    mainStreamManager.value = undefined;
    publisher.value = undefined;
    subscribers.value = [];
    OV.value = undefined;

    // Remove beforeunload listener
    window.removeEventListener("beforeunload", leaveSession)
    document.removeEventListener('keydown', preventRefresh)

    // 메인페이지로 넘어감
    router.push({
      // name:'roomAdd',// 임시로 roomAdd로 보냄.
      name:'main',// 임시 이름 main으로 넘겨줌.
      // params: { 
      //   roomName: mySessionId.value,
      // },
    })
  }

  function updateMainVideoStreamManager(stream) {
    if (mainStreamManager.value === stream) return
    mainStreamManager.value = stream
  }

  //// 방 참여에 있어 방을 생성해주고, 방에 참석할 수 있게 해주는 것.
  /**
  * --------------------------------------------
  * GETTING A TOKEN FROM YOUR APPLICATION SERVER
  * --------------------------------------------
  */
   //// Edited code with Beom's code
  async function createToken(mySessionId, roomId) {
    /////////////////////////////////////////////////////
    // 방 참가시에 관련한 것
    const roomNo = roomId  // join하면서 
    const userNo = store.userNo // 임시로 랜덤 값으로 보내는 중
    const inputPassword = store.inputPassword
    ////////////////////////////////////////////////////
    // 방 생성시에 관련한 것
    // const userNo = store.userNo
    const endHour = store.endHour
    const endMinute = store.endMinute
    const quota = store.quota
    const isPrivacy = store.isPrivacy
    const password = store.password
    const backImgFile = store.backImgFile
    const rule = store.rule
    const roomKeywords = store.roomKeywords
    ////////////////////////////////////////////////////
    try{
      console.log(roomNo, userNo, inputPassword)
      // const response = await axios.post(APPLICATION_SERVER_URL + 'rooms/' + roomNo, {userNo: userNo, password: inputPassword}, {
      const response = await axios.post(APPLICATION_SERVER_URL + 'rooms/' + roomNo, {userNo: userNo, password: inputPassword}, {
      headers: { 'Content-Type': 'application/json', },
      });
      /////////////////////////////////
      // 유저 누구누구 있는지 받아와야함.
      ////////////////////////////////
      console.log('조인룸 내부 해치웠나?1')
      console.log('joinRoom 리스폰스데이터 잘 받음',response.data)
      return response.data
    }
    catch(error){
      console.error('만들어진 방이없어서 발생한 에러:', error);
      try{
        console.log('방만들거임')
        const response = await axios.post(APPLICATION_SERVER_URL + 'rooms/add', 
        {userNo: userNo, title: mySessionId, endHour: endHour, endMinute: endMinute, quota: quota, 
          isPrivacy: isPrivacy, password: password, iamgePath: backImgFile, rule: rule, roomKeywords: roomKeywords}, 
          {headers: { 'Content-Type': 'application/json', },
        });
        console.log('create할때',userNo, mySessionId, endHour, endMinute, quota, isPrivacy, password, backImgFile, rule)
        console.log('크리에이트룸 내부 해치웠나?1')
        console.log('이것이 만든 방의 리스폰스데이터 \n', response.data)
        console.log('이것이 만든 방의 리스폰스데이터 \n', response.data.token)
        /////////////////////////////////////////////////
        // 방 ID 받아와야함. 그리고 그걸로 store및 여기 roomId.value에 저장할 것.
        /////////////////////////////////////////////////
        //response.data.room
        console.log('방 번호 조정중1', response.data.room.id)
        updateRoomId(response.data.room.id)
        console.log('방 번호 조정중2')
        return response.data.token;
      }
      catch(error){
        console.error("방생성에도 오류 났음.",error);
      }
    }
  }


  function updateRoomId(newroomId){
    store.updateRoomId(newroomId)
    roomId.value = store.roomId
  }
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

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////
  ///////기타 기능
  // 캠, 오디오 등 기기와 관련된 함수
  // 카메라와 오디오를 가져옴.
  async function getMedia() {
    try {
      const devices = await navigator.mediaDevices.enumerateDevices();
      const cameras = devices.filter((device) => device.kind === 'videoinput');
      console.log('카메라들',cameras)
      const audios = devices.filter((device) => device.kind === 'audioinput');
      console.log('마이크들',audios)
      // const audios = undefined

      const cameraSelect = document.querySelector('select[name="cameras"]');
      const audioSelect = document.querySelector('select[name="audios"]');
      
      // 카메라 및 오디오 선택기 요소가 존재하는지 확인
      // if (cameraSelect && audioSelect) {
      if (cameras) {
        cameras.forEach((camera) => {
          const option = document.createElement('option');
          option.value = camera.deviceId;
          option.text = camera.label;
          cameraSelect.appendChild(option);
        });
      } else {
        const notCamera = cameraSelect.querySelector('option:disabled');
        notCamera.innerText = '사용 가능한 카메라가 없습니다.'
        // console.error('Camera selector not found');
      }
      if(audios){
        audios.forEach((audio) => {
          const option = document.createElement('option');
          option.value = audio.deviceId;
          option.text = audio.label;
          audioSelect.appendChild(option);
        });
      } else {
        const notAudio = audioSelect.querySelector('option:disabled');
        notAudio.innerText = '사용 가능한 마이크가 없습니다.'
        // console.error('Audio selector not found');
      }
    } catch (error) {
      console.error('Error getting media devices:', error);
    }
  }

  // 음소거, 캠 활성화 버튼 작동
  function handleCameraBtn() {
    if (!publisher.value) return;
    // 카메라 상태 토글
    camerOff.value = !camerOff.value;
    const cameraActivate = document.getElementById('camera-activate')
    if(camerOff.value){   //카메라 비활성화상태
      cameraActivate.innerText = '카메라 활성화'
    }else{                //카메라 활성화상태
      cameraActivate.innerText = '카메라 비활성화'
    }
    
    // 카메라 작동 상태를 적용
    publisher.value.publishVideo(!camerOff.value);
  }

  function handleMuteBtn() {
    if (!publisher.value) return;

    // 음소거 상태 토글
    muted.value = !muted.value;
    const muteActivate = document.getElementById('mute-activate')
    if(muted.value){   //음소거 활성화상태
      muteActivate.innerText = '음소거 비활성화'
    }else{                //음소거 비활성화상태
      muteActivate.innerText = '음소거 활성화'
    }
    // 음소거 설정을 적용
    publisher.value.publishAudio(!muted.value);
  }
  
  // select태그에서 사용할 기기를 선택했을때
  async function handleCameraChange(event) {
    selectedCamera.value = event.target.value;
    await replaceCameraTrack(selectedCamera.value);
  }

  async function handleAudioChange(event) {
    selectedAudio.value = event.target.value;
    await replaceAudioTrack(selectedAudio.value);
  }

  async function replaceCameraTrack(deviceId) {
    if (!publisher.value) return;

    const newConstraints = {
        audio: false,
        video: {
            deviceId: { exact: deviceId },
        },
    };
    // 카메라 비활성화 조건을 추가합니다.
    if (camerOff.value) {
      newConstraints.video = false;
    }
    try {
      const newStream = await navigator.mediaDevices.getUserMedia(newConstraints);
      const newVideoTrack = newStream.getVideoTracks()[0];
      await publisher.value.replaceTrack(newVideoTrack);
    // mainStreamManager를 업데이트하여 선택한 카메라를 메인 비디오로 설정
      mainStreamManager.value = publisher.value
    } catch (error) {
        console.error("Error replacing video track:", error);

    }
  }

  async function replaceAudioTrack(deviceId) {
    if (!publisher.value) return;

    const newConstraints = {
        audio: {
            deviceId: { exact: deviceId },
        },
        video: false,
    };

    try {
        const newStream = await navigator.mediaDevices.getUserMedia(newConstraints);
        const newAudioTrack = newStream.getAudioTracks()[0];
        await publisher.value.replaceTrack(newAudioTrack);
    } catch (error) {
        console.error("Error replacing audio track:", error);
    }
  }


  /////////////////////////
  // 탭 메뉴 관련
  const funcTabs = ref(['참여멤버', '메시지', '그라운드 룰', '공유'])
  const activeFuncTab = ref(0)
  const roomRule = ref(store.rule)

  function changeTab(index) {
    activeFuncTab.value = index
    console.log(myUserName.value)
  }

  /////////////////////////
  async function shutDownRoom(roomId) {
    // store.shutDownRoom(roomId)
    try{
      // const confirmLeave = confirm("이 페이지를 떠나시겠습니까? 회의가 종료됩니다.")
      // console.log('나갈거야!!!!!',confirmLeave)
      // if(session.value && confirmLeave) session.value.disconnect()
      if(session.value) session.value.disconnect()
      
      // Empty all properties...
      session.value = undefined;
      mainStreamManager.value = undefined;
      publisher.value = undefined;
      subscribers.value = [];
      OV.value = undefined;

      // Remove beforeunload listener
      window.removeEventListener("beforeunload", leaveSession)
      document.removeEventListener('keydown', preventRefresh)
      // 메인페이지로 넘어감
      router.push({
        // name:'roomAdd',// 임시로 roomAdd로 보냄.
        name:'main',// 임시 이름 main으로 넘겨줌.
        // params: { 
        //   roomName: mySessionId.value,
        // },
      })
      store.roomExit(roomId.value)  // 방나가면 방나갔음을 백엔드로 전송.
      const response = await axios.delete(`http://localhost:8080/rooms/${roomId}`)
      console.log(response.data)
      console.log('방이 성공적으로 제거되었습니다.')
    }
    catch(error){
      console.error('방을 제거하지 못했습니다.',error.code, error.message)
    }
  }
  async function handleshutDownRoom(roomId) {
    const isShut = confirm("방 폐쇄 버튼을 눌렀습니다. 진심입니까? 휴먼??")
    if(isShut){
      alert('진심이군요 휴먼, 알겠습니다. 방을 폐쇄하도록하죠.')
      // store.shutDownRoom(roomId)
      shutDownRoom(roomId)
    }
    else{
      alert('거짓말을 하다니 그런짓은 하지마십시오. 휴먼.')
    }
  }

</script>

<!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  -->
<!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  -->

<template>
  <!-- session이 true일때! 즉, 방에 들어갔을 때 -->
  <!-- <div id="session" v-if="session" style="color: white;"> -->
  <div id="session" style="color: white;">
    <div>
      <div id="session-header">
        <h1 id="session-title">{{ mySessionId }}</h1>
        <input type="button" id="buttonLeaveSession" @click="leaveSession" value="Leave session" />
      </div>
      <!-- 화상이 보이는 곳 -->
      <div id="camScreen"> 
        
        <!-- 모든 캠 -->
        <div id="videoContainer">
          <!-- <UserVideo :stream-manager="publisherComputed" @click.native="updateMainVideoStreamManager(publisher)" :style="{width: '10%', height: '5%'}"/> -->
          <UserVideo :stream-manager="publisherComputed" @click.native="updateMainVideoStreamManager(publisher)" />
          <UserVideo v-for="sub in subscribersComputed" :key="sub.stream.connection.connectionId" :stream-manager="sub"
            @click.native="updateMainVideoStreamManager(sub)" />
          <!-- 이거는 빈 자리 검정박스 --><!--  --><!--  -->
          <!-- <div v-for="(number, i) in ( quota  - subscribersComputed.length -1)" :key="i" class="blackbox"></div> -->
          <!-- <div id="emptyBox"></div> -->
        </div>
        <!-- 선택 캠 -->
        <div id="mainVideo">
          <UserVideo :stream-manager="mainStreamManagerComputed" />
        </div>
      </div>
      <!-- 캠 및 오디오 관련 -->
      <div id="deviceDiv">
        <!-- 캠활성화, 음소거 버튼 -->
        <button id="camera-activate" @click="handleCameraBtn">캠 비활성화</button>
        <button id="mute-activate" @click="handleMuteBtn">음소거 활성화</button>
        <!-- 캠,오디오 선택 옵션 -->
        <div>
          <select name="cameras" @change="handleCameraChange">
            <option disabled>사용할 카메라를 선택하세요</option>
          </select>
          <select name="audios" @change="handleAudioChange">
            <option disabled>사용할 마이크를 선택하세요</option>
          </select>
        </div>
      </div>
      <!-- 방 종료 버튼 -->
      <div id=''>
        <button @click="handleshutDownRoom(roomId)">방 폐쇄하기</button>
      </div>
    </div>
  </div>
  <!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  -->
  <!-- 화상회 부가기능이 보이는 곳 -->
  <div id="functionTab" style="color: white;">
    <div id="tabs">
      <ul id="functionUl" style="display: flex;">
        <li v-for="(tab, index) in funcTabs" :key="index" id="tab{{index}}" :class="{ 'active': activeFuncTab === index }" @click="changeTab(index)">
          {{ tab }}
        </li>
      </ul>
      <div v-if="activeFuncTab === 0">참여멤버</div>
      <div v-if="activeFuncTab === 1">메시지</div>
      <div v-if="activeFuncTab === 2">그라운드 룰</div>
      <div v-if="activeFuncTab === 3">공유</div>
    </div>
    <!-- 참여 멤버 -->
    <div v-if="activeFuncTab === 0">
      <ul>
        <li>{{ myUserName }}</li>
        <li v-for="(sub, index) in subscribersComputed" :key="index">
          {{ JSON.parse(sub.stream.connection.data).clientData }}
        </li>
      </ul>
    </div>
    <!-- 나중에 <chat-winow />로 넘길수 있도록 해보자. -->
    <div id="chatContainer" v-if="activeFuncTab === 1">
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
    <!-- 그라운드 룰 -->
    <div v-if="activeFuncTab === 2">
      {{ roomRule }}
    </div>
    <!-- 공유 -->
    <div v-if="activeFuncTab === 3"></div>
  </div>
    
</template>

<style scoped>
  .blackbox{
    border : 1px solid;
    background-color: crimson;
    width : 160px;
    height: 120px;
  }
  #session {
    display: flex;
    width: 100%;
    height: 100%;
  }

  #camScreen {
    /* display: flex;
    flex-wrap: wrap; */
    height: 95%;
  }
  #videoContainer{
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    align-items: space-between;
  }

  #deviceDiv {
    width: 100%;
    height: 5%;
  }
  #functionUl {
    cursor: pointer;
    display: inline-block;
  }
  li.active {
    font-weight: bold;
    color: blue;
  }
</style>