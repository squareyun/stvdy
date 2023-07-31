<script setup>
import { storeToRefs } from 'pinia';
import { useQuestionsStore } from '@/stores';
import { ref } from 'vue';
import router from '../../router';


const questionsStore = useQuestionsStore();
const { question } = storeToRefs(questionsStore);
const localUser = ref(JSON.parse(localStorage.getItem('user')));

async function QtnUpdate(value) {
  questionsStore.question = value;
  console.log(questionsStore.question)
  router.push('CreateQtn')
}

</script>

<template>
  <div style="color:white">
    <br>질문 상세
    <div>
      질문 번호 {{ question.id }}<br>
      질문 제목 {{ question.title }}<br>
      질문 내용 {{ question.detail }}<br>
      질문작성 시간 {{ question.regist_time }}
    </div>
    <router-link to="question">목록으로 돌아가기</router-link>
    <span v-if="question.user_id === localUser.id" @click="QtnUpdate(question.id)">
      수정하기
    </span>
    <div>
      답변
    </div>
  </div>
</template>
