$(function(){
//	_i = showLoad();
	_getData(1);
})
var _i = -1;
var _lot = ['蒙','浙','沪','京','苏','福','晋','皖'];
var _payStatus = ['NOT_PAY','UNPAY','PAYED'];
var _d = '';
function _getData(_index){
	for(var i = 1;i<20;i++){
		$('#index'+i).removeClass('active');
	}
	$('#index'+_index).addClass('active');
	var _arr = [];
	for(var i = 0;i<20;i++){
		var _obj = {};
		_obj.lotNum = _lot[Math.floor(Math.random () * 8)] + randomString(5);
		_obj.time = Math.floor(Math.random () * 200);
		_obj.money = parseInt(_obj.time*5/60);
		_obj.desc = Math.floor(Math.random () * 900) + 100;
		_arr.push(_obj);
		_obj.status = _payStatus[Math.floor(Math.random () * 3)];
	}
	if(''==_d){
		_d = new Vue({
			 el: '#order',
			 data:{
				 orders:_arr
			 }
		 })
	}else{
		_d.orders = _arr;
	}
// if(-1==_i){
// _i = showLoad();
// }
// $.ajax({
// url:'/home/d/order/list',
// type:'post',
// dataType:'json',
// success:function(res){
// closeLoad(_i);
// if('200'==res.code){
// if(res.data!=null){
// new Vue({
// el: '#order',
// data:{orders:res.data.resultData.products}
// })
// }else{
// layer.alert('暂无数据！');
// }
// }else if('8001'==res.code){
// layer.alert('您无权限操作此项！');
// }else{
// layer.alert(res.msg);
// }
// }
// })
}

function randomString(len) {
	len = len || 32;
	var $chars = 'ABCDEFGHJKMNPQRSTWXYZ0123456789';    /** **默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1*** */
	var maxPos = $chars.length;
	var pwd = '';
	for (i = 0; i < len; i++) {
		pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
	}
	return pwd;
}