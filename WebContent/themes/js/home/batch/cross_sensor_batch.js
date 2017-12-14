$(function(){
	_getLocation();
})
var _d = '',_area='',_areas = '';
function _getData(){
	var _data = {};
	_data.mac = $('#mac').val();
	_data.areaId = $('#area').val();
	if(_data.areaId==null||_data.areaId==''){
		_data.areaId = 0;
	}
	$.ajax({
		url:'/d/device_cross_sensor/all/1_0',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
		type:'post',
		success:function(res){
			for(var i in res.data){
				res.data[i].lastSeenTime = toDateTime(res.data[i].lastSeenTime);
			}
			if(''==_d){
				_d = new Vue({
					el:'#datas',
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

function _getArea(){
	var _locationId = $('#location').val();
	if(0==_locationId){
		var _areaArray = new Array();
		_areaArray[0] = {};
		_areaArray[0].id = 0,_areaArray[0].name = '请选择area';
		if(_area==''){
			_area = new Vue({
				el:'#area',
				data:{
					areas:_areaArray
				}
			})
		}else{
			_area.areas = _areaArray
		}
	}else{
		var _data = {};
		_data.locationId = _locationId;
		$.ajax({
			url:'/d/business_area/all/1_0',
			dataType:'json',
			data:JSON.stringify(_data),
			contentType:'application/json;charSet=utf8',
			type:'post',
			success:function(res){
				_area.areas = res.data
			}
		})
	}
}

function _getAreas(){
	var _locationId = $('#locations').val();
	var _data = {};
	_data.locationId = _locationId;
	$.ajax({
		url:'/d/business_area/all/1_0',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
		type:'post',
		success:function(res){
			if(_areas==''){
				_areas = new Vue({
					el:'#areas',
					data:{
						areas:res.data
					}
				})
			}else{
				_areas.areas = res.data
			}
		}
	})
}

function _showArea(){
	$('#areaBox').show();
	$('.content').css('opacity',0.9);
}

function _showUpdateProperties(){
	$('#updateBox').show();
	$('.content').css('opacity',0.9);
}

function _hideAllNew(){
	$('#areaBox').hide();
	$('#updateBox').hide();
	$('.content').css('opacity',1);
}

function _getLocation(){
	$.ajax({
		url:'/d/business_location/all/1_0',
		dataType:'json',
		type:'post',
		success:function(res){
			var _locationArray = new Array();
			_locationArray[0] = {};
			_locationArray[0].id = 0,_locationArray[0].name = '请选择location';
			for(var i in res.data){
				_locationArray.push(res.data[i]);
			}
			new Vue({
				el:'#location',
				data:{
					locations:_locationArray
				}
			})
			new Vue({
				el:'#locations',
				data:{
					locations:res.data
				}
			})
			_getArea();
			_getAreas();
			_getData();
		}
	})
}
function _saveArea(){
	 var chk_value =[];
	 var areaId = $('#areas').val();
     $('input[name="chkItem"]:checked').each(function(){
         chk_value.push($(this).val());
     });
     if(chk_value.length==0){
         layer.alert('只要需要选择一个sensor!');
     }else{
    	 var _data = {};
         _data.mac = chk_value;
         _data.areaId = areaId;
         $.ajax({
     		url:'/d/device_cross_sensor/setArea/1_0',
     		dataType:'json',
     		data:JSON.stringify(_data),
     		contentType:'application/json;charSet=utf8',
     		type:'post',
     		success:function(res){
     			if('200'==res.code){
     				layer.alert('设置成功！',function(){
     					location.reload();
     				})
     			}else{
     				layer.alert(res.msg);
     			}
     		}
     	})
     }
}

function _saveUpdate(){
     var obj = {};
     obj.hth = $('#hth').val();
     obj.lth = $('#lth').val();
     obj.w_hth = $('#w_hth').val();
     obj.recount = $('#recount').val();
     obj.w_lth = $('#w_lth').val();
     obj.s_in_time = $('#s_in_time').val();
     obj.s_out_time = $('#s_out_time').val();
     obj.ex_itv = $('#ex_itv').val();
     obj.z_th = $('#z_th').val();
     obj.all = $('#all').val();
     var str = '';
     var chk_value =[];
	 var areaId = $('#areas').val();
     $('input[name="chkItem"]:checked').each(function(){
         chk_value.push($(this).val());
     });
     if(chk_value.length==0){
         layer.alert('只要需要选择一个sensor!');
     }else{
    	 var _data = {};
    	 for(var i in chk_value){
             str += chk_value[i]+',';
         }
         obj.nodes = str.substring(0,str.length-1);
         _data.job_detail = obj;
         _data.cmd = 'patchsetpara';
         _data.id = chk_value[0];
         $.ajax({
     		url:'/d/device_cross_sensor/setUpdate/1_0',
     		dataType:'json',
     		data:JSON.stringify(_data),
     		contentType:'application/json;charSet=utf8',
     		type:'post',
     		success:function(res){
     			if('200'==res.code){
     				layer.alert('任务添加成功！',function(){
     					_open('1','/p/device/job/list');
     				})
     			}else{
     				layer.alert(res.msg);
     			}
     		}
     	})
     }
}