<script setup>
import { ref, computed, watch, onMounted, onBeforeMount } from 'vue'
import { useRouter } from 'vue-router'
import { usewebRtcStore } from '@/stores'
import { useQuestionStore } from '@/stores'
// import { storeToRefs } from 'pinia';

const webRtcStore = usewebRtcStore()
const questionsStore = useQuestionStore()
const router = useRouter()
// 화상 회의 방과 관련된 것들
// const roomList = ref(webRtcStore.roomList)
const roomList = computed(() => webRtcStore.roomList)
const roomListSample = ref([])  // 현재 사용X
const wholepage = computed(() => Math.ceil(webRtcStore.wholeroomNo/20)) // 페이지네이션을 위한 작업

const title = ref(null)
const isRoomInfo = ref(false)
const selectedRoom = ref(null) // 선택한 방의 정보
const selectedRoomPw = ref(null) // 선택한 방의 비밀번호
const inputPw = ref(null) // 선택한 방 입장시 입력하는 비밀번호
const isSeeInputPw = ref(false)
const isHost = ref(false)
const isnotFull = ref(true) // 선택한 방의 입장가능 여부. default는 true
let tmpStudyImagePath = ref('/testBackground.png')

onBeforeMount(async () => {
  await webRtcStore.getRtcRooms()     // 페이지 네이션을 위해 전체 방의 갯수를 받기 위해 사용
  await webRtcStore.getsearchRooms()  // 20개의 방씩 페이지로 받는중. wholeList로는 이미지path를 받지 못함.
  extractSomeRooms()
  webRtcStore.notIsHost()
  
})

watch(
  () => webRtcStore.roomList,
  (newRoomList, oldRoomList) => {
    if (newRoomList) {
    }
  },
)
watch(
  () => questionsStore.questions,
  (newRoomList, oldRoomList) => {
    if (newRoomList) {
    }
  },
)

/// CSS 적용을 위한 임시 함수
// async function tmpGoRoom() {
//   setTimeout(() => {
//     console.log(roomList.value)
//     console.log(roomList.value[roomList.value.length - 1])
//     // joinTheRoom(roomList.value[roomList.value.length - 1])
//     tmpJoin(roomList.value[roomList.value.length - 1])
//   }, 2000)
// }
// function tmpJoin(room) {
//   console.log('되긴되나', room)
//   webRtcStore.joinTheRoom(room)
// }

////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////
// button 클릭시 사용할 수 있도록. getsearchRooms 정의해줌.
function getsearchRooms(pageNo = 0, keyword = '', size = 20) {
  webRtcStore.getsearchRooms(pageNo, keyword, size)
}

// // 방 몇 개를 추출 하는 함수 // 현재사용X
function extractSomeRooms() {
  const tmpRoomList = []
  console.log('이거',roomList.value)
  if (roomList.value.length > 3) {
    const usedIndexes = []
    for (let i = 0; i < 3; i++) {
      let randomNum
      do {
        randomNum = Math.floor(Math.random() * roomList.value.length)
      } while (usedIndexes.includes(randomNum))

      usedIndexes.push(randomNum)
      tmpRoomList.push(roomList.value[randomNum])
    }
    roomListSample.value = tmpRoomList
  } else {
    roomListSample.value = roomList.value
  }
}

// 선택한 방에 참여하는 함수
function joinTheRoom(room) {
  // cconsole.log()
  selectedRoom.value = room
  if (selectedRoomPw.value != inputPw.value) {
    // 비밀번호 불일치시
    alert('비밀번호가 불일치합니다.')
    return
  }
  /////////////////////////////////////
  // 이거 추가했음. 나중에 home에서 추가해야함.
  if (selectedRoom.value.currentNumber >= selectedRoom.value.quota) {
    alert('정원초과로 입장할 수 없습니다.')
    return
  }
  webRtcStore.joinTheRoom(room)
}

/// 방 입장 전 방 정보 모달 창 켜기
function showRoomInfo(room) {
  isRoomInfo.value = true
  selectedRoom.value = room
  selectedRoomPw.value = room.password
  console.log('해당 방의 정보', selectedRoom.value)
}
/// 방 입장 전 방 정보 모달 창 닫기
function hideRoomInfo() {
  isRoomInfo.value = false
  webRtcStore.updatePwInput(null) // 정보창을 닫을 때 오류 방지를 위해 store내 password null로 초기화
}

