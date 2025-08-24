
<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input style="width: 260px;margin-right: 10px" v-model="data.name" placeholder="请输入名称查询" prefix-icon="search" ></el-input>
      <el-button type="primary">查 询</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-button type="danger">批量删除</el-button>
      <el-button type="primary">新 增</el-button>
      <el-button type="success">批量导入</el-button>
      <el-button type="info">批量导出</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.tableData" style="width: 100%" :header-cell-style="{fontWeight:'bold', color:'#333',backgroundColor:'#eaf4ff'}">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="name" label="名称" width="200px" /> <!--prop的数据对应下方tableData的数据，label是在表头的名字 -->
        <el-table-column prop="phone" label="电话"  />
        <el-table-column prop="address" label="地址" />
      </el-table>
    </div>

    <div class="card">
      <el-pagination
          v-model:current-page="data.pageNum"
          :page-size="data.pageSize"
          layout="total, prev, pager, next"
          :total="data.total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
    </div>

  </div>
</template>

<script setup>
import {reactive} from "vue";
import axios from "axios";
const  data=reactive({
  name:null,
  pageNum:1,
  pageSize:5,
  total:6,
  tableData:[
      {name:'毛',phone:'13120043067',address:'北交'},
      {name:'郑',phone:'32154322222',address:'关东'},
      {name:'李',phone:'11444333235',address:'关西'},
      {name:'张',phone:'21442433232',address:'湖北'},
      {name:'唐',phone:'43254355555',address:'河北'},
      {name:'王',phone:'11133222222',address:'北京'},
  ]
})


axios.get('http://localhost:9999/admin/selectAll').then (res=>{
  if(res.code != 200){
    //错误提示
  }
  else{
    console.log(res)
  }
})
</script>
<!--端口不一样会产生跨域错误，需要处理跨域 -->





<!--
使用el-button和el-icon不需要但是 使用el-input中使用icon的话必须先导入，
快捷方式：指针在导入icon的图标名上alt+enter


测试用例：
<template>
  <div>
    <div>
      <el-icon size="30" color ='blue'><House /></el-icon>
      <el-input
          v-model="input2"
          style="width: 240px"
          placeholder="Type something"
          :prefix-icon="Search"
      />
      <el-input
          v-model="input1"
          style="width: 240px"
          placeholder="Pick a date"
          :suffix-icon="Calendar"
      />
      prefix指在输入框前面，suffix指在后面

</div>
主页

<el-button>Default</el-button>
<el-button type="primary" icon="search" >Primary</el-button>
<el-button type="success">Success</el-button>
<el-button type="info" style="background-color:deepskyblue">Info</el-button>
<el-button type="info">Info</el-button>
<el-button type="warning">Warning</el-button>
<el-button type="danger">Danger</el-button>


</div>
</template>

<script setup>

import {Calendar, Search} from "@element-plus/icons-vue";
</script>

-->