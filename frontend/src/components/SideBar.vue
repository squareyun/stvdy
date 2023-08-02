<script setup>
import { RouterLink } from 'vue-router';
import { useQuestionsStore } from '@/stores';
import router from '@/router';


async function myQuestion() {
  const questionStore = useQuestionsStore();
  await questionStore.getMyQtn();
  router.push({ name: 'question' })
}

</script>


<template>
  <div style="color:white">
    사이드바.vue
    <div>
      <router-link to="">
        홈(미구현)
      </router-link>
      <br>
      <router-link to="question">
        질문 게시판(구현 중)
      </router-link>
      <br>
      <router-link to="mypage">
        프로필(사진외 Done)
      </router-link>
      <br>
      <a href="#" @click="myQuestion()">
        내 질문
      </a>
      <br>
      <router-link to="">
        알림(미구현)
      </router-link>
      <br>
      <router-link to="">
        환경설정(미구현)
      </router-link>
    </div>
  </div>
</template>
