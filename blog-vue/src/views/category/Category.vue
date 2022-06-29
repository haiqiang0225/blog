<template>

  <CommonHello></CommonHello>
  <div style="height: 5000px; width: 1200px; background-color: unset;"></div>

</template>

<script>
import {ref} from "vue"

import CommonHello from "@/views/banner/CommonBanner";

import bannerImgURL from "@/assets/image/category-hello.jpg";

export default {
  name: "Category",
  components: {CommonHello},
  setup() {

  },
}
</script>

<style scoped>


</style>
