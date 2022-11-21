<template>
  <div >  学生

    <el-menu  :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
        <el-menu-item  index="1">未完成</el-menu-item>
        <el-menu-item  index="2">已提交</el-menu-item>
        <el-menu-item  index="3">已截止</el-menu-item>
    </el-menu>
    <WorkCard  :class-id="classId" :type="type" ></WorkCard>
  </div>
</template>

<script>
import WorkCard from "../components/WorkCard";
export default {
  name: "StudentPage",
  components:{WorkCard},
  data() {
    return {
      activeIndex: '1',
      videoVisible: false,
      classId:'',
      type:''
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      // console.log(key, keyPath);
      this.type=key
    }
  },
  created() {
    this.$axios.get("/api/user",{
      params:{
        name:JSON.parse(localStorage.getItem("Info")).name
      }
    }).then((res)=>{
      this.classId=res.data.data.classId
      this.type="1"
    })
  }
}
</script>

<style scoped>

</style>
