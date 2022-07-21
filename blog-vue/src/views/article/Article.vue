<template>

  <!--  封面 -->
  <CommonBanner :background-image-url="backgroundImg">
    <!--  文章信息  -->
    <template v-slot:info>

      <!--  标题    -->
      <div class="article-title">
        {{ article.title }}
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
            <span>评论数: {{ comments != null ? comments.length : 0 }}</span>
          </div>
        </div>
      </div>


    </template>
  </CommonBanner>
  <!-- 文章详情 -->
  <div class="main-content-container">
    <div class="main-content" v-loading="onInit">
      <div class="article-container">
        <!--   内容   -->
        <article class="markdown-body">
          <!--          <p v-html="toc"></p>-->
          <p v-html="html"></p>
        </article>
      </div>

    </div>
    <!-- 右侧目录导航栏 -->
    <div class="right-nav-container" v-loading="onInit">
      <article class="markdown-body">
        <span> 目录 </span>
        <p v-html="toc"></p>
      </article>
    </div>
  </div>

  <!-- 评论区 -->
  <div class="comment-container"
       ref="commentContainer"
       id="comment-container"
  >
    <form class="comment-wrap">
      <!--   输入框   -->
      <span style="font-size: 24px"> 评论 </span>
      <div class="comment-input">
        <el-input
            ref="commentInputRef"
            v-model="userComment"
            :placeholder="placeHolder"
            type="textarea"
            :autosize="{minRows: 4, maxRows: 10}"
            style="font-size: 16px"
        >
        </el-input>

        <div class="function_buttons">
          <el-button @click="uploadComment(userComment)">评论</el-button>
          <el-button
              circle
              style="color: rgb(225,167,49)"
              @click="openEmoji"
          >
            <font-awesome-icon icon="fa-solid fa-face-grin"/>
          </el-button>
        </div>
      </div>

      <!--   评论列表   -->
      <div class="comment-list" id="comment-list">
        <!-- 父级评论  -->
        <div
            v-for="(comment, index) in rootComments"
            class="root-comment-item"
            :key="index"
        >
          <div class="comment-info-container">

            <el-avatar :size="50"
                       :src="comment.user.userProfilePhotoPath ||
                             `https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png`"
            >
            </el-avatar>

            <!--    用户昵称      -->
            <span> {{
                comment.user.userNickname
              }}</span>
            <!--    评论时间      -->
            <span style="color: #a1a3a9"> {{ comment.createDate }} </span>

            <!-- 点赞按钮 -->
            <div style="margin-left: auto">
              <el-button class="like-button">
                <font-awesome-icon icon="fa-solid fa-thumbs-up"/>
                &nbsp;
                {{ comment.likeCount !== 0 ? comment.likeCount : '' }}
              </el-button>
            </div>
          </div>

          <!--     评论内容     -->
          <div class="comment-content" style="">
            {{ comment.content }}
          </div>

          <!-- 回复按钮  -->
          <div class="comment-function-container">
            <el-button
                class="reply-button"
                @click="doReply(comment.user.userId, comment.commentId, comment.commentId)"
            >
              <font-awesome-icon icon="fa-solid fa-message"/>
              &nbsp;
              <span style="font-size: 1rem">
                  {{ replyComments[comment.commentId] ? replyComments[comment.commentId].length : "" }}
              </span>
            </el-button>
          </div>

          <!--   子评论/回复评论   -->
          <div class="reply-list">

            <div class="reply-comment-item"
                 v-for="(reply, index) in replyComments[comment.commentId]"
                 :key="index"
            >
              <!--  评论信息  -->
              <div class="comment-info-container">

                <el-avatar :size="50"
                           :src="reply.user.userProfilePhotoPath ||
                             `https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png`"
                >
                </el-avatar>

                <!--    用户昵称      -->
                <span> {{
                    reply.user.userNickname
                  }}</span>
                <!--    评论时间      -->
                <span style="color: #a1a3a9"> {{ reply.createDate }} </span>

                <span> 回复:  {{ commentIdUserNameMap[reply.parentId] }}</span>
                <!-- 点赞按钮 -->
                <div style="margin-left: auto">
                  <el-button class="like-button">
                    <font-awesome-icon icon="fa-solid fa-thumbs-up"/>
                    &nbsp;
                    {{ comment.likeCount !== 0 ? comment.likeCount : '' }}
                  </el-button>
                </div>
              </div>

              <!--     评论内容     -->
              <div class="comment-content" style="">
                {{ reply.content }}
              </div>
              <!-- 回复按钮  -->
              <div class="comment-function-container">
                <el-button class="reply-button"
                           style="color:#c5c6cb;"
                           @click="doReply(reply.user.userId, comment.commentId, reply.commentId)"
                >
                  <font-awesome-icon icon="fa-solid fa-message"/>
                </el-button>
              </div>
              <!--              {{ reply }}-->
            </div>
          </div>
        </div>
      </div>
    </form>
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

    // 一些全局的设置
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


    // ===== 页面逻辑相关变量 =====
    // 控制相关
    const onInit = ref(true);

    // 显示相关
    const articleDetail = ref({});
    const article = ref({});
    let comments = ref();
    const placeHolder = ref("请输入评论...");
    // 存放所有顶级/根评论
    const rootComments = ref({});
    // key : parentId,  value: 子评论的列表, 按照顺序排列
    const replyComments = ref({});
    const toc = ref('');
    // 页面显示
    const timeUsed = ref("");
    let wordCount = ref(0);
    let html = ref('');

    // 评论相关
    const userIdNameMap = {};
    const commentIdUserNameMap = {};
    // 回复
    const replyUserId = ref();
    const replyRootCommentId = ref();
    const replyParentId = ref();


    // 用户评论
    const userComment = ref('');


    // ===== 具体逻辑 =====
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

    // 计算字数
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


    // 向后端请求
    let promise = load();
    promise.then(response => {
      // 本地缓存中不存在
      if (!article.value || article.value.articleId !== article_id) {
        article.value = response.data.article;
      }
      articleDetail.value = response.data.articleDetail;
      comments.value = response.data.comments;

      // 用于评论区的变量
      const parentIdMap = {};

      // 先找出所有的根评论
      comments.value.forEach(v => {
        if (v.parentId === null || v.parentId === -1 || v.parentId === '') {
          rootComments.value[v.commentId] = v;
          replyComments.value[v.commentId] = [];
        } else {
          parentIdMap[v.commentId] = v.parentId;
        }
        userIdNameMap[v.user.userId] = v.user.userNickname;
        commentIdUserNameMap[v.commentId] = v.user.userNickname;
      });

      // 解析评论comments, 将所有回复根据parentId 串成链表
      comments.value.forEach(v => {
        // 如果不再根级的列表里则添加
        if (v.parentId === null || v.parentId === -1 || v.parentId === '') {
        } else {
          let parentId = parentIdMap[v.commentId];
          let step = comments.value.length;
          while (parentIdMap[parentId] !== undefined) {
            parentId = parentIdMap[parentId];
            // 最多尝试次数,超过这个次数,则不存在
            if (--step < 0) {
              break;
            }
          }
          // step < 0
          if (step < 0) {
            return;
          }
          if (replyComments.value[parentId]) {
            replyComments.value[parentId].push(v);
          } else {
          }
        }
      });

      // 按照日期排序
      for (let k in replyComments.value) {
        replyComments.value[k].sort((a, b) => {
          return (new Date(b.createDate) - new Date(a.createDate));
        });
      }

      // 进行解析
      if ("md" === articleDetail.value.contentType) {
        html.value = marked(articleDetail.value.content);
        // html.value = tocObj.toHTML() + html.value;
        toc.value = tocObj.toHTML();
      } else {
        html.value = articleDetail.value.content;
      }

      wordCount.value = calWordCount(html.value);
      timeUsed.value = `${Math.ceil(wordCount.value / 300)} 分钟`;
    }).catch(error => {
      ElMessage.error("出错了,请刷新试试");
      console.log(error)
    });

    return {
      backgroundImg,
      article,
      wordCount,
      timeUsed,
      html,
      onInit,
      comments,
      toc,
      userComment,
      rootComments,
      replyComments,
      idNameMap: userIdNameMap,
      replyUserId,
      replyRootCommentId,
      globalScrollBar,
      commentIdUserNameMap,
      placeHolder,
      replyParentId,
    };
  },
  methods: {
    anchor(id) {
      let elementById = document.getElementById(id);
      this.globalScrollBar.scrollTo(0, elementById.offsetTop + document.documentElement.clientHeight * .35);
    },
    uploadComment(comment) {
      if (comment.replace(/(^\s*)|(\s*$)/g, "").length === 0) {
        ElMessage("没有有效输入");
        return;
      }
      if (this.onInit) {
        ElMessage("请稍等");
      }
      let url = "/api/comment/upload";
      let data = new FormData();
      let curUser = JSON.parse(localStorage.getItem("curUser"));
      // 设置userId (如果有)
      if (curUser) {
        let userId = curUser.userId;
        if (userId !== undefined) {
          data.set("userId", userId);
        }
      }
      data.set("content", this.userComment);
      data.set("articleId", this.article.articleId);
      if (this.replyParentId !== undefined) {
        data.set("parentId", this.replyParentId);
      } else {
        data.set("parentId", -1);
      }
      axios.post(url, data)
          .then(res => {
            let comment = res.data.comment;
            comment.user = curUser;
            // 如果是根评论,放到根评论的列表里
            if (this.replyRootCommentId === undefined || this.replyRootCommentId === null) {
              this.rootComments[comment.commentId] = comment;
            } else {
              this.replyComments[this.replyRootCommentId].unshift(comment);
            }

            // 重置
            this.userComment = "";
            this.replyUserId = undefined;
            this.replyRootCommentId = undefined;
            ElMessage.success("评论成功!");

          })
          .catch(err => {
            if (err.response && err.response.status === 403) {
              ElMessage.error("请登录后再评论!");
            } else {
              ElMessage.error("出错了!刷新试试");
            }
          })
    },
    //todo: 打开emoji面板
    openEmoji() {
      ElMessage("暂不支持Emoji表情😂");
    },
    doReply(userId, rootCommentId, parentId) {
      this.replyRootCommentId = rootCommentId;
      this.replyUserId = userId;
      this.replyParentId = parentId;
      this.globalScrollBar.scrollTo(0, this.$refs.commentContainer.offsetTop);
      this.placeHolder = "回复" + this.idNameMap[userId];
    },
    // 判断用户是否已经登录
    isAlreadyLogin() {
      return localStorage.getItem("token") !== null;
    }
  },
  mounted() {
    window.anchor = this.anchor;
  }
}
</script>

