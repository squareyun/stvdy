<script setup></script>
<template>
  <div><div id="ad-area"></div></div>
</template>
<style>
#ad-area {
  width: 960px;
  height: 300px;

  background-color: black;
}

@media (min-width: 1600px) {
  #ad-area {
    float: right;
  }
}

@media (max-width: 1600px) {
  #ad-area {
    float: none;
  }
}
</style>
