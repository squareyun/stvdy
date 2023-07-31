// 아래 참고
// import { ref, computed } from 'vue'
// import { defineStore } from 'pinia'

// export const webrtcStore = defineStore('counter', () => {
//   const count = ref(0)
//   const doubleCount = computed(() => count.value * 2)
//   function increment() {
//     count.value++
//   }

//   return { count, doubleCount, increment }
// })


import { defineStore } from 'pinia';

export const webRtcStore = defineStore({
  id: 'webrtc',
  state: () => ({
    mySessionId : null,
    myUserName : null
  }),
  actions: {
    // success(message) {
    //   this.alert = { message, type: 'alert-success' }
    // },
    // error(message) {
    //   this.alert = { message, type: 'alert-danger' }
    // },
    // clear() {
    //   this.alert = null
    // },
    // 맞춤 추천
    // getMyUserName(){
    //   this.mySessionId = ""
    // },

  },
  mutations:{
    GET_MYUSERNAME(state, myUserName){
      state.obj.myUserName = myUserName
      console.log(state.obj.myUserName)
    },
    GET_MYSESSIONID(state, mySessionId){
      state.obj.mySessionId = mySessionId
    },
  }
})
