$(function() {
	_getData();
})
function _getData() {
	$.ajax({
		url : '/home/d/user',
		type : 'post',
		dataType : 'json',
		success : function(res) {
			if ('200' == res.code) {
				for(var i in res.data){
					res.data[i].createTime = toDate(res.data[i].createTime);
				}
				new Vue({
					el : '#data',
					data : {
						datas : res.data
					}
				})
			}
		}
	})
}
function _delete(_e) {
	var _id = $(_e).attr('id').split('_delete')[1];
	layer.confirm('您确定删除该用户？', {
		btn : [ '否', '是' ]
	}, function() {
		layer.closeAll();
	}, function() {
		$.ajax({
			url:'/home/d/user_status',
			type:'post',
			data:{"id":_id,
				  "status":0},
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					layer.alert('操作成功！',function(){
						location.reload();
					})
				}
			}
		})
	});
}
function _newUser(){
	_open_('6','/home/p/system/user','/home/p/system/user_add');
}
var _editId = '';
function _edit(_e){
	var _id = $(_e).attr('id').split('_edit')[1];
	_editId = _id;
	var _roleName = $(_e).attr('roleName').split(_id+'_')[1];
	var _userName = $(_e).attr('userName').split(_id+'_')[1];
	var _realName = $(_e).attr('realName').split(_id+'_')[1];
	$('#realName').val(_realName);
	$('#userName').val(_userName);
	$.ajax({
		url:'/home/d/role',
		type:'post',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				var htmlStr = '';
				for(var i in res.data){
					if(res.data[i].name==_roleName){
						htmlStr += '<option selected="selected" value="'+res.data[i].id+'" >'+res.data[i].name+'</option>';
					}else{
						htmlStr += '<option value="'+res.data[i].id+'" >'+res.data[i].name+'</option>';
					}
					$('#roleId').html(htmlStr);
				}
			}
		}
	})
	_showNew();
}
function _save(){
	$.ajax({
		url:'/home/d/user_update',
		dataType:'json',
		data:'userId='+ parseInt(_editId)+'&userName='+$('#userName').val()+'&realName='+$('#realName').val()+'&pwd='+$('#pwd').val()+'&roleId='+parseInt($('#roleId').val()),
		type:'post',
		success:function(res){
			if('200'==res.code){
				layer.alert('操作成功！',function(){
					location.reload();
				})
			}
		}
	})
}