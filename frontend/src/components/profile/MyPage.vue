<script setup>
import { useAuthStore } from '@/stores';
import { Form, Field } from 'vee-validate';
import * as Yup from 'yup';
import router from '@/router'
import { storeToRefs } from 'pinia';
import { useUsersStore, useAlertStore } from '@/stores';
import { onMounted, ref } from 'vue';
import Deactivate from '@/components/profile/Deactivate.vue';
import { RouterLink, RouterView } from 'vue-router';


const localUser = ref(JSON.parse(localStorage.getItem('user')));
const userID = JSON.parse(localStorage.getItem('user')).id;


// const alertStore = useAlertStore();
// const usersStore = useUsersStore();
// let user = ref(null);

// onMounted(async () => {
//   ({ user } = storeToRefs(usersStore));
//   await usersStore.getById(userID)
//   const testItem = user._object.user;
//   user = testItem;
//   console.log(user)
// })

// const schema = Yup.object().shape({
//   username: Yup.string().max(45, '길이를 줄여주세요.').min(4, '더 긴 이름을 사용해야합니다.'),
//   password: Yup.string(),
//   passwordConfirm: Yup.string().oneOf([Yup.ref('password'), null], '비밀번호가 일치하지않습니다.')
// });

// async function onSubmit(values) {
//   // test console print below
//   console.log(values);
//   console.log(user.id);
//   const editData = values;
//   delete editData.passwordConfirm;
//   try {
//     await usersStore.update(user.id, values);
//     await router.push('/')
//     alertStore.success('회원정보가 변경되었습니다.');
//   } catch (error) {
//     alertStore.error(error);
//   }
// }

</script>


<template>
  <div>
    <div style="color:white">
      프로필
      <div>
        <img id="profile-bg-img" src="" alt="">
        <div>
          <span>
            <img id="profile-img" src="LoginBanner.png" alt="" width="100">
          </span>
          <span v-if="localUser">{{ localUser.username }}#{{ localUser.id }} </span>
          <span v-else>Loading...</span>
          <br>
          <label>본명</label>
          <span v-if="localUser"> {{ localUser.realname }} </span>
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


      <router-link to='/changeusername'>별명 변경</router-link>
      <router-link to='/changepwd'>비밀번호 변경</router-link>
    </div>
    <RouterView />
    <Deactivate />

  </div>
</template>

<style></style>
