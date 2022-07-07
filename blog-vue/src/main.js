import {createApp} from 'vue';
import App from './App.vue';
import router from "@/router/index.js";
import store from '@/store/index';

import 'element-plus/theme-chalk/el-message.css';


import {ElMessage} from "element-plus";

import {library} from '@fortawesome/fontawesome-svg-core';
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome';
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
    faSpinner,
    faClockRotateLeft,
    faClock,
    faUser,
    faLock,
    faEnvelope,
    faMobileScreenButton,
    faPlus,
} from '@fortawesome/free-solid-svg-icons';

import {faGithub, faQq, faWeixin} from "@fortawesome/free-brands-svg-icons";


import "@/assets/css/reset.css";
import "font-awesome/css/font-awesome.min.css";
import "animate.css";


// fortawesome 图标库
library.add(faHome, faSearch, faTags, faTag, faThLarge, faUserCircle,
    faAt, faAddressCard, faCodeFork, faChevronDown, faMapPin,
    faArrowUp, faCalendarDay, faBarChart, faMagnifyingGlass,
    faFileCircleXmark, faSpinner, faClockRotateLeft, faClock,
    faUser, faLock, faEnvelope, faMobileScreenButton, faPlus,
    faGithub, faQq, faWeixin);


const app = createApp(App);
app.component('font-awesome-icon', FontAwesomeIcon);
app.use(router);
app.use(store);
// app.use(VueTinymce);
// app.use(highlightJs);


app.provide('message', ElMessage);

app.mount('#app');
window.vm = app;
