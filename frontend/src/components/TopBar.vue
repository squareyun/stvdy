<script setup>
import { computed, onMounted } from 'vue'
import { mapState } from 'vuex'

const times = () => {
  var now = new Date()
  var month = now.getMonth()
  var day = now.getDate()
  var hour = now.getHours()
  // var min = now.getMinutes()
  var min = String(now.getMinutes()).padStart(2, '0')
  var sec = String(now.getSeconds()).padStart(2, '0')

  document.getElementById(
    'time',
  ).innerHTML = `${month}월 ${day}일 ${hour}:${min}:${sec} Welcome to Stvdy !!`
}

onMounted(() => {
  setInterval(times, 500)
})
</script>

<template>
  <div id="topbar">
    <router-link
      to="home"
      id="to-home">
      <p id="time"></p>
    </router-link>
  </div>
  <!-- TOP BAR에 Notification dropdown(my page로 가는 경로 필), searchbar 들어가야함 -->
</template>

<style scoped>
#topbar {
  width: 100vw;
  height: 55px;

  background-color: var(--background-up);
  border: none;
  border-bottom: 1px solid var(--hl-light30);

  z-index: 1;
}

#to-home {
  text-underline-offset: 4px;
  color: var(--topbar-time);
}

#time {
  font-family: consolas, 'ASDGothicM';
  color: var(--topbar-time);
  font-size: 0.9rem;

  position: absolute;
  padding-top: 18px;
  margin: 0;

  left: 50%;
  transform: translateX(-50%);
  transition: all ease 1s;
}

@media (max-width: 1350px) {
  #time {
    left: 30px;
    transform: translateX(0%);
  }
}
</style>
