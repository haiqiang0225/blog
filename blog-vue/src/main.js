import {createApp} from 'vue'
import App from './App.vue'
import router from "@/router/index.js";
import store from '@/store/index';

import {library} from '@fortawesome/fontawesome-svg-core'
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'
import {
    faHome,
    faSearch,
    faTags,
    faThLarge,
    faUserCircle,
    faAt,
    faAddressCard,
    faCodeFork,
    faChevronDown
} from '@fortawesome/free-solid-svg-icons'


import "@/assets/css/reset.css"
import "font-awesome/css/font-awesome.min.css"
import "animate.css"


library.add(faHome, faSearch, faTags, faThLarge, faUserCircle, faAt, faAddressCard, faCodeFork, faChevronDown);


const app = createApp(App)
app.component('font-awesome-icon', FontAwesomeIcon)
app.use(router)
app.use(store)
app.mount('#app')
