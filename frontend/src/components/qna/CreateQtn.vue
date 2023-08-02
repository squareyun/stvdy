<script setup>
import { Form, Field } from 'vee-validate';
import * as Yup from 'yup';
import { useQuestionsStore, useAlertStore } from '@/stores';
import router from '@/router';
import { ref } from 'vue';

const schema = Yup.object().shape({
  title: Yup.string().required('제목을 작성해주세요.'),
})

let question = {};
let editFlag = false;
let buttonName = ref('등록하기')
const questionsStore = useQuestionsStore();
const alertStore = useAlertStore();
if (questionsStore.question.id === questionsStore.pickedQtn) {
  questionsStore.pickedQtn = null;
  question = ref(questionsStore.question)
  editFlag = true;
  buttonName = ref('수정하기')
}


async function onSubmit(values) {
  try {
    if (editFlag) {
      await questionsStore.update(question.id, values);
      await router.push({ name: 'qtndetail' })
      alertStore.success('수정이 완료되었습니다.')
    } else {
      await questionsStore.create(values);
      alertStore.success('질문이 등록되었습니다.')
    }
  } catch (error) {
    alertStore.error(error);
  }
}

</script>

<template>
  <div style="color:white">
    질문 작성.vue
    <div>
      <Form @submit="onSubmit" :validation-schema="schema" v-slot="{ errors, isSubmitting }">
        <div class="form-group">
          <label>제목</label>
          <Field name="title" v-model="question.title" type="text" class="form-control"
            :class="{ 'is-invalid': errors.title }" />
          <div class="invalid-feedback">{{ errors.title }}</div>
        </div>
        <div class="form-group">
          <label>본문</label>
          <Field v-slot="{ field }" v-model="question.detail" name="detail" rules="required">
            <textarea v-bind="field" name="detail" />
          </Field>
        </div>
        <div class="form-group">
          <button :disabled="isSubmitting">
            {{ buttonName }}
          </button>
          <router-link to="question">취소하기</router-link>
        </div>
      </Form>

    </div>
  </div>
</template>
