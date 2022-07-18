<template>
  <transition
      appear
      name="animate__animated "
      enter-active-class="banner-fade-in-down"
  >
    <div class="banner-container">
      <div class="hello-img"></div>
      <h1>JHQ的博客</h1>
      <h2>{{ bannerText }} &nbsp;<transition-group
          appear
          name="animate__animated "
          enter-active-class="show-cursor"
          leave-active-class="show-cursor"
      >
        <span v-show="showCursorAnimation" key="1">|</span>
      </transition-group>
      </h2>
      <button @click="scrollToInfo" class="show-info-btn">

        <transition-group
            appear
            name="animate__animated "
            enter-active-class="animate__slideInDown"
            leave-active-class="animate__slideOutUp"
        >
          <font-awesome-icon v-show="showInfoAnimation" icon="fa-chevron-down" key="1"/>
        </transition-group>
      </button>
    </div>
  </transition>
</template>

<script>

import {reactive, ref} from "vue";
import {useStore} from "vuex"
import axios from "@/utils/axios";


export default {
  name: "Home",
  components: {},
  setup() {
    // 动画相关控制
    let showInfoAnimation = ref(true);
    let showCursorAnimation = ref(true);

    // vuex
    const store = useStore();
    // 获取全局的滚动条
    const globalScrollBar = store.state.globalScrollBar;

    // 控制动画的定时器
    const userAnimationInterval = setInterval(() => {
      showInfoAnimation.value = !showInfoAnimation.value;
      showCursorAnimation.value = !showCursorAnimation.value;
    }, 1000);

    const bannerText = ref('');

    let intervalText = function (text) {
      let i = 0;
      let textInterval = setInterval(() => {
        bannerText.value = text.substring(0, ++i);
        if (i === text.length) {
          clearInterval(textInterval);
        }
      }, 100);
    }

    axios.get("/hitokoto", {
      timeout: 1500,
    })
        .then(res => {
          intervalText(res.data.hitokoto);
        })
        .catch(err => {
          // 备用
          let t = [
            "彼岸花花开彼岸，断肠草草断肝肠。",
            "独学而无友，则孤陋而寡闻。",
            "万钟一品不足论，时来出手苏元元。",
            "人生最大的喜悦是每个人都说你做不到，你却完成它了!",
            "过去属于死神，未来属于你自己。",
            "永远都要记住你的人生掌握在你手中",
            "人生中出现的一切，都无法拥有，只能经历。",
            "不要慨叹生活底痛苦！---慨叹是弱者……",
            "宿命论是那些缺乏意志力的弱者的借口。",
            "简单的做人，不沉迷于幻想。",
            "人生最大遗憾莫过于错误坚持和轻易放弃",
            "千里之行，始于足下"
          ];
          intervalText(t[Math.floor(Math.random() * t.length)])
          // console.log(err)
        });


    // 从主页面移动到内容区的方法
    let scrollToInfo = function () {
      globalScrollBar.scrollTo({
        top: document.documentElement.clientHeight,
        behavior: "smooth",
      });
    };
    return {showInfoAnimation, scrollToInfo, showCursorAnimation, globalScrollBar, bannerText};
  }
}
</script>

<style scoped>

/* 欢迎容器样式*/
.banner-container {
  position: relative;
  height: 100vh;
  width: 100vw;
  max-width: 100vw;
  overflow: hidden;
}

/* 欢迎页背景样式 */
.hello-img {
  background-image: url("@/assets/image/index-main.jpg");
  background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;
  background-color: #464646;
  width: 100vw;
  height: 100vh;
  max-width: 100vw;
  min-width: 930px;
  position: absolute;
}

/* 欢迎页中间的蚊子🦟样式*/
.banner-container h1 {
  position: absolute;
  color: white;
  font-size: 70px;
  width: 600px;
  height: 100px;
  text-align: center;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  margin: auto;

}

.banner-container h2 {
  position: absolute;
  color: white;
  font-size: 30px;
  width: 600px;
  height: 100px;
  text-align: center;
  top: 200px;
  bottom: 0;
  left: 0;
  right: 0;
  margin: auto;

}

/* 欢迎页下方跳动的按钮样式 */
.show-info-btn {
  position: absolute;
  font-size: 24px;
  color: white;
  background-color: unset;
  border: 0;
  cursor: pointer;
  width: 50px;
  height: 66px;
  top: 94vh;
  left: 0;
  right: 0;
  margin: 0 auto;
  z-index: 1;
}

.show-cursor {
  animation: flash;
  animation-duration: 2s;
}

</style>
