<script setup>
import * as Yup from 'yup'
import { Form, Field } from 'vee-validate'
import { useUsersStore, useAlertStore } from '@/stores'
import router from '@/router'

const schema = Yup.object().shape({
  password: Yup.string(),
  passwordConfirm: Yup.string().oneOf(
    [Yup.ref('password'), null],
    '비밀번호가 일치하지않습니다.',
  ),
})

async function onSubmit(values) {
  // test console print below
  const usersStore = useUsersStore()
  const alertStore = useAlertStore()
  const editData = values
  delete editData.passwordConfirm
  console.log(alertStore)
  // 아래 코드는 백엔드 연결시 삭제
  delete editData.prvpassword
  try {
    await usersStore.update(2, values)
    await router.push('/mypage')
    alertStore.success('비밀번호가 변경되었습니다.')
  } catch (error) {
    alertStore.error(error)
  }
}
</script>

<template>
  <div>
    <p class="content-title">비밀번호 변경</p>
    <div class="content others">
      <Form
        @submit="onSubmit"
        :validation-schema="schema"
        v-slot="{ errors, isSubmitting }">
        <p class="field-name">
          &nbsp;&nbsp;기존 비밀번호
          <span class="error-yup">{{ errors.password }}</span>
          &nbsp;
        </p>
        <Field
          name="prvpassword"
          type="password"
          class="field"
          :class="{ 'is-invalid': errors.password }" />
        <div class="div-line"></div>
        <p class="field-name">
          &nbsp;&nbsp;새 비밀번호
          <span class="error-yup">{{ errors.password }}</span>
          &nbsp;
        </p>
        <Field
          name="password"
          type="password"
          class="field"
          :class="{ 'is-invalid': errors.password }" />
        <p class="field-name">
          &nbsp;&nbsp;새 비밀번호 확인
          <span class="error-yup">{{ errors.passwordConfirm }}</span>
          &nbsp;
        </p>
        <Field
          name="passwordConfirm"
          type="password"
          class="field"
          :class="{ 'is-invalid': errors.passwordConfirm }" />
        <button
          id="password-btn"
          :disabled="isSubmitting">
          변경하기
        </button>
      </Form>
    </div>
  </div>
</template>

<style>
#password-btn {
  position: absolute;
  top: calc(50% - 0.5rem);
  right: 30px;

  padding: 0;
  border: none;
  background-color: transparent;

  font-family: 'ASDGothic';
  font-size: 1rem;
  color: var(--hl-purple);
  transition: color 0.4s;

  cursor: pointer;
}

.div-line {
  width: 400px;
  height: 1px;
  margin-top: 25px;
  margin-bottom: 20px;
  margin-left: -10px;

  background-color: var(--hl-light30);
}

#password-btn:hover {
  color: var(--hl-light);
  transition: color 0.4s;
}
</style>
