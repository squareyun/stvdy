<script setup>
import {
  ref,
  computed,
  onBeforeMount,
  watch,
  onUpdated,
  onMounted,
  onUnmounted,
  onBeforeUnmount,
} from 'vue'
import axios from 'axios'
import { OpenVidu } from 'openvidu-browser'
// import { OpenVidu } from 'assets/openvidu-browser-2.28.0.min.js'; // 파일의 상대 경로로 수정
import UserVideo from '@/components/webrtc/UserVideo.vue'
import MessageChat from '@/components/webrtc/MessageChat.vue'
import { useRouter } from 'vue-router'
import { usewebRtcStore } from '@/stores'
import { useUserStore } from '@/stores'

// const store = usewebRtcStore()
const webrtcstore = usewebRtcStore()
const router = useRouter()
const userstore = useUserStore()

//////////// 새로고침 방지!
document.addEventListener('keydown', preventRefresh)
document.addEventListener('keyup', preventRefresh)
document.addEventListener('keypress', preventRefresh)
function preventRefresh(event) {
  const key_f5 = 116 // 116 = F5
  const key_r = 82 // 82 = R
  if (key_f5 === event.keyCode || (event.ctrlKey && key_r === event.keyCode)) {
    event.preventDefault()
    // alert('이 페이지는 F5키와 Ctrl + R로 새로 고침되지 않습니다.');
    alert('이 페이지는 새로 고침되지 않습니다.')
    return false
  }
}

var deviceToggled = ref(false)
const deviceSettingToggle = () => {
  const deviceBox = document.getElementById('profile-div')
  if (!deviceToggled.value) {
    deviceBox.animate(
      {
        transform: ['translateY(0px)', 'translateY(-90px)'],
      },
      {
        duration: 400,
        fill: 'forwards',
        easing: 'ease',
      },
    )
    deviceToggled.value = true
  } else {
    deviceBox.animate(
      {
        transform: ['translateY(-90px)', 'translateY(0px)'],
      },
      {
        duration: 400,
        fill: 'forwards',
        easing: 'ease',
      },
    )
    deviceToggled.value = false
  }
}

axios.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8'
// 추후 배포와 관련해서 이부분에 대해서 설정을 할 필요가 있게 될것.

// const APPLICATION_SERVER_URL =
//   process.env.NODE_ENV === 'production' ? '' : 'https://i9d205.p.ssafy.io/api/'
// const APPLICATION_SERVER_URL = 'https://i9d205.p.ssafy.io/api/api/'
const APPLICATION_SERVER_URL =
  process.env.NODE_ENV === 'production' ? '' : '/api'

// OpenVidu objects
const OV = ref(undefined)
const session = ref(undefined)
// const mainStreamManager = ref(undefined)
let mainStreamManager = ref(undefined)
const publisher = ref(undefined)
const subscribers = ref([]) //

// Join form
const mySessionId = ref(decodeURIComponent(webrtcstore.mySessionId)) // 인코딩값을 디코딩한 해줘서 받아야만 작동가능함
// const mySessionId = ref(webrtcstore.mySessionId)
// 만약 인코딩해서 받은 값이 아니라면, 디코딩하지 않은 상태로 받기 위함.
if (webrtcstore.mySessionId === encodeURIComponent(webrtcstore.mySessionId)) {
  mySessionId.value = webrtcstore.mySessionId
}
const myUserName = ref(webrtcstore.myUserName)
const roomId = ref(webrtcstore.roomId)
// 방에서 최대 인원수
const quota = ref(webrtcstore.quota)
const isHost = ref(webrtcstore.isHost)
/////////////////////채팅창을 위한 부분임.
const inputMessage = ref('')
const messages = ref([])
// const isChatContainer = ref(true)   /// 채팅창!!!!!
///////////////////카메라 및 오디오 설정을 위한 부분임
const muted = ref(false) // 기본은 음소거 비활성화
const cameraOff = ref(false) // 기본 카메라 활성화
const selectedCamera = ref('') // 카메라 변경시 사용할 변수
const selectedAudio = ref('') // 오디오 변경시 사용할 변수
////다시그려내기 위해 computed 작성
const mainStreamManagerComputed = computed(() => mainStreamManager.value)
const publisherComputed = computed(() => publisher.value)
const subscribersComputed = computed(() => subscribers.value)
const participantNumber = computed(() => subscribersComputed.value.length + 1)
// 방 이탈를 위한 변수들
const isExitRoom = ref(webrtcstore.isExitRoom) // leave
const isFull = ref(false)

// user Id를 받을 수 있는지 시도함
const userId = ref(webrtcstore.userNo)

var formerNumb = 0

const changeBoxSize = () => {
  const e = document.getElementsByClassName('user-box')
  if (formerNumb != e.length) {
    formerNumb = e.length
    for (var i = 0; i <= formerNumb; i++) {
      if (formerNumb == 1) {
        e[0].style.setProperty('width', 'calc(100% - 4px)')
        e[0].style.setProperty('height', 'calc(100% - 4px)')
      } else if (formerNumb <= 4) {
        e[i].style.setProperty('width', 'calc(50% - 4px)')
        e[i].style.setProperty('height', 'calc(50% - 4px)')
      } else if (formerNumb <= 9) {
        e[i].style.setProperty('width', 'calc(33% - 4px)')
        e[i].style.setProperty('height', 'calc(33% - 4px)')
      } else if (formerNumb <= 16) {
        e[i].style.setProperty('width', 'calc(25% - 4px)')
        e[i].style.setProperty('height', 'calc(25% - 4px)')
      }
    }
  }
}

const randomImage = () => {
  const e = document.getElementsByClassName('content-user-image')
  for (var i = 0; i < e.length; i++) {
    var numb = Math.floor(Math.random() * 34)
    if (e[i].style.getPropertyValue('background-image') === '') {
      e[i].style.setProperty(
        'background-image',
        `url('/randomImages/randomImage${numb}.png')`,
      )
      if (i == 0) {
        const m = document.getElementById('random-profile-image')
        m.style.setProperty(
          'background-image',
          `url('/randomImages/randomImage${numb}.png')`,
        )
      }
    }
  }
}

// watch(participantNumber, (numb) => {
//   changeBoxSize(numb)
// })

const sessionContainer = ref(null)
const aspectRatioContainer = ref(null)
// // 캠 사이즈 조절
const resizeHandler = () => {
  const containerWidth = sessionContainer.value.offsetWidth
  const containerHeight = sessionContainer.value.offsetHeight
  const aspectRatio = 4 / 3 // 4:3 비율
  const calculatedWidth = Math.min(
    containerWidth,
    containerHeight * aspectRatio,
  )
  const calculatedHeight = calculatedWidth / aspectRatio

  aspectRatioContainer.value.style.width = calculatedWidth + 'px'
  aspectRatioContainer.value.style.height = calculatedHeight + 'px'
}

onMounted(() => {
  window.addEventListener('resize', resizeHandler)
  resizeHandler()
  setTimeout(() => {
    console.log('user정보 받아옴', userInfo.value)
  }, 3000)
  setInterval(changeBoxSize, 500)
  setInterval(randomImage, 1000)
})

