import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import axios from 'axios'

axios.defaults.baseURL = 'http://54.180.9.43:8080'

import { fakeBackend } from './helpers'
fakeBackend()

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')
