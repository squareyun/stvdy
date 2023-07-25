<script setup>
import LoginBanner from '@/components/ent/LoginBanner.vue'
import LoginBox from '@/components/ent/LoginBox.vue'
</script>

<template>
  <LoginBanner />
  <LoginBox class="LoginBox"/>
</template>

<style>
.LoginBox {
  background-color: #f8f8f2;
  border-radius: 20px;
  width: 420px;
  height: 720px;
  margin: 0 auto;
}

@media (min-width: 1024px) {
}
</style>
