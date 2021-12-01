import { createApp } from 'vue'
import App from './App.vue'
import { key, store } from './store/store'
import router from './router/router'

const app = createApp(App);
app.use(store, key);
app.use(router);
app.mount('#app');
