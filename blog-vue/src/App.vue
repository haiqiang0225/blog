<template>
  <el-scrollbar ref="scrollbarRef" always height="100vh" @scroll="asyncGlobalScroll">
    <!-- 进度条 -->
    <div v-show="onNavChange" class="loading-container">
      <div class="progress"></div>
    </div>
    <!-- 过渡时遮罩 -->
    <div v-show="onNavChange" class="cover-container">
      <div class="cover"></div>
    </div>
    <!-- 网页整体背景色 -->
    <div class="background"></div>
    <!-- 顶部导航栏组件 -->
    <TopNavigator></TopNavigator>
    <!-- 主内容布局组件 -->
    <router-view :key="$route.fullPath"></router-view>
    <!-- 页脚 -->
    <Footer></Footer>
    <!--  <div style="height: 10000px"></div>-->
  </el-scrollbar>


</template>


<script>
import {ref} from "vue"


import TopNavigator from "@/components/layout/TopNavigator";
import Main from "@/components/layout/MainContent";
import Footer from "@/components/layout/Footer";


export default {

  name: 'App',
  components: {
    Footer,
    Main,
    TopNavigator,
  },
  setup() {
    // 地址栏地址变化时,用于触发过渡动画
    let onNavChange = ref(true);
    setTimeout(() => {
      onNavChange.value = false;
    }, 400);
    return {onNavChange}
  },
  methods: {
    asyncGlobalScroll(scrollPos) {
      this.$store.commit('asyncScrollTop', {
        scrollTop: scrollPos.scrollTop,
      })
    },

  },
  created() {
    this.$watch(() => this.$route.fullPath, (newVal, oldVal) => {
      this.onNavChange = true;
      setTimeout(() => {
        this.onNavChange = false;
      }, 400);
    });
  },
  mounted() {
    // 将滚动条存到vuex中,方便其它组件调用
    this.$store.state.globalScrollBar = this.$refs.scrollbarRef;
  }
}
</script>

<style>

/* start===设置x轴不出现滚动条===start */
html {
  overflow-y: auto;
}

:root {
  overflow-y: auto;
  overflow-x: hidden;
}

:root body {
  position: absolute;
}

body {
  height: 100vh;
  width: 100vw;
}

/**修改全局的滚动条*/
/**滚动条的宽度*/
::-webkit-scrollbar {
  width: 8px;
}

::-webkit-scrollbar-thumb {
  background-color: #eaecf1;
  border-radius: 3px;
}

/*表格*/
.el-table__body-wrapper::-webkit-scrollbar {
  width: 10px;
  height: 10px;
}

.el-table__body-wrapper::-webkit-scrollbar-thumb {
  background-color: #a1a3a9;
  border-radius: 3px;
}


/* end=====设置x轴不出现滚动条=====end */


/* 整体背景样式 */
.background {
  background-image: linear-gradient(20deg, rgba(210, 188, 188, 0.7), rgb(190, 241, 178), rgba(67, 163, 166, 0.7));
  width: 100%;
  height: 100vh;
  position: fixed;
  z-index: -2;
}

/* 进度条 */
.loading-container {
  position: fixed;
  border-radius: 10px;
  width: 100%;
  z-index: 2;
}


.loading-container .progress {
  position: relative;
  border-radius: 10px;
  top: 0;
  left: 0;
  width: 100%;
  height: 6px;
  background: linear-gradient(45deg, #22ffde, #2196f3);
  animation: processing 0.25s ease-in-out;
}


@keyframes processing {
  0% {
    width: 0;
  }
}

/* 页面遮罩及对应的动画 */
.cover-container {
  position: fixed;
  width: 100vw;
  height: 100vh;
  z-index: 2;

  animation: cover-animate 0.5s;
}

@keyframes cover-animate {
  0% {
    backdrop-filter: saturate(150%) blur(8px);
    -webkit-backdrop-filter: saturate(150%) blur(8px);
    background-color: rgba(0, 0, 0, .3);
  }
  100% {
    backdrop-filter: unset;
    -webkit-backdrop-filter: unset;
    background-color: rgba(0, 0, 0, 0);
  }
}

@-moz-keyframes cover-animate {
  0% {
    backdrop-filter: saturate(150%) blur(8px);
    -webkit-backdrop-filter: saturate(150%) blur(8px);
    background-color: rgba(0, 0, 0, .3);
  }
  100% {
    backdrop-filter: unset;
    -webkit-backdrop-filter: unset;
    background-color: rgba(0, 0, 0, 0);
  }
}

/* 通用动画 */
/* Banner 出现动画 */
.banner-fade-in-down {
  animation: top-banner-fade-in-anm 0.35s;
  animation-duration: 0.35s;
}

@keyframes top-banner-fade-in-anm {
  0% {
    transform: translateY(-120px);
    opacity: 0.85;

  }

  100% {
    transform: translateY(0);
    opacity: 1;
  }
}

@-moz-keyframes top-banner-fade-in-anm {
  0% {
    transform: translateY(-120px);
    opacity: 0.85;

  }

  100% {
    transform: translateY(0);
    opacity: 1;
  }
}

/* 内容区出现动画 */
.main-fade-in-up {
  animation: main-fade-in-anm 0.35s;
  animation-duration: 0.35s;
}

@keyframes main-fade-in-anm {
  0% {
    transform: translateY(120px);
    opacity: 0.85;
  }

  100% {
    transform: translateY(0);
    opacity: 1;
  }
}

@-moz-keyframes main-fade-in-anm {
  0% {
    transform: translateY(120px);
    opacity: 0.85;
  }

  100% {
    transform: translateY(0);
    opacity: 1;
  }
}

</style>
