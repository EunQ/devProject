<template>
      <v-menu
        v-model="menu"
        :close-on-content-click="false"
        :nudge-right="40"
        transition="scale-transition"
        offset-y
        min-width="290px"
      >
        <template v-slot:activator="{ on }">
          <v-text-field
            v-model="date"
            :label="title"
            readonly
            v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker v-model="date" @input="menu = false"></v-date-picker>
      </v-menu>
</template>
<script>
  export default {
    props:{
        title:String
    },
    data: () => ({
      date: new Date().toISOString().substr(0, 10),
      menu: false,
    }),
    methods:{
        updateDate(){
            this.$emit('updateDate',this.date);
        }
    },
    watch:{
        date(){
            this.updateDate();
        }
    }
  }
</script>