<script setup>
import SideBar from '@/components/SideBar.vue'
import { ref, computed, watch, onMounted, onBeforeMount } from 'vue'
import { useRouter } from "vue-router"
import { usewebRtcStore } from "@/stores"
import axios from 'axios'

const webRtcStore = usewebRtcStore()
const roomList = ref([])


async function getRtcRooms() {
  try{
    const response = axios.get('http://localhost:8080/rooms/list/')
    roomList.value = (await response).data
  }
  catch(error){
    console.error('방 리스트 받아오는 오류 발생: ', error)
  }
  
  
}

onBeforeMount(() => {
  getRtcRooms()
})


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
        
    </div>
    <div>하단= 칼럼</div>
    <!-- <router-view /> -->
  </main>
</template>
