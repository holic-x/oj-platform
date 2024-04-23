// initial state
import { StoreOptions } from "vuex";

export default {
  namespaced: true,
  state: () => ({
    loginUser: {
      userName: "未登录",
      role: "user",
    },
  }),
  actions: {
    async getLoginUser({ commit, state }, payload) {
      // todo 调整为远程登录获取用户信息并设置全局参数
      commit("updateUser", { userName: "noob" });
    },
  },
  mutations: {
    updateUser(state, payload) {
      state.loginUser = payload;
    },
  },
} as StoreOptions<any>;
