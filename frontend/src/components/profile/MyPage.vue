<script setup>
import { useUserStore } from '@/stores'
import { nameUser } from '@/api/user'
import Deactivate from '@/components/profile/Deactivate.vue'
import { RouterLink, RouterView } from 'vue-router'
import { computed } from 'vue'
import { Form, Field } from 'vee-validate'
import * as Yup from 'yup'
import router from '@/router'

const userStore = useUserStore()
const user = computed(() => userStore.user)

let nameWant = ''
nameWant = user.value.username

const changeUserName = async (name) => {
  const data = {
    nickname: name,
  }

  await nameUser(
    data,
    (res) => {
      console.log(res)
      user.value.username = name
    },
    (fail) => {
      console.log(fail)
    },
  )
  router.go('mypage')
}

const schema = Yup.object().shape({
  username: Yup.string()
    .required('닉네임을 작성해주세요.')
    .max(45, '길이를 줄여주세요.')
    .min(4, '더 긴 닉네임을 사용해야합니다.'),
})
</script>

<template>
  <div>
    <span class="mypage-content-title">프로필</span>
    <div class="mypage-content">
      <div id="user-background-img"></div>
      <div id="user-profile-img"></div>
      <h1 id="user-name">{{ user.username }}#{{ user.id }}</h1>
      <div id="user-edit">
        <Form
          autocomplete="off"
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
            :class="{ 'is-invalid': errors.username }"
            v-model="nameWant" />
          <button
            id="transmit-button"
            type="button"
            :disabled="isSubmitting"
            @click="changeUserName(nameWant)">
            변경
          </button>
          <p class="field-name">&nbsp;&nbsp;본명 &nbsp;</p>
          <Field
            name="realname"
            type="text"
            class="field disabled"
            :class="{ 'is-invalid': errors.realname }"
            v-model="user.realname"
            disabled />
          <p class="field-name">&nbsp;&nbsp;이메일 &nbsp;</p>
          <Field
            name="email"
            type="email"
            class="field disabled"
            :class="{ 'is-invalid': errors.email }"
            v-model="user.email"
            disabled />
        </Form>
      </div>

      <div id="edit-menu">
        <a href="">대표 이미지(스터디룸) 변경</a>
        <a href="">프로필 이미지 변경</a>
        <router-link to="/mypage/changepwd">비밀번호 변경</router-link>
        <router-link to="/mypage/changecolor">레이아웃 색상 설정</router-link>
      </div>
    </div>
    <RouterView />
    <Deactivate />
  </div>
</template>

<style>
#user-background-img {
  width: 960px;
  height: 220px;

  border-radius: 10px 10px 0px 0px;

  margin-left: -30px;

  background-image: url('/testBackground.png');
  background-size: cover;
  background-position: center;
}

#user-profile-img {
  position: absolute;
  top: 170px;
  left: 30px;

  width: 200px;
  height: 100px;

  border-radius: 50px;

  background-image: url('/testProfile.png');
  background-size: cover;
  background-position: center;

  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(0, 0, 0, 0.23);
}

#user-name {
  margin-top: 5px;
  margin-left: 210px;

  color: var(--hl-light);
}

#edit-menu {
  position: absolute;
  top: 230px;
  right: 30px;

  text-align: right;
}

#user-edit {
  position: relative;
}

.field {
  display: block;
  background-color: var(--hl-window);
  margin-top: -12px;
  margin-bottom: 4px;
  padding-left: 18px;

  color: var(--hl-light);

  font-size: 1rem;

  width: 360px;
  height: 46px;
  border-radius: 10px;
  border: 1px solid var(--hl-light30);
}

.field:focus {
  outline: none;
  box-shadow: 0 0 0 1px var(--font80) inset;
}

.field-name {
  position: relative;
  display: inline;
  margin-left: 11px;

  /* font-size: 14px; */
  font-size: 0.9rem;

  text-align: center;
  color: var(--hl-light);

  background-color: var(--background-window);
}

#name-transmit-button {
  position: absolute;
  top: 20px;
  left: 326px;

  border: 0;
  background-color: transparent;

  color: var(--hl-pres);
  transition: color 0.4s;
  font-size: 1rem;

  cursor: pointer;
}

#name-transmit-button:hover {
  color: var(--hl-light);
  transition: color 0.4s;
}

.error-yup {
  color: var(--hl-warn);
}
#edit-menu > a {
  display: block;
  margin-bottom: 0.3rem;

  color: var(--hl-pres);
  transition: color 0.4s;

  font-size: 1rem;
  text-decoration: none;
}

#edit-menu > a:hover {
  color: var(--hl-light);
  transition: color 0.4s;
}

.others {
  padding-top: 30px;
}

.disabled {
  color: var(--hl-light50);
}
</style>
