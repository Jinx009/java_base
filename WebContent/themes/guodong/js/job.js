$(function(){
	_getDevData();
})
var _data = '';
function _getDevData(){
	$.ajax({
		url:'/interface/gd/data',
		type:'get',
		dataType:'json',
		success:function(res){
			new Vue({
				el:'#datas',
				data:{
					datas:res.data
				}
			})
			_getJobData();
		}
	})
}
function _getJobData(){
	$.ajax({
		url:'/interface/gd/jobs',
		type:'get',
		dataType:'json',
		success:function(res){
			for(var i in res.data){
				res.data[i].createTime = formatDateTime(res.data[i].createTime);
				if(res.data[i].time_s!=null&&res.data[i].time_s!=''){
					res.data[i].time_s = formatDateTime(res.data[i].time_s);
				}
			}
			new Vue({
				el:'#jobs',
				data:{
					datas:res.data
				}
			})
		}
	})
}

function _saveOrUpdate(_devEui,_status){
	if(_devEui==''){
		_devEui = $('#datas').val();
	}else{
		_devEui = $(_devEui).attr('id');
	}
	$.ajax({
		url:'/interface/gd/job?devEui='+_devEui+'&status='+_status,
		type:'post',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				layer.alert('操作成功！',function(){
					location.reload();
				});
			}else{
				layer.alert(res.msg);
			}
		}
		
	})
}
function formatDateTime(inputTime) {    
    var date = new Date(inputTime);  
    var y = date.getFullYear();    
    var m = date.getMonth() + 1;    
    m = m < 10 ? ('0' + m) : m;    
    var d = date.getDate();    
    d = d < 10 ? ('0' + d) : d;    
    var h = date.getHours();  
    h = h < 10 ? ('0' + h) : h;  
    var minute = date.getMinutes();  
    var second = date.getSeconds();  
    minute = minute < 10 ? ('0' + minute) : minute;    
    second = second < 10 ? ('0' + second) : second;   
    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;    
};  
