import { defineStore } from 'pinia'
import { useAuthStore } from '@/stores'
import { getUser, changePwd, deleteUser } from '@/api/user'
import { ref, watch } from 'vue'

export const useUserStore = defineStore('users', () => {
  const user = ref([])
  const users = ref([])

  localStorage.removeItem('user')
  if (sessionStorage.getItem('user')) {
    user.value = JSON.parse(sessionStorage.getItem('user'))
  }

  watch(
    user,
    (userVal) => {
      sessionStorage.setItem('user', JSON.stringify(userVal))
      console.log('changed')
    },
    { deep: true },
  )

  const getInfo = async (token) => {
    const AuthStore = useAuthStore()

    await getUser(
      token,
      async () => {
        await AuthStore.setValid(true)
      },
      async (error) => {
        console.log(error)

        await AuthStore.setValid(false)
      },
    )
  }

  const setInfo = async (values) => {
    user.value = {
      id: values.id,
      email: values.email,
      realname: values.name,
      username: values.nickname,
    }
  }

  const changePassword = async (data) => {
    const values = {
      userNo: user.value.id,
      currentPassword: data.prvpassword,
      newPassword: data.password,
      confirmPassword: data.passwordConfirm,
    }

    changePwd(
      values,
      (res) => console.log(res),
      (fail) => console.log(fail),
    )
  }

  const deleteAccount = async () => {
    deleteUser(
      user.value.id,
      (res) => console.log(res),
      (fail) => console.log(fail),
    )
  }

  return { user, users, getInfo, setInfo, changePassword, deleteAccount }
})
