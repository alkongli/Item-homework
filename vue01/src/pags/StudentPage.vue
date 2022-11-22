<template>
  <div >
    <el-dialog
      :visible.sync="Visible"
      width="50%"
      title="修改密码"
    >
      <div style="text-align:center">
        <!--需要弹出的内容部分-->
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="密码" prop="pass">
            <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPass">
            <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
    <el-menu  :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
        <el-menu-item  index="1">未完成</el-menu-item>
        <el-menu-item  index="2">已提交</el-menu-item>
        <el-menu-item  index="3">已截止</el-menu-item>
      <div class="right" style="height: 40px;display: flex;">
        <el-button @click="changePassword" plain >修改密码</el-button>
        <el-button @click="Logout" type="danger" plain>退出登录</el-button>
      </div>
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
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      activeIndex: '1',
      classId:'',
      type:'',
      ruleForm: {
        pass: '',
        checkPass: '',
      },
      rules: {
        pass: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ]
      },
      Visible:false,
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      // console.log(key, keyPath);
      this.type=key
    },
    Logout(){
      this.$axios.post("/api/user/logout")
      this.$router.push("/")
    },
    submitForm(formName) {
      let data={
        password:this.ruleForm.pass,
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.put("/api/user/password",data).then((res)=>{
            if(res.data.code===1) {
              this.Visible=false;
              this.$message({
                message: res.data.data,
                type: 'success',
                duration: 2000
              })
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    changePassword(){
      this.Visible=true
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
.right{
  position: absolute;
  right: 30px;
  top: 15px;
}
</style>
