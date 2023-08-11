import axios from 'axios'

const listAlarms = async (success, fail) => {
  await axios.get(`/api/alarms/`).then(success).catch(fail)
}
const addAlarm = async (values, success, fail) => {
  await axios.post(`/api/alarms/add`, values).then(success).catch(fail)
}
const getAlarm = async (id, success, fail) => {
  await axios.get(`/api/alarms/detail/${id}`).then(success).catch(fail)
}

export { listAlarms, addAlarm, getAlarm }
