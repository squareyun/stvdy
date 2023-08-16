<script setup>
import { useAlarmStore } from '@/stores'
import { useUserStore } from '@/stores' // 프로필이미지 등록을 위해서 import
import { ref, computed } from 'vue'
import router from '@/router'

const userStore = useUserStore() // 프로필 이미지 등록을 위해 userStore사용
const user = computed(() => userStore.user)

const alarmStore = useAlarmStore()
const alarms = computed(() => alarmStore.alarms)

alarmStore.getList()

const openAlarms = () => {
  console.log(!user.value.id)
  if (!user.value.id) {
    router.push('/about')
  } else {
    const a = document.getElementById('alarms')
    if (a.style.display == '' || a.style.display == 'none')
      a.style.display = 'block'
    else a.style.display = 'none'

    const e = document.getElementById('room-add')
    if (e.style.display == 'block') e.style.display = 'none'
  }
}

let tmpProfileUrl = `/userBalloon.png`

let profileValue = ref(
  sessionStorage.getItem('profileImg')
    ? sessionStorage.getItem('profileImg')
    : userStore.user.profileImg,
)
let profileImageUrl = ref(profileValue.value ? profileValue.value : null)

const profileImagePath = computed(() => {
  // user가 등록한 프로필 이미지가 없으면, 임의 프로필을 보여줌
  return profileImageUrl.value ? profileImageUrl.value : tmpProfileUrl //'/testProfile.png'
})

async function showDetail(url) {
  router.push(url)
}

async function moreAlarms() {
  router.push('/alarms')
}
</script>

<template>
  <div id="search">
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
      :style="`background-image: url(${profileImagePath})`"
      @click="openAlarms"
      id="alarms-btn"></div>

    <div id="alarms">
      <p>알림</p>
      <!-- <router-link
        id="mypage-btn"
        to="mypage">
        > 마이페이지
      </router-link> -->
      <tr
        class="top-alarm-row"
        v-for="alr in alarms"
        :key="alr.id"
        @click="showDetail(alr.linkedUrl)">
        <td class="top-alarm-icon">
          <div
            v-if="alr.userNo == 1"
            class="top-announce-icon">
            <svg
              width="1.1rem"
              height="1.1rem"
              viewBox="0 0 113 128"
              fill="none"
              xmlns="http://www.w3.org/2000/svg">
              <path
                d="M70.7765 15.0588L67.7647 0H0V128H15.0588V75.2941H57.2235L60.2353 90.3529H112.941V15.0588H70.7765Z"
                fill="black" />
            </svg>
          </div>
          <div
            v-if="alr.userNo == 2"
            class="top-advertise-icon">
            <svg
              width="1.1rem"
              height="1.1rem"
              viewBox="0 0 128 122"
              fill="none"
              xmlns="http://www.w3.org/2000/svg">
              <path
                d="M128 60.8582L113.804 44.6836L115.782 23.2727L94.7782 18.5018L83.7818 0L64 8.49455L44.2182 0L33.2218 18.5018L12.2182 23.2145L14.1964 44.6255L0 60.8582L14.1964 77.0327L12.2182 98.5018L33.2218 103.273L44.2182 121.775L64 113.222L83.7818 121.716L94.7782 103.215L115.782 98.4436L113.804 77.0327L128 60.8582ZM69.8182 89.9491H58.1818V78.3127H69.8182V89.9491ZM69.8182 66.6764H58.1818V31.7673H69.8182V66.6764Z"
                fill="black" />
            </svg>
          </div>
          <div
            v-if="alr.userNo >= 3"
            class="top-personal-icon">
            <svg
              width="0.8rem"
              height="0.8rem"
              viewBox="0 0 128 128"
              fill="none"
              xmlns="http://www.w3.org/2000/svg">
              <path
                d="M64 0C28.672 0 0 28.672 0 64C0 99.328 28.672 128 64 128H96V115.2H64C36.224 115.2 12.8 91.776 12.8 64C12.8 36.224 36.224 12.8 64 12.8C91.776 12.8 115.2 36.224 115.2 64V73.152C115.2 78.208 110.656 83.2 105.6 83.2C100.544 83.2 96 78.208 96 73.152V64C96 46.336 81.664 32 64 32C46.336 32 32 46.336 32 64C32 81.664 46.336 96 64 96C72.832 96 80.896 92.416 86.656 86.592C90.816 92.288 97.984 96 105.6 96C118.208 96 128 85.76 128 73.152V64C128 28.672 99.328 0 64 0ZM64 83.2C53.376 83.2 44.8 74.624 44.8 64C44.8 53.376 53.376 44.8 64 44.8C74.624 44.8 83.2 53.376 83.2 64C83.2 74.624 74.624 83.2 64 83.2Z"
                fill="black" />
            </svg>
          </div>
        </td>
        <td class="alarm-main">
          <div class="top-alarm-title">
            {{ alr.title }} : {{ alr.detail }}
            <span class="wrote-from">&nbsp; {{ alr.registTime }}</span>
          </div>
        </td>
      </tr>

      <p
        id="alarm-more-btn"
        @click="moreAlarms"
        v-if="alarms[0]">
        더 보기 +
      </p>
      <p
        id="alarm-nothing"
        v-if="!alarms[0]">
        알림이 없습니다.
      </p>
    </div>
  </div>
  <!-- TOP BAR에 Notification dropdown(my page로 가는 경로 필), searchbar 들어가야함 -->
