<template>
  <a-row id="globalHeader" align="center" :wrap="false">
    <a-col flex="auto">
      <div>
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
          <a-menu-item v-for="item in visibleRoutes" :key="item.path">{{
            item.name
          }}</a-menu-item>
        </a-menu>
      </div>
    </a-col>
    <a-col flex="100px">
      <!--      <div>{{ store.state.user?.loginUser?.userName }}</div>-->
      <div>{{ store.state.user?.loginUser?.userName ?? "尚未登录" }}</div>
    </a-col>
  </a-row>
</template>

<!-- 设置setup vue3写法 -->
<script setup lang="ts">
import { routes } from "../router/routes";

import { useRouter } from "vue-router";
import { ref } from "vue";
import { useStore } from "vuex";

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

// 获取全局变量
const store = useStore();
/*
setTimeout(() => {
  store.dispatch("user/getLoginUser", {
    userName: "哈哈",
  });
}, 3000);
*/

// 显示在菜单的路由数组
const visibleRoutes = routes.filter((item, index) => {
  if (item.meta?.hideInMenu) {
    return false;
  }
  return true;
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
