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

// export default defineStore({
//   id: 'users',
//   persist: {
//     enabled: true,
//   },
// })

// export const useUserStore = defineStore({
//   id: 'users',
//   state: () => ({
//     users: {},
//     user: {},
//   }),
//   actions: {
//     async getInfo(token) {
//       const AuthStore = useAuthStore()

//       await getUser(
//         token,
//         async (res) => {
//           await AuthStore.setValid(true)
//           this.user.username = res.data.user.nickname
//         },
//         async (error) => {
//           console.log(error)

//           await AuthStore.setValid(false)
//         },
//       )
//     },
//     async setInfo(user) {
//       this.user = {
//         id: user.id,
//         email: user.email,
//         realname: user.name,
//         username: user.nickname,
//       }
//       console.log(this.user)
//     },
//   },
//   persist: {
//     enabled: true,
//   },
// })
