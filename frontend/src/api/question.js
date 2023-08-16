import axios from 'axios'

const listQuestion = async (values, success, fail) => {
  await axios
    .get(
      `/api/questions/list?keyword=${values.keyword}&nickname=${values.nickname}&page=${values.page}&noAnsFilter=${values.noAnsFilter}&noBestAnsFilter=${values.noBestAnsFilter}`,
    )
    .then(success)
    .catch(fail)
}

const listYourQuestion = async (values, success, fail) => {
  await axios
    .get(`/api/questions/list/my?userno=${values.userno}&page=${values.page}`)
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

const deleteQuestion = async (id, success, fail) => {
  await axios.delete(`/api/questions/${id}`).then(success).catch(fail)
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

const answerQuestion = async (values, success, fail) => {
  await axios.post(`/api/answers/add`, values).then(success).catch(fail)
}

const getAnswers = async (id, success, fail) => {
  await axios.get(`/api/answers/list/${id}`).then(success).catch(fail)
}

const bestAnswer = async (values, success, fail) => {
  await axios
    .put(`/api/answers/choose/${values.questionNo}/${values.answerNo}`)
    .then(success)
    .catch(fail)
}

export {
  listQuestion,
  listYourQuestion,
  writeQuestion,
  modifyQuestion,
  deleteQuestion,
  getQuestion,
  isLikeQuestion,
  likesQuestion,
  answerQuestion,
  getAnswers,
  bestAnswer,
}