onUnmounted(() => {
  window.removeEventListener('resize', resizeHandler)
})
onBeforeMount(() => {
  // window.addEventListener("beforeunload",leaveSession)
  window.addEventListener('beforeunload', escapePage)
  // mySessionId가 없으면 잘못된 접근이므로
  if (
    mySessionId.value !== null &&
    mySessionId.value !== undefined &&
    mySessionId.value !== 'null'
  ) {
    joinSession()
  } else {
    alert('잘못된 접근으로 방에서 나갑니다.')
    leaveSession()
  }
})
//// 페이지 벗어나면
onBeforeUnmount(() => {
  if (!isExitRoom.value) {
    // leaveSession버튼을 누르지 않았을때만.
    // 방을 나가는데 사람이 없으면 방을 폐쇄할 거임.
    console.log('나감으로 방폐쇄1')
    if (!subscribersComputed.value.length) {
      // 다른 참여자가 없으면 실행
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
  window.removeEventListener('beforeunload', escapePage)
  document.removeEventListener('keydown', preventRefresh)
  document.removeEventListener('keyup', preventRefresh)
  document.removeEventListener('keypress', preventRefresh)
  webrtcstore.roomExit(roomId.value) // 방나가면 방나갔음을 백엔드로 전송.
  isExitFalse()
})
const userInfo = ref(userstore.user)

onUpdated(() => {})

function isFullRoom() {
  console.log('!!!!!!방인원제한', quota.value)
  console.log('!!!!!!현재방인원', subscribersComputed.value)
  console.log('!!!!!!현재방 실제 인원수', subscribersComputed.value.length + 1)
  if (quota.value < subscribersComputed.value.length + 1) {
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
  console.log('이게 session.value', session.value)
  session.value.on('streamCreated', ({ stream }) => {
    const subscriber = session.value.subscribe(stream)
    subscribers.value.push(subscriber)
  })

  // 모든 스트림 파괴...
  session.value.on('streamDestroyed', ({ stream }) => {
    const index = subscribers.value.indexOf(stream.streamManager, 0)
    if (index >= 0) {
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
  session.value.on('streamManagerRemoved', (event) => {
    if (event.forced) {
      console.log('얍! streamManagerRemoved')
      // leaveSession();
      router.push({
        name: 'maintmp', // 임시로 main으로 넘겨줌.
        // name:'main',
      })
    }
  })

  session.value.on('sessionDisconnected', (event) => {
    // 이벤트 처리 코드
    console.log('세션에서 연결 끊어짐:', event)
    // 필요한 추가 작업을 수행합니다. 예: UI 업데이트, 사용자 알림 등
    router.push({
      name: 'maintmp', // 임시로 main으로 넘겨줌.
      // name:'main',
    })
  })

  session.value.on('exception', ({ exception }) => {
    console.warn(exception)
  })

  // 채팅 이벤트 수신 처리 함. session.on이 addEventListenr 역할인듯.
  session.value.on('signal:chat', (event) => {
    // event.from.connectionId === session.value.connection.connectionId 이건 나와 보낸이가 같으면임
    const messageData = JSON.parse(event.data)
    if (event.from.connectionId === session.value.connection.connectionId) {
      messageData['username'][0] = '나' //messageData['username'][0] => userNickname
    }
    console.log(messageData['username'][1]) // messageData['username'][1] => userNO 즉, user의 id
    console.log(messageData.userNo) // 아이디를 가져오나???
    messages.value.push(messageData)
  })

  // createToken(mySessionId.value).then((token) => {
  createToken(mySessionId.value, roomId.value).then((token) => {
    roomId.value = webrtcstore.roomId
    if (roomId.value !== undefined && roomId.value !== null) {
      // roomId가 변경되면 localstorage에 저장합니다.
      localStorage.setItem('roomId', roomId.value) // 로컬스토리지에 roomId를 저장시켰으니 shutDown시킬때
    }
    console.log(':: session ::', session.value)
    session.value
      .connect(token, { clientData: myUserName.value })
      .then(() => {
        console.log('방생성 및 입장완료나 다름없음')
        let publisherTmp = OV.value.initPublisher(undefined, {
          audioSource: undefined, // The source of audio. If undefined default microphone
          videoSource: undefined, // The source of video. If undefined default webcam
          publishAudio: !muted.value, // Whether you want to start publishing with your audio unmuted or not
          publishVideo: !cameraOff.value, // Whether you want to start publishing with your video enabled or not
          resolution: '640x480', // The resolution of your video
          // resolution: "1280x960", // The resolution of your video
          frameRate: 30, // The frame rate of your video
          insertMode: 'APPEND', // How the video is inserted in the target element 'video-container'
          mirror: false, // Whether to mirror your local video or not
        })
        mainStreamManager.value = publisherTmp
        publisher.value = publisherTmp

        session.value.publish(publisher.value)
        getMedia() // 세션이 만들어졌을때 미디어 불러옴
        console.log('방생성 완료!!!')
        webrtcstore.shareRoomAddress(localStorage.getItem('roomId')) // 방 공유관련 정보 받음
      })
      .catch((error) => {
        console.log(
          'session 커넥팅에 문제생김(createToken):',
          error.code,
          error.message,
        )
        escapePage()
      })
  })
  console.log('이건제발되라1', subscribers.value)
  console.log('이건제발되라2', subscribers.value.length)
  isFullRoom()
  // window.addEventListener("beforeunload",leaveSession);
}
function handleLeaveSession(event) {
  const isOut = confirm('방을 나가시겠습니까?')
  if (!isOut) return
  // 방을 나가는데 사람이 없으면 방을 폐쇄할 거임.
  console.log('핸들 나감으로 방폐쇄1')
  if (subscribersComputed.value.length == 0) {
    // 다른사람이 없으면 실행
    // webrtcstore.shutDownRoom(roomId.value)
    const localRoomId = localStorage.getItem('roomId')
    webrtcstore.shutDownRoom(localRoomId)
    console.log('핸들 나감으로 방폐쇄2')
  }
  leaveSession()
}
function leaveSession() {
  console.log('leaveSession() 함수 호출됨') // 확인용 로그
  console.log('참여자', subscribersComputed.value)
  console.log('참여자수', subscribersComputed.value.length)
  if (session.value) session.value.disconnect()
  // Empty all properties...
  session.value = undefined
  mainStreamManager.value = undefined
  publisher.value = undefined
  subscribers.value = []
  OV.value = undefined
  // 이벤트리스너 제거하기 beforeunload listener
  // window.removeEventListener("beforeunload", handleLeaveSession)
  // window.removeEventListener("beforeunload", leaveSession)
  // document.removeEventListener('keydown', preventRefresh)
  isExitTrue()
  // const localRoomId = localStorage.getItem('roomId')
  // openNewWindow2(`이건 리브세션버튼 누른거` + '섭스크라이브'+ subscribersComputed.value.length+'방id'+localRoomId)
  // 메인페이지로 넘어감
  router.push({
    name: 'maintmp', // 임시로 main으로 넘겨줌.
    // name:'main',
  })
}
function escapePage() {
  console.log('escapePage() 함수 호출됨')
  const localRoomId = localStorage.getItem('roomId')
  // openNewWindow2(`이스케이프페이지 탐 ${subscribersComputed.value.length}`)
  if (!isFull && !subscribersComputed.value.length) {
    // 다른 참여자가 없으면서 정원이 차서 나가는게 아닐떄만
    shutDownRoom(localRoomId)
  }
  leaveSession()
}
function handleShutDownRoom(roomId) {
  const isShut = confirm('방을 페쇄 하시겠습니까?')
  if (isShut) {
    alert('알겠습니다. 방을 폐쇄하겠습니다.')
    try {
      shutDownRoom(roomId)
    } catch (error) {
      console.log('이상생김', error)
    }
    leaveSession()
  } else {
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
  const roomNo = roomId // join하면서
  const userNo = webrtcstore.userNo // 임시로 랜덤 값으로 보내는 중
  const inputPassword = webrtcstore.inputPassword
  // 방 생성시에 관련한 것
  const endHour = webrtcstore.endHour
  const endMinute = webrtcstore.endMinute
  const quota = webrtcstore.quota
  const isPrivacy = webrtcstore.isPrivacy
  const password = webrtcstore.password
  const backImgFile = webrtcstore.backImgFile
  const rule = webrtcstore.rule
  const roomTags = webrtcstore.roomTags
  ////////////////////////////////////////////////////
  if (!isMaking) {
    try {
      console.log(roomNo, userNo, inputPassword)
      // const response = await axios.post(APPLICATION_SERVER_URL + 'rooms/' + roomNo, {userNo: userNo, password: inputPassword}, {
      const response = await axios.post(
        'https://i9d205.p.ssafy.io/api/rooms/' + roomNo,
        { userNo: userNo, password: inputPassword },
        {
          headers: { 'Content-Type': 'application/json' },
        },
      )
      /////////////////////////////////
      // 유저 누구누구 있는지 받아와야함.
      ////////////////////////////////
      console.log('방에 참여하나')
      console.log('방에 참여 리스폰스데이터 잘 받음', response.data)
      console.log(
        '방에 참여할때할때',
        userNo,
        mySessionId,
        endHour,
        endMinute,
        quota,
        isPrivacy,
        password,
        backImgFile,
        rule,
      )
      webrtcstore.isMakingFalse()
      return response.data
    } catch (error) {
      console.error('만들어진 방이없어서 발생한 에러:', error)
      webrtcstore.isMakingFalse()
      // webrtcstore.roomExit(roomId.value)  // 방나가면 방나갔음을 백엔드로 전송.
      // openNewWindow2('방에 참가하다 leaveSession함 방아이디는'+localStorage.getItem('roomId'))
      leaveSession()
    }
  } else {
    try {
      // console.log('몇시간작동할겨?',endHour, endMinute)
      const response = await axios.post(
        'https://i9d205.p.ssafy.io/api/rooms/add',
        {
          userNo: userNo,
          title: mySessionId,
          endHour: endHour,
          endMinute: endMinute,
          quota: quota,
          isPrivacy: isPrivacy,
          password: password,
          iamgePath: backImgFile,
          rule: rule,
          tags: roomTags,
          role: 'MODERATOR',
        },
        { headers: { 'Content-Type': 'application/json' } },
      )
      console.log(
        'create할때',
        userNo,
        mySessionId,
        endHour,
        endMinute,
        quota,
        isPrivacy,
        password,
        backImgFile,
        rule,
      )
      webrtcstore.updateRoomId(response.data.room.id) // 만든 방의 룸Id를 store에 표기,
      webrtcstore.isMakingFalse()
      console.log('방만들때 roomId 로컬에 저장!!!!!', response.data.room.id)
      // roomId가 변경되면 localstorage에 저장합니다.
      localStorage.setItem('roomId', response.data.room.id) // 로컬스토리지에 roomId를 저장시켰으니 shutDown시킬때

      // const responseImagePath = webrtcstore.downloadImagefromServer(userNo)
      // console.log(responseImagePath)
      // try{
      //   webrtcstore.giveRole(roomId)
      // }
      // catch(error){
      //   console.log(error)
      // }
      return response.data.token
    } catch (error) {
      console.error('방 생성에도 오류 났음.', error)
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
    console.log('카메라들', cameras)
    const audios = devices.filter((device) => device.kind === 'audioinput')
    console.log('마이크들', audios)
    // const audios = undefined
    const cameraSelect = document.querySelector('select[name="cameras"]')
    const audioSelect = document.querySelector('select[name="audios"]')
    // 카메라 및 오디오 선택기 요소가 존재하는지 확인
    if (cameras) {
      cameras.forEach((camera) => {
        const option = document.createElement('option')
        option.value = camera.deviceId
        option.text = camera.label
        cameraSelect.appendChild(option)
      })
      // 화면 공유 기능 추가를 위한 option
      const screenShareOpt = document.createElement('option')
      screenShareOpt.value = 'screenShare'
      screenShareOpt.innerText = '화면공유'
      cameraSelect.appendChild(screenShareOpt)
    } else {
      const notCamera = cameraSelect.querySelector('option:disabled')
      notCamera.innerText = '사용 가능한 카메라가 없습니다.'
      // 화면 공유 기능 추가를 위한 option
      const screenShareOpt = document.createElement('option')
      screenShareOpt.value = 'screenShare'
      screenShareOpt.innerText = '화면공유'
      cameraSelect.appendChild(screenShareOpt)
    }
    if (audios) {
      audios.forEach((audio) => {
        const option = document.createElement('option')
        option.value = audio.deviceId
        option.text = audio.label
        audioSelect.appendChild(option)
      })
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
    const displayMediaStream = await navigator.mediaDevices.getDisplayMedia({
      video: true,
    })
    const newVideoTrack = displayMediaStream.getVideoTracks()[0]
    await publisher.value.replaceTrack(newVideoTrack)
    mainStreamManager.value = publisher.value
  } catch (error) {
    console.error('Error starting screen share:', error)
  }
}
// 음소거, 캠 활성화 버튼 작동
function handleCameraBtn() {
  if (!publisher.value) return
  // 카메라 상태 토글
  cameraOff.value = !cameraOff.value
  publisher.value.publishVideo(!cameraOff.value)
}
function handleMuteBtn() {
  if (!publisher.value) return

  // 음소거 상태 토글
  muted.value = !muted.value
  publisher.value.publishAudio(!muted.value)
}
async function handleCameraChange(event) {
  // 스크린 공유 선택 옵션 확인 및 호출
  if (event.target.value === 'screenShare') {
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
  }
  // 카메라 비활성화 조건을 추가합니다.
  if (cameraOff.value) {
    newConstraints.video = false
  }
  try {
    const newStream = await navigator.mediaDevices.getUserMedia(newConstraints)
    const newVideoTrack = newStream.getVideoTracks()[0]
    await publisher.value.replaceTrack(newVideoTrack)
    // mainStreamManager를 업데이트하여 선택한 카메라를 메인 비디오로 설정
    mainStreamManager.value = publisher.value
  } catch (error) {
    console.error('Error replacing video track:', error)
  }
}
async function replaceAudioTrack(deviceId) {
  if (!publisher.value) return
  const newConstraints = {
    audio: {
      deviceId: { exact: deviceId },
    },
    video: false,
  }
  try {
    const newStream = await navigator.mediaDevices.getUserMedia(newConstraints)
    const newAudioTrack = newStream.getAudioTracks()[0]
    await publisher.value.replaceTrack(newAudioTrack)
  } catch (error) {
    console.error('Error replacing audio track:', error)
  }
}
const funcTabsHost = ref([
  '참여멤버',
  '메시지',
  '그라운드 룰',
  // '공유',
  // '사용자 강퇴',
])
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
  const windowName = '새 창'
  const windowFeatures = 'width=800,height=600'

  window.open(url, windowName, windowFeatures)
}
async function checkConnection(roomId) {
  console.log(subscribersComputed.value)
  console.log(subscribersComputed.value.length)
  try {
    const response = await axios.get(
      `https://i9d205.p.ssafy.io/api/rooms/currentConnection/${roomId}`,
    )
    console.log(response.data)
  } catch (error) {
    console.error('체크커넥션 실패...', error.code, error.message)
  }
}

////// 나중에 지울거임
function checkSubScirbers() {
  alert(`현재명수: ${subscribersComputed.value.length + 1}명`)
  subscribersComputed.value.forEach((sub) => {
    const Data = JSON.parse(sub.stream.connection.data)
    const clientData = JSON.parse(sub.stream.connection.data).clientData
    // 고유한 키(key) 값을 지정하지 않을 경우, index를 직접 사용할 수 있습니다.
    // 결과를 화면에 출력하거나, 다른 처리를 수행할 수 있습니다.
    console.log(Data)
    console.log(clientData)
  })
}

// 퇴장시키기
async function handleForceDisconnect(subscriber) {
  const kickReason = prompt('강제 퇴장의 사유를 작성해주세요.')
  try {
    await session.value.forceDisconnect(subscriber.stream.connection)
    // webrtcstore.kickUser(roomId. value, userId ,kickReason) // userId 를 못받아옴.
  } catch (error) {
    console.error('사용자 연결을 강제로 끊는 중 오류 발생:', error)
  }
}
</script>

<template>
  <div id="session-top">
    <!-- <span
      v-if="isHost"
      @click="handleShutDownRoom(roomId)">
      방 폐쇄하기
    </span> -->
    <span id="session-title">{{ mySessionId }}</span>
  </div>
  <div
    id="session"
    ref="sessionContainer"
    style="color: white">
    <div>
      <div
        id="video-container"
        ref="aspectRatioContainer">
        <!-- <UserVideo :stream-manager="publisherComputed" @click.native="updateMainVideoStreamManager(publisher)" :style="{width: '10%', height: '5%'}"/> -->
        <UserVideo
          :stream-manager="publisherComputed"
          @click.native="updateMainVideoStreamManager(publisher)" />
        <UserVideo
          v-for="sub in subscribersComputed"
          :key="sub.stream.connection.connectionId"
          :stream-manager="sub"
          @click.native="updateMainVideoStreamManager(sub)" />
      </div>
      <!-- 선택 캠 -->
      <!-- <div id="mainVideo">
          <UserVideo :stream-manager="mainStreamManagerComputed" />
        </div> -->
      <!-- 캠 및 오디오 관련 -->

      <!-- 방 종료 버튼 -->
    </div>
  </div>
  <!--  -->
  <!--  -->
  <!-- 화상회 부가기능이 보이는 곳 -->
  <div id="side-function-box">
    <!-- <input type="button" value="참가자 수 확인" @click="checkSubScirbers"> -->
    <div id="function-tabs">
      <ul
        id="function-ui"
        style="display: flex">
        <li
          :key="0"
          class="function-tab"
          :class="{ active: activeFuncTab === 0 }"
          @click="changeTab(0)">
          <div
            v-if="activeFuncTab === 0"
            class="tab-activated"
            id="function-userlist-activated">
            <div class="tab-inner-box">
              <svg
                width="1.2rem"
                height="1.2rem"
                viewBox="0 0 128 128"
                fill="none"
                xmlns="http://www.w3.org/2000/svg">
                <path
                  d="M64 0C28.672 0 0 28.672 0 64C0 99.328 28.672 128 64 128C99.328 128 128 99.328 128 64C128 28.672 99.328 0 64 0ZM87.104 40.576C93.952 40.576 99.456 46.08 99.456 52.928C99.456 59.776 93.952 65.28 87.104 65.28C80.256 65.28 74.752 59.776 74.752 52.928C74.688 46.08 80.256 40.576 87.104 40.576ZM48.704 30.464C57.024 30.464 63.808 37.248 63.808 45.568C63.808 53.888 57.024 60.672 48.704 60.672C40.384 60.672 33.6 53.888 33.6 45.568C33.6 37.184 40.32 30.464 48.704 30.464ZM48.704 88.896V112.896C33.344 108.096 21.184 96.256 15.808 81.152C22.528 73.984 39.296 70.336 48.704 70.336C52.096 70.336 56.384 70.848 60.864 71.744C50.368 77.312 48.704 84.672 48.704 88.896ZM64 115.2C62.272 115.2 60.608 115.136 58.944 114.944V88.896C58.944 79.808 77.76 75.264 87.104 75.264C93.952 75.264 105.792 77.76 111.68 82.624C104.192 101.632 85.696 115.2 64 115.2Z"
                  fill="white" />
              </svg>
            </div>
            <div class="tab-right-corner"><div></div></div>
          </div>
          <div
            v-if="activeFuncTab !== 0"
            class="tab-inactivated"
            id="function-userlist-inactivated">
            <svg
              width="1.2rem"
              height="1.2rem"
              viewBox="0 0 128 128"
              fill="none"
              xmlns="http://www.w3.org/2000/svg">
              <path
                d="M64 0C28.672 0 0 28.672 0 64C0 99.328 28.672 128 64 128C99.328 128 128 99.328 128 64C128 28.672 99.328 0 64 0ZM87.104 40.576C93.952 40.576 99.456 46.08 99.456 52.928C99.456 59.776 93.952 65.28 87.104 65.28C80.256 65.28 74.752 59.776 74.752 52.928C74.688 46.08 80.256 40.576 87.104 40.576ZM48.704 30.464C57.024 30.464 63.808 37.248 63.808 45.568C63.808 53.888 57.024 60.672 48.704 60.672C40.384 60.672 33.6 53.888 33.6 45.568C33.6 37.184 40.32 30.464 48.704 30.464ZM48.704 88.896V112.896C33.344 108.096 21.184 96.256 15.808 81.152C22.528 73.984 39.296 70.336 48.704 70.336C52.096 70.336 56.384 70.848 60.864 71.744C50.368 77.312 48.704 84.672 48.704 88.896ZM64 115.2C62.272 115.2 60.608 115.136 58.944 114.944V88.896C58.944 79.808 77.76 75.264 87.104 75.264C93.952 75.264 105.792 77.76 111.68 82.624C104.192 101.632 85.696 115.2 64 115.2Z"
                fill="white" />
            </svg>
          </div>
        </li>
        <li
          :key="1"
          class="function-tab"
          :class="{ active: activeFuncTab === 1 }"
          @click="changeTab(1)">
          <div
            v-if="activeFuncTab === 1"
            class="tab-activated"
            id="function-message-activated">
            <div class="tab-left-corner"><div></div></div>
            <div class="tab-inner-box">
              <svg
                width="1.1rem"
                height="1.1rem"
                viewBox="0 0 128 128"
                fill="none"
                xmlns="http://www.w3.org/2000/svg">
                <path
                  d="M128 0H0.0639999L0 128L25.6 102.4H128V0ZM102.4 76.8H25.6V64H102.4V76.8ZM102.4 57.6H25.6V44.8H102.4V57.6ZM102.4 38.4H25.6V25.6H102.4V38.4Z"
                  fill="white" />
              </svg>
            </div>
            <div class="tab-right-corner"><div></div></div>
          </div>
          <div
            v-if="activeFuncTab !== 1"
            class="tab-inactivated"
            id="function-message-inactivated">
            <svg
              width="1.1rem"
              height="1.1rem"
              viewBox="0 0 128 128"
              fill="none"
              xmlns="http://www.w3.org/2000/svg">
              <path
                d="M128 0H0.0639999L0 128L25.6 102.4H128V0ZM102.4 76.8H25.6V64H102.4V76.8ZM102.4 57.6H25.6V44.8H102.4V57.6ZM102.4 38.4H25.6V25.6H102.4V38.4Z"
                fill="white" />
            </svg>
          </div>
        </li>
        <li
          :key="2"
          class="function-tab"
          :class="{ active: activeFuncTab === 2 }"
          @click="changeTab(2)">
          <div
            v-if="activeFuncTab === 2"
            class="tab-activated"
            id="function-rule-activated">
            <div class="tab-left-corner"><div></div></div>
            <div class="tab-inner-box">
              <svg
                width="1.2rem"
                height="1.2rem"
                viewBox="0 0 128 102"
                fill="none"
                xmlns="http://www.w3.org/2000/svg">
                <path
                  d="M92.226 44.8444L69.772 22.3905L78.7156 13.447L92.1625 26.894L119.056 0L128 8.94351L92.226 44.8444ZM57.0862 19.4727H0V32.1586H57.0862V19.4727ZM120.515 60.1308L111.572 51.1873L95.1437 67.6155L78.7156 51.1873L69.772 60.1308L86.2002 76.559L69.772 92.9871L78.7156 101.931L95.1437 85.5025L111.572 101.931L120.515 92.9871L104.087 76.559L120.515 60.1308ZM57.0862 70.2161H0V82.9019H57.0862V70.2161Z"
                  fill="white" />
              </svg>
            </div>
            <div class="tab-right-corner"><div></div></div>
          </div>
          <div
            v-if="activeFuncTab !== 2"
            class="tab-inactivated"
            id="function-rule-inactivated">
            <svg
              width="1.2rem"
              height="1.2rem"
              viewBox="0 0 128 102"
              fill="none"
              xmlns="http://www.w3.org/2000/svg">
              <path
                d="M92.226 44.8444L69.772 22.3905L78.7156 13.447L92.1625 26.894L119.056 0L128 8.94351L92.226 44.8444ZM57.0862 19.4727H0V32.1586H57.0862V19.4727ZM120.515 60.1308L111.572 51.1873L95.1437 67.6155L78.7156 51.1873L69.772 60.1308L86.2002 76.559L69.772 92.9871L78.7156 101.931L95.1437 85.5025L111.572 101.931L120.515 92.9871L104.087 76.559L120.515 60.1308ZM57.0862 70.2161H0V82.9019H57.0862V70.2161Z"
                fill="white" />
            </svg>
          </div>
        </li>
        <!-- <li
          :key="3"
          class="function-tab"
          :class="{ active: activeFuncTab === 3 }"
          @click="changeTab(3)">
          <div
            v-if="activeFuncTab === 3"
            class="tab-activated"
            id="function-share-activated">
            <div class="tab-left-corner"><div></div></div>
            <div class="tab-inner-box">
              <svg
                width="1.1rem"
                height="1.1rem"
                viewBox="0 0 116 128"
                fill="none"
                xmlns="http://www.w3.org/2000/svg">
                <path
                  d="M96.3855 90.4739C91.502 90.4739 87.1325 92.4016 83.7912 95.4217L37.9759 68.755C38.2972 67.2771 38.5542 65.7992 38.5542 64.257C38.5542 62.7149 38.2972 61.2369 37.9759 59.759L83.2771 33.3494C86.747 36.5622 91.3092 38.5542 96.3855 38.5542C107.052 38.5542 115.663 29.9438 115.663 19.2771C115.663 8.61044 107.052 0 96.3855 0C85.7189 0 77.1084 8.61044 77.1084 19.2771C77.1084 20.8193 77.3655 22.2972 77.6868 23.7751L32.3855 50.1847C28.9157 46.9719 24.3534 44.9799 19.2771 44.9799C8.61044 44.9799 0 53.5904 0 64.257C0 74.9237 8.61044 83.5341 19.2771 83.5341C24.3534 83.5341 28.9157 81.5422 32.3855 78.3293L78.1365 105.06C77.8153 106.41 77.6225 107.823 77.6225 109.237C77.6225 119.582 86.0402 128 96.3855 128C106.731 128 115.149 119.582 115.149 109.237C115.149 98.8916 106.731 90.4739 96.3855 90.4739Z"
                  fill="white" />
              </svg>
            </div>
            <div class="tab-right-corner"><div></div></div>
          </div>
          <div
            v-if="activeFuncTab !== 3"
            class="tab-inactivated"
            id="function-share-inactivated">
            <svg
              width="1.1rem"
              height="1.1rem"
              viewBox="0 0 116 128"
              fill="none"
              xmlns="http://www.w3.org/2000/svg">
              <path
                d="M96.3855 90.4739C91.502 90.4739 87.1325 92.4016 83.7912 95.4217L37.9759 68.755C38.2972 67.2771 38.5542 65.7992 38.5542 64.257C38.5542 62.7149 38.2972 61.2369 37.9759 59.759L83.2771 33.3494C86.747 36.5622 91.3092 38.5542 96.3855 38.5542C107.052 38.5542 115.663 29.9438 115.663 19.2771C115.663 8.61044 107.052 0 96.3855 0C85.7189 0 77.1084 8.61044 77.1084 19.2771C77.1084 20.8193 77.3655 22.2972 77.6868 23.7751L32.3855 50.1847C28.9157 46.9719 24.3534 44.9799 19.2771 44.9799C8.61044 44.9799 0 53.5904 0 64.257C0 74.9237 8.61044 83.5341 19.2771 83.5341C24.3534 83.5341 28.9157 81.5422 32.3855 78.3293L78.1365 105.06C77.8153 106.41 77.6225 107.823 77.6225 109.237C77.6225 119.582 86.0402 128 96.3855 128C106.731 128 115.149 119.582 115.149 109.237C115.149 98.8916 106.731 90.4739 96.3855 90.4739Z"
                fill="white" />
            </svg>
          </div>
        </li> -->
      </ul>
    </div>
    <div id="function-content-box">
      <div id="function-title">
        <div v-if="activeFuncTab === 0">
          <div id="left-corner-fill"></div>
          참가자 수 : {{ participantNumber }}
        </div>
        <div v-if="activeFuncTab === 1">메시지</div>
        <div v-if="activeFuncTab === 2">그라운드 룰</div>
        <div v-if="activeFuncTab === 3">공유</div>
      </div>

      <div v-show="activeFuncTab === 0">
        <ul id="content-user-list">
          <li>
            <div class="content-user-image"></div>
            <div class="content-user-info">
              {{ myUserName }}
              <span id="its-me">나</span>
            </div>
          </li>
          <li
            v-for="(sub, index) in subscribersComputed"
            :key="index">
            <div class="content-user-image"></div>
            <div class="content-user-info">
              {{ JSON.parse(sub.stream.connection.data).clientData }}
            </div>
            <div
              class="ban-user-btn"
              v-if="isHost"
              @click="handleForceDisconnect(sub)">
              <svg
                width="1.1rem"
                height="1.1rem"
                viewBox="0 0 128 116"
                fill="none"
                xmlns="http://www.w3.org/2000/svg">
                <path
                  d="M96 25.6L86.976 34.624L103.488 51.2H38.4V64H103.488L86.976 80.512L96 89.6L128 57.6L96 25.6ZM12.8 12.8H64V0H12.8C5.76 0 0 5.76 0 12.8V102.4C0 109.44 5.76 115.2 12.8 115.2H64V102.4H12.8V12.8Z"
                  fill="black" />
              </svg>
            </div>
          </li>
        </ul>
      </div>
      <!-- 메시지 -->
      <!-- <MessageChat :messages="messages" :session="session" :myUserName="myUserName" v-if="activeFuncTab === 1" /> -->
      <!-- userNo를 Chat에 넘겨줌으로서 채팅마다 userId를 확인할 수가 있음.-->
      <MessageChat
        :messages="messages"
        :session="session"
        :myUserName="myUserName"
        :chatUserNo="userId"
        v-show="activeFuncTab === 1" />
      <!-- 그라운드 룰 -->
      <div
        id="content-rule-box"
        v-show="activeFuncTab === 2">
        <div id="content-rule-title">
          <div>우리의 스터디 룸 코드</div>
          <div>그라운드 룰</div>
        </div>
        <div>{{ roomRule }}</div>
      </div>
      <!-- 공유 -->
      <div
        id="content-share-box"
        v-show="activeFuncTab === 3"></div>
    </div>
    <!-- 참여 멤버 -->

    <div id="device-div">
      <!-- 캠활성화, 음소거 버튼 -->
      <div
        id="get-out"
        @click="handleLeaveSession">
        <svg
          width="20px"
          height="20px"
          viewBox="0 0 128 128"
          fill="none"
          xmlns="http://www.w3.org/2000/svg">
          <path
            d="M50.4178 89.5289L60.4444 99.5556L96 64L60.4444 28.4444L50.4178 38.4711L68.7644 56.8889H0V71.1111H68.7644L50.4178 89.5289ZM128 0H0V42.6667H14.2222V14.2222H113.778V113.778H14.2222V85.3333H0V128H128V0Z"
            fill="white" />
        </svg>
      </div>
      <div id="device-toggle-box">
        <div
          id="mic-activated"
          @click="handleMuteBtn"
          v-if="muted">
          <svg
            width="22px"
            height="22px"
            viewBox="0 0 95 128"
            fill="none"
            xmlns="http://www.w3.org/2000/svg">
            <path
              d="M47.1579 80.8421C58.3411 80.8421 67.3011 71.8147 67.3011 60.6316L67.3684 20.2105C67.3684 9.02737 58.3411 0 47.1579 0C35.9747 0 26.9474 9.02737 26.9474 20.2105V60.6316C26.9474 71.8147 35.9747 80.8421 47.1579 80.8421ZM39.0737 19.5368C39.0737 15.0905 42.7116 11.4526 47.1579 11.4526C51.6042 11.4526 55.2421 15.0905 55.2421 19.5368L55.1747 61.3053C55.1747 65.7516 51.6042 69.3895 47.1579 69.3895C42.7116 69.3895 39.0737 65.7516 39.0737 61.3053V19.5368ZM82.8632 60.6316C82.8632 80.8421 65.7516 94.9895 47.1579 94.9895C28.5642 94.9895 11.4526 80.8421 11.4526 60.6316H0C0 83.6042 18.3242 102.602 40.4211 105.903V128H53.8947V105.903C75.9916 102.669 94.3158 83.6716 94.3158 60.6316H82.8632Z"
              fill="black" />
          </svg>
        </div>
        <div
          id="mic-inactivated"
          @click="handleMuteBtn"
          v-if="!muted">
          <svg
            width="22px"
            height="22px"
            viewBox="0 0 95 128"
            fill="none"
            xmlns="http://www.w3.org/2000/svg">
            <path
              d="M47.1583 80.8421C58.3414 80.8421 67.3688 71.8147 67.3688 60.6316V20.2105C67.3688 9.02737 58.3414 0 47.1583 0C35.9751 0 26.9478 9.02737 26.9478 20.2105V60.6316C26.9478 71.8147 35.9751 80.8421 47.1583 80.8421Z"
              fill="white" />
            <path
              d="M80.8421 60.6318C80.8421 79.2255 65.7516 94.316 47.1579 94.316C28.5642 94.316 13.4737 79.2255 13.4737 60.6318H0C0 84.4129 17.5832 103.95 40.4211 107.251V128H53.8947V107.251C76.7326 103.95 94.3158 84.4129 94.3158 60.6318H80.8421Z"
              fill="white" />
          </svg>
        </div>
        <div
          id="cam-activated"
          @click="handleCameraBtn"
          v-if="cameraOff">
          <svg
            width="20px"
            height="20px"
            viewBox="0 0 128 128"
            fill="none"
            xmlns="http://www.w3.org/2000/svg">
            <path
              d="M127.064 98.6416V31.0303L100.313 57.7806V27.6865H56.1087L127.064 98.6416ZM9.42947 0L0 9.42947L18.2571 27.6865H6.68757V107.937H98.5078L118.571 128L128 118.571L9.42947 0Z"
              fill="white" />
          </svg>
        </div>
        <div
          id="cam-inactivated"
          @click="handleCameraBtn"
          v-if="!cameraOff">
          <svg
            width="19px"
            height="19px"
            viewBox="0 0 128 86"
            fill="none"
            xmlns="http://www.w3.org/2000/svg">
            <path
              d="M99.5556 32.4443V7.55545C99.5556 3.64434 96.3556 0.444336 92.4444 0.444336H7.11111C3.2 0.444336 0 3.64434 0 7.55545V78.6666C0 82.5777 3.2 85.7777 7.11111 85.7777H92.4444C96.3556 85.7777 99.5556 82.5777 99.5556 78.6666V53.7777L128 82.2221V3.99989L99.5556 32.4443Z"
              fill="black" />
          </svg>
        </div>
      </div>

      <!-- <button @click="checkConnection(roomId)">체크커넥션</button> -->
      <!-- 캠,오디오 선택 옵션 -->
      <div id="profile-div">
        <div id="profile-image"></div>
        <div id="profile-name">{{ userInfo.username }}</div>
        <div id="profile-email-name">{{ userInfo.email }}</div>
        <div
          class="profile-toggle-button"
          @click="deviceSettingToggle"
          v-if="!deviceToggled">
          <svg
            width="1.4rem"
            height="1.4rem"
            viewBox="0 0 128 128"
            fill="none"
            xmlns="http://www.w3.org/2000/svg">
            <path
              d="M40.7273 61.0909C40.7273 56.2618 36.8291 52.3636 32 52.3636C27.1709 52.3636 23.2727 56.2618 23.2727 61.0909C23.2727 65.92 27.1709 69.8182 32 69.8182C36.8291 69.8182 40.7273 65.92 40.7273 61.0909ZM81.4545 32C81.4545 27.1709 77.5564 23.2727 72.7273 23.2727H55.2727C50.4436 23.2727 46.5455 27.1709 46.5455 32C46.5455 36.8291 50.4436 40.7273 55.2727 40.7273H72.7273C77.5564 40.7273 81.4545 36.8291 81.4545 32ZM43.6364 81.4545C38.8073 81.4545 34.9091 85.3527 34.9091 90.1818C34.9091 95.0109 38.8073 98.9091 43.6364 98.9091C48.4655 98.9091 52.3636 95.0109 52.3636 90.1818C52.3636 85.3527 48.4655 81.4545 43.6364 81.4545ZM64 0C28.6836 0 0 28.6836 0 64C0 99.3164 28.6836 128 64 128C99.3164 128 128 99.3164 128 64C128 28.6836 99.3164 0 64 0ZM64 116.364C35.1418 116.364 11.6364 92.8582 11.6364 64C11.6364 35.1418 35.1418 11.6364 64 11.6364C92.8582 11.6364 116.364 35.1418 116.364 64C116.364 92.8582 92.8582 116.364 64 116.364ZM96 52.3636C91.1709 52.3636 87.2727 56.2618 87.2727 61.0909C87.2727 65.92 91.1709 69.8182 96 69.8182C100.829 69.8182 104.727 65.92 104.727 61.0909C104.727 56.2618 100.829 52.3636 96 52.3636ZM84.3636 81.4545C79.5345 81.4545 75.6364 85.3527 75.6364 90.1818C75.6364 95.0109 79.5345 98.9091 84.3636 98.9091C89.1927 98.9091 93.0909 95.0109 93.0909 90.1818C93.0909 85.3527 89.1927 81.4545 84.3636 81.4545Z"
              fill="black" />
          </svg>
        </div>
        <div
          class="profile-toggle-button"
          @click="deviceSettingToggle"
          v-if="deviceToggled">
          <svg
            width="1.4rem"
            height="1.4rem"
            viewBox="0 0 128 106"
            fill="none"
            xmlns="http://www.w3.org/2000/svg">
            <path
              d="M105.44 15.0933L112.907 22.56L44.96 90.5067L15.0933 60.64L22.56 53.1733L44.96 75.5733L105.44 15.0933ZM105.44 0L44.96 60.48L22.56 38.08L0 60.64L44.96 105.6L128 22.56L105.44 0Z"
              fill="black" />
          </svg>
        </div>
        <div id="profile-device-setting">
          <select
            id="profile-device-cam"
            name="cameras"
            @change="handleCameraChange">
            <option disabled>사용할 카메라를 선택하세요</option>
            <!-- <option value="screenShare">스크린 공유</option> -->
          </select>
          <select
            id="profile-device-mic"
            name="audios"
            @change="handleAudioChange">
            <option disabled>사용할 마이크를 선택하세요</option>
          </select>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
#session-top {
  position: fixed;
  top: 12px;
  right: 20px;

  z-index: 2;

  color: var(--hl-light80);
  font-family: 'ASDGothicM';
  font-size: 1rem;

  vertical-align: middle;
}

#session-title {
  font-family: 'ASDGothicH';
  font-size: 1.6rem;
}
#session {
  position: fixed;
  left: 0px;
  top: 55px;

  display: flex;
  width: calc(100vw - 300px);
  height: calc(100vh - 55px);
  min-height: 545px;
}

#cam-screen {
  /* display: flex;
    flex-wrap: wrap; */
  height: 95%;
}

#video-container {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translateX(-50%) translateY(-50%);

  width: 100%;

  display: flex;
  flex-wrap: wrap;

  /* align-items: center; */
  align-content: center;
  justify-content: center;

  overflow: hidden;
}

#video-container:hover {
  ::v-deep .video-user-name {
    opacity: 1;
  }
}

#video-container::-webkit-scrollbar {
  width: 10px;
}

#video-container::-webkit-scrollbar-thumb {
  background: #a0a0a0; /* 스크롤바 색상 */
  border-radius: 3px;
}

