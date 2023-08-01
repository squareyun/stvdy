<script setup>
import { Form, Field } from 'vee-validate';
import * as Yup from 'yup';
import { useQuestionsStore } from '../../stores/users/questions.store';
import router from '@/router';
import { ref } from 'vue';

const schema = Yup.object().shape({
  title: Yup.string().required('제목을 작성해주세요.'),
})
let question = {};
let editFlag = false;
const questionsStore = useQuestionsStore();
if (questionsStore.question.id === questionsStore.pickedQtn) {
  questionsStore.pickedQtn = null;
  question = ref(questionsStore.question)
  editFlag = true;
}

async function onSubmit(values) {
  try {
    if (editFlag) {
      console.log('go edit!')
      await questionsStore.update(question.id, values);
    } else {
      console.log('go create!')
      await questionsStore.create(values);
    }
  } catch (error) {
    console.log(error);
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
            등록하기
          </button>
          <router-link to="question">취소하기</router-link>
        </div>
      </Form>

    </div>
  </div>
</template>
