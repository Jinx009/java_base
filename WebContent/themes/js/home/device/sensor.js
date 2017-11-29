$(function(){
	_getData(0,1);
})
var _d = '';
var  _nowPage = 0,_max = 0;
function _getData(_type,_index){
	var _data = {};
	_data.p = _getPage(_type,_index);
	if(_data.p!=-1){
		$.ajax({
			url:'/d/device_sensor/list/1_0',
			dataType:'json',
			data:JSON.stringify(_data),
			contentType:'application/json;charSet=utf8',
			type:'post',
			success:function(res){
				$('#_end').bind('click',function(){
					_getData(0,res.data.page.pages);
				})
				_max = res.data.page.pages;
				for(var i in res.data.list){
					res.data.list[i].lastSeenTime = toDateTime(res.data.list[i].lastSeenTime);
				}
				if(''==_d){
					_d = new Vue({
						el:'#datas',
						data:{
							datas:res.data
						}
					})
				}else{
					_d.datas = res.data
				}
			}
		})
	}
}