#video-container::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0); /*스크롤바 뒷 배경 색상*/
}

#video-container::-webkit-scrollbar-button {
  height: 6px;
}

#side-function-box {
  position: fixed;
  top: 55px;
  left: calc(100vw - 300px);

  width: 300px;
  height: calc(100vh - 55px);
  min-height: 545px;

  background-color: var(--background-window);
}

#function-tabs {
  position: absolute;
  top: 10px;
  left: 0px;

  width: 280px;
  height: 30px;
}

#function-ui {
  position: absolute;
  top: 0px;
  left: 20px;

  width: 280px;

  margin: 0px;
  padding: 0px;

  display: block;
  list-style: none;
  cursor: pointer;
}

.function-tab {
  position: relative;
  width: 80px;

  margin-left: -20px;
}

.function-tab > div {
  float: left;

  width: 80px;
  height: 30px;
}

.tab-activated {
  float: left;

  width: 60px;
  height: 30px;
}

.tab-left-corner {
  position: absolute;
  bottom: 0px;
  left: 0px;

  width: 10px;
  height: 10px;

  overflow: hidden;

  background-color: var(--hl-light);
}

.tab-left-corner > div {
  position: absolute;
  top: -10px;
  left: -10px;

  width: 20px;
  height: 20px;

  border-radius: 10px;

  background-color: var(--background-window);
}

