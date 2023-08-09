<script setup>
import SideBar from '@/components/SideBar.vue'
import TopSearch from '@/components/TopSearch.vue'
import AiAssist from '@/components/main/AiAssist.vue'
</script>

<template>
  <SideBar />
  <TopSearch id="top-items" />
  <main>
    <!-- <TheWelcome /> -->
    <SideBar />
    <div id="testDiv">
      <AiAssist />
    </div>
    <router-view id="home-contents" />
  </main>
</template>

<style>
#top-items {
  z-index: 100;
}

#home-contents {
  position: absolute;
  top: 120px;
  left: calc(50vw - 480px);
  width: 960px;

  color: var(--hl-light);

  /* background-color: rgba(170, 170, 170, 0.123); */
}

@media (max-width: 1360px) {
  #home-contents {
    left: 200px;
  }
}

.home-content-title {
  margin: 0;
  color: var(--hl-light);
}

.home-content {
  position: relative;

  background-color: var(--background-window);
  border-radius: 10px;

  margin-bottom: 20px;
  padding-bottom: 30px;
  padding-left: 30px;
}
</style>
