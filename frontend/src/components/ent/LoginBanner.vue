<template>
  <div class="cBanner">Test Image</div>
</template>

<style scoped>
.cBanner {
  /* background-color: aquamarine; */
  border-radius: 20px;
  width: 720px;
  height: 720px;
  margin-right: 20px;

  background-image: url('./test.png');
}

@media (max-width: 1216px) {
  .cBanner {
    display: none;
  }
}
</style>
