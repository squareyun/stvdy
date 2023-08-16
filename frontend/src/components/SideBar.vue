<script setup>
import { RouterLink } from 'vue-router'
import { useUserStore, useAuthStore } from '@/stores'
import router from '@/router'
import { computed } from 'vue'

const userStore = useUserStore()
const user = computed(() => userStore.user)

const login = () => {
  router.push('/about')
}

const logout = async () => {
  const authStore = useAuthStore()
  await authStore.logout()
  router.push('/about')
}
</script>

<template>
  <div id="side-panel">
    <router-link
      to="/home"
      id="side-home">
      <div class="selected"></div>
      <svg
        width="1.2rem"
        height="1.2rem"
        viewBox="0 0 128 109"
        fill="none"
        xmlns="http://www.w3.org/2000/svg">
        <path
          d="M51.2 108.8V70.4H76.8V108.8H108.8V57.6H128L64 0L0 57.6H19.2V108.8H51.2Z"
          fill="white" />
      </svg>

      홈
    </router-link>
    <!-- <router-link
      to="/lecture"
      id="side-lecture">
      <div class="selected"></div>
      <svg
        width="1.2rem"
        height="1.2rem"
        viewBox="0 0 128 99"
        fill="none"
        xmlns="http://www.w3.org/2000/svg">
        <path
          d="M116.364 2.90909C109.905 0.872727 102.807 0 96 0C84.6545 0 72.4364 2.32727 64 8.72727C55.5636 2.32727 43.3455 0 32 0C20.6545 0 8.43636 2.32727 0 8.72727V98.9091C8.43636 92.5091 20.6545 90.1818 32 90.1818C43.3455 90.1818 55.5636 92.5091 64 98.9091C72.4364 92.5091 84.6545 90.1818 96 90.1818C102.807 90.1818 109.905 91.0545 116.364 93.0909C120.727 94.5454 124.509 96.2909 128 98.9091V8.72727C124.509 6.10909 120.727 4.36364 116.364 2.90909ZM116.364 81.4545C109.964 79.4182 102.982 78.5455 96 78.5455C86.1091 78.5455 71.8545 82.3273 64 87.2727V20.3636C71.8545 15.4182 86.1091 11.6364 96 11.6364C102.982 11.6364 109.964 12.5091 116.364 14.5455V81.4545Z"
          fill="white" />
        <path
          d="M95.9994 34.9091C101.119 34.9091 106.065 35.4327 110.545 36.4218V27.5782C105.949 26.7054 101.003 26.1818 95.9994 26.1818C86.1085 26.1818 77.1485 27.8691 69.8176 31.0109V40.6691C76.3922 36.9454 85.5267 34.9091 95.9994 34.9091Z"
          fill="white" />
        <path
          d="M69.8176 46.4871V56.1453C76.3922 52.4217 85.5267 50.3853 95.9994 50.3853C101.119 50.3853 106.065 50.909 110.545 51.8981V43.0544C105.949 42.1817 101.003 41.6581 95.9994 41.6581C86.1085 41.6581 77.1485 43.4035 69.8176 46.4871Z"
          fill="white" />
        <path
          d="M95.9994 57.1926C86.1085 57.1926 77.1485 58.8798 69.8176 62.0216V71.6798C76.3922 67.9562 85.5267 65.9198 95.9994 65.9198C101.119 65.9198 106.065 66.4435 110.545 67.4326V58.5889C105.949 57.658 101.003 57.1926 95.9994 57.1926Z"
          fill="white" />
      </svg>
      강의 정보 공유
    </router-link> -->
    <router-link
      to="/question/1"
      id="side-question">
      <div class="selected"></div>
      <svg
        width="1.2rem"
        height="1.2rem"
        viewBox="0 0 128 128"
        fill="none"
        xmlns="http://www.w3.org/2000/svg">
        <path
          d="M128 25.6H108.8V83.2H25.6V102.4H102.4L128 128V25.6ZM96 70.4V0H0V96L25.6 70.4H96Z"
          fill="white" />
      </svg>
      질문 게시판
    </router-link>
    <router-link
      to="/room"
      id="side-question">
      <div class="selected"></div>
      <svg
        width="1.2rem"
        height="1.2rem"
        viewBox="0 0 128 91"
        fill="none"
        xmlns="http://www.w3.org/2000/svg">
        <path
          d="M117.333 80V0H10.6667V80H0V90.6667H128V80H117.333ZM74.6667 80H53.3333V74.6667H74.6667V80ZM106.667 64H21.3333V10.6667H106.667V64Z"
          fill="black" />
      </svg>

      스터디 룸
    </router-link>
    <div v-if="user.id">
      <div class="side-bar-div-line"></div>
      <router-link
        to="/mypage"
        id="side-lecture">
        <div class="selected"></div>
        <svg
          width="1.2rem"
          height="1.2rem"
          viewBox="0 0 128 143"
          fill="none"
          xmlns="http://www.w3.org/2000/svg">
          <path
            d="M113.778 14.2222H84.0533C81.0667 5.97333 73.2444 0 64 0C54.7556 0 46.9333 5.97333 43.9467 14.2222H14.2222C6.4 14.2222 0 20.6222 0 28.4444V128C0 135.822 6.4 142.222 14.2222 142.222H113.778C121.6 142.222 128 135.822 128 128V28.4444C128 20.6222 121.6 14.2222 113.778 14.2222ZM64 14.2222C67.9111 14.2222 71.1111 17.4222 71.1111 21.3333C71.1111 25.2444 67.9111 28.4444 64 28.4444C60.0889 28.4444 56.8889 25.2444 56.8889 21.3333C56.8889 17.4222 60.0889 14.2222 64 14.2222ZM64 42.6667C75.8044 42.6667 85.3333 52.1956 85.3333 64C85.3333 75.8044 75.8044 85.3333 64 85.3333C52.1956 85.3333 42.6667 75.8044 42.6667 64C42.6667 52.1956 52.1956 42.6667 64 42.6667ZM106.667 128H21.3333V118.044C21.3333 103.822 49.7778 96 64 96C78.2222 96 106.667 103.822 106.667 118.044V128Z"
            fill="white" />
        </svg>
        프로필
      </router-link>
      <router-link
        to="/myquestion/1"
        id="side-myquestion">
        <div class="selected"></div>
        <svg
          width="1.2rem"
          height="1.2rem"
          viewBox="0 0 128 128"
          fill="none"
          xmlns="http://www.w3.org/2000/svg">
          <path
            d="M64 0C28.672 0 0 28.672 0 64C0 99.328 28.672 128 64 128C99.328 128 128 99.328 128 64C128 28.672 99.328 0 64 0ZM70.4 108.8H57.6V96H70.4V108.8ZM83.648 59.2L77.888 65.088C73.28 69.76 70.4 73.6 70.4 83.2H57.6V80C57.6 72.96 60.48 66.56 65.088 61.888L73.024 53.824C75.392 51.52 76.8 48.32 76.8 44.8C76.8 37.76 71.04 32 64 32C56.96 32 51.2 37.76 51.2 44.8H38.4C38.4 30.656 49.856 19.2 64 19.2C78.144 19.2 89.6 30.656 89.6 44.8C89.6 50.432 87.296 55.552 83.648 59.2Z"
            fill="white" />
        </svg>
        내 질문
      </router-link>
      <router-link
        to="/alarms"
        id="side-alarms">
        <div class="selected"></div>
        <svg
          width="1.2rem"
          height="1.2rem"
          viewBox="0 0 128 144"
          fill="none"
          xmlns="http://www.w3.org/2000/svg">
          <path
            d="M0 32H128V48H0V32ZM0 80H128V64H0V80ZM0 112H56V96H0V112ZM0 144H56V128H0V144ZM91.28 121.36L80 110L68.72 121.28L91.28 144L128 107.36L116.64 96L91.28 121.36ZM0 0V16H128V0H0Z"
            fill="white" />
        </svg>
        알림
      </router-link>
    </div>

    <div id="side-foot">
      <a
        id="logout-btn"
        @click="logout"
        v-if="user.id">
        <svg
          width="1.2rem"
          height="1.2rem"
          viewBox="0 0 128 128"
          fill="none"
          xmlns="http://www.w3.org/2000/svg">
          <path
            d="M77.5822 38.4711L67.5556 28.4444L32 64L67.5556 99.5556L77.5822 89.5289L59.2356 71.1111L128 71.1111L128 56.8889L59.2356 56.8889L77.5822 38.4711ZM-9.53674e-07 128L128 128L128 85.3333L113.778 85.3333L113.778 113.778L14.2222 113.778L14.2222 14.2222L113.778 14.2222L113.778 42.6667L128 42.6667L128 -9.53674e-07L1.02364e-05 -1.21438e-05L-9.53674e-07 128Z"
            fill="white" />
        </svg>
        로그아웃
      </a>
      <a
        id="login-btn"
        @click="login"
        v-if="!user.id">
        <svg
          width="1.2rem"
          height="1.2rem"
          viewBox="0 0 128 116"
          fill="none"
          xmlns="http://www.w3.org/2000/svg">
          <path
            d="M57.6 25.6L48.64 34.56L65.28 51.2H0V64H65.28L48.64 80.64L57.6 89.6L89.6 57.6L57.6 25.6ZM115.2 102.4H64V115.2H115.2C122.24 115.2 128 109.44 128 102.4V12.8C128 5.76 122.24 0 115.2 0H64V12.8H115.2V102.4Z"
            fill="white" />
        </svg>

        로그인
      </a>
      <router-link
        to="/mypage"
        v-if="user.id">
        <svg
          width="1.2rem"
          height="1.2rem"
          viewBox="0 0 128 128"
          fill="none"
          xmlns="http://www.w3.org/2000/svg">
          <path
            d="M113.778 0H14.2222C6.32889 0 0 6.4 0 14.2222V113.778C0 121.6 6.32889 128 14.2222 128H113.778C121.671 128 128 121.6 128 113.778V14.2222C128 6.4 121.671 0 113.778 0ZM101.333 64C101.333 65.7067 101.191 67.3422 100.978 69.0489L101.049 68.9067L111.502 77.1556C112.498 77.8667 113.138 78.4356 113.138 78.4356L101.049 99.3422L86.6845 93.6533L86.8267 93.44C84.1956 95.5022 81.3511 97.2089 78.2222 98.4889H78.2933L76.3733 111.644C76.2311 112.853 76.0889 113.778 76.0889 113.778H51.9111L49.7067 98.4889H49.7778C46.6489 97.2089 43.8044 95.5022 41.1733 93.44L41.3156 93.6533L26.9511 99.3422L14.8622 78.4356C14.8622 78.4356 15.5733 77.8667 16.4978 77.1556L26.9511 68.9067L27.0222 69.0489C26.8089 67.3422 26.6667 65.7067 26.6667 64C26.6667 62.2933 26.8089 60.6578 27.0222 59.0933L26.9511 59.1644L14.8622 49.6356L26.9511 28.6578L41.2444 34.4178V34.4889C43.8756 32.4978 46.72 30.7911 49.7778 29.5111H49.7067L51.9111 14.2222H76.16L78.2933 29.5111H78.2222C81.28 30.7911 84.1244 32.4978 86.7556 34.4889V34.4178L101.049 28.6578L113.138 49.6356L100.978 59.1644L100.907 59.0933C101.191 60.6578 101.333 62.2933 101.333 64Z"
            fill="white" />
          <path
            d="M64.0003 81.4222C73.6224 81.4222 81.4226 73.622 81.4226 64C81.4226 54.378 73.6224 46.5778 64.0003 46.5778C54.3783 46.5778 46.5781 54.378 46.5781 64C46.5781 73.622 54.3783 81.4222 64.0003 81.4222Z"
            fill="white" />
        </svg>
        환경설정
      </router-link>
    </div>
  </div>
