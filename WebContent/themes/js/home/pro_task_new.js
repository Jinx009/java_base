$(function(){
	_getSelect();
})
function _getSelect(){
	$.ajax({
		url:'/home/d/pro_driver/selectList',
		type:'post',
		dataType:'json',
		success:function(res){
			var obj = {};
			obj.id = 0;
			obj.name = 'Driver Info';
			obj.mobilePhone = '0';
			res.data.push(obj);
			new Vue({
				el:'#driver',
				data:{
					drivers:res.data
				}
			})
		}
	})
	$.ajax({
		url:'/home/d/pro_task_title/selectList',
		type:'post',
		dataType:'json',
		success:function(res){
			new Vue({
				el:'#taskTitle',
				data:{
					titles:res.data
				}
			})
		}
	})
}

function _save(){
	var _name = $('#name').val();
	var _noId = $('#noId').val();
	var _pickTime = $('#pickTime').val();
	var _dep = $('#dep').val();
	var _dateStr = $('#dateStr').val();
	var _taskTitle = $('#taskTitle').val();
	var _flight = $('#flight').val();
	var _description = $('#description').val();
	var _driverMobile = $('#driver').val();
	var _driverName=$("#driver").find("option:selected").text();
	var _pickedTime = $('#pickedTime').val();
	var _params = 'name='+_name+'&noId='+_noId+'&dep='+_dep+'&pickedTime='+_pickedTime+'&description='+_description+'&flight='+
	_flight+'&driverName='+_driverName+'&pickTime='+_pickTime+'&driverMobile='+_driverMobile+'&taskTitleId='+_taskTitle+'&dateStr='+_dateStr;
	if(_name==''){
		layer.alert('待接人员姓名不能为空！');
	}else{
		$.ajax({
			url:'/home/d/pro_task/save',
			type:'post',
			data:_params,
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					layer.alert('添加成功！',function(){
						_open('l1','/home/p/pro_task');
					})
				}else{
					layer.alert(res.msg);
				}
			}
		})
	}
}
