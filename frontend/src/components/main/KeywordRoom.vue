<script setup>
import { ref, computed, watch, onMounted, onBeforeMount } from 'vue'
import { useRouter } from 'vue-router'
import { usewebRtcStore } from '@/stores'
import axios from 'axios'

const router = useRouter()
const webRtcStore = usewebRtcStore()

// const roomList = computed(() => webRtcStore.roomList)
const roomList = ref(webRtcStore.roomList)

onBeforeMount(() => {}),
  onMounted(() => {
    webRtcStore.getRtcRooms()
  })

// watch(() => webRtcStore.roomList, (newRoomList, oldRoomList) => {
//   if (newRoomList) {
//     console.log('흐으음')
//     console.log(roomList.value)
//   }
// })
</script>

<template>
  <div style="display: flex">
    <div
      v-for="room in roomList"
      :key="room.id"
      class="card"
      style="margin: 10px">
      <div
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
          style="width: 100%; height: 100%; background-color: crimson"></div>
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
        <!-- 키워드 확인하기, 현재 키워드 안받으므로 미구현 -->
        <!-- <div v-for="(keyword,i) in room.keywords" :key="i">
          {{ keyword }}
        </div> -->
      </div>
      <div></div>
    </div>
  </div>
</template>

<style scoped></style>
