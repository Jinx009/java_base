$(function(){
	$('#datepicker').val(new Date().Format('yyyy-MM-dd'));
	laydate.render({
		elem : '#datepicker',
	});
	_getDevice();
//	setInterval(function() {
//
//		_getData();
//
//	}, 15000);
})

function _getDevice(){
	$.ajax({
		url:'/home/d/device/sensor',
		type:'post',
		dataType:'json',
		success:function(res){
			_getData();
			new Vue({
				el:'#mac',
				data:{
					devices:res.data.result
				}
			})
		}
	})
}
var _data = '';
function _getData(){
	var dateStr = $('#datepicker').val();
	var mac = $('#mac').val();
	$.ajax({
		url:'/home/d/parking_data/inOut?dateStr='+dateStr+'&mac='+mac,
		type:'get',
		dataType:'json',
		success:function(res){
			for(var i in res.data){
				res.data[i].changeTime = toDateTime(res.data[i].changeTime);
			}
			if(_data ==''){
				_data = new Vue({
					el:'#datas',
					data:{
						datas:res.data
					}
				})
			}else{
				_data.datas = res.data;
			}
		}
	})
}