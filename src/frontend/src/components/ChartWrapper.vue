<template>
  <div>
    <v-chart class="chart" :option="bidOption" autoresize />
  </div>
</template>

<script setup>
import { use } from 'echarts/core';
import { CanvasRenderer } from 'echarts/renderers';
import { CandlestickChart } from 'echarts/charts';
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
} from 'echarts/components';
import VChart, { THEME_KEY } from 'vue-echarts';
import { computed, ref, provide, onMounted } from 'vue';
import axios from 'axios';
// local
import { getXaxisFormCurrencyPair, getAskData, getBidData } from '../utils/chart';

use([
  CanvasRenderer,
  CandlestickChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
]);

provide(THEME_KEY, 'dark');

const currencyPair = ref([]);

const bidXaxis = computed(() => {
  return getXaxisFormCurrencyPair(currencyPair.value);
})

const bidSeriesData = computed(() => {
  return getAskData(currencyPair.value);
})

const askSeriesData = computed(() => {
  return getBidData(currencyPair.value);
})

const bidOption = computed(() => ({
  xAxis: [{
    type: 'category',
    data: bidXaxis.value
  }],
  yAxis: [],
  series: [
    {
      type: 'candlestick',
      data: bidSeriesData.value
    }
  ]
})
)


async function fetchData() {
  const currencyPairResponse = await axios.get('api/currency-pair?currencyPair=EURUSD&period=1d');
  currencyPair.value = currencyPairResponse.data;
  console.log(bidOption);
  console.log(askSeriesData.value);
}

onMounted(async () => {
  await fetchData();
})
</script>

<style scoped>
.chart {
  height: 100vh;
}
</style>
