<script setup>
import { Form, Field } from 'vee-validate'
import { useQuestionStore, useUserStore, useImagePath } from '@/stores'
import { ref, computed, onBeforeUnmount } from 'vue'
import { useRoute } from 'vue-router'
import router from '@/router'
import {
  isLikeQuestion,
  likesQuestion,
  answerQuestion,
  deleteQuestion,
  bestAnswer,
} from '@/api/question'

const $route = useRoute()

let myAnswer = ''

const imagePath = useImagePath()

const userStore = useUserStore()
const user = computed(() => userStore.user)

const questionStore = useQuestionStore()
const question = computed(() => questionStore.question)
const answers = computed(() => questionStore.answers)

questionStore.getQuestionById($route.params.id).then(() => {
  islikeQtn()
})

const islikeQtn = () => {
  const values = {
    userNo: user.value.id,
    questionNo: $route.params.id,
  }
  isLikeQuestion(
    values,
    (res) => {
      const likes = res.data.isQuetionLiked

      if (likes) {
        document.getElementById('content-like-btn-path').style.fill =
          'var(--hl-pres)'
        document.getElementById('content-dislike-btn-path').style.fill =
          'var(--hl-light)'
      } else if (likes === null) {
        document.getElementById('content-like-btn-path').style.fill =
          'var(--hl-light)'
        document.getElementById('content-dislike-btn-path').style.fill =
          'var(--hl-light)'
      } else {
        document.getElementById('content-like-btn-path').style.fill =
          'var(--hl-light)'
        document.getElementById('content-dislike-btn-path').style.fill =
          'var(--hl-pres)'
      }
    },
    (fail) => console.log(fail),
  )
}

const likesQtn = (value) => {
  const values = {
    id: $route.params.id,
    userNo: user.value.id,
    isLike: value,
  }

  likesQuestion(
    values,
    () => {
      questionStore.getQuestionById($route.params.id)
    },
    (fail) => console.log(fail),
  ).then(() => {
    islikeQtn()
  })
}

const answerQtn = () => {
  const newQuestion = {
    userNo: user.value.id,
    questionNo: question.value.id,
    content: myAnswer,
  }

  const newAlarm = {
    title: '질문에 대한 답변이 있습니다.',
    detail: question.value.title,
    userNo: question.value.userNo,
  }

  answerQuestion(
    newQuestion,
    () => {
      router.go(0)
      // addAlarm(
      //   newAlarm,
      //   (res) => {
      //     console.log(res)
      //   },
      //   (fail) => {
      //     console.log(fail)
      //   },
      // )
    },
    (fail) => console.log(fail),
  )
}

const modifyQtn = () => {
  router.push(`/modifyQuestion/${question.value.id}`)
}

const deleteQtn = () => {
  deleteQuestion(
    $route.params.id,
    (res) => {
      console.log(res)
      router.push(`/question/1`)
    },
    (fail) => console.log(fail),
  )
}

onBeforeUnmount(() => {
  questionStore.clearState()
})

const bestAns = (id) => {
  const data = {
    questionNo: question.value.id,
    answerNo: id,
  }
  bestAnswer(
    data,
    (res) => router.go(0),
    (fail) => console.log(fail),
  )
}
let tmpquestionerImage = ref('/authorImage.png')
let questionerImage = ref(question.value.profileImage) // question에 담겨있는 profileImage 경로를 받아옴.
let questionerImagePath = computed(()=>{
  return questionerImage.value?questionerImage.value:tmpquestionerImage.value
})

let tmprespondentImage = ref(`/randomImages/randomImage${Math.floor(Math.random() * 34)}.png`)

</script>

