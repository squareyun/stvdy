import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import axios from 'axios'

export const webRtcStore = defineStore({
  id: 'webrtc',
  state: () => ({
    mySessionId: null,
    // myUserName : null,
    myUserName: '우르릉쾅쾅쾅',
    endHour: 0,
    endMinute: 0,
    quota: 16,
    isPassword: false,
    isPrivacy: false, // 이건 방에 대한게 아닌 방장외 전부 캠 오프와 관련된 거임.
    password: null,
  }),
  actions: {
    updateMyUserName(newUserName) {
      console.log(newUserName)
      this.myUserName = newUserName
    },
    updateMySessionId(newSessionId) {
      this.mySessionId = newSessionId
      console.log('#####', this.mySessionId)
    },
    updateEndHour(newEndHour) {
      console.log(newEndHour)
      this.endHour = newEndHour
    },
    updateEndMinute(newEndMinute) {
      console.log(newEndMinute)
      this.endMinute = newEndMinute
    },
    updateIsPassword(newisPassword) {
      console.log(newisPassword)
      this.isPassword = newisPassword
      if (!this.isPassword) {
        this.password = null
      }
      console.log(this.password)
    },
    updateIsPrivacy(newisPrivacy) {
      console.log(newisPrivacy)
      this.isPrivacy = newisPrivacy
    },
    updatePassword(newPassword) {
      this.password = newPassword
      console.log('메롱', this.password)
    },
    updateQuota(newQuota) {
      this.quota = newQuota
      console.log(this.quota)
    },
    // joinSession(router){
    //   axios({
    //     method: 'post',
    //     url: 'http://54.180.9.43:8080/rooms/add/',
    //     data : {
    //       // userNo: this.userNo,
    //       // title: this.title,
    //       title: this.titlemySessionId,
    //       endHour: this.endHour,
    //       endMinute: this.endMinute,
    //       quota: this.quota,
    //       isPrivacy: this.isPrivacy,
    //       password: this.password
    //     }
    //   })
    //   .then(()=>{
    //     router.push({
    //       name:'roomJoin',
    //       params: {
    //         roomNo: encodeURIComponent(this.mySessionId),  // 인코딩해서 보내줘야만 작동함
    //       },
    //     })
    //   })
    //   .catch((err)=>{
    //     console.log(err)
    //   })
    // }
    joinSession(router) {
      router.push({
        name: 'roomJoin',
        params: {
          roomNo: encodeURIComponent(this.mySessionId), // 인코딩해서 보내줘야만 작동함
        },
      })
    },
    // joinSession(){
    //   // 새로운 탭에서 경로를 엽니다.
    //   const newTabUrl = `/room/${this.mySessionId}`;
    //   window.open(newTabUrl, '_blank');
    // }
    //////////닉네임 받기 위한 getmySessionId
    // getmySessionId(context){
    //   axios({
    //     method: 'get',
    //     url: 'http://54.180.9.43:8080/rooms/add/',
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
    // createRoom(userNo, title, endHour, endMinute, quota, isPrivacy) {
    //   axios.post('http://54.180.9.43:8080/rooms/add/', {
    //     userNo: userNo,
    //     title: title,
    //     endHour: endHour,
    //     endMinute: endMinute,
    //     quota: quota,
    //     isPrivacy: isPrivacy,
    //   })
    //     .then((response) => {
    //       // POST 요청에 성공한 경우, 응답 데이터를 이용하여 mySessionId를 업데이트합니다.
    //       console.log(response)
    //       // this.mySessionId = response.data.mySessionId; // 실제 응답 데이터에 맞게 수정
    //     })
    //     .catch((error) => {
    //       console.error(error);
    //       // POST 요청에 실패한 경우, 에러 처리를 원하는 대로 추가합니다.
    //     });
    // },
  },
  mutations: {
    // GETMYSESSIONID(state, datas){
    //   state.mySessionId = datas.nickName
    // }
    JOINSESSION(state, data) {},
  },
})
