$(function(){
	_getData();
})
/**
 * 获取列表数据
 */
var _areaList = {};
function _getData(){
	$.ajax({
		url:'/home/d/areaList',
		type:'GET',
		dataType:'json',
		success:function(res){
			if(res.data!=null){
				_areaList = res.data;
				new Vue({
	   				  el: '#areaId',
	   				  data:{areas:res.data}
	    		})
				$.ajax({
					url:'/home/d/streetList',
					type:'GET',
					dataType:'json',
					success:function(res){
						if(res.data!=null){
							for(var i in res.data){
								for(var j in _areaList){
									if(res.data[i].areaId==_areaList[j].id){
										res.data[i].areaName = _areaList[j].name;
									}
								}
							}
							new Vue({
				   				  el: '#street',
				   				  data:{streets:res.data}
				    		})
						}
					}
				})
			}
		}
	})
}
/**
 * 新增数据
 */
function _addStreet(){
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
		url:'/home/d/delStreet',
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
	var _name = $('#name').val(),_desc = $('#desc').val(),_areaId = $('#areaId').val();
	if(_name==null||''==_name){
		$('#errorMsg').html('名称未填写！');
	}else if(_desc==null||''==_desc){
		$('#errorMsg').html('描述未填写！');
	}else{
		var _params = 'name='+_name+'&desc='+_desc+'&areaId='+_areaId;
		$.ajax({
			url:'/home/d/saveStreet',
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