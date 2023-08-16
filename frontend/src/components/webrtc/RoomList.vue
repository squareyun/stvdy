<script setup>
import { ref, computed, watch, onBeforeMount } from 'vue'
import { usewebRtcStore } from '@/stores'
import { useQuestionStore } from '@/stores'
// import { storeToRefs } from 'pinia';

const webRtcStore = usewebRtcStore()
const questionsStore = useQuestionStore()
// 화상 회의 방과 관련된 것들
// const roomList = ref(webRtcStore.roomList)
const roomList = computed(() => webRtcStore.roomList)
const wholepage = computed(() => Math.ceil(webRtcStore.wholeroomNo / 20)) // 페이지네이션을 위한 작업

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
  await webRtcStore.getRtcRooms() // 페이지 네이션을 위해 전체 방의 갯수를 받기 위해 사용
  await webRtcStore.getsearchRooms() // 20개의 방씩 페이지로 받는중. wholeList로는 이미지path를 받지 못함.
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

// 선택한 방에 참여하는 함수
function joinTheRoom(room) {
  // cconsole.log()
  selectedRoom.value = room

  if (selectedRoomPw.value && selectedRoomPw.value != inputPw.value) {
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
  <div>
    <span class="room-content-title">스터디 룸</span>
    <div id="room-list-area">
      <div
        class="room-list"
        v-for="room in roomList"
        @click="showRoomInfo(room)">
        <div>
          <div
            class="room-card-image"
            :style="`background-image: url(${
              room.imagePath || tmpStudyImagePath
            });
            background-repeat : no-repeat;background-size: cover; background-position: center;`"></div>
          <div class="room-image-filter-top">
            <svg
              width="1.4rem"
              height="1.4rem"
              viewBox="0 0 95 128"
              fill="none"
              xmlns="http://www.w3.org/2000/svg">
              <path
                d="M25.0435 75.1304L0 100.174L8.34783 108.522L27.8261 89.0435H38.9565L25.0435 75.1304ZM61.2174 0C55.0957 0 50.087 5.0087 50.087 11.1304C50.087 17.2522 55.0957 22.2609 61.2174 22.2609C67.3391 22.2609 72.3478 17.2522 72.3478 11.1304C72.3478 5.0087 67.3391 0 61.2174 0ZM94.6087 111.36L77.913 128L61.273 111.249V102.957L21.76 63.4991C20.0348 63.7774 18.3652 63.8887 16.6957 63.8887V51.8678C25.9339 52.0348 36.7861 47.0261 42.6852 40.5148L50.4765 31.8887C51.5339 30.72 52.8696 29.7739 54.3165 29.1061C55.9304 28.327 57.767 27.8261 59.6591 27.8261H59.8261C66.727 27.8261 72.3478 33.447 72.3478 40.3478V89.0435L67.2278 84.4243L47.3043 64.5009V51.8678C43.7983 54.7617 39.3461 57.5443 34.56 59.6035L69.5652 94.6087H77.913L94.6087 111.36Z"
                fill="white" />
            </svg>
            <span>{{ room.currentNumber }} / {{ room.quota }}</span>
          </div>
          <div class="room-list-title">{{ room.title }}</div>
          <div class="room-list-detail">{{ room.rule }}</div>
        </div>
      </div>
    </div>
    <div>
      <div v-for="page in wholepage">
        <button @click="getsearchRooms(`${page - 1}`)">{{ page }}</button>
      </div>
    </div>
    <!-- 방선택시 모달창 -->
    <div
      class="room-join-window"
      v-if="isRoomInfo">
      <!-- <button @click="joinRoom(room)">방 입장</button> -->
      <p>{{ selectedRoom.currentNumber }} / {{ selectedRoom.quota }}명</p>
      <h1>{{ selectedRoom.title }}</h1>
      <h4>{{ selectedRoom.rule }}</h4>
      <div class="room-join-pwd">
        <p
          class="join-field-name"
          v-if="selectedRoomPw">
          &nbsp;&nbsp;키워드 &nbsp;
        </p>
        <input
          v-if="selectedRoomPw"
          class="join-field"
          type="password"
          @input="updatePwInput"
          :value="inputPw" />
      </div>
      <div class="room-join-btn">
        <button @click="joinTheRoom(selectedRoom)">방 입장</button>
        <button @click="hideRoomInfo">창 닫기</button>
      </div>
    </div>
  </div>
</template>

<style>
.room-join-window {
  position: fixed;
  top: 30%;
  left: 50%;
  transform: translatex(-50%);

  background-color: var(--hl-light);
  border: 1px solid #b7b7b3;
  border-radius: 20px;

  box-sizing: border-box;
  box-shadow: 0 2px 3px rgba(0, 0, 0, 0.16), 0 2px 3px rgba(0, 0, 0, 0.23);

  padding: 20px;
}

.room-join-window > h1,
.room-join-window > h4,
.room-join-window > p {
  color: var(--font80);
  margin: 0px;
}

.room-join-window > div {
  margin-top: 60px;
}

.join-field {
  display: block;
  background-color: var(--hl-light);
  margin-top: -12px;
  margin-bottom: 30px;
  padding-left: 18px;

  font-size: 1rem;
  color: var(--font100);

  width: calc(100% - 20px);
  height: 46px;
  border-radius: 10px;
  border: 1px solid var(--font30);
}

.join-field:focus {
  outline: none;
  box-shadow: 0 0 0 1px var(--font80) inset;
}

.join-field-name {
  position: relative;
  display: inline;
  margin-left: 11px;

  /* font-size: 14px; */
  color: var(--font80);
  font-size: 0.9rem;

  text-align: center;

  background-color: var(--hl-light);
}

.room-join-btn {
  position: absolute;
  right: 20px;
  bottom: 20px;
}

.room-join-btn > button {
  border: none;
  background-color: transparent;

  font-family: 'ASDGothicH';
  font-size: 1rem;

  cursor: pointer;
}

.room-join-btn > button:first-child {
  color: var(--hl-pres);
}

.room-card-image {
  width: 208px;
  height: 140px;

  border-radius: 2px;
}

.room-image-filter-top > svg {
  position: relative;
  top: 8px;
  left: 8px;
}

.room-image-filter-top > svg > path {
  fill: #ffffff;
}

.room-image-filter-top > span {
  color: #ffffff;
  position: relative;
  top: 2px;
  left: 8px;
}

.room-image-filter-top {
  position: absolute;
  left: 10px;
  top: 10px;

  width: 208px;
  height: 140px;

  background: linear-gradient(
    180deg,
    #00000055 0%,
    transparent 50%,
    transparent 100%
  );

  opacity: 1;
}

#room-list-area {
  column-count: 4;

  color: var(--hl-light);
}
.room-list {
  position: relative;

  padding: 10px;
  margin-bottom: 20px;

  cursor: pointer;

  -webkit-column-break-inside: avoid;
  page-break-inside: avoid;
  break-inside: avoid;
}

.room-list-title {
  color: var(--hl-light);
  font-size: 1rem;
}

.room-list-detail {
  font-size: 0.8rem;
  font-family: 'ASDGothicUL';
  color: var(--hl-light);

  max-height: 4rem;

  overflow: hidden;
}

#room-list-box {
  width: 960px;
}

.centered-container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
/* .card {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: transparent;
} */
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
