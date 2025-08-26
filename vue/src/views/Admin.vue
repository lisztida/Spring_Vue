
<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input clearable @clear="load" style="width: 260px;margin-right: 10px" v-model="data.username" placeholder="请输入账号查询" prefix-icon="search" ></el-input>
      <el-input clearable @clear="load" style="width: 260px;margin-right: 10px" v-model="data.name" placeholder="请输入名称查询" prefix-icon="search" ></el-input>
      <el-button type="primary" @click="load">查 询</el-button>
      <el-button @click="reset">重置</el-button>
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
        <el-table-column prop="username" label="账号" width="200px" /> <!--prop的数据对应下方tableData的数据，label是在表头的名字 -->
        <el-table-column prop="name" label="名称"  />
        <el-table-column prop="phone" label="电话" />
        <el-table-column prop="email" label="邮箱" />
      </el-table>
    </div>

    <div class="card">
      <el-pagination
          v-model:current-page="data.pageNum"
          v-model:page-size="data.pageSize"
          layout="total, sizes ,prev, pager, next, jumper"
          :total="data.total"
          :page-sizes="[5,10,20]"
          @size-change="load"
          @current-change="load"
      />
    </div>

  </div>
</template>


<script setup>
import {reactive} from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
const  data=reactive({
  username:null,
  name:null,
  pageNum:1,
  pageSize:5,
  total:0,
  tableData:[]
})


const load = () =>{
  request.get('admin/selectPage',{
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      username: data.username,
      name: data.name
    }
  }).then (res => {
    if(res.code ==='200'){
      data.tableData=res.data.list
      data.total=res.data.total
    }
    else{
      ElMessage.error(res.msg)
    }
  })
}
load()

const reset = () =>{
  data.username = null
  data.name = null
  load()
}



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