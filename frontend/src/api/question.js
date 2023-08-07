import axios from 'axios'

const listQuestion = async (values, success, fail) => {
  await axios
    .get(
      `/api/questions/list?keyword=${values.keyword}&nickname=${values.nickname}&page=${values.page}`,
    )
    .then(success)
    .catch(fail)
}

const writeQuestion = async (values, success, fail) => {
  await axios.post(`/api/questions/add`, values).then(success).catch(fail)
}

const getQuestion = async (id, success, fail) => {
  await axios.get(`/api/questions/${id}`).then(success).catch(fail)
}

export { listQuestion, writeQuestion, getQuestion }
