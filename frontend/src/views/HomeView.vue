<script setup>
import SideBar from '@/components/SideBar.vue'
</script>

<template>
  <main>
    <SideBar />
    <router-view id="my-page" />
  </main>
</template>
