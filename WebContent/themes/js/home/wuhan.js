$(function(){
	_getData();
})
function _getData(){
		$.ajax({
			url:'/iot/device/wuhan',
			dataType:'json',
			type:'post',
			success:function(res){
				for(var i in res.data){
					if(res.data[i].dataTime!=null){
						res.data[i].dataTime = toDateTime(res.data[i].dataTime);
					}
					if(res.data[i].updateTime!=null){
						res.data[i].updateTime = toDateTime(res.data[i].updateTime);
					}
				}
					_d = new Vue({
						el:'#datas',
						data:{
							datas:res.data
						}
					})
			}
		})
}

