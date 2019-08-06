$(function(){
	_getData(0,1)
})

function _save(){
	var _mac = $('#mac').val();
	var _value1 = $('#value1').val();
	var value2 = $('#value2').val();
	var cmdName = $('#cmdName').val();
	var params = 'mac='+_mac+'&value1='+_value1+'&value2='+value2+'&cmdName='+cmdName;
	$.ajax({
		url:'/iot/log/save',
		data:params,
		dataType:'json',
		type:'post',
		success:function(res){
			if('200'==res.code){
				layer.alert('下发成功！');
				_getData(0,1);
			}
		}
	})
}
var _d = '';
var  _nowPage = 0,_max = 0,_mac = '';
function _getData(_type,_index){
	var _data = {};
	_data.p = _getPage(_type,_index);
	if(_data.p!=-1){
		$.ajax({
			url:'/iot/log/cmdList?p='+_data.p,
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
}

