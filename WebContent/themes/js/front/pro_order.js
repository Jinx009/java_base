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
				var _zyq = [],_sfq =[],_yy = [],_js=[];
				for(var i in res.data){
					if(res.data[i].type==1){
						_zyq.push(res.data[i]);
					}else if(res.data[i].type==2){
						_sfq.push(res.data[i]);
					}else if(res.data[i].type==3){
						_yy.push(res.data[i]);
					}else if(res.data[i].type==4){
						_js.push(res.data[i]);
					}
				}
				new Vue({
					el:'#zyq',
					data:{
						zyq:_zyq
					}
				})
				new Vue({
					el:'#sfq',
					data:{
						sfq:_sfq
					}
				})
				new Vue({
					el:'#yy',
					data:{
						yy:_yy
					}
				})
				new Vue({
					el:'#js',
					data:{
						js:_js
					}
				})
			}
		})
	}
}