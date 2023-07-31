<script setup>
import { storeToRefs } from 'pinia';
import { useQuestionsStore } from '@/stores';

const questionsStore = useQuestionsStore();
const { questions } = storeToRefs(questionsStore);
questionsStore.getAll();

function testFunction1() {
  console.log('테스트펑션1 작동됨')
}
function testFunction2() {
  console.log('테스트펑션2 작동됨')
}
function testFunction3() {
  console.log('테스트펑션3 작동됨')
}

</script>

<template>
  <div style="color:white">
    질문 게시판.vue
    <router-link to="createqtn">질문 작성하기(구현 중)</router-link>
    <form action="">
      <label><input type="radio" name="listSort" value="newest" @click="testFunction1">최신순</label>
      <label><input type="radio" name="listSort" value="noAnswer" @click="testFunction2">답변없음</label>
      <label><input type="radio" name="listSort" value="answered" @click="testFunction3">활성됨</label>
    </form>
    <div v-if="questions.length">
      <tr v-for="question in questions" :key="question.id">
        <td>{{ question.id }}</td>
        <td>{{ question.title }}</td>
      </tr>

    </div>
  </div>
</template>

<style></style>