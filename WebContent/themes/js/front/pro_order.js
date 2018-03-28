$(function(){
	_getData();
})

function _getData(){
	var _userId = getLocalStorage('userId');
	if(''==_userId){
		setLocalStorage('_url','/f/p/pro_order');
		location.href = '/f/p/logon';
	}else{
		$.ajax({
			url:'/d/order/myOrder',
			data:'userId='+_userId,
			dataType:'json',
			type:'post',
			success:function(res){
				var _sA = [],_sR =[];
				for(var i in res.data){
					if(res.data[i].type==1){
						_sR.push(res.data[i]);
					}else{
						_sA.push(res.data[i]);
					}
				}
				new Vue({
					el:'#s',
					data:{
						s:_sA
					}
				})
				new Vue({
					el:'#r',
					data:{
						r:_sR
					}
				})
			}
		})
		$.ajax({
			url:'/d/classOrder/list?userId='+_userId,
			type:'get',
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					new Vue({
						el:'#c',
						data:{
							c:res.data
						}
					})
				}
			}
		})
	}
}