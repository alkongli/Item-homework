<template>
<div>
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="作业名称" prop="name">
        <el-input v-model="ruleForm.name"></el-input>
      </el-form-item>
      <el-form-item label="作业描述" prop="text">
        <el-input type="textarea" v-model="ruleForm.text"></el-input>
      </el-form-item>
      <el-form-item label="截止时间" prop="days">
        <el-select  v-model="ruleForm.days" placeholder="请选择截止时间">
          <el-option :style="workInfo.id!==null?'':'display:none'" :label="ruleForm.endTime" value="0"></el-option>
          <el-option label="一天后截止" value="1"></el-option>
          <el-option label="三天后截止" value="3"></el-option>
          <el-option label="七天后截止" value="7"></el-option>
          <el-option label="三十天后截止" value="30"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="发布班级" prop="classId">
        <el-select v-model="ruleForm.classId" placeholder="请选择班级">
          <el-option v-for="item in classList" :key="item.id" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button :style="workInfo.id===null?'':'display:none'" type="primary" @click="submitForm('ruleForm')">发布作业</el-button>
        <el-button :style="workInfo.id!==null?'':'display:none'" type="primary" @click="submitFormUpd('ruleForm')">修改作业</el-button>
      </el-form-item>
    </el-form>
</div>
</template>

<script>
export default {
  name: "WorkForm",
  props:{
    workInfo:{}
  },
  watch:{
    workInfo(now){
      this.ruleForm=now
      if(now.id!==null)
        this.ruleForm.days='0'
      else
        this.ruleForm.days=' '
    }
  },

  data(){
    return{
      classList:{},
      ruleForm: {
        name: this.workInfo.name,
        text:this.workInfo.text,
        classId:this.workInfo.classId,
        endTime:this.workInfo.endTime,
        days: '',
      },
      rules: {
        name: [
          { required: true, message: '请输入作业名称', trigger: 'blur' },
        ],
        days: [
          { required: true, message: '请选择截止时间', trigger: 'change' }
        ],
        classId: [
          { required: true, message: '请选择班级', trigger: 'change' }
        ],
        text: [
          { required: true, message: '请填写作业内容', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.$axios.get("/api/uClass/list").then((res)=>{
      this.classList=res.data.data
    })
    if(this.workInfo.id!==null)
      this.ruleForm.days='0'
  },
  methods: {
    submitForm(formName) {
      let data = this.ruleForm
      let user=JSON.parse(localStorage.getItem("Info"))
      this.$refs[formName].validate((valid) => {
        if (valid) {
          data.createId=user.id
          this.$axios.post("/api/work/add",data).then((res)=>{
            if(res.data.code===1) {
              this.$emit("close",false)
              this.$message({
                message: "发布成功",
                type: 'success',
                duration: 2000
              })
            }
          })
          this.Visible=false
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    submitFormUpd(formName) {
      let data = this.ruleForm
      let user=JSON.parse(localStorage.getItem("Info"))
      this.$refs[formName].validate((valid) => {
        if (valid) {
          data.createId=user.id
          this.$axios.put("/api/work",data).then((res)=>{
            if(res.data.code===1) {
              this.$emit("close",false)
              this.$message({
                message: "修改成功",
                type: 'success',
                duration: 2000
              })
            }
          })
          this.Visible=false
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
  }
}
</script>

<style scoped>

</style>
