$(function() {
	var type = $('#_type').val();
	if (type == 1) {
		$('#type').val('10:00至14:00');
	}
	if (type == 2) {
		$('#type').val('14:00至18:00');
	}
	if (type == 3) {
		$('#type').val('18:00至22:00');
	}
	_getData();
})
function _getData() {
	var dateStr = $('#_dateStr').val();
	var _userId = getLocalStorage('userId');
	var _type = $('#_type').val();
	var _userType = getLocalStorage('type');
	$.ajax({
		url : '/d/order/getButtons',
		data : 'userId=' + _userId + '&userType=' + _userType + '&orderDate='
				+ dateStr + '&orderTime=' + _type,
		type : 'post',
		dataType : 'json',
		success : function(res) {
			if ('200' == res.code) {
				if (res.data.zyq == '0') {
					$('#zyq').removeClass('button-warning');
					$('#zyq').addClass('disabled');
				} else {
					$('#zyq').attr('onclick', '_setType("zyq")');
				}
				if (res.data.sfq == '0') {
					$('#sfq').removeClass('button-warning');
					$('#sfq').addClass('disabled');
				} else {
					$('#sfq').attr('onclick', '_setType("sfq")');
				}
				if (res.data.yx == '0') {
					$('#yx').removeClass('button-warning');
					$('#yx').addClass('disabled');
				} else {
					$('#yx').attr('onclick', '_setType("yx")');
				}
				if (res.data.js == '0') {
					$('#js').removeClass('button-warning');
					$('#js').addClass('disabled');
				} else {
					$('#js').attr('onclick', '_setType("js")');
				}
			}
		}
	})
}
function _save(){
	if(_t==''){
		layer.open({
		    content: '您没有任何可选科目！'
		    ,btn: '好'
		  });
	}
	var dateStr = $('#_dateStr').val();
	var _userId = getLocalStorage('userId');
	var _type = $('#_type').val();
	var _userType = getLocalStorage('type');
	var num = $('#num').val();
	var remark = $('#remark').val();
	var _params = 'userId=' + _userId + '&userType=' + _userType + '&orderDate='
	+ dateStr + '&orderTime=' + _type+'&num='+num+'&reamrk='+remark+'&type='+_t;
	 $.ajax({
		   url:'/d/order/save',
		   data:_params,
		   dataType:'json',
		   type:'post',
		   success:function(res){
			   if('200'==res.code){
//				   layer.open({
//					   content:'预约成功！',
//					   btn:'好'
//				   })
				   location.href = '/f/p/pro_order';
			   }else{
				   layer.open({
					   content:res.msg,
					   btn:'好'
				   })
			   }
		   }
	   })
}
var _t = '';
function _setType(type){
	if('zyq'==type){
		_t = 1;
	}
	if('sfq'==type){
		_t = 2;
	}
	if('yx'==type){
		_t = 3;
	}
	if('js'==type){
		_t = 4;
	}
	$('.bb').removeClass('button-success');
	$('.bb').addClass('button-warning');
	$('#'+type).removeClass('button-warning');
	$('#'+type).addClass('button-success');
}