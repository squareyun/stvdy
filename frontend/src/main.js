import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPersist from 'pinia-plugin-persist'

import App from './App.vue'
import router from './router'

import { fakeBackend } from './helpers'
fakeBackend()

const app = createApp(App)
const pinia = createPinia()
pinia.use(piniaPersist)

app.use(router)
app.use(pinia)
// app.use((req, res, next) => {
//   res.setHeader('Access-Control-Allow-Origin', '*')
//   res.header('Access-Control-Allow-Methods', 'GET, OPTIONS, PUT, POST')
//   res.header('Access-Control-Allow-Headers', 'Content-Type, Authorization')
//   res.header('Access-Control-Allow-Credentials', 'true')
// })

// app.globalProperties.$axios = axios
app.mount('#app')
