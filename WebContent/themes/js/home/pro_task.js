$(function(){
	_getSelect();
	_getData(0,1);
})
var drivers = [];
function _getSelect(){
	$.ajax({
		url:'/home/d/pro_driver/selectList',
		type:'post',
		dataType:'json',
		success:function(res){
			drivers = res.data;
			var obj = {};
			obj.id = 0;
			obj.name = '抢单模式';
			obj.mobilePhone = '0';
			drivers.push(obj);
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
function _go(){
	var _taskTitle = $('#taskTitle').val();
	if(_taskTitle=='0'){
		layer.alert('您必须选择一个TaskTitle!');
	}else{
		window.open('/home/d/pro_task/excel?titleId='+_taskTitle,'_self');
	}
}
var _d = '';
var  _nowPage = 0,_max = 0;
var _bd = [];
var _taskTitleId = 0,_typeId = 2;
function _getData(_type,_index){
	var _data = {};
	var _taskTitle = $('#taskTitle').val();
	var _status = $('#status').val();
	_data.p = _getPage(_type,_index);
	if(_taskTitleId!=_taskTitle||_status!=_typeId){
		_data.p = 1;
	}
	if(_data.p!=-1){
		$.ajax({
			url:'/home/d/pro_task/list',
			dataType:'json',
			data:'p='+_data.p+'&taskTitleId='+_taskTitle+'&status='+_status,
			type:'post',
			success:function(res){
				$('#_end').bind('click',function(){
					_getData(0,res.data.page.pages);
				})
				for(var i in res.data.list){
					res.data.list[i].index = i;
				}
				_max = res.data.page.pages;
				_bd = res.data.list;
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
	var _index = parseInt($(_e).attr('index'));
	_editId = _id;
	var _d = _bd[_index];
	$('#name').val(_d.name);
	$('#noId').val(_d.noId);
	$('#dep').val(_d.dep);
	$('#description').val(_d.description);
	$('#pickedTime').val(_d.pickedTime);
	$('#mailTime').val(_d.mailTime);
	$('#flight').val(_d.flight);
	$('#pickTime').val(_d.pickTime.split(' ')[1]);
	var _htmlStr = '';
	for(var i in drivers){
		if(drivers[i].mobilePhone==_d.driverMobile){
			_htmlStr += '<option value="'+drivers[i].mobilePhone+'" selected="selected" >'+drivers[i].name+'</option>';
		}else{
			_htmlStr += '<option value="'+drivers[i].mobilePhone+'" >'+drivers[i].name+'</option>';
		}
	}
	$('#driver').html(_htmlStr);
	_showNew();
}

function _update(){
	var _name = $('#name').val();
	var _noId = $('#noId').val();
	var _mailTime = $('#mailTime').val();
	var _dep = $('#dep').val();
	var _flight = $('#flight').val();
	var _description = $('#description').val();
	var _driverMobile = $('#driver').val();
	var _driverName=$("#driver").find("option:selected").text();
	var _pickedTime = $('#pickedTime').val();
	var _pickTime = $('#pickTime').val();
	var _params = 'name='+_name+'&noId='+_noId+'&dep='+_dep+'&pickedTime='+_pickedTime+'&description='+_description+'&flight='+
	_flight+'&driverMobile='+_driverMobile+'&driverName='+_driverName+'&maeilTime='+_mailTime+'&pickTime='+_pickTime+'&id='+_editId;
	if(_name==''){
		layer.alert('待接人员姓名不能为空！');
	}else{
		$.ajax({
			url:'/home/d/pro_task/update',
			type:'post',
			data:_params,
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					layer.alert('更新成功！',function(){
						_open('l1','/home/p/pro_task');
					})
				}else{
					layer.alert(res.msg);
				}
			}
		})
	}
}
function testNumber(_num){
	var reg = new RegExp("^[0-9]*$");
	if(reg.test(_num)&&_num.length==11){
		return true;
	}
	return false;
}
