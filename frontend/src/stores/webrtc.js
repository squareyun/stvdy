import { defineStore } from 'pinia';


export const webRtcStore = defineStore({
  id: 'webrtc',
  state: () => ({
    mySessionId : null,
    myUserName : null
  }),
  actions: {
    updateMyUserName(newUserName) {
      console.log(newUserName)
      this.myUserName = newUserName;
    },
    updateMySessionId(newSessionId) {
      // console.log(newSessionId)
      this.mySessionId = newSessionId;
    },
  },
  mutations:{
  }
})
