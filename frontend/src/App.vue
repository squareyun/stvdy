<script setup>
import { RouterView } from 'vue-router'
import Alert from '@/components/Alert.vue'
import TopBar from '@/components/TopBar.vue'
import AiChat from '@/components/main/AiAssist.vue'

import { useUserStore } from '@/stores'
import { computed } from 'vue'

const userStore = useUserStore()
const user = computed(() => userStore.user)
</script>

<template>
  <header>
    <TopBar id="topbar" />
    <AiChat
      id="ai-assist"
      v-if="user.id" />
    <Alert />
  </header>

  <RouterView />
</template>

<style scoped>
header {
  #topbar {
    position: fixed;
    left: 0;
    top: 0;
  }

  #ai-assist {
    position: fixed;
  }
}
</style>
