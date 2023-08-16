<script setup>
import { Form, Field } from 'vee-validate'
import { useAuthStore } from '@/stores'
import router from '@/router'
import * as Yup from 'yup'

const authStore = useAuthStore()

const schema = Yup.object().shape({
  email: Yup.string().email('*올바른 입력이 필요합니다.'),
  password: Yup.string().required('*비밀 번호가 필요합니다.'),
})

const onSubmit = async (values) => {
  values.keeplog = values.keeplog === 'keeplog'
  await authStore.login(values)

  if (authStore.isLogin) {
    router.push('/')
  }
}
</script>

<template>
  <div>
    <h2 class="ents-title">"만나게 되어 반가워요"</h2>
    <div class="ents">
      <Form
        autocomplete="off"
        @submit="onSubmit"
        :validation-schema="schema"
        v-slot="{ errors, isSubmitting }">
        <p class="field-name">
          &nbsp;&nbsp;이메일
          <span class="error-yup">{{ errors.email }}</span>
          &nbsp;
        </p>
        <Field
          name="email"
          type="text"
          class="field"
          :class="{ 'is-invalid': errors.email }" />
        <p class="field-name">
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
            value="keeplog"
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

.error-yup {
  color: var(--hl-warn);
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

  accent-color: var(--hl-pres);
}

#login-button {
  position: absolute;
  top: 328px;
  right: 20px;
  border: 0;
  background-color: transparent;
  cursor: pointer;
}

#login-button > svg > path {
  fill: var(--font100);
  transition: fill 0.4s;
}

#login-button:hover > svg > path {
  fill: var(--hl-green);
  transition: fill 0.4s;
}

#options {
  margin-left: 20px;
  font-size: 1.3rem;
  position: relative;
  top: 375px;
}

.link {
  color: var(--font80);
  transition: color 0.4s;

  text-underline-offset: 4px;
}

.link:hover {
  color: var(--hl-green);
  transition: color 0.4s;
}
</style>
