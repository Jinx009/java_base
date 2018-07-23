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
var _a ,_b ,_c;
function _getData() {
	var _userId = getLocalStorage('userId');
	$.ajax({
		url : '/d/order/getStatus',
		data : 'date=' + _date + '&userId=' + _userId+'&type=1',
		type : 'post',
		dataType : 'json',
		success : function(res) {
			_a = res.data.a;
		    _b = res.data.b;
			_c = res.data.c;
			if (_a == 0) {
				$('#time1').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('上午 剩余名额：0 [已满]');
			}else if (_a == 1000) {
				$('#time1').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('上午 [您已经预约过该时间段]');
			}else{
				$('#time1').removeClass('disabled').addClass('button-danger').attr('onclick', '_save(1)').html('上午 剩余名额：'+_a+' [点击预约]');
			}
			if (_b == 0) {
				$('#time2').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('下午 剩余名额：0 [已满]');
			}else if (_b == 1000) {
				$('#time2').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('下午 [您已经预约过该时间段]');
			}else{
				$('#time2').removeClass('disabled').addClass('button-danger').attr('onclick', '_save(2)').html('下午 剩余名额：'+_b+' [点击预约]');
			}
			if (_c == 0) {
				$('#time3').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('夜间 剩余名额：0 [已满]');
			}else if (_c == 1000) {
				$('#time3').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('夜间 [您已经预约过该时间段]');
			}else{
				$('#time3').removeClass('disabled').addClass('button-danger').attr('onclick', '_save(3)').html('夜间 剩余名额：'+_c+' [点击预约]');
			}
		}
	})
}
function _save(_orderTime) {
	var _num = $('#num').val();
	var _num_ = 0;
	var _time = parseInt(_orderTime);
	if(_time = 1 ){
		_num_ = parseInt(_a) - _num_;
	}
	if(_time = 2 ){
		_num_ = parseInt(_b) - _num_;
	}
	if(_time = 3 ){
		_num_ = parseInt(_c) - _num_;
	}
	var _userId = getLocalStorage('userId');
	if(_userId==''){
		setLocalStorage('_url','/f/p/pro_diving');
		location.href = '/f/p/login';
	}else{
		var _type = getLocalStorage('type');
		if(_type!=2&&_type!=3&&_type!=5){
			  layer.open({
			    content: '只有会员、俱乐部、潜水教练才可以预约潜水池！'
			    ,btn: '我知道了'
			  });
		}else if(_num_<0){
			layer.open({
			    content: '随行人数不应大于剩余名额！'
			    ,btn: '我知道了'
			  });
		}else{
			var reg = /^[1-9]\d*$/;
			if(!reg.test(_num)){
				layer.open({
				    content: '请输入正确的随行人数！'
				    ,btn: '我知道了'
				  });
			}else{
				layer.open({
				    content: '您确定要预约此时段潜水池吗？'
				    ,btn: ['确定', '取消']
				    ,skin: 'footer'
				    ,yes: function(index){
				       var _params = 'type=1&orderType='+_orderTime+'&userId='+_userId+'&num='+_num+'&orderDate='+_date;
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