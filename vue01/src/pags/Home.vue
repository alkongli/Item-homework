<template>
  <div style=" display: flex;">
    <div >
      <!--  <Menu style="width: 180px;"></Menu>-->
      <div style="width: 180px;">
        <el-menu  background-color="#545c64"
                  text-color="#fff"
                  active-text-color="#ffd04b"   default-active="1" class="el-menu-vertical-demo" >
          <el-menu-item index="1" @click="click('User')">
            <span slot="title">用户管理</span>
          </el-menu-item>
          <el-menu-item index="2" @click="click('Class')">
            <span slot="title">班级管理</span>
          </el-menu-item>
        </el-menu>
      </div>
    </div>
    <component :is="name" />
  </div>
</template>

<script>
import Class from "../components/Class";
import User from "../components/User";
export default {
  name: "Home",
  components:{User,Class},
  data() {
    return {
      name:'User'
    }
  },
  methods:{
    click( name){
      this.name=name
    }
  }
}
</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 150px;
  min-height: 550px;
}
</style>
