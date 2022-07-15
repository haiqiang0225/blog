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
          <span>发表于: {{ article.createDate }}</span>
        </div>
        <span class="separator">|</span>
        <!--   更新时间   -->
        <div class="time">
          <font-awesome-icon icon="fa-solid fa-clock-rotate-left"/>
          <span>更新于: {{ article.updateDate }}</span>
        </div>
        <span class="separator">|</span>
        <!--   分类   -->
        <div class="category"
             v-for="(tag,index) in article.tags"
             :key="index">
          <font-awesome-icon icon="fa-th-large"/>
          {{ tag.name }}
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
            <span>阅读量: {{ article.viewCount }}</span>
          </div>
          <span class="separator">|</span>

          <!--   评论数   -->
          <div class="comment-count">
            <span>评论数: {{ article.commentCount }}</span>
          </div>
        </div>
      </div>


    </template>
  </CommonBanner>
  <!-- 文章详情 -->
  <div class="main-content" v-loading="onInit">
    <div class="article-container">
      <!--   内容   -->
      <article class="markdown-body">
        <a href="javascript:" @click="anchor(`bottom`)">aaa</a>
        <p v-html="html"></p>
        <div id="bottom"></div>
      </article>
    </div>
  </div>

  <!-- todo:评论区树形展示 -->
  <!-- 评论区 -->
  <div class="comment-container">
    <div class="comment-wrap">
      <!--   输入框   -->
      <div class="comment-input">

      </div>

      <!--   评论列表   -->
      <div class="comment-list">
        <ul>
          <li
              v-for="(comment, index) in comments"
              :key="index"
          >
            {{ comment }}
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import {ref, reactive, defineComponent} from "vue"
import {useRoute} from "vue-router";
import Editor from "@tinymce/tinymce-vue"
import CommonBanner from "@/views/banner/CommonBanner";
import axios from "@/utils/axios";
import {marked} from "marked"
import {useStore} from "vuex";
import highlightJs from "highlight.js";
import "github-markdown-css";
import 'highlight.js/styles/atom-one-dark.css'
import {ElMessage} from "element-plus";

export default {
  name: "Article",
  components: {Editor, CommonBanner},
  setup(props, context) {
    // 设置 marked
    const tocObj = {
      add: function (text, level) {
        let anchor = `#toc${level}${++this.index}`;
        this.toc.push({anchor: anchor, level: level, text: text});
        return anchor;
      },
      // 使用堆栈的方式处理嵌套的ul,li，level即ul的嵌套层次，1是最外层
      // <ul>
      //   <li></li>
      //   <ul>
      //     <li></li>
      //   </ul>
      //   <li></li>
      // </ul>
      toHTML: function () {
        let levelStack = [];
        let result = '';
        const addStartUL = () => {
          result += '<ul>';
        };
        const addEndUL = () => {
          result += '</ul>\n';
        };
        const addLI = (anchor, text) => {
          result += '<li><a href="javascript:" onclick="anchor(`' + anchor + '`)">' + text + '<a></li>\n';
        };

        this.toc.forEach(function (item) {
          let levelIndex = levelStack.indexOf(item.level);
          // 没有找到相应level的ul标签，则将li放入新增的ul中
          if (levelIndex === -1) {
            levelStack.unshift(item.level);
            addStartUL();
            addLI(item.anchor, item.text);
          } // 找到了相应level的ul标签，并且在栈顶的位置则直接将li放在此ul下
          else if (levelIndex === 0) {
            addLI(item.anchor, item.text);
          } // 找到了相应level的ul标签，但是不在栈顶位置，需要将之前的所有level出栈并且打上闭合标签，最后新增li
          else {
            while (levelIndex--) {
              levelStack.shift();
              addEndUL();
            }
            addLI(item.anchor, item.text);
          }
        });
        // 如果栈中还有level，全部出栈打上闭合标签
        while (levelStack.length) {
          levelStack.shift();
          addEndUL();
        }
        // 清理先前数据供下次使用
        this.toc = [];
        this.index = 0;
        return result;
      },
      toc: [],
      index: 0
    };
    let renderer = new marked.Renderer();
    renderer.heading = function (text, level, raw) {
      let anchor = tocObj.add(text, level);
      return `<a id=${anchor} class="anchor-fix"></a><h${level}>${text}</h${level}>\n`;
    };
    marked.setOptions({
      renderer: renderer,
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

    // 控制相关
    const onInit = ref(true);

    // 显示相关
    const articleDetail = ref({});
    const article = ref({});
    const comments = ref({});


    let load = async function () {
      article.value = await JSON.parse(localStorage.getItem("articleCache"));
      // 本地缓存中不存在
      let url = "/api/article/getDetails?articleId=" + article_id;
      if (!article.value || article.value.articleId !== article_id) {
        url = url + "&queryArticle=true";
      }
      let promise = await axios.get(url);
      onInit.value = false;
      return promise;
    }

    let calWordCount = function (data) {
      let pattern = /[a-zA-Z0-9_\u0392-\u03c9]+|[\u4E00-\u9FFF\u3400-\u4dbf\uf900-\ufaff\u3040-\u309f\uac00-\ud7af]+/g;
      let m = data.match(pattern);
      let count = 0;
      if (m == null) {
        return count;
      }
      for (let i = 0; i < m.length; i++) {
        if (m[i].charCodeAt(0) >= 0x4E00) {
          count += m[i].length;
        } else {
          count += 1;
        }
      }
      return count;
    }

    // 页面显示
    const timeUsed = ref("");
    let wordCount = ref(0);
    let html = ref('');
    let promise = load();
    promise.then(response => {
      // 本地缓存中不存在
      if (!article.value || article.value.articleId !== article_id) {
        article.value = response.data.article;
      }
      articleDetail.value = response.data.articleDetail;
      comments.value = response.data.comments;
      console.log(article.value);
      console.log(articleDetail.value);

      // 进行解析
      if ("md" === articleDetail.value.contentType) {
        html.value = marked(articleDetail.value.content);
        html.value = tocObj.toHTML() + html.value;
      } else {
        html.value = articleDetail.value.content;
      }

      wordCount.value = calWordCount(html.value);
      timeUsed.value = `${Math.ceil(wordCount.value / 300)} 分钟`;
    }).catch(error => {
      ElMessage.error("出错了,请刷新试试");
    });


    return {backgroundImg, article, wordCount, timeUsed, html, onInit, comments};
  },
  methods: {
    anchor(id) {
      let globalScrollBar = this.$store.state.globalScrollBar;
      let elementById = document.getElementById(id);
      globalScrollBar.scrollTo(0, elementById.offsetTop + document.documentElement.clientHeight * .36);
    }
  },
  mounted() {
    window.anchor = this.anchor;
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

/* markdown 样式*/
.markdown-body {
  box-sizing: border-box;
  min-width: 200px;
  max-width: 980px;
  margin: 0 auto;
  padding: 45px;
  border-radius: inherit;
  height: 100%;
}

/* 评论区样式 */
.comment-container {
  position: relative;
  top: 46px;
  margin-bottom: 20px;
}

.comment-wrap {
  box-sizing: border-box;
  width: 960px;
  position: relative;
  left: 0;
  right: 0;
  margin: 0 auto;
  max-width: 1300px;
  min-width: 960px;
  background-color: white;
  border-radius: 16px;
  padding: 45px;
}

.comment-input {

}

.comment-list {
}

</style>

