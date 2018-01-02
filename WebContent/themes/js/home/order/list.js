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
	var _searchStatus = $('#status').val();
	var page = _getPage(_type,_index);
	if(_searchStatus!=_search){
		page = 1;
		_search = _searchStatus;
	}
	if(page!=-1){
		$.ajax({
			url:'/home/d/order/list?page='+page+'&status='+$('#status').val(),
			type:'post',
			dataType:'json',
			success:function(res){
				closeLoad(_i);
				if('200'==res.code){
					if(res.data!=null){
						var _t = parseInt(res.data.resultData.totalAmount%20);
						var totalPage = parseInt(res.data.resultData.totalAmount/20);
						if(_t!==0){
							totalPage++;
						}
						$('#_end').bind('click',function(){
							_getData(0,totalPage);
						})
						_max = totalPage;
						var _d = {};
						_d.data = res.data.resultData.products;
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