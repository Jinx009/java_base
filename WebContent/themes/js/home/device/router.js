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
			url:'/d/device_router/list/1_0',
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
					res.data.list[i].status = 1;
					var timestamp = new Date().getTime();
					if((timestamp-res.data.list[i].lastSeenTime)>1000 * 120){
						res.data.list[i].status = 0;
					}
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

function _edit(_e){
	var _mac = $(_e).attr('id').split('_e')[1];
	_open_('1','/p/device/router/list','/p/device/router/edit?mac='+_mac);
}

function _job(_e){
	var _mac = $(_e).attr('id').split('_j')[1];
	_open_('1','/p/device/router/list','/p/device/router/job?mac='+_mac);
}