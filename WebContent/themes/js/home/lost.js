$(function(){
	_getData();
})
function _getData(){
		$.ajax({
			url:'/iot/device/broken',
			dataType:'json',
			type:'post',
			success:function(res){
				for(var i in res.data){
					if(res.data[i].dataTime!=null){
						res.data[i].dataTime = toDateTime(res.data[i].dataTime);
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
