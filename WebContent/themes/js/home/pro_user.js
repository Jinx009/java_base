$(function(){
	_getData(0,1);
})
var _d = '';
var  _nowPage = 0,_max = 0;
var _name = '',_type = 0;
function _getData(_type,_index){
	var _data = {};
	_data.p = _getPage(_type,_index);
	var name = $('#name').val(),type = $('#type').val();
	if(name!=_name||type!=_type){
		_data.p = 1;
		_name = name;
		_type = type;
	}
	if(_data.p!=-1){
		$.ajax({
			url:'/d/user/pageList?p='+_data.p+'&type='+type+'&name='+name,
			dataType:'json',
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
						el:'#data',
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
	var _mobilePhone = $('#_mobilePhone').val(),
		_type = $('#_type').val(),
		_name = $('#_name').val(),
		_pwd = $('#_pwd').val(),
//		_remarkA = $('#_remarkA').val(),
		_remarkB = $('#_remarkB').val(),
//		_remarkC = $('#_remarkC').val(),
		_desc = $('#_desc').val();
	if(_mobilePhone==''||_name==''||_pwd==''){
		layer.alert('手机号，密码，名称为必填项！');
	}else{
		var _params = 'mobilePhone='+_mobilePhone+'&name='+_name+'&type='+_type+'&remarkA='+_remarkA+
			'&remarkB='+_remarkB+'&remarkC='+_remarkC+'&pwd='+_pwd+'&desc='+_desc;
		$.ajax({
			url:'/d/user/homeSave',
			type:'post',
			data:_params,
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					layer.alert('操作成功！',function(){
						location.reload();
					})
				}else{
					layer.alert(res.msg);
				}
			}
		})
	}
}

function _changeType(_e){
	var _id = $(_e).attr('id').split('_type')[1];
	var _params = 'id='+_id+'&type='+$('#type_'+_id).val();
	$.ajax({
		url:'/d/user/updateType',
		type:'post',
		dataType:'json',
		data:_params,
		success:function(res){
			if('200'==res.code){
				layer.alert('操作成功！',function(){
					location.reload();
				})
			}else{
				layer.alert(res.msg);
			}
		}
	})
}

function _reset(_e){
	var _id = $(_e).attr('id').split('_reset')[1];
	var _params = 'id='+_id;
	$.ajax({
		url:'/d/user/resetPwd',
		type:'post',
		dataType:'json',
		data:_params,
		success:function(res){
			if('200'==res.code){
				layer.alert('操作成功！',function(){
					location.reload();
				})
			}else{
				layer.alert(res.msg);
			}
		}
	})
}

function _saveRemark(_e){
	var _id = $(_e).attr('id').split('_remark')[1];
	var _desc = $('#desc'+_id).val(),
	_remarkA = $('#remarkA'+_id).val();
	var _params = 'desc='+_desc+'&remarkA='+_remarkA+'&id='+_id;
	$.ajax({
		url:'/d/user/updateRemark',
		type:'post',
		dataType:'json',
		data:_params,
		success:function(res){
			if('200'==res.code){
				layer.alert('操作成功！',function(){
					location.reload();
				})
			}else{
				layer.alert(res.msg);
			}
		}
	})
}