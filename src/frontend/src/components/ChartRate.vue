<template>
  <div class="container mt-4">
    <div v-if="errorMessage" class="alert alert-danger" role="alert">
      {{ errorMessage }}
    </div>
    <div class="row mb-3">
      <div class="col-4">
        <div class="text-start">
          <label for="firstCurrency" class="form-label">Currency Pair</label>
        </div>
        <div class="d-flex gap-1">
          <select id="firstCurrency" v-model="firstCurrency" class="form-select">
            <option :disabled="currency.name === lastCurrency" v-for="currency in currencyList" :value="currency.name" :key="currency.name">{{ currency.name }}</option>
          </select>
          <select id="lastCurrency" v-model="lastCurrency" class="form-select">
            <option :disabled="currency.name === firstCurrency" v-for="currency in currencyList" :value="currency.name" :key="currency.name">{{ currency.name }}</option>
          </select>
        </div>
      </div>
      <div class="col-2">
        <div class="text-start">
          <label for="period" class="form-label">Period</label>
        </div>
        <select id="period" v-model="period" class="form-select">
          <option v-for="period in periodList" :value="period.name" :key="period.name">{{ period.detail }}</option>
        </select>
      </div>
      <div class="col d-flex justify-content-center align-items-end">
        <button @click="fetchData" class="btn btn-primary" :disabled="isLoading">
          <span v-if="isLoading" class="spinner-border spinner-border-sm" aria-hidden="true"></span>
          Apply</button>
      </div>
    </div>
    <div class="row mt-4">
      <div class="col">
        <div class="text-center fw-bold">
          <span>{{ chartTitle }}</span>
        </div>
        <v-chart class="chart" :option="bidOption" autoresize />
      </div>
    </div>
  </div>
</template>

<script setup>
import { use } from 'echarts/core'
import { CanvasRenderer } from 'echarts/renderers'
import { BarChart, CandlestickChart } from 'echarts/charts'
import { GridComponent } from 'echarts/components'
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
} from 'echarts/components'
import VChart, { THEME_KEY } from 'vue-echarts'
import { computed, ref, provide, onMounted } from 'vue'
import axios from 'axios'
// local
import {getXaxisFormCurrencyPair, getXaxisDate, getBidData, getAskData} from '@/utils/chart'
import { currencyList } from '@/utils/currency'
import { periodList } from "@/utils/period"

use([
  CanvasRenderer,
  BarChart,
  GridComponent,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  CandlestickChart
])

provide(THEME_KEY, 'light')

/*
 * Data
 */
const firstCurrency = ref('AUD')
const lastCurrency = ref('USD')
const period = ref('1d')
const selectedPeriod = ref(period.value)
const dataResponse = ref({})
const chartTitle = ref('')
const isLoading = ref(false)
const errorMessage = ref('')

/*
 * Computed
 */
const currencyPair = computed(()=> firstCurrency.value + lastCurrency.value)

const xAxisData = computed(() => {
  return getXaxisFormCurrencyPair(dataResponse.value ?? [])
})

const xAxisDateString = computed(() => {
  return getXaxisDate(dataResponse.value ?? [], selectedPeriod.value)
})

const dataAskBid = computed(() => {
  return xAxisData.value.map((xAxisLabel) => dataResponse.value[xAxisLabel])
})

const dataAsk = computed(() => {
  return getAskData(dataAskBid.value)
})

const dataBid = computed(() => {
  return getBidData(dataAskBid.value)
})

const bidOption = computed(() => ({
  height: '500px',
  legend: {
    show: !isLoading.value
  },
  toolbox: {
    show: true,
    feature: {
      dataZoom: {
        yAxisIndex: "none"
      },
      dataView: {
        readOnly: false
      },
      saveAsImage: {}
    }
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      animation: false,
      type: 'cross',
      lineStyle: {
        color: '#376df4',
        width: 2,
        opacity: 1
      }
    }
  },
  xAxis: {
    data: xAxisDateString.value
  },
  yAxis: {
    scale: true,
    axisLine: { lineStyle: { color: '#8392A5' } },
    splitLine: { show: false }
  },
  series: [
    {
      name: 'ask',
      type: 'candlestick',
      data: dataAsk.value,
      itemStyle: {
        color: '#FD1050',
        color0: '#0CF49B',
        borderColor: '#FD1050',
        borderColor0: '#0CF49B'
      }
    },
    {
      name: 'bid',
      type: 'candlestick',
      data: dataBid.value,
      itemStyle: {
        color: '#1F78B4',
        color0: '#A6CEE3',
        borderColor: '#1F78B4',
        borderColor0: '#A6CEE3'
      }
    },
  ]
})
)

/*
 * Method
 */
async function fetchData() {
  isLoading.value = true
  errorMessage.value = ''
  try {
    const response = await axios.get(`api/currency-pair?currencyPair=${currencyPair.value}&period=${period.value}`)
    dataResponse.value = response.data
    selectedPeriod.value = period.value
    chartTitle.value = `${firstCurrency.value}/${lastCurrency.value} Exchange Rate - ${period.value} Chart`
  } catch (error)  {
    errorMessage.value = 'Error when loading data'
  }
  isLoading.value = false
}

/*
 * Lifecycle
 */
onMounted(async () => {
  await fetchData()
})
</script>
<style scoped>
.chart {
  height: 100vh;
}
</style>