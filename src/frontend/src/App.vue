<template>
  <ChartRate />
  <!-- <ChartWrapper /> -->
</template>

<script>
// import ChartWrapper from './components/ChartWrapper.vue'
import ChartRate from './components/ChartRate.vue'

export default {
  name: 'App',
  components: {
    // ChartWrapper,
    ChartRate
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
