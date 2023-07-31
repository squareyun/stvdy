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
        roomNo: mySessionId.value,
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
      <h1>Join a video session</h1>
      <div>
        <p>
          <label>Participant</label>
          <input :value="myUserName" @input="updateMyuserName" required />
        </p>
        <p>
          <label>Session</label>
          <input :value="mySessionId" @input="updateMysessionId" required />
        </p>
        <p>
          <button @click="joinSession">
            Join!
          </button>
        </p>
      </div>
    </div>
  </div>
</template>

<style>

</style>