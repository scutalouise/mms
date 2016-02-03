$(function() {
	jQuery.extend(jQuery.validator.messages, {
		required : window.parent.i18nMsg.required,
		minlength: jQuery.validator.format(window.parent.i18nMsg.password + window.parent.i18nMsg.minlength + "{0}" + window.parent.i18nMsg.chars),
		email:window.parent.i18nMsg.enter + window.parent.i18nMsg.correctly + window.parent.i18nMsg.email,
		digits:window.parent.i18nMsg.integer,
		maxlength:jQuery.format(window.parent.i18nMsg.maxlength + "{0}"),
		max: jQuery.format(window.parent.i18nMsg.maxvalue + "{0} " + window.parent.i18nMsg.value),
		range:jQuery.format(window.parent.i18nMsg.range_head + " {0} " + window.parent.i18nMsg.range_and + " {1} " + window.parent.i18nMsg.range_end)
	});
	jQuery.validator.addMethod("isMobile", function(value, element) {
		//var length = value.length;
		var mobile = /((^(\d{11})$)|(^(\d{12})$)|^((\d{7,8})|(\d{4}|\d{3})[-](\d{7,8})|(\d{4}|\d{3})[-](\d{7,8})[-](\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})[-](\d{4}|\d{3}|\d{2}|\d{1}))$)/;
		return this.optional(element) || mobile.test(value);
	}, window.parent.i18nMsg.enter + window.parent.i18nMsg.correctly + window.parent.i18nMsg.tels);
	jQuery.validator.addMethod("isEmail", function(value,element){
		var email=/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
		return this.optional(element)||email.test(true);
	},window.parent.i18nMsg.enter + window.parent.i18nMsg.correctly + window.parent.i18nMsg.email);
	jQuery.validator.addMethod("filterDBcom", function(value, element) {
		var command = /’|"|'|=|;|>|<|%/i;
		return this.optional(element) || !(command.test(value));
	}, window.parent.i18nMsg.illegalchars);
	jQuery.validator.addMethod("filterHTML", function(value, element) {
		var chrnum = /<[^>]+>/;
		return this.optional(element) || !(chrnum.test(value));
	}, window.parent.i18nMsg.illegalchars);
	
	jQuery.validator.addMethod("absolutePath", function(value, element) {
		var chrnum = /(\/\w+)+/;
		return this.optional(element) || (chrnum.test(value));
	}, window.parent.i18nMsg.illegalchars);
	
	jQuery.validator.addMethod("isIp", function(value, element) {
		var chrnum = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
		return this.optional(element) || chrnum.test(value);
	}, window.parent.i18nMsg.enter + window.parent.i18nMsg.correctly + window.parent.i18nMsg.ipaddress);
	jQuery.validator.addMethod("haveBlank", function(value, element) {
		var is = value.indexOf(" ") >= 0?false:true;
		return this.optional(element) || is;
	}, window.parent.i18nMsg.cannotcontainspaces);
	
	jQuery.validator.addMethod("mustContain", function(value, element) {
		var contain1 = /[0-9]+/;
		var contain2 = /[a-z]+/;
		var contain3 = /[A-Z]+/;
		return this.optional(element) || (contain1.test(value) && contain2.test(value) && contain3.test(value)) ;
	}, window.parent.i18nMsg.must);
	$.validator.addMethod("checkinput", function(value, element) {
		var chrnum = /^([a-zA-Z0-9]|[._]){1,255}$/;
		return this.optional(element) || (chrnum.test(value));
	}, window.parent.i18nMsg.illegalchars);
	
	jQuery.validator.addMethod("maxrang",function(value,element,params){
		if(params.indexOf("d_",0) > -1){
			$("#d_maxrangstr").text("");
			if(isGreater[0]){
				if(parseFloat($("#"+params).val())<=parseFloat(value)&&$("#"+params).val()!=""){
					/*$("#d_maxrangstr").text(d_maxrang);*/
					return false;
				}else{
					return true;
				}
			}else{
				if(parseFloat($("#"+params).val())>=parseFloat(value)&&$("#"+params).val()!=""){
					/*$("#d_maxrangstr").text(d_maxrang);*/
					return false;
				}else{
					return true;
				}
			}
		}else{
			$("#e_maxrangstr").text("");
			if(isGreater[1]){
				if(parseFloat($("#"+params).val())<=parseFloat(value)&&$("#"+params).val()!=""){
					/*$("#e_maxrangstr").text(e_maxrang);*/
					return false;
				}else{
					return true;
				}
			}else{
				if(parseFloat($("#"+params).val())>=parseFloat(value)&&$("#"+params).val()!=""){
					/*$("#e_maxrangstr").text(e_maxrang);*/
					return false;
				}else{
					return true;
				}
			}
		}
	},"");
	jQuery.validator.addMethod("minrang",function(value,element,params){
		if(params.indexOf("d_",0) > -1){
			$("#d_minrangstr").text("");
			if(isGreater[0]){
				if(parseFloat($("#"+params).val())>=parseFloat(value)&&$("#"+params).val()!=""){
					/*$("#d_minrangstr").text(d_minrang);*/
					return false;
				}else{
					return true;
				}
			}else{
				if(parseFloat($("#"+params).val())<=parseFloat(value)&&$("#"+params).val()!=""){
					/*$("#d_minrangstr").text(d_minrang);*/
					return false;
				}else{
					return true;
				}
			}
		}else{
			$("#e_minrangstr").text("");
			if(isGreater[1]){
				if(parseFloat($("#"+params).val())>=parseFloat(value)&&$("#"+params).val()!=""){
					/*$("#e_minrangstr").text(e_minrang);*/
					return false;
				}else{
					return true;
				}
			}else{
				if(parseFloat($("#"+params).val())<=parseFloat(value)&&$("#"+params).val()!=""){
					/*$("#e_minrangstr").text(e_minrang);*/
					return false;
				}else{
					return true;
				}
			}
		}
		
	},"");
	$.validator.setDefaults({
		errorElement : "label"
	});
});
//扩展验证两位小数点的数字
$(function() {
    jQuery.validator.addMethod("twoDecimalPointOfNumber",  function(value) {
        var params = /^\d+(\.\d{1,2})?$/;
        var exp = new RegExp(params);
        return exp.test(value);
    });
});
//扩展验证正整数
$(function() {
    jQuery.validator.addMethod("NonNegative",  function(value) {
        var params = "^\\d+$";
        var exp = new RegExp(params);
        return exp.test(value);
    });
});
//特殊字符
$(function() {
    jQuery.validator.addMethod("specialCharacters",  function(value) {
        var params =/^([\u4E00-\u9FA5]|\w)*$/;
        var exp = new RegExp(params);
        return exp.test(value);
    });
    
});
//不包含分号的特殊字符
jQuery.validator.addMethod("filterDBcon", function(value, element) {
	var command = /’|"|'|=|>|<|%/i;
	return this.optional(element) || !(command.test(value));
}, window.parent.i18nMsg.illegalchars);
