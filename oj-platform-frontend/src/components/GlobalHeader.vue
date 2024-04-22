<template>
  <div id="globalHeader">
    <a-menu
      mode="horizontal"
      :selectedKeys="selectedKeys"
      @menu-item-click="doMenuClick"
    >
      <a-menu-item
        key="0"
        :style="{ padding: 0, marginRight: '38px' }"
        disabled
      >
        <div class="title-bar">
          <img class="logo" src="../assets/oj-logo.png" />
          <div class="title">Noob OJ</div>
        </div>
      </a-menu-item>
      <a-menu-item v-for="item in routes" :key="item.path"
        >{{ item.name }}
      </a-menu-item>
    </a-menu>
  </div>
</template>

<!-- 设置setup vue3写法 -->
<script setup lang="ts">
import { routes } from "../router/routes";

import { useRouter } from "vue-router";
import { ref } from "vue";

const router = useRouter();
const doMenuClick = (key: string) => {
  router.push({
    path: key,
  });
};

// 默认主页
const selectedKeys = ref(["/"]);
// 路由跳转后，更新选中的菜单项
router.afterEach((to, from, failure) => {
  selectedKeys.value = [to.path];
});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.globalHeader {
  box-sizing: border-box;
  width: 100%;
  padding: 40px;
  background-color: var(--color-neutral-2);
}

.title-bar {
  display: flex;
  align-items: center;
}

.title {
  color: black;
  margin-left: 16px;
}
</style>
