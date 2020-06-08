$(function() {
	laydate.render({
		elem : '#date',
		range : true
	});
	checkWeek();
})
function checkWeek(){
	var week = $('#week').val();
	if(week==0||week==6){
		$('#time1').show();
		$('#time').hide();
	}else{
		$('#time').show();
		$('#time1').hide();
	}
}
function _save() {
	var date = $('#date').val();
	var address = $('#address').val();
	var _mobilePhone = $('#mobilePhone').val();
	var _userName = $('#userName').val();
	var week = $('#week').val();
	var time = $('#time').val();
	if(week==0||week==6){
		time = $('#time1').val();
	}
	var remark = $('#remark').val();
	var _params = 'address=' + address + '&date=' + date + '&mobilePhone='
			+ _mobilePhone + '&userName=' + _userName + '&time=' + time+'&week='+week;
	if (date == '') {
		layer.alert('请选择日期！');
	} else {
		$.ajax({
			url : '/h/pro_order_add/save',
			type : 'post',
			data : _params,
			dataType : 'json',
			success : function(res) {
				if ('200' == res.code) {
					layer.alert('添加成功！', function() {
						_openC('l5', '/home/p/pro_order_add_list');
					})
				} else {
					layer.alert(res.msg);
				}
			}
		})
	}
}
function testNumber(_num) {
	var reg = new RegExp("^[0-9]*$");
	if (reg.test(_num) && _num.length == 11) {
		return true;
	}
	return false;
}
