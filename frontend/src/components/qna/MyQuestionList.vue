<script setup>
import { useQuestionStore, useUserStore } from '@/stores'
import router from '@/router'
import { useRoute } from 'vue-router'
import { computed, onBeforeUnmount, watch } from 'vue'

const $route = useRoute()

const userStore = useUserStore()
const user = computed(() => userStore.user)

const questionStore = useQuestionStore()
const questions = computed(() => questionStore.questions)
const totalAmount = computed(() => questionStore.totalAmount)
const pageable = computed(() => questionStore.pageable)

let pages
watch(pageable, () => {
  let start =
    1 > pageable.value.pageNumber - 3 ? 1 : pageable.value.pageNumber - 3
  let end =
    pageable.value.totalPages < pageable.value.pageNumber + 5
      ? pageable.value.totalPages
      : pageable.value.pageNumber + 5

  pages = []
  for (let i = start; i <= end; i++) {
    pages.push(i)
  }

  console.log(pages)
})

const query = {
  userno: user.value.id,
  page: $route.params.page - 1,
}

questionStore.getMyList(query)

async function showDetail(id) {
  router.push(`/questiondetail/${id}`)
}

const pageMove = (page) => {
  const query = {
    userno: user.value.id,
    page: page - 1,
  }

  questionStore.getMyList(query)
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
    <div
      v-if="pageable.totalPages"
      id="page-list">
      <div
        class="page-nav-left"
        :class="{ deactivated: 1 === pageable.pageNumber + 1 }"
        @click="pageMove(1)">
        <svg
          width="1.2rem"
          height="1.2rem"
          viewBox="0 0 66 56"
          fill="none"
          xmlns="http://www.w3.org/2000/svg">
          <path
            d="M20 56H38L18 28L38 0H20L0 28L20 56Z"
            fill="black" />
          <path
            d="M48 56H66L46 28L66 0H48L28 28L48 56Z"
            fill="black" />
        </svg>
      </div>
      <ul>
        <li
          :class="{ active: page === pageable.pageNumber + 1 }"
          v-for="page in pageable.totalPages"
          :key="page"
          @click="pageMove(page)">
          {{ page }}
        </li>
      </ul>
      <div
        class="page-nav-right"
        :class="{
          deactivated: pageable.pageNumber + 1 === pageable.totalPages,
        }"
        @click="pageMove(pageable.totalPages)">
        <svg
          width="1.2rem"
          height="1.2rem"
          viewBox="0 0 66 56"
          fill="none"
          xmlns="http://www.w3.org/2000/svg">
          <path
            d="M46 0H28L48 28L28 56H46L66 28L46 0Z"
            fill="black" />
          <path
            d="M18 0H0L20 28L0 56H18L38 28L18 0Z"
            fill="black" />
        </svg>
      </div>
    </div>
  </div>
</template>

<style>
#page-list {
  text-align: center;
  width: 960px;
}

#page-list > div {
  position: relative;
  display: inline-block;

  top: 3px;
  cursor: pointer;
}

#page-list > div.deactivated {
  pointer-events: none;
}

#page-list > div > svg > path {
  fill: var(--hl-pres);
  opacity: 0.8;
}

#page-list > ul {
  display: inline-block;
  list-style: none;

  padding: 0 10px 0 10px;
}

#page-list > ul > li {
  margin: 10px;

  font-size: 1rem;
  display: inline;
}

#page-list > ul > li.active {
  font-family: 'ASDGothicH';
  font-size: 1rem;

  color: var(--hl-pres);
}

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
