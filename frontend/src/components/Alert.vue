<script setup>
import { storeToRefs } from 'pinia';
import { useAlertStore } from '@/stores';

const alertStore = useAlertStore();
const { alert } = storeToRefs(alertStore);

</script>

<template>
  <div v-if="alert" class="" style="color:white">
    <div>
      <div class="alert" :class="alert.type">
        <button @click="alertStore.clear()" class="btn">&times;</button>
        {{ alert.message }}
        Alert box입니다.
      </div>
    </div>
  </div>
</template>