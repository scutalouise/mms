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