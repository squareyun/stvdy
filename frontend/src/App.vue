<script setup>
import { RouterLink, RouterView } from 'vue-router'
import Alert from '@/components/Alert.vue';
import TopBar from '@/components/TopBar.vue'
</script>

<template>
  <header>
    <TopBar />
    <Alert />
  </header>

  <RouterView />
</template>

<style scoped>
header {
  position: fixed;
  left: 0;
  top: 0;
}
</style>
