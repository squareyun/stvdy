<script setup></script>
<template>
  <div>
    <div id="word-area">
      <h1>꾸물거리지 마라.</h1>
      <h1>위대한 행운의 기회는 짧은 것이다.</h1>
      <h3>실리우스 이탈리쿠스</h3>
    </div>
  </div>
</template>
<style>
#word-area {
  position: absolute;
  top: 380px;
  text-align: center;

  overflow: hidden;
  word-break: break-all;

  color: var(--hl-light);

  height: 300px;
}

@media (min-width: 1600px) {
  #word-area {
    width: calc(100vw - 1140px);

    margin-top: 10px;
  }
}

@media (max-width: 1600px) {
  #word-area {
    display: none;
  }
}
</style>
