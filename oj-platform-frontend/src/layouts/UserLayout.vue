<template>
  <div id="basicLayout">
    <h2>我的用户布局</h2>
    <a-layout style="min-height: 100vh">
      <a-layout-content class="content">
        <router-view />
        <h1></h1>
      </a-layout-content>
      <a-layout-footer class="footer">
        <a href="http://noob.holic-x.com" target="_blank"> 一人の境 </a>
      </a-layout-footer>
    </a-layout>
  </div>
</template>
<style scoped>
#basicLayout {
}

#basicLayout .header {
  margin-bottom: 16px;
}

#basicLayout .content {
  background: linear-gradient(to right, #bbb, #fff);
  margin-bottom: 16px;
  padding: 20px;
}

#basicLayout .footer {
  background: #42b983;
  padding: 16px;
  position: sticky;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
}
</style>
<script setup lang="ts">
import GlobalHeader from "@/components/GlobalHeader.vue";
</script>
