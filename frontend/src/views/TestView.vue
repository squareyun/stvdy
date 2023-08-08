<script setup>
import SideBar from '@/components/SideBar.vue'
import TopSearch from '@/components/TopSearch.vue'
</script>

<template>
  <SideBar />
  <TopSearch id="top-items" />
  <main id="home-main"></main>
</template>

<style>
#top-items {
  z-index: 100;
}

#home-main {
  position: absolute;
  left: 200px;
  width: calc(100vw - 300px);
  height: 3000px;
  background-color: aqua;
}
</style>
