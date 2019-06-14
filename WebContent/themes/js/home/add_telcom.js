function _add(){
	var mac = $('#mac').val();
	var imei = $('#imei').val();
	var ipLocal = $('#ipLocal').val();
	var simCard = $('#simCard').val();
	var name = $('#name').val();
	var params = 'mac='+mac+'&name='+name+'&simCard='+simCard+'&ipLocal='+ipLocal+'&imei='+imei;
	$.ajax({
		url:'/telcom/register',
		data:params,
		type:'post',
		success:function(res){
			if('200'==res.code){
				alert('操作成功！');
			}
		}
	})
}