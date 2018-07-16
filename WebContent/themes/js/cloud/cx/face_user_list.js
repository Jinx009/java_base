$(function(){
	_getData(0,1);
})
var _d = '',_l = '',_p = '';
var  _nowPage = 0,_max = 0;
function _getData(_type,_index){
	var _data = {};
	_data.p = _getPage(_type,_index);
	if(_data.p!=-1){
		_nowPage = _data.p;
		$.ajax({
			url:'/home/cloud/face/faceFactoryUsers?p='+_data.p,
			dataType:'json',
			type:'post',
			success:function(res){
				_max = res.data.page.pages;
				for(var i in res.data.list){
					res.data.list[i].createTime = toDateTime(res.data.list[i].createTime);
					res.data.list[i].imagePath = 'http://106.14.94.245:8090/static/user/'+res.data.list[i].imagePath;
				}
				if(''==_l){
					_l = new Vue({
						el:'#users',
						data:{
							users:res
						}
					})
				}else{
					_l.users = res
				}
			}
		})
	}
}