<template>
  <CommonBanner :background-image-url="backgroundImg"></CommonBanner>
  <div class="main-content">
    <div class="about-me-container content-glass">
      <h1>个人简介</h1>
      <div class="avatar-wrap">
        <el-avatar class="avatar" :size="200" :src="avatarIMG"></el-avatar>
      </div>
      <ul class="info-item-list">

        <!--   描述项,可以用html     -->
        <li
            class="info-item"
            v-for="item in userInfo"
        >
          <p v-html="item"></p>
        </li>
      </ul>
      <div class="ref-items">

        <!--   github -->
        <el-button
            circle
            class="ref-item"
            type="info"
            @click="openUrl(`https://github.com/haiqiang0225`)"
        >
          <font-awesome-icon icon="fa-brands fa-github"/>
        </el-button>

        <!--    weixin    -->
        <el-button
            circle
            class="ref-item wechat-button"
            type="success"
        >
          <font-awesome-icon icon="fa-brands fa-weixin"/>
        </el-button>

        <!--    qq    -->
        <el-button
            circle
            class="ref-item"
            type="primary"
            @click="openUrl(`http://wpa.qq.com/msgrd?v=3&uin=540911185&site=qq&menu=yes`)"
        >
          <font-awesome-icon icon="fa-brands fa-qq"/>
        </el-button>

        <div class="show-wechat">
          <el-image
              :src="`https://haiqiang-picture.oss-cn-beijing.aliyuncs.com/blog/wechat.JPG`"
              :fit="fit"></el-image>
        </div>
      </div>
    </div>


  </div>
</template>

<script>
import CommonBanner from "@/views/banner/CommonBanner";


export default {
  name: "AboutMe",
  components: {CommonBanner},
  setup() {
    const backgroundImg = require('@/assets/image/index-main.jpg');
    const avatarIMG = require('@/assets/image/avatar.jpg');

    const userInfo = [
      '姓名: JHQ',
      '<h1 style="color: #23a6d5;">技能: Java, Vue, MySQL, ... </h1>',
    ];
    return {backgroundImg, userInfo, avatarIMG};
  },
  methods: {
    openUrl(url) {
      window.open(url);
    }
  }
}
</script>

<style scoped>

.main-content {
  min-height: 640px;
}

.about-me-container {
  width: 100%;
  border-radius: inherit;
}

.about-me-container h1 {
  float: top;
  font-size: 2rem;
  top: 26px;
  margin: 10px auto;
  text-align: center;
}


/* 头像 */
.avatar-wrap {
  position: relative;
  width: auto;
  height: 200px;
  margin: 24px;
}

.avatar {
  position: absolute;
  left: 0;
  right: 0;
  margin: 10px auto;
}

.info-item-list {
  position: relative;
}


.info-item {
  position: relative;
  margin: 24px 0;
  left: 10%;
}

.ref-items {
  position: relative;
  display: flex;
  justify-content: center;
}

.ref-item {
  position: relative;
  font-size: 1.5rem;
  width: auto;
  height: auto;
}

.wechat-button:hover ~ .show-wechat {
  display: block;
}

.show-wechat {
  position: absolute;
  top: 50px;
  width: 200px;
  border: 1px solid black;
  border-radius: 5px;
  display: none;
  box-shadow: 0 0 10px 0 black;
  z-index: 9999;
}
</style>
