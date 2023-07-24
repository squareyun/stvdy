<script setup>
import { RouterLink, RouterView } from 'vue-router'
import TopBar from '@/components/TopBar.vue'
</script>

<template>
  <header>
    <TopBar />
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
