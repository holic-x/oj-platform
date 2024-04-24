<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png" />
    <MdEditor :value="value" :handle-change="onChange"></MdEditor>
    <HelloWorld msg="Welcome to Your Vue.js + TypeScript App" />
  </div>
</template>

<script setup lang="ts">
import { defineComponent, ref } from "vue";
import HelloWorld from "@/components/HelloWorld.vue";
import MdEditor from "@/components/MdEditor.vue"; // @ is an alias to /src

const value = ref("");
const onChange = (v: string) => {
  value.value = v;
};
</script>
