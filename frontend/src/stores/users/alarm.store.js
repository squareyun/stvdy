import { defineStore } from 'pinia'
import { listAlarms, addAlarm, getAlarm } from '@/api/alarm'
import { ref } from 'vue'

export const useAlarmStore = defineStore('alarms', () => {
  const alarm = ref([])
  const alarms = ref([])

  const getList = async () => {
    await listAlarms(
      (res) => {
        alarms.value = res.data.alarms

        for (var a in alarms.value) {
          alarms.value[a].registTime = alarms.value[a].registTime.replaceAll(
            'T',
            ' ',
          )
        }
      },
      (fail) => {
        console.log(fail)
      },
    )
  }

  return { alarm, alarms, getList }
})
