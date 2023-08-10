<script setup></script>
<template>
  <div>
    <span id="tag-tile">키워드 트렌드</span>
    <div id="tag-area">
      <h3>키워드</h3>
      <span>경찰</span>
      <span>9급 공무원</span>
      <span>임용고시</span>
      <span>대학원생</span>
      <span>개발자</span>
      <span>Java</span>
      <span>보드게임</span>
      <span>우테코</span>
      <span>공무원</span>
      <span>경찰 공무원</span>
      <span>코딩</span>
      <span>50:10</span>
      <span>자율</span>
      <span>대학원생</span>
      <span>개발자</span>
      <span>Java</span>
      <span>보드게임</span>
      <span>우테코</span>
      <span>공무원</span>
      <span>경찰 공무원</span>
      <span>코딩</span>
      <span>경찰 공무원</span>
      <span>9급 공무원</span>
      <span>임용고시</span>
      <span>SSAFY</span>
      <span>코딩</span>
      <span>주식</span>
      <span>50:10</span>
      <span>자율</span>
      <span>대학원생</span>
      <span>개발자</span>
      <span>Java</span>
      <span>보드게임</span>
      <span>우테코</span>
      <span>공무원</span>
      <span>경찰 공무원</span>
      <span>9급 공무원</span>
      <span>임용고시</span>
      <span>SSAFY</span>
      <span>코딩</span>
      <span>경찰 공무원</span>
      <span>9급 공무원</span>
      <span>임용고시</span>
      <span>SSAFY</span>
      <span>코딩</span>
      <span>면접</span>
    </div>
    <div id="tag-hidden">
      <svg
        id="tag-more-btn"
        width="1.5rem"
        height="0.4rem"
        viewBox="0 0 128 32"
        fill="none"
        xmlns="http://www.w3.org/2000/svg">
        <path
          d="M32 16.0015C32 7.72061 24.8 0.945351 16 0.945351C7.2 0.945351 0 7.72061 0 16.0015C0 24.2824 7.2 31.0576 16 31.0576C24.8 31.0576 32 24.2824 32 16.0015ZM48 16.0015C48 24.2824 55.2 31.0576 64 31.0576C72.8 31.0576 80 24.2824 80 16.0015C80 7.72061 72.8 0.945351 64 0.945351C55.2 0.945351 48 7.72061 48 16.0015ZM96 16.0015C96 24.2824 103.2 31.0576 112 31.0576C120.8 31.0576 128 24.2824 128 16.0015C128 7.72061 120.8 0.945351 112 0.945351C103.2 0.945351 96 7.72061 96 16.0015Z"
          fill="white" />
      </svg>
    </div>
  </div>
</template>
<style>
#tag-area {
  float: left;
  text-align: center;

  overflow: hidden;
  word-break: break-all;

  height: 260px;
}

#tag-area > span {
  display: inline-block;
  background-color: var(--background-window);

  color: var(--hl-light);
  transition: color 0.4s;

  font-family: 'ASDGothicUL';

  border: 1px solid var(--border-color);
  border-radius: 8px;
}

#tag-area > h3 {
  display: inline;
  color: var(--hl-pres);
  font-family: 'ASDGothicM';
  font-weight: 100;

  margin-right: 20px;
}

#tag-hidden {
  position: absolute;

  height: 40px;

  background: linear-gradient(to bottom, transparent, var(--background-up));
}

#tag-more-btn {
  position: absolute;
  top: 50px;
  right: 20px;
}

@media (min-width: 1600px) {
  #tag-area {
    width: calc(100vw - 1370px);

    margin-top: 10px;
  }

  #tag-tile {
    display: none;
  }

  #tag-area > span {
    padding: 0.2vw 0.4vw 0.1vw 0.4vw;
    font-size: 1vw;

    margin-right: 0.5vw;
    margin-bottom: 0.3vw;
  }

  #tag-area > h3 {
    font-size: 1vw;
  }

  #tag-hidden {
    top: 220px;
    width: calc(100vw - 1370px);

    margin-top: 10px;
  }
}

@media (max-width: 1600px) {
  #tag-area {
    width: 230px;

    margin-top: 40px;
  }

  #tag-tile {
    display: none;

    position: absolute;
    top: 309px;
    left: 0px;

    color: var(--hl-light);

    font-size: 1rem;
  }

  #tag-area > span {
    padding: 0.2rem 0.4rem 0.1rem 0.4rem;
    font-size: 1rem;

    margin-right: 7px;
    margin-bottom: 4px;
  }

  #tag-area > h3 {
    font-size: 1rem;
  }

  #tag-hidden {
    top: 520px;
    width: 230px;

    margin-top: 40px;
  }
}
</style>
