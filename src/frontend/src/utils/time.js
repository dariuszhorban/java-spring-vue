function convertUnixTimeToDate(unixTime) {
  return new Date(unixTime * 1000)
}

function convertToDateString(date) {
  return new Date(date).toISOString().slice(0,10)
}

// General function to format date with custom hour and minute
function formatDate(date, hour, minute) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are zero-based
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(hour).padStart(2, '0');
  const minutes = String(minute).padStart(2, '0');
  return `${year}-${month}-${day} ${hours}:${minutes}`;
}

// Function to format date to 'YYYY-MM-DD HH:00'
function displayDateHour(date) {
  return formatDate(date, date.getHours(), 0);
}

// Function to format date to 'YYYY-MM-DD HH:MM'
function displayDateAndMinutes(date) {
  return formatDate(date, date.getHours(), date.getMinutes());
}

export { convertUnixTimeToDate, convertToDateString, displayDateHour, displayDateAndMinutes }