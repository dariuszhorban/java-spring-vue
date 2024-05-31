<template>
  <div>
    <v-chart class="chart" :option="bidOption" autoresize />
  </div>
</template>

<script setup>
import { use } from 'echarts/core';
import { CanvasRenderer } from 'echarts/renderers';
import { BarChart } from 'echarts/charts';
import { GridComponent } from 'echarts/components';
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
} from 'echarts/components';
import VChart, { THEME_KEY } from 'vue-echarts';
import { computed, ref, provide, onMounted } from 'vue';
import axios from 'axios';
// local
import { getXaxisFormCurrencyPair } from '../utils/chart';

use([
  CanvasRenderer,
  BarChart,
  GridComponent,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
]);

provide(THEME_KEY, 'light');

const dataResponse = ref([{
  rates: []
}]);


const xAxisData = computed(() => {
  console.log(dataResponse.value);
  return getXaxisFormCurrencyPair(dataResponse.value?.rates ?? []);
})

const seriesData = computed(() => {
  return xAxisData.value.map((xAxisLabel) => dataResponse.value.rates[xAxisLabel]);
})

const bidOption = computed(() => ({
  xAxis: {
    type: 'category',
    data: xAxisData.value
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: seriesData.value,
      type: 'bar'
    }
  ]
})
)


async function fetchData() {
  const response = await axios.get('api/exchange-rates');
  console.log(response.data);
  dataResponse.value = response.data;
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