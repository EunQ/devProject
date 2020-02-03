<template>
  <div>
   <v-layout>
     <v-flex xs10 style="height:fit-content;margin:auto;">
        <joinlist></joinlist>
     </v-flex>
   </v-layout>
  </div>
</template>

<script>
import joinlist from '../components/joinlist'
export default {
  name: 'HelloWorld',
  components : {
   joinlist,
  },
  data(){
    return {
      modal : false,
      message : ''
    }
  },
  methods:{

  }
 
}
</script>


