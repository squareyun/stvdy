<script>
export default {
  name: 'UserVideo',
}
</script>

<script setup>
// import { ref, onMounted, computed } from 'vue';
import { computed } from 'vue'
import OvVideo from '@/components/webrtc/OvVideo.vue'

const props = defineProps({
  streamManager: Object,
})

// clientData는 computed로 진행됨
const clientData = computed(() => {
  const { clientData } = getConnectionData()
  return clientData
})

function getConnectionData() {
  const { connection } = props.streamManager.stream
  return JSON.parse(connection.data)
}
</script>

<template>
  <div
    class="user-box"
    v-if="streamManager">
    <OvVideo
      class="video-box"
      :stream-manager="streamManager" />
    <!-- {{ clientData }}이게 내 현재 이름임 -->
    <div class="video-user-name">
      <p>{{ clientData }}</p>
    </div>
  </div>
</template>

<style>
.user-box {
  position: relative;
  width: calc(33% - 4px);
  height: calc(33% - 4px);

  padding: 2px 2px 2px 2px;
}

.video-box {
  width: 100%;
  height: 100%;
  border-radius: 10px;
}

.video-user-name {
  position: absolute;
  left: 0px;
  bottom: 0px;

  opacity: 0;
  transition: opacity 0.2s;
}
</style>
