$.extend($.fn.validatebox.defaults.rules, {
	validIp : { // 验证主机名称是否可用
		validator : function(value, param) {
			var val = param[0], type = param[1];
			if (type) {
				switch (String(type).toLowerCase()) {
				case "jquery":
				case "dom":
					val = $(val).val();
					break;
				case "id":
					val = $("#" + val).val();
					break;
				case "string":
				default:
					break;
				}
			}
			var result = false;

			$.ajax({
				type : 'POST',
				async : false,
				dateType : 'json',
				url : ctx + '/gitInfo/validVailable',
				data : {
					ip : value,
					gitInfoId : val
				},
				success : function(res) {

					result = res;
				}
			});
			return result;
		},
		message : 'ip已经被使用'
	},
	 checkIp : {// 验证IP地址  
		           validator : function(value) {  
		               var reg = /^((1?\d?\d|(2([0-4]\d|5[0-5])))\.){3}(1?\d?\d|(2([0-4]\d|5[0-5])))$/ ;  
		               return reg.test(value);  
		           },  
		            message : 'IP地址格式不正确' 
	 }

});