<template>
    <!-- <v-row>
     <v-row style='text-align:center;align-content:center;max-height:35vw'>
            <h4>해커톤 랭킹</h4> -->
     <!-- <v-layout style="height:15vw; width: 60%; display:block; overflow:scroll; overflow-x:hidden; border:thin inset #009688; margin:auto">
        <div v-for="ranker in rankers" :key="ranker.key" style="text-align:center">
            <v-divider ></v-divider>
                {{ranker.name}}
        <v-divider></v-divider>
                    </div>
    </v-layout> -->
     <!-- </v-row>
     <v-row>
      <v-card
            tile
            outlined
            height="15vw"
            >
            <v-list 
                shaped
                style="margin:0">
                <v-list-item-group  color='primary'>
                    <v-list-item
                    v-for="(ranker,i) in rankers"
                    :key="i"
                    >
                    <v-list-item-content>
                        <v-list-item-title v-text="ranker.name"></v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list-item-group>
        </v-list>
        </v-card>
    </v-row>
</v-row> -->
    <div>
        <v-container style="padding:0; margin:0; max-width:35vw; width:100% !important">
            <v-row no-gutters>
                <v-row style="width:100%">
                    <v-container>
                        <v-layout my-5>
                            <v-flex>
                                <div style="width=100%;text-align:center;">
                                    <h2 class="headline mb-3" style="font-weight:bold;font-style:italic"> 해커톤 랭킹 </h2>
                                </div>
                            </v-flex>
                        </v-layout>
                    </v-container>
                </v-row>
                <v-row style="width:100%;margin:0;">
                     <v-card
                        tile
                        outlined
                        height="15vw"
                        style="width:80%;overflow:scroll; overflow-x:hidden;margin:auto;text-align:center"
                    >
                    <v-list 
                        shaped
                        >
                <v-list-item-group  color='primary'>
                    <v-list-item
                    v-for="(ranker,i) in rankers"
                    :key="i"
                    >
                    <v-list-item-content>
                        <v-list-item-title v-text="ranker.name"></v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list-item-group>
        </v-list>
        </v-card>
                </v-row>
            </v-row>
        </v-container>
    </div>
</template>

<script>
export default {
    name: 'ranking',
    data(){
        return{
            rankers :  [
                {name:"해커톤 a"},
                {name:"해커톤 b"},
                {name:"해커톤 c"},
                {name:"해커톤 d"},
                {name:"해커톤 f"},
                {name:"해커톤 g"},
                {name:"해커톤 h"},
                {name:"해커톤 i"},
                {name:"해커톤 j"},
                {name:"해커톤 k"},
                {name:"해커톤 l"},
                {name:"해커톤 a"},
                {name:"해커톤 b"},
                {name:"해커톤 c"},
                {name:"해커톤 d"},
                {name:"해커톤 f"},
                {name:"해커톤 g"},
                {name:"해커톤 h"},
                {name:"해커톤 i"},
                {name:"해커톤 j"},
                {name:"해커톤 k"},
                {name:"해커톤 l"},
            ],
        }
    }
}
</script>