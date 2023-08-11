<script setup>
  import { ref, computed, onBeforeMount, watch, onMounted, onBeforeUnmount, } from 'vue'
  import axios from 'axios'
  import { OpenVidu } from "openvidu-browser";
  import UserVideo from "@/components/webrtc/UserVideo.vue";
  import MessageChat from "@/components/webrtc/MessageChat.vue";
  import { useRouter } from "vue-router"
  import { usewebRtcStore } from "@/stores"
  import { storeToRefs } from 'pinia';

  // const store = usewebRtcStore()
  const webrtcstore = usewebRtcStore()
  const router = useRouter()

  //////////// 새로고침 방지!
  document.addEventListener('keydown', preventRefresh)
  document.addEventListener('keyup', preventRefresh)
  document.addEventListener('keypress', preventRefresh)
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
  const mySessionId = ref(decodeURIComponent(webrtcstore.mySessionId))  // 인코딩값을 디코딩한 해줘서 받아야만 작동가능함
  // const mySessionId = ref(webrtcstore.mySessionId)
  // 만약 인코딩해서 받은 값이 아니라면, 디코딩하지 않은 상태로 받기 위함.
  if (webrtcstore.mySessionId === encodeURIComponent(webrtcstore.mySessionId)) {
    mySessionId.value = webrtcstore.mySessionId
  }
  const myUserName = ref(webrtcstore.myUserName)
  const roomId = ref(webrtcstore.roomId)
  // 방에서 최대 인원수
  const quota = ref(webrtcstore.quota)
  const isHost =  ref(webrtcstore.isHost)
  /////////////////////채팅창을 위한 부분임.
  const inputMessage = ref("")
  const messages = ref([])
  // const isChatContainer = ref(true)   /// 채팅창!!!!!
  ///////////////////카메라 및 오디오 설정을 위한 부분임
  const muted = ref(false)       // 기본은 음소거 비활성화
  const camerOff = ref(false)    // 기본 카메라 활성화
  const selectedCamera = ref("")  // 카메라 변경시 사용할 변수 
  const selectedAudio  = ref("")  // 오디오 변경시 사용할 변수
  ////다시그려내기 위해 computed 작성
  const mainStreamManagerComputed = computed(() => mainStreamManager.value);
  const publisherComputed = computed(() => publisher.value);
  const subscribersComputed = computed(() => subscribers.value);
  // 방 이탈를 위한 변수들
  const isExitRoom = ref(webrtcstore.isExitRoom)  // leave
  const isFull = ref(false)

  onBeforeMount(() => {
    // window.addEventListener("beforeunload",leaveSession)
    window.addEventListener("beforeunload",escapePage)
    // mySessionId가 없으면 잘못된 접근이므로 
    if(mySessionId.value !== null && mySessionId.value !== undefined && mySessionId.value !== 'null'){
      joinSession()
    }
    else{
      alert('잘못된 접근으로 방에서 나갑니다.')
      leaveSession()
    }
  })
  //// 페이지 벗어나면 
  onBeforeUnmount(()=>{
    if(!isExitRoom.value){  // leaveSession버튼을 누르지 않았을때만.
      // 방을 나가는데 사람이 없으면 방을 폐쇄할 거임.
      console.log('나감으로 방폐쇄1')
      if(!subscribersComputed.value.length){  // 다른 참여자가 없으면 실행
        console.log(`제발좀 되라${subscribersComputed.value.length}`)
        const localRoomId = localStorage.getItem('roomId')
        webrtcstore.shutDownRoom(localRoomId)
        // roomId.value = null                //방 폐쇄하고 브라우저의 roomId도 null값으로 만듬
        console.log('나감으로 방폐쇄2')
      }
      // leaveSession()
      alert('방에서 나갑니다.')
      escapePage()
    }
    // window.removeEventListener("beforeunload", leaveSession)
    window.removeEventListener("beforeunload", escapePage)
    document.removeEventListener('keydown', preventRefresh)
    document.removeEventListener('keyup', preventRefresh)
    document.removeEventListener('keypress', preventRefresh)
    webrtcstore.roomExit(roomId.value)  // 방나가면 방나갔음을 백엔드로 전송.
    isExitFalse()
  })
  onMounted(() => {
  })
  function isFullRoom(){
    console.log('!!!!!!방인원제한',quota.value)
    console.log('!!!!!!현재방인원',subscribersComputed.value)
    console.log('!!!!!!현재방 실제 인원수',subscribersComputed.value.length+1)
    if(quota.value < subscribersComputed.value.length+1){
      isFull.value = true
      alert('정원이 가득차서 입장할 수 없습니다. 메인 페이지로 돌아갑니다.')
      console.log('정원이 가득차서 입장할 수 없습니다. 메인 페이지로 돌아갑니다.')
      isExitRoom.value = true
      webrtcstore.isExitTrue()
      leaveSession()
      return
    }
  }

  function isExitTrue() {
    webrtcstore.isExitTrue()
    isExitRoom.value = true
  }
  function isExitFalse() {
    webrtcstore.isExitFalse()
    isExitRoom.value = false
  }

  function joinSession() {
    OV.value = new OpenVidu()
    session.value = OV.value.initSession()
    console.log('이게뭐람',session.value)
    session.value.on("streamCreated", ( {stream} )=> {
      const subscriber = session.value.subscribe(stream)
      subscribers.value.push(subscriber)
    })

    // 모든 스트림 파괴...
    session.value.on("streamDestroyed", ( {stream} ) => {
      const index = subscribers.value.indexOf(stream.streamManager, 0)
      if(index >= 0){
        console.log('스트림 파괴함!!!')
        subscribers.value.splice(index, 1)
      }
    })

    // session.value.on("streamDestroyed", ({ stream, reason }) => {
    //   if (reason === "forceDisconnectByServer") {
    //     console.log("Leaving Session (forced=true)");
    //     leaveSession();
    //   } else {
    //     const index = subscribers.value.indexOf(stream.streamManager, 0)
    //     if (index >= 0) {
    //       subscribers.value.splice(index, 1)
    //     }
    //   }
    // })

    // 새로 추가한 이벤트 리스너, 강제 종료되면 leaveSession()함.
    session.value.on("streamManagerRemoved", (event) => {
       if(event.forced) {
          console.log('얍! streamManagerRemoved')
          // leaveSession();
          router.push({
            // name:'maintmp',// 임시로 main으로 넘겨줌.
            name:'main',
          })
       }
    });

    session.value.on('sessionDisconnected', event => {
      // 이벤트 처리 코드
      console.log("세션에서 연결 끊어짐:", event);
      // 필요한 추가 작업을 수행합니다. 예: UI 업데이트, 사용자 알림 등
      router.push({
        // name:'maintmp',// 임시로 main으로 넘겨줌.
        name:'main',
      })
    });

    session.value.on("exception", ({ exception }) => {
      console.warn(exception);
    })

    // 채팅 이벤트 수신 처리 함. session.on이 addEventListenr 역할인듯.
    session.value.on('signal:chat', (event) => { // event.from.connectionId === session.value.connection.connectionId 이건 나와 보낸이가 같으면임
      const messageData = JSON.parse(event.data)
      if(event.from.connectionId === session.value.connection.connectionId){
        messageData['username'] = '나'
      }
      console.log(messageData)
      console.log(messageData.userNo)
      messages.value.push(messageData)
    })
    
    // createToken(mySessionId.value).then((token) => {
    createToken(mySessionId.value, roomId.value).then((token) => {
      roomId.value = webrtcstore.roomId
      if(roomId.value !== undefined && roomId.value !== null){
        // roomId가 변경되면 localstorage에 저장합니다.
        localStorage.setItem('roomId', roomId.value)  // 로컬스토리지에 roomId를 저장시켰으니 shutDown시킬때
      }      
      session.value.connect(token, {clientData: myUserName.value})
      .then(() => {
        console.log('방생성 및 입장완료나 다름없음')
          let publisherTmp = OV.value.initPublisher(undefined, {
            audioSource: undefined, // The source of audio. If undefined default microphone
            videoSource: undefined, // The source of video. If undefined default webcam
            publishAudio: !muted.value, // Whether you want to start publishing with your audio unmuted or not
            publishVideo: !camerOff.value, // Whether you want to start publishing with your video enabled or not
            resolution: "640x480", // The resolution of your video
            // resolution: "1280x960", // The resolution of your video
            frameRate: 30, // The frame rate of your video
            insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
            mirror: false, // Whether to mirror your local video or not
          });
          mainStreamManager.value = publisherTmp
          publisher.value = publisherTmp

          session.value.publish(publisher.value)
          getMedia()  // 세션이 만들어졌을때 미디어 불러옴
          console.log('방생성 완료!!!')
          webrtcstore.shareRoomAddress(localStorage.getItem('roomId'))  // 방 공유관련 정보 받음
        })
        .catch((error) => {
          console.log("session 커넥팅에 문제생김(createToken):", error.code, error.message);
          escapePage()
        })
    })
    console.log('이건제발되라1',subscribers.value)
    console.log('이건제발되라2', subscribers.value.length)
    isFullRoom()
    // window.addEventListener("beforeunload",leaveSession);
  }
  function handleLeaveSession(event){
    const isOut = confirm('방을 나가시겠습니까?')
    if(!isOut) return
    // 방을 나가는데 사람이 없으면 방을 폐쇄할 거임.
    console.log('핸들 나감으로 방폐쇄1')
    if(subscribersComputed.value.length == 0){  // 다른사람이 없으면 실행
      // webrtcstore.shutDownRoom(roomId.value)
      const localRoomId = localStorage.getItem('roomId')
      webrtcstore.shutDownRoom(localRoomId)
      console.log('핸들 나감으로 방폐쇄2')
    }
    leaveSession()
  }
  function leaveSession(){
    console.log('leaveSession() 함수 호출됨'); // 확인용 로그
    console.log('참여자',subscribersComputed.value)
    console.log('참여자수',subscribersComputed.value.length)
    if(session.value) session.value.disconnect()
    // Empty all properties...
    session.value = undefined;
    mainStreamManager.value = undefined;
    publisher.value = undefined;
    subscribers.value = [];
    OV.value = undefined;
    // 이벤트리스너 제거하기 beforeunload listener
    // window.removeEventListener("beforeunload", handleLeaveSession)
    // window.removeEventListener("beforeunload", leaveSession)
    // document.removeEventListener('keydown', preventRefresh)
    isExitTrue()
    // const localRoomId = localStorage.getItem('roomId')
    // openNewWindow2(`이건 리브세션버튼 누른거` + '섭스크라이브'+ subscribersComputed.value.length+'방id'+localRoomId)
    // 메인페이지로 넘어감
    router.push({
      // name:'maintmp',// 임시로 main으로 넘겨줌.
      name:'main',
    })
  }
  function escapePage() {
    console.log('escapePage() 함수 호출됨')
    const localRoomId = localStorage.getItem('roomId')
    // openNewWindow2(`이스케이프페이지 탐 ${subscribersComputed.value.length}`)
    if (!isFull && !subscribersComputed.value.length){ // 다른 참여자가 없으면서 정원이 차서 나가는게 아닐떄만
      shutDownRoom(localRoomId)
    }
    leaveSession()
  }
  function handleShutDownRoom(roomId) {
    const isShut = confirm("방을 페쇄 하시겠습니까?")
    if(isShut){
      alert('알겠습니다. 방을 폐쇄하겠습니다.')
      try{shutDownRoom(roomId)}
      catch(error){
        console.log('이상생김',error)
      }
      leaveSession()
    }
    else{
      console.log('방 페쇄 안하기로 결정')
    }
  }

  function shutDownRoom(roomId) {
    webrtcstore.shutDownRoom(roomId)
    // roomId.value = null             // 방 폐쇄하고 브라우저의 roomId도 null값으로 만듬
  }
  ////////////////////////
  function updateMainVideoStreamManager(stream) {
    if (mainStreamManager.value === stream) return
    mainStreamManager.value = stream
  }
  //////////////////////////// 방 참여에 있어 방을 생성해주고, 방에 참석할 수 있게 해주는 것.
  async function createToken(mySessionId, roomId) {
    // 방 생성과 참가를 가르는 변수
    const isMaking = webrtcstore.isMaking // 해당 참여자의 isMaking의 값이 true인지 false인지 확인함,
    // 방 참가시에 관련한 것
    const roomNo = roomId  // join하면서 
    const userNo = webrtcstore.userNo // 임시로 랜덤 값으로 보내는 중
    const inputPassword = webrtcstore.inputPassword
    // 방 생성시에 관련한 것
    // const userNo = webrtcstore.userNo
    const endHour = webrtcstore.endHour
    const endMinute = webrtcstore.endMinute
    const quota = webrtcstore.quota
    const isPrivacy = webrtcstore.isPrivacy
    const password = webrtcstore.password
    const backImgFile = webrtcstore.backImgFile
    const rule = webrtcstore.rule
    const roomTags = webrtcstore.roomTags
    ////////////////////////////////////////////////////
    if(!isMaking){
      try{
        console.log(roomNo, userNo, inputPassword)
        // const response = await axios.post(APPLICATION_SERVER_URL + 'rooms/' + roomNo, {userNo: userNo, password: inputPassword}, {
        const response = await axios.post(APPLICATION_SERVER_URL + 'rooms/' + roomNo, {userNo: userNo, password: inputPassword}, {
        headers: { 'Content-Type': 'application/json', },
        })
        /////////////////////////////////
        // 유저 누구누구 있는지 받아와야함.
        ////////////////////////////////
        console.log('방에 참여하나')
        console.log('방에 참여 리스폰스데이터 잘 받음',response.data)
        webrtcstore.isMakingFalse()
        return response.data
      }
      catch(error){
        console.error('만들어진 방이없어서 발생한 에러:', error);
        webrtcstore.isMakingFalse()
        // webrtcstore.roomExit(roomId.value)  // 방나가면 방나갔음을 백엔드로 전송.
        // openNewWindow2('방에 참가하다 leaveSession함 방아이디는'+localStorage.getItem('roomId'))
        leaveSession()
      }
    }
    else{
      try{
        console.log('몇시간작동할겨?',endHour, endMinute)
        const response = await axios.post(APPLICATION_SERVER_URL + 'rooms/add', 
        {userNo: userNo, title: mySessionId, endHour: endHour, endMinute: endMinute, quota: quota, 
          isPrivacy: isPrivacy, password: password, iamgePath: backImgFile, rule: rule, tags: roomTags, role:'MODERATOR'}, 
          {headers: { 'Content-Type': 'application/json', },
        })
        console.log('create할때',userNo, mySessionId, endHour, endMinute, quota, isPrivacy, password, backImgFile, rule)
        webrtcstore.updateRoomId(response.data.room.id) // 만든 방의 룸Id를 store에 표기,
        webrtcstore.isMakingFalse()
        console.log('방만들때 roomId 로컬에 저장!!!!!',  response.data.room.id)
        // roomId가 변경되면 localstorage에 저장합니다.
        localStorage.setItem('roomId', response.data.room.id)  // 로컬스토리지에 roomId를 저장시켰으니 shutDown시킬때
        // try{
        //   webrtcstore.giveRole(roomId)
        // }
        // catch(error){
        //   console.log(error)
        // }
        return response.data.token;
      }
      catch(error){
        console.error("방 생성에도 오류 났음.",error);
        webrtcstore.isMakingFalse()
        // openNewWindow2('방을 만들다가 leaveSession함 방아이디는'+localStorage.getItem('roomId'))
        leaveSession()
      }
    }
  }
  ///////////////////////////////////////////////////////////////////////////////////////
  // 캠, 오디오 등 기기와 관련된 함수
  // 카메라와 오디오를 가져옴.
  async function getMedia() {
    try {
      const devices = await navigator.mediaDevices.enumerateDevices()
      const cameras = devices.filter((device) => device.kind === 'videoinput')
      console.log('카메라들',cameras)
      const audios = devices.filter((device) => device.kind === 'audioinput')
      console.log('마이크들',audios)
      // const audios = undefined
      const cameraSelect = document.querySelector('select[name="cameras"]')
      const audioSelect = document.querySelector('select[name="audios"]')
      // 카메라 및 오디오 선택기 요소가 존재하는지 확인
      if (cameras) {
        cameras.forEach((camera) => {
          const option = document.createElement('option')
          option.value = camera.deviceId;
          option.text = camera.label;
          cameraSelect.appendChild(option);
        });
        // 화면 공유 기능 추가를 위한 option
        const screenShareOpt = document.createElement('option')
        screenShareOpt.value = 'screenShare'
        screenShareOpt.innerText = '화면공유'
        cameraSelect.appendChild(screenShareOpt)
      } else {
        const notCamera = cameraSelect.querySelector('option:disabled');
        notCamera.innerText = '사용 가능한 카메라가 없습니다.'
        // 화면 공유 기능 추가를 위한 option
        const screenShareOpt = document.createElement('option')
        screenShareOpt.value = 'screenShare'
        screenShareOpt.innerText = '화면공유'
        cameraSelect.appendChild(screenShareOpt)
      }
      if(audios){
        audios.forEach((audio) => {
          const option = document.createElement('option')
          option.value = audio.deviceId
          option.text = audio.label
          audioSelect.appendChild(option)
        });
      } else {
        const notAudio = audioSelect.querySelector('option:disabled')
        notAudio.innerText = '사용 가능한 마이크가 없습니다.'
      }
      
    } catch (error) {
      console.error('Error getting media devices:', error)
    }
  }
  /////// 화면 공유기능
  async function screenShare() {
    try {
      const displayMediaStream = await navigator.mediaDevices.getDisplayMedia({video: true})
      const newVideoTrack = displayMediaStream.getVideoTracks()[0]
      await publisher.value.replaceTrack(newVideoTrack)
      mainStreamManager.value = publisher.value
    } catch (error) {
      console.error("Error starting screen share:", error)
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
    publisher.value.publishVideo(!camerOff.value)
  }
  function handleMuteBtn() {
    if (!publisher.value) return

    // 음소거 상태 토글
    muted.value = !muted.value;
    const muteActivate = document.getElementById('mute-activate')
    if(muted.value){   //음소거 활성화상태
      muteActivate.innerText = '음소거 비활성화'
    }else{                //음소거 비활성화상태
      muteActivate.innerText = '음소거 활성화'
    }
    // 음소거 설정을 적용
    publisher.value.publishAudio(!muted.value)
  }
  async function handleCameraChange(event) {
    // 스크린 공유 선택 옵션 확인 및 호출
    if (event.target.value === "screenShare") {
      screenShare()
    } else {
      selectedCamera.value = event.target.value
      await replaceCameraTrack(selectedCamera.value)
    }
  }
  async function handleAudioChange(event) {
    selectedAudio.value = event.target.value
    await replaceAudioTrack(selectedAudio.value)
  }
  async function replaceCameraTrack(deviceId) {
    if (!publisher.value) return

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
      const newVideoTrack = newStream.getVideoTracks()[0]
      await publisher.value.replaceTrack(newVideoTrack)
    // mainStreamManager를 업데이트하여 선택한 카메라를 메인 비디오로 설정
      mainStreamManager.value = publisher.value
    } catch (error) {
        console.error("Error replacing video track:", error)

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
        const newAudioTrack = newStream.getAudioTracks()[0]
        await publisher.value.replaceTrack(newAudioTrack)
    } catch (error) {
        console.error("Error replacing audio track:", error)
    }
  }
  /////////////////////////
  // 탭 메뉴 관련
  // const funcTabs = ref(['참여멤버', '메시지', '그라운드 룰', '공유'])
  const funcTabsHost = ref(['참여멤버', '메시지', '그라운드 룰', '공유', '사용자 강퇴'])
  const funcTabs = ref(['참여멤버', '메시지', '그라운드 룰', '공유'])
  const activeFuncTab = ref(1)
  const roomRule = ref(webrtcstore.rule)

  function changeTab(index) {
    activeFuncTab.value = index
    console.log(myUserName.value)
  }
  /////////////////////////
  ///////////// 확인용으로 tmp  임시로 만들어둠
  function openNewWindow2(roomabc) {
    const url = `https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=${roomabc}`
    const windowName = "새 창"
    const windowFeatures = "width=800,height=600"
    
    window.open(url, windowName, windowFeatures);
  }
  async function checkConnection(roomId) {
    console.log(subscribersComputed.value)
    console.log(subscribersComputed.value.length)
    try{
      const response = await axios.get(`http://localhost:8080/rooms/currentConnection/${roomId}`)
      console.log(response.data)
    }
    catch(error){
      console.error('체크커넥션 실패...',error.code, error.message)
    }
  }
  ////// 나중에 지울거임
  function checkSubScirbers(){
    alert(`현재명수: ${subscribersComputed.value.length+1}명`)
  }

  // 퇴장시키기
  async function handleForceDisconnect(subscriber) {
    const kickReason = prompt('강제 퇴장의 사유를 작성해주세요.')
    try {
      await session.value.forceDisconnect(subscriber.stream.connection);
      // webrtcstore.kickUser(roomId. value, userId ,kickReason) // userId 를 못받아옴.
    } catch (error) {
      console.error("사용자 연결을 강제로 끊는 중 오류 발생:", error);
    }
  }

