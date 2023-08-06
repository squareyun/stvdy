import axios from 'axios'

const hello = async (success, fail) => {
  await axios.get(`/users/hello`).then(success).catch(fail)
}

// const getUser = async (email, success, fail) => {
//   await axios.post(`/users/${email}`).then(success).catch(fail)
// }

const joinUser = async (user, success, fail) => {
  await axios.post(`/users/join`, user).then(success).catch(fail)
}

const getUser = async (token, success, fail) => {
  axios.defaults.headers['Authorization'] = 'Bearer ' + token
  await axios.get(`/mypage/`).then(success).catch(fail)
}

const nameUser = async (name, success, fail) => {
  await axios.put('/mypage/nickname', name).then(success).catch(fail)
}

const deleteUser = async (userId, success, fail) => {
  await axios.delete(`/users/${userId}`).then(success).catch(fail)
}

export { hello, getUser, joinUser, nameUser, deleteUser }