.tab-inner-box {
  position: absolute;
  top: 0px;
  left: 10px;
  width: 60px;
  height: 30px;

  display: flex;
  justify-content: center;
  align-items: center;

  background-color: var(--hl-light);

  border-radius: 10px 10px 0px 0px;
}

.tab-right-corner {
  position: absolute;
  bottom: 0px;
  right: 0px;

  width: 10px;
  height: 10px;

  background-color: var(--hl-light);
}

.tab-right-corner > div {
  position: absolute;
  top: -10px;
  right: -10px;

  width: 20px;
  height: 20px;

  border-radius: 10px;

  background-color: var(--background-window);
}

.tab-inner-box > svg > path {
  fill: var(--font100);
}

.tab-inactivated {
  float: left;

  width: 80px;
  height: 30px;

  display: flex;
  justify-content: center;
  align-items: center;
}

.tab-inactivated > svg > path {
  fill: var(--hl-light);
}

#function-content-box {
  position: absolute;
  top: 40px;
  left: 10px;
  width: 280px;

  background-color: var(--hl-light);

  border-radius: 10px;
}

#left-corner-fill {
  position: absolute;
  top: 0px;
  left: 0px;

  width: 11px;
  height: 11px;

  background-color: var(--hl-light);
}

#function-title > div {
  text-align: center;
  padding-top: 10px;

  color: var(--font50);
  font-family: 'ASDGothicM';
  font-weight: 700;
}

