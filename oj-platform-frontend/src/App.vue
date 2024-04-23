<template>
  <div id="app">
    <template v-if="route.path.startsWith('/user')">
      <router-view />
    </template>
    <template v-else>
      <BasicLayout />
    </template>
  </div>
</template>

<style>
#app {
}
</style>

<script setup lang="ts">
import BasicLayout from "@/layouts/BasicLayout.vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import { onMounted } from "vue";
import accessEnum from "@/access/accessEnum";

// 权限拦截校验
const router = useRouter();
const store = useStore();
const route = useRoute();

/* ----- 迁移 ------ 在access/index.ts中配置
// 权限拦截（路由点击的时候触发校验，如果不满足则进行拦截）
router.beforeEach((to, from, next) => {
  console.log(to);
  // 仅限制管理员可见，判断当前用户是否有权限
  if (to.meta?.access === accessEnum.ADMIN) {
    // 校验用户权限
    if (store.state.user.loginUser?.userRole !== accessEnum.ADMIN) {
      // 无权访问，跳转到无权访问页面
      next("/noAuth");
      return;
    }
  }
  next();
});
*/

const doInit = () => {
  console.log("hello 欢迎来到我的OJ项目");
  console.log(
    " ......................阿弥陀佛......................\n" +
      "                       _oo0oo_                      \n" +
      "                      o8888888o                     \n" +
      '                      88" . "88                     \n' +
      "                      (| -_- |)                     \n" +
      "                      0\\  =  /0                     \n" +
      "                   ___/‘---’\\___                   \n" +
      "                  .' \\|       |/ '.                 \n" +
      "                 / \\\\|||  :  |||// \\                \n" +
      "                / _||||| -卍-|||||_ \\               \n" +
      "               |   | \\\\\\  -  /// |   |              \n" +
      "               | \\_|  ''\\---/''  |_/ |              \n" +
      "               \\  .-\\__  '-'  ___/-. /              \n" +
      "             ___'. .'  /--.--\\  '. .'___            \n" +
      '         ."" ‘<  ‘.___\\_<|>_/___.’>’ "".          \n' +
      "       | | :  ‘- \\‘.;‘\\ _ /’;.’/ - ’ : | |        \n" +
      "         \\  \\ ‘_.   \\_ __\\ /__ _/   .-’ /  /        \n" +
      "    =====‘-.____‘.___ \\_____/___.-’___.-’=====     \n" +
      "                       ‘=---=’                      \n" +
      "                                                    \n" +
      "....................佛祖保佑 ,永无BUG..................."
  );
};

onMounted(() => {
  doInit();
});
</script>
