<script setup>
import { onMounted, computed } from 'vue'
import { useUserStore } from '@/stores'
import { nameUser } from '@/api/user'
import Deactivate from '@/components/profile/Deactivate.vue'
import { RouterLink, RouterView } from 'vue-router'
import { Form, Field } from 'vee-validate'
import * as Yup from 'yup'
import router from '@/router'
import { listQuestion } from '@/api/question'
</script>

<template>
  <div></div>
</template>

<style>
#user-background-img {
  width: 960px;
  height: 220px;

  border-radius: 10px 10px 0px 0px;

  margin-left: -30px;

  background-image: url('/testBackground.png');
  background-size: cover;
  background-position: center;
}

#user-profile-img {
  position: absolute;
  top: 170px;
  left: 30px;

  width: 200px;
  height: 100px;

  border-radius: 50px;

  background-image: url('/testProfile.png');
  background-size: cover;
  background-position: center;

  box-shadow: 0 2px 3px rgba(0, 0, 0, 0.16), 0 2px 3px rgba(0, 0, 0, 0.23);
}

#user-name {
  margin-top: 5px;
  margin-left: 210px;

  color: var(--hl-light);
}

#edit-menu {
  position: absolute;
  top: 230px;
  right: 30px;

  text-align: right;
}

#user-edit {
  position: relative;
}

.field {
  display: block;
  background-color: var(--hl-window);
  margin-top: -12px;
  margin-bottom: 4px;
  padding-left: 18px;

  color: var(--hl-light);

  font-size: 1rem;

  width: 360px;
  height: 46px;
  border-radius: 10px;
  border: 1px solid var(--hl-light30);
}

.field:focus {
  outline: none;
  box-shadow: 0 0 0 1px var(--font80) inset;
}

.field-name {
  position: relative;
  display: inline;
  margin-left: 11px;

  /* font-size: 14px; */
  font-size: 0.9rem;

  text-align: center;
  color: var(--hl-light);

  background-color: var(--background-window);
}

#transmit-button {
  position: absolute;
  top: 20px;
  left: 326px;

  border: 0;
  background-color: transparent;

  color: var(--hl-pres);
  transition: color 0.4s;
  font-size: 1rem;

  cursor: pointer;
}

#transmit-button:hover {
  color: var(--hl-light);
  transition: color 0.4s;
}

.error-yup {
  color: var(--hl-warn);
}
#edit-menu > a {
  display: block;
  margin-bottom: 0.3rem;

  color: var(--hl-pres);
  transition: color 0.4s;

  font-size: 1rem;
  text-decoration: none;
}

#edit-menu > a:hover {
  color: var(--hl-light);
  transition: color 0.4s;
}

.others {
  padding-top: 30px;
}

.disabled {
  color: var(--hl-light50);
}
</style>
