<template>
    <v-row justify="center">
            <template>
                <v-btn color="primary" dark @click="clickLoginBtn()">checkLogin</v-btn>
            </template>

    </v-row>
</template>

<script>
    import http from '../http-common';

    export default {
        name: 'CheckLoginBtn',
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
                http.post('/checkLogin')
                    .then(response => {
                        if (response.data.state === "succ") {
                            alert('로그인 성공');
                            this.dialog = false;
                        }else{
                            alert('로그인 실패');
                        }
                    });
            }
        }
    };
</script>