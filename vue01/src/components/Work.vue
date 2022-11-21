<template>
  <div style="width: 100%">
    <el-form :inline="true"  @keyup.enter.native="">
      <el-form-item>
        <el-input v-model="text" placeholder="作业名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getByName">查询</el-button>
        <el-button  type="primary" @click="userAdd">发布作业</el-button>
      </el-form-item>
    </el-form>
    <el-dialog
      :visible.sync="Visible"
      width="50%"
      :title="title"
    >
      <div style="text-align:center">
        <!--需要弹出的内容部分-->
        <WorkForm :work-info="workInfo"  v-on:close="close"></WorkForm>

      </div>
    </el-dialog>
    <el-table
      :data="tableData"
      border
      style="width: 100%"
    >
      <el-table-column label="作业名" width="100">
        <template slot-scope="scope">{{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column label="作业内容" width="100">
        <template slot-scope="scope">{{scope.row.text}}</template>
      </el-table-column>
      <el-table-column label="提交次数" width="100">
        <template slot-scope="scope">{{scope.row.num}}次</template>
      </el-table-column>
      <el-table-column label="班级" width="100">
        <template slot-scope="scope">{{scope.row.className}}
        </template>
      </el-table-column>
      <el-table-column
        label="创建日期"
        width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="截止日期"
        width="180">
        <template slot-scope="scope">
          <span :style="new Date(scope.row.endTime).getTime()>new Date().getTime()?'margin-left: 10px':'margin-left: 10px;color: #ee3c3c'">
            {{ new Date(scope.row.endTime).getTime()>new Date().getTime()?scope.row.endTime:'已截止'}}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button
            size="mini"
            type="primary"
            @click="handleLook(scope.$index, scope.row)">查看</el-button>

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
import WorkForm from "./WorkForm";
export default {
  inject: ['reload'],
  name: "Work",
  components:{WorkForm},

  data() {
    return {
      workInfo:'',
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
    this.$axios.get("/api/work/tList",{
      params:{
        createId:JSON.parse(localStorage.getItem("Info")).id
      }
    }).then((res)=>{
      this.listData=res.data.data
      this.tableData=this.listData.slice(0,this.pageInfo.pageSize)
      this.pageInfo.total=res.data.data.length
    })
  },
  methods:{
    close(flag){
      this.Visible=flag
      this.reload()
    },
    getByName(){
      this.$axios.get("/api/work/like",{
        params:{
          name:this.text
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
      this.title="添加用户"
      this.workInfo={"id":null,"name": "","text": "","classId":" " }
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
      this.workInfo=row
      this.title="修改作业"
      this.Visible = true
    },
    handleLook(index, row) {

      let resolve = this.$router.resolve({name:'listPage',query:{workId:row.id,classId:row.classId,name:row.name}});
      window.open(resolve.href,'_blank')

    },
  }
}
</script>

<style scoped>

</style>
