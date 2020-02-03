<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="600px" @keydown.esc="ifClickESC()">
      <template v-slot:activator="{ on }">
        <v-btn color="primary" dark v-on="on">공모전 & 해커톤 등록</v-btn>
      </template>
      <v-card>
        <v-card-title>
          <span class="headline">공모전 & 해커톤 등록</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-img v-if="url!=''" :src="url"></v-img>
              </v-col>
              <v-col cols="12">
                   <v-file-input type="file" id="file" ref="file" accept="image/*" v-model="chosenFile" v-on:change="handleFileUpload"/>
              </v-col>
              <v-col cols="12" sm="5">
                <v-text-field :color="titleColor" v-model="title" label="제목*" required></v-text-field>
              </v-col>
              <v-col cols="12" sm="6">
                <v-text-field :color="hostColor" v-model="host" label="주최*" required></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-text-field :color="peopleNumColor" v-model="people_num" type="number"  label="정원*" required></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-text-field :color="priceColor" v-model="price" type="number"  label="참가비" required></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field readonly label="장소" :value="address" @click="sample6_execDaumPostcode()"></v-text-field>
              </v-col>
              <v-col cols="6">
                <v-text-field :readonly="NoplaceCheckBox" v-model="detailAddress" :value="detailAddress" id="_detailAddress"  label="상세 주소"></v-text-field>
              </v-col>
              <v-col clos="6">
                <v-checkbox v-model="NoplaceCheckBox" label="장소 미정"></v-checkbox>
              </v-col>
              <v-col cols="6">
                <calendar @updateColor: :color="applyStartColor" title="접수 시작" @updateDate="updateApplyStart"></calendar>
              </v-col>
              <v-col cols="6">
                <calendar :color="applyEndColor" title="접수 마감" @updateDate="updateApplyEnd"></calendar>
              </v-col>
              <v-col cols="6">
                <calendar :color="startColor" title="시작" @updateDate="updateStartDate"></calendar>
              </v-col>
              <v-col cols="6">
                <calendar :color="endColor" title="마감" @updateDate="updateEndDate"></calendar>
              </v-col>
              <v-col cols="12">
                <v-text-field v-model="info" label="Info" required></v-text-field>
              </v-col>
            </v-row>
          </v-container>

          <small>*indicates required field</small>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">Close</v-btn>
          <v-btn color="blue darken-1" text @click="clickSaveBtn()">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
import Calendar from './Calendar';
import http from '../http-common';
export default {
  name: 'RegistModal',
  components:{
    Calendar,
  },
  props: {
    msg: String,
    showModal : Boolean,
  },
  data(){
    return{
      dialog : false,
      ismailOther : false,
      title : "",
      host:"",
      people_num:Number,
      price:Number,
      file : null,
      chosenFile:null,
      startDate : String,
      endDate : String,
      apply_start : String,
      apply_end : String,
      showPreview: false,
      imagePreview: '',
      address:'',
      detailAddress:'',
      NoplaceCheckBox:false,
      url:"",
      info : "",
	titleColor:'red',
	hostColor:'red',
	peopleNumColor:'red',
	priceColor:'red',
	applyStartColor:'red',
	applyEndColor:'red',
	startColor:'red',
	endColor:'red',
      
    }
  },
  methods:{
    updateStartDate(newDate){
      this.startDate = newDate;
      console.log(this.startDate);
    },
    updateEndDate(newDate){
      this.endDate = newDate;
      console.log(this.endDate);
    },
    updateApplyStart(newDate){
      this.apply_start = newDate;
      console.log(this.apply_start);
    },
    updateApplyEnd(newDate){
      this.apply_end = newDate;
      console.log(this.apply_end);
    },
    handleFileUpload(e){
      this.file = e;
      if(e===undefined||e===""||e===null){
        this.url = "";
        return;
      }
      if (!this.chosenFile) {this.file = "No File Chosen"}
      var reader = new FileReader();
      reader.readAsDataURL(this.chosenFile);
      reader.onload = () => {
        this.url = reader.result;
      }
    },
    sample6_execDaumPostcode(){
      if(this.NoplaceCheckBox){
        return;
      }
      let addr = "";
      let func = this.callbackFunc;
        new daum.Postcode({
            oncomplete: function(data) {
                if (data.userSelectedType === 'R') {
                    addr = data.postcode+", "+data.address+", "
                }
                return func(addr);
            },
            onclose: function(state) {
              if(state === 'FORCE_CLOSE'){
                document.getElementById("_detailAddress").focus();          
              }
            }
        }).open()
    },
    callbackFunc(data){
      this.address = data;
      document.getElementById("_detailAddress").focus();
    },
    ifClickESC(){
      this.dialog = false;
    },
    registCheck(){
      //정확하게 입력하였는지 채킹해야된다.
      return false;
    },
    clickSaveBtn(){
      let formdata = new FormData();
      if(this.address===""){
        this.address="장소 미정";
      }
      let dto = {
        title:this.title,
        host:this.host,
        apply_start:this.apply_start,
        apply_end:this.apply_end,
        start:this.startDate,
        end:this.endDate,
        people_num:this.people_num,
        price:this.price,
        info:this.info,
        email:'data@data'
        };
        dto = JSON.stringify(dto);
      console.log(dto);
      formdata.append('file',this.chosenFile);
      formdata.append('dto_str',dto);
      if(this.registCheck()){
        return;
      }
      
      http.post('/addBoard',formdata,{
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      )
      .then(response => {
        if (response.data.state === "succ") {
            alert('성공');
        }else{
          alert('실패');
        }
      }).catch(()=>{
        console.log("실패")
      });
      return console.log(dto);
    }
  }, 
  watch:{
    NoplaceCheckBox(){
      if(this.NoplaceCheckBox){
        this.address = "";
        this.detailAddress = "";
      }
    },
    title(){

    },
    host(){

    },
    apply_start(){

    },
    apply_end(){

    },
    startDate(){

    },
    endDate(){

    },
    price(){

    },
    people_num(){
      
    }
  },
}
</script>
<style>

  .overline.align-self-center{
    display:inline-block;
    padding-left:15px;
  }
  .myBtn{
    display:inline-block;
    
  }
</style>