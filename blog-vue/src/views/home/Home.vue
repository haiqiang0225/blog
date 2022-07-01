<template>
  <!-- 封面 -->
  <HomeBanner></HomeBanner>
  <!-- 文章内容 -->
  <div class="main-content">
    <!--  无限加载  -->
    <ul v-infinite-scroll="load" class="article-list">
      <!--   文章卡片   -->
      <li class="article-list-item-card content-glass"
          v-for="article in articleList"
          :key="article.id">

        <!-- 文章配图 -->
        <router-link
            to="#"
            style="height: 100%"
        >
          <el-image
              class="article-image"
              :class="article.id % 2 === 0? `image-float-left`: `image-float-right` "
              :src="article.id  < 2 ? `https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg` : ``"
              :fit="`fill`"
              lazy
          >
            <template #error>
              <div class="image-slot">
                <el-icon>
                  <font-awesome-icon icon="fa-solid fa-file-circle-xmark"/>
                </el-icon>
              </div>
            </template>
          </el-image>
        </router-link>


        <!--    文章信息    -->
        <div class="article-info">
          <!--  文章标题    -->
          <div class="title">
            <router-link to="#">
              {{ article.title }}
            </router-link>
          </div>
          <!--   是否置顶   -->
          <span v-if="article.isTop" style="color: #ff7242">
            <font-awesome-icon icon="fa-arrow-up"/>
            置顶
           <span class="separator">|</span>
          </span>

          <!--    发表时间    -->
          <font-awesome-icon icon="fa-calendar-day"/>
          {{ article.createTime }}
          <span class="separator">|</span>

          <!--     文章分类     -->
          <router-link to="#" class="article-category">
            <font-awesome-icon icon="fa-th-large"/>
            {{ `分类` }}
          </router-link>
          <span class="separator">|</span>

          <!--     文章标签     -->
          <router-link to="#" class="article-tag"
                       v-for="tag in article.tags"
          >
            <font-awesome-icon icon="fa-tag"/>
            {{ `标签` }}
          </router-link>

          <!--    文章内容    -->
          <div class="article-content">
            {{ article.content }}
          </div>

        </div>

      </li>
    </ul>

    <!--  主页右侧内容  -->
    <div class="main-right-content">

      <!--  随便看看    -->
      <div class="look-random content-glass">
        <div class="mac-top-button-right">
          <font-awesome-icon icon="fa-magnifying-glass"/>
          随便看看
        </div>

        <div class="look-random-item"
             v-for="item in lookRandoms"
        >

          {{ item.title }}

        </div>
      </div>

      <!--   网站统计信息   -->
      <div class="web-info content-glass">
        <div class="mac-top-button-right">
          <font-awesome-icon icon="fa-bar-chart"/>
          <span> &nbsp;&nbsp;网站统计信息</span>
        </div>

        <div style="padding:4px 0 0; margin-top: 30px;">
          运行时间: <span style="float: right">{{ webRunTime }}</span>
        </div>

        <div style="padding:4px 0 0; margin-top: 20px;">
          总访问量: <span style="float: right">{{ webTotalViewCount }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import {ref, reactive} from "vue";
import HomeBanner from "@/views/banner/HomeBanner";

export default {
  name: "Home",
  components: {HomeBanner},
  setup() {
    const webRunTime = '0 天 0 时 0 分 0 秒';
    const webTotalViewCount = 0;
    const lookRandoms = [{title: '1'}];
    let articleSample = {
      id: 0,
      isTop: true,
      title: '标题',
      createTime: new Date().toLocaleDateString(),
      tags: ['123', '456'],
      content: "这少年便是闰土。我认识他时，也不过十多岁，离现在将有三十年了；那时我的父亲还在世，家景也好，我正是一个少爷。那一年，我家是一件大祭祀的值年。这祭祀，说是三十多年才能轮到一回，所以很郑重。正（zhēng）月里供像，供品很多，祭器很讲究，拜的人也很多，祭器也很要防偷去。我家只有一个忙月（我们这里给人做工的分三种：整年给一定人家做工的叫长工；按日给人做工的叫短工；自己也种地，只在过年过节以及收租时候来给一定的人家做工的称忙月），忙不过来，他便对父亲说，可以叫他的儿子闰土来管祭器的。",
    };
    const articleList = ref([articleSample]);
    const load = () => {
      if (articleList.value.length >= 50) {
        alert("没有了别划了");
        return;
      }
      articleList.value[articleList.value.length] = {
        id: articleList.value.length,
        isTop: false,
        title: '标题' + articleList.value.length,
        createTime: new Date().toLocaleDateString(),
        tags: ['123', '456'],
        content: "这少年便是闰土。我认识他时，也不过十多岁，离现在将有三十年了；那时我的父亲还在世，家景也好，我正是一个少爷。那一年，我家是一件大祭祀的值年。这祭祀，说是三十多年才能轮到一回，所以很郑重。正（zhēng）月里供像，供品很多，祭器很讲究，拜的人也很多，祭器也很要防偷去。我家只有一个忙月（我们这里给人做工的分三种：整年给一定人家做工的叫长工；按日给人做工的叫短工；自己也种地，只在过年过节以及收租时候来给一定的人家做工的称忙月），忙不过来，他便对父亲说，可以叫他的儿子闰土来管祭器的。",
      };
    }

    return {articleList, webRunTime, webTotalViewCount, lookRandoms, load};

  }
}
</script>

<style scoped>

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

.image-slot .el-icon {
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
  height: 300px;
  margin-top: 10px;
  margin-bottom: 16px;
  border-radius: inherit;
  color: #a1a3a9;

}

/* 文章标题样式 */
.title {
  line-height: 1.8;
  font-size: 24px;
  margin: 10px;
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
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}


/* 右侧浮动栏*/
.main-right-content {
  width: 300px;
  height: 200px;
  border-radius: inherit;
  margin: 15px 10px;
}

/* 随便看看 */
.look-random {
  width: auto;
  height: auto;
  margin-top: 10px;
  border-radius: 10px;
  padding: 24px;

}

.look-random-item {
  height: auto;
  width: auto;
}

.look-random-item:before {
  content: '';
  display: block;
  margin-top: 24px;
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
}

/* mac按钮样式 */
.mac-top-button-right:after {
  content: '● ● ●';
  font-size: 1.2rem;
  float: right;

  background: linear-gradient(to right, red, red, green, green, blue, blue);
  -webkit-background-clip: text;
  color: transparent;
}


</style>
