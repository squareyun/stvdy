<script setup>
import * as Yup from 'yup';
import { Form, Field } from 'vee-validate';
import { useUsersStore, useAlertStore } from '@/stores';
import router from '@/router'


const userID = JSON.parse(localStorage.getItem('user')).id;

const schema = Yup.object().shape({
  password: Yup.string(),
  passwordConfirm: Yup.string().oneOf([Yup.ref('password'), null], '비밀번호가 일치하지않습니다.')
});

async function onSubmit(values) {
  // test console print below
  const usersStore = useUsersStore();
  const alertStore = useAlertStore();
  const editData = values;
  delete editData.passwordConfirm;
  console.log(alertStore)
  // 아래 코드는 백엔드 연결시 삭제
  delete editData.prvpassword;
  try {
    await usersStore.update(2, values);
    await router.push('/mypage')
    alertStore.success('비밀번호가 변경되었습니다.');
  } catch (error) {
    alertStore.error(error);
  }
}
</script>

<template>
  <div style="color:white">
    비밀번호 변경
    <Form @submit="onSubmit" :validation-schema="schema" v-slot="{ errors, isSubmitting }">
      <div class="form-group">
        <label>기존 비밀번호</label>
        <Field name="prvpassword" type="password" class="form-control" :class="{ 'is-invalid': errors.password }" />
        <div class="invalid-feedback">{{ errors.password }}</div>
      </div>
      <div class="form-group">
        <label>바꿀 비밀번호</label>
        <Field name="password" type="password" class="form-control" :class="{ 'is-invalid': errors.password }" />
        <div class="invalid-feedback">{{ errors.password }}</div>
      </div>
      <div class="form-group">
        <label>비밀번호 확인</label>
        <Field name="passwordConfirm" type="password" class="form-control"
          :class="{ 'is-invalid': errors.passwordConfirm }" />
        <div class="invalid-feedback">{{ errors.passwordConfirm }}</div>
      </div>
      <div class="form-group">
        <button class="" :disabled="isSubmitting">
          변경하기
        </button>
      </div>
    </Form>
  </div>
</template>


