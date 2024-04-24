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
          <a-menu-item v-for="item in visibleRoutes" :key="item.path"
            >{{ item.name }}
          </a-menu-item>
        </a-menu>
      </div>
    </a-col>
    <a-col flex="100px">
      <!--      <div>{{ store.state.user?.loginUser?.userName }}</div>-->
      <!--      <div>{{ store.state.user?.loginUser?.userName ?? "尚未登录" }}</div>-->

      <a-dropdown trigger="hover">
        <template
          v-if="loginUserInfo.userRole as string !== ACCESS_ENUM.NOT_LOGIN"
        >
          <template v-if="loginUserInfo.userAvatar">
            <a-avatar>
              <img
                alt="avatar"
                :src="loginUserInfo.userAvatar"
                width="24px"
                height="24px"
              />
            </a-avatar>
            {{ loginUserInfo.userName }}
          </template>
          <template v-else>
            <a-avatar>
              <IconUser />
            </a-avatar>
            {{ loginUserInfo.userName }}
          </template>
        </template>
        <template v-else>
          <a-avatar :style="{ backgroundColor: '#168CFF' }"> 未登录</a-avatar>
        </template>
        <template #content>
          <template v-if="loginUserInfo.userRole !== accessEnum.NOT_LOGIN">
            <a-doption>
              <template #icon>
                <icon-idcard />
              </template>
              <template #default>
                <a-anchor-link href="/user/info">个人信息</a-anchor-link>
              </template>
            </a-doption>
            <a-doption>
              <template #icon>
                <icon-poweroff />
              </template>
              <template #default>
                <a-anchor-link @click="logout">退出登陆</a-anchor-link>
              </template>
            </a-doption>
          </template>
          <template v-else>
            <a-doption>
              <template #icon>
                <icon-user />
              </template>
              <a-anchor-link href="/user/login">用户登录</a-anchor-link>
            </a-doption>
            <a-doption>
              <template #icon>
                <icon-user-add />
              </template>
              <a-anchor-link href="/user/register">用户注册</a-anchor-link>
            </a-doption>
          </template>
        </template>
      </a-dropdown>
    </a-col>
  </a-row>
</template>

<!-- 设置setup vue3写法 -->
<script setup lang="ts">
import { routes } from "../router/routes";

import { useRouter } from "vue-router";
import { computed, ref } from "vue";
import { useStore } from "vuex";
import checkAccess from "@/access/checkAccess";
import accessEnum from "@/access/accessEnum";
import ACCESS_ENUM from "@/access/accessEnum";
import { LoginUserVO, UserControllerService } from "../../generated";

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
// setTimeout(() => {
//   store.dispatch("user/getLoginUser", {
//     userName: "哈哈",
//     userRole: accessEnum.ADMIN,
//   });
// }, 3000);

// 定义显示在菜单的路由数组（过滤隐藏的路由）：computed 动态计算，会联动变更visibleRoutes的值
const visibleRoutes = computed(() => {
  return routes.filter((item, index) => {
    if (item.meta?.hideInMenu) {
      return false;
    }
    // 根据权限过滤菜单
    if (
      !checkAccess(store.state.user.loginUser, item?.meta?.access as string)
    ) {
      return false;
    }
    return true;
  });
});

//获取登陆用户信息
const loginUserInfo: LoginUserVO = computed(
  () => store.state.user?.loginUser
) as LoginUserVO;

// ------ start 操作方法定义 -------
const logout = async () => {
  // 调用登录注销方法
  alert("用户即将注销登录");
  const res = await UserControllerService.userLogoutUsingPost();
  if (res.code === 0) {
    // 注销成功，跳转登录页面
    router.push({
      path: "/user/login",
      replace: true,
    });
  } else {
    alert("登录注销失败，请联系管理员处理");
  }
};
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
