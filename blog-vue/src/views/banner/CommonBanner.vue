<template>
  <transition
      appear
      name="animate__animated "
      enter-active-class="banner-fade-in-down"
  >
    <div class="banner-container">
      <div class="hello-img" :style='{backgroundImage: "url(" + backgroundImg + ")",}'></div>
    </div>
  </transition>
</template>

<script>
import {useRoute} from "vue-router";
import {ref} from "vue";

export default {
  name: "CommonHello",
  props: {
    backgroundImageUrl: {
      type: String,
      required: false,
    }
  },
  setup(props, context) {
    const route = useRoute();
    let path = ref(route.path);
    // 使用自定义图片或者默认: 路由path + '-hello.jpg'
    let backgroundImg = props.backgroundImageUrl ||
        require('../../assets/image' + path.value + '-hello.jpg');
    backgroundImg = ref(backgroundImg);
    return {backgroundImg, path};
  },
  created() {
    // this.$watch(() => this.$route.params, (toParams, previousParams) => {
    //   this.backgroundImg = toParams.backgroundImaURL;
    // });
  }
}
</script>

<style scoped>

/* 欢迎容器样式*/
.banner-container {
  position: relative;
  height: 35vh;
  width: 100vw;
  max-width: 100vw;
  overflow: hidden;
}

/* 欢迎页背景样式 */
.hello-img {
  background-repeat: no-repeat;
  /*background-position: center center;*/
  /*background-size: cover;*/
  background-color: #464646;
  width: 100vw;
  height: 100vh;
  max-width: 100vw;
  min-width: 930px;
  position: absolute;
}

</style>
