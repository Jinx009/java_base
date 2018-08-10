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
var _a ,_b ,_c,_d,_e,_f,_g,_h;
function _getData() {
	var _userId = getLocalStorage('userId');
	$.ajax({
		url : '/d/order/getSwimmingStatus',
		data : 'date=' + _date + '&userId=' + _userId+'&type=2',
		type : 'post',
		dataType : 'json',
		success : function(res) {
			_a = res.data.a;
		    _b = res.data.b;
			_c = res.data.c;
			_d = res.data.d;
			_e = res.data.e;
			_f = res.data.f;
			_g = res.data.g;
			_h = res.data.h;
			if (_a == 0) {
				$('#time1').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('09:00:00~10:30:00 剩余名额：0 ');
			}else if (_a == 1000) {
				$('#time1').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('09:00:00~10:30:00 [已预约]');
			}else{
				$('#time1').removeClass('disabled').addClass('button-danger').attr('onclick', '_save(1)').html('09:00:00~10:30:00 剩余名额：'+_a+' [点击预约]');
			}
			if (_b == 0) {
				$('#time2').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('10:30:00~12:00:00 剩余名额：0 ');
			}else if (_b == 1000) {
				$('#time2').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('10:30:00~12:00:00 [已预约]');
			}else{
				$('#time2').removeClass('disabled').addClass('button-danger').attr('onclick', '_save(1)').html('10:30:00~12:00:00 剩余名额：'+_b+' [点击预约]');
			}
			if (_c == 0) {
				$('#time3').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('12:00:00~13:30:00 剩余名额：0 ');
			}else if (_c == 1000) {
				$('#time3').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('12:00:00~13:30:00 [已预约]');
			}else{
				$('#time3').removeClass('disabled').addClass('button-danger').attr('onclick', '_save(1)').html('12:00:00~13:30:00 剩余名额：'+_c+' [点击预约]');
			}
			if (_d == 0) {
				$('#time4').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('13:30:00~15:00:00 剩余名额：0 ');
			}else if (_d == 1000) {
				$('#time4').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('13:30:00~15:00:00 [已预约]');
			}else{
				$('#time4').removeClass('disabled').addClass('button-danger').attr('onclick', '_save(1)').html('13:30:00~15:00:00 剩余名额：'+_d+' [点击预约]');
			}
			if (_e == 0) {
				$('#time5').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('15:00:00~16:30:00 剩余名额：0 ');
			}else if (_e == 1000) {
				$('#time5').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('15:00:00~16:30:00 [已预约]');
			}else{
				$('#time5').removeClass('disabled').addClass('button-danger').attr('onclick', '_save(1)').html('15:00:00~16:30:00 剩余名额：'+_e+' [点击预约]');
			}
			if (_f == 0) {
				$('#time6').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('16:30:00~18:00:00 剩余名额：0 ');
			}else if (_f == 1000) {
				$('#time6').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('16:30:00~18:00:00 [已预约]');
			}else{
				$('#time6').removeClass('disabled').addClass('button-danger').attr('onclick', '_save(1)').html('16:30:00~18:00:00 剩余名额：'+_f+' [点击预约]');
			}
			if (_g == 0) {
				$('#time7').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('18:00:00~19:30:00 剩余名额：0 ');
			}else if (_g == 1000) {
				$('#time7').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('18:00:00~19:30:00 [已预约]');
			}else{
				$('#time7').removeClass('disabled').addClass('button-danger').attr('onclick', '_save(1)').html('18:00:00~19:30:00 剩余名额：'+_g+' [点击预约]');
			}
			if (_h == 0) {
				$('#time8').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('19:30:00~21:00:00 剩余名额：0 ');
			}else if (_h == 1000) {
				$('#time8').removeClass('button-danger').addClass('disabled').attr('onclick', '').html('19:30:00~21:00:00 [已预约]');
			}else{
				$('#time8').removeClass('disabled').addClass('button-danger').attr('onclick', '_save(1)').html('19:30:00~21:00:00 剩余名额：'+_h+' [点击预约]');
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
	if(_time = 4 ){
		_num_ = parseInt(_d) - _num_;
	}
	if(_time = 5 ){
		_num_ = parseInt(_e) - _num_;
	}
	if(_time = 6 ){
		_num_ = parseInt(_f) - _num_;
	}
	if(_time = 7 ){
		_num_ = parseInt(_g) - _num_;
	}
	if(_time = 8 ){
		_num_ = parseInt(_h) - _num_;
	}
	var _userId = getLocalStorage('userId');
	if(_userId==''){
		setLocalStorage('_url','/f/p/pro_swimming_pool');
		location.href = '/f/p/register';
	}else{
		var _type = getLocalStorage('type');
		if(_type!=2&&_type!=3&&_type!=5){
			  layer.open({
			    content: '只有俱乐部、游泳教练才可以预约潜水池！'
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
				    content: '您确定要预约此时段游泳池吗？'
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