<script setup>
import SideBar from '@/components/SideBar.vue'
import { ref, computed, watch, onMounted, onBeforeMount } from 'vue'
import { useRouter } from "vue-router"
import { usewebRtcStore } from "@/stores"
import axios from 'axios'

const webRtcStore = usewebRtcStore()
const roomList = ref(webRtcStore.roomList)

const title = ref(null)


onBeforeMount(() => {
  webRtcStore.getRtcRooms()
  roomList.value = webRtcStore.roomList
})

function joinRoom(room) {
  // this.$router.push({ name: 'MovieDetailView', params: { id: moviecard.id }});  
  router.push({
    name:'roomJoin',
    params: { 
      // roomName: encodeURIComponent(mySessionId.value),  // 인코딩해서 보내줘야만 작동함
      roomName: encodeURIComponent(room.roomTitle),  // 인코딩해서 보내줘야만 작동함
    },
  })
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
    <div>상단
        <div>상단 왼쪽 = 인기 키워드</div>
        <div>상단 오른쪽 = (배너)들어갈 자리 </div>
    </div>
    <div>중단= 공개 스터디 룸
      <div v-for="room in roomList" :key="room.id" class="card">
        <p>{{ room.quota }}</p>
        <div>
          <img @click="joinRoom(room)" :src="room.imgPreviewUrl" alt="imgPreview" style="max-width: 300px; max-height: 300px">
        </div>
      </div>
    </div>
    <div>하단= 칼럼</div>
    <!-- <router-view /> -->
  </main>
</template>

<style>
  .card {
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: transparent;
  }
</style>