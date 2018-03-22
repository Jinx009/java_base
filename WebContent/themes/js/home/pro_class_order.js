$(function(){
	laydate.render({
	  elem: '#classDate'
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
			url:'/d/classOrder/pageList?p='+_data.p+'&classDate='+classDate,
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
	var _params = 'id='+_id+'&remark='+_remark;
	$.ajax({
		url:'/d/classOrder/saveRemark',
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
function _dao(_e){
	var _id = $(_e).attr('id').split('_dao')[1];
	var _params = 'id='+_id+'&status=2';
	$.ajax({
		url:'/d/classOrder/changeStatus',
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

function _tao(_e){
	var _id = $(_e).attr('id').split('_tao')[1];
	var _params = 'id='+_id+'&status=1';
	$.ajax({
		url:'/d/classOrder/changeStatus',
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
