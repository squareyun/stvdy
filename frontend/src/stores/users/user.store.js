import { defineStore } from 'pinia'
import { useAuthStore } from '@/stores'
import { getUser } from '@/api/user'
import { ref, watch } from 'vue'

export const useUserStore = defineStore('users', () => {
  const user = ref([])
  const users = ref([])

  if (localStorage.getItem('user')) {
    user.value = JSON.parse(localStorage.getItem('user'))
  }

  watch(
    user,
    (userVal) => {
      localStorage.setItem('user', JSON.stringify(userVal))
      console.log('changed')
    },
    { deep: true },
  )

  const getInfo = async (token) => {
    const AuthStore = useAuthStore()

    await getUser(
      token,
      async (res) => {
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

  return { user, users, getInfo, setInfo }
})