#device-toggle-box {
  position: absolute;
  top: 40px;
  left: 10px;

  width: 80px;
  height: 39px;

  border-radius: 20px;
  cursor: pointer;

  box-shadow: 0 3px 3px rgba(0, 0, 0, 0.16), 0 3px 3px rgba(0, 0, 0, 0.23);
}

#its-me {
  color: var(--font50);
}

#content-user-list {
  list-style: none;
  padding: 0px;
  padding-left: 10px;

  max-height: calc(100vh - 360px);

  overflow-x: hidden;
  overflow-y: scroll;
}

#content-user-list::-webkit-scrollbar {
  width: 10px;
  border-left: 1px solid var(--hl-light30);
}

#content-user-list::-webkit-scrollbar-thumb {
  background: #a0a0a0; /* 스크롤바 색상 */
  border-radius: 3px;
}

#content-user-list::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0); /*스크롤바 뒷 배경 색상*/
}

#content-user-list::-webkit-scrollbar-button {
  height: 6px;
}

#content-user-list > li {
  position: relative;
  width: 260px;
  height: 40px;
}

.content-user-image {
  position: absolute;
  top: 5px;
  left: 0px;

  width: 60px;
  height: 30px;

  background-size: cover;
  background-position: center;

  border-radius: 15px;
}

