$(function(){
	_i = showLoad();
	getData();
})
var _i = -1;
function getData(){
	if(-1==_i){
		_i = showLoad();
	}
	$.ajax({
		url:'/home/d/proUser_list',
		type:'post',
		dataType:'json',
		success:function(res){
			closeLoad(_i);
			if(res.data!=null){
				for(var i in res.data){
					res.data[i].createTime = toDate(res.data[i].createTime);
				}
				new Vue({
	   				  el: '#account',
	   				  data:{accounts:res.data}
	    		})
			}else{
				layer.alert('暂无数据！');
			}
		}
	})
}
function _delete(_e){
	var _id = $(_e).attr('id');
	$.ajax({
		url:'/home/d/proUser_delete?id='+_id,
		type:'get',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				layer.alert('操作成功！',function(){
					location.reload();
				})
			}
		}
	})
}

function _save(){
	var _name = $('#name').val(),_mobilePhone = $('#mobilePhone').val(),
	_card = $('#card').val(),_carNum = $('#carNum').val();
	if(_card ==null||_card == ''||
	   _carNum == null||_carNum == ''||
	   _name == null||_name == ''||
	   _mobilePhone == null||_mobilePhone == ''){
		layer.alert('请完善用户信息!');
	}else{
		var params = 'carNum='+_carNum+'&name='+_name+'&card='+_card+'&mobilePhone='+_mobilePhone;
		$.ajax({
			url:'/home/d/proUser_add',
			type:'post',
			data:params,
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
}
