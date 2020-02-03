<template>
    <v-card
        style="background:#FFBF00">
        <v-row>
            <v-col
            cols="5">
                <v-img :src="content.img" aspect-ratio ="0.8" width="100%"/>
            </v-col>
            <v-col
            cols="7">
                <v-card-title primary-title style="color:black; font-size:2rem" class="text-truncate">
                    {{content.title}}
                </v-card-title>
                <v-card-text style="color:black; font-size:1.5rem">
                    <p class="text"> 모집시작시간 </p>
                    <p class="text">{{content.start}}</p>
                    <p class="text"> 모집시간종료 </p>
                    <p class="text">{{content.end}}</p>
                    <p class="text"> 장소 </p>
                    <p class="text">{{content.location}}</p>
                </v-card-text>
            </v-col>
        
        </v-row>
    </v-card>
</template>
<script>
export default {
    name:'cards',
    props:{
        content: {type:Object}
    }
}
</script>