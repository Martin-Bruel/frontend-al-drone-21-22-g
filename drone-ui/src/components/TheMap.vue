<template>
  <div ref="map-root" class="map"></div>
  <div v-if="mapLoaded">
    <div v-for="drone in droneList" :key="drone.id">
      <Drone :drone="drone"></Drone>
    </div>
  </div>
</template>

<script src="./TheMap/TheMap.component.ts" lang="ts"></script>
<style src="./TheMap/TheMap.component.scss" lang="scss"></style>
