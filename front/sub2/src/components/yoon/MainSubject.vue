<template>
  <v-hover v-slot:default="{ hover }">
    <v-card
      class="mx-auto"
      color="grey lighten-4"
      max-width="600"
      titile="123"
    >
      <v-img
        :aspect-ratio="16/9"
        src="https://cdn.vuetifyjs.com/images/cards/kitchen.png"
      >
        <v-expand-transition>
          <v-row
            v-if="hover"
            class="d-flex transition-fast-in-fast-out darken-2 v-card--reveal display-3 white--text"
            justify="center"
            align="center"
          >
          </v-row>
        </v-expand-transition>
        
      </v-img>
    </v-card>
  </v-hover>
