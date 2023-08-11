<script setup>
import { Form, Field } from 'vee-validate'
import router from '@/router'

const preset = localStorage.getItem('colorScheme')
if (!preset) {
  const colorPalette = {
    backgroundUp: '#282a36',
    backgroundDown: '#1b1f2c',
    backgroundWindow: '#44475a',
    borderColor: '#282a36',
    topbarBasic: '#282a36',
    topbarTime: '#f8f8f280',
    hlPres: '#bd93f9',
    hlPresO: '#a27aff',
    hlGreen: '#56fd7f',
    hlWarn: '#ff5555',
    hlLight: '#f8f8f2',
    hlLight80: '#f8f8f2cc',
    hlLight50: '#f8f8f280',
    hlLight30: '#f8f8f24d',
    hlLight20: '#f8f8f233',
    hlLight10: '#f8f8f21a',
    font100: '#202020',
    font80: '#202020cc',
    font50: '#20202080',
    font30: '#2020204d',
    font25: '#20202040',
    font20: '#20202033',
    font10: '#2020201a',
  }

  localStorage.setItem('colorScheme', JSON.stringify(colorPalette))
}

function onSubmit(value) {
  localStorage.setItem('colorScheme', value.scheme)
  // localStorage.removeItem('colorScheme')
  // console.log(33)
  router.go(0)
}

const reset = () => {
  const colorPalette = {
    backgroundUp: '#282a36',
    backgroundDown: '#1b1f2c',
    backgroundWindow: '#44475a',
    borderColor: '#282a36',
    topbarBasic: '#282a36',
    topbarTime: '#f8f8f280',
    hlPres: '#bd93f9',
    hlPresO: '#a27aff',
    hlGreen: '#56fd7f',
    hlWarn: '#ff5555',
    hlLight: '#f8f8f2',
    hlLight80: '#f8f8f2cc',
    hlLight50: '#f8f8f280',
    hlLight30: '#f8f8f24d',
    hlLight20: '#f8f8f233',
    hlLight10: '#f8f8f21a',
    font100: '#202020',
    font80: '#202020cc',
    font50: '#20202080',
    font30: '#2020204d',
    font25: '#20202040',
    font20: '#20202033',
    font10: '#2020201a',
  }

  localStorage.setItem('colorScheme', JSON.stringify(colorPalette))
  router.go(0)
}
</script>

<template>
  <div>
    <span class="mypage-content-title">레이아웃 색상 설정</span>
    <div class="mypage-content others">
      <Form @submit="onSubmit">
        <p class="field-name">&nbsp;&nbsp;json 포맷 스키마 &nbsp;</p>
        <Field
          v-slot="{ field }"
          v-model="preset"
          name="scheme">
          <textarea
            id="field-text"
            v-bind="field"
            type="text"
            name="scheme"
            placeholder="..."></textarea>
        </Field>
        <button id="password-btn">변경하기</button>
        <div
          id="reset-btn"
          @click="reset">
          초기화
        </div>
      </Form>
      <div id="light-theme-title">바로 밝은 테마를 적용해보세요!</div>
      <div id="light-theme-preset">
        {"backgroundUp":"#f8f8f2","backgroundDown":"#c3d1d4","backgroundWindow":"#ffffff","borderColor":"#282a3633","topbarBasic":"#282a3620","topbarTime":"#202020","hlPres":"#6389df","hlLight":"#282a36","hlLight80":"#282a36cc","hlLight50":"#282a3680","hlLight30":"#282a364d","hlLight20":"#282a3633","hlLight10":"#282a361a","font100":"#f8f8f2","font80":"#f8f8f2cc","font50":"#f8f8f280","font30":"#f8f8f24d","font25":"#f8f8f240","font20":"#f8f8f233","font10":"#f8f8f21a"}
      </div>
    </div>
  </div>
</template>

<style>
#password-btn {
  position: absolute;
  top: calc(50% - 1rem);
  right: 30px;

  padding: 0;
  border: none;
  background-color: transparent;

  font-family: 'ASDGothicM';
  font-size: 1rem;
  color: var(--hl-pres);
  transition: color 0.4s;

  cursor: pointer;
}

#reset-btn {
  position: absolute;
  top: calc(50% + 1rem);
  right: 30px;

  padding: 0;
  border: none;
  background-color: transparent;

  font-family: 'ASDGothicM';
  font-size: 1rem;
  color: var(--hl-pres);
  transition: color 0.4s;

  cursor: pointer;
}

#field-text {
  display: block;
  background-color: var(--hl-window);
  margin-top: -12px;
  margin-bottom: 4px;
  padding-top: 15px;
  padding-left: 18px;

  color: var(--hl-light);

  font-size: 1rem;

  width: 790px;
  height: 120px;
  border-radius: 10px;
  border: 1px solid var(--hl-light30);

  resize: none;
}

#field-text:focus {
  outline: none;
  box-shadow: 0 0 0 1px var(--font80) inset;
}

#field-name {
  position: relative;
  display: inline;
  margin-left: 11px;

  /* font-size: 14px; */
  font-size: 0.9rem;

  text-align: center;
  color: var(--hl-light);

  background-color: var(--background-window);
}

.others > form > .div-line {
  width: 400px;
  height: 1px;
  margin-top: 25px;
  margin-bottom: 20px;
  margin-left: -10px;

  background-color: var(--hl-light30);
  opacity: 0.5;
}

#password-btn:hover {
  color: var(--hl-light);
  transition: color 0.4s;
}

#light-theme-title {
  margin-top: 30px;
  margin-left: 10px;

  color: var(--hl-light);
  font-size: 1.1rem;
}

#light-theme-preset {
  width: 780px;
  height: 100px;

  margin-top: 10px;
  margin-left: 10px;
  padding-left: 10px;

  overflow: hidden;
  word-wrap: break-word;

  color: var(--hl-light);

  border-left: 7px solid var(--border-color);
}
</style>
