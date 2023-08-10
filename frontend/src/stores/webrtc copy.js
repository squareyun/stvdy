import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores'
import axios from 'axios'

export const usewebRtcStore = defineStore({
  id: 'webrtc',
  state: () => ({
    // userNo: null,
    userNo: useUserStore().user.id,
    // userNo: Math.floor(Math.random() * (200 - 1 + 1)) + 1,
    mySessionId: '되라' + (Math.floor(Math.random() * (200 - 1 + 1)) + 1),
    // myUserName : null,
    myUserName: useUserStore().user.username,
    // myUserName : Math.floor(Math.random() * (200 - 1 + 1)) + 1+'제발되라',
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
    // realname: '홍길동'+ Math.floor(Math.random() * (200 - 1 + 1)) + 1,

    //////////////////////////
    // 메인페이지에서 사용되는 방 목록 관련
    roomList: [],
    roomId: null,

    peopleNo: 0, // 우선 peopleNo은 0으로 둠. 방 탈퇴 및 방 정보에 사용할 예정임

    router: useRouter(),
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
    ////////닉네임 받기 위한 getmySessionId
    getmySessionId(context) {
      axios({
        method: 'get',
        // url: 'http://54.180.9.43:8080/rooms/add/'',
        url: 'http://54.180.9.43:8080/rooms/add/',
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
      try {
        // const response = axios.get('http://54.180.9.43:8080/rooms/list/')
        console.log('getRtcRooms내부1')
        const response = await axios.get('http://54.180.9.43:8080/rooms/list')
        console.log('getRtcRooms내부2')
        this.roomList = response.data.roomList
        console.log(this.roomList)
        console.log('getRtcRooms내부3')
      } catch (error) {
        console.log('getRtcRooms내부 오류')
        console.error('방 리스트 받아오는 오류 발생: ', error)
      }
    },

    async getEveryRoomTags() {
      try {
        // const response = axios.get('http://54.180.9.43:8080/rooms/list/')
        console.log('getEveryRoomTags 내부1')
        // const response = await axios.get('http://54.180.9.43:8080/rooms/tags')
        console.log('getEveryRoomTags 내부2')
        // this.roomList = response.data.roomList
        console.log(this.roomList)
        console.log('getEveryRoomTags 내부3')
      } catch (error) {
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
          `http://54.180.9.43:8080/rooms/${roomId}`,
        )
        console.log('shutDownRoom', response.data)
        console.log('방이 성공적으로 제거되었습니다.')
        // this.roomId = null
      } catch (error) {
        console.error('방을 제거하지 못했습니다.', error.code, error.message)
      }
    },

    async roomExit(roomId) {
      console.log('나갈방번호', roomId)
      console.log('방나갈거야!!!!!!!!!!!')
      try {
        // participan~~ 는 방에 참여한 사람 번호,  userNo는 내 번호.
        // participantNo는 방에 독립적인 참여자 번호,
        const response = await axios.post(
          'http://54.180.9.43:8080/rooms/exit',
          {
            roomNo: roomId,
            userNo: this.userNo,
          },
        )
        console.log('방나감의 리스폰스', response.data)
        console.log('방나갔다!!!!!!!')
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
          `http://54.180.9.43:8080//rooms/currentConnection/${roomId}`,
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

    // 강제퇴장 시키기
    async checkCurrentConnection(roomId) {
      console.log('현재 커넥션 확인할 방 번호', roomId)
      try {
        const response = await axios.get(
          `http://54.180.9.43:8080//rooms/kick`,
          {
            roomNo: this.roomId,
            userNo: userNo,
            reason: reason,
          },
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
  },
  mutations: {
    GETMYSESSIONID(state, datas) {
      state.mySessionId = datas.nickName
    },
    JOINSESSION(state, data) {},
  },
})
