<script setup>
import LoginBanner from '@/components/ent/LoginBanner.vue'
</script>

<template>
  <LoginBanner class="window" />
  <router-view
    class="window"
    id="ents-box" />
</template>

<style>
#ents-box {
  background-color: var(--hl-light);
  border-radius: 20px;
  width: 420px;
  height: 720px;
  margin: 0 auto;
}

@media (min-height: 820px) {
  #app {
    margin-top: calc((100vh - 820px) / 2);
  }
}

.ents-title {
  margin-top: 40px;
  text-align: center;

  color: var(--font100);
}
</style>
