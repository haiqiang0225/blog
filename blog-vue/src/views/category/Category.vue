<template>

  <CommonBanner></CommonBanner>
  <div class="main-content" v-loading="onInit">
    <div class="category-container content-glass">
      <h1>分类</h1>
      <!--   分类列表   -->
      <ul>

        <!--    列表项    -->
        <li
            v-for="(item, index) in categoryList"
            class="category-item"
            :key="index"
        >
          <router-link
              to=""
              @click="search(item)"
              :key="item.id"
          >
            <span>● &nbsp;</span>
            {{ item.name }}
            &nbsp;
            <div class="category-count">({{ item.count ? item.count : 0 }})</div>

          </router-link>

        </li>
      </ul>
    </div>

  </div>

</template>

<script>
import {ref} from "vue"

import CommonBanner from "@/views/banner/CommonBanner";

import axios from "@/utils/axios";
import {ElMessage} from "element-plus";

export default {
  name: "Category",
  components: {CommonBanner},
  setup() {
    //todo: 点击分类跳转到文章列表页并显示所有该分类下的文章
    const categoryList = ref();
    const onInit = ref(true);

    let url = "/api/category/get/all";
    axios.get(url)
        .then(res => {
          categoryList.value = res.data.categories;
          onInit.value = false;
        })
        .catch(err => {
          ElMessage.error("出错了");
          console.log(err);
        });

    return {categoryList, onInit};
  },
  methods: {
    async search(category) {
      let categoryId = category.categoryId;
      if (categoryId === undefined || categoryId === null) {
        ElMessage.error("出错了")
        return;
      }
      let url = "/api/article/category?start=0&categoryId=" + categoryId;
      let res = await axios.get(url);
      if (res.data.code === 403) {
        ElMessage.error("访问过快,请等待一分钟再访问!")
        return;
      }
      await this.$store.commit('syncArticles', {articles: res.data.articles})
      await this.$store.commit('setNeedSimpleLoad', false);
      await this.$router.push("/")
    }
  }
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
