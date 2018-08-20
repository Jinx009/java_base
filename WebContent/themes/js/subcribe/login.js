function login(){
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
	   $.ajax({
		   url:'/subcribe/d/user/login',
		   data:'mobilePhone='+_mobilePhone+'&pwd='+_password,
		   dataType:'json',
		   type:'post',
		   success:function(res){
			   if('200'==res.code){
				   _open('icon-home-','/subcribe/p/subcribe');
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