import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPersist from 'pinia-plugin-persist'

import App from './App.vue'
import router from './router'
import axios from 'axios'

axios.defaults.baseURL = 'http://54.180.9.43:8080'

import { fakeBackend } from './helpers'
fakeBackend()

const app = createApp(App)
const pinia = createPinia()
pinia.use(piniaPersist)

app.use(router)
app.use(pinia)

app.mount('#app')
