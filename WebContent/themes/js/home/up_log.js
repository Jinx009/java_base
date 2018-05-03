$(function(){
	_getData(0,1)
})
var _d = '';
var  _nowPage = 0,_max = 0,_mac = '';
function _getData(_type,_index){
	var _data = {};
	var _mac_ = $('#mac').val();
	_data.p = _getPage(_type,_index);
	if(_mac!=_mac_){
		_data.p = 1;
		_mac = _mac_
	}
	if(_data.p!=-1){
		$.ajax({
			url:'/iot/log/list?p='+_data.p+'&type=0&mac='+_mac,
			dataType:'json',
			type:'post',
			success:function(res){
				$('#_end').bind('click',function(){
					_getData(0,res.data.page.pages);
				})
				_max = res.data.page.pages;
				for(var i in res.data.list){
					res.data.list[i].createTime = toDateTime(res.data.list[i].createTime);
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

