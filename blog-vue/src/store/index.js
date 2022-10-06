import {createStore} from "vuex";
import actions from "@/store/actions";
import mutations from "@/store/mutations";


const state = {
    globalScrollBar: {},
    scrollTop: 0,
    articles: [],
    searchMode: false,
    keyWord: null,
    lookRandoms: [],
    tags: [],
    // 控制主页是否需要按默认方式加载文章
    needSimpleLoad: true,
    firstVisitOurHomePage: true,
};

export default createStore({
    state,
    actions,
    mutations,
    getters: {}
});
