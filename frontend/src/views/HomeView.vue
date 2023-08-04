<script setup>
import SideBar from '@/components/SideBar.vue'
import AiAssist from '@/components/main/AiAssist.vue'
</script>

<template>
  <main>
    <!-- <TheWelcome /> -->
    <SideBar />
    <div id="testDiv">
      <AiAssist />
    </div>
    <router-view id="contents" />
  </main>
</template>

<style>
main {
  position: relative;
  margin: 0 auto;
}
</style>
