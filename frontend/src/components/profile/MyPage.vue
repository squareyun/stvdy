<script setup>
import { onMounted, computed } from 'vue'
import { useUsersStore } from '@/stores'
import Deactivate from '@/components/profile/Deactivate.vue'
import { RouterLink, RouterView } from 'vue-router'

const usersStore = useUsersStore()
const localUser = usersStore.user
</script>

<template>
  <div>
    <div style="color: white">
      {{ localUser }}
      프로필
      <div>
        <img
          id="profile-bg-img"
          src=""
          alt="" />
        <div>
          <span>
            <!-- <img
              id="profile-img"
              src="/LoginBanner.png"
              alt=""
              width="100" /> -->
          </span>
          <span v-if="localUser">
            {{ localUser.username }}#{{ localUser.id }}
          </span>
          <span v-else>Loading...</span>
          <br />
          <label>본명</label>
          <span v-if="localUser">{{ localUser.realname }}</span>
          <span v-else>Loading...</span>
        </div>
        <div>
          <label>별명</label>
          <span v-if="localUser">{{ localUser.username }}</span>
          <span v-else>Loading...</span>
        </div>
        <div>
          <label>이메일</label>
          <span v-if="localUser">{{ localUser.email }}</span>
          <span v-else>Loading...</span>
        </div>

        <!-- <Form @submit="onSubmit" :validation-schema="schema" v-slot="{ errors, isSubmitting }">
          <div>
            <label>닉네임</label>
            <span v-if="user">
              <Field name="username" type="text" class="form-control" :class="{ 'is-invalid': errors.username }"
                v-model="user.username" />
            </span>
            <span v-else>Loading...</span>
            <div class="invalid-feedback">{{ errors.username }}</div>
          </div>
          <div class="form-group">
            <label>비밀번호</label>
            <Field name="password" type="password" class="form-control" :class="{ 'is-invalid': errors.password }" />
            <div class="invalid-feedback">{{ errors.password }}</div>
          </div>
          <div class="form-group">
            <label>비밀번호 확인</label>
            <Field name="passwordConfirm" type="password" class="form-control"
              :class="{ 'is-invalid': errors.passwordConfirm }" />
            <div class="invalid-feedback">{{ errors.passwordConfirm }}</div>
          </div>
          <div class="form-group">
            <button class="" :disabled="isSubmitting">
              수정하기
            </button>
          </div>
        </Form> -->
      </div>

      <a href="">대표 이미지 변경</a>
      <router-link to="/changeusername">별명 변경</router-link>
      <router-link to="/changepwd">비밀번호 변경</router-link>
    </div>
    <!-- <RouterView />
    <Deactivate /> -->
  </div>
</template>

<style></style>
