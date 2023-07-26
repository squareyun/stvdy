<template>
  <div id="cTopBar">
    <p id="time">
    </p>
    <router-link to="mypage" style="background:white">마이페이지</router-link>
    <button @click="logout">LOGOUT</button>
  </div>
  <!-- TOP BAR에 Notification dropdown(my page로 가는 경로 필), searchbar 들어가야함 -->
</template>

<script>
import { onMounted } from 'vue'
import { useAuthStore } from '@/stores';

export default {
  setup(props) {
    onMounted(() => {
      var now = new Date()
      var month = now.getMonth()
      var day = now.getDate()
      var hour = now.getHours()
      var min = now.getMinutes()
      document.getElementById(
        'time',
      ).innerHTML = `${month}월 ${day}일 ${hour} : ${min} Welcome to Stvdy !!`
    })
  },
  methods: {
    async logout() {
      const authStore = useAuthStore();
      await authStore.logout();
    }
  }
}
</script>

<style scoped>
#cTopBar {
  background-color: #282a36;
  width: 100vw;
  height: 30px;
}

#time {
  color: #ffffff80;
  font-size: 11pt;
  margin: 0;
  padding-top: 4px;
  text-align: center;
}
</style>
