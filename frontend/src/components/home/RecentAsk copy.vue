<script setup>
import { useQuestionStore } from '@/stores'
import { computed } from 'vue'

const questionStore = useQuestionStore()
const questions = computed(() => questionStore.questions)

const query = {
  keyword: '',
  nickname: '',
  page: 0,
}
questionStore.getList(query)
</script>
<template>
  <div>
    <span id="home-ask-list-title">새로운 질문</span>
    <div id="home-ask-area">
      <span
        class="home-asks"
        v-for="qtn in questions">
        <div class="home-asks-inner-box">
          <div class="home-asks-title">{{ qtn.title }}</div>
          <div class="home-asks-detail">{{ qtn.detail }}</div>
        </div>
      </span>
    </div>
  </div>
</template>
<style>
#home-ask-area {
  position: absolute;
  top: 700px;

  writing-mode: vertical-rl;

  overflow: hidden;
  word-break: break-all;

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
  display: inline-block;

  vertical-align: middle;

  border-left: 7px solid var(--hl-light30);

  padding: 10px;
  margin-bottom: 30px;
}

.home-asks-title {
  font-size: 1.5rem;
}

.home-asks-detail {
  font-size: 1rem;
  font-family: 'ASDGothicUL';
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
}
</style>
