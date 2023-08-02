<template>
  <div id="banner">Test Image</div>
</template>

<style scoped>
#banner {
  border-radius: 20px;
  height: 720px;
  width: 720px;
  margin-right: 20px;

  background-image: url('./LoginBanner.png');
  background-size: cover;
  background-position: center;
}

@media (max-width: 1225px) {
  #banner {
    width: calc(100vw - 505px);
  }
}

@media (max-width: 715px) {
  #banner {
    width: 0px;
  }
}
</style>
