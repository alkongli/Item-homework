<template>
  <div>
    <el-dialog
      :title="info.name"
      :visible.sync="Visible"
      width="50%"
    >
      <div style="text-align:center">
        <!--需要弹出的内容部分-->
        <SWork :info="info"  v-on:close="close"></SWork>
      </div>
    </el-dialog>
    <el-row >
      <el-card v-for="i in work" :key="i.id" shadow="hover" style="margin-top: 10px" :body-style="{ padding: '10px' }">
        <span>{{i.name}}</span>
        <div style="padding: 14px;">
          <span>{{i.text}}</span>
          <div class="bottom clearfix">
            <time class="time">截止时间: {{i.endTime}}</time>
            <el-button :style="type!=='1'?'display:none':''" type="text" class="button" @click="play(i)">提交作业</el-button>
            <el-alert :style="type!=='2'?'display:none':'width:30%;margin-top:10px'" title="作业已提交" type="success" :closable="false"></el-alert>
            <el-alert :style="type!=='3'?'display:none':'width:30%;margin-top:10px'" title="时间已截止" type="error" :closable="false"></el-alert>
          </div>
        </div>
      </el-card>
    </el-row>
  </div>
</template>

<script>
import SWork from "./SWork";
export default {
  name: "WorkCard",
  components:{SWork},
  props:{
    createId:'',
    classId:'',
    type:''
  },
  data(){
    return{
      work: {},
      Visible: false,
      info:{}
    }
  },
  watch:{
    type(a){
      let path="/api/work/sList"
      if (a==="2")
        path="/api/work/finish"
      this.$axios.get(path,{
        params:{
          classId:this.classId,
          userId:JSON.parse(localStorage.getItem("Info")).id
        }
      }).then((res)=>{
        if(a==="3"){
          this.work={}
          res.data.data.forEach(
            (item,index)=>{
              if(new Date().getTime()-new Date(item.endTime).getTime()>0)
                this.work.append(item)
            }
          )
        }else
          this.work=res.data.data
        // console.log(new Date().getTime()-new Date(this.work[0].endTime).getTime())
      })
    },
    createId(){
      this.$axios.get("/api/work/list",{
        params:{
          createId:this.createId
        }
      }).then((res)=>{
        this.work=res.data.data
      })
    }
  },
  methods:{
    close(flag){
      this.Visible = flag

    },
    play(i) {
      //触发点击时,让其显示
      this.info=i
      this.Visible = true
    },
  }
}
</script>

<style scoped>
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
</style>
