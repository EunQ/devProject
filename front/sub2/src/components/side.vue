<template>
    <header>
        <v-app-bar class="container-fluid" style="background:black;height:10vw;width:100%;padding-top:3vw">
            <v-toolbar-title class="col-2">
                <router-link to="/" tag="span" style="cursor:pointer; font-weight:bold;"> 
                <v-img
                width="130px"
                height="80px"
                src="https://files.slack.com/files-pri/TS565PB7V-FTEGLM6H4/______1.png"
                contain>
                </v-img>
                </router-link> 
            </v-toolbar-title>
            <v-container class="d-flex justify-center col-6 offset-1" id="search" style="width:100%;" >
                <v-text-field solo rounded label='검색' hide-details="auto" height="4rem"></v-text-field>
            </v-container>
            <v-toolbar-items class="col justify-end">
                <v-btn
                text
                large
                v-for="item in menuItems"
                :key="item.title"
                :to="item.path"
                style="padding:0;">
                <v-icon left color="white" style="margin:0" x-large>{{item.icon}}</v-icon>
            </v-btn>
                <regist-hackaton></regist-hackaton>
                <regi-before></regi-before>
                <RegistModal></RegistModal>
            </v-toolbar-items>
        </v-app-bar>
    </header>
</template>
<script>
import RegiBefore from './yoon/RegisterBefore'
import RegistHackaton from './yoon/RegistHackaton'
import RegistModal from './yoon/RegistModal';
export default {
    components:{
        RegiBefore,
        RegistHackaton,
        RegistModal
    },
    name : 'side',
    data(){
        return {
            menuItems:[
                {title: '신청하기', path: '/join', icon: 'public'}
            ]
        }
    },
    methods: {

    }
}
</script>
