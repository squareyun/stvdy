<script setup>
import { Form, Field } from 'vee-validate'
import { useQuestionStore, useAlertStore, useUserStore } from '@/stores'
import { computed, watch } from 'vue'
import { useRoute } from 'vue-router'
import router from '../../router'

const userStore = useUserStore()

const $route = useRoute()

const questionStore = useQuestionStore()
const question = computed(() => questionStore.question)
questionStore.getQuestionById($route.params.id)
</script>

<template>
  <div v-if="question">
    <span class="content-title">질문</span>
    <span id="question-content-id">#{{ question.id }}</span>
    <div class="content">
      <span id="question-content-title">{{ question.title }}</span>
      <div id="question-detail-main">
        <div
          placeholder="ex) 6.2전쟁은 몇년 도에 발발 했나요?"
          id="question-detail-main-text"
          name="content">
          {{ question.detail }}
        </div>
        <div id="author-box">
          <div id="author-image"></div>
          <div id="author-name">
            {{ question.regist_time }}
            <span id="wrote-time">{{ question.userNickname }}</span>
          </div>
        </div>
      </div>
      <!-- <span v-if="question.user_id === localUser.id">
        <button @click="QtnUpdate(question.id)">수정하기</button>
        <button @click="QtnDelete(question.id)">삭제하기</button>
      </span> -->
      <div id="answer-write-title">질문에 대한 좋은 답변이 있으신가요?</div>
      <div id="answer-form-main">
        <Form @submit="postAnswer">
          <Field
            v-slot="{ field }"
            name="detail">
            <textarea
              id="answer-form-main-text"
              v-bind="field"
              type="text"
              name="detail"
              placeholder="친절한 답변 문화를 희망합니다."></textarea>
          </Field>
          <div id="answer-form-menu">
            <button id="answer-form-menu-btn">답변 등록</button>
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
  font-family: 'ASDGothicT';
}
#question-content-title {
  display: inline-block;
  height: 30px;
  width: 900px;

  padding: 0px;
  padding-top: 30px;

  color: var(--hl-light);
  font-size: 1.4rem;
  font-family: 'ASDGothicM';

  border-bottom: 7px solid var(--font30);
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

#author-box {
  position: relative;
  height: 35px;
}

#author-image {
  position: absolute;
  bottom: 12px;
  right: 30px;

  border-radius: 25px;
  width: 60px;
  height: 30px;

  background-image: url('/authorImage.png');
  background-size: cover;
  background-position: center;
  cursor: pointer;

  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(0, 0, 0, 0.23);
}

#author-name {
  position: absolute;
  top: -4px;
  right: 100px;

  text-align: right;
  margin-left: 7px;

  font-family: 'ASDGothicT';
}

#wrote-time {
  font-family: 'ASDGothicM';
}

#answer-write-title {
  margin-top: 40px;

  font-size: 1.2rem;
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
}

#answer-form-menu {
  margin-bottom: 20px;
}

#answer-form-menu-btn {
  height: 28px;
  width: 85px;
  background-color: var(--hl-purple);

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
</style>
