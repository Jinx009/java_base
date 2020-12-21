$(function(){
	_getData(0,1);
	_getAppInfo();
})
var _d = '';
var  _nowPage = 0,_max = 0;
function _getData(_type,_index){
	var _data = {};
	_data.p = _getPage(_type,_index);
	if(_data.p!=-1){
		$.ajax({
			url:'/d/business_location/list/1_0',
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

function _edit(_e){
	var _id = $(_e).attr('id').split('_edit')[1];
	location.href = '/p/setting/location/edit?id='+_id;
}

function _delete(_e){
	var _id = $(_e).attr('id').split('_delete')[1];
	var _data = {};
	_data.id  = _id;
	$.ajax({
		url:'/d/business_location/delete/1_0',
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

function _save(){
	var _name = $('#name').val();
	var _description = $('#note').val();
	var _appInfoId = parseInt($('#appInfoId').val());
	var _noticeType = parseInt($('#noticeType').val());
	var _data = {};
	_data.name = _name;
	_data.description = _description;
	_data.appInfoId = _appInfoId;
	_data.noticeType = _noticeType;
	if(_name == null||'' == _name){
		layer.alert('Location名称不能为空！');
	}else{
		$.ajax({
			url:'/d/business_location/create/1_0',
			dataType:'json',
			data:JSON.stringify(_data),
			contentType:'application/json;charSet=utf8',
			type:'post',
			success:function(res){
				if('200'==res.code){
					layer.alert('新建成功！',function(){
						location.reload();
					})
				}else{
					layer.alert(res.msg);
				}
			}
		})
	}
}

function _getAppInfo(){
	$.ajax({
		url:'/d/business_appinfo/all/1_0',
		dataType:'json',
		contentType:'application/json;charSet=utf8',
		type:'post',
		success:function(res){
			new Vue({
				el:'#appInfoId',
				data:{
					apps:res.data
				}
			})
		}
	})
}