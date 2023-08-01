import { defineStore } from 'pinia';


export const webRtcStore = defineStore({
  id: 'webrtc',
  state: () => ({
    mySessionId : null,
    myUserName : null,
    endHour : 0,
    endMinute : 1,
    quota : 16,
    isPraivacy : false,
  }),
  actions: {
    updateMyUserName(newUserName) {
      console.log(newUserName)
      this.myUserName = newUserName;
    },
    updateMySessionId(newSessionId) {
      console.log(newSessionId)
      this.mySessionId = newSessionId;
    },
    updateEndHour(newEndHour){
      console.log(newEndHour)
      this.endHour = newEndHour
    },
    updateEndMinute(newEndMinute){
      console.log(newEndMinute)
      this.endMinute = newEndMinute
    },
    updateIsPravacy(newisPraivacy){
      console.log(newisPraivacy)
      this.isPraivacy = newisPraivacy
    }

    // getmySessionId(context){
    //   axios({
    //     method: 'get',
    //     url: 'http://127.0.0.1:8000/rooms/add/',
    //     headers: store.getters.authHeader,
    //   })
    //     .then((res) => {
    //       console.log(res.data)
    //       context.commit('UPDATEMYSESSIONID', res.data.nickname)
    //     })
    //     .catch((err) => {
    //       console.log(err)
    //     })
    // },
    
  },
  mutations:{
    // UPDATEMYSESSIONID(state, mySessionId){
    //   state.mySessionId = mySessionId
    // }
  }
})
