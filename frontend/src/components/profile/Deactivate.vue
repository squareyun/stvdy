<script setup>
import { useAuthStore, useAlertStore, useUserStore } from '@/stores'
import { computed, ref } from 'vue'
import router from '@/router'

const userStore = useUserStore()
const user = computed(() => userStore.user)

const deleteConfirm = ref('')

async function deactivate() {
  if (
    deleteConfirm.value ==
    `본인은 ${user.value.username}#${user.value.id} 계정을 삭제하겠습니다.`
  )
    await userStore.deleteAccount()
}
</script>

<template>
  <div>
    <span class="mypage-content-title">계정 삭제</span>
    <div class="mypage-content others">
      <p id="confirm-test">
        "본인은 {{ user.username }}#{{ user.id }} 계정을 삭제하겠습니다."
      </p>

      <form autocomplete="off">
        <p class="field-name">&nbsp;&nbsp;위 문장을 따라 입력해주세요 &nbsp;</p>
        <input
          v-model="deleteConfirm"
          type="text"
          class="field" />
      </form>
      <p
        id="delete-btn"
        @click="deactivate()">
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
