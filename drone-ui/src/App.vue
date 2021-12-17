<template>
  <router-view></router-view>
</template>

<script lang="ts">
import { Vue } from 'vue-class-component';

export default class App extends Vue {

  mounted(){

    const connection = new WebSocket('ws://172.27.10.86:3000/');
    connection.onopen = function() {
      console.log("ws::open : connection established ");
    }
    connection.onmessage = (event:any) => {
      console.log('received: %s', event);
    }
    
  }  
}
</script>

<style lang="scss">
*{
  box-sizing: border-box;
}

html,body{
  margin: 0;
  overflow: hidden;
}
</style>
