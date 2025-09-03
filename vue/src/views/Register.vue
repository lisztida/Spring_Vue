<template>
  <div class="bg">
    <div style="margin-top: 60px;padding: 20px 20px 20px 20px;margin-right: 10vh;width: 40vh;height: 50vh;min-height: 400px;min-width: 240px;background-color: white;border-radius: 10px;box-shadow:0 0 10px rgba(0,0,0,0.1);opacity: 0.7">
      <el-form status-icon ref="formRef" :model="data.form" :rules="data.rules" >
        <div style="margin-bottom: 40px; text-align: center;font-weight: bold;font-size: 24px">注 册 账 号</div>
        <el-form-item prop="username">
          <el-input size="large" v-model="data.form.username" autocomplete="off" prefix-icon="User" placeholder="请输入账号"/>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="large" show-password v-model="data.form.password" autocomplete="off" prefix-icon="Lock" placeholder="请输入密码"/>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input size="large" show-password v-model="data.form.confirmPassword" autocomplete="off" prefix-icon="Lock" placeholder="请确认密码"/>
        </el-form-item>
        <div style="margin-bottom: 20px">
          <el-button  style="background-color: #03f103;width: 100%" size="large" type="primary" @click="register">注 册</el-button>
        </div>
        <div style="text-align: right">
          已经有账号了？快来<a style="color: #00bbff" href="/login">登录</a>吧
        </div>
      </el-form>
    </div>
  </div>
</template>


<script setup>

import {reactive,ref} from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
import router from "@/router/index.js";

const validatePass = (rule, value, callback) => {
  if (value !== data.form.password) {
    callback(new Error("两次密码输入不一致！"))
  } else {
    callback()
  }
}

const formRef=ref()
const data = reactive({
  form: {},
  rules: {
    username: [
      {required: true, message: '请输入账号', trigger: 'blur'},
      {min: 3, message: '账号最少3位', trigger: 'blur'}
    ],
    password: [
      {required: true, message: '请输入密码', trigger: 'blur'}
    ],
    confirmPassword:[
      {required: true, message: '请再次输入密码', trigger: 'blur'},
      {validator:validatePass,trigger:'blur'}
    ]
  }
})




const login = () =>{
  request.post('/login',data.form).then(res => {
    if (res.code === '200') {
    // 存储用户信息
    localStorage.setItem("code_user", JSON.stringify(res.data ||{}))
      ElMessage.success('登录成功')
      router.push('/')
    } else {
      ElMessage.error(res.msg)
   }
  })
}

const register= () =>{
  formRef.value.validate((valid) => {
    if (valid) {
      request.post('/register', data.form).then(res => {
        if (res.code === '200') {
          ElMessage.success('注册成功')
          router.push('/login')
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}







</script>




<style scoped>
.bg{
  height: 100vh;
  display: flex;
  justify-content: center;
  overflow: hidden;
  background-image: url("@/assets/imgs/Login_Bg1.png");
  background-size: cover;
}

</style>