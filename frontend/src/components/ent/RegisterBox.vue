<script setup>
import { Form, Field } from 'vee-validate';
import * as Yup from 'yup';

import { useUsersStore, useAlertStore } from '@/stores';
import router from '@/router';


const schema = Yup.object().shape({
  username: Yup.string().required('닉네임을 작성해주세요.').max(45, '길이를 줄여주세요.').min(4, '더 긴 닉네임을 사용해야합니다.'),
  realname: Yup.string().required('이름을 작성해주세요.').max(45, '길이를 줄여주세요.').min(4, '더 긴 이름을 사용해야합니다.'),
  email: Yup.string().required('이메일을 넣어주세요'),
  password: Yup.string().required('비밀번호를 넣어주세요'),
  passwordConfirm: Yup.string().oneOf([Yup.ref('password'), null], '비밀번호가 일치하지 않습니다')
});


async function varificationEmail(value) {
  const usersStore = useUsersStore();
  const alertStore = useAlertStore();
  try {
    await usersStore.varifyEmail(value);
    alertStore.success('인증메일이 전송되었습니다.');
  } catch (error) {
    alertStore.error(error);
  }
  // GET, /users/register/{email}
}

async function onSubmit(values) {
  const registerData = values;
  delete registerData.passwordConfirm;
  const usersStore = useUsersStore();
  const alertStore = useAlertStore();
  try {
    await usersStore.register(values);
    await router.push('/login');
    alertStore.success('회원가입을 완료했습니다!');
  } catch (error) {
    alertStore.error(error);
  }
}


</script>

<template>
  <div class="card m-3">
    <h4 class="card-header">Registeration</h4>
    <div class="card-body">
      <Form @submit="onSubmit" :validation-schema="schema" v-slot="{ errors, isSubmitting }">
        <div class="form-group">
          <label>닉네임</label>
          <Field name="username" type="text" class="form-control" :class="{ 'is-invalid': errors.username }" />
          <div class="invalid-feedback">{{ errors.username }}</div>
        </div>
        <div class="form-group">
          <label>이름</label>
          <Field name="realname" type="text" class="form-control" :class="{ 'is-invalid': errors.username }" />
          <div class="invalid-feedback">{{ errors.realname }}</div>
        </div>
        <div class="form-group">
          <label>이메일</label>
          <Field name="email" v-model="emailValue" type="email" class="form-control"
            :class="{ 'is-invalid': errors.email }" />
          <div class="invalid-feedback">{{ errors.email }}</div>
          <button type="button" @click="varificationEmail(emailValue)">전송</button>
        </div>
        <div class="form-group">
          <label>인증번호</label>
          <Field name="emailVarify" type="text" class="form-control" />
        </div>
        <div class="form-group">
          <label>비밀번호</label>
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
            가입하기
          </button>
          <router-link to="login">취소하기</router-link>
        </div>
      </Form>
    </div>
  </div>
</template>
