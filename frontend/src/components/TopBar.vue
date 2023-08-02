<script setup>
import { computed, onMounted } from 'vue'
import { mapState } from 'vuex'
import { useAuthStore } from '@/stores'

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
  setInterval(times, 1000)
})

const logout = async () => {
  const authStore = useAuthStore()
  await authStore.logout()
}
</script>

<template>
  <div id="topbar">
    <router-link
      to="home"
      id="to-home">
      <p id="time"></p>
    </router-link>
    <span>
      <router-link
        to="mypage"
        style="background: white">
        마이페이지
      </router-link>
      <button @click="logout">LOGOUT</button>
      <form action="">
        <input
          type="text"
          placeholder="zzz" />
        <button type="submit">검색</button>
      </form>
    </span>
  </div>
  <!-- TOP BAR에 Notification dropdown(my page로 가는 경로 필), searchbar 들어가야함 -->
</template>

<style scoped>
#test {
  position: fixed;
  right: 0;
  top: 0;
}
#topbar {
  background-color: var(--topbar-basic);
  width: 100vw;
  height: 30px;

  z-index: 1;
}

#to-home {
  text-underline-offset: 4px;
  color: var(--topbar-time);
}

#time {
  font-family: consolas, 'ASDGothic';
  color: var(--topbar-time);
  font-size: 0.9rem;
  margin: 0;
  padding-top: 4px;
  text-align: center;
}
</style>
