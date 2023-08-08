import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import { useUsersStore } from "@/stores"
import axios from 'axios'

export const usewebRtcStore = defineStore({
  id: 'webrtc',
  state: () => ({
    // userNo: null,
    userNo: useUsersStore().user.id,
    // userNo: Math.floor(Math.random() * (200 - 1 + 1)) + 1,
    mySessionId: '이젠 될때가 되지 않았나???',
    // myUserName : null,
    myUserName : useUsersStore().user.username,
    // myUserName : Math.floor(Math.random() * (200 - 1 + 1)) + 1+'제발되라',
    endHour: 0,
    endMinute: 0,
    quota: 16,
    isPassword: false,
    isPrivacy: false, // 이건 방에 대한게 아닌 방장외 전부 캠 오프와 관련된 거임.
    password: null,
    imagePath: null,
    isHost : false,
    rule: '모두 열공해서 합격합시다 Ψ(￣∀￣)Ψ',
    backImgFile: null,
    roomKeywords : [],

    /// 참여시 사용할 것
    inputPassword: null,

    realname: useUsersStore().user.realname,
    // realname: '홍길동'+ Math.floor(Math.random() * (200 - 1 + 1)) + 1,
    
    //////////////////////////
    // 메인페이지에서 사용되는 방 목록 관련
    roomList: [],
    roomId : null,

    peopleNo : 0, // 우선 peopleNo은 0으로 둠. 방 탈퇴 및 방 정보에 사용할 예정임


    router : useRouter()

  }),
  actions: {
    creatorIsHost(){
      this.isHost = true
    },
    notIsHost(){
      this.isHost = false
    },
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
    updateRoomKeywords(newKeywords){
      this.roomKeywords = newKeywords
    },
    
    //  방 생성시에 roomId를 모르고 있으므로 그 값을 적용시킴
    updateRoomId(newRoomId){
      this.roomId = newRoomId
    },

    // main 화면에서 방 입장 비밀번호 입력시
    updatePwInput(newPwInput) {
      this.password = newPwInput
      console.log(this.password)
    },
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

    /////////////////////////////////////////////////
    // 메인 페이지에서 사용되는 방 리스트 목록
    // 방들 찾아옴.
    async getRtcRooms() {
      try{
        // const response = axios.get('http://54.180.9.43:8080/rooms/list/')
        console.log('getRtcRooms내부1')
        const response = await axios.get('http://localhost:8080/rooms/list')
        console.log('getRtcRooms내부2')
        this.roomList = response.data.roomList
        console.log(this.roomList)
        console.log('getRtcRooms내부3')
      }
      catch(error){
        console.log('getRtcRooms내부 오류')
        console.error('방 리스트 받아오는 오류 발생: ', error)
      }
    },
    
    async getEveryRoomKeywords() {
      try{
        // const response = axios.get('http://54.180.9.43:8080/rooms/list/')
        console.log('getEveryRoomKeywords 내부1')
        // const response = await axios.get('http://localhost:8080/rooms/list')
        console.log('getEveryRoomKeywords 내부2')
        // this.roomList = response.data.roomList
        console.log(this.roomList)
        console.log('getEveryRoomKeywords 내부3')
      }
      catch(error){
        console.log('getRtcRooms내부 오류')
        console.error('방 리스트 받아오는 오류 발생: ', error)
      }
    },

    joinTheRoom(room) {
      // this.$router.push({ name: 'MovieDetailView', params: { id: moviecard.id }});  
      this.mySessionId = room.title
      this.quota = room.quota
      this.roomId = room.id
      this.peopleNo = room.currentNumber
      console.log('조인더룸내부1', this.roomId)
      this.router.push({
        name:'roomJoin',
        params: { 
          // roomName: encodeURIComponent(mySessionId.value),  // 인코딩해서 보내줘야만 작동함
          // roomName: room.roomTitle,  // 인코딩해서 보내줘야만 작동함
          roomName: encodeURIComponent(room.title),
        },
      })
      console.log('조인더룸내부2')
    },
    // async shutDownRoom(roomId){
    //   try{
    //     const response = await axios.delete(`http://localhost:8080/rooms/${roomId}`)
    //     console.log(response.data)
    //     console.log('방이 성공적으로 제거되었습니다.')
    //   }
    //   catch(error){
    //     console.error('방을 제거하지 못했습니다.',error.code, error.message)
    //   }
    // },

    async roomExit(roomId){
      console.log('나갈방번호',roomId)
      console.log("방나갈거야!!!!!!!!!!!")
      try{
        // participan~~ 는 방에 참여한 사람 번호,  userNo는 내 번호.
        // participantNo는 방에 독립적인 참여자 번호,
        const response = await axios.post('http://localhost:8080/rooms/exit',{roomNo:roomId, userNo: this.userNo})
        console.log('방나감의 리스폰스',response.data)
        console.log("방나갔다!!!!!!!")
      }
      catch(error){
        console.error('RoomExit에 문제가 생겼습니다.', error.code, error.message);
      }
    },

    async checkCurrentConnection(roomId){
      console.log('현재 커넥션 확인할 방 번호',roomId)
      try{
        const response = await axios.get(`http://localhost:8080//rooms/currentConnection/${roomId}`)
        console.log('확인함')
        console.log('현재 커넥션',response.data)
      }
      catch(error){
        console.error('checkCurrentConnection에 문제가 생겼습니다.', error.code, error.message);
      }
    },
    
    
  },
  mutations: {
    GETMYSESSIONID(state, datas){
      state.mySessionId = datas.nickName
    },
    JOINSESSION(state, data) {},
  },
})
