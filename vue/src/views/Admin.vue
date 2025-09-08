
<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input clearable @clear="load" style="width: 260px;margin-right: 10px" v-model="data.username" placeholder="请输入账号查询" prefix-icon="search" ></el-input>
      <el-input clearable @clear="load" style="width: 260px;margin-right: 10px" v-model="data.name" placeholder="请输入名称查询" prefix-icon="search" ></el-input>
      <el-button type="primary" @click="load">查 询</el-button>
      <el-button @click="reset">重置</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary" @click="handleAdd">新 增</el-button>
<!--1、在新增按钮上加上点击事件
2、定义弹窗和表单的页面代码
3、点击触发弹窗打开（清空form对象）
4、表单做数据绑定和表单验证
5、后台要有对应的新增接口接受数据
6、新增的接口复制把数据加到数据库
7、表单输入数据后点击确认按钮，把表单的数据传给后台接口
8、在新增成功之后在此加载表格的数据，关闭弹窗-->
<!--表单验证：
在表单el=form上有三个属性
在el-form-item上写上表单项的prop
在rules里面定义验证的规则
定义formRef作为表单的引用
通过formRef对象进行表单验证-->
      <el-button type="danger" @click ="deleteBatch" >批量删除</el-button>
      <el-button type="info" @click="exportData">批量导出</el-button>
      <el-upload action="http://localhost:9999/admin/import"
       style="display: inline-block;margin-left: 10px "
      :show-file-list="false"
      :on-success="handleImportSuccess"
      >
        <el-button type="success">批量导入</el-button>
      </el-upload>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.tableData" style="width: 100%" @selection-change="handleSelectionChange"
                :header-cell-style="{fontWeight:'bold', color:'#333',backgroundColor:'#eaf4ff'}">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="username" label="账号" width="200px" /> <!--prop的数据对应下方tableData的数据，label是在表头的名字 -->
        <el-table-column prop="name" label="名称"  />
        <el-table-column prop="phone" label="电话" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column label="操作" width="100" >
          <template #default="scope">
            <el-button type="primary" icon="Edit" circle @click="handleEdit(scope.row)"></el-button>
            <el-button type="danger" icon="Delete" circle @click="del(scope.row.id)"> </el-button>
          </template>
        </el-table-column>
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

    <el-dialog title="管理员信息"  v-model="data.formVisible" width="40%" destroy-on-close>
      <el-form ref="formRef" :model="data.form" :rules="data.rules" label-width="80px" >
        <el-form-item prop="username" label="账号" >
          <el-input v-model="data.form.username" autocomplete="off" />
        </el-form-item>
        <el-form-item prop="name" label="名称">
          <el-input v-model="data.form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item prop="phone" label="电话">
          <el-input v-model="data.form.phone" autocomplete="off" />
        </el-form-item>
        <el-form-item prop="email" label="邮箱">
          <el-input v-model="data.form.email" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisible = false">取消</el-button>
          <el-button type="primary" @click="save">保存</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>


<script setup>
import {reactive,ref} from "vue";
import request from "@/utils/request.js";
import {ElMessage, ElMessageBox} from "element-plus";
const  data=reactive({
  user:JSON.parse(localStorage.getItem('code_user')||'{}'),
  username:null,
  name:null,
  pageNum:1,
  pageSize:5,
  total:0,
  tableData:[],
  formVisible:false,
  form:{},
  rules:{
    username:[
      {required:true,message:'请填写账号',trigger:'blur'}
    ],
    name:[
      {required:true,message:'请填写名称',trigger:'blur'}
    ],
    phone:[
      {required:true,message:'请填写电话',trigger:'blur'}
    ],
    email:[
      {required:true,message:'请填写邮箱',trigger:'blur'}
    ]
  },
  rows:[]

})

const formRef=ref()



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
  data.username = ''
  data.name = ''
  load()
}

const handleAdd = () => {
  data.formVisible = true
  data.form={}

}

const add =() => {
  //formRef是表单的引用
  formRef.value.validate((valid)=> {
    if (valid) {  //验证通过的情况下
      request.post('/admin/add', data.form).then(res => {
        if (res.code === '200') {
          data.formVisible = false
          ElMessage.success('新增成功')
          load()
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}

const handleEdit = (row) =>{
  data.form =JSON.parse(JSON.stringify(row)) //深度拷贝数据
  data.formVisible = true
}

const update = () =>{
//formRef是表单的引用
  formRef.value.validate((valid)=> {
    if (valid) {  //验证通过的情况下
      request.put('/admin/update', data.form).then(res => {
        if (res.code === '200') {
          data.formVisible = false
          ElMessage.success('修改成功')
          load()
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}


const save = () =>{
  data.form.id ? update() : add()
}

const del = (id) =>{
  ElMessageBox.confirm('删除后无法恢复！您确认删除吗？','删除确认',{type:'warning'}).then(res =>{
    request.delete('/admin/delete'+ id).then(res => {
          if (res.code === '200') {
            ElMessage.success('修改成功')
            load()
          } else {
            ElMessage.error(res.msg)
          }
        })
  }).catch(err =>{})
}

const handleSelectionChange = (rows) =>{ // row就是实际选择的数组
  data.rows =rows
}

const deleteBatch = () =>{
  if(data.rows.length === 0)
  {
    ElMessage.warning("请选择数据")
    return
  }

  ElMessageBox.confirm('删除后无法恢复！您确认删除吗？','删除确认',{type:'warning'}).then(res =>{
    request.delete('/admin/deleteBatch/', {data:data.rows}).then(res => {
      if (res.code === '200') {
        ElMessage.success('批量删除成功')
        load()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err =>{})
}

const exportData = () =>{
  let url =`http://localhost:9999/admin/export?username=${data.username}&name=${data.name}`+`&token=${data.user.token}`
  window.open(url) //open() 方法用于打开一个新的浏览器窗口或查找一个已命名的窗口。

}

const handleImportSuccess =()=>{
  ElMessage.success("批量导入成功")
  load();
}


</script>




<!--端口不一样会产生跨域错误，需要处理跨域

使用el-button和el-icon不需要但是 使用el-input中使用icon的话必须先导入，
快捷方式：指针在导入icon的图标名上alt+enter

405错误:请求的方法和后端定义打的方法类型不一致，例如前端是put请求后端是post接口
-->