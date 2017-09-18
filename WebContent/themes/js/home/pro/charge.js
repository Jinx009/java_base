$(function() {
	_i = showLoad();
	getData();
})
var _i = -1;
function getData() {
	if (-1 == _i) {
		_i = showLoad();
	}
	$.ajax({
		url : '/home/d/charge',
		type : 'get',
		dataType : 'json',
		success : function(res) {
			closeLoad(_i);
			if ('200' == res.code) {
				for(var i in res.data){
					if(res.data[i].dateType==0){
						res.data[i].singleDate = toDate(res.data[i].singleDate);
					}else{
						res.data[i].singleDate = '';
					}
				}
				new Vue({
					el : '#charge',
					data : {
						charges : res.data
					}
				})
			}
		}
	})
}
function _del(_id){
	$.ajax({
		url:'/home/d/delCharge',
		type:'post',
		data:'id='+_id,
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				layer.alert('删除成功！',function(){
					location.reload();
				})
			}
		}
	})
}