import axios from 'axios'
const loginAuth = async (user, success, fail) => {
  await axios.post(`/users/login`, user).then(success).catch(fail)
}

export { loginAuth }
