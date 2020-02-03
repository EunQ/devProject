<template>
  <v-app >
    <v-content>
      <side/>
      <router-view/>
    </v-content>
  </v-app>
</template>

<script>
import side from './components/side'
export default {
  name: 'App',
  components : {
    side,
  },
  data: () => ({
    //
  }),
};
</script>
