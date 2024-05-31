function getXaxisFormCurrencyPair(currencyPair) {
    return Object.keys(currencyPair);
}

function getBidData(currencyPair) {
    return Object.values(currencyPair).map(arr => arr.slice(4, 8));

}

function getAskData(currencyPair) {
    return Object.values(currencyPair).map(arr => arr.slice(0, 4));
}

export { getXaxisFormCurrencyPair, getBidData, getAskData};