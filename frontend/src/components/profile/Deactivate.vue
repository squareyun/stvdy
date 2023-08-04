<script setup>
import { useAuthStore, useAlertStore, useUsersStore } from '@/stores'
import router from '@/router'
import { ref } from 'vue'

const usersStore = useUsersStore()
const localUser = usersStore.user

async function deactivate() {
  let isDeact = confirm('계정을 삭제합니다. 진행하나요?')
  if (isDeact) {
    const alertStore = useAlertStore()
    const authStore = useAuthStore()
    try {
      await authStore.deactivate()
      await router.push('/about')
      alertStore.success('계정이 삭제되었습니다.')
    } catch (error) {
      alertStore.error(error)
    }
  }
}
</script>

<template>
  <div>
    <p class="content-title">계정 삭제</p>
    <div class="content others">
      <p id="confirm-test">
        "본인은 {{ localUser.username }}#{{ localUser.id }} 계정을
        삭제하겠습니다."
      </p>

      <Form
        autocomplete="off"
        @submit="onSubmit">
        <p class="field-name">&nbsp;&nbsp;위 문장을 따라 입력해주세요 &nbsp;</p>
        <Input
          name="username"
          type="text"
          class="field" />
        <!-- <input /> -->
        <!-- <button
          id="transmite="button"
          @click="varificationEmail(localUser.email)">
          변경
        </button> -->
      </Form>

      <p id="delete-btn">삭제</p>
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
