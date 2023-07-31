<script setup>
import { Form, Field } from 'vee-validate';
import * as Yup from 'yup';
import { useQuestionsStore } from '../../stores/users/questions.store';
import router from '@/router';
import { ref } from 'vue';

const schema = Yup.object().shape({
  title: Yup.string().required('제목을 작성해주세요.'),
})

const testValue = ref('YYYYEEEAAAAHH')

async function onSubmit(values) {
  const questionsStore = useQuestionsStore();
  try {
    await questionsStore.create(values);
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
          <Field name="title" v-model="testValue" type="text" class="form-control"
            :class="{ 'is-invalid': errors.title }" />
          <div class="invalid-feedback">{{ errors.title }}</div>
        </div>
        <div class="form-group">
          <label>본문</label>
          <Field v-slot="{ field }" name="detail" rules="required">
            <textarea v-bind="field" name="detail" />
          </Field>
        </div>
        <div class="form-group">
          <button class="" :disabled="isSubmitting">
            등록하기
          </button>
          <router-link to="question">취소하기</router-link>
        </div>
      </Form>

    </div>
  </div>
</template>
