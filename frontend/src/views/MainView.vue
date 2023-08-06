<script setup>
import SideBar from '@/components/SideBar.vue'
import { ref, computed, watch, onMounted, onBeforeMount } from 'vue'
import { useRouter } from "vue-router"
import { usewebRtcStore } from "@/stores"
import axios from 'axios'

const webRtcStore = usewebRtcStore()
// const roomList = ref(webRtcStore.roomList)
const router = useRouter();

const title = ref(null)
const isRoomInfo = ref(false)
const selectedRoom = ref(null)    // 선택한 방의 정보
const selectedRoomPw = ref(null)  // 선택한 방의 비밀번호
const inputPw = ref(null)         // 선택한 방 입장시 입력하는 비밀번호
const isSeeInputPw = ref(false)
// onBeforeMount(() => {
//   webRtcStore.getRtcRooms()
// })
onMounted(() => {
  webRtcStore.getRtcRooms()
})

const roomList = computed(() => webRtcStore.roomList)
// const selectedRoomComputed = computed(() => {
//   selectedRoom.value
// })

watch(() => webRtcStore.roomList, (newRoomList, oldRoomList) => {
  if (newRoomList) {
    console.log('흐으음')
    console.log(roomList.value)
  }
})


// function joinRoom(room) {
function joinTheRoom(room) {
  if(selectedRoomPw.value != inputPw.value){   // 비밀번호 불일치시
    alert(selectedRoomPw.value+'이거랑'+inputPw.value,'ㅇㅋ비밀번호가 일치하지 않습니다.')
    return
  } 
  console.log('되긴되나',room)

  webRtcStore.joinTheRoom(room)

}

/// 방 입장 전 방 정보 모달 창 켜기
function showRoomInfo(room) {
  console.log(room)
  isRoomInfo.value = true
  selectedRoom.value = room
  selectedRoomPw.value = room.password
  console.log(selectedRoom.value)
}
/// 방 입장 전 방 정보 모달 창 닫기
function hideRoomInfo() {
  isRoomInfo.value = false
  webRtcStore.updatePwInput(null) // 정보창을 닫을 때 오류 방지를 위해 store내 password null로 초기화
}

// 방 입장 전 비밀번호 입력
function updatePwInput(event) {
  webRtcStore.updatePwInput(event.target.value)   // store에 비밀번호를 입력해놓기 위함.
  inputPw.value = event.target.value        // 입력한 비밀번호를 동기화
  console.log(inputPw.value)
}

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

</script>

<template>
  <main style="color: white;">
    <!-- <TheWelcome /> -->
    <SideBar />
    <!-- 방 세부 정보 확인 모달 창 -->
    <div class="whiteBg centered-container" v-if="isRoomInfo" style="color:black;">
      <!-- <button @click="joinRoom(room)">방 입장</button> -->
      <h1>{{selectedRoom.title}}</h1>
      <h4>{{ selectedRoom.rule }}</h4>
      <p> 명 / {{ selectedRoom.quota }}명</p>
      <input v-if="selectedRoomPw && !isSeeInputPw" type="password" @input="updatePwInput" :value="inputPw">
      <input v-if="selectedRoomPw && isSeeInputPw" type="text" @input="updatePwInput" :value="inputPw">
      <span v-if="selectedRoomPw">비밀번호 보기</span>
      <input v-if="selectedRoomPw" type="checkbox" :checked="isSeeInputPw" @change="updateIsSeeInputPw">
      <button @click="joinTheRoom(selectedRoom)">방 입장</button>
      <button @click="hideRoomInfo">창 닫기</button>
    </div>
    <!-- 주 화면 -->
    <div style="margin-top: 20px; margin-left: 250px;"> <!-- 임시로 마진값을 줘서 띄움 -->
      <p>상단</p>
      <div>
          <div>상단 왼쪽 = 인기 키워드</div>
          <div>상단 오른쪽 = (배너)들어갈 자리 </div>
      </div>
      <p>중단= 공개 스터디 룸</p>
      <div style="display:flex">
        <div v-for="room in roomList" :key="room.id" class="card" style="margin: 10px;">
          <div style="position: relative; display: inline-block; width: 300px; height: 300px;" @click="showRoomInfo(room)">
            <img v-if="room.imgPreviewUrl" :src="room.imgPreviewUrl" alt="imgPreview" style="max-width: 100%; max-height: 100%;">
            <div v-else style="width: 100%; height: 100%; background-color: crimson;"></div>
            <div style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; display: flex; justify-content: center; align-items: center;">{{ room.title }}</div>
            <p>정원: {{ room.quota }}</p>
          </div>
          <div>
          </div>
        </div>
      </div>
      <div style="margin:50px;"></div>      <!-- 임시로 마진값을 줘서 띄움 -->
      <p>하단= 칼럼</p>
      <div></div>
    </div>

    <!-- 어떤 방을 누르면 발생할 모달 창 같은 녀석 -->
    <div>
      
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
  .blackBg{
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0);
    position: fixed;
    padding: 20px;
  }
  .whiteBg{
    width: 80%;
    background: white;
    border-radius: 8px;
    padding: 20px;
    /* display: flex; */
    justify-content: center;
  }
</style>