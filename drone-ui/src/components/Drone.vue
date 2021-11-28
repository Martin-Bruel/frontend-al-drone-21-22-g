<template>
    <div class="drone" :style="'left:'+drone.position.latitude+'px; top: '+drone.position.longitude+'px'">
        
    </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import { DroneType } from '../types/types'

@Options({
    props: {
        drone: undefined
    }
})
export default class Drone extends Vue {
    drone!: DroneType
}
</script>

<style lang="scss" scoped>
    .drone{
        position: absolute;
        width: 5px;
        height: 5px;
        background-color: $drone-color-flying;
    }
</style>