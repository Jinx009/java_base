$(function(){
	_getData();
})

function _getData(){
	var _id = $('#id').text();
	var _data = {};
	_data.id = _id;
	$.ajax({
		url:'/d/business_area/detail/1_0',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
		type:'post',
		success:function(res){
			res.data.lastSeenTime = toDateTime(res.data.lastSeenTime);
			res.data.createTime = toDateTime(res.data.createTime);
			var _locationId = res.data.locationId;
			_getLocations(_locationId);
			new Vue({
				el:'#data',
				data:{
					datas:res.data
				}
			})
		}
	})
}

function _edit(){
	var _id = $('#id').text();
	var _name = $('#name').val();
	var _description = $('#description').val();
	var _locationId = $('#locationId').val();
	var _data = {};
	_data.id = _id;
	_data.locationId = _locationId;
	_data.description = _description;
	_data.recSt = 1;
	_data.name = _name;
	$.ajax({
		url:'/d/business_area/edit/1_0',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
		type:'post',
		success:function(res){
			if('200'==res.code){
				layer.alert('编辑成功！',function(){
					_open('1','/p/setting/area/list');
				})
			}else{
				layer.alert(res.msg);
			}
		}
	})
}

function _getLocations(_locationId){
	$.ajax({
		url:'/d/business_location/all/1_0',
		dataType:'json',
		contentType:'application/json;charSet=utf8',
		type:'post',
		success:function(res){
			var _htmlStr = ''
			for(var i in res.data){
				if(_locationId==res.data[i].id){
					_htmlStr += '<option value="'+res.data[i].id+'" selected="selected" >'+res.data[i].name+'</option>';
				}else{
					_htmlStr += '<option value="'+res.data[i].id+'" >'+res.data[i].name+'</option>';
				}
			}
			$('#locationId').html(_htmlStr);
		}
	})
}
