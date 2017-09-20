$(function(){
	_getData();
})
function _getData(){
	$.ajax({
		url:'/home/d/invoice',
		type:'get',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				for(var i in res.data){
					res.data[i].createTime = toDate(res.data[i].createTime);
				}
				new Vue({
					el:'#invoice',
					data:{
						invoices:res.data
					}
				})
			}else{
				layer.alert(res.msg);
			}
		}
	})
}
function _update(_id){
	$.ajax({
		url:'/home/d/updateInvoiceStatus',
		data:'id='+_id+'&status='+1,
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
function _del(_id){
	$.ajax({
		url:'/home/d/delInvoice',
		data:'id='+_id,
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