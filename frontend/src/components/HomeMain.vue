<script setup>
import SideBar from '@/components/SideBar.vue'
import { ref, computed, watch, onMounted, onBeforeMount } from 'vue'
import { useRouter } from 'vue-router'
import { usewebRtcStore, useQuestionStore } from '@/stores'
// import { storeToRefs } from 'pinia';
import axios from 'axios'

const webRtcStore = usewebRtcStore()
const questionsStore = useQuestionStore()
const router = useRouter()

console.log(123)

////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////
// 화상 회의 방과 관련된 것들
// const roomList = ref(webRtcStore.roomList)
const roomList = computed(() => webRtcStore.roomList)
const roomListSample = ref([])

const title = ref(null)
const isRoomInfo = ref(false)
const selectedRoom = ref(null) // 선택한 방의 정보
const selectedRoomPw = ref(null) // 선택한 방의 비밀번호
const inputPw = ref(null) // 선택한 방 입장시 입력하는 비밀번호
const isSeeInputPw = ref(false)
const isHost = ref(false)
const isnotFull = ref(true) // 선택한 방의 입장가능 여부. default는 true

////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////
// const participantNo = ref(0)

// const everyRoomTags = ref([])
////////////////

////////////////
// 질문게시판과 관련한 것.
// const { questions } = storeToRefs(questionsStore);
// const questions = ref(questionsStore.questions);
const questions = computed(() => questionsStore.questions)
const questionsSample = ref([])
////////////////

onBeforeMount(async () => {
  await webRtcStore.getRtcRooms()
  // questionsStore.getAll() // 모든 질문을 가지고 옴.
  await webRtcStore.getEveryRoomTags() // 230808 현재는 제대로 구현되어있지 않음
  extractSomeRooms()
  // extractSomeQuestions()
  webRtcStore.notIsHost()
})

onMounted(() => {})

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

// // 질문 글 몇개를 추출 하는 함수
function extractSomeQuestions() {
  const tmpQuestionsSample = []
  if (questions.value.length > 3) {
    const usedIndexes = []
    for (let i = 0; i < 3; i++) {
      let randomNum
      do {
        randomNum = Math.floor(Math.random() * questions.value.length)
      } while (usedIndexes.includes(randomNum))

      usedIndexes.push(randomNum)
      tmpQuestionsSample.push(questions.value[randomNum])
    }

    questionsSample.value = tmpQuestionsSample
  } else {
    questionsSample.value = questions.value
  }
}
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////

// // 방 몇 개를 추출 하는 함수
function extractSomeRooms() {
  const tmpRoomList = []
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
  if (selectedRoomPw.value != inputPw.value) {
    // 비밀번호 불일치시
    return
  }
  console.log('되긴되나', room)

  webRtcStore.joinTheRoom(room)
}

/// 방 입장 전 방 정보 모달 창 켜기
function showRoomInfo(room) {
  console.log('해당 방의 정보', room)
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
// // 이거 이미지 불러오려고 했는데 라이프사이클 바궈야 할 듯
// onBeforeMount(()=>{
//   readInputImage()
// })

// // 이미지 업로드 및 미리보기 함수
// function readInputImage(event){
//   // console.log(event.target.files[0])
//   store.updateBackImg(event.target.files[0])
//   backImgFile.value = event.target.files[0]

//   // 이미지 파일을 데이터 Url로 변환하기
//   const reader = new FileReader()
//   reader.onload = (event) => {
//     imgPreviewUrl.value = event.target.result
//   }
//   reader.readAsDataURL(backImgFile.value)
// }

//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
// 질문글의 내용을 일부만 보여주기 위한 함수
function limitQesCon(str, maxLength) {
  return str.length > maxLength ? str.slice(0, maxLength - 3) + '...' : str
}

// 해당 질문글을 보러 가는 글
function goThisQuestion(question) {
  console.log('질문정보', question)
  router.push({
    name: 'qtndetail',
    params: {
      // roomName: encodeURIComponent(question.id),
      roomName: question.id,
    },
  })
}
</script>

<template>
  <main style="color: white">
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!-- <TheWelcome /> -->
    <!-- <SideBar /> -->
    <!-- 방 세부 정보 확인 모달 창 -->
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
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->

    <!-- 주 화면 -->
    <div style="margin-top: 30px; margin-left: 100px">
      <!-- 임시로 마진값을 줘서 띄움 -->
      <p>상단</p>
      <div>
        상단 왼쪽 = 인기 키워드
        <div
          v-for="(tag, i) in everyRoomTags"
          :key="i">
          {{ tag }}
        </div>
        <div>상단 오른쪽 = (배너)들어갈 자리</div>
      </div>
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <p>중단= 공개 스터디 룸</p>
      <div style="display: flex">
        <!-- <div v-for="room in roomList" :key="room.id" class="card" style="margin: 10px;"> -->
        <div
          v-for="room in roomListSample"
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
            <img
              v-if="room.imgPreviewUrl"
              :src="room.imgPreviewUrl"
              alt="imgPreview"
              style="max-width: 100%; max-height: 100%" />
            <div
              v-else
              style="
                width: 100%;
                height: 100%;
                background-color: crimson;
              "></div>
            <div
              style="
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                display: flex;
                justify-content: center;
                align-items: center;
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
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <!--  -->
      <div style="margin: 50px"></div>
      <!-- 임시로 마진값을 줘서 띄움 -->
      <p>하단= 칼럼 => 질문글 몇 개</p>
      <div style="display: flex">
        <div
          v-for="question in questionsSample"
          :key="question.id"
          style="border-color: white; border: 1px solid white">
          <!-- <p>{{ question.id }}</p> -->
          <h3 @click="goThisQuestion(question)">{{ question.title }}</h3>
          <p>{{ limitQesCon(question.detail, 100) }}</p>
          <!-- <p>{{ question.regist_time }}</p> -->
        </div>
      </div>
    </div>

    <!-- <router-view /> -->
  </main>
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
