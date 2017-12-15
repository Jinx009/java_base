$(function(){
	_i = showLoad();
	getData();
})
var _i = -1;
function getData(){
	if(-1==_i){
		_i = showLoad();
	}
	$.ajax({
		url:'/home/d/bluetooth/list',
		type:'post',
		dataType:'json',
		success:function(res){
			closeLoad(_i);
			if('200'==res.code){
				if(res.data!=null){
					new Vue({
		   				  el: '#device',
		   				  data:{devices:res.data}
		    		})
					new Vue({
		   				  el: '#new',
		   				  data:{blues:res.data}
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
function _del(_e){
	var _id = $(_e).attr('id');
	$.ajax({
		url:'/home/d/bluetooth/del',
		data:'id='+_id,
		type:'post',
		dataType:'json',
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
	var uuidA = $('#uuidA').val();
	if(uuidA==''||uuidA==null){
		uuidA = $('#uuid').val();
	}
	var params = 'uuid='+uuidA+'&mobilePhone='+$('#mobilePhone').val()+'&carNo='+$('#carNo').val();
	$.ajax({
		url:'/home/d/bluetooth/saveOrUpdate',
		data:params,
		type:'post',
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

function showNew(){
	$('#main').hide();
	$('#new').show()
}
function hideNew(){
	$('#main').show();
	$('#new').hide()
}