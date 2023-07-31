<template>
  <div id="banner">Test Image</div>
</template>

<style scoped>
#banner {
  border-radius: 20px;
  width: 720px;
  height: 720px;
  margin-right: 20px;

  background-image: url('./LoginBanner.png');
  background-size: cover;
}

@media (max-width: 1216px) {
  #banner {
    display: none;
  }
}
</style>
