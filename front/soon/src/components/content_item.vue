<template>
    <v-row justify="center">
        <v-dialog v-model="model" width="80%">
            <template v-slot:activator="{on}">
                <v-card
                    class="ma-4"
                    height="500"
                    width="250"
                    :img="im_u"
                    v-on="on"
                >    
            </v-card>
            </template>
            <v-card>
                <v-card-text>
                    <v-img :src="im_u"></v-img>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn outlined x-large color="green darken-1" text @click="model=false">확인</v-btn>
                    <v-btn outlined x-large color="green darken-5" text @click="model=false">취소</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-row>
</template>
<script>
export default {
    name:'content_item',
    props: {
        img_u : String,
    },
    data (){
        return{
            im_u : '',
            model:false,
        }
    },
    mounted(){
        this.first_set()
    },
    methods:{
        first_set(){
            this.im_u = this.img_u;            
        }
    }
}
</script>