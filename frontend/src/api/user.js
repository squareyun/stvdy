import axios from 'axios'

const hello = async (success, fail) => {
  await axios.get(`/api/users/hello`).then(success).catch(fail)
}

// const getUser = async (email, success, fail) => {
//   await axios.post(`/users/${email}`).then(success).catch(fail)
// }

const joinUser = async (user, success, fail) => {
  await axios.post(`/api/users/join`, user).then(success).catch(fail)
}

const getUser = async (token, success, fail) => {
  axios.defaults.headers['Authorization'] = 'Bearer ' + token
  await axios.get(`/api/mypage/`).then(success).catch(fail)
}

const nameUser = async (name, success, fail) => {
  await axios.put('/api/mypage/nickname', name).then(success).catch(fail)
}

const changePwd = async (values, success, fail) => {
  await axios.put('/api/mypage/findpwd', values).then(success).catch(fail)
}

const deleteUser = async (userId, success, fail) => {
  await axios.delete(`/api/users/${userId}`).then(success).catch(fail)
}

export { hello, getUser, joinUser, nameUser, changePwd, deleteUser }