<style scoped>


/* 内容区 */

.main-content-container {
  position: relative;
  width: 100vw;
}

.main-content {
  display: inline-block;
  position: relative;
  background-color: white;
  /* 屏幕宽度 100vw - 960 / 2 即居中*/
  left: calc((100vw - 960px) / 2);

}


.article-container {
  width: 960px;
  max-width: 960px;
  min-width: 960px;
  height: auto;
  border-radius: inherit;
}

/* 标题 */
.article-title {
  text-align: center;
  color: white;
  font-size: 2.5rem;
  overflow: hidden;
  text-overflow: ellipsis;
  display: flex;
  display: -webkit-box;
  -webkit-line-clamp: 4;
  -webkit-box-orient: vertical;
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
  margin-top: 16px;
}

.comment-content {
  display: block;
  min-height: 2.4rem;
  line-height: 1.2rem;
  margin-top: 10px;
  margin-left: 50px;
}

.comment-function-container {
  position: relative;
  margin-left: 50px;
}

.reply-button {
  border: none;
  font-size: 1.3rem;
  color: #a1a3a9
}

/* 输入栏下方按钮样式 */
.function_buttons {
  display: block;
  margin-top: 16px;
}

.function_buttons > button {
  float: right;
  margin: 0 5px;
}

.comment-list {
  margin-top: 66px;
}

.comment-info-container {
  position: relative;
  height: 50px;
  min-height: 50px;
  max-height: 50px;
  display: flex;
  align-items: center
}

.comment-info-container span {
  margin-right: 5px;
}

.like-button {

}

/*右侧导航栏 */
.right-nav-container {
  min-width: 400px;
  min-height: 500px;
  border-radius: 16px;
  background-color: white;
  position: sticky;
  position: -webkit-sticky;
  top: 70px;
  right: 10vw;
  float: right;
  display: inline-block;
  margin-top: 24px;
}

.root-comment-item {

}

.reply-list {
  position: relative;
  margin-left: 25px;
  padding: 10px;
  /*border: 1px #a1a3a9 solid;*/
  /*border-radius: 16px;*/
}

.reply-comment-item {
  margin: 5px 0;
}


</style>

