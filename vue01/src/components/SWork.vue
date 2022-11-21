<template>

  <div>
<!--    <el-alert :style="file?'margin-bottom: 10px':'display:none'" title="文件上传成功" type="success"  center show-icon/>-->
    <el-upload action :show-file-list="false" class="upload-demo" drag :http-request="upload" multiple>
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
    </el-upload>
    <el-button
      style="margin-top: 100px"
      size="mini"
      type="primary"
      @click="handleUp">提交作业
    </el-button>
  </div>
</template>

<script>
export default {
  name: "SWork",
  props:{
    info:''
  },
  watch:{
    info(){
      this.file=false
    }
  },
  data(){
    return{
      file:false,
      sWork:{
        name:'',
        workId:this.info.id,
        userId:JSON.parse(localStorage.getItem("Info")).id,
        userName:JSON.parse(localStorage.getItem("Info")).name
      }
    }
  },
  methods:{
    handleUp(){
      if(this.file===true){
        this.$axios.post("/api/sWork/add",this.sWork).then((res=>{
          if(res.data.code===1){
            this.$message({
              message:res.data.data,
              type: 'success',
              duration:2000
            });
          }else{
            this.$message({
              message:"提交失败",
              type: 'error',
              duration:2000
            });
          }
        }))
        this.$emit("close",false)
      }else {
        this.$message({
          message:"请先上传文件",
          type: 'error',
          duration:2000
        });
      }
    },
    upload(file){
      let formData = new FormData();
      this.sWork.name=file.file.name
      formData.set("file", file.file);
      formData.set("workId",this.sWork.workId)
      formData.set("userName",this.sWork.userName);
      this.$axios.post("/api/upload",formData).then((res)=>{
          if(res.data.code===1){
            this.file=true
            this.$message({
              message:res.data.data,
              type: 'success',
              duration:2000
            });
          }
        }
      )
    }
  }
}
</script>

<style scoped>

</style>
