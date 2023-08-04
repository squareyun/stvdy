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
    <p class="content-title">회원 탈퇴</p>
    <div class="content">
      <div>
        본인은 {{ localUser.username }}#{{ localUser.id }} 계정을
        삭제하겠습니다.
      </div>
      <div>
        <button @click="deactivate">계정 삭제</button>
      </div>
    </div>
  </div>
</template>
