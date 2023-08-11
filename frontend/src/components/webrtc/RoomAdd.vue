<script setup>
  import { ref, computed, onUpdated, watch, onMounted } from 'vue'
  import { useRouter } from "vue-router"
  import { usewebRtcStore } from "@/stores"
  import { useUserStore } from "@/stores"
  import axios from 'axios' 
  // import { storeToRefs } from "pinia";
  // const APPLICATION_SERVER_URL = 'https://i9d205.p.ssafy.io/api/'
  const APPLICATION_SERVER_URL = 'http://localhost:8080/'
  const usersStore = useUserStore()
  const localUser = usersStore.user
  console.log(localUser)

  // const store = usewebRtcStore()
  const webrtcstore = usewebRtcStore()
  const userstore = useUserStore()
  const router = useRouter()
  const userNo = ref(webrtcstore.userNo)
  
  // 방 생성에 사용할 변수들 
  const myUserName = ref(webrtcstore.myUserName)
  // const myUserName = ref(localUser.username)
  const mySessionId = ref(webrtcstore.mySessionId)
  
  // 방 이미지 관련 내용
  const backImgFile = ref(webrtcstore.backImgFile)
  const imgPreviewUrl = ref(null)

  // 방 시간 관련 내용
  // const hours = ref([...Array(24).keys()].map(hour=> hour.toString()))
  const hours = ref([...Array(25).keys()])
  // const minutes = ref([...Array(60).keys()].map(minute=> minute.toString()))
  const minutes = ref([...Array(60).keys()])
  const endHour = ref(webrtcstore.endHour)
  const endMinute = ref(webrtcstore.endMinute)
  const timeSet = ref(false)
  // 방 비공개 관련 내용
  const isPassword = ref(webrtcstore.isPassword)
  const password = ref(webrtcstore.password)
  // 방 인원설정 관련 내용
  const quota = ref(webrtcstore.quota)
  // 방장의 방장여부 등록
  const isHost = ref(webrtcstore.isHost)

  // 방 참여자들의 캠 전체 비활성화 여부를 의미
  const isPrivacy = ref(webrtcstore.isPrivacy)

  onMounted(() => {
    // creatorIsHost()
    joinImmediately() // css 동안 임시로 해둠.
  })
  
  // css 동안 임시로 해둠. 
  function joinImmediately() {
    setTimeout(() => {
      joinSession()
    }, 1000);
  }


  //tiemSet이 flase면 종료시간 초기화.
  watch(timeSet, (newtimeSet) => {
    if (!newtimeSet) {
      webrtcstore.updateEndHour(0);
      endHour.value = 0;
      webrtcstore.updateEndMinute(0);
      endMinute.value = 0;
    }
    else{
      webrtcstore.updateEndHour(0)
      endHour.value = 0;
      webrtcstore.updateEndMinute(1);
      endMinute.value = 1;
    }
  })
  // endHour와 timeSet이 둘다 0이면 안됨.
  if(timeSet.value && endHour.value === 0 && endMinute.value === 0 ){
    alert("타이머가 0일 수는 없습니다. \n다시 설정 해주세요.")
    endHour.value = 0
    endMinute.value = 1
  }
  watch(endHour, (newendHour) => {
    if (timeSet.value && newendHour === 0 && endMinute.value === 0) {
      alert("타이머가 0일 수는 없습니다. \n다시 설정 해주세요.");
      endHour.value = 0
      endMinute.value = 1
    }
  })
  watch(endMinute, (newendMinute) => {
    if (timeSet.value && endHour.value === 0 && newendMinute === 0) {
      alert("타이머가 0일 수는 없습니다. \n다시 설정 해주세요.");
      endHour.value = 0
      endMinute.value = 1
    }
  })

  watch(isPassword,(newisPassword) => {
    const isPasswordSpan = document.getElementById("isPasswordSpan")
    if(newisPassword){
      isPasswordSpan.innerText = "설정"
    }else{
      isPasswordSpan.innerText = "미설정"
    }
  })
  watch(isPrivacy,(newisPrivacySpan) => {
    const isPrivacySpan = document.getElementById("isPrivacySpan")
    if(newisPrivacySpan){
      isPrivacySpan.innerText = "설정"
    }else{
      isPrivacySpan.innerText = "미설정"
    }
  })
  // isPassword가 변동되면 webrtcstore의 isPassword도 변동
  
  // 방 생성에 사용할 함수들
  function updateMyuserName(event) {
    webrtcstore.updateMyUserName(event.target.value)    // pinia에 의해 dispatch로 접근X
    myUserName.value = event.target.value
  }

  function updateMysessionId(event) {
    webrtcstore.updateMySessionId(event.target.value)
    mySessionId.value = event.target.value
  }
  
  function updateEndHour(event) {
    webrtcstore.updateEndHour(Number(event.target.value))
    endHour.value = Number(event.target.value)
  }
  function updateEndMinute(event) {
    webrtcstore.updateEndMinute(Number(event.target.value))
    endMinute.value = Number(event.target.value)
  }
  
  function updateIsPassword(event) {
    webrtcstore.updateIsPassword(event.target.checked)
    isPassword.value = event.target.checked
    password.value = webrtcstore.password
  }
  function updateIsPrivacy(event) {
    webrtcstore.updateIsPrivacy(event.target.checked)
    isPrivacy.value = event.target.checked
  }

  function updatePassword(event){
    webrtcstore.updatePassword(event.target.value)
    password.value = event.target.value
  }

  function updateQuota(event){
    webrtcstore.updateQuota(Number(event.target.value))
    quota.value = Number(event.target.value)
  }
  
  function creatorIsHost() {  // webrtcstore의 isHost값을 true로 만들어줌
    webrtcstore.creatorIsHost()
    isHost.value=true
  }

  function joinSession() {

    // if(!webrtcstore.myUserName || !webrtcstore.mySessionId){
    if(!webrtcstore.myUserName){
      alert("이름을 작성해주세요.")
      return
    }
    if(!webrtcstore.mySessionId){
      alert("방제목을 작성해주세요.")
      return
    }
    creatorIsHost() // 방장권한 부여
    webrtcstore.isMakingTrue() // 방을 만들수 있는 권한 부여
    webrtcstore
    router.push({
      name:'roomJoin',
      params: { 
        roomName: encodeURIComponent(mySessionId.value),  // 인코딩해서 보내줘야만 작동함
      },
    })
  }

  // 키워드 관련 함수
  const roomTags = ref([])
  const roomTag = ref(null)
  // 키워드 추가하는 함수
  function addTag(event) {
    event.preventDefault();
    // if(roomTag.value in roomTags.value){
    if(roomTags.value.includes(roomTag.value)){
      alert('이미 추가된 키워드입니다.')
      roomTag.value = null  
      return
    }
    // roomTag는 최대 3개만 사용할 수 있게하기 위해
    else if(roomTag.value && roomTags.value.length < 3){
      roomTags.value.push(roomTag.value)
      webrtcstore.updateRoomTags(roomTag.value)
    }
    else if(!roomTag.value){
      alert('추가할 키워드명을 입력해주세요')
    }
    else{
      alert('키워드를 더 추가할 수 없습니다.')
    }
    roomTag.value = null
  }
  // 키워드 삭제하는 함수
  function removeTag(index) {
    roomTags.value.splice(index, 1)
    webrtcstore.updateRoomTags(roomTag.value)
  }
  // 키워드는 최대 15글자.
  function limitInput() {
   const maxLength = 15
    if (roomTag && roomTag.value && roomTag.value.length > maxLength) {
      roomTag.value = roomTag.value.slice(0, maxLength)
    }
  }


  // 이미지 업로드 및 미리보기 함수
  async function readInputImage(event){
    // console.log(event.target.files[0])
    webrtcstore.updateBackImg(event.target.files[0])
    backImgFile.value = event.target.files[0]

    // 이미지 파일을 데이터 Url로 변환하기
    const reader = new FileReader()
    reader.onload = (event) => {
      imgPreviewUrl.value = event.target.result
      console.log(backImgFile.value)
      console.log(imgPreviewUrl.value)
    }
    reader.readAsDataURL(backImgFile.value)

    // 서버로 전송할 FormData 객체 생성
    try{
      console.log('업로드 되나1')
      const imgformData = new FormData();
      imgformData.append("file", backImgFile.value);
      const response = await axios.post(APPLICATION_SERVER_URL+'files/upload/room/'+userNo.value, imgformData, {
      // const response = await axios.post(`http://localhost:8080/`+'files/upload/room/'+userNo.value, imgformData, {
      // const response = await axios.post(`https://i9d205.p.ssafy.io/api/`+'files/upload/room/'+userNo.value, imgformData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });
      console.log('리스폰스', response)
      console.log('리스폰스', response.data)
      // console.log(imgformData)
      webrtcstore.updateUploadImage(imgformData)
      // console.log(userNo.value)
      // webrtcstore.uploadImagetoServer(userNo.value)
      console.log('업로드 성공인가?')
    }
    catch(error){
      console.log('업로드 안되나', error)
    }
  }

  // uploadImagetoServer(userNo) {
  //     // 서버에 이미지 파일 업로드하기
  //     try {
  //       const response = axios.post(this.APPLICATION_SERVER_URL+'files/upload/room/'+userNo, this.imgformData, {
  //         headers: {
  //           "Content-Type": "multipart/form-data",
  //         },
  //       });
  //       console.log("이미지 업로드 성공: ", response.data);
  //     } catch (error) {
  //       console.log("이미지 업로드 에러: ", error);
  //     }
  //   },

  const rule = ref(webrtcstore.rule)
  function updateRule(event){
    webrtcstore.updateRule(event.target.value)
    rule.value = event.target.value
  }

  // // 이미지 파일 업로드 함수
  // async function uploadImage() {
  //   // 서버로 전송할 FormData 객체 생성
  //   try{
  //     console.log('업로드 되나1')
  //     const imgformData = new FormData();
  //     imgformData.append("file", backImgFile.value);
  //     console.log(imgformData)
  //     updateUploadImage(backImgFile.value)
  //     console.log(userNo.value)
  //     webrtcstore.uploadImagetoServer(userNo.value)
  //     console.log('업로드 되나2')
  //   }
  //   catch(error){
  //     console.log('없나')
  //   }
  //   webrtcstore.uploadImagetoServer(userNo.value)
  // }



</script>

<template>
  <!-- <router-link :to="{name: 'ArticleDetailView',params: {id: article.id }}"></router-link> -->
  <div id="join" style="color: white;">
    <div id="img-div">
    </div>
    <div id="join-dialog">
      <h1>스터디룸 생성하기</h1>
      <div>
        <!-- 방 제목 설정 -->
        <p>
          <label>방 제목 설정: </label>
          <input :value="mySessionId" @input="updateMysessionId" required />
        </p>
        <!-- 스터디룸 룰 작성 부분 -->
        <p>
          <textarea :value="rule" name="" id="" cols="35" rows="5" @input="updateRule"></textarea>
        </p>
        <!-- 이미지 파일 업로드 및 미리보기 -->
        <p>
          <form @submit.prevent="uploadImage" enctype="multipart/form-data">
            <input type="file" accept="image/*" @change="readInputImage" id="backImgFile" >
          </form>
            <div v-if="backImgFile">
              <img :src="imgPreviewUrl" alt="imgPreview" style="max-width: 300px; max-height: 300px">
            </div>
        </p>
        <!-- 닉네임 설정은 백 연동 완료 이후 삭제해야 함 -->
        <p>
          <label>닉네임 설정: </label>
          <input :value="myUserName" @input="updateMyuserName" required />
        </p>
        <p>
          <form @submit="addTag"> <!-- 메서드 생성 -->
            <div>
              <label for="tag">키워드</label>
              <input id="tag" type="text" v-model="roomTag" @input="limitInput">
            </div>
            <button type="submit">키워드 추가</button>
          </form>
          <span v-for="(tag, i) in roomTags" :key="i">
            {{ tag }}
            <!-- <button id="kwDelBtn{{i}}">X</button> -->
            <button @click="removeTag(i)">X</button>
          </span>
        </p>
        <!-- 비밀번호 설정여부 -->
        <p>
          <label for="">비밀번호 여부</label>
          <input type="checkbox" :checked="isPassword" @change="updateIsPassword">
          <span id="isPasswordSpan">미설정</span>
          <input v-if="isPassword" type="text" :value="password" @keyup="updatePassword">
        </p>
        <!-- 프라이버시 설정여부 -->
        <p>
          <label for="">프라이버시 여부</label>
          <input type="checkbox" :checked="isPrivacy" @change="updateIsPrivacy">
          <span id="isPrivacySpan">미설정</span>
        </p>
        
        <!-- 방 종료 시간 작성 -->
        <p>
          <label>시간 설정</label>
          <input type="checkbox" v-model="timeSet">
          
          <div v-if="timeSet">
            <select :value="endHour" @change="updateEndHour">
              <option disabled>시간(시)을 설정해주세요</option>
              <option v-for="(hour) in hours" :key="hour">{{ hour }}</option>
            </select>
            <span>시간</span>
            <select :value="endMinute" @change="updateEndMinute">
              <option disabled>시간(분)을 설정해주세요</option>
              <option  v-for="(minute) in minutes" :key="minute">{{ minute }}</option>
            </select>
            <span>분</span>
          </div>
        </p>
        
        <!-- 방 제한 인원수 -->
        <p>
          <label>제한 인원수: </label>
          <select name="" id="" :value="quota" @change="updateQuota">
            <option>2</option>
            <option>4</option>
            <option>8</option>
            <option>16</option>
          </select>
        </p>
        
        <p>
          <button @click="joinSession">
            방 생성하기
          </button>
        </p>
      </div>
    </div>
  </div>
</template>

<style>

</style>