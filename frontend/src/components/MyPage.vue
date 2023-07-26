<script setup>
import { useAuthStore } from '@/stores';


async function deactivate() {
  const authStore = useAuthStore();
  await authStore.deactivate();
}
</script>


<template>
  <div id="tmp-div">
    My Page!!!
    ????? zzzz

    <button @click="deactivate">
      계정삭제
    </button>


  </div>
</template>

<style scoped>
#tmp-div {
  background-color: white;
  color: white;
}
</style>