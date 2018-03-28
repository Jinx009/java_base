$(function(){
	_getData();
})
function _getData(){
	var _userId = getLocalStorage('userId');
	$.ajax({
		url:'/d/class/getStatus',
		type:'post',
		data:'userId='+_userId,
		dataType:'json',
		success:function(res){
			var _htmlStr = '';
			if('200'==res.code){
				for(var i in res.data){
					if(res.data[i].desc==='1'){
						_htmlStr+= ' <p><a onclick="_save('+res.data[i].id+')" class="button button-danger">'+res.data[i].classDate +' ['+res.data[i].name+'] '+res.data[i].time+'</a></p>';
					}else{
						_htmlStr+= ' <p><a  class="button disabled">'+res.data[i].classDate +' ['+res.data[i].name+'] '+res.data[i].time+'</a></p>';
					}
				}
			}
			$('.content-block').html(_htmlStr);
		}
	})
}
function _save(_id){
	var _userId = getLocalStorage('userId');
	if(_userId==''){
		setLocalStorage('_url','/f/p/pro_class');
		location.href = '/f/p/login';
	}else{
		var _type = getLocalStorage('type');
		if(1!=_type){
			layer.open({
			    content: '只有学员才能预定课程！'
			    ,btn: '我知道了'
			  });
		}else{
			var _params = 'userId='+_userId+'&classId='+_id;
			$.ajax({
				url:'/d/classOrder/save',
				type:'post',
				data:_params,
				dataType:'json',
				success:function(res){
					if('200'==res.code){
						layer.open({
		   				    content: '恭喜您预约成功！'
		   				    ,btn: '好',
		   				    yes:function(index){
		   				    	location.href = '/f/p/pro_order';
		   				    }
		   				  });
					}else{
						layer.open({
						    content: res.msg
						    ,btn: '我知道了'
						  });
					}
				}
			})
		}
	}
}