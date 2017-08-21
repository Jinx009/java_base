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
	var _params = 'mac='+$('#mac').val()+
				  '&code='+$('#code').val()+
				  '&place='+$('#place').val()+
				  '&remark='+$('#remark').val();
	$.ajax({
		url:'/home/d/insert',
		type:'post',
		dataType:'json',
		data:_params,
		success:function(res){
			console.log(res);
		}
	})
}
function changeValue(){
	var _value = $('#place').val();
	if(_value!=''&&_value!=null){
		for(var i in _places){
			if(_places[i].parkPlaceId==_value){
				$('#mac').val(_places[i].magneticStripeId);
				$('#code').val(_places[i].code);
				$('#remark').val(_places[i].remark);
				break;
			}
		}
	}else{
		$('#mac').val('');
		$('#code').val('');
		$('#remark').val('');
	}
}