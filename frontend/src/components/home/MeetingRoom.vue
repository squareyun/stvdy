<script setup></script>
<template>
  <div>
    <span id="room-list-tile">인기 스터디 룸</span>
    <div id="room-list-area"></div>
  </div>
</template>
<style>
#room-list-area {
  position: absolute;
  top: 330px;
  height: 300px;

  background-color: brown;
  opacity: 0.3;
}

#room-list-tile {
  position: absolute;
  top: 309px;

  color: var(--hl-light);
  font-size: 1rem;
}

@media (min-width: 1600px) {
  #room-list-area {
    width: calc(100vw - 400px);
  }

  #room-list-tile {
    left: 0px;
  }
}

@media (max-width: 1600px) {
  #room-list-area {
    right: 0px;
    width: 720px;
  }

  #room-list-tile {
    right: 0px;
  }
}
</style>
