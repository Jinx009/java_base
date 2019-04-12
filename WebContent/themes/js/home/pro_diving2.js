$(function(){
	laydate.render({
	  elem: '#orderDate'
	});
	_getData(0,1);
})
var _d = '';
var  _nowPage = 0,_max = 0;
var _orderDate = '';
function _getData(_type,_index){
	var _data = {};
	_data.p = _getPage(_type,_index);
	var orderDate = $('#orderDate').val();
	if(orderDate!=_orderDate){
		_data.p = 1;
		_orderDate = orderDate;
	}
	if(_data.p!=-1){
		$.ajax({
			url:'/d/order/pageList?p='+_data.p+'&orderDate='+orderDate+'&type=2',
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
						el:'#data',
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
function _saveRemark(_e){
	var _id = $(_e).attr('id').split('_remark')[1];
	var _remark = $('#remark'+_id).val();
	var _realNum = $('#realNum'+_id).val();
	var _params = 'id='+_id+'&remark='+_remark+'&realNum='+_realNum;
	$.ajax({
		url:'/d/order/saveRemark',
		type:'post',
		data:_params,
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				layer.alert('操作成功！',function(){
					location.reload();
				})
			}else{
				layer.alert(res.msg);
			}
		}
		})
}
