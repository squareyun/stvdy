import axios from 'axios'

const listQuestion = async (values, success, fail) => {
  await axios
    .get(
      `/api/questions/list?keyword=${values.keyword}&nickname=${values.nickname}&page=${values.page}&noAnsFilter=${values.noAnsFilter}&noBestAnsFilter=${values.noBestAnsFilter}`,
    )
    .then(success)
    .catch(fail)
}

const writeQuestion = async (values, success, fail) => {
  await axios.post(`/api/questions/add`, values).then(success).catch(fail)
}

const modifyQuestion = async (values, success, fail) => {
  await axios
    .put(`/api/questions/${values.id}`, values)
    .then(success)
    .catch(fail)
}

const getQuestion = async (id, success, fail) => {
  await axios.get(`/api/questions/${id}`).then(success).catch(fail)
}

const isLikeQuestion = async (values, success, fail) => {
  await axios
    .get(`/api/questions/islike/${values.userNo}/${values.questionNo}`)
    .then(success)
    .catch(fail)
}

const likesQuestion = async (values, success, fail) => {
  await axios
    .put(`/api/questions/likes/${values.id}`, values)
    .then(success)
    .catch(fail)
}

// const likesAnswer = async (values, success, fail) => {
//   await axios.put(`/api/answers/likes/${values.id}`).then(success).catch(fail)
// }

const answerQuestion = async (values, success, fail) => {
  await axios.post(`/api/answers/add`, values).then(success).catch(fail)
}

const getAnswers = async (id, success, fail) => {
  await axios.get(`/api/answers/list/${id}`).then(success).catch(fail)
}

export {
  listQuestion,
  writeQuestion,
  modifyQuestion,
  getQuestion,
  isLikeQuestion,
  likesQuestion,
  // likesAnswer,
  answerQuestion,
  getAnswers,
}
