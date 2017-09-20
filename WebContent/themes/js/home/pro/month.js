$(function(){
	_getData();
})
var _data = '';
function _getData(){
	var _type = $('#type').val(),
		_status = $('#status').val();
	$.ajax({
		url:'/home/d/monthCard',
		type:'post',
		data:'type='+_type+'&status='+_status,
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				for(var i in res.data){
					res.data[i].createTime = toDate(res.data[i].createTime);
					if(res.data[i].startTime!=null){
						res.data[i].startTime = toData(res.data[i].startTime);
					}
					if(res.data[i].endTime!=null){
						res.data[i].endTime = toData(res.data[i].endTime);
					}
				}
				if(''==_data){
					_data = new Vue({
						el:'#_data',
						data:{
							datas:res.data
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
function _del(_id){
	$.ajax({
		url:'/home/d/delMonthCard',
		type:'post',
		data:'id='+_id,
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