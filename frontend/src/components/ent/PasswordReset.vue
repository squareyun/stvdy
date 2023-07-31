<script setup>
import { Form, Field } from 'vee-validate'
import { useAuthStore, useAlertStore } from '@/stores'
import router from '@/router'
import * as Yup from 'yup'

const schema = Yup.object().shape({
  email: Yup.string().email('*올바른 입력이 필요합니다.'),
})

const onSubmit = async (values) => {
  const authStore = useAuthStore()
  const alertStore = useAlertStore()
  try {
    await authStore.passwordreset(values)
    await router.push('/login')
    alertStore.success('임시 비밀번호가 메일로 전송되었습니다.')
  } catch (error) {
    alertStore.error(error)
  }
}
</script>

<template>
  <div>
    <h2 class="ents-title">비밀번호 초기화</h2>
    <div>
      <div id="slogan">" 괜찮아요. 저희가 찾아드릴게요..! "</div>
      <Form
        autocomplete="off"
        @submit="onSubmit"
        :validation-schema="schema"
        v-slot="{ errors, isSubmitting }">
        <p class="field-name">&nbsp;&nbsp;본명 &nbsp;</p>
        <Field
          name="username"
          type="text"
          class="field" />
        <p class="field-name">
          &nbsp;&nbsp;이메일
          <span class="error-yup">{{ errors.email }}</span>
          &nbsp;
        </p>
        <Field
          name="email"
          type="email"
          class="field"
          :class="{ 'is-invalid': errors.email }" />
        <button
          id="confirm"
          :disabled="isSubmitting">
          초기화
        </button>
      </Form>
    </div>
  </div>
</template>

<style>
#slogan {
  width: 420px;
  height: 70px;

  padding-top: 170px;

  color: var(--font50);
  font-size: 1.65rem;
  text-align: center;
}
#confirm {
  position: absolute;
  right: 15px;
  bottom: 40px;

  border: 0;
  background-color: transparent;

  color: var(--font100);
  transition: color 0.45s;

  font-size: 1.2rem;
  font-weight: 700;

  cursor: pointer;
}

#confirm:hover {
  color: var(--hl-green);
  transition: color 0.45s;
}
</style>
