//扩展easyui表单的验证
$.extend($.fn.validatebox.defaults.rules, {
    //验证汉字
    CHS: {
        validator: function (value) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: '只允许汉字.'
    },
    //移动手机号码验证
    Mobile: {//value值为文本框中的值
        validator: function (value) {
            var reg = /^1[3|4|5|8|9]\d{9}$/;
            return reg.test(value);
        },
        message: '请输入正确的手机号码.'
    },
    //国内邮编验证
    ZipCode: {
        validator: function (value) {
            var reg = /^[0-9]\d{5}$/;
            return reg.test(value);
        },
        message: '邮编错误.'
    },
  //数字
    Number: {
        validator: function (value) {
            var reg =/^[0-9]*.[0-9]*$/;
            return reg.test(value);
        },
        message: '请输入一个数字.'
    },
})

$.extend($.fn.validatebox.methods, {  
    remove: function(jq, newposition){  
        return jq.each(function(){  
            $(this).removeClass("validatebox-text validatebox-invalid").unbind('focus').unbind('blur');
        });  
    },
    reduce: function(jq, newposition){  
        return jq.each(function(){  
           var opt = $(this).validatebox.options;
           $(this).addClass("validatebox-text").validatebox(opt);
        });  
    }   
});