$(function(){
	_getData(0,1);
	_getRouters();
})
var _d = '';
var  _nowPage = 0,_max = 0;
function _getData(_type,_index){
	var _data = {};
	_data.p = _getPage(_type,_index);
	if(_data.p!=-1){
		$.ajax({
			url:'/d/device_job/list/1_0',
			dataType:'json',
			data:JSON.stringify(_data),
			contentType:'application/json;charSet=utf8',
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

function _getRouters(){
	$.ajax({
		url:'/d/device_router/all/1_0',
		dataType:'json',
		contentType:'application/json;charSet=utf8',
		type:'post',
		success:function(res){
			new Vue({
				el:'#target',
				data:{
					routers:res.data
				}
			})
		}
	})
}

function _delete(_e){
	var _id = $(_e).attr('id');
	var _data = {};
	_data.id = _id;
	$.ajax({
		url:'/d/device_job/delete/1_0',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
		type:'post',
		success:function(res){
			if('200'==res.code){
				layer.alert('放弃成功！',function(){
					location.reload();
				});
			}else{
				layer.alert(res.msg);
			}
		}
	})
}

function _save(){
	var _target = $('#target').val();
	var _jobDetail = $('#jobDetail').val();
	var _cmd = $('#cmd').val();
	var _data = {};
	_data.mac = _target;
	_data.cmd = _cmd;
	_data.job_detail = _jobDetail;
	$.ajax({
		url:'/d/device_job/base_create/1_0',
		type:'post',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
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