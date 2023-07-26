<script setup>
import { useAuthStore } from '@/stores';
import { Form, Field } from 'vee-validate';
import * as Yup from 'yup';
import { useRoute } from 'vue-router';
import { storeToRefs } from 'pinia';
import { useUsersStore, useAlertStore } from '@/stores';
import { computed, onMounted } from 'vue';

const userID = JSON.parse(localStorage.getItem('user')).id;
const usersStore = useUsersStore();
const alertStore = useAlertStore();
let user = null;

// try {
//   console.log('initiated');
//   ({ user } = storeToRefs(usersStore));
//   await usersStore.getById(userID)
//   const testItem = user._object.user;
//   console.log(testItem.id);
//   user = testItem;
// } catch (error) {
//   alertStore.error(error);
// }

// const user = computed(() => {
//   try {
//     console.log('initiated');
//     ({ user } = storeToRefs(usersStore));
//     usersStore.getById(userID)
//     const testItem = user._object.user;
//     console.log(testItem.id);
//     user = testItem;
//     console.log(user);
//     return user;
//   } catch (error) {
//     alertStore.error(error);
//   }
// }
// );

onMounted(async () => {
  console.log('initiated');
  ({ user } = storeToRefs(usersStore));
  await usersStore.getById(userID)
  const testItem = user._object.user;
  console.log(testItem.id);
  user = testItem;
})


async function deactivate() {
  // const authStore = useAuthStore();
  // await authStore.deactivate();
  // console.log(user._object.user.id)
  console.log(user)
}

const schema = Yup.object().shape({
  username: Yup.string().required('이름을 작성해주세요.').max(45, '길이를 줄여주세요.').min(4, '더 긴 이름을 사용해야합니다.'),
  password: Yup.string(),
  password: Yup.string().oneOf([Yup.ref('password'), null], '비밀번호가 일치하지않습니다.')
});

</script>


<template>
  <div>
    <div id="tmp-testest">
      My Page!!!
      <div>
        <span>내 정보 수정</span>
        <Form @submit="onSubmit" :validation-schema="schema" :initial-values="user" v-slot="{ errors, isSubmitting }">
          <div>
            <label>이메일</label>
            <!-- {{ user.email }} -->
          </div>
          <div>
            <label>이름</label>
            <Field name="Username" type="text" class="form-control" :class="{ 'is-invalid': errors.username }" />
            <div class="invalid-feedback">{{ errors.username }}</div>
          </div>
          <div>
            <label>Last Name</label>
            <Field name="lastName" type="text" class="form-control" :class="{ 'is-invalid': errors.lastName }" />
            <div class="invalid-feedback">{{ errors.lastName }}</div>
          </div>
        </Form>
      </div>


      <button @click="deactivate">
        계정삭제
      </button>
    </div>

  </div>
</template>

<style scoped>
#tmp-testest {
  background-color: white;
}
</style>