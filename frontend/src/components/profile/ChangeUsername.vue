<script setup>
import * as Yup from 'yup';
import { Form, Field } from 'vee-validate';
import { useUsersStore, useAlertStore } from '@/stores';
import router from '@/router'


const userID = JSON.parse(localStorage.getItem('user')).id;

const schema = Yup.object().shape({
  username: Yup.string().max(45, '길이를 줄여주세요.').min(4, '더 긴 이름을 사용해야합니다.'),
});

async function onSubmit(values) {
  // test console print below
  const usersStore = useUsersStore();
  const alertStore = useAlertStore();
  try {
    await usersStore.update(2, values);
    await router.push('/mypage')
    alertStore.success('닉네임이 변경되었습니다.');
  } catch (error) {
    alertStore.error(error);
  }
}
</script>

<template>
  <div style="color:white">
    닉네임 변경
    <Form @submit="onSubmit" :validation-schema="schema" v-slot="{ errors, isSubmitting }">
      <div class="form-group">
        <label>닉네임</label>
        <Field name="username" type="text" class="form-control" :class="{ 'is-invalid': errors.username }" />
        <div class="invalid-feedback">{{ errors.username }}</div>
      </div>
      <div class="form-group">
        <button class="" :disabled="isSubmitting">
          변경하기
        </button>
      </div>
    </Form>
  </div>
</template>


