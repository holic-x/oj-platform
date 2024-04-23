import { RouteRecordRaw } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import accessEnum from "@/access/accessEnum";

export const routes: Array<RouteRecordRaw> = [
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
