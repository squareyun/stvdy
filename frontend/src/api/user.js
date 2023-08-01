import axios from 'axios'

const hello = async (success, fail) => {
  await axios.get(`/users/hello`).then(success).catch(fail)
}
const joinUser = async (user, success, fail) => {
  await axios.post(`/users/join`, user).then(success).catch(fail)
}

export { hello, joinUser }
