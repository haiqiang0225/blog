<template>

  <!--  封面 -->
  <CommonBanner :background-image-url="backgroundImg">
    <!--  文章信息  -->
    <template v-slot:info>

      <!--  标题    -->
      <div class="article-title">
        <span>{{ article.title }}</span>
      </div>

      <div class="article-info-wrap">
        <!--    发表时间    -->
        <div class="time">
          <font-awesome-icon icon="fa-calendar-day"/>
          <span>发表于: {{ article.createTime }}</span>
        </div>
        <span class="separator">|</span>
        <!--   更新时间   -->
        <div class="time">
          <font-awesome-icon icon="fa-solid fa-clock-rotate-left"/>
          <span>更新于: {{ article.createTime }}</span>
        </div>
        <span class="separator">|</span>
        <!--   分类   -->
        <div class="category">
          <font-awesome-icon icon="fa-th-large"/>
          {{ article.category }}
        </div>

        <!--   统计信息     -->
        <div class="summary-info-wrap">
          <!--   字数统计   -->
          <div class="word-count">
            <span>字数统计: {{ wordCount }}</span>
          </div>
          <span class="separator">|</span>

          <!--   看完预计需要时间   -->
          <div class="time">
            <font-awesome-icon icon="fa-solid fa-clock"/>
            <span>阅读时长: {{ timeUsed }}</span>
          </div>
          <span class="separator">|</span>

          <!--   阅读量   -->
          <div class="read-count">
            <span>阅读量: {{ article.readCount }}</span>
          </div>
          <span class="separator">|</span>

          <!--   评论数   -->
          <div class="comment-count">
            <span>评论数: {{ article.comment.length }}</span>
          </div>
        </div>
      </div>


    </template>
  </CommonBanner>
  <div class="main-content">
    <div class="article-container">
      <!--   内容   -->
      <article class="markdown-body">
        <h1>h1标签</h1>
        <h1>h1标签</h1>
        <h1>h1标签</h1>
        <h1>h1标签</h1>
        <h1>h1标签</h1>
        <h1>h1标签</h1>
        <h1>h1标签</h1>
        <h1>h1标签</h1>
        <h1>h1标签</h1>
        <h1>h1标签</h1>

        <p v-html="html"></p>

      </article>
    </div>
  </div>
</template>

<script>
import {ref, reactive, defineComponent} from "vue"
import {useRoute} from "vue-router";
import Editor from "@tinymce/tinymce-vue"
import CommonBanner from "@/views/banner/CommonBanner";
import axios from "axios";
import {marked} from "marked"
import {useStore} from "vuex";
import highlightJs from "highlight.js";
import "github-markdown-css";
import 'highlight.js/styles/atom-one-dark.css'

export default {
  name: "Article",
  components: {Editor, CommonBanner},
  setup(props, context) {
    // 设置 marked
    marked.setOptions({
      renderer: new marked.Renderer(),
      highlight(code, lang, callback) {
        return highlightJs.highlightAuto(code).value;
      },
      pedantic: false,
      gfm: true,
      tables: true,
      breaks: false,
      sanitize: false,
      smartLists: true,
      smartypants: false,
      xhtml: false,
    });
    const route = useRoute();
    // vuex
    const store = useStore();
    // 获取全局的滚动条
    const globalScrollBar = store.state.globalScrollBar;
    const backgroundImg = require("@/assets/image/index-main.jpg");
    const article_id = route.params.id;

    // 确保切换过来后,滚动条的位置初始化
    globalScrollBar.scrollTo(0, 0);

    //todo: 根据id 向后端请求文章信息
    const article = {
      id: 0,
      isTop: true,
      title: '标题',
      backgroundImageURL: "https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg",
      createTime: new Date().toLocaleDateString(),
      category: 'Test',
      readCount: 0,
      tags: ['123', '456'],
      content: "这少年便是闰土。我认识他时，" +
          "也不过十多岁，离现在将有三十年了；" +
          "那时我的父亲还在世，家景也好，我正是一个少" +
          "爷。那一年，我家是一件大祭祀的值年。这祭祀，说是" +
          "三十多年才能轮到一回，所以很郑重。正（zhēng）月里供像，" +
          "供品很多，祭器很讲究，拜的人也很多，祭器也很要防偷去。我家" +
          "只有一个忙月（我们这里给人做工的分三种：整年给一定人家做工" +
          "的叫长工；按日给人做工的叫短工；自己也种地，只在过年过节以" +
          "及收租时候来给一定的人家做工的称忙月），忙不过来，他便对父" +
          "亲说，可以叫他的儿子闰土来管祭器的。",
      comment: [{}, {}]
    };

    let md = "#   Vue2笔记\n" +
        "\n" +
        "## 脚手架文件结构\n" +
        "\n" +
        "\t├── node_modules \n" +
        "\t├── public\n" +
        "\t│   ├── favicon.ico: 页签图标\n" +
        "\t│   └── index.html: 主页面\n" +
        "\t├── src\n" +
        "\t│   ├── assets: 存放静态资源\n" +
        "\t│   │   └── logo.png\n" +
        "\t│   │── component: 存放组件\n" +
        "\t│   │   └── HelloWorld.vue\n" +
        "\t│   │── App.vue: 汇总所有组件\n" +
        "\t│   │── main.js: 入口文件\n" +
        "\t├── .gitignore: git版本管制忽略的配置\n" +
        "\t├── babel.config.js: babel的配置文件\n" +
        "\t├── package.json: 应用包配置文件 \n" +
        "\t├── README.md: 应用描述文件\n" +
        "\t├── package-lock.json：包版本控制文件\n";

    md += "```java\n" +
        "public class Lamborghini implements Car{\n" +
        "    @Override\n" +
        "    public void name() {\n" +
        "        System.out.println(\"兰博基尼\");\n" +
        "    }\n" +
        "}\n" +
        "public class Porsche implements Car{\n" +
        "    @Override\n" +
        "    public void name() {\n" +
        "        System.out.println(\"保时捷\");\n" +
        "    }\n" +
        "}\n" +
        "```";

    const wordCount = 0;
    const timeUsed = '0分钟';
    const html = marked(md);
    return {backgroundImg, article, wordCount, timeUsed, html};
  },
  methods: {
    getArticle() {
      let params = new FormData();
      axios.get('http://localhost:8080/proxy/test/get').then(
          response => {
            console.log(response.data);
          },
          error => {
            console.log(error.message);
          }
      );
    }
  }
}
</script>

<style scoped>

/* 内容区 */
.main-content {
  background-color: white;
}

.article-container {
  width: 960px;
  height: auto;
  border-radius: inherit;
}

/* 标题 */
.article-title {
  text-align: center;
  color: white;
  font-size: 2.5rem;
}

.article-info-wrap {
  text-align: center;
  position: relative;
  top: 20px;
  color: #c5c6cb;
}

/* 时间 */
.time {
  display: inline-block;
  margin: 0 7px;
}

/* 文章分类 */
.category {
  display: inline-block;
  margin: 0 7px;
}

/* 统计信息 */
.summary-info-wrap {
  text-align: center;
  position: relative;
  top: 5px;
  color: #c5c6cb;
}

.word-count {
  display: inline-block;
  margin: 0 7px;
}

.read-count {
  display: inline-block;
}

.comment-count {
  display: inline-block;
}

.markdown-body {
  box-sizing: border-box;
  min-width: 200px;
  max-width: 980px;
  margin: 0 auto;
  padding: 45px;
  border-radius: inherit;
  height: 100%;
}

</style>

