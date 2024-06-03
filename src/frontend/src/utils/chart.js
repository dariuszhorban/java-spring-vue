import {
    convertUnixTimeToDate,
    convertToDateString, displayDateAndMinutes, displayDateHour
} from './time'

function getXaxisFormCurrencyPair(currencyPair) {
    return Object.keys(currencyPair)
}

function getXaxisDate(listXaxis, period = '1d') {
    const listUnixDate = getXaxisFormCurrencyPair(listXaxis).map(unixTime => convertUnixTimeToDate(unixTime))
    switch (period) {
        case "30m":
            return listUnixDate.map(unixDate => displayDateAndMinutes(unixDate))
        case "1h":
        case "6h":
        case "12h":
        case "1d":
        case "2d":
            return listUnixDate.map(unixDate => displayDateHour(unixDate))
        default:
            return listUnixDate.map(unixDate => convertToDateString(unixDate))
    }
}
/*
 * format data from xchangeapi to correct order in echart
 */
function formatData(data = [0,1,2,3]) {
    // current data order: min, max, open, close
    return [
        // open
        data[2],
        // close
        data[3],
        // min
        data[0],
        // max
        data[1]
    ]
}

function getBidData(data) {
    return data.map(arr => formatData(arr.slice(4, 8)))
}

function getAskData(data) {
    return data.map(arr => formatData(arr.slice(0, 4)))
}

export { getXaxisFormCurrencyPair, getBidData, getAskData, getXaxisDate}