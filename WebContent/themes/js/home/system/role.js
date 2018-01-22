$(function() {
	_getData();
})
function _getData() {
	$.ajax({
		url : '/home/d/role',
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
function _newUser(){
	_open_('6','/home/p/system/role','/home/p/system/role_add');
}
function _delete(_e){
	var _id = $(_e).attr('id').split('_delete')[1];
	var _level = $(_e).attr('level');
	if(_level=='1'){
		layer.alert('管理员角色不能删除！');
	}else{
		$.ajax({
			url:'/home/d/role_delete',
			type:'post',
			data:'id='+_id,
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					layer.alert('操作成功！',function(){
						location.reload();
					})
				}
			}
		})
	}
}