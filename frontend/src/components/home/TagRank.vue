<script setup></script>
<template>
  <div>
    <span id="tag-tile">키워드 트렌드</span>
    <div id="tag-area">
      <span>경찰 공무원</span>
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
      <span>남성전용</span>
      <span>주식</span>
      <span>50:10</span>
      <span>자율</span>
      <span>대학원생</span>
      <span>개발자</span>
      <span>우테코</span>
      <span>공무원</span>
      <span>경찰 공무원</span>
      <span>9급 공무원</span>
      <span>임용고시</span>
      <span>경찰 공무원</span>
      <span>9급 공무원</span>
      <span>임용고시</span>
      <span>SSAFY</span>
      <span>코딩</span>
      <span>면접</span>
      <span>남성전용</span>
      <span>주식</span>
      <span>50:10</span>
      <span>자율</span>
      <span>대학원생</span>
      <span>개발자</span>
      <span>보드게임</span>
      <span>우테코</span>
      <span>공무원</span>
      <span>경찰 공무원</span>
      <span>9급 공무원</span>
      <span>임용고시</span>
      <span>SSAFY</span>
      <span>코딩</span>
      <span>면접</span>
      <span>경찰 공무원</span>
      <span>9급 공무원</span>
      <span>임용고시</span>
      <span>SSAFY</span>
      <span>코딩</span>
      <span>면접</span>
      <span>남성전용</span>
      <span>주식</span>
      <span>50:10</span>
      <span>자율</span>
      <span>대학원생</span>
      <span>개발자</span>
      <span>9급 공무원</span>
      <span>임용고시</span>
      <span>SSAFY</span>
      <span>코딩</span>
      <span>면접</span>
      <span>남성전용</span>
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
      <span>면접</span>
      <span>남성전용</span>
      <span>주식</span>
      <span>50:10</span>
      <span>자율</span>
      <span>대학원생</span>
      <span>개발자</span>
      <span>Java</span>
      <span>보드게임</span>
      <span>우테코</span>
      <span>공무원</span>
    </div>
  </div>
</template>
<style>
#tag-area {
  float: left;
  text-align: center;
  height: 265px;

  overflow: hidden;
  word-break: break-all;
}

#tag-area > span {
  display: inline-block;

  padding: 0.2rem 0.4rem 0.1rem 0.4rem;
  margin-right: 7px;
  margin-bottom: 5px;
  background-color: var(--background-window);

  color: var(--hl-light);
  transition: color 0.4s;

  font-family: 'ASDGothicUL';
  font-size: 0.9rem;

  border: none;
  border-radius: 8px;
}

@media (min-width: 1600px) {
  #tag-area {
    width: calc(100vw - 1370px);
  }

  #tag-tile {
    display: none;
  }
}

@media (max-width: 1600px) {
  #tag-area {
    width: 230px;

    margin-top: 40px;
  }

  #tag-tile {
    display: block;

    position: absolute;
    top: 309px;
    left: 0px;

    color: var(--hl-light);

    font-size: 1rem;
  }
}
</style>
