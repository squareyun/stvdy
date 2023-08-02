import axios from 'axios'

const hello = async (success, fail) => {
  await axios.get(`/users/hello`).then(success).catch(fail)
}

const getUser = async (token, success, fail) => {
  axios.defaults.headers['Authorization'] = 'Bearer ' + token
  await axios.get(`/users/user`).then(success).catch(fail)
}

// const getUser = async (email, success, fail) => {
//   await axios.post(`/users/${email}`).then(success).catch(fail)
// }

const joinUser = async (user, success, fail) => {
  await axios.post(`/users/join`, user).then(success).catch(fail)
}

export { hello, getUser, joinUser }
