<script setup>
import { storeToRefs } from 'pinia'
import { useQuestionsStore } from '@/stores'
import router from '@/router'
import { watch } from 'vue'

const questionsStore = useQuestionsStore()
const questions = questionsStore.questions
const category = questionsStore.category

// watch(category, () => {
//   console.log(category)
//   document.getElementById('sort-new').style.fontWeight = 700
//   document.getElementById('sort-active').style.fontWeight = 0
//   document.getElementById('sort-none').style.fontWeight = 0
// })

const sortNew = () => {
  questionsStore.sortNew()

  document.getElementById('sort-new').style.fontWeight = 700
  document.getElementById('sort-active').style.fontWeight = 100
  document.getElementById('sort-none').style.fontWeight = 100
}

const sortActive = () => {
  questionsStore.sortActive()

  document.getElementById('sort-new').style.fontWeight = 100
  document.getElementById('sort-active').style.fontWeight = 700
  document.getElementById('sort-none').style.fontWeight = 100
}

const sortNone = () => {
  questionsStore.sortNone()

  document.getElementById('sort-new').style.fontWeight = 100
  document.getElementById('sort-active').style.fontWeight = 100
  document.getElementById('sort-none').style.fontWeight = 700
}

async function showDetail(value) {
  await questionsStore.getById(value)
  questionsStore.pickedQtn = value
  router.push({ name: 'questiondetail' })
}
</script>

<template>
  <div>
    <span class="content-title">도와주세요!</span>
    <router-link
      id="add-question"
      to="/createquestion">
      + 질문 작성
    </router-link>
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

      <div v-if="questions">
        <tr
          class="question-row"
          v-for="qtn in questions"
          :key="qtn.id"
          @click="showDetail(qtn.id)">
          <td class="question-done"><div>채택안됨</div></td>
          <div class="question-title">{{ qtn.title }}</div>
          <div class="question-detail">{{ qtn.detail }}</div>
          <td>{{ qtn.regist_time }}</td>

          <div class="question-div-line"></div>
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
  padding-bottom: 30px;

  width: calc(960px - 7rem);
}

#question-sort {
  position: absolute;
  right: -6.5rem;

  font-family: 'ASDGothicT';
  color: var(--hl-light);
}

#question-sort > ul > li {
  cursor: pointer;
}

#add-question {
  float: right;
  margin-right: calc(7rem);

  text-decoration: none;
  color: var(--hl-purple);
}

.question-row {
  position: relative;
  width: 100%;
  height: 100px;

  color: var(--hl-light);
}

.question-div-line {
  position: absolute;
  left: 0px;
  bottom: 0px;
  width: 960px;
  height: 1px;

  background-color: var(--background-up);
}

.question-done {
  position: relative;
  width: 90px;
  left: 0px;
}

.question-done > div {
  position: absolute;
  top: 20px;
  right: 0px;
  padding: 0.3rem 0.6rem 0.2rem 0.6rem;

  color: var(--hl-light);
  display: inline-block;
  font-family: 'ASDGothicT';
  text-align: center;

  border: 1px solid var(--hl-purple);
  border-radius: 20px;
}

.question-title {
  position: absolute;
  top: 21px;
  left: 100px;

  width: 700px;

  font-size: 1.4rem;
}

.question-detail {
  position: absolute;
  top: 60px;
  left: 100px;

  width: 700px;

  font-family: 'ASDGothicT';
  font-size: 0.9rem;
}
</style>
