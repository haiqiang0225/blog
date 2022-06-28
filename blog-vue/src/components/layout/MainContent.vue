<template>

  <!-- 内容 -->
  <transition
      name="animate__animated "
      appear
      enter-active-class="main-fade-in-up"
  >
    <div class="main-container">
      <div class="content">
        <slot></slot>
      </div>
    </div>
  </transition>
</template>

<script>

import {ref, reactive} from "vue";
import Article from "@/views/article/Article";
import Home from "@/views/home/Home";

export default {
  name: "Main",
  components: {Home, Article},
  props: {},
  setup(props, context) {
    return {Home}
  },
  computed: {},

}
</script>

<style scoped>
/* 主容器样式 */
.main-container {
  position: relative;
  top: 24px;
}


.content {
  position: relative;
  height: 100%;
  width: 1300px;
  top: 0;
  left: 0;
  right: 0;
  margin: 0 auto;

  backdrop-filter: saturate(150%) blur(8px);
  -webkit-backdrop-filter: saturate(150%) blur(8px);
  background-color: rgba(0, 0, 0, .15);
  border-radius: 16px;
}

</style>
