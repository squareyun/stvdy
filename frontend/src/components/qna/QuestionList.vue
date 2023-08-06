<script setup>
import { storeToRefs } from 'pinia'
import { useQuestionsStore } from '@/stores'
import router from '@/router'

const questionsStore = useQuestionsStore()
const { questions } = storeToRefs(questionsStore)
questionsStore.getAll()

function sortMostRecent() {
  questionsStore.sortMostRecent()
}

function sortNoAnswer() {
  questionsStore.sortNoAnswer()
}

function sortActivated() {
  questionsStore.sortActivated()
}

async function showDetail(value) {
  await questionsStore.getById(value)
  questionsStore.pickedQtn = value
  router.push({ name: 'qtndetail' })
}
</script>

<template>
  <div>
    <p class="content-title">새 질문</p>
    <router-link to="/createquestion">비밀번호 변경</router-link>
    <div class="content">
      <form action="">
        <label>
          <input
            type="radio"
            name="listSort"
            value="newest"
            @click="sortMostRecent" />
          최신순
        </label>
        <label>
          <input
            type="radio"
            name="listSort"
            value="noAnswer"
            @click="sortNoAnswer" />
          답변없음
        </label>
        <label>
          <input
            type="radio"
            name="listSort"
            value="answered"
            @click="sortActivated" />
          활성됨
        </label>
      </form>

      <div v-if="questions.length">
        <tr
          v-for="qtn in questions"
          :key="qtn.id"
          @click="showDetail(qtn.id)">
          <td>{{ qtn.id }}</td>
          <td>{{ qtn.title }}</td>
          <td>{{ qtn.detail }}</td>
          <td>{{ qtn.regist_time }}</td>
        </tr>
      </div>
    </div>
  </div>
</template>

<style></style>
