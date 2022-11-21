import Vue from 'vue'
import Router from 'vue-router'
import home from "../pags/Home";
import login from "../pags/LoginPage";
import teacher from "../pags/TeacherHome"
import student from "../pags/StudentPage"
import listPage from "../pags/ListPage";

Vue.use(Router)

export default new Router({
  mode:'history',
  routes: [
    {
      path: '/home',
      name: 'home',
      component: home
    },
    {
      path: '/',
      name: 'login',
      component: login
    },
    {
      path: '/teacher',
      name: 'teacher',
      component: teacher
    },
    {
      path: '/student',
      name: 'student',
      component: student
    },
    {
      path: '/listPage',
      name: 'listPage',
      component: listPage
    },
  ]
})
