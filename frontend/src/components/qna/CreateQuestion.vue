<script setup>
import { Form, Field } from 'vee-validate'
import * as Yup from 'yup'
import { useQuestionStore, useAlertStore, useUserStore } from '@/stores'
import { writeQuestion, modifyQuestion } from '@/api/question'
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import router from '@/router'
const $route = useRoute()

const questionStore = useQuestionStore()
let question = {}

if ($route.name == 'modifyquestion') {
  question = computed(() => questionStore.question)
  questionStore.getQuestionById($route.params.id)
}

const schema = Yup.object().shape({
  title: Yup.string().required('*제목을 작성해주세요.'),
  content: Yup.string().required('*본문을 작성해주세요.'),
})

const userStore = useUserStore()
const user = userStore.user

const openTextArea = (title) => {
  if (typeof title != 'undefined' && title.length > 0) {
    document.getElementById('question-form-main').style.opacity = 1
    document.getElementById('question-form-main-text').style.pointerEvents =
      'auto'
    document.getElementById('question-form-menu-btn').style.opacity = 1
    document.getElementById('question-form-menu-btn').style.cursor = 'pointer'
  }
}

async function onSubmit(values) {
  const data = {
    userNo: user.id,
    title: values.title,
    content: values.content,
    category: values.category,
  }

  if ($route.name == 'createquestion') {
    writeQuestion(
      data,
      (res) => {
        console.log(res)
        router.push('/question/1')
      },
      (fail) => {
        console.log(fail)
      },
    )
  } else if ($route.name == 'modifyquestion') {
    data.id = $route.params.id
    modifyQuestion(
      data,
      (res) => {
        console.log(res)
        router.push('/question/1')
      },
      (fail) => {
        console.log(fail)
      },
    )
  }
}
</script>

<template>
  <div>
    <span
      class="question-content-title"
      v-if="$route.name == 'createquestion'">
      질문 작성
    </span>
    <span
      class="question-content-title"
      v-if="$route.name == 'modifyquestion'">
      질문 수정
    </span>
    <div class="question-content">
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
        <div
          id="question-form-title-btn"
          @click="openTextArea(question.title)">
          다음
        </div>
        <div id="category-select">
          <p class="field-name">
            &nbsp;&nbsp;#카테고리 혹은 태그를 입력해주세요.
            <span class="error-yup">{{ errors.username }}</span>
            &nbsp;
          </p>
          <Field
            name="category"
            type="text"
            class="field"
            v-model="question.category" />
        </div>
        <div id="question-form-main">
          <Field
            v-slot="{ field }"
            v-model="question.detail"
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
            id="question-form-menu-cancel"
            to="question">
            취소하기
          </router-link>
          <button
            id="question-form-menu-btn"
            :disabled="isSubmitting">
            <span v-if="$route.name == 'createquestion'">작성</span>
            <span v-if="$route.name == 'modifyquestion'">수정</span>
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

  color: var(--hl-light80);
  font-size: 1.4rem;
  font-family: 'ASDGothicM';

  border: none;
  border-bottom: 7px solid var(--border-color);
  background-color: transparent;

  outline-color: transparent;
}

#question-form-title-btn {
  position: absolute;
  top: 35px;
  right: 30px;

  font-family: 'ASDGothicM';
  font-size: 1rem;

  color: var(--hl-pres);
  transition: color 0.4s;

  background-color: transparent;
  border: none;

  cursor: pointer;
}

#question-form-title-btn:hover {
  color: var(--hl-light);
  transition: color 0.4s;
}

#category-select {
  margin-top: 40px;

  /* text-align: center; */
  font-family: 'ASDGothicUL';
}

.field {
  display: block;
  background-color: var(--hl-window);
  margin-top: -12px;
  margin-bottom: 4px;
  padding-left: 18px;

  color: var(--hl-light80);

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

#question-form-main {
  margin-top: 10px;

  width: 900px;

  border-radius: 10px;
  background-color: var(--background-down);

  opacity: 0.5;
}

#question-form-main-text {
  width: 860px;
  height: 400px;

  margin-top: 15px;
  margin-left: 20px;
  margin-bottom: 10px;
  padding: 0px;
  padding-top: 10px;
  padding-bottom: 10px;

  color: var(--hl-light);
  font-family: 'ASDGothicM';
  font-size: 1rem;

  background-color: transparent;
  border: none;
  /* border-top: 1px solid var(--hl-light30); */
  border-bottom: 1px solid var(--hl-light30);
  outline: none;

  pointer-events: none;

  resize: none;
}

#question-form-menu {
  position: relative;
  margin-top: 5px;
  margin-bottom: 20px;
}

#question-form-menu-cancel {
  position: absolute;
  top: 3px;
  left: 5px;

  color: var(--hl-light80);
  font-size: 1rem;
}

#question-form-menu-btn {
  height: 28px;
  width: 50px;
  background-color: var(--hl-pres);

  float: right;

  color: var(--font100);
  transition: color 0.4s;

  font-family: 'ASDGothicM';
  font-size: 1rem;

  border: none;
  border-radius: 15px;

  opacity: 0.5;
}
</style>
