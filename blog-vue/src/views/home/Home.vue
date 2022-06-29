<template>
  <!-- 封面 -->
  <HomeBanner></HomeBanner>
  <div class="main-content">
    <ul v-infinite-scroll="load" class="article-list">
      <li class="article-list-item" v-for="i in count" :key="i">
        <div class="title">
          <a href="">{{ `title` }}</a>
        </div>
        <div class="times">{{ `times-创建时间.更新时间` }}</div>
        <div class="content-abstract">{{ `摘要` }}</div>
      </li>
    </ul>
    <!--  主页右侧内容  -->
    <div class="main-right-content"></div>
  </div>
</template>

<script>

import {ref, reactive} from "vue";
import HomeBanner from "@/views/banner/HomeBanner";

export default {
  name: "Home",
  components: {HomeBanner},
  setup() {
    const count = ref(20)
    const load = () => {
      if (count.value >= 50) {
        alert("没有了别划了");
        return;
      }
      count.value += 5
    }

    return {count, load};

  }
}
</script>

<style scoped>
/* 文章容器 */
.article-list {
  width: 960px;
  background-color: #22ffde;
  border-radius: inherit;
  padding: inherit;
  margin: 5px 10px;
}

/* 文章item */
.article-list-item {
  width: auto;
  height: auto;
  background-color: #bbffaa;
  margin-top: 10px;
  border-radius: inherit;
  padding: inherit;
}

.main-right-content {
  width: 300px;
  height: 200px;
  background-color: #2196f3;
  border-radius: inherit;
  margin: 5px 10px;
}

</style>
