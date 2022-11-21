<template>
  <div style="width: 100%">
    <el-form :inline="true"  @keyup.enter.native="">
      <el-form-item>
        <el-input v-model="text" placeholder="班级名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getByName">查询</el-button>
        <el-button  type="primary" @click="userAdd">新增</el-button>
      </el-form-item>
    </el-form>
    <el-dialog
      :visible.sync="Visible"
      width="50%"
      :title="title"
    >
      <div style="text-align:center">
        <!--需要弹出的内容部分-->
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="班级名称" prop="name">
            <el-input v-model="ruleForm.name"></el-input>
          </el-form-item>
          <el-form-item >
            <el-button :style="type?'display:none':''" type="primary" @click="submitFormAdd('ruleForm')">添加班级</el-button>
            <el-button :style="type?'':'display:none'" type="primary" @click="submitFormUpd('ruleForm')">更新班级</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
    <el-table
      :data="tableData"
      border
      style="width: 100%"
    >
      <el-table-column label="班级名" width="300">
        <template slot-scope="scope">{{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column label="操作" >
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="block" >
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[2, 10, 20, 40]"
        :page-size="10"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pageInfo.total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import UserForm from "./UserForm";
export default {
  inject: ['reload'],
  name: "Class",
  components:{UserForm},
  data() {
    return {
      ruleForm: {
        name: '',
      },
      rules: {
        name: [
          {required: true, message: '请输入班级名', trigger: 'blur'}
        ],
      },
      type:true,
      userInfo:'',
      title:'',
      Visible: false,
      text:'',
      currentPage: 1,
      listData:[],
      tableData:[],
      pageInfo:{
        total:0,
        page:1,
        pageSize:10
      },
    }
  },
  created() {
    this.$axios.get("/api/uClass/list").then((res)=>{
      this.listData=res.data.data
      this.tableData=this.listData.slice(0,this.pageInfo.pageSize)
      this.pageInfo.total=res.data.data.length
    })
  },
  methods:{
    getByName(){
      this.$axios.get("/api/uClass/like",{
        params:{
          text:this.text
        }
      }).then((res)=>{
        if(res.data.code===1) {
          this.listData=res.data.data
          this.tableData=this.listData.slice(0,this.pageInfo.pageSize)
          this.$message({
            message: "搜索成功",
            type: 'success',
            duration: 1000
          })
        }
      })
    },
    userAdd(){
      this.title="添加班级"
      this.type=false
      this.Visible = true
    },
    handleSizeChange(val) {
      this.pageInfo.pageSize=val
      let i=(this.pageInfo.page-1)*val
      let j=i+this.pageInfo.pageSize
      this.tableData=this.listData.slice(i,j)

    },
    handleCurrentChange(val) {
      this.pageInfo.page=val
      let i=(val-1)*this.pageInfo.pageSize
      let j=i+this.pageInfo.pageSize
      this.tableData=this.listData.slice(i,j)
    },
    handleEdit(index, row) {
      this.type=true
      this.title="修改班级名"
      this.Visible = true
    },
    submitFormAdd(formName) {
      let data = this.ruleForm
      let axios=this.$axios
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post('/api/uClass/add',data).then((res)=>{
            if(res.data.code===1) {
              this.Visible=false
              this.reload()
              this.$message({
                message: "添加成功",
                type: 'success',
                duration: 2000
              })
            }
            else {
              this.$message({
                message: res.data.msg,
                type: 'error',
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
    submitFormUpd(formName) {
      let data = this.ruleForm
      data.id=this.userInfo.id
      let axios=this.$axios
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.put("/api/uClass",data).then((res)=>{
            if(res.data.code===1) {
              this.Visible=false
              this.reload()
              this.$message({
                message: "修改成功",
                type: 'success',
                duration: 2000
              })
            }else {
              this.$message({
                message: res.data.msg,
                type: 'error',
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
  }
}
</script>

<style scoped>

</style>
