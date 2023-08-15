<script setup>
import SideBar from '@/components/SideBar.vue'
import TopSearch from '@/components/TopSearch.vue'
import RoomAdd from '@/components/webrtc/RoomAdd.vue'
import AiChat from '@/components/main/AiAssist.vue'

import { useUserStore } from '@/stores'
import { computed } from 'vue'

const userStore = useUserStore()
const user = computed(() => userStore.user)
</script>

<template>
  <SideBar />
  <TopSearch id="top-items" />
  <RoomAdd
    v-if="user.id"
    id="room-add" />
  <AiChat id="ai-assist" />
  <main>
    <!-- <TheWelcome /> -->
    <router-view id="question-contents" />
  </main>
</template>

<style>
#top-items {
  z-index: 100;
}

#room-add {
  z-index: 101;
}

#ai-assist {
  z-index: 102;
}

#question-contents {
  position: absolute;
  top: 120px;
  left: calc(50vw - 480px);
  width: 960px;

  color: var(--hl-light);

  /* background-color: rgba(170, 170, 170, 0.123); */
}

@media (max-width: 1360px) {
  #question-contents {
    left: 200px;
  }
}

.question-content-title {
  margin: 0;
  color: var(--hl-light);
}

.question-content {
  position: relative;

  background-color: var(--background-window);
  border-radius: 10px;

  margin-bottom: 20px;
  padding-bottom: 30px;
  padding-left: 30px;

  border: 1px solid var(--border-color);
}
</style>
