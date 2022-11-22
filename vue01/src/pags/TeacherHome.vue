<template>
<div>
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

  <div style=" display: flex;margin-top: 20px;text-align: center">
    <Work ></Work>
    <div class="right" style="height: 40px;display: flex;">
      <el-button @click="changePassword" plain >修改密码</el-button>
      <el-button @click="Logout" type="danger" plain>退出登录</el-button>
    </div>
  </div>

</div>
</template>

<script>
import Work from "../components/Work";
export default {
  name: "TeacherHome",
  components:{Work},
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
    };
  },
  methods: {
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
  }
}
</script>

<style scoped>
.right{
  position: absolute;
  right: 30px;
  top: 20px;
}
</style>