.content-user-info {
  position: absolute;
  top: calc(20px - 0.6rem);
  left: 70px;
}

.ban-user-btn {
  position: absolute;
  top: calc(20px - 0.6rem);
  right: 10px;

  cursor: pointer;
}

.ban-user-btn > svg > path {
  fill: var(--font80);
}

#content-rule-box {
  max-height: calc(100vh - 360px);
}

#content-rule-title {
  position: relative;
  width: 260px;
  height: 100px;

  top: 26px;
  left: 10px;

  border-left: 5px solid var(--font30);

  display: table-cell;
  vertical-align: middle;
}

#content-rule-title > div:first-child {
  font-size: 1.4rem;
  font-family: 'ASDGothicM';
  margin-left: 10px;
}

#content-rule-title > div:nth-child(2) {
  font-size: 1.4rem;
  font-family: 'ASDGothicH';
  margin-left: 10px;
}

#content-rule-box > div:nth-child(2) {
  margin-top: 40px;
  margin-left: 10px;
  margin-bottom: 15px;
}

/* #content-share-box {
  height: 200px;
} */

#mic-activated {
  position: absolute;
  top: 0px;
  left: 0px;

  width: 40px;
  height: 40px;

  background-color: #b2b2b2;

  border-radius: 20px 0px 0px 20px;
}

