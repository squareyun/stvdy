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
div {
  color: var(--font80);
}

#ents-box {
  position: relative;
}

#ents-box {
  background-color: var(--hl-light);
  border-radius: 20px;
  width: 420px;
  height: 720px;
  margin: 0 auto;
}

.ents-title {
  margin-top: 40px;
  text-align: center;

  font-weight: 300;
  color: var(--font100);
}

.field {
  /* position: relative;
  top: 50px; */
  background-color: var(--hl-light);
  margin-left: 19px;
  margin-top: -12px;
  margin-bottom: 4px;
  padding-left: 18px;

  color: var(--font100);

  font-size: 1rem;

  width: 360px;
  height: 46px;
  border-radius: 10px;
  border: 1px solid var(--font30);
}

.field:focus {
  outline: none;
  box-shadow: 0 0 0 1px var(--font80) inset;
}

.field-name {
  position: relative;
  display: inline;
  margin-left: 31px;

  /* font-size: 14px; */
  font-size: 0.9rem;

  text-align: center;

  background-color: var(--hl-light);
}

.error-yup {
  color: var(--hl-warn);
}

@media (min-height: 820px) {
  #app {
    margin-top: calc((100vh - 820px) / 2);
  }
}
</style>
