<script setup>
import { useAuthStore, useAlertStore, useUserStore } from '@/stores'
import { deleteUser } from '@/api/user'
import router from '@/router'

const userStore = useUserStore()
const user = userStore.user

async function deactivate() {
  const data = user.id
  deleteUser(
    data,
    (res) => {
      console.log(res)
    },
    (fail) => {
      console.log('여기')
      console.log(fail)
      console.log('에러')
    },
  )
}
</script>

<template>
  <div>
    <p class="content-title">계정 삭제</p>
    <div class="content others">
      <p id="confirm-test">
        "본인은 {{ user.username }}#{{ user.id }} 계정을 삭제하겠습니다."
      </p>

      <Form
        autocomplete="off"
        @submit="onSubmit">
        <p class="field-name">&nbsp;&nbsp;위 문장을 따라 입력해주세요 &nbsp;</p>
        <Input
          name="username"
          type="text"
          class="field" />
      </Form>

      <p
        id="delete-btn"
        @click="deactivate">
        삭제
      </p>
    </div>
  </div>
</template>

<style>
.others > p {
  margin: 0;
}

#confirm-test {
  margin-left: 12px;
  margin-bottom: 20px;
  font-size: 1rem;
  font-family: Arial;
  font-weight: 700;

  color: var(--hl-warn);
  opacity: 0.9;
}

#delete-btn {
  position: absolute;
  top: calc(50% - 0.5rem);
  right: 30px;

  color: var(--hl-warn);
  opacity: 0.9;
  transition: all 0.4s;

  cursor: pointer;
}

#delete-btn:hover {
  color: var(--hl-light);
  opacity: 1;
  transition: all 0.4s;
}
</style>
