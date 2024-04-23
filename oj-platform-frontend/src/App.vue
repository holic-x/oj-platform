<template>
  <div id="app">
    <BasicLayout />
  </div>
</template>

<style>
#app {
}
</style>

<script setup lang="ts">
import BasicLayout from "@/layouts/BasicLayout.vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

// 权限拦截校验
const router = useRouter();
const store = useStore();

router.beforeEach((to, from, next) => {
  console.log(to);
  // 仅限制管理员可见，判断当前用户是否有权限
  if (to.meta?.access === "canAdmin") {
    // 校验用户权限
    if (store.state.user.loginUser?.role !== "admin") {
      // 无权访问，跳转到无权访问页面
      next("/noAuth");
      return;
    }
  }
  next();
});
</script>
