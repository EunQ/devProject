<template>
<div>
<v-container style="width:75%; margin:auto;">
    <v-row no-gutter>
      <v-col
        cols="6">
        <v-container>
          <v-img
          :src="lists[h_key-1].i_url"
          style="margin:auto"
          width="100%"
          aspect-ratio="0.8"
          />
        </v-container>
      </v-col>
      <v-col
        cols="6">
        <v-container>
            <v-row style="height: 10vw; align-items:center;">
                <div class="headline font-weight-bold text-truncate" style="align-items:center;">{{lists[h_key-1].title}}</div>
            </v-row>
            <v-row style="height: 10vw; align-items:center;font-size:2rem">
                <div >{{lists[h_key-1].s_date}} ~ {{lists[h_key-1].e_date}}</div>
            </v-row>
            <v-row style="height: 10vw; align-items:center;font-size:2rem">
                <div>주최자</div>
            </v-row>
            <v-row style="height: 10vw; align-items:center;font-size:2rem">
                <v-row style='align-items:center'>
                  <v-col
                  cols="6"
                  >
                  <div style="text-align:center;font-size:1.5rem" class="text-truncate"> 장소</div>
                  </v-col>
                  <v-col
                  cols="6"><div style="text-align:center;font-size:1.5rem">현황(현재 신청자수 / 신청자 수 제한)</div>
                  </v-col>
                </v-row>
            </v-row>
        </v-container>
      </v-col>
    </v-row>
  </v-container>
  <v-container height="10vw" style="width:75%">
    <v-flex>
      <v-layout>
        <a :href="lists[h_key-1].i_url" target="__blank" style="text-decoration:none;margin-left:auto">
          <v-btn
          prima
          color="orange" 
          style=" height: 3rem; width: 15rem; padding: 0; font-size: 2rem; font-weight: bolder">신청하기</v-btn></a>
      </v-layout>
    </v-flex>
  </v-container>
  </div>
</template>
<script>
export default {
    name:'detail',
    props:{
        h_key: Number,
    },
    data(){
        return{
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
                {sep:'/detail/11',title:'k', s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/12',title:'l',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/13',title:'m',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/14',title:'n',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/15',title:'o',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/16',title:'p',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/17',title:'q',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/18',title:'r',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/19',title:'s',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/20',title:'t',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/21',title:'u',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/22',title:'v',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/23',title:'w',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/24',title:'x',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/25',title:'y',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/26',title:'z',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/27',title:'ㄱ',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/28',title:'ㄴ',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/29',title:'ㄷ',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/30',title:'ㄹ',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/31',title:'ㅁ',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/32',title:'ㅂ',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/33',title:'아',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/34',title:'자',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
                {sep:'/detail/35',title:'차',s_date:'20200128',e_date:'20200128',i_url:'https://img9.yna.co.kr/etc/inner/KR/2018/05/09/AKR20180509095500004_01_i_P2.jpg'},
            ]
        }
    }
}
</script>