import { defineStore } from 'pinia'
import axios from 'axios'


export const webRtcStore = defineStore({
  id: 'webrtc',
  state: () => ({
    mySessionId : null,
    // myUserName : null,
    myUserName : '우르릉쾅쾅쾅',
    endHour : 0,
    endMinute : 0,
    quota : 16,
    isPraivacy : false,
    password : null,
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
    },
    updatePassword(newPassword){
      this.password = newPassword
    },
    updateQuota(newQuota){
      this.quota = newQuota
    },
    //////////닉네임 받기 위한 getmySessionId
    // getmySessionId(context){
    //   axios({
    //     method: 'get',
    //     url: 'http://localhost:8080/rooms/add/',
    //     headers: store.getters.authHeader,
    //   })
    //     .then((res) => {
    //       console.log(res.data)
    //       context.commit('GETMYSESSIONID', res.data)
    //     })
    //     .catch((err) => {
    //       console.log(err)
    //     })
    // },
    ////////// userNo(int), title(String), endHour(int), endMinute(int), quota(int), isPrivacy(Boolean)을 보내기 위함
    createRoom(userNo, title, endHour, endMinute, quota, isPrivacy) {
      axios.post('http://localhost:8080/rooms/add/', {
        userNo: userNo,
        title: title,
        endHour: endHour,
        endMinute: endMinute,
        quota: quota,
        isPrivacy: isPrivacy,
      })
        .then((response) => {
          // POST 요청에 성공한 경우, 응답 데이터를 이용하여 mySessionId를 업데이트합니다.
          console.log(response)
          // this.mySessionId = response.data.mySessionId; // 실제 응답 데이터에 맞게 수정
        })
        .catch((error) => {
          console.error(error);
          // POST 요청에 실패한 경우, 에러 처리를 원하는 대로 추가합니다.
        });
    },
    
  },
  mutations:{
    // GETMYSESSIONID(state, datas){
    //   state.mySessionId = datas.nickName
    // }
  }
})