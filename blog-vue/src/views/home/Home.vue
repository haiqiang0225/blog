<template>
  <!-- 封面 -->
  <HomeBanner></HomeBanner>
  <ul v-infinite-scroll="load">
    <li v-for="i in count" :key="i" class="infinite-list-item">{{ i }}</li>
  </ul>
</template>

<script>

import {ref, reactive} from "vue";
import HomeBanner from "@/views/banner/HomeBanner";

export default {
  name: "Home",
  components: {HomeBanner},
  setup() {
    const count = ref(0)
    const load = () => {
      if (count.value >= 100) {
        return;
      }
      count.value += 2
    }

    return {count, load};

  }
}
</script>

<style scoped>


</style>
