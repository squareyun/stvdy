<script setup>
import TheWelcome from '../components/TheWelcome.vue'
import SideBar from '../components/SideBar.vue'
</script>

<template>
  <main>
    <!-- <TheWelcome /> -->
    <SideBar />
    <router-view id="my-page" />
  </main>
</template>
