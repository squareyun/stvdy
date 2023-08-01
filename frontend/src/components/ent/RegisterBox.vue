<script setup>
import { Form, Field } from 'vee-validate'
import { useAlertStore } from '@/stores'
import { joinUser } from '@/api/user'
import router from '@/router'
import * as Yup from 'yup'

const schema = Yup.object().shape({
  username: Yup.string()
    .required('닉네임을 작성해주세요.')
    .max(45, '길이를 줄여주세요.')
    .min(4, '더 긴 닉네임을 사용해야합니다.'),
  realname: Yup.string()
    .required('이름을 작성해주세요.')
    .max(45, '길이를 줄여주세요.')
    .min(2, '더 긴 이름을 사용해야합니다.'),
  email: Yup.string().email('이메일을 넣어주세요'),
  password: Yup.string().required('비밀번호를 넣어주세요'),
  passwordConfirm: Yup.string().oneOf(
    [Yup.ref('password'), null],
    '비밀번호가 일치하지 않습니다',
  ),
})

const emailValue = ''
const varificationEmail = async (value) => {
  console.log(value)
  // const usersStore = useUsersStore()
  // const alertStore = useAlertStore()
  // try {
  //   await usersStore.varifyEmail(value)
  //   alertStore.success('인증메일이 전송되었습니다.')
  // } catch (error) {
  //   alertStore.error(error)
  // }
}

async function onSubmit(values) {
  const user = {
    email: values.email,
    password: values.password,
    name: values.realname,
    nickname: values.username,
  }

  joinUser(
    user,
    (data) => {
      console.log(data)
      router.push('/about')
    },
    (error) => {
      console.log(error)
    },
  )
}
</script>

<template>
  <div>
    <h2 class="ents-title">회원가입</h2>
    <div>
      <Form
        autocomplete="off"
        @submit="onSubmit"
        :validation-schema="schema"
        v-slot="{ errors, isSubmitting }">
        <p class="field-name">
          &nbsp;&nbsp;별명
          <span class="error-yup">{{ errors.username }}</span>
          &nbsp;
        </p>
        <Field
          name="username"
          type="text"
          class="field"
          :class="{ 'is-invalid': errors.username }" />
        <p class="field-name">
          &nbsp;&nbsp;본명
          <span class="error-yup">{{ errors.realname }}</span>
          &nbsp;
        </p>
        <Field
          name="realname"
          type="text"
          class="field"
          :class="{ 'is-invalid': errors.realname }" />
        <div class="div-line"></div>
        <p class="field-name">
          &nbsp;&nbsp;이메일
          <span class="error-yup">{{ errors.email }}</span>
          &nbsp;
        </p>
        <Field
          name="email"
          v-model="emailValue"
          type="email"
          class="field"
          :class="{ 'is-invalid': errors.email }" />
        <button
          id="transmit-button"
          type="button"
          @click="varificationEmail(emailValue)">
          전송
        </button>
        <p class="field-name">&nbsp;&nbsp;인증번호 &nbsp;</p>
        <Field
          name="emailVarify"
          type="text"
          class="field" />
        <div class="div-line"></div>
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
        <p class="field-name">
          &nbsp;&nbsp;비밀번호 확인
          <span class="error-yup">{{ errors.passwordConfirm }}</span>
          &nbsp;
        </p>
        <Field
          name="passwordConfirm"
          type="password"
          class="field"
          :class="{ 'is-invalid': errors.passwordConfirm }" />
        <button
          id="confirm"
          :disabled="isSubmitting">
          가입하기
        </button>
      </Form>
    </div>
  </div>
</template>

<style>
.div-line {
  width: 400px;
  height: 1px;
  margin-top: 25px;
  margin-bottom: 20px;
  margin-left: 10px;

  background-color: var(--font10);
}

#transmit-button {
  position: absolute;
  top: 284px;
  right: 30px;

  border: 0;
  background-color: transparent;

  color: var(--hl-green);
  transition: color 0.45s;
  font-size: 1rem;

  cursor: pointer;
}

#transmit-button:hover {
  color: var(--font100);
  transition: color 0.45s;
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
