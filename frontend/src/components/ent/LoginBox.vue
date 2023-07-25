<script setup>
import { Form, Field } from 'vee-validate';
import * as Yup from 'yup';

import { useAuthStore } from '@/stores';

const schema = Yup.object().shape({
    username: Yup.string().required('이메일 입력이 필요합니다.'),
    password: Yup.string().required('비밀번호 입력이 필요합니다.')
});

async function onSubmit(values) {
    const authStore = useAuthStore();
    const { username, password } = values;
    console.log('>>>>여기 도착<<<<<')
    await authStore.login(username, password);
}
</script>

<template>
    <div class="card m-3">
        <h4 class="card-header">Login</h4>
        <div class="card-body">
            <Form @submit="onSubmit" :validation-schema="schema" v-slot="{ errors, isSubmitting }">
                <div class="form-group">
                    <label>Email</label>
                    <Field name="username" type="text" class="form-control" :class="{ 'is-invalid': errors.username }" />
                    <div class="invalid-feedback">{{ errors.username }}</div>
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <Field name="password" type="password" class="form-control" :class="{ 'is-invalid': errors.password }" />
                    <div class="invalid-feedback">{{ errors.password }}</div>
                </div>
                <div class="form-group">
                    <!-- 로그인 유지 - 쿠키유지 로직 -->
                    <input type="checkbox">로그인 유지
                    <button class="btn btn-primary" :disabled="isSubmitting">
                        <span v-show="isSubmitting" class="spinner-border spinner-border-sm mr-1"></span>
                        로그인
                    </button>
                    <router-link to="register" class="btn btn-link">회원가입</router-link>
                    <router-link to="passwordReset" class="btn btn-link">비밀번호를 잊으셨나요?</router-link>
                </div>
            </Form>
        </div>
    </div>
</template>
