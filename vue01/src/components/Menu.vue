
<template>
<div style=" display: flex;">
 <div>
   <el-menu  background-color="#545c64"
             text-color="#fff"
             active-text-color="#ffd04b"  default-active="1" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" >
     <el-menu-item index="1">
       <span slot="title">导航1</span>
     </el-menu-item>
     <el-menu-item @click.native="c" index="2">
       <span slot="title">导航2</span>
     </el-menu-item>
     <el-menu-item index="3">
       <span slot="title">导航3</span>
     </el-menu-item>
     <el-menu-item index="4">
       <span slot="title">导航四</span>
     </el-menu-item>
   </el-menu>
 </div>

</div>
</template>

<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 150px;
  min-height: 550px;
}
</style>

<script>
export default {
  name: "Menu",
  data() {
    return {
    };
  },

  methods: {
    c(){
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>
