<script setup>
import { RouterLink } from 'vue-router'
import { useQuestionsStore } from '@/stores'
import router from '@/router'

async function myQuestion() {
  const questionStore = useQuestionsStore()
  await questionStore.getMyQtn()
  router.push({ name: 'question' })
}
</script>

<template>
  <div id="side-panel">
    <div>
      <router-link
        cl
        to="">
        홈(미구현)
      </router-link>
    </div>
    <div><router-link to="question">강의 정보 공유(미구현)</router-link></div>
    <div><router-link to="question">질문 게시판(구현 중)</router-link></div>
    <div class="div-line"></div>
    <div>
      <router-link to="mypage">프로필(사진외 Done)</router-link>
    </div>
    <div>
      <a
        href="#"
        @click="myQuestion()">
        내 질문
      </a>
    </div>
    <div>
      <router-link to="">알림(미구현)</router-link>
    </div>
    <div><router-link to="">환경설정(미구현)</router-link></div>
  </div>
</template>

<style>
#side-panel {
  position: fixed;
  left: 0;
  top: 80px;
  width: 250px;
  height: calc(100vh - 80px);
  /* 
  background-color: rgb(65, 65, 65); */
  color: var(--hl-light);
}

#side-panel a {
  color: var(--hl-light);
  text-decoration: none;
}

#side-panel div {
  margin-left: 20px;
  margin-top: 10px;
  font-size: 1.2rem;
}

.div-line {
  width: 160px;
  height: 1px;
  margin-top: 20px;
  margin-bottom: 30px;

  background-color: var(--hl-light);
  opacity: 0.5;
}
</style>
