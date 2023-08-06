import axios from 'axios'

const listQuestion = async (query, success, fail) => {
  await axios
    .get(
      `questions/list?keyword=${query.keyword}&nickname=${query.nickname}&page=${query.page}`,
    )
    .then(success)
    .catch(fail)
}

export { listQuestion }
