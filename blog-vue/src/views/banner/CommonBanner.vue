<template>
  <transition
      appear
      name="animate__animated "
      enter-active-class="banner-fade-in-down"
  >
    <div class="banner-container" :style="{height: bannerHeight}">
      <div class="hello-img" :style='{backgroundImage: "url(" + backgroundImg + ")",}'>
        <div class="info-container">
          <div class="info">
            <slot name="info"></slot>
          </div>
        </div>
      </div>
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
    },
    bannerHeight: {
      type: String,
      required: false,
      default: '35vh'
    }
  },
  setup(props, context) {
    const route = useRoute();
    const path = ref(route.path);
    // 使用自定义图片或者默认: 路由path + '-hello.jpg'
    let backgroundImg = props.backgroundImageUrl ||
        require('../../assets/image' + path.value + '-hello.jpg');
    backgroundImg = ref(backgroundImg);
    const bannerHeight = props.bannerHeight || '35vh';
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
  background-color: #464646;
  width: 100vw;
  height: 100vh;
  max-width: 100vw;
  min-width: 930px;
  position: absolute;
}

/* 中间展示信息样式 */
.info-container {
  position: relative;
  height: 35vh;
  width: 100vw;
  max-width: 100vw;
  overflow: hidden;
}

.info-container::before {
  content: '';
  display: inline-block;
  vertical-align: middle;
  height: 45%;
}

.info {
  position: absolute;
  width: 900px;
  max-width: 1000px;
  min-width: 900px;
  left: 0;
  right: 0;
  margin: auto auto;
  /*transform: translateY(0%);*/
}

</style>
