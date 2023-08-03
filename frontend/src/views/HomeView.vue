<script setup>
import SideBar from '@/components/SideBar.vue'
</script>

<template>
  <main>
    <!-- <TheWelcome /> -->
    <SideBar />
    <router-view id="contents" />
  </main>
</template>

<style>
main {
  position: relative;
  margin: 0 auto;
}
</style>