#mic-activated > svg {
  position: absolute;
  top: 9px;
  left: 11px;
}

#mic-inactivated {
  position: absolute;
  top: 0px;
  left: 0px;

  width: 40px;
  height: 40px;

  background-color: var(--hl-green);

  border-radius: 20px 0px 0px 20px;
}

#mic-inactivated > svg {
  position: absolute;
  top: 9px;
  left: 11px;
}

#cam-activated {
  position: absolute;
  top: 0px;
  left: 40px;

  width: 40px;
  height: 40px;

  background-color: #5c5c5c;

  border-radius: 0px 20px 20px 0px;
}

#cam-activated > svg {
  position: absolute;
  top: 9px;
  left: 8px;
}

#cam-inactivated {
  position: absolute;
  top: 0px;
  left: 40px;

  width: 40px;
  height: 40px;

  background-color: var(--hl-pres);

  border-radius: 0px 20px 20px 0px;
}

#cam-inactivated > svg {
  position: absolute;
  top: 10px;
  left: 9px;
}

#get-out {
  position: absolute;
  top: 40px;
  right: 10px;

  width: 40px;
  height: 40px;

  display: flex;
  justify-content: center;
  align-items: center;

  background-color: var(--hl-warn);
  border-radius: 20px;

  box-shadow: 0 3px 3px rgba(0, 0, 0, 0.1), 0 3px 3px rgba(0, 0, 0, 0.16);

  cursor: pointer;
}

