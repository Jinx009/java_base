function register(){
   var _mobilePhone = $('#mobilePhone').val();
   var _password = $('#password').val();
   var _remarkB = $('#remarkB').val();
   var _name = $('#name').val();
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
		   url:'/data/commom/register',
		   data:'userName='+_mobilePhone+'&pwd='+_password+'&remarkB='+_remarkB+'&name='+_name,
		   dataType:'json',
		   type:'post',
		   success:function(res){
			   if('200'==res.code){
			    	location.href = '/f/login';
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
