<template>

  <CommonBanner></CommonBanner>
  <div class="main-content">
    <div class="category-container content-glass">
      <h1>分类</h1>
      <!--   分类列表   -->
      <ul>

        <!--    列表项    -->
        <li
            v-for="item in categoryList"
            class="category-item"
        >
          <router-link
              to=""
              :key="item.id"
          >
            <span>● &nbsp;</span>
            {{ item.title }}
            &nbsp;
            <div class="category-count">({{ `0` }})</div>

          </router-link>

        </li>
      </ul>
    </div>

  </div>

</template>

<script>
import {ref} from "vue"

import CommonBanner from "@/views/banner/CommonBanner";

import bannerImgURL from "@/assets/image/category-hello.jpg";

export default {
  name: "Category",
  components: {CommonBanner},
  setup() {
    //todo: 后端请求分类
    const categoryList = [{
      id: 1,
      title: 'Java'
    }, {
      id: 2,
      title: 'Java'
    }];

    return {categoryList};
  },
}
</script>

<style scoped>

.category-container {
  position: relative;
  padding: 10px;
  width: 100%;
  height: auto;
  min-height: 64px;
  border-radius: 16px;
}

.category-container::after {
  content: '';
  height: 64px;
  clear: both;
}

.category-container h1 {
  float: top;
  font-size: 2rem;
  top: 26px;
  margin: 10px auto;
  text-align: center;
}

.category-item {
  position: relative;
  width: auto;
  left: 10%;
  font-size: 1.2rem;
  line-height: 2.5rem;
}

.category-item a {
  color: #464646;
}

.category-item span {
  color: blue;
}

.category-count {
  display: inline-block;
  font-size: 14px;
  color: #a1a3a9;
}

</style>
