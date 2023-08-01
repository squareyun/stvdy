<script setup>
  import { ref, computed, onUpdated, watch } from 'vue'
  import { useRouter } from "vue-router"
  import { webRtcStore } from "@/stores"
  // import { storeToRefs } from "pinia";


  const store = webRtcStore()
  const router = useRouter()
  
  // 방 생성에 사용할 변수들 
  const myUserName = ref(store.myUserName)
  const mySessionId = ref(store.mySessionId)
  
  // 방 시간 관련 내용
  // const hours = ref([...Array(24).keys()].map(hour=> hour.toString()))
  const hours = ref([...Array(25).keys()])
  // const minutes = ref([...Array(60).keys()].map(minute=> minute.toString()))
  const minutes = ref([...Array(60).keys()])
  const endHour = ref(store.endHour)
  const endMinute = ref(store.endMinute)
  const timeSet = ref(false)
  // 방 비공개 관련 내용
  const isPrivacy = ref(store.isPraivacy)

  //tiemSet이 flase면 종료시간 초기화.
  watch(timeSet, (newtimeSet) => {
    if (!newtimeSet) {
      store.updateEndHour(0);
      endHour.value = 0;
      store.updateEndMinute(0);
      endMinute.value = 0;
    }
  })
  // endHour와 timeSet이 둘다 0이면 안됨.
  // if(endHour.value === 0 && endMinute.value === 0 ){
  //   alert("타이머가 0일 수는 없습니다. \n다시 설정 해주세요.")
  //   endHour.value = 0
  //   endMinute.value = 1
  // }
  watch(endHour, (newendHour) => {
    if (timeSet && newendHour === 0 && endMinute.value === 0) {
      alert("타이머가 0일 수는 없습니다. \n다시 설정 해주세요.");
      console.log(typeof newendHour)
      console.log(typeof endMinute.value)
      endHour.value = 0
      endMinute.value = 1
    }
  })
  watch(endMinute, (newendMinute) => {
    if (timeSet && endHour.value === 0 && newendMinute === 0) {
      alert("타이머가 0일 수는 없습니다. \n다시 설정 해주세요.");
      console.log(typeof endHour.value)
      console.log(typeof newendMinute)
      endHour.value = 0
      endMinute.value = 1
    }
  })

  watch(isPrivacy,(newisPrivacy) => {
    const isPrivacySpan = document.getElementById("isPrivacySpan")
    if(newisPrivacy){
      isPrivacySpan.innerText = "비공개"
    }else{
      isPrivacySpan.innerText = "공개"
    }
  })
  // isPrivacy가 변동되면 store의 isPrivacy도 변동
  
  // 방 생성에 사용할 함수들
  function updateMyuserName(event) {
    store.updateMyUserName(event.target.value)    // pinia에 의해 dispatch로 접근X
    myUserName.value = event.target.value
  }

  function updateMysessionId(event) {
    console.log(event.target.value)
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
  
  function updateIsPravacy(event) {
    store.updateIsPravacy(event.target.checked)
    isPrivacy.value = event.target.checked
  }

  // 방 참가를 위한 함수
  function joinSession() {
    if(!store.myUserName || !store.mySessionId){
      alert("이름과 방제목을 작성해주세요.")
      return
    }
    router.push({
      name:'roomJoin',
      params: { 
        roomNo: encodeURIComponent(mySessionId.value),  // 인코딩해서 보내줘야만 작동함
      },
    })
  }
</script>

<template>
  <!-- <router-link :to="{name: 'ArticleDetailView',params: {id: article.id }}"></router-link> -->
  <div id="join" style="color: white;">
    <div id="img-div">
    </div>
    <div id="join-dialog">
      <h1>스터디룸 생성하기</h1>
      <div>
        <!-- 닉네임 설정은 백 연동 완료 이후 삭제해야 함 -->
        <p>
          <label>닉네임 설정: </label>
          <input :value="myUserName" @input="updateMyuserName" required />
        </p>
        <!-- 비공개 설정 -->
        <p>
          <label for="">비공개 여부</label>
          <input type="checkbox" :checked="isPrivacy" @change="updateIsPravacy">
          <span id="isPrivacySpan">공개</span>
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
          <label>제한 인원수</label>
          <select name="" id="">
            <option>2명</option>
            <option>4명</option>
            <option>8명</option>
            <option>16명</option>
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