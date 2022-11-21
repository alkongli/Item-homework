<template >
<div style="text-align: center">
  <el-dialog
    :visible.sync="Visible" width="100%" :title="logTitle"
  >
    <div style="text-align:center">
      <!--需要弹出的内容部分-->
      <div :style="logTitle==='未加学生学号'?'display:none':''" ref="file"></div>
      <div :style="logTitle!=='未加学生学号'?'display:none':'margin-bottom: 10px'">
        <div v-for="i in Student" :key="i.id" >{{i.name}} 未提交</div>
      </div>
      <el-button type="primary" @click="Visible=false">关闭</el-button>
    </div>
  </el-dialog>
  <el-form :inline="true"  @keyup.enter.native="">
    <el-form-item>{{title}}
    </el-form-item>
    <el-form-item style="margin-left: 100px">
      <el-button
        size="mini"
        type="primary"
        @click="handleUnFish">查看未提交名单</el-button>
    </el-form-item>
  </el-form>
  <div style="margin: 0 auto;width: 50%">
    <el-table
      :data="tableData"
      border
      style="width: 100%"
    >
      <el-table-column label="学号" width="100">
        <template slot-scope="scope">{{ scope.row.userName }}
        </template>
      </el-table-column>
      <el-table-column
        label="提交日期"
        width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="提交文档" width="100">
        <template slot-scope="scope">{{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleLook(scope.$index, scope.row)">预览</el-button>
          <el-button
            size="mini"
            type="primary"
            @click="handleDownload(scope.$index, scope.row)">下载</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">退回</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
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
let docxx = require("docx-preview");
export default {
  inject: ['reload'],
  name: "ListPage",
  data(){
    return{
      Visible:false,
      title:this.$route.query.name,
      text:'',
      logTitle:'',
      Student:[],
      listData:[],
      tableData:[],
      currentPage:1,
      pageInfo:{
        total:0,
        page:1,
        pageSize:10
      },
      classId:this.$route.query.classId,
      workId:this.$route.query.workId
    }
  },
  created() {
    if(this.workId!==undefined&&this.classId!==undefined){
      this.$axios.get("/api/sWork/list",{
        params:{
          workId:this.workId
        }
      }).then((res)=>{
        this.listData=res.data.data
        this.tableData=this.listData.slice(0,this.pageInfo.pageSize)
        this.pageInfo.total=res.data.data.length
      })
    } else{
      this.$message({
        message:"请求参数错误",
        type: 'error',
        duration:2000
      });
    }
  },
  methods:{
    handleUnFish(){
      this.$axios.get("/api/sWork/unFinish",{
        params:{
          workId:this.workId,
          classId:this.classId
        }
      }).then((res)=>{
        this.logTitle="未加学生学号"
        this.Student=res.data.data
        this.Visible=true
      })
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
    handleLook(index, row) {
      this.name=''
      this.Visible=true
      this.$axios.get("/api/download",{
        params:{
          fileName:row.name,
          pathName:row.path
        },
        responseType:'blob'
      }).then((res)=>{
        this.logTitle=row.name
        docxx.renderAsync(res.data,  this.$refs.file);
      })
    },
    handleDownload(index, row) {
      this.$axios.get("/api/download",{
        params:{
          fileName:row.name,
          pathName:row.path
        },
        responseType:'blob'
      }).then((res)=>{
        let blob = new Blob([res.data]);
        let url=window.URL.createObjectURL(blob);
        let a=document.createElement('a')
        a.href=url;
        a.download=row.name;
        a.click();
        window.URL.revokeObjectURL(url)
      })
    },
    handleDelete(index, row) {
      this.$confirm('此操作将退回作业, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
          this.$axios.delete("/api/sWork", {
            params:{
              workId:this.workId,
              sWorkId:row.id
            }
          }).then((res)=>{
            if(res.data.code===1){
              this.reload()
              this.$message({
                message:res.data.data,
                type: 'success',
                duration:2000
              });
            }
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
  }

}
</script>

<style scoped>

</style>
