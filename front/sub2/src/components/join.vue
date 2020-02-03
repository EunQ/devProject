<template>
        <v-card @click="clickCard()">
            <v-img :src="img_url" height="45vh"></v-img>
            <v-card-title primary-title>
                <div class="headline font-weight-bold text-truncate">{{i_title}}</div>
            </v-card-title>
            <v-card-text>
                <p class="grey--text text-center">{{s_date}} ~ {{e_date}}</p>
            </v-card-text>
        </v-card>
</template>
<script>
export default {
    name:'join',
    props:{
        img_url:{ type: String},
        i_title : {type: String},
        s_date: {type:String},
        e_date : {type:String},
        location : String,
        host : String,
        people_num : String,
    },
    mounted(){
        console.log(this.img_url);
    },
    methods:{
        clickCard(){
            let dto = {
                img_url:this.img_url,
                i_title : this.title,
                s_date: this.s_date,
                e_date : this.e_date,
                location : this.location,
                host : this.host,
                people_num : this.people_num, 
            }
            this.$router.push({ name:'detailpage',params : { data : dto } });
            
        }
    }
   
}
</script>