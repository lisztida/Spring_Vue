<template>
  <div>
    <!--网站的头部区域-->
    <div style="height: 55px;display: flex; border-bottom: 5px solid rgb(154,206,201);background-color:rgba(184,250,242,0.8);align-items: center">
      <div style="display: flex;align-items: center">
        <img style="height: 60px;width: auto;border-radius:0 0 50% 0  "  src="@/assets/imgs/logo.jpg"  alt="">
        <span style="font-size: 25px;font-weight:bold ;color: #524d4d; margin-left: 5px">管理系统</span>
      </div>

      <div style="flex: 1;display: flex;align-items: center;margin-left:70px;padding-left: 10px;border-left: 6px solid #ff0000;color:#000000;font-size: 20px;font-weight: bold">
        <span @click="router.push('/manager/home')" >首页</span> / {{router.currentRoute.value.meta.name}}
      </div>
      <div style="width: fit-content;display: flex;align-items: center;padding-right: 20px;border-bottom: 1px solid #ddd">
       <el-dropdown>
         <div style="display: flex;align-items: center">
           <img v-if="data.user?.avatar" src="@/assets/imgs/profle.jpg" style="width: 50px;border-radius: 50%" :src="data.user?.avatar"/>
           <img v-else src="@/assets/imgs/profle.jpg" style="width: 50px;border-radius: 50%"  alt="">
           <span style="margin-left: 5px">{{data.user?.name}}</span>
         </div>
         <template #dropdown>
           <el-dropdown-menu>
             <el-dropdown-item>个人信息</el-dropdown-item>
             <el-dropdown-item>修改密码</el-dropdown-item>
             <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
           </el-dropdown-menu>
         </template>
       </el-dropdown>

      </div>
    </div>
    <!--头部区域结束 -->


    <!--下方区域开始 -->
    <div style="display: flex">
      <!--菜单区域开始 -->
      <div style="width: 240px" >
        <el-menu router :default-openeds="['1']" :default-active="router.currentRoute.value.path" style="min-height: calc(100vh - 60px)">
          <!--default-active:进入网页该网页时默认高亮的menu，对应下方index序号相同的menu -->
          <!--为了使菜单触底，使用min-height表示菜单高度,clac为计算方法，100vh表示该元素会撑开与屏幕高度一致，-60px是因为头部高60px，子元素设置高度100vh不会被父元素高度限制 -->
          <el-menu-item index="/manager/home">
            <el-icon><house /></el-icon>
            <span>首页</span>
          </el-menu-item>
          <el-sub-menu index="1">
            <template #title>
              <el-icon><location /></el-icon>
              <span>用户管理</span>
            </template>
            <el-menu-item index="/manager/admin">管理员信息</el-menu-item>
            <el-menu-item index="/manager/user">普通信息信息</el-menu-item>
            <el-menu-item index="/manager/about">关于我</el-menu-item>
          </el-sub-menu>
        </el-menu>

      </div>
      <!--菜单区域结束 -->

      <!--数据渲染区域开始 -->
      <div style="flex: 1;width: 0;padding:10px;background-color: #ebebfa">
        <RouterView />
      </div>
      <!--数据渲染区域结束 -->

    </div>
    <!--下方区域结束 -->
  </div>
</template>





<script setup>
import router from "@/router/index.js";
import {reactive} from "vue";


const data = reactive({
  user:JSON.parse(localStorage.getItem('code_user')||"{}")

})

const logout =()=>{
  localStorage.removeItem('code_user')
  location.href='/login'
}

// if(!data.user.id){
//   location.href='/login'
// } //不使用




</script>

<style>

.el-menu{background-color: #575b83;
  border-color: rgb(154,206,201)
}

.el-sub-menu__title{
  color: #aeefcc;
  background-color: #575b83
}

.el-menu-item{
  color: #ffffff;
}

.el-menu .is-active{
  background-color: #537bee;
  color: #fff
}

.el-sub-menu__title:hover{
  background-color: #575b83
}

.el-menu-item:not(.is-active):hover{
  background-color: #3b518a;
  color: #aeefcc
}
.el-dropdown{
  cursor: pointer;
}
.el-tooltip__trigger{
  outline:none;
}
.el-menu--inline .el-menu-item{
  margin-left: 30px;
}

</style>



<!--
style用于修改样式
.el-menu            :整个菜单样式
.el-sub-menu__title :父菜单样式
.el-menu-item       :子菜单样式
.el-menu .is-active :正在高亮的菜单样式
.el-sub-menu__title:hover:鼠标指针所在的父菜单高亮时的样式
.el-menu-item:not(.is-active):hover:鼠标指针所在的非已经选中的子菜单高亮时的样式
.el-tooltip__trigger :选中下拉栏时，outline:none关闭下拉栏的边框
.el-dropdown{cursor: pointer;}选中下拉栏时鼠标会变成点击
-->



