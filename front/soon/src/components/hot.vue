<template>
    <!-- <v-card
    style="background:black; align-items:center"
    contain>
    <v-img
    :src="content.i_url" width="80%" style="margin:auto;" aspect-ratio="0.65"/>
    </v-card> -->
    <v-card
        style="background:black">
        <v-row>
            <v-col
            cols="5">
                <v-img :src="content.img" width="100%" aspect-ratio="0.7"/>
            </v-col>
            <v-col
            cols="7">
                <v-card-title primary-title class="text-truncate" style="color:white; font-size:2rem">
                    {{content.title}}
                </v-card-title>
                <v-card-text style="color:white; font-size:1.5rem">
                    <p class="text">모집시작시간</p>
                    <p class="text">{{content.start}}</p>
                    <p class="text">모집시간종료</p>
                    <p class="text">{{content.end}}</p>
                    <p class="text">장소</p>
                    <p class="text">{{content.location}}</p>
                </v-card-text>
            </v-col>
        
        </v-row>
    </v-card>
</template>
<script>
export default {
    name:'hot',
    props:{
        content : Object
    }
}
</script>