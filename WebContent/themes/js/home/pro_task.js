$(function(){
	laydate.render({
	  elem: '#pickDate'
	});
	laydate.render({
	  elem: '#fromDate'
	});
	laydate.render({
	  elem: '#endDate'
	});
	laydate.render({
	  elem: '#pickTime'
	  ,type: 'time'
	});
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
		}
	})
}
function _go(){
	var _fromDate = $('#fromDate').val();
	var _endDate = $('#endDate').val();
	var _status = $('#status').val();
	window.open('/home/d/pro_task/excel?fromDate='+_fromDate+'&endDate='+_endDate+'&status='+_status,'_self');
}
var _d = '';
var  _nowPage = 0,_max = 0;
var _bd = [];
function _getData(_type,_index){
	var _data = {};
	var _fromDate = $('#fromDate').val();
	var _endDate = $('#endDate').val();
	var _status = $('#status').val();
	_data.p = _getPage(_type,_index);
	if(_data.p!=-1){
		$.ajax({
			url:'/home/d/pro_task/list?p='+_data.p+'&fromDate='+_fromDate+'&endDate='+_endDate+'&status='+_status,
			dataType:'json',
			type:'post',
			success:function(res){
				$('#_end').bind('click',function(){
					_getData(0,res.data.page.pages);
				})
				_max = res.data.page.pages;
				_bd = res.data.list;
				for(var i in res.data.list){
					res.data.list[i].pickTime = toDateTime(res.data.list[i].pickTime);
					res.data.list[i].index = i;
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
	var _index = parseInt($(_e).attr('index'));
	_editId = _id;
	var _d = _bd[_index];
	$('#name').val(_d.name);
	$('#mobilePhone').val(_d.mobilePhone);
	$('#dep').val(_d.dep);
	$('#description').val(_d.description);
	$('#number').val(_d.number);
	$('#flight').val(_d.flight);
	$('#pickTime').val(_d.pickTime.split(' ')[1]);
	$('#pickDate').val(_d.pickTime.split(' ')[0]);
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
	var _mobilePhone = $('#mobilePhone').val();
	var _number = $('#number').val();
	var _dep = $('#dep').val();
	var _flight = $('#flight').val();
	var _description = $('#description').val();
	var _driverMobile = $('#driver').val();
	var _driverName=$("#driver").find("option:selected").text();
	var _pickDate = $('#pickDate').val();
	var _pickTime = $('#pickTime').val();
	var _params = 'name='+_name+'&mobilePhone='+_mobilePhone+'&dep='+_dep+'&number='+_number+'&description='+_description+'&flight='+
	_flight+'&driverMobile='+_driverMobile+'&driverName='+_driverName+'&pickDate='+_pickDate+'&pickTime='+_pickTime+'&id='+_editId;
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
