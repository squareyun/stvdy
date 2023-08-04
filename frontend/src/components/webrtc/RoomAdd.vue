<script setup>
  import { ref, computed, onUpdated, watch, onMounted } from 'vue'
  import { useRouter } from "vue-router"
  import { usewebRtcStore } from "@/stores"
  import { useUsersStore } from "@/stores"
  import axios from 'axios' 
  // import { storeToRefs } from "pinia";

  const usersStore = useUsersStore()
  const localUser = usersStore.user
  console.log(localUser)

  const store = usewebRtcStore()
  const userstore = useUsersStore()
  const router = useRouter()
  
  // 방 생성에 사용할 변수들 
  const myUserName = ref(store.myUserName)
  // const myUserName = ref(localUser.username)
  const mySessionId = ref(store.mySessionId)
  
  // 방 이미지 관련 내용
  const backImgFile = ref(store.backImgFile)
  const imgPreviewUrl = ref(null)

  // 방 시간 관련 내용
  // const hours = ref([...Array(24).keys()].map(hour=> hour.toString()))
  const hours = ref([...Array(25).keys()])
  // const minutes = ref([...Array(60).keys()].map(minute=> minute.toString()))
  const minutes = ref([...Array(60).keys()])
  const endHour = ref(store.endHour)
  const endMinute = ref(store.endMinute)
  const timeSet = ref(false)
  // 방 비공개 관련 내용
  const isPassword = ref(store.isPassword)
  const password = ref(store.password)
  // 방 인원설정 관련 내용
  const quota = ref(store.quota)
  
  // 방 참여자들의 캠 전체 비활성화 여부를 의미
  const isPrivacy = ref(store.isPrivacy)

  //tiemSet이 flase면 종료시간 초기화.
  watch(timeSet, (newtimeSet) => {
    if (!newtimeSet) {
      store.updateEndHour(0);
      endHour.value = 0;
      store.updateEndMinute(0);
      endMinute.value = 0;
    }
    else{
      store.updateEndHour(0);
      endHour.value = 0;
      store.updateEndMinute(0);
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
  // isPassword가 변동되면 store의 isPassword도 변동
  
  // 방 생성에 사용할 함수들
  function updateMyuserName(event) {
    store.updateMyUserName(event.target.value)    // pinia에 의해 dispatch로 접근X
    myUserName.value = event.target.value
  }

  function updateMysessionId(event) {
    store.updateMySessionId(event.target.value)
    mySessionId.value = event.target.value
  }
  
  function updateEndHour(event) {
    store.updateEndHour(Number(event.target.value))
    endHour.value = Number(event.target.value)
  }
  function updateEndMinute(event) {
    store.updateEndMinute(Number(event.target.value))
    endMinute.value = Number(event.target.value)
  }
  
  function updateIsPassword(event) {
    store.updateIsPassword(event.target.checked)
    isPassword.value = event.target.checked
    password.value = store.password
  }
  function updateIsPrivacy(event) {
    store.updateIsPrivacy(event.target.checked)
    isPrivacy.value = event.target.checked
  }

  function updatePassword(event){
    store.updatePassword(event.target.value)
    password.value = event.target.value
  }

  function updateQuota(event){
    store.updateQuota(Number(event.target.value))
    quota.value = Number(event.target.value)
  }

  /////// 방 참가를 위한 함수
  // function joinSession() {
  //   if(!store.myUserName || !store.mySessionId){
  //     alert("이름과 방제목을 작성해주세요.")
  //     return
  //   }
  //   router.push({
  //     name:'roomJoin',
  //     params: { 
  //       roomName: encodeURIComponent(mySessionId.value),  // 인코딩해서 보내줘야만 작동함
  //     },
  //   })
  // }
  function joinSession() {
    if(!store.myUserName || !store.mySessionId){
      alert("이름과 방제목을 작성해주세요.")
      return
    }
    store.joinSession(router)  // 이 코드 대신 아래 코드를 추가했음.
  }
  function checkmyUserName(){
    store.checkmyUserName()
  }


  // 이미지 업로드 및 미리보기 함수
  function readInputImage(event){
    // console.log(event.target.files[0])
    store.updateBackImg(event.target.files[0])
    backImgFile.value = event.target.files[0]

    // 이미지 파일을 데이터 Url로 변환하기
    const reader = new FileReader()
    reader.onload = (event) => {
      imgPreviewUrl.value = event.target.result
      console.log(backImgFile.value)
      console.log(imgPreviewUrl.value)
    }
    reader.readAsDataURL(backImgFile.value)
  }

  const rule = ref(store.rule)
  function updateRule(event){
    store.updateRule(event.target.value)
    rule.value = event.target.value
  }
</script>

<template>
  <!-- <router-link :to="{name: 'ArticleDetailView',params: {id: article.id }}"></router-link> -->
  <div id="join" style="color: white;">
    <button @click="checkmyUserName">유저정보 확인버튼</button>
    <div id="img-div">
    </div>
    <div id="join-dialog">
      <h1>스터디룸 생성하기</h1>
      <div>
        <!-- 스터디룸 룰 작성 부분 -->
        <p>
          <textarea :value="rule" name="" id="" cols="35" rows="5" @input="updateRule"></textarea>
        </p>
        <!-- 이미지 파일 업로드 및 미리보기 -->
        <p>
          <input type="file" accept="image/*" @change="readInputImage" id="backImgFile" >
          <div v-if="backImgFile">
            <img :src="imgPreviewUrl" alt="imgPreview" style="max-width: 300px; max-height: 300px">
          </div>
        </p>
        <!-- 닉네임 설정은 백 연동 완료 이후 삭제해야 함 -->
        <p>
          <label>닉네임 설정: </label>
          <input :value="myUserName" @input="updateMyuserName" required />
        </p>
        <!-- 비공개 설정 -->
        <!-- <p>
          <label for="">비공개 여부</label>
          <input type="checkbox" :checked="isPassword" @change="updateIsPassword">
          <span id="isPasswordSpan">공개</span>
        </p> -->
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
        <!-- 방 제목 설정 -->
        <p>
          <label>방 제목 설정: </label>
          <input :value="mySessionId" @input="updateMysessionId" required />
        </p>
        <p>
          <button @click="joinSession">
            생성! 또는 참가!(임시)
          </button>
        </p>
      </div>
    </div>
  </div>
</template>

<style>

</style>