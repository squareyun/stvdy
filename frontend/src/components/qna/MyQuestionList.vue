<script setup>
import { useQuestionStore, useUserStore } from '@/stores'
import router from '@/router'
import { useRoute } from 'vue-router'
import { computed, onBeforeUnmount } from 'vue'

const $route = useRoute()

const userStore = useUserStore()
const user = computed(() => userStore.user)

const questionStore = useQuestionStore()
const questions = computed(() => questionStore.questions)
const totalAmount = computed(() => questionStore.totalAmount)

const query = {
  userno: user.value.id,
  page: $route.params.page - 1,
}

questionStore.getMyList(query)

async function showDetail(id) {
  router.push(`/questiondetail/${id}`)
}

onBeforeUnmount(() => {
  questionStore.clearState()
})
</script>

<template>
  <div>
    <span class="question-content-title">내 질문</span>
    <router-link
      id="my-add-question"
      to="/createquestion">
      + 질문 작성
    </router-link>
    <span
      id="my-total-amount"
      v-if="totalAmount">
      총 {{ totalAmount }} 질문
    </span>

    <span
      id="my-total-amount"
      v-if="!totalAmount">
      등록한 질문이 없습니다.
    </span>

    <div class="my-question-list">
      <div>
        <tr
          class="my-question-row"
          v-for="(qtn, index) in questions"
          :key="qtn.id"
          @click="showDetail(qtn.id)">
          <td class="my-question-done">
            <div
              id="best-selected"
              v-if="qtn.bestSelected">
              채택됨
            </div>
            <div
              id="best-not-selected"
              v-if="!qtn.bestSelected">
              채택안됨
            </div>
          </td>
          <td class="question-main">
            <div class="question-title">
              {{ qtn.title }}
            </div>
            <div class="question-detail">{{ qtn.detail }}</div>
          </td>
          <td class="my-question-info">
            <p class="my-info-etc">
              <span class="author">{{ qtn.userNickname }}</span>
              <span class="wrote-from">&nbsp; {{ qtn.regist_time }}</span>
            </p>
            <div class="info-status">
              <p>{{ qtn.questionScore }} 질문점수</p>
              <p>{{ qtn.cntAnswer }} 답변</p>
              <p>{{ qtn.hit }} 조회</p>
            </div>
          </td>
          <div></div>

          <div
            v-if="index != questions.length - 1"
            class="my-question-div-line"></div>
        </tr>
      </div>
    </div>
  </div>
</template>

<style>
.my-question-list {
  position: relative;

  background-color: var(--background-window);
  border-radius: 10px;

  margin-bottom: 20px;

  width: 960px;

  border: 1px solid var(--border-color);
}

#my-total-amount {
  position: absolute;
  right: calc(200px - 7rem);

  font-family: 'ASDGothicUL';
}

#my-add-question {
  float: right;

  text-decoration: none;
  color: var(--hl-pres);
}

.my-question-row {
  position: relative;
  height: 140px;

  color: var(--hl-light);
}

.my-question-div-line {
  position: absolute;
  left: 20px;
  bottom: 0px;
  width: calc(920px);
  height: 1px;

  background-color: var(--hl-light20);
}

.my-question-done {
  position: relative;
  width: 90px;
  left: 0px;
}

#best-selected {
  position: absolute;
  top: 20px;
  right: 0px;
  padding: 0.3rem 0.6rem 0.2rem 0.6rem;

  color: var(--hl-light);
  display: inline-block;
  font-family: 'ASDGothicM';
  text-align: center;

  border: 1px solid var(--hl-pres);
  border-radius: 20px;
  background-color: var(--hl-pres);
}

#best-not-selected {
  position: absolute;
  top: 20px;
  right: 0px;
  padding: 0.3rem 0.6rem 0.2rem 0.6rem;

  color: var(--hl-light);
  display: inline-block;
  font-family: 'ASDGothicUL';
  text-align: center;

  border: 1px solid var(--hl-pres);
  border-radius: 20px;
}

.question-main {
  position: relative;
  width: 600px;
}

.question-title {
  position: absolute;
  top: 22px;
  left: 10px;

  color: var(--hl-light);

  font-size: 1.3rem;

  cursor: pointer;
}

.question-detail {
  position: absolute;
  top: 53px;
  left: 10px;

  height: 60px;
  overflow: hidden;

  color: var(--hl-light);

  font-family: 'ASDGothicUL';
  font-size: 0.9rem;
}

.my-question-info {
  width: calc(270px);
}

.my-info-etc {
  position: absolute;
  right: 20px;
}

.author {
  font-size: 0.9rem;
}

.wrote-from {
  font-size: 0.9rem;
  font-family: 'ASDGothicUL';
}

.info-status {
  position: absolute;
  top: 45px;
  right: 20px;

  color: var(--hl-light);

  text-align: right;
}

.info-status > p {
  margin: 0px;
  font-family: 'ASDGothicUL';
  font-size: 0.9rem;
}

.info-status > p:first-child {
  font-family: 'ASDGothicM';
  color: var(--hl-pres);
}
</style>
