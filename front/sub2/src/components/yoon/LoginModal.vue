
<template>
    <v-row justify="center">
        <v-dialog v-model="dialog" persistent max-width="600px" @keydown.esc="ifClickESC()">
            <template v-slot:activator="{ on }">
                <v-btn v-on="on" class="button" style="margin:auto;" width="350" height="50">Login</v-btn>
            </template>
            <v-card>
                <v-card-title>
                    <span class="headline">Login</span>
                </v-card-title>
                <v-card-text>
                    <v-container>
                        <v-row>
                            <v-col cols="12">
                                <v-text-field v-model="uid" :color="checkIDcolor" label="ID"></v-text-field>
                            </v-col>
                            <v-col cols="12">
                                <v-text-field type="password" v-model="pwd" :color="checkIDcolor" label="Password"></v-text-field>
                            </v-col>
                        </v-row>
                    </v-container>
                </v-card-text>
                <v-card-text>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="clickLoginBtn()">Login</v-btn>
                    <v-btn color="blue darken-1" text @click="dialog=false">취소</v-btn>
                </v-card-text>
            </v-card>
        </v-dialog>
    </v-row>
</template>

<script>
    import http from '../../http-common'


    export default {
        name: 'LoginModal',
        data () {
            return {
                uid: '',
                pwd: '',
                dialog : false,
                checkIDcolor: "red",
            };
        },
        methods: {
             clickLoginBtn () {
                http.post('/login', { 
                    email : this.uid, 
                    pwd : this.pwd })
                    .then(response => {
                        if (response.data.state === "succ") {
                            alert('로그인 성공');
                            console.log(response.data);
                            // document.cookie = `accessToken=${response.data.accessToken}`;
                            // http.defaults.headers.common['x-access-token'] = response.data.accessToken;
                            document.cookie = `accessToken=${response.data.name}`;
                            http.defaults.headers.common['x-access-token'] = response.data.name;
                            this.dialog = false;
                        }else{
                            alert('로그인 실패');
                        }
                    });
                    return this.loginEnd();
            },
            ifClickESC(){
                this.dialog = false;
            },
            loginEnd(){
                //isLogin
                if(!this.dialog){
                    this.$emit('loginEnd',true);
                }
            }
        }
    };
</script>
<style scoped>
    .button {
        font-weight: bold;
        background:darkgray;
        color:black; border-radius: 10px; margin-top: 10px;
        display: flex; align-items: center; justify-content: center;
    }
</style>