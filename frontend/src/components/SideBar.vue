<script setup>
import { RouterLink, RouterView } from 'vue-router';

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
      <router-link to="">
        내 질문(미구현)
      </router-link>
      <br>
      <router-link to="">
        알림(미구현)
      </router-link>
    </div>
  </div>
</template>
