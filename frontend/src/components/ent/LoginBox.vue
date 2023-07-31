<script setup>
import { Form, Field } from 'vee-validate'
import * as Yup from 'yup'

import { useAuthStore } from '@/stores'

const schema = Yup.object().shape({
  username: Yup.string().email('*올바른 입력이 필요합니다.'),
  password: Yup.string().required('*올바른 입력이 필요합니다.'),
})

async function onSubmit(values) {
  const authStore = useAuthStore()
  if (values.keeplog === 'keeplogon') {
    values.keeplog = true
  } else {
    values.keeplog = false
  }
  const { username, password, keeplog } = values
  await authStore.login(username, password, keeplog)
}
</script>

<template>
  <div>
    <h2 class="ents-title">"다시 만나 반가워요"</h2>
    <div id="login-form">
      <Form
        @submit="onSubmit"
        :validation-schema="schema"
        v-slot="{ errors, isSubmitting }">
        <p
          class="field-name"
          id="name-email">
          &nbsp;&nbsp;메일
          <span class="error-yup">{{ errors.username }}</span>
          &nbsp;
        </p>
        <Field
          name="username"
          type="text"
          class="field"
          :class="{ 'is-invalid': errors.username }" />
        <p
          class="field-name"
          id="name-pwd">
          &nbsp;&nbsp;비밀번호
          <span class="error-yup">{{ errors.password }}</span>
          &nbsp;
        </p>
        <Field
          name="password"
          type="password"
          class="field"
          :class="{ 'is-invalid': errors.password }" />
        <div id="keep-login">
          <!-- 로그인 유지 - 쿠키유지 로직 -->
          로그인 유지
          <Field
            name="keeplog"
            type="checkbox"
            value="keeplogon"
            class="form-control" />
          <button
            id="login-button"
            :disabled="isSubmitting">
            <svg
              width="64"
              height="64"
              viewBox="0 0 128 128"
              xmlns="http://www.w3.org/2000/svg">
              <path
                d="M57.6 28.4444L48.64 38.4L65.28 56.8889H0V71.1111H65.28L48.64 89.6L57.6 99.5556L89.6 64L57.6 28.4444ZM115.2 113.778H64V128H128V0H64V14.2222H115.2V113.778Z" />
            </svg>
          </button>
        </div>
      </Form>
    </div>
    <div id="options">
      <router-link
        class="link"
        to="regist">
        회원가입
      </router-link>
      <br />
      <router-link
        class="link"
        to="passwordReset">
        비밀번호를 잊으셨나요?
      </router-link>
    </div>
  </div>
</template>

<style>
#login-window {
  display: flex;
  align-items: center;
}

#login-form {
  position: relative;
  margin-top: 25px;
}

.field-name {
  position: relative;
  display: inline;
  margin-left: 31px;

  color: var(--font80);

  /* font-size: 14px; */
  font-size: 0.75vw;

  text-align: center;

  background-color: var(--hl-light);
}

.error-yup {
  color: var(--hl-warn);
}

.field {
  /* position: relative;
  top: 50px; */
  background-color: var(--hl-light);
  margin-left: 20px;
  margin-top: -12px;
  padding-left: 18px;

  color: var(--font100);

  width: 360px;
  height: 46px;
  border-radius: 10px;
  border-style: solid;
  border-width: 1px;
  font-size: 16px;
  margin-bottom: 10px;
}

#keep-login {
  margin-left: 20px;

  color: var(--font80);
}

input[type='checkbox'] {
  position: relative;
  margin-left: -1px;
  top: 2px;
  width: 15px;
  height: 15px;

  accent-color: var(--hl-purple);
}

#login-button {
  position: absolute;
  top: 216px;
  right: 20px;
  border: 0;
  background-color: transparent;
  cursor: pointer;
}

path {
  fill: var(--font80);
}

#options {
  margin-left: 20px;
  font-size: 18px;
  position: relative;
  top: 375px;
}

.link {
  color: var(--font80);
}
</style>
