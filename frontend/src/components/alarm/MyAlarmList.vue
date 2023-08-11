<script setup>
import { useAlarmStore } from '@/stores'
import { computed } from 'vue'
import { addAlarm } from '@/api/alarm'
import router from '@/router'

const alarmStore = useAlarmStore()
const alarms = computed(() => alarmStore.alarms)

alarmStore.getList()

const addTestAlarm = () => {
  const values = {
    userNo: 4,
    title: 'test알림',
    content: 'test내용!',
  }

  addAlarm(
    values,
    (res) => {
      console.log(res)
    },
    (fail) => {
      console.log(fail)
    },
  )
}

async function showDetail(url) {
  router.push(url)
}
</script>

<template>
  <div>
    <span class="question-content-title">내 알림</span>
    <div class="my-alarm-list">
      <div>
        <tr
          class="alarm-row"
          v-for="alr in alarms"
          :key="alr.id"
          @click="showDetail(alr.linkedUrl)">
          <td class="alarm-icon">
            <div
              v-if="alr.userNo == 1"
              class="announce-icon">
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
              class="advertise-icon">
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
              class="personal-icon">
              <svg
                width="1.1rem"
                height="1.1rem"
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
            <div class="alarm-title">
              {{ alr.title }}
            </div>
            <div class="alarm-detail">{{ alr.detail }}</div>
          </td>
          <td class="alarm-info">
            <p class="info-etc">
              <span class="wrote-from">&nbsp; {{ alr.registTime }}</span>
            </p>
          </td>
          <div></div>

          <div class="alarm-div-line"></div>
        </tr>
      </div>
    </div>
  </div>
</template>

<style>
.my-alarm-list {
  position: relative;

  background-color: var(--background-window);
  border-radius: 10px;

  margin-bottom: 20px;

  width: 960px;

  border: 1px solid var(--border-color);
}

.alarm-row {
  display: block;
  position: relative;
  height: 100px;

  color: var(--hl-light);
}

.alarm-div-line {
  position: absolute;
  left: 0px;
  bottom: 0px;
  width: 960px;
  height: 1px;

  background-color: var(--border-color);
}

.alarm-icon {
  position: relative;
  width: 90px;
  left: 0px;
}

.alarm-icon > div {
  position: absolute;
  top: 33px;
  right: 9px;
  padding: 0.3rem 0.6rem 0.2rem 0.6rem;

  width: 40px;
  height: 20px;

  color: var(--hl-light);
  display: inline-block;
  font-family: 'ASDGothicM';
  text-align: center;

  border-radius: 20px;
}

.announce-icon {
  background-color: var(--hl-light);
}

.announce-icon > svg > path {
  fill: var(--font80);
}

.advertise-icon {
  background-color: var(--hl-watch);
}

.advertise-icon > svg > path {
  fill: var(--font100);
}

.personal-icon {
  background-color: var(--hl-pres);
}

.personal-icon > svg > path {
  fill: var(--hl-light);
}

.alarm-main {
  position: relative;
  width: 600px;
}

.alarm-title {
  position: absolute;
  top: 22px;
  left: 10px;

  color: var(--hl-light);

  font-size: 1.3rem;

  cursor: pointer;
}

.alarm-detail {
  position: absolute;
  top: 60px;
  left: 10px;

  height: 60px;
  overflow: hidden;

  color: var(--hl-light);

  font-family: 'ASDGothicUL';
  font-size: 0.9rem;
}

.alarm-info {
  width: calc(270px - 7rem);
}

.wrote-from {
  font-size: 0.9rem;
  font-family: 'ASDGothicUL';
}
</style>
