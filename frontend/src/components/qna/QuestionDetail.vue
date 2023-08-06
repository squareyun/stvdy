<script setup>
import { storeToRefs } from 'pinia'
import { Form, Field } from 'vee-validate'
import { useQuestionsStore, useAlertStore, useUserStore } from '@/stores'
import { ref } from 'vue'
import router from '../../router'

const questionsStore = useQuestionsStore()
const alertStore = useAlertStore()
const userStroe = userUserStore()
const { question } = storeToRefs(questionsStore)
const localUser = ref(JSON.parse(localStorage.getItem('user')))
const { answers } = storeToRefs(questionsStore)

function QtnUpdate(value) {
  questionsStore.pickedQtn = value
  router.push('CreateQtn')
}

async function QtnDelete(value) {
  let isDelete = confirm('질문을 삭제할까요?')
  if (isDelete) {
    try {
      await questionsStore.delete(value)
      await router.push('question')
      alertStore.success('질문이 삭제되었습니다.')
    } catch (error) {
      alertStore.error(error)
    }
  }
}

async function postAnswer(value) {
  value.user_id = localUser._value.id
  const qtnId = questionsStore.pickedQtn
  value.question_id = qtnId
  console.log(value)
  try {
    await questionsStore.createAnswer(value)
    await router.push('questiondetail')
    alertStore.success('답변이 등록되었습니다.')
    document.getElementById('answer-field').value = '??????'
  } catch (error) {
    alertStore.error(error)
  }
}

async function editAnswer(id, detail) {
  console.log(id)
  const editSpace = document.getElementById(id)
  const editTextarea = document.createElement('textarea')
  const cancelButton = document.createElement('button')
  // const testFunction = document.createAttribute("onclick");
  // testFunction.value = `deleteAnswer${id}`
  cancelButton.setAttribute(onClick, `deleteAnswer${id}`)
  editTextarea.innerText = detail
  cancelButton.innerText = '취소'
  // removeChild????
  editSpace.appendChild(editTextarea)
  editSpace.appendChild(cancelButton)
  console.log(detail)
}

async function deleteAnswer(id) {
  let isDelAsr = confirm('답변을 삭제할까요?')
  if (isDelAsr) {
    try {
      await questionsStore.deleteAnswer(id)
      alertStore.success('답변이 삭제되었습니다.')
    } catch (error) {
      alertStore.error(error)
    }
  }
}

async function awardAnswer(id) {
  try {
    questionsStore.awardAnswer(id)
    alertStore.success('답변이 채택되었습니다.')
  } catch (error) {
    alertStore.error(error)
  }
}
</script>

<template>
  <div style="color: white">
    <br />
    질문 상세
    <div>
      질문 번호 {{ question.id }}
      <br />
      질문 제목 {{ question.title }}
      <br />
      질문 내용 {{ question.detail }}
      <br />
      질문작성 시간 {{ question.regist_time }}
    </div>
    <router-link to="question">목록으로 돌아가기</router-link>
    <span v-if="question.user_id === localUser.id">
      <button @click="QtnUpdate(question.id)">수정하기</button>
      <button @click="QtnDelete(question.id)">삭제하기</button>
    </span>
    <div>답변</div>
    <Form @submit="postAnswer">
      <Field
        v-slot="{ field }"
        name="detail">
        <textarea
          id="answer-field"
          v-bind="field"
          type="text"
          name="detail" />
      </Field>
      <button>답변 등록하기</button>
    </Form>
    <div v-if="answers.length">
      <tr
        v-for="asr in answers"
        :key="asr.id">
        <div v-if="asr.question_id == question.id">
          <td v-if="question.best_answer === asr.id">채택됨!</td>
          <td>작성자 PK: {{ asr.user_id }}</td>
          <td>답변 내용: {{ asr.detail }}</td>
          <button
            v-if="
              question.user_id === localUser.id &&
              asr.user_id != localUser.id &&
              question.best_answer === null
            "
            @click="awardAnswer(asr.id)">
            채택하기
          </button>
          <span :id="asr.id"></span>
          <span v-if="asr.user_id === localUser.id">
            <button @click="editAnswer(asr.id, asr.detail)">수정하기</button>
            <button @click="deleteAnswer(asr.id)">삭제하기</button>
          </span>
        </div>
      </tr>
    </div>
  </div>
</template>
