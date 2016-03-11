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
	 },
	 validScheduleName:{ //校验定时器名称在当前任务组下面是否重复
		 validator:function(value,param){
			
		
			 $.ajax({
					type : 'POST',
					async : false,
					dateType : 'json',
					url : ctx + '/system/dischargeTask/validScheduleName',
					data : {
						scheduleName : value,
						scheduleGroup:param[0],
						type:$("#"+param[1]).val()
					
					},
					success : function(res) {
						result = res;
					}
				});
			 
				return result;
		 },
		 message:"任务名称已存在"
	 },
	 minValue:{
		 validator: function (value, param) {
			
	           	if($(param[0]).val()!=""){
	           		return $(param[0]).val()>=value;
	           	}
	           	return true;
	          },
	          message: '{1}'

	 },
	 maxValue:{
		 validator: function (value, param) {
			
	           	if($(param[0]).val()!=""){
	           		return $(param[0]).val()<=value;
	           	}
	           	return true;
	          },
	          message: "{1}"

	 },
	 checkPwd : { // 校验密码
			validator : function(value, param) {
			
				var result = false;

				$.ajax({
					type : 'POST',
					async : false,
					dateType : 'json',
					url : ctx + '/system/user/checkPwd',
					data : {
						oldPassword:value
						
					},
					success : function(res) {

						
						if (res == "true") {
							result = true;
						} else {
							res = false;
						}
					}
				});
		
				return result;
			},
			message : '原密码不正确'
		},

});
$.extend($.fn.validatebox.methods, {  
    remove: function(jq, newposition){  
        return jq.each(function(){  
            $(this).removeClass("validatebox-text validatebox-invalid").unbind('focus').unbind('blur');
        });  
    },
    reduce: function(jq, newposition){  
        return jq.each(function(){  
           var opt = $(this).data().validatebox.options;
           $(this).addClass("validatebox-text").validatebox(opt);
        });  
    }   
});

