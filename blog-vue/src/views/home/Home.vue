<template>
  <!-- 封面 -->
  <HomeBanner></HomeBanner>
  <!-- 文章内容 -->
  <div class="main-content">
    <!--  无限加载  -->
    <ul v-infinite-scroll="load"
        class="article-list"
        :infinite-scroll-disabled="disabledInfiniteLoading"
        v-loading="onInit"
    >
      <!--   文章卡片   -->
      <li class="article-list-item-card content-glass"
          v-for="(article, index) in articleList"
          :key="index">

        <!-- 文章配图 -->
        <a
            @click="toArticleDetail(article)"
            style="height: 100%; cursor: pointer;"
        >
          <el-image
              class="article-image"
              :class="index % 2 === 0? `image-float-left`: `image-float-right` "
              :src="article.articleImageUrl"
              :fit="`fill`"
          >
            <template #error>
              <div class="image-slot">
                <el-icon>
                  <font-awesome-icon icon="fa-solid fa-file-circle-xmark"/>
                </el-icon>
              </div>
            </template>
          </el-image>
        </a>


        <!--    文章信息    -->
        <div class="article-info">
          <!--  文章标题    -->
          <div class="title">
            <a
                @click="toArticleDetail(article)"
                style="height: 100%; cursor: pointer;"
            >
              {{ article.title }}
            </a>
          </div>
          <!--   是否置顶   -->
          <span v-if="article.topFlag" style="color: #ff7242; cursor: default;">
            <font-awesome-icon icon="fa-arrow-up"/>
            置顶
           <span class="separator">|</span>
          </span>

          <!--    发表时间    -->
          <font-awesome-icon icon="fa-calendar-day"/>
          {{ article.createDate || new Date().toLocaleDateString().replaceAll("/", "-") }}
          <span class="separator">|</span>

          <!--     文章分类     -->
          <router-link to="#" class="article-category">
            <font-awesome-icon icon="fa-th-large"/>
            {{ article.category != null ? article.category.name : `` }}
          </router-link>
          <span class="separator">|</span>

          <!--     文章标签     -->
          <router-link to="" class="article-tag"
                       v-for="(tag, index) in article.tags"
                       :key="index"
          >
            <font-awesome-icon icon="fa-tag"/>
            {{ tag.name }}
          </router-link>

          <!--    文章内容    -->
          <div class="article-content">
            {{ article.abstractTitle }}
          </div>

        </div>
      </li>

      <li class="loading-text" v-show="loading && !onInit">

        <font-awesome-icon class="loading" style="position: relative; top: 10px; right: 10px"
                           icon="fa-solid fa-spinner"/>
        <span>
          Loading ...
        </span>
      </li>
    </ul>


    <!--  主页右侧内容  -->
    <div class="main-right-content" style="position: sticky; top: 50px;">

      <!--  随便看看    -->
      <div class="look-random content-glass" v-loading="onInit">
        <div class="mac-top-button-right">
          <span>
            <font-awesome-icon icon="fa-magnifying-glass"/>
            随便看看
          </span>
          <div style="display: inline-block; float: right">
            <div class="circle" style="background-color: red; margin-left: 5px;">
            </div>
            <div class="circle" style="background-color: green; margin-left: 5px;">
            </div>
            <div class="circle" style="background-color: blue; margin-left: 5px;">
            </div>
          </div>
        </div>

        <!--    随便看看item    -->
        <div class="look-random-item"
             v-for="(item, index) in lookRandoms"
             :key="index"
        >
          <a
              @click="toArticleDetail(item)"
              style="height: 100%; cursor: pointer;"
          >
            <!--      封面图      -->
            <div class="look-random-image-wrap">
              <el-image
                  class="look-random-image"
                  :src="item.articleImageUrl"
                  :fit="`fill`"
                  lazy
              >
                <!--        错误时默认样式        -->
                <template #error>
                  <div class="image-slot">
                    <el-icon>
                      <font-awesome-icon icon="fa-solid fa-file-circle-xmark"/>
                    </el-icon>
                  </div>
                </template>
              </el-image>
            </div>
            <!-- 随便看看的标题 -->
            <div class="look-random-title">
              {{ item.title }}
            </div>

          </a>
        </div>
      </div>

      <!--   标签列表   -->
      <div class="tag-list content-glass" v-loading="onInit">
        <div class="mac-top-button-right" style="margin-bottom: 24px;">
          <span>
            <font-awesome-icon icon="fa-tag"/>
            标签列表
          </span>
          <div style="display: inline-block; float: right">
            <div class="circle" style="background-color: red; margin-left: 5px;">
            </div>
            <div class="circle" style="background-color: green; margin-left: 5px;">
            </div>
            <div class="circle" style="background-color: blue; margin-left: 5px;">
            </div>
          </div>
        </div>

        <!--  标签item  -->
        <div
            v-for="(item, index) in tags"
            :key="index"
        >
          <router-link
              to="#"
              class="tag-item"
              :style="{color: colorList[(index + randomSeed) % colorList.length]}"
          >

            {{ item.name }}

          </router-link>
        </div>
      </div>

      <!--   网站统计信息   -->
      <div class="web-info content-glass" v-loading="onInit">
        <div class="mac-top-button-right">
          <font-awesome-icon icon="fa-bar-chart"/>
          <span> &nbsp;&nbsp;网站统计信息</span>
          <div style="display: inline-block; float: right">
            <div class="circle" style="background-color: red; margin-left: 5px;">
            </div>
            <div class="circle" style="background-color: green; margin-left: 5px;">
            </div>
            <div class="circle" style="background-color: blue; margin-left: 5px;">
            </div>
          </div>
        </div>

        <div style="margin-top: 30px;line-height: 15px;">
          运行时间: <span style="float: right; font-size: 14px;">
                      {{ webRunTime }}
                   </span>
        </div>

        <div style="padding:4px 0 0; margin-top: 20px;">
          总访问量: <span style="float: right">{{ totalVisitCount }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import {onUnmounted, onMounted, ref} from "vue";
import {ElMessage} from "element-plus"
import HomeBanner from "@/views/banner/HomeBanner";
import axios from "@/utils/axios";
import JsCookie from "js-cookie";
import router from "@/router";

export default {
  name: "Home",
  components: {HomeBanner},
  setup() {

    // 网站统计信息
    const webStartTime = new Date('2022/7/14 00:00:00');
    const now = new Date();
    let dateDiff = Math.abs(now - webStartTime);
    const webRunTime = ref('');
    // 总访问数
    const totalVisitCount = ref(0);


    // 展示信息
    const lookRandoms = ref([]);
    const articleList = ref([]);
    let totalCount = ref(0);
    const tags = ref([]);

    // 页面状态控制
    const onInit = ref(true);
    const loading = ref(false);
    const disabledInfiniteLoading = ref(false);

    // 初次加载从后端请求, 这里包装一层,进行同步
    let onFirstLoading = async function () {
      loading.value = true;
      let articleListVersion = localStorage.getItem("articleListVersion") || "";
      let r = await axios.get("/api/index/get?start=0&count=10&version=" + articleListVersion);
      loading.value = false;
      onInit.value = false;
      return r;
    }
    let res = onFirstLoading();
    res.then(response => {
      if (response.data.articleListVersion === localStorage.getItem("articleListVersion")) {
        articleList.value = JSON.parse(localStorage.getItem("articleList"));
        totalCount.value = JSON.parse(localStorage.getItem("totalCount"));
        lookRandoms.value = JSON.parse(localStorage.getItem("lookRandoms"));
        tags.value = JSON.parse(localStorage.getItem("tags"));
        totalVisitCount.value = JSON.parse(localStorage.getItem("totalVisitCount"));
        return;
      }
      articleList.value = response.data.data;
      totalCount.value = response.data.count;
      lookRandoms.value = response.data.lookRandoms;
      tags.value = response.data.tags;
      totalVisitCount.value = response.data.totalVisitCount;

      // dev:
      if (process.env.NODE_ENV === "development") {
        console.log(articleList.value.length)
        console.log(articleList.value)
      }

      // 存储到本地
      localStorage.setItem("articleListVersion", response.data.articleListVersion);
      localStorage.setItem("articleList", JSON.stringify(articleList.value));
      localStorage.setItem("totalCount", JSON.stringify(totalCount.value));
      localStorage.setItem("lookRandoms", JSON.stringify(lookRandoms.value));
      localStorage.setItem("tags", JSON.stringify(tags.value));
      localStorage.setItem("totalVisitCount", JSON.stringify(totalVisitCount.value));
      if (!JsCookie.get("visited")) {
        JsCookie.set("visited", true, {expires: 0.5});
      }
    }).catch(error => {
      loading.value = false;
    })


    // 从后端请求
    const load = () => {
      // 正在加载文章中则不允许调用该方法,避免出错以及优化性能
      if (loading.value || onInit.value) {
        return;
      }
      // 如果没有更多了
      if (articleList.value.length >= totalCount.value) {
        ElMessage('没有更多了');
        disabledInfiniteLoading.value = true;
        return;
      }
      loading.value = true;

      //dev:
      if (process.env.NODE_ENV === "development") {
        console.log(articleList.value.length)
      }
      let url = "/api/article/get?start=" + articleList.value.length + "&count=5&total=" + totalCount.value;
      let promise = axios.get(url);
      promise
          .then(response => {
            articleList.value = articleList.value.concat(response.data.data);
            loading.value = false;
          }).catch(error => {
        loading.value = false;
        ElMessage.error("出错了,请一会重试");
      });
    }


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

    let timeInterval = null;


    // 将毫秒转成时间格式
    let msToFormatDate = function (ms) {
      if (ms) {
        let day = Math.floor(ms / (24 * 60 * 60 * 1000));
        let hour = Math.floor((ms / (60 * 60 * 1000) - day * 24));
        let min = Math.floor(ms / (60 * 1000) - day * 24 * 60 - hour * 60);
        let sec = Math.floor(ms / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        return `${day} 天 ${hour} 小时 ${min} 分 ${sec}秒`;
      } else {
        return ""
      }
    };

    onMounted(() => {
      timeInterval = setInterval(() => {
        dateDiff += 1000;
        webRunTime.value = msToFormatDate(dateDiff);
      }, 1000);
    });

    onUnmounted(() => {
      //清除定时器
      timeInterval != null ? clearInterval(timeInterval) : null;
    })


    return {
      articleList,
      webRunTime,
      lookRandoms,
      tags,
      loading,
      disabledInfiniteLoading,
      colorList,
      randomSeed,
      load,
      totalCount,
      onInit,
      totalVisitCount,
    };

  },
  watch: {
    articleList(newVal, oldVal) {
      newVal.forEach(item => {
        if (item.createDate != null)
          item.createDate = item.createDate.substring(0, 10);
      });
    }
  },
  methods: {
    toArticleDetail(article) {
      localStorage.setItem("articleCache" + article.articleId, JSON.stringify(article));
      router.push(`/article/${article.articleId}`);
    }
  }
}
</script>

<style scoped>

.main-content {
  width: auto;
  padding: 10px;
  min-height: 80vh;
}

/* 文章容器 */
.article-list {
  width: 960px;
  border-radius: inherit;
  padding: inherit;
  margin: 5px 10px;
  font-size: 16px;
}

/* 文章配图 */
.article-image {
  width: 40%;
  height: inherit;
  display: inline-block;
  overflow: hidden;
}

.image-float-right {
  float: right;
  border-radius: 0 16px 16px 0;
  margin-left: 24px;

}

.image-float-left {
  float: left;
  border-radius: 16px 0 0 16px;
  margin-right: 24px;
}

/* 图片加载失败时的样式 */
.image-slot {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: var(--el-fill-color-light);
  color: var(--el-text-color-secondary);
  font-size: 30px;
}

/* 文章信息容器 */
.article-info {
  margin: 50px;
}

/* 文章item */
.article-list-item-card {
  float: right;
  width: auto;
  min-width: 950px;
  height: 300px;
  margin-top: 10px;
  margin-bottom: 16px;
  border-radius: inherit;
  color: #a1a3a9;

  box-shadow: 0 0 10px 0;

}

/* 文章标题样式 */
.title {
  line-height: 1.8;
  font-size: 22px;
  margin: 10px;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.title a {
  color: #464646;
}

.article-category {
  color: inherit;
}

.article-tag {
  color: inherit;
  margin: 0 4px;
}

/* 通用内容区玻璃效果 */
.content-glass {
  background-color: rgba(255, 255, 255, 0.75);
  -webkit-backdrop-filter: saturate(150%) blur(8px);
  backdrop-filter: saturate(150%) blur(8px);
}

/* 文章内容 */
.article-content {
  margin-top: 1.2rem;
  color: #586464;
  line-height: 2;
  overflow: hidden;
  text-overflow: ellipsis;
  display: flex;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}

/* 加载新内容时显示的Loading文字样式 */
.loading-text {
  width: 100%;
  height: 3rem;
  line-height: 3rem;
  font-size: 30px;
  display: flex;
  justify-content: center;
  float: bottom;
}

.loading-text span {
  background: linear-gradient(90deg,
  rgb(255, 90, 12),
  rgb(255, 90, 12),
  rgb(67, 163, 166),
  rgb(52, 194, 177)
  );
  -webkit-background-clip: text;
  color: transparent;

}

/* 加载时旋转样式 */
.loading {
  animation: rotate 2s linear infinite;
}

@keyframes rotate {
  to {
    transform: rotate(360deg);
  }
}


/* 右侧浮动栏*/
.main-right-content {
  width: 300px;
  height: auto;
  position: sticky;
  position: -webkit-sticky;
  top: 50px;
  border-radius: inherit;
  margin: 15px 10px;
}

/* 随便看看 */
.look-random {
  width: auto;
  height: auto;
  min-width: 240px;
  min-height: 320px;
  margin-top: 10px;
  border-radius: 10px;
  padding: 24px;
}

/* 清除浮动对父元素的影响,避免高度塌陷 */
.look-random::after {
  content: '';
  display: block;
  clear: both;
}

.look-random-image-wrap {
  width: inherit;
  height: auto;
  border-radius: 5px;
}

.look-random-image {
  width: 110px;
  height: 75px;
  border-radius: 5px;
  display: inline-block;
  overflow: hidden;
}

.look-random span {
  height: 21px;
  line-height: 1rem;
  transition: all .2s ease-in-out;
  cursor: default;
}

.look-random-item {
  height: auto;
  width: 45%;
  float: left;
  margin: 10px 2% 20px 2%;
  text-overflow: ellipsis;
}

.look-random-title {
  font-size: 14px;
  height: 3em;
  overflow: hidden;
  text-overflow: ellipsis;
  display: flex;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}

.look-random-item a {
  color: darkblue;
}

/* 标签列表 */
.tag-list {
  width: auto;
  height: auto;
  min-height: 100px;
  border-radius: 10px;
  margin-top: 10px;
  padding: 24px;
}

/* 避免高度塌陷 */
.tag-list::after {
  content: '';
  display: block;
  clear: both;
}


.tag-item {
  height: 30px;
  width: 26%;
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
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;

}


/* 网站统计信息 */
.web-info {
  width: auto;
  height: auto;
  margin-top: 10px;
  border-radius: 10px;
  padding: 24px;
}

.mac-top-button-right {
  font-size: 1.3rem;
  margin-bottom: 24px;
}

/* mac按钮样式 */
/*.mac-top-button-right:after {*/
/*  content: '● ● ●';*/
/*  font-size: 1.2rem;*/
/*  float: right;*/

/*  background: linear-gradient(to right, red, red, green, green, blue, blue);*/
/*  -webkit-background-clip: text;*/
/*  color: transparent;*/
/*}*/

.circle {
  border-radius: 50%;
  height: 10px;
  width: 10px;
  display: inline-block;
}

</style>
