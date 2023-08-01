<script setup>
import { storeToRefs } from 'pinia';
import { Form, Field } from 'vee-validate';
import { useQuestionsStore, useAlertStore } from '@/stores';
import { ref } from 'vue';
import router from '../../router';


const questionsStore = useQuestionsStore();
const alertStore = useAlertStore();
const { question } = storeToRefs(questionsStore);
const localUser = ref(JSON.parse(localStorage.getItem('user')));
const { answers } = storeToRefs(questionsStore);



function QtnUpdate(value) {
  questionsStore.pickedQtn = value;
  router.push('CreateQtn')
}

async function QtnDelete(value) {
  let isDelete = confirm("질문을 삭제할까요?")
  if (isDelete) {
    try {
      await questionsStore.delete(value)
      await router.push('question')
      alertStore.success('질문이 삭제되었습니다.')
    } catch (error) {
      alertStore.error(error);
    }
  }
}

async function postAnswer(value) {
  value.user_id = localUser._value.id;
  const qtnId = questionsStore.pickedQtn;
  value.question_id = qtnId;
  console.log(value)
  try {
    await questionsStore.createAnswer(value)
    await router.push('qtndetail')
    alertStore.success('답변이 등록되었습니다.')
  } catch (error) {
    alertStore.error(error);
  }

}


</script>

<template>
  <div style="color:white">
    <br>질문 상세
    <div>
      질문 번호 {{ question.id }}<br>
      질문 제목 {{ question.title }}<br>
      질문 내용 {{ question.detail }}<br>
      질문작성 시간 {{ question.regist_time }}
    </div>
    <router-link to="question">목록으로 돌아가기</router-link>
    <span v-if="question.user_id === localUser.id">
      <button @click="QtnUpdate(question.id)">
        수정하기
      </button>
      <button @click="QtnDelete(question.id)">
        삭제하기
      </button>
    </span>
    <div>
      답변
    </div>
    <Form @submit="postAnswer">
      <Field v-slot="{ field }" name="detail">
        <textarea v-bind="field" type="text" name="detail" />
      </Field>
      <button>답변 등록하기</button>
    </Form>
    <!-- v-for를 이용하여 질문ID를 Foreign key로 갖는 답변들을 이곳에 순환시킴 -->
    <div v-if="answers.length">
      <tr v-for="asr in answers" :key="asr.id">
        <div v-if="asr.question_id == question.id">
          <td>작성자 PK : {{ asr.user_id }}</td>
          <td>답변 내용: {{ asr.detail }}</td>
        </div>
      </tr>
    </div>
  </div>
</template>