<template>
  <div>
    <span class="question-content-title">질문</span>
    <span id="question-content-id">#{{ question.id }}</span>
    <div class="question-content">
      <span id="question-question-content-title">
        {{ question.title }}
        <span id="question-info">
          #{{ question.category }} · 조회수 {{ question.hit }}
        </span>
      </span>
      <div id="question-detail-main">
        <div
          placeholder="ex) 6.2전쟁은 몇년 도에 발발 했나요?"
          id="question-detail-main-text"
          name="content">
          {{ question.detail }}
        </div>
        <div id="content-info-box">
          <div id="content-score">
            {{ question.questionScore }}
            <svg
              width="1.3rem"
              height="1.1rem"
              viewBox="0 0 128 117"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
              id="content-like-btn"
              @click="likesQtn(true)">
              <path
                id="content-like-btn-path"
                d="M76.6255 0L34.9091 41.7746V116.364H106.531L128 66.3273V40.7273H79.6509L86.1673 9.42545L76.6255 0ZM0 46.5455H23.2727V116.364H0V46.5455Z"
                fill="white" />
            </svg>
            <svg
              width="1.3rem"
              height="1.3rem"
              viewBox="0 0 128 128"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
              id="content-dislike-btn"
              @click="likesQtn(false)">
              <g clip-path="url(#clip0_782_927)">
                <path
                  id="content-dislike-btn-path"
                  d="M52.4266 122.667L90.6666 84.3733V16H25.0133L5.33325 61.8667V85.3333H49.6533L43.6799 114.027L52.4266 122.667ZM101.333 16H122.667V80H101.333V16Z"
                  fill="white" />
              </g>
              <defs>
                <clipPath id="clip0_782_927">
                  <rect
                    width="128"
                    height="128"
                    fill="white" />
                </clipPath>
              </defs>
            </svg>
          </div>
          <div
            :style="`background-image: url('${questionerImagePath}');`"
            id="author-image"></div>
          <div id="author-name">
            {{ question.regist_time }}
            <span id="wrote-time">{{ question.userNickname }}</span>
          </div>
          <div
            id="edit-delete-box"
            v-if="question.userNo === user.id">
            <button @click="modifyQtn">수정하기</button>
            <button @click="deleteQtn">삭제하기</button>
          </div>
        </div>
      </div>
      <div id="answers-title">{{ answers.length }}개의 의견이 있습니다.</div>
      <div
        id="answers-box"
        v-if="answers.length > 0">
        <tr
          class="answers-row"
          v-for="ans in answers"
          :key="ans.id">
          <div class="answers-detail-box">
            {{ ans.detail }}
            <svg
              class="best-btn"
              v-if="!question.bestSelected && user.id != question.userNo"
              @click="bestAns(ans.id)"
              width="1.4rem"
              height="1.4rem"
              viewBox="0 0 128 128"
              fill="none"
              xmlns="http://www.w3.org/2000/svg">
              <path
                d="M64 0C28.672 0 0 28.672 0 64C0 99.328 28.672 128 64 128C99.328 128 128 99.328 128 64C128 28.672 99.328 0 64 0ZM64 115.2C35.776 115.2 12.8 92.224 12.8 64C12.8 35.776 35.776 12.8 64 12.8C92.224 12.8 115.2 35.776 115.2 64C115.2 92.224 92.224 115.2 64 115.2ZM93.376 35.712L51.2 77.888L34.624 61.376L25.6 70.4L51.2 96L102.4 44.8L93.376 35.712Z"
                fill="black" />
            </svg>
            <div
              class="bested-btn"
              v-if="ans.bestSelected">
              <svg
                width="1.4rem"
                height="1.4rem"
                viewBox="0 0 128 128"
                fill="none"
                xmlns="http://www.w3.org/2000/svg">
                <path
                  d="M64 0C28.672 0 0 28.672 0 64C0 99.328 28.672 128 64 128C99.328 128 128 99.328 128 64C128 28.672 99.328 0 64 0ZM51.2 96L19.2 64L28.224 54.976L51.2 77.888L99.776 29.312L108.8 38.4L51.2 96Z"
                  fill="black" />
              </svg>
              <span class="bested-title">채택 답변</span>
            </div>

            <div
              :style="`background-image: url('${ans.profileImage || tmprespondentImage}');`"
              id="author-image"></div>

            <div id="author-name">
              <span
                id="answer-edit-delete-box"
                v-if="ans.userNo === user.id">
                <button @click="QtnUpdate(question.id)">수정</button>
                <button @click="QtnDelete(question.id)">삭제</button>
              </span>
              {{ ans.regist_time }}
              <span id="wrote-time">{{ ans.userNickname }}</span>
            </div>
          </div>
        </tr>
      </div>

      <div id="my-answer-title">질문에 대한 좋은 답변이 있으신가요?</div>
      <div id="answer-form-main">
        <Form @submit="answerQtn">
          <Field
            v-slot="{ field }"
            v-model="myAnswer"
            name="detail">
            <textarea
              id="answer-form-main-text"
              v-bind="field"
              type="text"
              name="detail"
              placeholder="친절한 답변 문화를 희망합니다."></textarea>
          </Field>
          <div id="answer-form-menu">
            <button id="answer-form-menu-btn">의견 등록</button>
          </div>
        </Form>
      </div>
      <!-- <div v-if="answers.length">
        <tr
          v-for="asr in answers"
          :key="asr.id">
          <div v-if="asr.question_id == question.id">
            <td v-if="question.best_answer === asr.id">채택됨!</td>
            <td>작성자 PK: {{ asr.user_id }}</td>
            <td>답변 내용: {{ asr.detail }}</td>
            <button
              v-if="
                question.user_id === localUser.id &&
                asr.user_id != localUser.id &&
                question.best_answer === null
              "
              @click="awardAnswer(asr.id)">
              채택하기
            </button>
            <span :id="asr.id"></span>
            <span v-if="asr.user_id === localUser.id">
              <button @click="editAnswer(asr.id, asr.detail)">수정하기</button>
              <button @click="deleteAnswer(asr.id)">삭제하기</button>
            </span>
          </div>
        </tr>
      </div> -->
    </div>
  </div>
