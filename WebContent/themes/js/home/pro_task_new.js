$(function(){
	$('#pickDate').val(new Date().Format('yyyy-MM-dd'));
	$('#pickTime').val('23:59:59');
	laydate.render({
	  elem: '#pickDate'
	});
	laydate.render({
	  elem: '#pickTime'
	  ,type: 'time'
	});
	_getSelect();
})
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
function _getSelect(){
	$.ajax({
		url:'/home/d/pro_driver/selectList',
		type:'post',
		dataType:'json',
		success:function(res){
			new Vue({
				el:'#driver',
				data:{
					drivers:res.data
				}
			})
		}
	})
}

function _save(){
	var _name = $('#name').val();
	var _mobilePhone = $('#mobilePhone').val();
	var _number = $('#number').val();
	var _dep = $('#dep').val();
	var _flight = $('#flight').val();
	var _description = $('#description').val();
	var _driverMobile = $('#driver').val();
	var _driverName=$("#driver").find("option:selected").text();
	var _pickDate = $('#pickDate').val();
	var _pickTime = $('#pickTime').val();
	var _params = 'name='+_name+'&mobilePhone='+_mobilePhone+'&dep='+_dep+'&number='+_number+'&description='+_description+'&flight='+
	_flight+'&driverMobile='+_driverMobile+'&driverName='+_driverName+'&pickDate='+_pickDate+'&pickTime='+_pickTime;
	if(_name==''){
		layer.alert('待接人员姓名不能为空！');
	}else{
		$.ajax({
			url:'/home/d/pro_task/save',
			type:'post',
			data:_params,
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					layer.alert('添加成功！',function(){
						_open('l1','/home/p/pro_task');
					})
				}else{
					layer.alert(res.msg);
				}
			}
		})
	}
}
