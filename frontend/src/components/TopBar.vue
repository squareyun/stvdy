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

const openAlarms = () => {
  const alarmList = document.getElementById('alarms')
  if (alarmList.className == 'closed') alarmList.className = 'opened'
  else alarmList.className = 'closed'
}

onMounted(() => {
  setInterval(times, 1000)
})
</script>

<template>
  <div id="topbar">
    <router-link
      to="home"
      id="to-home">
      <p id="time"></p>
    </router-link>
    <span id="test">
      <form action="">
        <input
          type="text"
          id="search-input"
          placeholder="검색" />
        <button
          id="search-btn"
          type="submit">
          <svg
            width="1rem"
            height="1rem"
            viewBox="0 0 128 129"
            fill="none"
            xmlns="http://www.w3.org/2000/svg">
            <path
              d="M61.0307 122.061C27.3894 122.061 0 94.672 0 61.0307C0 27.3894 27.3894 0 61.0307 0C94.672 0 122.061 27.3894 122.061 61.0307C122.061 94.672 94.672 122.061 61.0307 122.061ZM61.0307 8.93132C32.2718 8.93132 8.93132 32.3314 8.93132 61.0307C8.93132 89.73 32.2718 113.13 61.0307 113.13C89.7895 113.13 113.13 89.73 113.13 61.0307C113.13 32.3314 89.7895 8.93132 61.0307 8.93132Z"
              fill="white" />
            <path
              d="M123.549 128.014C122.418 128.014 121.287 127.597 120.394 126.704L108.485 114.796C106.758 113.069 106.758 110.211 108.485 108.484C110.212 106.758 113.07 106.758 114.797 108.484L126.705 120.393C128.432 122.119 128.432 124.977 126.705 126.704C125.812 127.597 124.681 128.014 123.549 128.014Z"
              fill="white" />
          </svg>
        </button>
      </form>
      <div
        @click="openAlarms"
        id="alarms-btn"></div>

      <div
        id="alarms"
        class="closed">
        <p>알림</p>
        <router-link
          id="mypage-btn"
          to="mypage">
          > 마이페이지
        </router-link>
        <ul>
          <li>알림내용 1</li>
          <li>알림내용 2</li>
          <li>알림내용 3</li>
          <li>알림내용 4</li>
          <li>알림내용 5</li>
          <li>알림내용 6</li>
        </ul>
      </div>
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

  position: absolute;
  padding-top: 4px;
  margin: 0;

  left: 50%;
  transform: translateX(-50%);
  transition: all ease 1s;
}

@media (max-width: 1250px) {
  #time {
    left: 20px;
    transform: translateX(0%);
  }
}

#search-input {
  position: fixed;
  top: 7px;
  right: 110px;
  background-color: var(--hl-light);
  padding-left: 1rem;

  color: var(--font100);

  font-family: 'ASDGothic';
  font-size: 1rem;

  width: 330px;
  height: 37px;
  border-radius: 20px;
  border: 1px solid var(--font30);
}

#search-btn {
  position: fixed;
  top: 11px;
  right: 114px;
  background-color: var(--hl-purple);

  border: none;
  border-radius: 17px;
  height: 33px;
  width: 70px;
}

#alarms-btn {
  position: fixed;
  top: 7px;
  right: 20px;

  border-radius: 25px;
  width: 82px;
  height: 41px;

  background-color: rgb(133, 133, 133);
  cursor: pointer;
}

#alarms {
  position: fixed;
  top: 55px;
  right: 20px;

  width: 440px;
  height: auto;
  padding-bottom: 16px;
  padding-left: 17px;
  padding-right: 20px;

  font-size: 1rem;

  background-color: var(--hl-light);
  border: 1px solid #b7b7b3;
  border-radius: 20px;

  box-sizing: border-box;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(0, 0, 0, 0.23);
}

.closed {
  display: none;
}

.opened {
  display: block;
}

#mypage-btn {
  position: absolute;
  top: 16px;
  right: 17px;

  color: var(--font50);
  transition: color 0.4s;
  font-size: 1rem;
  font-weight: 700;
  text-decoration: none;
}

#mypage-btn:hover {
  color: var(--font100);
  transition: color 0.4s;
}

#logout-btn {
  position: absolute;
  bottom: 0px;
  right: 17px;
  color: var(--hl-warn);
  opacity: 0.6;
  transition: opacity 0.4s;

  cursor: pointer;
}

#logout-btn:hover {
  opacity: 1;
  transition: opacity 0.4s;
}
</style>
