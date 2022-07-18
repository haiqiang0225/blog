import {createRouter, createWebHistory} from "vue-router";
import CommonHello from "@/views/banner/CommonBanner";
import Home from "@/views/home/Home";
import Category from "@/views/category/Category";
import Tag from "@/views/tag/Tag";
import AboutMe from "@/views/about/AboutMe";
import Article from "@/views/article/Article";
import MessageBoard from "@/views/message/MessageBoard";
import Login from "@/views/login/Login";

const routes = [
    {
        path: '/',
        name: 'home',
        component: Home,
    },
    {
        path: '/category',
        name: 'category',
        component: Category,
    },
    {
        path: '/tag',
        name: 'tag',
        component: Tag
    },
    {
        path: '/message-board',
        name: 'messageBoard',
        component: MessageBoard,
    },
    {
        path: '/about-me',
        name: 'aboutMe',
        component: AboutMe,
    },
    {
        path: '/login',
        name: 'login',
        component: Login,
    },
    {
        path: '/article/:id',
        name: 'article',
        component: Article,
        // props: {
        //     id: true,
        //     article: true
        // }
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
    scrollBehavior(to, from, savedPosition) {
        if (to.hash) {
            return {
                el: to.hash,
                behavior: 'smooth',
            };
        } else {
            // 页面刷新时保存滚动条位置, 切换路由时恢复位置
            return savedPosition ? savedPosition : {top: 0};
        }
    }
});


export default router
