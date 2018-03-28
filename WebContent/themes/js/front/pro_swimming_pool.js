$(function() {
	$("#my-input").calendar({
		minDate : _date
	});
	_date = _newDate();
	_getData();
})

var _date = _newDate_1();
function check() {
	var _year = $('.picker-calendar-day-selected').attr('data-year');
	var _month = $('.picker-calendar-day-selected').attr('data-month');
	var _day = $('.picker-calendar-day-selected').attr('data-day');
	_month = parseInt(_month) + 1;
	if (_month < 10) {
		_month = '0' + _month;
	}
	_date = _year + '-' + _month + '-' + _day;
	_getData();
}
function _getData() {
	var _userId = getLocalStorage('userId');
	$.ajax({
		url : '/d/order/getStatus',
		data : 'date=' + _date + '&userId=' + _userId+'&type=2',
		type : 'post',
		dataType : 'json',
		success : function(res) {
			var _a = res.data.a;
			var _b = res.data.b;
			var _c = res.data.c;
			if (_a == 0) {
				$('#time1').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('上午 [已满]');
			}
			if (_a == 2) {
				$('#time1').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('上午 [已预订]');
			}
			if (_a == 1) {
				$('#time1').removeClass('disabled').addClass('button-danger').attr('onclick', '_save(1)').html('上午 [可预约]');
			}
			if (_b == 0) {
				$('#time2').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('下午 [已满]');
			}
			if (_b == 2) {
				$('#time2').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('下午 [已预定]');
			}
			if (_b == 1) {
				$('#time2').removeClass('disabled').addClass('button-danger').attr('onclick', '_save(2)').html('下午 [可预约]');
			}
			if (_c == 0) {
				$('#time3').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('夜间 [已满]');
			}
			if (_c == 2) {
				$('#time3').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('夜间 [已预约]');
			}
			if (_c == 1) {
				$('#time3').removeClass('disabled').addClass('button-danger').attr('onclick', '_save(3)').html('夜间 [可预约]');
			}
		}
	})
}
function _save(_orderTime) {
	var _userId = getLocalStorage('userId');
	if(_userId==''){
		setLocalStorage('_url','/f/p/pro_class_room');
		location.href = '/f/p/login';
	}else{
		var _type = getLocalStorage('type');
		if(1==2){
			  layer.open({
			    content: '只有企业或者教练才可以预定教室！'
			    ,btn: '我知道了'
			  });
		}else{
			var _num = $('#num').val();
			var reg = /^[1-9]\d*$/;
			if(!reg.test(_num)){
				layer.open({
				    content: '请输入正确的随行人数！'
				    ,btn: '我知道了'
				  });
			}else{
				layer.open({
				    content: '您确定要预约此时段泳池吗？'
				    ,btn: ['确定', '取消']
				    ,skin: 'footer'
				    ,yes: function(index){
				       var _params = 'type=2&orderType='+_orderTime+'&userId='+_userId+'&num='+_num+'&orderDate='+_date;
				       $.ajax({
				    	   url:'/d/order/save',
				    	   data:_params,
				    	   dataType:'json',
				    	   type:'post',
				    	   success:function(res){
				    		   if(res.code=='200'){
				    			   layer.open({
				   				    content: '恭喜您预约成功！'
				   				    ,btn: '好',
				   				    yes:function(index){
				   				    	location.href = '/f/p/pro_order';
				   				    }
				   				  });
				    		   }
				    	   }
				       })
				    }
				 });
			}
		}
	}
}