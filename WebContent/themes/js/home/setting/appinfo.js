$(function(){
	_getData(0,1);
})
var _d = '';
var  _nowPage = 0,_max = 0;
function _getData(_type,_index){
	var _data = {};
	_data.p = _getPage(_type,_index);
	if(_data.p!=-1){
		$.ajax({
			url:'/d/business_appinfo/list/1_0',
			dataType:'json',
			data:JSON.stringify(_data),
			contentType:'application/json;charSet=utf8',
			type:'post',
			success:function(res){
				$('#_end').bind('click',function(){
					_getData(0,res.data.page.pages);
				})
				_max = res.data.page.pages;
				for(var i in res.data.list){
					res.data.list[i].createTime = toDateTime(res.data.list[i].createTime);
				}
				if(''==_d){
					_d = new Vue({
						el:'#datas',
						data:{
							datas:res.data
						}
					})
				}else{
					_d.datas = res.data
				}
			}
		})
	}
}

function _save(){
	var _appId = $('#appId').val();
	var _appSecret = $('#appSecret').val();
	var _description = $('#description').val();
	var _data = {};
	_data.appId = _appId;
	_data.appSecret = _appSecret;
	_data.description = _description;
	$.ajax({
		url:'/d/business_appinfo/create/1_0',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
		type:'post',
		success:function(res){
			if('200'==res.code){
				layer.alert('创建成功！',function(){
					location.reload();
				})
			}else{
				layer.alert(res.msg);
			}
		}
	})
}

function _edit(_e){
	var _id = $(_e).attr('id').split('_e')[1];
	location.href = '/p/setting/appinfo/edit?appId='+_id;
}

function _delete(_e){
	var _id = $(_e).attr('id').split('_delete')[1];
	var _data = {};
	_data.appId = _id;
	$.ajax({
		url:'/d/business_appinfo/delete/1_0',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
		type:'post',
		success:function(res){
			if('200'==res.code){
				layer.alert('删除成功！',function(){
					location.reload();
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
