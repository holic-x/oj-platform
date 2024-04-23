// initial state
import { StoreOptions } from "vuex";
import accessEnum from "@/access/accessEnum";

export default {
  namespaced: true,
  state: () => ({
    loginUser: {
      userName: "未登录",
      userRole: accessEnum.NOT_LOGIN,
    },
  }),
  actions: {
    async getLoginUser({ commit, state }, payload) {
      // todo 调整为远程登录获取用户信息并设置全局参数
      // 根据传递的值设置登录用户信息（{userName: "哈哈",userRole: accessEnum.ADMIN}）
      commit("updateUser", payload);
    },
  },
  mutations: {
    updateUser(state, payload) {
      state.loginUser = payload;
    },
  },
} as StoreOptions<any>;
