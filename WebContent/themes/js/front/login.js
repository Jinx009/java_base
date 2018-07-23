$(function(){
	var _m = getLocalStorage('_m');
	var _p = getLocalStorage('_p');
	if(_m!=null&&_m!=''&&_p!=null&&_p!=''){
		_login(_m,_p);
	}
})
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
		   url:'/data/commom/login',
		   data:'userName='+_mobilePhone+'&pwd='+_password,
		   dataType:'json',
		   type:'post',
		   success:function(res){
			   if('200'==res.code){
				    setLocalStorage('userId',res.data.id);
				    setLocalStorage('type',res.data.type);
				    setLocalStorage('_m',_mobilePhone);
				    setLocalStorage('_p',_password);
				    var _url = getLocalStorage('_url');
				    if(_url!=''){
				    	location.href = _url;
				    }else{
				    	location.href = '/f/p/me';
				    }
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
function _login(_m,_p){
	$.ajax({
		url:'/data/commom/login',
		data:'userName='+_m+'&pwd='+_p,
		type:'post',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				 setLocalStorage('userId',res.data.id);
				 setLocalStorage('type',res.data.type);
				_open('icon-settings-','/front/p/me');
			}
		}
	})
}