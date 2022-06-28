<template>
  <!-- 封面 -->
  <HomeBanner></HomeBanner>
  <MainContent>
    <div style="height: 5000px; width: 1200px; background-color: unset;"></div>
  </MainContent>
</template>

<script>

import {ref, reactive} from "vue";
import MainContent from "@/components/layout/MainContent";
import HomeBanner from "@/views/banner/HomeBanner";

export default {
  name: "Home",
  components: {MainContent, HomeBanner},
  setup() {

  }
}
</script>

<style scoped>


</style>
