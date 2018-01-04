function _update(_e){
	var _id = $(_e).attr('id');
	var _postNum = $('#postNum').val();
	if(_postNum==''||_postNum==null){
		layer.alert('快递单号未填写！');
	}else{
		$.ajax({
			url:'/home/d/pro_book_order/update?id='+_id+'&postNum='+postNum,
			type:'post',
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
}

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
			url:'/home/d/pro_book_order/list?p='+_data.p,
			dataType:'json',
			type:'post',
			success:function(res){
				$('#_end').bind('click',function(){
					_getData(0,res.data.page.pages);
				})
				_max = res.data.page.pages;
				for(var i in res.data.list){
					res.data.list[i].createTime = toDateTime(res.data.list[i].createTime);
					res.data.list[i].postTime = toDateTime(res.data.list[i].postTime);
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
