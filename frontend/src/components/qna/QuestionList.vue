<script setup>
import { useQuestionStore } from '@/stores'
import router from '@/router'
import { useRoute } from 'vue-router'
import { computed, onBeforeUnmount } from 'vue'

const $route = useRoute()

const questionStore = useQuestionStore()
const questions = computed(() => questionStore.questions)
const totalAmount = computed(() => questionStore.totalAmount)

const query = {
  keyword: '',
  nickname: '',
  page: $route.params.page - 1,
  noAnsFilter: false,
  noBestAnsFilter: false,
}
console.log(query)
questionStore.getList(query)

const sortNew = () => {
  document.getElementById('sort-new').style.fontWeight = 700
  document.getElementById('sort-active').style.fontWeight = 100
  document.getElementById('sort-none').style.fontWeight = 100

  const query = {
    keyword: '',
    nickname: '',
    page: 0,
    noAnsFilter: false,
    noBestAnsFilter: false,
  }
  questionStore.getList(query)
}

const sortActive = () => {
  document.getElementById('sort-new').style.fontWeight = 100
  document.getElementById('sort-active').style.fontWeight = 700
  document.getElementById('sort-none').style.fontWeight = 100

  const query = {
    keyword: '',
    nickname: '',
    page: 0,
    noAnsFilter: false,
    noBestAnsFilter: true,
  }
  questionStore.getList(query)
}

const sortNone = () => {
  document.getElementById('sort-new').style.fontWeight = 100
  document.getElementById('sort-active').style.fontWeight = 100
  document.getElementById('sort-none').style.fontWeight = 700

  const query = {
    keyword: '',
    nickname: '',
    page: 0,
    noAnsFilter: true,
    noBestAnsFilter: false,
  }
  console.log(33, query)
  questionStore.getList(query)
}

async function showDetail(id) {
  router.push(`/questiondetail/${id}`)
}

onBeforeUnmount(() => {
  questionStore.clearState()
})
</script>

<template>
  <div>
    <span class="question-content-title">질문 게시판</span>
    <router-link
      id="add-question"
      to="/createquestion">
      + 질문 작성
    </router-link>
    <span id="total-amount">총 {{ totalAmount }} 질문</span>

    <div class="question-list">
      <div id="question-sort">
        <ul>
          <li
            id="sort-new"
            @click="sortNew()">
            최신 질문
          </li>
          <li
            @click="sortActive()"
            id="sort-active">
            해결 중
          </li>
          <li
            @click="sortNone()"
            id="sort-none">
            답변 없음
          </li>
        </ul>
      </div>

      <div>
        <tr
          class="question-row"
          v-for="(qtn, index) in questions"
          :key="qtn.id"
          @click="showDetail(qtn.id)">
          <td class="question-done">
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
          <td class="question-info">
            <p class="info-etc">
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
            class="question-div-line"></div>
        </tr>
      </div>
    </div>
  </div>
</template>

<style>
.question-list {
  position: relative;

  background-color: var(--background-window);
  border-radius: 10px;

  margin-bottom: 20px;

  width: calc(960px - 7rem);

  border: 1px solid var(--border-color);
}

#total-amount {
  position: absolute;
  right: 200px;

  font-family: 'ASDGothicUL';
}

#question-sort {
  position: absolute;
  right: -6.5rem;

  font-family: 'ASDGothicUL';
  color: var(--hl-light);
}

#question-sort > ul > li {
  cursor: pointer;
}

#sort-new {
  font-weight: 700;
}

#add-question {
  float: right;
  margin-right: calc(7rem);

  text-decoration: none;
  color: var(--hl-pres);
}

.question-row {
  position: relative;
  height: 130px;

  color: var(--hl-light);
}

.question-div-line {
  position: absolute;
  left: 20px;
  bottom: 0px;
  width: calc(920px - 7rem);
  height: 1px;

  background-color: var(--hl-light20);
}

.question-done {
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

.question-info {
  width: calc(270px - 7rem);
}

.info-etc {
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
