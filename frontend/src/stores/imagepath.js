import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import { useUserStore } from "@/stores"

import axios from 'axios'

export const useImagePath = defineStore({
  id: 'imagepath',
  state: () => ({
    // applicationServerUrl : "https://i9d205.p.ssafy.io/api/"
    applicationServerUrl : "/api/",
    questionerProfilePath : '/authorImage.png',  // 선택한 질문자의 프로필 이미지
  }),
  actions: {
    updateQuestionerProfilePath(newPath){
      this.questionerProfilePath = newPath
      sessionStorage.setItem('questionerImage', newPath)
      console.log(this.questionerProfilePath)
    },

    async uploadStudyImagetoServer(userNo, newimgformData){
      try {
        console.log('스터디룸 이미지 업로드중1')
        const response = await axios.post(this.applicationServerUrl+'files/upload/room/'+userNo, newimgformData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });
        console.log('스터디룸 이미지 업로드중2')
        console.log("스터디룸 이미지 업로드 성공: ", response.data);
        // return this.downloadStudyImagefromServer(userNo)
      } catch (error) {
        console.error("스터디룸 이미지 업로드 에러: ", error);
      }
    },
    async downloadStudyImagefromServer(userNo){
      try {
        const response = await axios.get(this.applicationServerUrl+'files/get/room/'+userNo,{
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });
        console.log("스터디룸 이미지 경로 다운로드 성공: ", response.data.message)
        return response.data
      } catch (error) {
        console.error("스터디룸 이미지 경로 다운로드 에러: ", error)
      }
    },
    async uploadProfileImagetoServer(userNo, newimgformData){
      try {
        console.log('프로필 이미지 업로드중1')
        const response = await axios.post(this.applicationServerUrl+'files/upload/profile/'+userNo, newimgformData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });
        console.log('프로필 이미지 업로드중2')
        console.log("이미지 업로드 성공: ", response.data);
      } catch (error) {
        console.error("이미지 업로드 에러: ", error);
      }
    },
    // async downloadProfileImagefromServer(){
    //   try {
    //     const response = await axios.get(this.applicationServerUrl+'files/get/room/'+userNo,{
    //       headers: {
    //         "Content-Type": "multipart/form-data",
    //       },
    //     });
    //     console.log("프로필 이미지 경로 다운로드 성공: ", response.data)
    //     return response.data
    //   } catch (error) {
    //     console.error("프로필 이미지 경로 다운로드 에러: ", error)
    //   }
    // }
  }
})
