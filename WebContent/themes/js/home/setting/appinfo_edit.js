$(function(){
	_getData();
})

function _getData(){
	var _appId = $('#appId').text();
	var _data = {};
	_data.appId = _appId;
	$.ajax({
		url:'/d/business_appinfo/detail/1_0',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
		type:'post',
		success:function(res){
			res.data.lastSeenTime = toDateTime(res.data.lastSeenTime);
			res.data.createTime = toDateTime(res.data.createTime);
			new Vue({
				el:'#data',
				data:{
					datas:res.data
				}
			})
		}
	})
}

function _save(){
	var _appId = $('#appId').text();
	var _appSecret = $('#appSecret').val();
	var _description = $('#description').val();
	var _data = {};
	_data.appId = _appId;
	_data.appSecret = _appSecret;
	_data.description = _description;
	$.ajax({
		url:'/d/business_appinfo/edit/1_0',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
		type:'post',
		success:function(res){
			if('200'==res.code){
				layer.alert('编辑成功！',function(){
					_open('1','/p/setting/appinfo/list');
				})
			}else{
				layer.alert(res.msg);
			}
		}
	})
}

function _newSecret(){
	$('#appSecret').val(uuid());
}

