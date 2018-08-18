function register(){
   var _mobilePhone = $('#mobilePhone').val();
   var _password = $('#password').val();
   if(_mobilePhone==null||_mobilePhone==''){
	   layer.open({
	    content: '手机号码不能为空！'
	    ,skin: 'msg'
	    ,time: 2 //2秒后自动关闭
	  });
   }else if(_password==null||_password==''){
	   layer.open({
	    content: '密码不能为空！'
	    ,skin: 'msg'
	    ,time: 2 //2秒后自动关闭
	  });
   }else{
	   var name = $('#name').val();
	   var plateNumber = $('#plateNumber').val();
	   $.ajax({
		   url:'/front/d/pro_driver/register',
		   data:'mobilePhone='+_mobilePhone+'&pwd='+_password+'&name='+name+'&plateNumber='+plateNumber,
		   dataType:'json',
		   type:'post',
		   success:function(res){
			   if('200'==res.code){
				   layer.open({
					    content: '注册成功'
					    ,btn: '好',
					    yes:function(){
					    	location.href = '/f/p/login';
					    }
					  });
			   }else{
				   layer.open({
				    content: res.msg
				    ,btn: '好'
				  });
			   }
		   }
	   })
   }
}
