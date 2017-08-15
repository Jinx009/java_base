/**
 * 修改校验
 * @returns
 */
function save(){
	var name = $('#name').val();
	var desc = $('#desc').val();
	var keyword = $('#keyword').val();
	if(name==''||name==null){
		$('#errorMsg').html('活动名称不能为空!');
	}else if(desc==''||desc==null){
		$('#errorMsg').html('活动描述不能为空!');
	}else if(keyword==''||keyword==null){
		$('#errorMsg').html('链接参数不能为空!');
	}else{
		var params = 'name='+name+'&desc='+desc+'&keyword='+keyword;
		$.ajax({
			url:'/back/d/saveActive',
			type:'post',
			data:params,
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					layer.alert('保存成功',function(){
						location.href = '/home/p/active/list';
					})
				}else{
					layer.alert(res.msg);
				}
			}
		})
	}
}
