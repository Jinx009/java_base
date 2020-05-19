$(function(){
	_getData();
})
var _d = '';
function _getData(_){
		$.ajax({
			url:'/h/pro_price/list',
			dataType:'json',
			type:'post',
			success:function(res){
				for(var i in res.data){
					res.data[i].createTime = toDateTime(res.data[i].createTime);
				}
				if(''==_d){
					_d = new Vue({
						el:'#data',
						data:{
							datas:res.data
						}
					})
				}else{
					_d.datas = res.data
				}
			}
		})
}

function _edit(_e){
	var id = $(_e).attr('id');
	var name = $('#name_'+id).val();
	var price = $('#price_'+id).val();
	var params = 'name='+name+'&price='+price+'&id='+id;
	$.ajax({
		url:'/h/pro_price/update',
		type:'post',
		dataType:'json',
		data:params,
		success:function(res){
			if('200'==res.code){
				layer.alert('修改成功！',function(){
					location.reload();
				});
			}else{
				layer.alert('修改失败！');
			}
		}
	})
}