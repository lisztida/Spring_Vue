import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
// https://vite.dev/config/


import AutoImport from 'unplugin-auto-import/vite' //自动导入vue中的组件
import Components from 'unplugin-vue-components/vite'//自动导入UI组件比如element-plus等等
import {ElementPlusResolver} from "unplugin-vue-components/resolvers";//对应组件库导入，AntDesignVueResolver



export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
    AutoImport({resolvers:[ElementPlusResolver()]}),//element-plus按需导入
    Components({resolvers:[ElementPlusResolver({importStyle:'sass'})]}),//配置elementPlus采用sass式样配置系统
  ],
  css:{
    preprocessorOptions:{
      scss:{
        additionalData:'@use "@/assets/css/index.scss" as *;',
      },
    },
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
})
