$(function(){
	_getData();
})
/**
 * 获取列表数据
 */
function _getData(){
	$.ajax({
		url:'/home/d/area',
		type:'GET',
		dataType:'json',
		success:function(res){
			if(res.data!=null){
				for(var i in res.data){
					res.data[i].createTime = toDate(res.data[i].createTime);
				}
				new Vue({
	   				  el: '#area',
	   				  data:{areas:res.data}
	    		})
			}
		}
	})
}
/**
 * 新增数据
 */
function _addArea(){
	$('#_main').hide();
	$('#name').val('');
	$('#desc').val('');
	$('#errorMsg').html('');
	$('#_add').show();
}
/**
 * 显示主屏幕
 */
function _showMain(){
	$('#_main').show();
	$('#_add').hide();
}
/**
 * 删除数据
 * @param id
 */
function _del(id){
	$.ajax({
		url:'/home/d/delArea',
		type:'post',
		data:'id='+id,
		dataType:'json',
		success:function(res){
			if(res.code=='200'){
				layer.alert('删除成功！',function(){
					location.reload();
				})
			}
		}
	})
}
/**
 * 真实保存数据
 */
function _save(){
	var _name = $('#name').val(),_desc = $('#desc').val();
	if(_name==null||''==_name){
		$('#errorMsg').html('名称未填写！');
	}else if(_desc==null||''==_desc){
		$('#errorMsg').html('描述未填写！');
	}else{
		var _params = 'name='+_name+'&desc='+_desc;
		$.ajax({
			url:'/home/d/saveArea',
			type:'post',
			data:_params,
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					layer.alert('新建成功！',function(){
						location.reload();
					})
				}
			}
		})
	}
}