$(function(){
	_i = showLoad();
	getData();
})
var _i = -1,_places = [];
function getData(){
	if(-1==_i){
		_i = showLoad();
	}
	$.ajax({
		url:'/home/d/place',
		type:'post',
		dataType:'json',
		success:function(res){
			closeLoad(_i);
			if(res.data!=null){
				_places = res.data.resultData.parkPlaceList;
				var _html = '';
				for(var i in res.data.resultData.parkPlaceList){
					_html += '<option value="'+res.data.resultData.parkPlaceList[i].parkPlaceId+'" >'+res.data.resultData.parkPlaceList[i].parkPlaceId+'</option>';
				}
				$('#place').append(_html);
			}else{
				layer.alert('暂无数据！');
			}
		}
	})
}
function save(){
	var _place = $('#place').val(),_code = $('#code').val(),_mac = $('#mac').val(),_remark = $('#remark').val();
	var re = /^[0-9]+$/;
	if(realStatus){
		_place = $('#realPlace').val();
	}
	var _params = 'mac='+_mac+
				  '&code='+_code+
				  '&place='+_place+
				  '&remark='+_remark;
	if(_place==null||''==_place){
		layer.alert('placeId不能为空！');
	}else if(!re.test(_place)){
		layer.alert('placeId必须为正整数！');
	}else if(_mac==null||''==_mac){
		layer.alert('对应地磁mac号码不能为空!');
	}else if(_code==null||''==_code){
		layer.alert('车位显示地址不能为空！');
	}else{
		$.ajax({
			url:'/home/d/insert',
			type:'post',
			dataType:'json',
			data:_params,
			success:function(res){
				if(res.data&&'200'==res.data.status){
					layer.alert('操作成功！',function(){
						location.href = '/home/p/place';
					});
				}
			}
		})
	}
}
var realStatus = true;
function changeValue(){
	var _value = $('#place').val();
	if(_value!=''&&_value!=null){
		realStatus = false;
		$('#realPlace').hide();
		for(var i in _places){
			if(_places[i].parkPlaceId==_value){
				$('#mac').val(_places[i].magneticStripeId);
				$('#code').val(_places[i].code);
				$('#remark').val(_places[i].remark);
				break;
			}
		}
	}else{
		$('#realPlace').show();
		realStatus = true;
		$('#mac').val('');
		$('#code').val('');
		$('#remark').val('');
	}
}