import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import { useUserStore } from "@/stores"

import axios from 'axios'

export const usewebRtcStore = defineStore({
  id: 'webrtc',
  state: () => ({
    APPLICATION_SERVER_URL: 'http://localhost:8080/',
    // APPLICATION_SERVER_URL: 'https://i9d205.p.ssafy.io/api/', // 배포된 서버

    userNo: useUserStore().user.id,
    userId: (Math.floor(Math.random() * (200 - 1 + 1)) + 1), // 테스트를 위해서 임시로...

    mySessionId: 'SSAP STVDY'+ (Math.floor(Math.random() * (200 - 1 + 1)) + 1),
    // myUserName : useUserStore().user.username,
    myUserName : '테스트 유저'+ (Math.floor(Math.random() * (200 - 1 + 1)) + 1),

    endHour: 0,
    endMinute: 0,
    quota: 16,
    isPassword: false,
    isPrivacy: false, // 이건 방에 대한게 아닌 방장외 전부 캠 오프와 관련된 거임.
    password: null,
    imagePath: null,
    isHost: false,
    rule: '모두 열공해서 합격합시다 Ψ(￣∀￣)Ψ',
    backImgFile: null,
    roomTags: [],

    // 방  참여 시 사용할 것
    isMaking: false,
    inputPassword: null,
    roomUsers: [],
    // 방 이탈 시 사용할 것
    isExitRoom: false,

    realname: useUserStore().user.realname,
    tmp : useUserStore().user,
    // realname: '홍길동'+ Math.floor(Math.random() * (200 - 1 + 1)) + 1,

    //////////////////////////
    // 메인페이지에서 사용되는 방 목록 관련
    roomList: [],
    roomId: null,

    peopleNo: 0, // 우선 peopleNo은 0으로 둠. 방 탈퇴 및 방 정보에 사용할 예정임

    router: useRouter(),

    /////////////////////////////////
    imgformData: null

  }),
  actions: {
    isMakingTrue() {
      this.isMaking = true
    },
    isMakingFalse() {
      this.isMaking = false
    },
    isExitTrue() {
      this.isExitRoom = true
    },
    isExitFalse() {
      this.isExitRoom = false
    },
    creatorIsHost() {
      this.isHost = true
    },
    notIsHost() {
      this.isHost = false
    },
    checkmyUserName() {
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
    updateBackImg(newBackImgFile) {
      this.backImgFile = newBackImgFile
      console.log(this.backImgFile)
    },
    updateRule(newRule) {
      this.rule = newRule
    },
    updateRoomTags(newTags) {
      this.roomTags = newTags
    },

    //  방 생성시에 roomId를 모르고 있으므로 그 값을 적용시킴
    updateRoomId(newRoomId) {
      this.roomId = newRoomId
      console.log('이건 store의 roomId', this.roomId)
    },

    // main 화면에서 방 입장 비밀번호 입력시
    updatePwInput(newPwInput) {
      this.password = newPwInput
      console.log(this.password)
    },
    // 이미지 등록할 이미지 업데이트하는 함수
    updateUploadImage(newimgFormData){
      this.imgformData = newimgFormData
    },

    ////// 이미지를 등록하기 위한 함수 // 스터디룸 이미지가 유저에 종속되어있음.
    uploadImagetoServer(userNo) {
      // 서버에 이미지 파일 업로드하기
      try {
        const response = axios.post(this.APPLICATION_SERVER_URL+'files/upload/room/'+userNo, this.imgformData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });
        console.log("이미지 업로드 성공: ", response.data);
      } catch (error) {
        console.log("이미지 업로드 에러: ", error);
      }
    },

    // 이미지 경로 얻기
    async downloadImagefromServer(userNo) {
      // 서버에서 이미지 경로 얻기
      try {
        const response = await axios.get(this.APPLICATION_SERVER_URL+'files/get/room/'+userNo,{
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });
        console.log("이미지 경로 다운로드 성공: ", response.data);
      } catch (error) {
        console.log("이미지 경로 다운로드 에러: ", error);
      }
    },


    // 개인 프로필 이미지 경로 얻기
    async downloadProfiefromServer(userNo) {
      // 서버에서 이미지 경로 얻기
      try {
        const response = await axios.get(this.APPLICATION_SERVER_URL+'files/get/room/'+userNo,{
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });
        console.log("이미지 경로 다운로드 성공: ", response.data);
      } catch (error) {
        console.log("이미지 경로 다운로드 에러: ", error);
      }
    },

    ////////닉네임 받기 위한 getmySessionId
    getmySessionId(context) {
      axios({
        method: 'get',
        // url: 'http://54.180.9.43:8080/rooms/add/'',
        url: this.APPLICATION_SERVER_URL + 'rooms/add/',
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
        // const response = await axios.get(this.APPLICATION_SERVER_URL+'rooms/list')
        const response = await axios.get(this.APPLICATION_SERVER_URL+'rooms/wholeList')
        this.roomList = response.data.roomList
        console.log(this.roomList)
      }
      catch(error){
        console.error('getRtcRooms함수 오류: ', error)
      }
    },
    
    getsearchRooms(pageNo=0, keyword='', size=20){
      try{
        const response = axios.get(this.APPLICATION_SERVER_URL+`rooms/list?page=${pageNo}&keyword=${keyword}&size=${size}`)
        this.roomList = response.data.roomList
        console.log(this.roomList)
      }
      catch(error){
        console.error('getsearchRooms함수 오류: ', error)
      }
    },

    // async getEveryRoomTags() {
    //   try{
    //     // const response = axios.get('http://54.180.9.43:8080/rooms/list/')
    //     console.log('getEveryRoomTags 내부1')
    //     // const response = await axios.get(this.APPLICATION_SERVER_URL+'rooms/tags')
    //     console.log('getEveryRoomTags 내부2')
    //     // this.roomList = response.data.roomList
    //     console.log(this.roomList)
    //     console.log('getEveryRoomTags 내부3')
    //   }
    //   catch(error){
    //     console.log('getRtcRooms내부 오류')
    //     console.error('방 리스트 받아오는 오류 발생: ', error)
    //   }
    // },

    joinTheRoom(room) {
      // this.$router.push({ name: 'MovieDetailView', params: { id: moviecard.id }});
      this.mySessionId = room.title
      this.quota = room.quota
      this.roomId = room.id
      this.peopleNo = room.currentNumber
      console.log('조인더룸내부1', this.roomId)
      this.router.push({
        name: 'roomJoin',
        params: {
          // roomName: encodeURIComponent(mySessionId.value),  // 인코딩해서 보내줘야만 작동함
          // roomName: room.roomTitle,  // 인코딩해서 보내줘야만 작동함
          roomName: encodeURIComponent(room.title),
        },
      })
      console.log('조인더룸내부2')
    },
    async shutDownRoom(roomId) {
      console.log('shutDownRoom내부1\n', roomId)
      try {
        console.log('shutDownRoom내부2', roomId)
        const response = await axios.delete(
          this.APPLICATION_SERVER_URL + `rooms/${roomId}`,
        )
        console.log('shutDownRoom', response.data)
        console.log('방이 성공적으로 제거되었습니다.')
        // this.roomId = null
      } catch (error) {
        console.error('방을 제거하지 못했습니다.', error.code, error.message)
      }
    },

    async roomExit(roomId) {
      console.log('함수 roomExit 들어옴', roomId)
      try {
        // participan~~ 는 방에 참여한 사람 번호,  userNo는 내 번호.
        // participantNo는 방에 독립적인 참여자 번호,
        const response = await axios.post(
          this.APPLICATION_SERVER_URL + 'rooms/exit',
          { roomNo: roomId, userNo: this.userNo },
        )
        console.log('함수 roomExit 의 리스폰스', response.data)
      } catch (error) {
        console.error(
          'RoomExit에 문제가 생겼습니다.',
          error.code,
          error.message,
        )
      }
    },

    async checkCurrentConnection(roomId) {
      console.log('현재 커넥션 확인할 방 번호', roomId)
      try {
        const response = await axios.get(
          this.APPLICATION_SERVER_URL + `rooms/currentConnection/${roomId}`,
        )
        console.log('확인함')
        console.log('현재 커넥션', response.data)
      } catch (error) {
        console.error(
          'checkCurrentConnection에 문제가 생겼습니다.',
          error.code,
          error.message,
        )
      }
    },

    async giveRole(roomId) {
      console.log('giveRole()함수 들어옴', roomId)
      try {
        const response = await axios.put(
          this.APPLICATION_SERVER_URL + `rooms/role`,
          { roomNo: this.roomId, userNo: this.userNo },
        )
        console.log('giveRole()함수 값', response.data)
      } catch (error) {
        console.error(
          'giveRole()함수에 문제가 생겼습니다.',
          error.code,
          error.message,
        )
      }
    },

    // 강제퇴장 시키기
    async kickUser(roomId, userNo, reason){
      console.log('현재 커넥션 확인할 방 번호',roomId)
      try{
        const response = await axios.get(this.APPLICATION_SERVER_URL+`rooms/kick`,{roomNo: this.roomId, userNo: userNo, reason: reason})
        console.log('현재 커넥션',response.data)
      }
      catch(error){
        console.error('kickUser에 문제가 생겼습니다.', error.code, error.message);
      }
    },
    //이건 post 방만들때
    async shareRoomAddress(roomId) {
      console.log('방 공유 함수 들어옴')
      try{
        const response = await axios.post(this.APPLICATION_SERVER_URL+'rooms/code/'+roomId)
        console.log('방 공유 백엔드 연결완료',response.data)
      }
      catch(error){
        console.error('방 공유 함수에 문제가 생겼습니다.', error.code, error.message);
      }
    },
    //이건 get 룸정보 얻는 코드임
    async shareRoomAddress2(roomId) {
      console.log('방 공유 함수 들어옴')
      try {
        const response = await axios.post(this.APPLICATION_SERVER_URL+'rooms/code/'+roomId)
        console.log('방 공유 백엔드 연결완료1',response.data)
      } catch (error) {
        console.error('방 공유 함수에 문제가 생겼습니다.', error.code, error.message)
      }
    },
  },
  mutations: {
    GETMYSESSIONID(state, datas) {
      state.mySessionId = datas.nickName
    },
    JOINSESSION(state, data) {},
  },
})
