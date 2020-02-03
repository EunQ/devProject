<template>
    <div style="border: grey solid 1px; margin-top:30px">
            <v-col
    cols="12"
    style="text-align:center"
    >
    <v-layout class="headline mb-3" style="font-weight:bold;font-style:italic;background:lightgrey;font-size:3rem !important; height:5rem">
      <v-flex style="margin:auto">공지사항</v-flex>
    </v-layout>
    </v-col>
        <div class="text-center" v-for="i in alarams.length" :key="i">
            <v-sheet color="#FED64D" v-if="!alarams[i-1].kind">
                <v-row>
                    <v-col
                    cols="1">
                    {{i}}번
                    </v-col>
                    <v-col
                    cols="5">
                        {{alarams[i-1].content}}
                    </v-col>
                    <v-col
                    cols="3">
                        {{alarams[i-1].date}}
                    </v-col>
                    <v-col
                    cols="3">
                        {{alarams[i-1].user}}
                    </v-col>
                </v-row>
            </v-sheet>
            <v-sheet color="gray dark-2" v-else>
                   <v-row>
                    <v-col
                    cols="1">
                    {{i}}번
                    </v-col>
                    <v-col
                    cols="5">
                        {{alarams[i-1].content}}
                    </v-col>
                    <v-col
                    cols="3">
                        {{alarams[i-1].date}}
                    </v-col>
                    <v-col
                    cols="3">
                        {{alarams[i-1].user}}
                    </v-col>
                </v-row>
            </v-sheet>

        </div>
    </div>
</template>
<script>
import axios from 'axios'
export default {
    name:'alarm',
    data(){
        return{
            alarams:[
                {kind:true, content:'<공지> 이것은 공지를 테스트하기위한 1번입니다',date:'20200120',user:'admin'},
                {kind:false, content:'<필독> 홍순범 정대윤 11시내로 모든걸 끝내야함',date:'20191220',user:'admin'},
                {kind:true, content:'<공지> 이것은 공지를 테스트하기위한 2번입니다',date:'20191215',user:'admin'},
                {kind:true, content:'<공지> 이것은 공지를 테스트하기위한 3번입니다',date:'20191218',user:'admin'},
                {kind:true, content:'<공지> 이것은 공지를 테스트하기위한 4번입니다',date:'20191228',user:'admin'},
                {kind:false, content:'<긴급> 홍순범 정대윤 끝나고 카페 가야함',date:'20191230',user:'admin'},
                {kind:true, content:'<공지> 이것은 공지를 테스트하기위한 5번입니다',date:'20191231',user:'admin'},
                {kind:true, content:'<공지> 이것은 공지를 테스트하기위한 6번입니다',date:'20200105',user:'admin'},
            ]
        }
    },
    mounted(){
        this.setting()
    },
    methods:{
        setting(){
             axios.get('http://127.0.0.1:8000/board/')
            .then(message =>{
                console.log(message)
            })
    }
}
}
</script>