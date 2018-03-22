$(function(){
	laydate.render({
	  elem: '#classDate'
	});
	laydate.render({
	  elem: '#_classDate'
	});
	laydate.render({
	  elem: '#_time'
	  ,type: 'time'
	  ,range: true
	});
	_getData(0,1);
})
var _d = '';
var  _nowPage = 0,_max = 0;
var _classDate = '';
function _getData(_type,_index){
	var _data = {};
	_data.p = _getPage(_type,_index);
	var classDate = $('#classDate').val();
	if(classDate!=_classDate){
		_data.p = 1;
		_classDate = classDate;
	}
	if(_data.p!=-1){
		$.ajax({
			url:'/d/class/pageList?p='+_data.p+'&classDate='+classDate,
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
function _save(){
	var _classDate = $('#_classDate').val(),
		_time = $('#_time').val(),
		_name = $('#_name').val(),
		_desc = $('#_desc').val();
	if(_classDate==''||_time==''||_name==''){
		layer.alert('请完善每一个必填项！');
	}else{
		var _params = 'classDate='+_classDate+'&time='+_time+'&name='+_name+'&desc='+_desc;
		$.ajax({
			url:'/d/class/add',
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
