<script setup>
import { Form, Field } from 'vee-validate';
import * as Yup from 'yup';
import router from '@/router';

const schema = Yup.object().shape({
  title: Yup.string().required('제목을 작성해주세요.'),
})


async function onSubmit(values) {
  console.log(values)
}

</script>

<template>
  <div style="color:white">
    질문 작성.vue
    <div>
      <Form @submit="onSubmit" :validation-schema="schema" v-slot="{ errors, isSubmitting }">
        <div class="form-group">
          <label>제목</label>
          <Field name="title" type="text" class="form-control" :class="{ 'is-invalid': errors.title }" />
          <div class="invalid-feedback">{{ errors.title }}</div>
        </div>
        <div class="form-group">
          <label>본문</label>
          <Field v-slot="{ field }" name="content" rules="required">
            <textarea v-bind="field" name="content" />
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
