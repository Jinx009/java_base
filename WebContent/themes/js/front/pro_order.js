$(function(){
	_getData();
})

function _getData(){
	var _userId = getLocalStorage('userId');
	if(''==_userId){
		setLocalStorage('_url','/f/p/pro_order');
		location.href = '/f/p/register';
	}else{
		$.ajax({
			url:'/d/order/myOrder',
			data:'userId='+_userId,
			dataType:'json',
			type:'post',
			success:function(res){
				var _diving = [],_swimming =[],_class_room = [];
				for(var i in res.data){
					if(res.data[i].type==1){
						_diving.push(res.data[i]);
					}else if(res.data[i].type==2){
						_swimming.push(res.data[i]);
					}else if(res.data[i].type==3){
						_class_room.push(res.data[i]);
					}
				}
				new Vue({
					el:'#diving',
					data:{
						diving:_diving
					}
				})
				new Vue({
					el:'#swimming',
					data:{
						swimming:_swimming
					}
				})
				new Vue({
					el:'#class_room',
					data:{
						class_room:_class_room
					}
				})
			}
		})
	}
}