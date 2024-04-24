<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png" />
    <MdEditor :value="mdValue" :handle-change="mdOnChange"></MdEditor>
    <CodeEditor :value="codeValue" :handle-change="codeOnChange"></CodeEditor>
    <HelloWorld msg="Welcome to Your Vue.js + TypeScript App" />
  </div>
</template>

<script setup lang="ts">
import { defineComponent, ref } from "vue";
import HelloWorld from "@/components/HelloWorld.vue";
import MdEditor from "@/components/MdEditor.vue";
import CodeEditor from "@/components/CodeEditor.vue"; // @ is an alias to /src

const mdValue = ref("");
const mdOnChange = (v: string) => {
  mdValue.value = v;
};

const codeValue = ref("");
const codeOnChange = (v: string) => {
  codeValue.value = v;
};

</script>
