export default {
    asyncScrollTop(state, payload) {
        state.scrollTop = payload.scrollTop;
    },
    syncArticles(state, payload) {
        state.articles = payload.articles
    },
    articlesConcat(state, payload) {
        state.articles = state.articles.concat(payload.articles)
    },
    setSearchMode(state, mode) {
        state.searchMode = mode
    },
    setKeyWord(state, keyword) {
        state.keyWord = keyword
    },
    setLookRandoms(state, lookRandoms) {
        state.lookRandoms = lookRandoms
    },
    setTags(state, tags) {
        state.tags = tags
    },
    setNeedSimpleLoad(state, flag) {
        state.needSimpleLoad = flag;
    },
    setFirstVisitOurHomePage(state, flag) {
        state.firstVisitOurHomePage = flag;
    }
};
