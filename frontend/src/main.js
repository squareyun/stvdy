import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

const app = createApp(App)
const pinia = createPinia()
// pinia.use(piniaPersist)

app.use(router)
app.use(pinia)
app.mount('#app')
