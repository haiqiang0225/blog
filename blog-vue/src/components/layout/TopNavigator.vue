<template>


  <transition-group
      name="animate__animated animate__bounce"
      enter-active-class="animate__fadeInDown"
      leave-active-class="animate__fadeOutUp"
  >
    <div v-show="showNav" class="nav" key="1" :class="{glass: scrollTop > 65}">
      <div class="nav-container">
        <div class="nav-left">
          <img src="" alt="">
          <span>Blog</span>
        </div>

        <div class="nav-right">
          <div class="menus-item">
            <router-link :to="{name: 'home'}">
              <font-awesome-icon icon="fa-home"/>
              首页
            </router-link>
            <span>|</span>
          </div>

          <div class="menus-item">
            <router-link :to="{
              name: 'category'
              }">
              <font-awesome-icon icon="fa-th-large"/>
              分类
            </router-link>
            <span>|</span>
          </div>

          <div class="menus-item">
            <router-link :to="{
              name: 'tag'
              }">
              <font-awesome-icon icon="fa-tags"/>
              标签
            </router-link>
          </div>


          <!--  搜索  -->
          <div class="search-container">
            <input type="text">
            <button>
              <font-awesome-icon icon="fa-search"/>
              搜索
            </button>
          </div>

          <div class="menus-item">
            <router-link :to="{name: 'messageBoard'}">
              <font-awesome-icon icon="fa-at"/>
              留言板
            </router-link>
            <span>|</span>
          </div>
          <div class="menus-item">
            <router-link :to="{name : 'aboutMe'}">
              <font-awesome-icon icon="fa-address-card"/>
              关于我
            </router-link>
            <span>|</span>
          </div>
          <div class="menus-item">
            <router-link to="/">
              <font-awesome-icon icon="fa-code-fork"/>
              个人项目
              <font-awesome-icon icon="fa-chevron-down"/>
            </router-link>
            <span>|</span>
          </div>
          <div class="menus-item">
            <router-link :to="{name: 'login'}">
              <font-awesome-icon icon="fa-user-circle"/>
              登陆/注册
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </transition-group>

</template>

<script>
import {onMounted, ref, reactive, computed} from "vue";

import categoryHelloImgURL from "@/assets/image/category-hello.jpg";
import tagHelloImgRUL from "@/assets/image/tag-hello.jpg"
import {useStore} from "vuex";


export default {
  name: "TopNavigator",
  setup() {
    // 控制是否显示导航栏
    let showNav = ref(true);
    // 获取滚动多少
    // 获取鼠标位置
    // const attrs = reactive({
    //   x: 0,
    //   y: 0,
    //   mousePosHandler(e) {
    //     attrs.x = e.clientX;
    //     attrs.y = e.clientY;
    //   }
    // })
    // vuex
    const store = useStore();
    // 获取全局的滚动条
    let globalScrollBar = store.state.globalScrollBar;

    return {
      showNav, globalScrollBar, categoryHelloImgURL, tagHelloImgRUL,
      // vuex
      scrollTop: computed(() => store.state.scrollTop),
    };
  },
  methods: {},
  computed: {},
  watch: {
    // // 监听滚动
    scrollTop(newVal, oldVal) {
      // 已经滑动了 100px 并且是向下滑动的情况下才隐藏导航栏
      this.showNav = !(newVal > 100 && newVal > oldVal);
    },

  },
  mounted() {

  }
}
</script>

<style scoped>

i {
  margin-right: 4px;
}

ul {
  list-style: none;
}

/*导航栏整体样式*/
.nav {
  width: 100%;
  height: 60px;
  position: fixed;
  min-width: 950px;
  z-index: 1;
}

.glass {
  background-color: rgba(0, 0, 0, .3);
  -webkit-backdrop-filter: saturate(150%) blur(8px);
  backdrop-filter: saturate(150%) blur(8px);
}


.nav-container {
  position: relative;
  font-size: 14px;
  width: 100%;
  height: 60px;
}

.nav-right {
  height: 100%;
  float: right;
  display: flex;
  justify-content: center;
}

.nav-right:last-child {
  margin-right: 24px;
}

.nav-left {
  height: 100%;
  float: left;
  display: flex;
  justify-content: center;
}


.nav-left:first-child {
  margin-left: 24px;
}

/* 导航栏按钮 */
.menus-item {
  display: flex;
  position: relative;
  top: 0;
  margin: 0 0 0 1rem;
  height: 1.35rem;
  line-height: 100%;
  align-self: center;
}

.menus-item a {
  position: relative;
  height: 100%;
  line-height: 100%;
  transition: all 0.2s;
  align-self: center;
  text-decoration-line: none;
  color: white;
}

.menus-item span {
  margin-left: 6px;
  color: white;
}

.menus-item a:hover {
  color: #80c8f8;
}

.menus-item a:hover:after {
  width: 100%;
}

.menus-item a:after {
  position: absolute;
  bottom: -5px;
  left: 0;
  z-index: -1;
  width: 0;
  height: 3px;
  background-color: #80c8f8;
  content: "";
  transition: all 0.3s ease-in-out;
}

/* 搜索框样式 */
.search-container {
  position: relative;
  align-self: center;
  margin: 0 0 5px 0.875rem;
  height: 35px;
}

.search-container input {
  float: left;
  box-sizing: border-box;
  color: white;
  width: 200px;
  height: 35px;
  border: 2px solid white;
  border-right: none;
  border-radius: 5px 0 0 5px;
  background: transparent;
  top: 0;
  right: 0;
}

.search-container button {
  float: left;
  box-sizing: border-box;
  background: white;
  height: 35px;
  border-radius: 0 5px 5px 0;
  cursor: pointer;
  top: 0;
  right: 0;
  border-style: none;

}


.search-container input:focus {
  outline: 1px solid #bfa;
}

.search-container input:focus ~ button {
  outline: 1px solid #bfa;
}


</style>
