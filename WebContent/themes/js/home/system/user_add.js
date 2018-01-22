$(function() {
	_getData();
})
function _getData(){
	$.ajax({
		url:'/home/d/role',
		type:'post',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				new Vue({
					el:'#roleId',
					data:{
						datas:res.data
					}
				})
			}
		}
	})
}
function _save(){
	var userName = $('#userName').val();
	var realName = $('#realName').val();
	var roleId = $('#roleId').val();
	var pwd = $('#pwd').val();
	if(userName ==null||userName==''||
	   realName==null||realName==''||
	   pwd==null||pwd==''){
		layer.alert('请完善用户信息！');
	}else{
		var params = 'userName='+userName+'&realName='+realName+'&pwd='+pwd+'&roleId='+roleId;
		$.ajax({
			url:'/home/d/user_add',
			type:'post',
			data:params,
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					layer.alert('操作成功！',function(){
						_open('5','/home/p/system/user');
					})
				}
			}
		})
	}
}