<template>
  <router-view></router-view>
</template>

<script lang="ts">
import { Vue } from 'vue-class-component';

export default class App extends Vue {

  mounted(){
    // Change the following ip to the one where the WS has been launched
    const connection = new WebSocket('ws://192.168.0.45:3000/');
    connection.onopen = function() {
      console.log("ws::open : connection established ");
    }
    connection.onmessage = (event: MessageEvent) => {
      console.log('received: ', event);
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
