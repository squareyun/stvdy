<template>
  <div v-if="streamManager">
    <OvVideo :stream-manager="streamManager" />
    <!-- {{ clientData }} is the current name -->
    <div>
      <p>{{ clientData }}</p>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'OvVideo',
  }
</script>
<script setup>
  import { defineProps, computed, defineExpose } from 'vue';
  import OvVideo from '@/components/webrtc/OvVideo.vue';

  const props = defineProps({
    streamManager: Object,
  });

  // clientData is computed
  const clientData = computed(() => {
    const { clientData } = getConnectionData();
    return clientData;
  });

  function getConnectionData() {
    const { connection } = props.streamManager.stream;
    return JSON.parse(connection.data);
  }

  // // Expose component's name
  // defineExpose({
  //   name: 'UserVideo',
  // });
</script>