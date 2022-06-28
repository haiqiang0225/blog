import {createRouter, createWebHistory} from "vue-router";
import CommonHello from "@/views/banner/CommonBanner";
import Home from "@/views/home/Home";
import Category from "@/views/category/Category";

const routes = [
    {
        path: '/',
        name: 'home',
        component: Home
    },
    {
        path: '/category',
        name: 'category',
        component: Category,
    },
    {
        path: '/tag',
        name: 'tag',
        components: {
            'Home': CommonHello,
        },
    },
    {
        path: '/message-board',
        name: 'messageBoard',
        components: {
            'Home': CommonHello,
        },
    },
    {
        path: '/about-me',
        name: 'aboutMe',
        components: {
            'Home': CommonHello,
        },
    },
    {
        path: '/login',
        name: 'login',
        components: {},
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
    scrollBehavior(to, from, savedPosition) {
        // 页面刷新时保存滚动条位置, 切换路由时恢复位置
        return savedPosition ? savedPosition : {top: 0};
    }
});


export default router
