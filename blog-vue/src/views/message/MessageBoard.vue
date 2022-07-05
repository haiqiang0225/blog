<template>
  <CommonHello
      :background-image-url="`https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg`"></CommonHello>
  <div class="main-content" style="">
    <div class="send-message-wrap">
      <input class="send-message-ipt">
      <button class="send-message-btn">留言</button>
    </div>
  </div>
</template>

<script>
import CommonHello from "@/views/banner/CommonBanner";

export default {
  name: "MessageBoard",
  components: {CommonHello}
}
</script>

<style scoped>

.main-content {
  backdrop-filter: none;
  -webkit-backdrop-filter: none;
  background-color: unset;
  border-radius: 16px;
  box-shadow: none;
}

.send-message-wrap{
  position: relative;
  /*align-self: center;*/
  margin: 0 0 5px 0.875rem;
  height: 35px;
}

.send-message-ipt {
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

.send-message-btn {
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

.send-message-wrap .send-message-ipt:focus {
  outline: 2px solid #eac816;
  border-color: #eac816;
}
.send-message-wrap .send-message-ipt:focus ~ button {
  outline: 2px solid #eac816;
}

</style>
