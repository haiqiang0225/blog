<template>

  <CommonHello></CommonHello>
  <MainContent>
    <div style="height: 5000px; width: 1200px; background-color: unset;"></div>
  </MainContent>

</template>

<script>
import {ref} from "vue"

import CommonHello from "@/views/banner/CommonBanner";
import MainContent from "@/components/layout/MainContent";

import bannerImgURL from "@/assets/image/category-hello.jpg";

export default {
  name: "Category",
  components: {CommonHello, MainContent},
  setup() {

  },
}
</script>

<style scoped>


</style>
