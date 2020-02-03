<template>
<div>
    <v-layout wrap>
        <v-flex xs12 sm6 lg3 v-for="i in lists.slice(8*(page-1),8*page)" :key="i.key">
            <join class="ma-3"
            :i_title="i.title"
            :s_date="i.start"
            :e_date="i.end"
            :img_url="i.img"
            :location="i.location"
            :host="i.host"
            :people_num="i.people_num"
            ></join>
        </v-flex>
    </v-layout>

    <div class="text-center" style="margin:auto;">
        <v-pagination
            v-model="page"
            class="my-4"
            :length="parseInt(lists.length / 8) + 1"
            ></v-pagination>
    </div>
    </div>
</template>

<script>
import Join from './Join';
import http from '../http-common';
export default {
    components:{
        Join,
    },
    data(){
        return{
            keyword:"",
            lists : [],
            page:1,
        }
    },
    methods:{
        mountedKeyword(){
            this.keyword = decodeURI(this.$route.params.keyword);
            console.log(this.keyword);
            return this.initList();
        },
        initList(){
            http.get('/searchBoard/'+this.keyword)
            .then(respons=>{
                this.lists = respons.data;
                console.log(this.lists);
            })
            .catch(err=>{
                console.log(err);
            })
        }
    },
    mounted(){
        this.mountedKeyword()
    }
}
</script>