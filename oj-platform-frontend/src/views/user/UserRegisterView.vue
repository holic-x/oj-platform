<template>
  <div id="userRegisterView">
    <h2 style="margin-bottom: 16px">用户注册</h2>
    <a-form
      style="max-width: 480px; margin: 0 auto"
      label-align="left"
      auto-label-width
      :model="form"
      @submit="handleSubmit"
    >
      <a-form-item
        :rules="[{ required: true, message: '昵称不能为空' }]"
        field="userName"
        label="昵称"
      >
        <a-input v-model="form.userName" placeholder="请输入昵称" />
      </a-form-item>
      <a-form-item
        :rules="[
          { required: true, message: '账号不能为空' },
          { minLength: 4, message: '账号长度不能低于4位' },
        ]"
        field="userAccount"
        label="账号"
      >
        <a-input v-model="form.userAccount" placeholder="请输入账号" />
      </a-form-item>
      <a-form-item
        :rules="[
          { required: true, message: '密码不能为空' },
          { minLength: 8, message: '密码不能低于8位' },
        ]"
        field="userPassword"
        tooltip="密码不少于8位"
        label="密码"
      >
        <a-input-password
          v-model="form.userPassword"
          placeholder="请输入密码"
        />
      </a-form-item>
      <a-form-item
        :rules="[
          { required: true, message: '密码不能为空' },
          { minLength: 8, message: '密码不能低于8位' },
        ]"
        field="checkPassword"
        tooltip="密码不少于8位"
        label="重复密码"
      >
        <a-input-password
          v-model="form.checkPassword"
          placeholder="请再次输入密码"
        />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit" style="width: 120px"
          >注册
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { reactive } from "vue";
import { UserControllerService, UserRegisterRequest } from "../../../generated";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

/**
 * 表单信息
 */
const form = reactive({
  userAccount: "",
  userPassword: "",
  checkPassword: "",
  userName: "",
} as UserRegisterRequest);

const router = useRouter();
const store = useStore();

/**
 * 提交表单
 * @param data
 */
const handleSubmit = async () => {
  if (
    form.userPassword?.length !== form.checkPassword?.length ||
    form.userPassword !== form.checkPassword
  ) {
    message.error("两次输入的密码不一致");
    return;
  }
  const res = await UserControllerService.userRegisterUsingPost(form);
  // 登陆成功，跳转到主页
  if (res.code === 0) {
    message.success("注册成功！！！");
    await router.push({
      path: "/user/login",
      replace: true,
    });
  } else {
    message.error("登陆失败" + res.message);
  }
};
</script>
