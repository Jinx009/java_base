$(function(){
	var _id = $('#_id').val(),
		_mobilePhone = $('#_mobilePhone').val(),
		_plateNo = $('#_plateNo').val();
	if(_id==null||''==_id||_mobilePhone==null||''==_mobilePhone||_plateNo==null||''==_plateNo){
		layer.alert('你所选中的会员信息有误！',function(){
			_open('6','/home/p/user');
		})
	}
	_getData();
})		
var _data = '';
function _getData(){
	var _type = $('#type').val();
	$.ajax({
		url:'/home/d/monthCard',
		type:'post',
		data:'type='+_type+'&status=0',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				if(''==_data){
					_data = new Vue({
						el:'#cardNo',
						data:{
							cards:res.data
						}
					})
				}else{
					_data.datas = res.data;
				}
			}else{
				layer.alert(res.msg);
			}
		}
	})
}
function _save(){
	var _id = $('#_id').val(),
		_monthId = $('#cardNo').val();
	$.ajax({
		url:'/home/d/updateUserMonth',
		type:'post',
		data:'id='+_id+'&monthId='+_monthId,
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				layer.alert('操作成功！',function(){
					_open('6','/home/p/user');
				})
			}else{
				layer.alert(res.msg);
			}
		}
	})
}