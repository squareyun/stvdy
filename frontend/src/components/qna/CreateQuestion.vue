<script setup>
import { Form, Field } from 'vee-validate'
import * as Yup from 'yup'
import { useQuestionsStore, useAlertStore, useUserStore } from '@/stores'

import { writeQuestion } from '@/api/question'
import router from '@/router'

const schema = Yup.object().shape({
  title: Yup.string().required('제목을 작성해주세요.'),
})

let question = {}
// let editFlag = false
// let buttonName = ref('등록하기')
// const questionsStore = useQuestionsStore()
// const alertStore = useAlertStore()
// if (questionsStore.question.id === questionsStore.pickedQtn) {
//   questionsStore.pickedQtn = null
//   question = ref(questionsStore.question)
//   editFlag = true
//   buttonName = ref('수정하기')
// }

const userStore = useUserStore()
const user = userStore.user

async function onSubmit(values) {
  const data = {
    userNo: user.id,
    title: values.title,
    content: values.content,
    category: 'test',
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
    <div>
      <Form
        @submit="onSubmit"
        :validation-schema="schema"
        v-slot="{ errors, isSubmitting }">
        <div class="form-group">
          <label>제목</label>
          <Field
            name="title"
            v-model="question.title"
            type="text"
            class="form-control"
            :class="{ 'is-invalid': errors.title }" />
          <div class="invalid-feedback">{{ errors.title }}</div>
        </div>
        <div class="form-group">
          <label>본문</label>
          <Field
            v-slot="{ field }"
            v-model="question.content"
            name="content"
            rules="required">
            <textarea
              v-bind="field"
              name="content"></textarea>
          </Field>
        </div>
        <div class="form-group">
          <button :disabled="isSubmitting">작성</button>
          <router-link to="question">취소하기</router-link>
        </div>
      </Form>
    </div>
  </div>
</template>