</template>

<style>
#side-panel {
  position: fixed;
  left: 0;
  top: 130px;
  width: 200px;
  height: calc(100vh - 110px);
  /*
  background-color: rgb(65, 65, 65); */
  color: var(--hl-light);
}

#side-panel a {
  height: 30px;
  position: relative;
  color: var(--hl-light);
  text-decoration: none;
  opacity: 0.75;
  transition: opacity 0.4s;
}

#side-panel a:hover {
  opacity: 1;
  transition: opacity 0.4s;
}

#side-panel a.router-link-exact-active {
  opacity: 1;
  transition: opacity 0.4s;
  .selected {
    opacity: 1;
    transition: opacity 0.4s;
  }
}

/* .router-link-exact-active {
  .selected {
    opacity: 1;
    transition: opacity 0.4s;
  }
} */

#side-panel a,
#side-foot a {
  display: block;
  margin-left: 12px;
  padding-top: 5px;
  font-size: 1rem;
}

.selected {
  position: absolute;
  left: -19px;
  top: 7px;

  background-color: var(--hl-light);
  height: 1.5rem;
  width: 10px;

  opacity: 0;
}

a > svg,
p > svg {
  position: inherit;
  top: 4px;

  margin-right: 15px;
}

path {
  fill: var(--hl-light);
}

.side-bar-div-line {
  width: 120px;
  height: 1px;
  margin-left: 10px;
  margin-top: 10px;
  margin-bottom: 30px;

  background-color: var(--hl-light);
  opacity: 0.5;
}

#side-foot {
  position: absolute;
  bottom: 60px;
}

#side-foot #logout-btn {
  color: var(--hl-warn);
}

@media (max-height: 555px) {
  #side-foot {
    top: 315px;
    bottom: auto;
  }
}

#login-btn {
  position: relative;
  margin: 0px;

  opacity: 0.7;
  transition: opacity 0.4s;

  cursor: pointer;
}

#login-btn > svg > path {
  fill: var(--hl-light);
}

#login-btn:hover {
  opacity: 1;
  transition: opacity 0.4s;
}

#logout-btn {
  position: relative;
  margin: 0px;

  opacity: 0.7;
  transition: opacity 0.4s;

  cursor: pointer;
}

#logout-btn > svg > path {
  fill: var(--hl-warn);
}

#logout-btn:hover {
  opacity: 1;
  transition: opacity 0.4s;
}
</style>
