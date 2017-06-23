$(function(){
	_i = showLoad();
	getData();
})
var _i = -1;
var uuid_array = new Array();
function getData(){
	if(-1==_i){
		_i = showLoad();
	}
	$.ajax({
		url:'/home/d/device',
		type:'post',
		dataType:'json',
		success:function(res){
			closeLoad(_i);
			if('200'==res.code){
				if(res.data!=null){
					for(var i in res.data.result){
						if(res.data.result[i].bluetooth==null||''==res.data.result[i].bluetooth){
							res.data.result[i].bluetooth = '';
						}else{
							uuid_array.push(res.data.result[i].bluetooth);
						}
					}
					getCar();
					new Vue({
		   				  el: '#device',
		   				  data:{devices:res.data.result}
		    		})
				}else{
					layer.alert('暂无数据！');
				}
			}else if('8001'==res.code){
				layer.alert('您无权限操作此项！');
			}else{
				layer.alert(res.msg);
			}
		}
	})
}
var _index = 0;
function getCar(){
	if(_index<uuid_array.length){
		$.ajax({
			url:'/home/d/carNo?v='+uuid_array[_index],
			type:'post',
			data:'uuid='+uuid_array[_index],
			dataType:'json',
			ansyc:true,
			success:function(res){
				if('200'==res.code&&res.data!=null){
					setCarNo(uuid_array[_index],res.data);
					_index++;
					getCar();
				}
			}
		})
	}
}
function setCarNo(a,b){
	$('#car'+a).html(b);
}