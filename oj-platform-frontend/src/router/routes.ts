import { RouteRecordRaw } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import accessEnum from "@/access/accessEnum";
import UserLayout from "@/layouts/UserLayout.vue";
import UserLoginView from "@/views/user/UserLoginView.vue";
import UserRegisterView from "@/views/user/UserRegisterView.vue";

export const routes: Array<RouteRecordRaw> = [
  // 定义路由组件（用户相关）
  {
    path: "/user",
    name: "用户",
    component: UserLayout,
    children: [
      {
        path: "/user/login",
        name: "用户登录",
        component: UserLoginView,
      },
      {
        path: "/user/register",
        name: "用户注册",
        component: UserRegisterView,
      },
    ],
  },

  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/about",
    name: "about",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/AboutView.vue"),
  },
  {
    path: "/admin",
    name: "管理员",
    component: () => import("../views/AdminView.vue"),
    meta: {
      access: accessEnum.ADMIN,
    },
  },
  {
    path: "/noAuth",
    name: "无权限访问",
    component: () => import("../views/NoAuthView.vue"),
  },
  {
    path: "/hideMenu",
    name: "隐藏页面",
    meta: {
      hideInMenu: true,
    },
    component: () => import("../views/HideView.vue"),
  },
];
