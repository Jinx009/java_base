var _data = {},_roles = {};
/**
 * 获取数据
 */
function getData(){
	$.ajax({
		url:'/home/d/user',
		type:'post',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				if(res.data!=null){
					_data = res.data;
					for(var i in res.data){
						res.data[i].createTime = toDate(res.data[i].createTime);
					}
					new Vue({
		   				  el: '.data-tbody',
		   				  data:{data:res.data}
		    		})
				}else{
					layer.alert('暂无数据！');
				}
			}else if('8001'==res.code){
				layer.alert('您无权限操作此项！');
			}else{
				layer.alert(res.msg);
			}
		}
	});
	$.ajax({
		url:'/home/d/role',
		type:'post',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				_roles = res.data;
				new Vue({
	   				  el: '._add',
	   				  data:{roles:res.data}
	    		})
			}else if('8001'==res.code){
				layer.alert('您无权限操作此项！');
			}else{
				layer.alert(res.msg);
			}
		}
	});
}
/**
 * 列表和新增切换
 * @param _class
 */
function changeDiv(_class){
	$('._edit input').each(function(){
		$(this).val();
	})
	$('._add input').each(function(){
		$(this).val();
	})
	$('._edit').addClass('none');
	$('._add').addClass('none');
	$('._main').addClass('none');
	$('.'+_class).removeClass('none');
}
/**
 * 新增账户
 */
function add(){
	var _userName = $('#userName').val();
	var _realName = $('#realName').val();
	var _roleId = $('#roleId').val();
	var _pwd = $('#pwd').val();
	var _rePwd = $('#rePwd').val();
	if(_userName==null||''==_userName){
		layer.msg('账户名称不能为空！');
	}else if(_pwd==null||''==_pwd){
		layer.msg('密码不能为空！');
	}else if(_pwd!=_rePwd){
		layer.msg('确认密码不一致！');
	}else{
		$.ajax({
			url:'/home/d/user_add',
			type:'post',
			data:{"userName":_userName,
				  "pwd":_pwd,
				  "roleId":_roleId,
				  "realName":_realName},
			dataType:'json',	  
			success:function(res){
				if('200'==res.code){
					layer.alert('操作成功！',function(){
						location.reload();
					})
				}else if('8001'==res.code){
					layer.alert('您无权限操作此项！');
				}else{
					layer.alert(res.msg);
				}
			}
		})
	}
};
/**
 * 显示编辑框
 * @param id
 */
function showEdit(id){
	changeDiv('_edit');
	for(var i in _data){
		if(id==_data[i].id){
			$('#_userName').val(_data[i].userName);
			$('#_realName').val(_data[i].realName);
			var htmlStr = '';
			for(var j in _roles){
				if(_roles[j].id == _data[i].roleId){
					htmlStr += '<option value="'+_roles[i].id+'" selected="selected"  >'+_roles[i].name+'</option>'
				}else{
					htmlStr += '<option value="'+_roles[i].id+'"  >'+_roles[i].name+'</option>'
				}
			}
			$('#_roleId').html(htmlStr);
		}
	}
	$('._edit_submit').bind('click',function(){edit(id)});
};

/**
 * 真实编辑
 * @param id
 */
function edit(id){
	var _userName = $('#_userName').val();
	var _realName = $('#_realName').val();
	var _roleId = $('#_roleId').val();
	var _pwd = $('#_pwd').val();
	var _rePwd = $('#_rePwd').val();
	var _userId = id;
	if(_userName==null||''==_userName){
		layer.msg('账户名称不能为空！');
	}else if(_pwd!=null&&''!=_pwd&&_pwd!=_rePwd){
		layer.msg('两次密码不一致！');
	}else{
		$.ajax({
			url:'/home/d/user_update',
			type:'post',
			data:{"userName":_userName,
				  "pwd":_pwd,
				  "roleId":_roleId,
				  "realName":_realName,
				  "userId":_userId},
			dataType:'json',	  
			success:function(res){
				if('200'==res.code){
					layer.alert('操作成功！',function(){
						location.reload();
					})
				}else if('8001'==res.code){
					layer.alert('您无权限操作此项！');
				}else{
					layer.alert(res.msg);
				}
			}
		})
	}
}
