<template>
  <div id="app">
    <button v-on:click="getGithubCode">get data</button>
    <button v-on:click="getGithubAccessToken">get token</button>
    <button v-on:click="postSave">save</button>
  </div>
</template>

<script>
import axios from 'axios'

export default {

  name: 'app',
  mounted() { 
    var c  = location.search.split('code=')[1]
    console.log(c);
  },
  data(){
    return {
      githubToken : null
    };
  }
  ,
  methods:{
    getGithubCode : function(){
      window.location.href = "https://github.com/login/oauth/authorize?redirect_uri=http://localhost:8080/github&client_id=7038e018caed836c5c38";
      return false;
    },
    getGithubAccessToken : function(){


      var code = location.search.split('code=')[1];
      var url = "http://192.168.31.131:9000/v1/accessToken";
     
      console.log(code);
      var params = new URLSearchParams();
      params.append("code", code);
      axios.post(url, params)
      .then(response => {
        console.log(response);
        this.githubToken = response['data']['access_token'];
        console.log(this.githubToken);
      }).catch(err => {
        console.log(err);
      })

      return;
    },
    postSave : function(){

      var url = "http://192.168.31.131:9000/v1/signup/github";
     
      var params = new URLSearchParams();
      params.append("accessToken", this.githubToken);
      axios.post(url, params)
      .then(response => {
        console.log(response);
      }).catch(err => {
        console.log(err);
      })

      return;
    }
  }
  
}
</script>

