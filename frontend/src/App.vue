<script setup>
import { RouterLink, RouterView } from 'vue-router'
import Alert from '@/components/Alert.vue'
import TopBar from '@/components/TopBar.vue'
// import AiAssist from '@/components/main/AiAssist.vue'
// import AppView from '@/views/AppView.vue'
</script>

<template>
  <header>
    <TopBar id="topbar" />
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
}
</style>