#device-div {
  position: absolute;
  display: relative;
  right: 0px;
  bottom: 0px;

  width: 300px;
  height: 150px;
}

#profile-div {
  position: absolute;
  right: 0px;
  bottom: -90px;

  width: 300px;
  height: 150px;

  background-color: var(--hl-light);

  box-shadow: 0 -2px 3px rgba(0, 0, 0, 0.1);

  border-radius: 10px 10px 0px 0px;
}

#profile-image {
  position: absolute;
  top: 10px;
  left: 10px;

  width: 80px;
  height: 40px;

  background-image: url('/testProfile.png');
  background-size: cover;
  background-position: center;

  box-shadow: 0 2px 3px rgba(0, 0, 0, 0.1), 0 2px 3px rgba(0, 0, 0, 0.16);

  border-radius: 20px;
}

#profile-name {
  position: absolute;
  top: 10px;
  left: 100px;

  color: var(--font100);
  font-family: 'ASDGothicM';
  font-size: 1.1rem;
  font-weight: 700;
}

#profile-email-name {
  position: absolute;
  top: calc(30px + 0.1rem);
  left: 100px;

  color: var(--font50);
  font-family: 'ASDGothicM';
  font-size: 0.9rem;
}

.profile-toggle-button {
  position: absolute;
  top: calc(30px - 0.7rem);
  right: calc(30px - 0.7rem);

  cursor: pointer;
}

.profile-toggle-button > svg > path {
  fill: var(--font80);
}

#profile-device-setting {
  position: absolute;
  left: 10px;
  bottom: 10px;
}

#profile-device-cam {
  position: absolute;
  left: 0px;
  bottom: 37px;

  width: 280px;
  height: 33px;

  border-radius: 10px;

  border: 1px solid var(--font30);
}

#profile-device-mic {
  position: absolute;
  left: 0px;
  bottom: 0px;

  width: 280px;
  height: 33px;

  border-radius: 10px;
  border: 1px solid var(--font30);
}
</style>
