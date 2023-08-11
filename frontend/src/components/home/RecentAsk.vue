<script setup>
import { useQuestionStore } from '@/stores'
import router from '@/router'
import { computed } from 'vue'

const questionStore = useQuestionStore()
const questions = computed(() => questionStore.questions)

const query = {
  keyword: '',
  nickname: '',
  page: 0,
  noAnsFilter: false,
  noBestAnsFilter: false,
}
questionStore.getList(query)

async function showDetail(id) {
  router.push(`/questiondetail/${id}`)
}
</script>
<template>
  <div>
    <span id="home-ask-list-title">새로운 질문</span>
    <div id="home-ask-area">
      <div
        class="home-asks"
        v-for="qtn in questions"
        @click="showDetail(qtn.id)">
        <div class="home-asks-inner-box">
          <div class="home-asks-title">{{ qtn.title }}</div>
          <div class="home-asks-detail">{{ qtn.detail }}</div>
        </div>
      </div>
    </div>
  </div>
</template>
<style>
#home-ask-area {
  position: absolute;
  top: 700px;

  column-count: 3;

  color: var(--hl-light);
}

#home-ask-list-title {
  position: absolute;
  top: 679px;
  left: 0px;

  color: var(--hl-light);
  font-size: 1rem;
}

.home-asks {
  position: relative;

  border-left: 7px solid var(--hl-light30);

  padding: 10px;
  margin-bottom: 30px;

  cursor: pointer;

  -webkit-column-break-inside: avoid;
  page-break-inside: avoid;
  break-inside: avoid;
}

.home-asks-title {
  color: var(--hl-light);
  font-size: 1.5rem;
}

.home-asks-detail {
  font-size: 1rem;
  font-family: 'ASDGothicUL';
  color: var(--hl-light);

  max-height: 4rem;

  overflow: hidden;
}

@media (min-width: 1600px) {
  #home-ask-area {
    width: calc(100vw - 400px);
  }

  .home-asks {
    width: calc((100vw - 400px) / 3 - 27px);
  }
}

@media (max-width: 1600px) {
  #home-ask-area {
    width: 960px;
  }

  .home-asks {
    width: 293px;
  }

  .home-asks-title {
    font-size: 1.3rem;
  }
}
</style>
