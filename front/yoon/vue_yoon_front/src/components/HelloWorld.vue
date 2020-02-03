<template>
  <div class="hello">
    <regist-modal></regist-modal>
    <login-modal></login-modal>
    <check-login></check-login>
    <regist-hackaton></regist-hackaton>
    <main-subject></main-subject>
    <register-before></register-before>
    <v-btn @click="getGithubAccessToken()">console.log token</v-btn>
    <v-text-field v-model="keyword" @keydown.enter="searchEnter()"> @keydown.enter="searchEnter"</v-text-field>

  </div>
</template>

<script>
import RegistModal from "./RegistModal.vue";
import LoginModal from "./LoginModal.vue";
import CheckLogin from "./CheckLogin.vue";
import RegistHackaton from "./RegistHackaton";
import MainSubject from './MainSubject';
import RegisterBefore from './RegisterBefore';
export default {
  name: 'HelloWorld',
  components : {
    RegistModal,
    LoginModal,
    CheckLogin,
    RegistHackaton,
    MainSubject,
    RegisterBefore,
  },
  props: {
    msg: String
  }, data() {
    return{
    showModal: true,
    keyword:""
  }
  },
  methods:{
    focusOutModel(){
      this.showModal = false;
    },
    searchEnter(){
      this.$router.push({ name: 'searchlist', params: { keyword: this.keyword }});
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
