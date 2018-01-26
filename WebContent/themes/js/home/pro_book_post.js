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
			url:'/home/d/pro_book_post/list?p='+_data.p,
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
var _editId = '';
function _edit(_e){
	var _id = $(_e).attr('id');
	_editId = _id;
	_showNew();
}
function _save(){
	var points = $('#points').val(),
		pointsRemark = $('#pointsRemark').val(),
		sellPoints = $('#sellPoints').val();
	if(points==null||points==''||
	   pointsRemark==null||pointsRemark==''||
	   sellPoints==null||sellPoints==''){
		layer.alert('请完善信息！');
	}else{
		var _params = 'points='+points+'&pointsRemark='+pointsRemark+'&sellPoints='+sellPoints+'&id='+_editId;
		$.ajax({
			url:'/home/d/pro_book_post/edit',
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
}
