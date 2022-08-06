<template>

  <div class="footer-container">
    <h1>@2022 - {{ new Date().getFullYear() }} By HQ</h1>
    <h2>powered by:</h2>
    <a href="https://cn.vuejs.org/">Vue3</a>
    &nbsp;
    <a href="https://spring.io/">Spring</a>
      &nbsp;
    <a href="https://beian.miit.gov.cn/#/Integrated/index">鲁ICP备2022025309号-1</a>
    &nbsp;
  </div>
</template>

<script>
export default {
  name: "Footer"
}
</script>

<style scoped>
.footer-container {
  width: 100%;
  line-height: 2;
  position: relative;
  top: 48px;
  min-height: 96px;
  max-height: 96px;
  padding: 40px 20px;
  color: #eee;
  font-size: 14px;
  text-align: center;

  background: linear-gradient(-45deg, #ee7752, #ce3e75, #23a6d5, #23d5ab);
  background-size: 400% 400%;
  animation: changed-color 10s ease infinite;

}

@keyframes changed-color {
  0% {
    background-position: 0 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0 50%;
  }
}

.footer-container h1 {
  font-size: 20px;
  text-align: center;
}
</style>
