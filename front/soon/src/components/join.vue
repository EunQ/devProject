<template>
        <v-card>
            <v-img :src="img_url" height="45vh"></v-img>
            <v-card-title primary-title>
                <div class="headline font-weight-bold text-truncate">{{i_title}}</div>
            </v-card-title>
            <v-card-text>
                <p class="grey--text text-center">{{s_date}} ~ {{e_date}}</p>
                <p class="grey --text text-center">참가비 {{money}}</p>
            </v-card-text>
        </v-card>
</template>
<script>
export default {
    name:'join',
    props:{
        money : {type: Number},
        img_url:{ type: String},
        i_title : {type: String},
        s_date: {type:String},
        e_date : {type:String}
    },
    data(){
        return{

        }
    }
   
}
</script>