</script>

<template>
  <!-- session이 true일때! 즉, 방에 들어갔을 때 -->
  <!-- <div id="session" v-if="session" style="color: white;"> -->
  <div id="session" style="color: white;">
    <div>
      <div>
        <input type="button" value="참가자 수 확인" @click="checkSubScirbers">
      </div>
      <div id="session-header">
        <h1 id="session-title">{{ mySessionId }}</h1>
        <input type="button" id="buttonLeaveSession" @click="handleLeaveSession" value="방 떠나기" />
      </div>
      <!-- 화상이 보이는 곳 -->
      <div id="camScreen"> 
        
        <!-- 모든 캠 -->
        <div id="videoContainer">
          <!-- <UserVideo :stream-manager="publisherComputed" @click.native="updateMainVideoStreamManager(publisher)" :style="{width: '10%', height: '5%'}"/> -->
          <UserVideo :stream-manager="publisherComputed" @click.native="updateMainVideoStreamManager(publisher)" />
          <UserVideo v-for="sub in subscribersComputed" :key="sub.stream.connection.connectionId" :stream-manager="sub"
            @click.native="updateMainVideoStreamManager(sub)" />
        </div>
        <!-- 선택 캠 -->
        <!-- <div id="mainVideo">
          <UserVideo :stream-manager="mainStreamManagerComputed" />
        </div> -->
      </div>
      <!-- 캠 및 오디오 관련 -->
      <div id="deviceDiv">
        <!-- 캠활성화, 음소거 버튼 -->
        <button id="camera-activate" @click="handleCameraBtn">캠 비활성화</button>
        <button id="mute-activate" @click="handleMuteBtn">음소거 활성화</button>
        <button @click="checkConnection(roomId)">체크커넥션</button>
        <!-- 캠,오디오 선택 옵션 -->
        <div>
          <select name="cameras" @change="handleCameraChange">
            <option disabled>사용할 카메라를 선택하세요</option>
            <!-- <option value="screenShare">스크린 공유</option> -->
          </select>
          <select name="audios" @change="handleAudioChange">
            <option disabled>사용할 마이크를 선택하세요</option>
          </select>
        </div>
      </div>
      <!-- 방 종료 버튼 -->
      <div id='' v-if="isHost">
        <button @click="handleShutDownRoom(roomId)">방 폐쇄하기</button>
      </div>
    </div>
  </div>
  <!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  -->
  <!-- 화상회 부가기능이 보이는 곳 -->
  <div id="functionTab" style="color: white;">
    <div id="tabs">
      <ul id="functionUl" style="display: flex;" v-if="isHost" >
        <div >
          <li v-for="(tab, index) in funcTabsHost" :key="index" id="tab{{index}}" :class="{ 'active': activeFuncTab === index }" @click="changeTab(index)">
            {{ tab }}
          </li>
        </div>
      </ul>
      <ul id="functionUl" style="display: flex;" v-else >
        <div >
          <li v-for="(tab, index) in funcTabs" :key="index" id="tab{{index}}" :class="{ 'active': activeFuncTab === index }" @click="changeTab(index)">
            {{ tab }}
          </li>
        </div>
      </ul>
      <div v-if="activeFuncTab === 0">참여멤버</div>
      <div v-if="activeFuncTab === 1">메시지</div>
      <div v-if="activeFuncTab === 2">그라운드 룰</div>
      <div v-if="activeFuncTab === 3">공유</div>
      <div v-if="isHost && activeFuncTab === 4">강제퇴장</div>
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
    <!-- 메시지 -->
    <MessageChat :messages="messages" :session="session" :myUserName="myUserName" v-if="activeFuncTab === 1" />
    <!-- 그라운드 룰 -->
    <div v-if="activeFuncTab === 2">
      {{ roomRule }}
    </div>
    <!-- 공유 -->
    <div v-if="activeFuncTab === 3"></div>

    <!-- 강제퇴장 -->
    <div v-if="isHost && activeFuncTab === 4">
      <div v-for="(subscriber, index) in subscribersComputed" :key="index">
        <button @click="handleForceDisconnect(subscriber)">{{ JSON.parse(subscriber.stream.connection.data).clientData }}님을 퇴장시키기</button>
      </div>
    </div>
  </div>
    
</template>

<style scoped>
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