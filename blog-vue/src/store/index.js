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
    tags: []
};

export default createStore({
    state,
    actions,
    mutations,
    getters: {}
});
