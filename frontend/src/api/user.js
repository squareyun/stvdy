import axios from 'axios'

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

const changeOpenAiKey = async (apiKey, success, fail) => {
  const key = {
    apiKey: apiKey,
  }
  await axios.put(`/api//mypage/api-key`, key).then(success).catch(fail)
}

const emailCheck = async (email, success, fail) => {
  await axios.get(`/users/join/${email}`).then(success).catch(fail)
}

export {
  getUser,
  joinUser,
  nameUser,
  changePwd,
  deleteUser,
  changeOpenAiKey,
  emailCheck,
}
