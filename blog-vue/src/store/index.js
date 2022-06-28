import {createStore} from "vuex";
import actions from "@/store/actions";
import mutations from "@/store/mutations";


const state = {
    globalScrollBar: {},
    scrollTop: 0,
};

export default createStore({
    state,
    actions,
    mutations,
    getters: {

    }
});
