import {createApp} from 'vue'
import App from './App.vue'
import router from "@/router/index.js";
import store from '@/store/index';

import {library} from '@fortawesome/fontawesome-svg-core'
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'
// fortawesome 图标库
import {
    faHome,
    faSearch,
    faTags,
    faTag,
    faThLarge,
    faUserCircle,
    faAt,
    faAddressCard,
    faCodeFork,
    faChevronDown,
    faMapPin,
    faArrowUp,
    faCalendarDay,
    faBarChart,
    faMagnifyingGlass,
    faFileCircleXmark,
} from '@fortawesome/free-solid-svg-icons'


import "@/assets/css/reset.css"
import "font-awesome/css/font-awesome.min.css"
import "animate.css"

// fortawesome 图标库
library.add(faHome, faSearch, faTags, faTag, faThLarge, faUserCircle,
    faAt, faAddressCard, faCodeFork, faChevronDown, faMapPin,
    faArrowUp, faCalendarDay, faBarChart, faMagnifyingGlass,
    faFileCircleXmark);


const app = createApp(App)
app.component('font-awesome-icon', FontAwesomeIcon)
app.use(router)
app.use(store)
app.mount('#app')