</template>

<style scoped>
#search {
  position: fixed;
  right: 0;
  top: 0;
}

#search-input {
  position: fixed;
  top: 7px;
  right: 110px;
  background-color: var(--hl-light);
  padding-left: 1rem;

  color: var(--font100);

  font-family: 'ASDGothicM';
  font-size: 1rem;

  width: 330px;
  height: 37px;
  border-radius: 20px;
  border: 1px solid var(--font30);

  box-shadow: 0 2px 3px rgba(0, 0, 0, 0.16), 0 2px 3px rgba(0, 0, 0, 0.23);
}

#search-input:focus {
  outline: none !important;
}

#search-btn {
  position: fixed;
  top: 14px;
  right: 114px;
  background-color: transparent;

  border: none;
  border-left: 1px solid #b7b7b3;
  height: 27px;
  width: 50px;

  cursor: pointer;
}

#search-btn > svg > path {
  fill: var(--font50);
}

#alarms-btn {
  position: fixed;
  top: 7px;
  right: 20px;

  border-radius: 25px;
  width: 82px;
  height: 41px;

  /* background-image: url('/testProfile.png'); */
  background-size: cover;
  background-position: center;
  cursor: pointer;

  box-shadow: 0 2px 3px rgba(0, 0, 0, 0.16), 0 2px 3px rgba(0, 0, 0, 0.23);
}

#alarms {
  display: none;

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
  box-shadow: 0 2px 3px rgba(0, 0, 0, 0.16), 0 2px 3px rgba(0, 0, 0, 0.23);
}

.top-alarm-row {
  display: block;
  margin-bottom: 15px;

  height: auto;
  cursor: pointer;
}
.top-alarm-icon {
  position: relative;
  width: 65px;
  left: 0px;
}

.top-alarm-icon > div {
  position: inherit;
  padding: 0.2rem 0.6rem 0.3rem 0.6rem;

  top: 0.7rem;

  width: 30px;
  height: 15px;

  color: var(--hl-light);
  display: inline-block;
  font-family: 'ASDGothicM';
  text-align: center;

  border-radius: 20px;
}

.top-announce-icon {
  background-color: var(--hl-light);
}

.top-announce-icon > svg > path {
  fill: var(--font80);
}

.top-advertise-icon {
  background-color: var(--hl-watch);
}

.top-advertise-icon > svg > path {
  fill: var(--font100);
}

.top-personal-icon {
  background-color: var(--hl-pres);
}

.top-personal-icon > svg > path {
  fill: var(--hl-light);
}

#alarm-more-btn {
  margin-top: 25px;

  text-align: center;
  color: var(--font80);
  transition: color 0.4s;

  cursor: pointer;
}

#alarm-more-btn:hover {
  color: var(--font100);
}

#alarm-nothing {
  margin-top: 25px;

  text-align: center;
  color: var(--font80);
  transition: color 0.4s;
}

.top-alarm-title {
  display: block;
  font-size: 0.95rem;
  color: var(--font100);
}

@media (max-width: 1180px) {
  #search-input {
    left: 721px;
    right: auto;
  }

  #search-btn {
    left: 1016px;
    right: auto;
  }

  #alarms-btn {
    left: 1078px;
    right: auto;
  }

  #alarms {
    left: 721px;
    right: auto;
  }
}
</style>
