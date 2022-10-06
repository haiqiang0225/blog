<template>
  <CommonBanner :backgroundImageUrl="pic"></CommonBanner>
  <div class="main-content" v-loading="onInit">
    <div class="tag-container content-glass">
      <h1>标签</h1>

      <button
          @click="searchByTag(item.tagId)"
          class="tag-item"
          :style="{color: colorList[(index + randomSeed) % colorList.length]}"
          v-for="(item, index) in tags"
          :key="index"
      >

        {{ item.name }}

      </button>

    </div>
  </div>
</template>

<script>
import {ref} from "vue"
import CommonBanner from "@/views/banner/CommonBanner";
import axios from "@/utils/axios"
import {ElMessage} from "element-plus";

export default {
  name: "Tag",
  components: {CommonBanner},
  setup() {
    //todo: 后端
    const pic = require('@/assets/image/index-main.jpg');
    const tags = ref();
    const onInit = ref(true);

    let url = "/api/tag/get/all"
    axios.get(url)
        .then(res => {
          tags.value = res.data.tags;
          onInit.value = false;
        })
        .catch(err => {
          console.log(err)
        })


    // 默认有的颜色,减少随机生成深色颜色的调用次数
    const colorList = ['#102b6a', '#d93a49', '#1d953f', '#dea32c',
      '#65c294', '#c63c26', '#009ad6', '#f36c21',
      '#ef5b9c', '#2e3a1f', '#007947', '#f173ac',
      '#009ad6', '#fdb933', '#00ae9d', '#694d9f'];

    // 随机生成的偏移量, 用于从colorList取颜色时的偏移
    const randomSeed = Math.floor(Math.random() * 10);
    // 随机生成深色颜色的函数
    const randomColor = function (exist_color) {
      let r = Math.floor(Math.random() * 192);
      let g = Math.floor(Math.random() * 192);
      let b = Math.floor(Math.random() * 192);
      let r16 = r.toString(16).length === 1 && r.toString(16) <= "f" ? 0 + r.toString(16) : r.toString(16);
      let g16 = g.toString(16).length === 1 && g.toString(16) <= "f" ? 0 + g.toString(16) : g.toString(16);
      let b16 = b.toString(16).length === 1 && b.toString(16) <= "f" ? 0 + b.toString(16) : b.toString(16);
      let color = '#' + r16 + g16 + b16;
      if (exist_color.indexOf(color) === -1) {
        return color;
      } else {
        randomColor(exist_color);
      }
    };
    for (let i = 0, len = colorList.length; i < len; ++i) {
      colorList.push(randomColor(colorList));
    }

    return {pic, tags, colorList, randomSeed, onInit};
  },
  methods: {
    // 根据Tag查询文章
    async searchByTag(tagId) {
      console.log(tagId)
      let url = "/api/article/tags?start=0&tagId=" + tagId
      let res = await axios.get(url)
      if (res.data.code === 403) {
        ElMessage.error("访问过快,请等待一分钟再访问!")
        return;
      }
      if (res.data.articles === undefined || res.data.articles === null || res.data.articles.length <= 0) {
        ElMessage.warning("暂时没有文章有该标签")
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

.tag-container {
  position: relative;
  padding: 10px;
  width: 100%;
  height: auto;
  min-height: 64px;
  border-radius: 16px;
}


.tag-container::after {
  content: '';
  height: 64px;
  clear: both;
}

.tag-container h1 {
  float: top;
  font-size: 2rem;
  top: 26px;
  margin: 10px auto;
  text-align: center;
}


.tag-item {
  height: 30px;
  width: 10%;
  border: 1px solid rgba(221, 221, 221, 0.9);
  border-radius: 18px;
  background-color: #fafafa;
  justify-content: center;
  padding: 0 5px;
  font-size: 12px;
  table-layout: fixed;
  word-break: break-all;
  line-height: 30px;
  text-align: center;
  opacity: .8;
  margin: 0 1% 10px 1%;
  float: left;
  text-overflow: ellipsis;
}

.tag-item:hover {
  cursor: pointer;
}
</style>
