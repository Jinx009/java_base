function _save(){
	var postNum = $('#postNum').val(),
		reamrk = $('#remark').val();
	if(postNum==null||postNum==''){
		layer.alert('请填写快递单号！');
	}else{
		var _params = 'postNum='+postNum+'&remark='+remark;
		$.ajax({
			url:'/front/d/pro_book_post/save',
			type:'post',
			data:_params,
			success:function(res){
				if('200'==res.code){
					layer.open({
				    content: '操作成功！'
				    ,btn: '好'
				  },function(){
					  _open('icon-settings-','/front/p/my_book_post');
				  });
				}
			}
		})
	}
	
	
}