</template>

<style>
#question-content-id {
  font-family: 'ASDGothicUL';
}
#question-question-content-title {
  display: inline-block;
  height: 30px;
  width: 900px;

  padding: 0px;
  padding-top: 30px;

  color: var(--hl-light80);
  font-size: 1.4rem;
  font-family: 'ASDGothicM';

  border-bottom: 7px solid var(--border-color);
}

#question-info {
  float: right;
  margin-top: 5px;

  font-size: 1rem;
  font-family: 'ASDGothicM';
}

#question-detail-main {
  margin-top: 20px;

  padding-top: 15px;

  width: 900px;
  height: auto;

  border-radius: 10px;
  background-color: var(--background-up);

  opacity: 1;
}

#question-detail-main-text {
  width: 840px;

  margin-left: 30px;
  margin-bottom: 50px;
  padding: 0px;

  white-space: pre-wrap;

  color: var(--hl-light);
  font-family: 'ASDGothicM';
  font-size: 1rem;
}

#content-info-box {
  position: relative;
  height: 35px;
}

#content-score {
  position: absolute;
  left: 30px;
  bottom: 12px;

  color: var(--hl-pres);
  font-size: 1.4rem;
}

#content-like-btn {
  position: absolute;
  bottom: 8px;
  left: 25px;

  cursor: pointer;
}

#content-like-btn > path {
  fill: var(--hl-light);
  transition: fill 0.4s;
}

#content-like-btn:hover > path {
  fill: var(--hl-pres);
  transition: fill 0.4s;
}

#content-dislike-btn {
  position: absolute;
  bottom: 6px;
  left: 55px;

  cursor: pointer;
}

#content-dislike-btn > g > path {
  fill: var(--hl-light);
  transition: fill 0.4s;
}

#content-dislike-btn:hover > g > path {
  fill: var(--hl-pres);
  transition: fill 0.4s;
}

