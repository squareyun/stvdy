<script setup>
import { useAuthStore, useAlertStore } from '@/stores';
import router from '@/router';


async function onSubmit(values) {
  const authStore = useAuthStore();
  const alertStore = useAlertStore();
  try {
    await authStore.passwordreset(values);
    await router.push('/login');
    alertStore.success('임시 비밀번호가 메일로 전송되었습니다.');
  } catch (error) {
    alertStore.error(error);
  }
}


</script>

<template>
  <div class="card m-3">
    <h4 class="card-header">Password Reset</h4>
    <div class="card-body">
      <Form @submit="onSubmit">
        <div class="form-group">
          <label>이름</label>
          <input name="username" type="text" class="form-control" />
        </div>
        <div class="form-group">
          <label>이메일</label>
          <input name="email" type="email" class="form-control" />
        </div>
        <div class="form-group">
          <button class="">
            비밀번호 초기화하기
          </button>
          <router-link to="login">취소하기</router-link>
        </div>


      </Form>
    </div>
  </div>
</template>
