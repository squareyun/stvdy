import axios from 'axios'

const loginAuth = async (user, success, fail) => {
  await axios.post(`/api/users/login`, user).then(success).catch(fail)
}

export { loginAuth }