// 방 입장 전 비밀번호 입력
function updatePwInput(event) {
  webRtcStore.updatePwInput(event.target.value) // store에 비밀번호를 입력해놓기 위함.
  inputPw.value = event.target.value // 입력한 비밀번호를 동기화
  console.log(inputPw.value)
}

// 방 모달 창에서의 비밀번호 보기 여부
function updateIsSeeInputPw(event) {
  isSeeInputPw.value = event.target.checked
}


// // 질문글의 내용을 일부만 보여주기 위한 함수
// function limitQesCon(str, maxLength) {
//   return str.length > maxLength ? str.slice(0, maxLength - 3) + '...' : str
// }

// // 해당 질문글을 보러 가는 글
// function goThisQuestion(question) {
//   console.log('질문정보', question)
//   router.push({
//     name: 'qtndetail',
//     params: {
//       // roomName: encodeURIComponent(question.id),
//       roomName: question.id,
//     },
//   })
// }
</script>

<template>
  <!-- 주 화면 -->
  <div style="margin-top: 30px; margin-left: 100px">
    <!-- 임시로 마진값을 줘서 띄움 -->
    <p>상단</p>
    <div>
      상단 왼쪽 = 인기 키워드
      <!-- <div v-for="(tag,i) in everyRoomTags" :key="i">
            {{ tag }}
          </div> -->
      <div>상단 오른쪽 = (배너)들어갈 자리</div>
    </div>

    <p>중단= 공개 스터디 룸</p>
    <!-- <div style="display: flex"> -->
    <div>
      <!-- <div v-for="room in roomList" :key="room.id" class="card" style="margin: 10px;"> -->
      <div
        v-for="room in roomList"
        :key="room.id"
        class="card"
        style="margin: 10px">
        <div
          v-if="isnotFull"
          style="
            position: relative;
            display: inline-block;
            width: 300px;
            height: 300px;
          "
          @click="showRoomInfo(room)">
          <div
            :style="`width: 100%; height: 100%; background-image: url(${room.imagePath || tmpStudyImagePath});
            background-repeat : no-repeat;background-size: cover; background-position: center;` "></div>
          <div
            style="
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                display: flex;
                justify-content: center;
                align-items: center;s
              ">
            {{ room.title }}
          </div>
          <div>
            <span>정원: {{ room.quota }}</span>
            <span>방 id : {{ room.id }}</span>
          </div>
        </div>
        <div></div>
      </div>
    </div>
    <!-- 페이지네이션 관련 내용으로 구현하였음. -->
    <div>
      <div v-for="page in wholepage">
        <button @click="getsearchRooms(`${page-1}`)">{{page}}</button>
      </div>
    </div>
    <!-- 방선택시 모달창 -->
    <div
      class="whiteBg centered-container"
      v-if="isRoomInfo"
      style="color: black">
      <!-- <button @click="joinRoom(room)">방 입장</button> -->
      <h1>{{ selectedRoom.title }}</h1>
      <h4>{{ selectedRoom.rule }}</h4>
      <!-- 현재 명 수는 room 에서 받아와야함. -->
      <p>{{ selectedRoom.currentNumber }} 명 / {{ selectedRoom.quota }}명</p>
      <input
        v-if="selectedRoomPw && !isSeeInputPw"
        type="password"
        @input="updatePwInput"
        :value="inputPw" />
      <input
        v-if="selectedRoomPw && isSeeInputPw"
        type="text"
        @input="updatePwInput"
        :value="inputPw" />
      <span v-if="selectedRoomPw">비밀번호 보기</span>
      <input
        v-if="selectedRoomPw"
        type="checkbox"
        :checked="isSeeInputPw"
        @change="updateIsSeeInputPw" />
      <button @click="joinTheRoom(selectedRoom)">방 입장</button>
      <button @click="hideRoomInfo">창 닫기</button>
    </div>
  </div>
</template>

<style>
.centered-container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.card {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: transparent;
}
.blackBg {
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0);
  position: fixed;
  padding: 20px;
}
.whiteBg {
  width: 80%;
  background: white;
  border-radius: 8px;
  padding: 20px;
  /* display: flex; */
  justify-content: center;
}
</style>
