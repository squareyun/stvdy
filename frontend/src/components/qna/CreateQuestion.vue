<script setup>
import { Form, Field } from 'vee-validate'
import * as Yup from 'yup'
import { useQuestionStore, useAlertStore, useUserStore } from '@/stores'

import { writeQuestion } from '@/api/question'
import router from '@/router'

const schema = Yup.object().shape({
  title: Yup.string().required('제목을 작성해주세요.'),
  content: Yup.string().required('본문을 작성해주세요.'),
})

let question = {}

const userStore = useUserStore()
const user = userStore.user

const openTextArea = (title) => {
  if (typeof title != 'undefined' && title.length > 0) {
    document.getElementById('question-form-main').style.opacity = 1
    document.getElementById('question-form-main-text').style.pointerEvents =
      'auto'
    document.getElementById('question-form-main-btn').style.opacity = 1
    document.getElementById('question-form-main-btn').style.cursor = 'pointer'
  }
}

async function onSubmit(values) {
  const data = {
    userNo: user.id,
    title: values.title,
    content: values.content,
    category: '알고리즘',
  }

  console.log(data)

  writeQuestion(
    data,
    (res) => {
      console.log(res)
    },
    (fail) => {
      console.log(fail)
    },
  )
}
</script>

<template>
  <div>
    질문 작성
    <div class="content">
      <Form
        id="question-form"
        @submit="onSubmit"
        :validation-schema="schema"
        v-slot="{ errors, isSubmitting }">
        <Field
          name="title"
          v-model="question.title"
          type="text"
          id="question-form-title"
          placeholder="제목을 입력해주세요."
          :class="{ 'is-invalid': errors.title }" />
        <button
          id="question-form-title-btn"
          @click="openTextArea(question.title)">
          다음
        </button>
        <div id="question-form-main">
          <Field
            v-slot="{ field }"
            v-model="question.content"
            name="content"
            rules="required"
            :class="{ 'is-invalid': errors.content }">
            <textarea
              placeholder="ex) 6.2전쟁은 몇년 도에 발발 했나요?"
              id="question-form-main-text"
              v-bind="field"
              name="content"></textarea>
          </Field>
        </div>
        <div id="question-form-menu">
          <router-link
            id="question-form-main-cancel"
            to="question">
            취소하기
          </router-link>
          <button
            id="question-form-main-btn"
            :disabled="isSubmitting">
            작성
          </button>
        </div>
      </Form>
    </div>
  </div>
</template>

<style>
#question-form {
  padding-top: 30px;
  width: 900px;
}

#question-form-title {
  height: 30px;
  width: 900px;

  padding: 0px;

  color: var(--hl-light);
  font-size: 1.4rem;
  font-family: 'ASDGothicM';

  border: none;
  border-bottom: 7px solid var(--font30);
  background-color: transparent;

  outline-color: transparent;
}

#question-form-title-btn {
  position: absolute;
  top: 72px;
  right: 30px;

  font-family: 'ASDGothicM';
  font-size: 1.2rem;

  color: var(--hl-purple);
  transition: color 0.4s;

  background-color: transparent;
  border: none;

  cursor: pointer;
}

#question-form-title-btn:hover {
  color: var(--hl-light);
  transition: color 0.4s;
}
#question-form-main {
  margin-top: 50px;

  width: 900px;

  border-radius: 10px;
  background-color: var(--background-up);

  opacity: 0.5;
}

#question-form-main-text {
  width: 840px;
  height: 600px;

  margin-top: 15px;
  margin-left: 30px;
  margin-bottom: 15px;
  padding: 0px;

  color: var(--hl-light);
  font-family: 'ASDGothicM';
  font-size: 1rem;

  background-color: transparent;
  border: none;
  outline: none;

  pointer-events: none;
}

#question-form-menu {
  position: relative;
  margin-top: 5px;
  margin-bottom: 20px;
}

#question-form-main-cancel {
  position: absolute;
  top: 3px;
  left: 5px;

  color: var(--hl-light80);
  font-size: 1.2rem;
}

#question-form-main-btn {
  height: 30px;
  width: 60px;
  background-color: var(--hl-purple);

  float: right;

  color: var(--font80);
  transition: color 0.4s;

  font-family: 'ASDGothicM';
  font-size: 1.2rem;

  border: none;
  border-radius: 15px;

  opacity: 0.5;
}
</style>
