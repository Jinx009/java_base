$(function(){
	_i = showLoad();
	_getData();
})
var _i = -1;
function _getData(){
	if(-1==_i){
		_i = showLoad();
	}
	$.ajax({
		url:'/home/d/area/list?id=22',
		type:'get',
		dataType:'json',
		success:function(res){
			closeLoad(_i);
			var _arr = [];
			if(res.data!=null){
				for(var i in res.data){
					res.data[i].createTime = toDate(res.data[i].createTime);
				}
				new Vue({
	   				  el: '#data',
	   				  data:{datas:res.data}
	    		})
			}else{
				layer.alert('暂无数据！');
			}
		}
	})
}
var _update_id = 0;
function _edit(_e){
	var _name = $(_e).attr('name').split('_name')[1];
	var _id = $(_e).attr('id').split('_id')[1];
	var _desc = $(_e).attr('desc').split('_desc')[1];
	_update_id = _id;
	$('#name').val(_name);
	$('#desc').val(_desc);
	_showNew();
}
function _save(){
	var _name = $('#name').val();
	var _desc = $('#desc').val();
	var _params = 'id='+_update_id+'&name='+_name+'&desc='+_desc;
	$.ajax({
		url:'/home/d/area/update',
		type:'post',
		data:_params,
		dataType:'json',
		success:function(res){
			closeLoad(_i);
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
