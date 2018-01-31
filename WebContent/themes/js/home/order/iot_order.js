$(function(){
	_i = showLoad();
	_getData(0,1);
})
var _i = -1,_data = '';
var  _nowPage = 0,_max = 0,_search = '';
function _getData(_type,_index){
	if(-1==_i){
		_i = showLoad();
	}
	var page = _getPage(_type,_index);
	if(page!=-1){
		$.ajax({
			url:'/home/d/iot_order_list?p='+page,
			type:'post',
			dataType:'json',
			success:function(res){
				closeLoad(_i);
				if('200'==res.code){
					if(res.data!=null){
						var totalPage = parseInt(res.data.page.pages);
						$('#_end').bind('click',function(){
							_getData(0,totalPage);
						})
						_max = totalPage;
						var _d = {};
						_d.data = res.data.list;
						for(var i in _d.data){
							if(_d.data[i].startTime!=null&&_d.data[i].startTime!=''){
								_d.data[i].startTime = toDateTime(_d.data[i].startTime);
							}else{
								_d.data[i].startTime = '';
							}
							if(_d.data[i].endTime!=null&&_d.data[i].endTime!=''){
								_d.data[i].endTime = toDateTime(_d.data[i].endTime);
							}else{
								_d.data[i].endTime = '';
							}
							if(_d.data[i].payTime!=null&&_d.data[i].payTime!=''){
								_d.data[i].payTime = toDateTime(_d.data[i].payTime);
							}else{
								_d.data[i].payTime = '';
							}
						}
						_d.currentPage = page;
						_d.max = _max;
						if(''==_data){
							_data = new Vue({
				   				  el: '#order',
				   				  data:{orders:_d}
				    		})
						}else{
							_data.orders = _d;
						}
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
	}else{
		closeLoad(_i);
	}
}