<script setup>
import SideBar from '@/components/SideBar.vue'
import TopSearch from '@/components/TopSearch.vue'
import Advertisment from '../components/home/advertisment.vue'
import TagRank from '../components/home/TagRank.vue'
import MeetingRoom from '../components/home/MeetingRoom.vue'
</script>

<template>
  <SideBar />
  <TopSearch id="top-items" />
  <main id="home-main">
    <Advertisment />
    <TagRank />
    <MeetingRoom />
  </main>
</template>

<style>
#top-items {
  z-index: 100;
}

@media (min-width: 1600px) {
  #home-main {
    position: absolute;
    left: 270px;

    top: 70px;

    width: calc(100vw - 400px);
  }
}

@media (max-width: 1600px) {
  #home-main {
    position: absolute;
    left: calc(50vw - 480px);

    top: 70px;

    width: calc(960px);
  }
}

@media (max-width: 1360px) {
  #home-main {
    position: absolute;
    left: 200px;

    top: 70px;

    width: calc(960px);
    background-color: aqua;
  }
}
</style>
