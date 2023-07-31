<script setup>
  import { ref, computed } from 'vue'
  // import axios from 'axios'
  // import { OpenVidu } from "openvidu-browser";
  // import UserVideo from "@/components/UserVideo.vue";
  import { useRouter } from "vue-router"; // useRouter 임포트
  // import { useStore } from "vuex"; // useRouter 임포트
  import { webRtcStore } from "@/stores"; // useRouter 임포트

  const store = webRtcStore()
  const router = useRouter()
  const mySessionId = ref("")
  const myUserName = ref("")
  
  // function getGenrecards() {
  //   this.$store.dispatch('getGenrecards')
  // },
  function updateMyuserName(event) {
    $store.commit('GET_MYUSERNAME', e.target.value)
  }

  function updateMysessionId(event) {
    $store.commit('GET_MYSESSIONID', e.target.value)
  }


  function joinSession() {
    if(!${store.webRtcStore.mySessionId} || !${store.webRtcStore.mySessionId}){
      alert("이름과 방제목을 작성해주세요.")
      return
    }
    router.push({
      name:'roomJoin',
      params: { 
        roomNo: mySessionId.value,
        // sessionId: mySessionId.value, 
        // userName: myUserName.value ,
      },
    })
  }
</script>

<template>
    
  <!-- <router-link :to="{name: 'ArticleDetailView',params: {id: article.id }}"></router-link> -->
  <div id="join">
      <div id="img-div">
        <!-- <img src="resources/images/openvidu_grey_bg_transp_cropped.png" alt="오픈비두 로고 이미지임"> --><!-- 이미지부분 -->
      </div>
      <div id="join-dialog">
        <h1>Join a video session</h1>
        <div>
          <p>
            <label>Participant</label>
            <!-- <input v-model="myUserName" type="text" required /> -->
            <input :value="myUserName" @input="updateMyuserName" required />
          </p>
          <p>
            <label>Session</label>
            <!-- <input v-model="mySessionId" type="text" required /> -->
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