import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
      { path: '/', redirect:'/manager/home'},
      { path: '/manager', component: import ('../views/Manager.vue'),
        children:[
            { path: 'home',meta: {name :'主页'},component: import ('../views/Home.vue'), },
            { path: 'admin',meta: {name :'管理员信息'},component: import ('../views/Admin.vue'), },
            { path: 'about', component: import ('../views/About.vue'), },
        ]
      },
//父组件网址后面接子组件/home，此时两者都显示，即嵌套路由，/manager/home,效果就类似，左边一栏选择项（菜单）不变，选到不同选项右边具体内容变
    { path: '/notFound', component: import ('../views/404.vue'), },
    { path: '/:pathMatch(.*)', redirect: '/notFound' },

  ],
})

export default router
