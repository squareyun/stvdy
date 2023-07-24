<template>
  <div id="cTopBar">
    <p id="time"></p>
  </div>
</template>

<script>
import { onMounted } from 'vue'

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
