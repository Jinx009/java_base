function getData(){
	$.ajax({
		url:'/home/d/role',
		type:'post',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				new Vue({
	   				  el: '.data-tbody',
	   				  data:{data:res.data}
	    		})
			}else if('8001'==res.code){
				layer.alert('您无权限操作此项！');
			}else{
				layer.alert(res.msg);
			}
		}
	})
}
/**
 * 列表和新增切换
 * @param _class
 */
function changeDiv(_class){
	$('._add input').each(function(){
		$(this).val();
	})
	$('._add').addClass('none');
	$('._main').addClass('none');
	$('.'+_class).removeClass('none');
}
/**
 *	新增
 */
function add(){
	var name = $('#name').val();
	var level = $('#level').val();
	var description = $('#description').val();
	if(!testNumber(level)){
		layer.alert('级别必须为数字！');
	}else if(name==null||''==name){
		layer.alert('角色名称不能为空！');
	}else{
		var params = 'name='+name+'&level='+level+'&description='+description;
		$.ajax({
			url:'/home/d/role_add',
			type:'post',
			data:params,
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					layer.alert('操作成功！');
				}else if('8001'==res.code){
					layer.alert('您无权限操作此项！');
				}else{
					layer.alert(res.msg);
				}
			}
		})
	}
}