#author-image {
  position: absolute;
  bottom: 12px;
  right: 30px;

  border-radius: 25px;
  width: 60px;
  height: 30px;

  /* background-image: url('/authorImage.png'); */
  background-size: cover;
  background-position: center;
  cursor: pointer;

  box-shadow: 0 2px 3px rgba(0, 0, 0, 0.16), 0 2px 3px rgba(0, 0, 0, 0.23);
}

#author-name {
  position: absolute;
  bottom: 15px;
  right: 100px;

  text-align: right;
  margin-left: 7px;

  color: var(--hl-light);
  font-family: 'ASDGothicUL';
}

#wrote-time {
  color: var(--hl-light);
  font-family: 'ASDGothicM';
}

#edit-delete-box {
  position: absolute;
  bottom: -30px;
  right: 0px;
}

#edit-delete-box > button {
  font-family: 'ASDGothicM';
  font-size: 1rem;
  color: var(--hl-light80);
  transition: color 0.4s;

  border: none;
  background-color: transparent;

  cursor: pointer;
}

#edit-delete-box > button:nth-child(2) {
  color: var(--hl-warn);
  opacity: 0.9;
}

#edit-delete-box > button:hover {
  color: var(--hl-light);
}

#answer-edit-delete-box {
  display: inline-block;
}

#answer-edit-delete-box > button {
  font-family: 'ASDGothicM';
  font-size: 0.8rem;
  color: var(--hl-light80);
  padding-left: 0px;

  border: none;
  background-color: transparent;

  cursor: pointer;
}

#answer-edit-delete-box > button:nth-child(2) {
  color: var(--hl-pres);
  opacity: 0.8;
}

#answers-box {
  width: 900px;

  margin-bottom: 38px;
  border-radius: 10px;
  background-color: var(--font50);

  border: 1px solid var(--border-color);
}

.answers-detail-box {
  position: relative;
  width: 840px;
  height: auto;

  color: var(--hl-light);

  padding: 30px;
  padding-top: 15px;
  padding-bottom: 50px;

  border-bottom: 1px solid var(--border-color);
}

#answers-title {
  margin-top: 40px;

  color: var(--hl-light);
  font-size: 1.2rem;
}

#my-answer-title {
  color: var(--hl-light);
  font-size: 1.2rem;
}

.best-btn {
  position: absolute;
  left: 30px;
  bottom: 15px;

  cursor: pointer;
}

.best-btn > path {
  fill: var(--hl-light50);
}

.bested-btn {
  position: absolute;
  left: 30px;
  bottom: 9px;
}

.bested-btn > svg > path {
  fill: var(--hl-pres);
}

.bested-title {
  display: inline-block;
  position: inherit;
  margin-top: 2px;
  margin-left: 3px;

  width: 4rem;
  color: var(--hl-pres);
}

#answer-form-main {
  padding-top: 15px;

  width: 900px;
  height: auto;

  border-radius: 10px;
  background-color: var(--background-down);
}

#answer-form-main-text {
  width: 860px;
  height: 240px;

  margin-left: 20px;
  margin-bottom: 10px;
  padding: 0px;
  padding-top: 10px;
  padding-bottom: 10px;

  color: var(--hl-light);
  font-family: 'ASDGothicM';
  font-size: 1rem;

  background-color: transparent;
  border: none;
  /* border-top: 1px solid var(--hl-light30); */
  border-bottom: 1px solid var(--hl-light30);
  outline: none;

  resize: none;
}

#answer-form-menu {
  margin-bottom: 20px;
}

#answer-form-menu-btn {
  height: 28px;
  width: 85px;
  background-color: var(--hl-pres);

  margin-top: 5px;

  float: right;

  color: var(--font80);
  transition: color 0.4s;

  font-family: 'ASDGothicM';
  font-size: 1rem;

  border: none;
  border-radius: 15px;

  cursor: pointer;
}

#answer-form-menu-btn:hover {
  color: var(--hl-light);
}
</style>
