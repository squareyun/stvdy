<script setup>
import { useAlarmStore } from '@/stores'
import { computed } from 'vue'
import { addAlarm } from '@/api/alarm'

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

// async function showDetail(id) {
//   router.push(`/questiondetail/${id}`)
// }
</script>

<template>
  <div>
    <span class="question-content-title">내 알림</span>
    <div class="alarm-list">
      <div>
        <tr
          class="alarm-row"
          v-for="alr in alarms"
          :key="alr.id"
          @click="showDetail(alr.linkedUrl)">
          <td class="alarm-icon">
            <div class="profile-icon">채택됨</div>
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
.alarm-list {
  position: relative;

  background-color: var(--background-window);
  border-radius: 10px;

  margin-bottom: 20px;

  width: 960px;
}

#question-sort {
  position: absolute;
  right: -6.5rem;

  font-family: 'ASDGothicUL';
  color: var(--hl-light);
}

#question-sort > ul > li {
  cursor: pointer;
}

#sort-new {
  font-weight: 700;
}

#add-question {
  float: right;
  margin-right: calc(7rem);

  text-decoration: none;
  color: var(--hl-purple);
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

  background-color: var(--background-up);
}

.alarm-icon {
  position: relative;
  width: 90px;
  left: 0px;
}

.profile-icon {
  position: absolute;
  top: 33px;
  right: 9px;
  padding: 0.3rem 0.6rem 0.2rem 0.6rem;

  color: var(--hl-light);
  display: inline-block;
  font-family: 'ASDGothicM';
  text-align: center;

  border: 1px solid var(--hl-purple);
  border-radius: 20px;
  background-color: var(--hl-purple);
}

#best-not-selected {
  position: absolute;
  top: 20px;
  right: 0px;
  padding: 0.3rem 0.6rem 0.2rem 0.6rem;

  color: var(--hl-light);
  display: inline-block;
  font-family: 'ASDGothicUL';
  text-align: center;

  border: 1px solid var(--hl-purple);
  border-radius: 20px;
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

.info-etc {
  position: absolute;
  right: 20px;
}

.wrote-from {
  font-size: 0.9rem;
  font-family: 'ASDGothicUL';
}
</style>
