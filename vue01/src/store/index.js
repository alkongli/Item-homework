
import Vuex from 'vuex'
import Vue from "vue";

Vue.use(Vuex)
//响应组件中的动作
const actions={
  username(context,value){
    context.commit('Username',value)
  },
  table(context,value){
    context.commit('Table',value)
  },
  page(context,value){
    context.commit('Page',value)
  },
}

//操作数据(state)
const mutations={
  Username(state,value){
    state.username=value
  },
  Table(state,value){
    state.table=value
  },
  Page(state,value){
    state.page=value
  },
}

//存储数据
const state={
  username:'',
  table:'',
  page:{}
}

export default new Vuex.Store({
  actions,
  mutations,
  state
})
