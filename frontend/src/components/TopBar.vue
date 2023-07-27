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
    </span>
  </div>
  <!-- TOP BAR에 Notification dropdown(my page로 가는 경로 필), searchbar 들어가야함 -->
</template>

<script>
import { onMounted } from 'vue'
import { useAuthStore } from '@/stores'

export default {
  setup(props) {
    onMounted(() => {
      var now = new Date()
      var month = now.getMonth()
      var day = now.getDate()
      var hour = now.getHours()
      // var min = now.getMinutes()
      var min = String(now.getMinutes()).padStart(2, '0')
      document.getElementById(
        'time',
      ).innerHTML = `${month}월 ${day}일 ${hour}:${min} Welcome to Stvdy !!`
    })
  },
  methods: {
    async logout() {
      const authStore = useAuthStore()
      await authStore.logout()
    },
  },
}
</script>

<style scoped>
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
  color: var(--topbar-time);
  font-size: 0.9rem;
  margin: 0;
  padding-top: 4px;
  text-align: center;
}
</style>
