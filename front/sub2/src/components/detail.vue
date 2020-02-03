<template>
<div>
<v-container style="width:75%; margin:auto;">
    <v-row no-gutter>
      <v-col
        cols="6">
        <v-container>
          <v-img
          :src="hackatonData.i_url"
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
                <div class="headline font-weight-bold text-truncate" style="align-items:center;">{{hackatonData.title}}</div>
            </v-row>
            <v-row style="height: 10vw; align-items:center;font-size:2rem">
                <div >{{hackatonData.s_date}} ~ {{hackatonData.e_date}}</div>
            </v-row>
            <v-row style="height: 10vw; align-items:center;font-size:2rem">
                <div>{{hackatonData.host}}</div>
            </v-row>
            <v-row style="height: 10vw; align-items:center;font-size:2rem">
                <v-row style='align-items:center'>
                  <v-col
                  cols="6"
                  >
                  <div style="text-align:center;font-size:1.5rem" class="text-truncate">{{hackatonData.location}}</div>
                  </v-col>
                  <v-col
                  cols="6"><div style="text-align:center;font-size:1.5rem">{{hackatonData.people_num}}</div>
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
            hackatonData:null,
        }
    },
    mounted(){
       this.hackatonData = this.$route.params.data;
    }
}
</script>