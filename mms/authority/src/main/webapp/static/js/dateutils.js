/**
 * 传入长整数的时间，转换成对应格式的日期类型 
 * @param {Object} data
 * @param {Object} pattern
 */
function formatDate(data,pattern) {
	if (data != null) {
		var time = new Date(data);
		var nowStr = time.format(pattern);
		return nowStr;
	} else {
		return "";
	}
}

/**
 * 对日期按天数进行加减计算
 * @param dateStr
 * @param days
 * @param pattern
 * @returns
 */
function addDays(dateStr, days, pattern) {
	if (dateStr != null && dateStr != "") {
		var date = new Date(dateStr);
		date = date.valueOf();
		date = date + days * 24 * 60 * 60 * 1000;
		date = new Date(date)
		return formatDate(date,pattern);
	}
	return "";
}