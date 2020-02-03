<template>
    <!-- <v-container>
        <v-layout>
            <v-flex> -->
                <v-row >
                    <v-container style="padding:0;margin:0;max-width:100% !important;">
                        <v-layout>
                            <v-avatar size="100" style='margin:auto'>
                                <v-img
                                    src="https://www.snuh.org/upload/med/dr/b0c68de4757d4c41a572c52157aa62ee.jpg"
                                    alt="홍순범"/>
                            </v-avatar>
                        </v-layout>
                        <v-layout style="height:10vh; text-items:center">
                            <h2 style="margin:auto"> {{u_id}}</h2>
                        </v-layout>
                        <v-layout style="height:10vh; text-items:center" >
                            <h2 style="margin:auto"> {{u_nick}}</h2>
                        </v-layout>
                        <v-layout style="height:5vh; align-items:center">
                            <a :href="e_url" target="_blank" style="text-decoration:none">
                                <v-icon x-large >
                                {{'fa-github'}}
                                </v-icon>
                            </a>
                        </v-layout>
                    </v-container>
                </v-row>
            <!-- </v-flex>
        </v-layout>
    </v-container> -->
</template>
<script>
export default {
    name:'profile',
    data(){
        return{
            u_id:'ssshhh0402',
            u_nick:'홍순범',
            e_url : 'https://www.github.com/ssshhh0402',
            users : [
                {name:"유저 a"},
                {name:"유저 b"},
                {name:"유저 c"},
                {name:"유저 d"},
                {name:"유저 f"},
                {name:"유저 g"},
                {name:"유저 h"},
                {name:"유저 i"},
                {name:"유저 j"},
                {name:"유저 k"},
                {name:"유저 l"},

            ]
        }
    },

}
</script>
