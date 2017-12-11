$(function(){
	_getData();
})

function _getData(){
	var _id = $('#id').text();
	var _data = {};
	_data.id = _id;
	$.ajax({
		url:'/d/business_location/detail/1_0',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
		type:'post',
		success:function(res){
			res.data.lastSeenTime = toDateTime(res.data.lastSeenTime);
			res.data.createTime = toDateTime(res.data.createTime);
			var _appInfoId = res.data.appInfoId;
			var _noticeType = res.data.noticeType;
			_getApps(_appInfoId);
			_getNoticeType(_noticeType);
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
	var _appInfoId = $('#appInfoId').val();
	var _noticeType = $('#noticeType').val();
	var _data = {};
	_data.id = _id;
	_data.description = _description;
	_data.recSt = 1;
	_data.name = _name;
	_data.appInfoId = _appInfoId;
	_data.noticeType = _noticeType;
	$.ajax({
		url:'/d/business_location/edit/1_0',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
		type:'post',
		success:function(res){
			if('200'==res.code){
				layer.alert('编辑成功！',function(){
					_open('1','/p/setting/location/list');
				})
			}else{
				layer.alert(res.msg);
			}
		}
	})
}

function _getApps(_appInfoId){
	$.ajax({
		url:'/d/business_appinfo/all/1_0',
		dataType:'json',
		contentType:'application/json;charSet=utf8',
		type:'post',
		success:function(res){
			var _htmlStr = '<option value="0" >请选择AppInfo</option>'
			for(var i in res.data){
				if(_appInfoId==res.data[i].id){
					_htmlStr += '<option value="'+res.data[i].id+'" selected="selected" >'+res.data[i].description+'</option>';
				}else{
					_htmlStr += '<option value="'+res.data[i].id+'" >'+res.data[i].description+'</option>';
				}
			}
			$('#appInfoId').html(_htmlStr);
		}
	})
}
function _getNoticeType(_noticeType){
	var _htmlStr = '';
	for(var i = 0;i<4;i++){
		if(_noticeType==i){
			_htmlStr += '<option value="'+i+'" selected="selected" >'+_getTypeName(i)+'</option>';
		}else{
			_htmlStr += '<option value="'+i+'" >'+_getTypeName(i)+'</option>';
		}
	}
	$('#noticeType').html(_htmlStr);
}

function _getTypeName(_index){
	if(0==_index){
		return '不推送';
	}else if(1==_index){
		return '推送车检';
	}else if(2==_index){
		return '推送心跳';
	}else if(3==_index){
		return '双通道推送';
	}
	return '';	
}

