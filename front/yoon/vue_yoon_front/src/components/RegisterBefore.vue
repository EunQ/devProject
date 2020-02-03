<template>
  <v-row justify="center">
    <v-dialog  v-model="dialog" persistent max-width="1000px" @keydown.esc="ifClickESC()">
      <template v-slot:activator="{ on }">
        <v-btn color="primary" dark v-on="on">회원가입</v-btn>
      </template>
      <v-card justify-center>
        <v-img :aspect-ratio="2" src="https://cdn.pixabay.com/photo/2015/10/02/10/36/the-background-968350_1280.jpg">
        <v-layout align-center fill-height>
                <v-flex >
                    <v-row style="margin:10px;">
                        <login-modal @loginEnd="isLogin"></login-modal>
                    </v-row>
                    <v-row style="margin:10px;">
                        <v-btn class="button" style="margin:auto;" width="350" height="50" @click="getGithubCode()">
                             <img class='image' src='https://github.githubassets.com/images/modules/logos_page/Octocat.png' />
                            GtiHub Login
                            </v-btn>
                    </v-row>
                    <v-row>
                        <v-btn class="button" @click="getGithubCode()" style="margin:auto;" min-width="350" min-height="50">
                            <img class='image' src='https://github.githubassets.com/images/modules/logos_page/Octocat.png' />
                            GitHub 연동 회원가입
                            </v-btn>
                    </v-row>
                </v-flex>
            </v-layout>
        </v-img>
        <v-card-actions style="background-color: darkgray;">
          <v-spacer></v-spacer>
        <v-layout justify-center>
            <v-flex >
                <v-btn text @click="dialog = false">Close</v-btn>
            </v-flex>
        </v-layout>
        </v-card-actions>
      </v-card>
      
    </v-dialog>
  </v-row>
</template>

<script>
import LoginModal from './LoginModal';
export default {
    components:{
        LoginModal,
    },
    data(){
        return{
        dialog : false,
        }
    },
    methods:{
        getGithubCode(){
            window.location.href = "https://github.com/login/oauth/authorize?redirect_uri=http://localhost:8080/github&client_id=7038e018caed836c5c38";
            return false;
        },
        ifClickESC(){
            this.dialog = false;
        },
        loginEnd(data){
            if(data == true){
                this.dialog = false;
            }
        }
    }
}
</script>

<style scoped>
    .button {
        font-weight: bold;
        background:darkgray;
        color:black; border-radius: 10px; margin-top: 10px;
        display: flex; align-items: center; justify-content: center;
    }
    .button:hover {
        cursor: pointer;
    }
    .image {
        width: 40px; height: auto;
    }
</style>
