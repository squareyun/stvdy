<script setup></script>
<template>
  <div>
    <div id="ad-area"></div>
    <div id="ad-filter">
      <div id="ad-text">
        <p>2023 FW 스터디 리그</p>
        <p>최우수 성취도 팀 발표</p>
      </div>
    </div>
  </div>
</template>
<style>
#ad-area {
  width: 960px;
  height: 300px;

  background-image: url('/ad-gif.gif');
  background-size: cover;
  background-position: center;
  border-radius: 10px;
}

#ad-filter {
  position: absolute;
  top: 0px;
  right: 0px;
  width: 960px;
  height: 300px;

  background-color: #d9d9d933;
  border-radius: 10px;
}

#ad-text {
  position: absolute;

  bottom: 20px;
  left: 10px;

  color: var(--hl-light);
  font-size: 'ASDGothicM';
  font-size: 4rem;
  font-weight: 700;
}

#ad-text > p {
  margin: 0px;
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
