<script setup>
import { ref, computed, onUpdated, watch, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { usewebRtcStore } from '@/stores'
import { useUserStore } from '@/stores'

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
const imgPreviewUrl = ref(webrtcstore.roomImagePath) // 초기값으로 유저의 이미지를 가짐

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

// sc3 이미지 잘되나????
// const computedImage = ref(null)
// onUpdated(()=>{
//   computedImage.value = downloadImagefromServer()
// })

onMounted(() => {
  // creatorIsHost()
  // joinImmediately() // css 동안 임시로 해둠.
})

// css 동안 임시로 해둠.
function joinImmediately() {
  setTimeout(() => {
    joinSession()
  }, 1000)
}

//tiemSet이 flase면 종료시간 초기화.
watch(timeSet, (newtimeSet) => {
  if (!newtimeSet) {
    webrtcstore.updateEndHour(0)
    endHour.value = 0
    webrtcstore.updateEndMinute(0)
    endMinute.value = 0
  } else {
    webrtcstore.updateEndHour(0)
    endHour.value = 0
    webrtcstore.updateEndMinute(1)
    endMinute.value = 1
  }
})
// endHour와 timeSet이 둘다 0이면 안됨.
if (timeSet.value && endHour.value === 0 && endMinute.value === 0) {
  alert('타이머가 0일 수는 없습니다. \n다시 설정 해주세요.')
  endHour.value = 0
  endMinute.value = 1
}
watch(endHour, (newendHour) => {
  if (timeSet.value && newendHour === 0 && endMinute.value === 0) {
    alert('타이머가 0일 수는 없습니다. \n다시 설정 해주세요.')
    endHour.value = 0
    endMinute.value = 1
  }
})
watch(endMinute, (newendMinute) => {
  if (timeSet.value && endHour.value === 0 && newendMinute === 0) {
    alert('타이머가 0일 수는 없습니다. \n다시 설정 해주세요.')
    endHour.value = 0
    endMinute.value = 1
  }
})

watch(isPassword, (newisPassword) => {
  const isPasswordSpan = document.getElementById('isPasswordSpan')
  if (newisPassword) {
    isPasswordSpan.innerText = '설정'
  } else {
    isPasswordSpan.innerText = '미설정'
  }
})
watch(isPrivacy, (newisPrivacySpan) => {
  const isPrivacySpan = document.getElementById('isPrivacySpan')
  if (newisPrivacySpan) {
    isPrivacySpan.innerText = '설정'
  } else {
    isPrivacySpan.innerText = '미설정'
  }
})
// isPassword가 변동되면 webrtcstore의 isPassword도 변동

// 방 생성에 사용할 함수들
function updateMyuserName(event) {
  webrtcstore.updateMyUserName(event.target.value) // pinia에 의해 dispatch로 접근X
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

function updatePassword(event) {
  webrtcstore.updatePassword(event.target.value)
  password.value = event.target.value
}

function updateQuota(event) {
  webrtcstore.updateQuota(Number(event.target.value))
  quota.value = Number(event.target.value)
}

function creatorIsHost() {
  // webrtcstore의 isHost값을 true로 만들어줌
  webrtcstore.creatorIsHost()
  isHost.value = true
}

function joinSession() {
  // if(!webrtcstore.myUserName || !webrtcstore.mySessionId){
  if (!webrtcstore.myUserName) {
    alert('이름을 작성해주세요.')
    return
  }
  if (!webrtcstore.mySessionId) {
    alert('방제목을 작성해주세요.')
    return
  }
  creatorIsHost() // 방장권한 부여
  webrtcstore.isMakingTrue() // 방을 만들수 있는 권한 부여
  webrtcstore
  router.push({
    name: 'roomJoin',
    params: {
      roomName: encodeURIComponent(mySessionId.value), // 인코딩해서 보내줘야만 작동함
    },
  })
}

// 키워드 관련 함수
const roomTags = ref([])
const roomTag = ref(null)
// 키워드 추가하는 함수
function addTag(event) {
  event.preventDefault()
  // if(roomTag.value in roomTags.value){
  if (roomTags.value.includes(roomTag.value)) {
    alert('이미 추가된 키워드입니다.')
    roomTag.value = null
    return
  }
  // roomTag는 최대 3개만 사용할 수 있게하기 위해
  else if (roomTag.value && roomTags.value.length < 3) {
    roomTags.value.push(roomTag.value)
    webrtcstore.updateRoomTags(roomTag.value)
  } else if (!roomTag.value) {
    alert('추가할 키워드명을 입력해주세요')
  } else {
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
async function readInputImage(event) {
  // console.log(event.target.files[0])
  webrtcstore.updateBackImg(event.target.files[0])
  backImgFile.value = webrtcstore.backImgFile

  // 이미지 파일을 데이터 Url로 변환하기
  const reader = new FileReader()
  reader.onload = (event) => {
    imgPreviewUrl.value = event.target.result
  }
  reader.readAsDataURL(backImgFile.value)
  uploadImagetoServer()
}

const rule = ref(webrtcstore.rule)
function updateRule(event) {
  webrtcstore.updateRule(event.target.value)
  rule.value = event.target.value
}

// 이미지 파일 업로드 함수
async function uploadImagetoServer() {
  // 서버로 전송할 FormData 객체 생성
  try {
    const imgformData = new FormData()
    imgformData.append('file', backImgFile.value)
    webrtcstore.updateUploadImage(imgformData) // webstore의 imgformData 변동
    webrtcstore.uploadImagetoServer(userNo.value) // 서버에 이미지 업로드하기
  } catch (error) {
    console.log(error)
  }
}
</script>

<template>
  <!-- <router-link :to="{name: 'ArticleDetailView',params: {id: article.id }}"></router-link> -->
  <div id="room-add">
    <div id="img-div"></div>
    <div id="add-dialog-box">
      <p>스터디 룸 생성하기</p>
      <!-- 방 제목 설정 -->
      <p class="add-field-name">&nbsp;&nbsp;방 제목 &nbsp;</p>
      <input
        class="add-field"
        :value="mySessionId"
        @input="updateMysessionId"
        required />
      <!-- 스터디룸 룰 작성 부분 -->
      <p class="add-field-name">&nbsp;&nbsp;그라운드 룰 &nbsp;</p>
      <textarea
        class="add-field-text"
        :value="rule"
        name=""
        id=""
        cols="35"
        rows="5"
        @input="updateRule"></textarea>
      <!-- 이미지 파일 업로드 및 미리보기 -->
      <form
        @submit.prevent="uploadImage"
        enctype="multipart/form-data"
        id="file-upload-btn">
        <input
          type="file"
          accept="image/*"
          @change="readInputImage" />
      </form>

      <div v-if="backImgFile">
        <img
          :src="imgPreviewUrl"
          alt="imgPreview"
          style="max-width: 300px; max-height: 300px" />
      </div>
      <!-- 닉네임 설정은 백 연동 완료 이후 삭제해야 함 -->

      <form
        id="keyword-box"
        @submit="addTag">
        <!-- 메서드 생성 -->
        <p class="add-field-name">&nbsp;&nbsp;키워드 &nbsp;</p>
        <input
          class="add-field"
          type="text"
          v-model="roomTag"
          @input="limitInput" />
        <button type="submit">추가</button>
      </form>
      <div
        v-for="(tag, i) in roomTags"
        :key="i"
        class="keyword-tag">
        + {{ tag }}
        <!-- <button id="kwDelBtn{{i}}">X</button> -->
        <button @click="removeTag(i)">X</button>
      </div>
      <br v-if="roomTags[0]" />
      <!-- 비밀번호 설정여부 -->
      <p class="add-field-name">&nbsp;&nbsp;비밀번호 여부 &nbsp;</p>
      <input
        class="add-field"
        type="text"
        :value="password"
        @keyup="updatePassword" />
      <!-- 프라이버시 설정여부 -->
      <label for="">프라이버시 여부</label>
      <input
        type="checkbox"
        :checked="isPrivacy"
        @change="updateIsPrivacy" />
      <span id="isPrivacySpan">미설정</span>

      <!-- 방 종료 시간 작성 -->
      <label>시간 설정</label>
      <input
        type="checkbox"
        v-model="timeSet" />

      <div v-if="timeSet">
        <select
          :value="endHour"
          @change="updateEndHour">
          <option disabled>시간(시)을 설정해주세요</option>
          <option
            v-for="hour in hours"
            :key="hour">
            {{ hour }}
          </option>
        </select>
        <span>시간</span>
        <select
          :value="endMinute"
          @change="updateEndMinute">
          <option disabled>시간(분)을 설정해주세요</option>
          <option
            v-for="minute in minutes"
            :key="minute">
            {{ minute }}
          </option>
        </select>
        <span>분</span>
      </div>

      <!-- 방 제한 인원수 -->
      <label>제한 인원수:</label>
      <select
        name=""
        id=""
        :value="quota"
        @change="updateQuota">
        <option>2</option>
        <option>4</option>
        <option>8</option>
        <option>16</option>
      </select>

      <button @click="joinSession">방 생성하기</button>

      <!-- <p>
          <img :src="computedImage" alt="업로드 후 다운로드가 되나?">
        </p> -->
    </div>
  </div>
</template>

<style>
#room-add {
  position: fixed;
  top: 55px;
  right: 500px;

  width: 400px;

  padding: 20px;
  padding-top: 1rem;

  border-radius: 20px;

  background-color: var(--hl-light);
  border: 1px solid #b7b7b3;
  border-radius: 20px;

  box-sizing: border-box;
  box-shadow: 0 2px 3px rgba(0, 0, 0, 0.16), 0 2px 3px rgba(0, 0, 0, 0.23);
}

#add-dialog-box > p:first-child {
  margin: 0;
  margin-bottom: 15px;
}

.add-field {
  background-color: var(--hl-light);
  margin-top: -12px;
  margin-bottom: 4px;
  padding-left: 18px;

  font-size: 1rem;
  color: var(--font100);

  width: 336px;
  height: 46px;
  border-radius: 10px;
  border: 1px solid var(--font30);
}

.add-field:focus {
  outline: none;
  box-shadow: 0 0 0 1px var(--font80) inset;
}

.add-field-text {
  display: block;

  background-color: var(--hl-light);
  margin-top: -12px;
  margin-bottom: 4px;
  padding-top: 15px;
  padding-left: 15px;

  font-size: 1rem;
  font-family: Arial;
  color: var(--font100);

  width: 339px;
  height: 120px;
  border-radius: 10px;
  border: 1px solid var(--font30);

  resize: none;
}

.add-field-text:focus {
  outline: none;
  box-shadow: 0 0 0 1px var(--font80) inset;
}

.add-field-name {
  position: relative;
  display: inline;
  margin-left: 11px;

  /* font-size: 14px; */
  color: var(--font80);
  font-size: 0.9rem;

  text-align: center;

  background-color: var(--hl-light);
}

#file-upload-btn > input[type='file']::file-selector-button {
  color: var(--hl-pres);
  font-family: 'ASDGothicM';
  font-size: 1rem;
  border: none;
  background-color: transparent;

  cursor: pointer;
}

#keyword-box {
  margin-top: 30px;
  position: relative;
}

#keyword-box > button {
  position: absolute;
  border: none;
  background-color: transparent;

  top: 18px;
  right: 0px;

  display: inline-block;
  width: 4rem;
  height: 2rem;

  color: var(--hl-pres);
  font-family: 'ASDGothicUL';
  font-size: 1rem;

  cursor: pointer;
}

.keyword-tag {
  color: var(--font100);
  font-size: 0.9rem;
}

.keyword-tag > button {
  color: var(--font80);
  font-family: 'ASDGothicH';

  border: none;
  background-color: transparent;
}

.password-check {
  display: block;
  margin-top: 30px;
}
</style>
