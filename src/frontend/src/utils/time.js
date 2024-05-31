function convertUnixTimeToDate(unixTime) {
  return new Date(unixTime * 1000);
}

export { convertUnixTimeToDate };