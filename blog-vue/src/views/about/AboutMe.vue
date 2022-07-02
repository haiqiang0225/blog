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
      '技能: Java, Vue, MySQL, OS',
      '<h1 style="color: red;"> 这是一个h1标签 </h1>',
    ];
    return {backgroundImg, userInfo, avatarIMG};
  }
}
</script>

<style scoped>

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
</style>
