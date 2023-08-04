import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import { useUsersStore } from "@/stores"
import axios from 'axios'

export const usewebRtcStore = defineStore({
  id: 'webrtc',
  state: () => ({
    // userNo: null,
    // userNo: useUsersStore().user.id,
    userNo: Math.floor(Math.random() * (200 - 1 + 1)) + 1,
    mySessionId: '123',
    // myUserName : null,
    // myUserName : useUsersStore().user.username,
    myUserName : Math.floor(Math.random() * (200 - 1 + 1)) + 1+'제발되라',
    endHour: 0,
    endMinute: 0,
    quota: 16,
    isPassword: false,
    isPrivacy: false, // 이건 방에 대한게 아닌 방장외 전부 캠 오프와 관련된 거임.
    password: null,
    imagePath: null,
    rule: '모두 열공해서 합격합시다 Ψ(￣∀￣)Ψ',
    backImgFile: null,

    /// 참여시 사용할 것
    inputPassword: null,

    // realname: useUsersStore().user.realname,
    realname: '홍길동'+ Math.floor(Math.random() * (200 - 1 + 1)) + 1,


    //////////////////////////
    // 메인페이지에서 사용되는 방 목록 관련
    roomList: null,

  }),
  actions: {
    checkmyUserName(){
      console.log(this.myUserName)
      console.log(this.userNo)
      console.log(this.realname)
    },
    updateMyUserName(newUserName) {
      this.myUserName = newUserName
    },
    updateMySessionId(newSessionId) {
      this.mySessionId = newSessionId
    },
    updateEndHour(newEndHour) {
      this.endHour = newEndHour
    },
    updateEndMinute(newEndMinute) {
      this.endMinute = newEndMinute
    },
    updateIsPassword(newisPassword) {
      this.isPassword = newisPassword
      if (!this.isPassword) {
        this.password = null
      }
    },
    updateIsPrivacy(newisPrivacy) {
      this.isPrivacy = newisPrivacy
    },
    updatePassword(newPassword) {
      this.password = newPassword
    },
    updateQuota(newQuota) {
      this.quota = newQuota
    },
    updateBackImg(newBackImgFile){
      this.backImgFile = newBackImgFile
      console.log(this.backImgFile)
    },
    updateRule(newRule){
      this.rule = newRule
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
          roomName: encodeURIComponent(this.mySessionId), // 인코딩해서 보내줘야만 작동함
        },
      })
    },
    // joinSession(){
    //   // 새로운 탭에서 경로를 엽니다.
    //   const newTabUrl = `/room/${this.mySessionId}`;
    //   window.open(newTabUrl, '_blank');
    // }
    ////////닉네임 받기 위한 getmySessionId
    getmySessionId(context){
      axios({
        method: 'get',
        // url: 'http://54.180.9.43:8080/rooms/add/'',
        url: 'http://localhost:8080/rooms/add/',
        headers: store.getters.authHeader,
      })
        .then((res) => {
          console.log(res.data)
          context.commit('GETMYSESSIONID', res.data)
        })
        .catch((err) => {
          console.log(err)
        })
    },
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



    /////////////////////////////////////////////////
    // 메인 페이지에서 사용되는 방 리스트 목록
    // 방들 찾아옴.
    async getRtcRooms() {
      try{
        // const response = axios.get('http://54.180.9.43:8080/rooms/list/')
        const response = axios.get('http://localhost:8080/rooms/list/')
        roomList.value = await response.data
      }
      catch(error){
        console.error('방 리스트 받아오는 오류 발생: ', error)
      }
    }


    
  },
  mutations: {
    GETMYSESSIONID(state, datas){
      state.mySessionId = datas.nickName
    },
    JOINSESSION(state, data) {},
  },
})
