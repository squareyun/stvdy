<script setup>
  import { ref, computed } from 'vue'
  import { useRouter } from "vue-router"
  import { webRtcStore } from "@/stores"


  const store = webRtcStore()
  const router = useRouter()
  
  // const myUserName = computed(() => {store.myUserName})
  // const mySessionId = computed(() => {store.mySessionId})
  const myUserName = ref(store.myUserName)
  const mySessionId = ref(store.mySessionId)
  
  
  function updateMyuserName(event) {
    store.updateMyUserName(event.target.value)    // pinia에 의해 dispatch로 접근X
    myUserName.value = event.target.value
  }

  function updateMysessionId(event) {
    console.log(event.target.value)
    store.updateMySessionId(event.target.value)
    mySessionId.value = event.target.value
  }
  
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
  <div id="join">
    <div id="img-div">
    </div>
    <div id="join-dialog">
      <h1>화상회의 방 생성하기</h1>
      <div>
        <p>
          <label>닉네임 설정: </label>
          <input :value="myUserName" @input="updateMyuserName" required />
        </p>
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