<template>
  <div style="border: 1px solid #ee3c3c;width: 380px;margin: 10% auto">

    <el-dialog
      title="初次登录，修改个人信息"
      :visible.sync="Visible"
      width="50%"
    >
      <div style="text-align:center">
        <!--需要弹出的内容部分-->
        <el-form :model="form" :rules="rules2" label-width="100px" class="demo-ruleForm">
          <el-form-item label="新的密码" prop="password">
            <el-input v-model="form.password"></el-input>
          </el-form-item>
          <el-form-item label="我的班级" prop="classId">
            <el-select v-model="form.classId" placeholder="请选择班级">
              <el-option v-for="item in classList" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitFormUpd('form')">修改完成</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>


    <el-form style="width: 300px;margin-top: 15px" :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="账号:" prop="user">
        <el-input v-model="ruleForm.user" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item  label="密码:" prop="pass">
        <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
      </el-form-item>
      <div style="height: 30px;">
        <input class="link" name="remember" type="checkbox" id="cookie" v-model="checked"><label for="cookie">记住密码</label>
      </div>

      <el-form-item>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import Qs from "qs";

export default {

  name:"login",
  data() {
    var validateUser = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入账号!'));
      } else {
        callback();
      }

    };
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码!'));
      } else {
        callback();
      }
    };
    return {
      //默认false 弹框隐藏状态
      Visible: false,
      classList:{},
      user:{
        id:'',
        name:''
      },
      checked:'',
      ruleForm: {
        user: '',
        pass: '',
      },
      form: {
        classId:'',
        password:''
      },
      rules: {
        user: [
          { validator: validateUser, trigger: 'blur' }
        ],
        pass: [
          { validator: validatePass, trigger: 'blur' }
        ],
      },
      rules2: {
        classId: [
          { required: true, message: '请选择班级', trigger: 'change' }
        ],
        password: [
          { required: true, message: '请修改密码', trigger: 'change' }
        ],
      }
    };
  },
  created() {
    let a=this.$cookies.get("localhost_login")
    if (a==null)
      this.checked=false
    else {
      this.ruleForm.user=a.name
      this.ruleForm.pass=a.password
      this.checked=true
    }
  },
  methods: {
    submitForm(formName) {
      let cook=this.checked
      let data = {
        'name':this.ruleForm.user,
        'password':this.ruleForm.pass
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post("/api/user/login",data
            // Qs.stringify(data)
          )
            .then(res => {
              if(res.data.data!=null){
                if(cook)
                  this.$cookies.set("localhost_login",data,60 )
                this.$store.dispatch("username",res.data.data)
                this.user.id=res.data.data.id;
                this.user.name=res.data.data.name;
                localStorage.setItem("Info",JSON.stringify(this.user));
                if(res.data.data.type===2){
                  if(res.data.data.classId==null)
                  {
                    this.Visible=true
                    this.$axios.get("/api/uClass/list").then((res)=>{
                      this.classList=res.data.data
                    })
                  }else
                    this.$router.push("/student")
                }
                else if(res.data.data.type===1)
                  this.$router.push("/teacher")
                else
                  this.$router.push("/home")
              }else{
                alert(res.data.msg)
              }
            })
        } else {
          console.log('1 error submit!!');
          console.log(this.f);
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    submitFormUpd(formName) {
      let data = this.$store.state.username
      data.password=this.form.password
      data.classId=this.form.classId
      this.$axios.put("/api/user",data).then((res)=>{
        this.$message({
          message:res.data.data,
          type: 'success',
          duration:2000
        })
        this.$router.push("/student")
        this.Visible=false
      })
    },
  }
}
</script>
<style>
.link{
  margin-left:60px;
  margin-bottom: 20px;
  font-size: 10px;
  /*float: left;*/
  /*display: inline-block;*/
}
</style>
