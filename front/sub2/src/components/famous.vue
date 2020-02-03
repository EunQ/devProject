<template>
    <v-row style="max-height:35vw; align-items:center">
        <v-col
        cols="7"
        style="padding:0;">
        <!-- <v-img
            contain
            height="auto"
            max-height="35vw"
            aspect-ratio="1"
            :src='lists[item].i_url'
            style="margin:0;"/> -->
            <v-card 
            outlined
            style="width:100%; height:35vw;margin:0">
            <v-row>
                <v-col
                cols="7"
                style="padding:0; width:100%; height:35vw;"
                >
                <v-img
                    :src="lists[item].i_url"
                    height="35vw">
                </v-img>
                </v-col>
                <v-col
                cols="5"
                style="padding:0; width:100%; height:35vw; overflow:scroll; overflow-x:hidden"
                >
                <v-row style="height:5vw;margin:0">
                    <h3 class="headline mb-3" style="margin:auto">{{lists[item].title}}</h3>
                </v-row>
                <v-row style="height:5vw;margin:0">
                    <h3 style="margin:auto"> {{lists[item].s_date}}</h3>
                </v-row>
                <v-row style="height:5vw; margin:0;align:items:center">
                    <h3 style="margin:auto">{{lists[item].e_date}}</h3>
                </v-row>
                
                </v-col>
            </v-row>
            </v-card>
        </v-col>
        <v-col
        cols="4"
        style="padding:0;max-height:35vw; overflow:scroll;overflow-x:hidden">
            <v-card
            tile
            outlined
            >
            <v-list 
                shaped
                style="padding:0;">
                <v-list-item-group v-model='item' color='primary'>
                    <v-list-item
                    v-for="(item,i) in lists"
                    :key="i"
                    >
                        <v-list-item-content>
                            <v-list-item-title v-text="item.title"></v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                </v-list-item-group>
            </v-list>
            </v-card>

        </v-col>
    </v-row>
</template>
<script>
export default {
    name:'famous',
    data(){
        return{
            item : 0,
            target:0,
            lists:[
                {sep:'/detail/1',title:'예술해커톤', s_date:'20200128',e_date:'20200128',i_url:'http://www.seouldesign.or.kr/file/temp/2017/201711/2f8ee2be-bfd7-47cf-b2c2-341362865135'},
                {sep:'/detail/2',title:'BlockChainHackerthon', s_date:'20200128',e_date:'20200128',i_url:'http://jccei.kr/files/editor/4cc13380-3c07-4247-acc1-0c8d43bb2264.jpg'},
                {sep:'/detail/3',title:'Iot 해커톤', s_date:'20200128',e_date:'20200128',i_url:'http://www.gist.ac.kr/kr/html/sub07/070103.html?mode=D&no=192467&file_id=50131'},
                {sep:'/detail/4',title:'d', s_date:'20200128',e_date:'20200128',i_url:'http://www.kosmi.org/rang_utils/tiny_mce/UPimg/180914120930_%ED%95%B4%EC%BB%A4%ED%86%A4%ED%8F%AC%EC%8A%A4%ED%84%B0.jpg'},
                {sep:'/detail/5',title:'e',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/6',title:'f',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/7',title:'g',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/8',title:'h',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/9',title:'i',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/10',title:'j',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
               ],
        }
    }
}
</script>