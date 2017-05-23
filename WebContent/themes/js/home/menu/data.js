var parentMenuList = [],sonMenuList = [];
function getData(){
	$.ajax({
		url:'/home/d/data',
		type:'post',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				if(res.data!=null){
					new Vue({
		   				  el: '.content',
		   				  data:{datas:res.data}
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
	})
}
/**
 * 新增
 */
function add(){
	var name = $('#name').val();
	var description = $('#description').val();
	var uri = $('#uri').val();
	if(name==null||''==name){
		layer.msg('名称不能为空！');
	}else if(uri==null||''==uri){
		layer.msg('链接不能为空！');
	}else{
		var params = 'name='+name+'&description='+description+'&uri='+uri;
		$.ajax({
			url:'/home/d/resource_data_add',
			type:'post',
			data:params,
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					layer.alert('操作成功！',function(){
						location.reload();
					});
				}else if('8001'==res.code){
					layer.alert('您无权限操作此项！');
				}else{
					layer.alert(res.msg);
				}
			}
		})
	}
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
 * 更改数据菜单状态
 * @param status
 * @param id
 */
function changeStatus(status,id){
	var _text = '您确定要禁用该数据吗？';
	if('1'===status){
		_text = '您确定要激活该数据吗？';
	}
	layer.confirm(_text, {
	  btn: ['确定','取消'] 
	}, function(){
	   var params = 'status='+status+'&id='+id;
	   $.ajax({
		   url:'/home/d/resource_data_status',
		   data:params,
		   type:'post',
		   dataType:'json',
		   success:function(res){
			   if('200'==res.code){
					layer.alert('操作成功！',function(){
						location.reload();
					});
				}else if('8001'==res.code){
					layer.alert('您无权限操作此项！');
				}else{
					layer.alert(res.msg);
				}
		   }
	   })
	});
}