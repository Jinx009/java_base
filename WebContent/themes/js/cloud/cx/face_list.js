$(function(){
	_getData(0,1);
})
var _d = '',_l = '',_p = '';
var  _nowPage = 0,_max = 0,_haha;
function _getData(_type,_index){
	var _data = {};
	_data.p = _getPage(_type,_index);
	if(_data.p!=-1){
		_nowPage = _data.p;
		$.ajax({
			url:'/home/cloud/face/faceFactoryUsers?p='+_data.p,
			dataType:'json',
			type:'post',
			success:function(res){
				$('#nnn').html('当前第'+_data.p+'页，共'+res.data.page.pages+'页');
				_max = res.data.page.pages;
				for(var i in res.data.list){
					res.data.list[i].createTime = toDateTime(res.data.list[i].createTime);
					res.data.list[i].type = testType(res.data.list[i].accessWay);
					res.data.list[i].num = i;
					if(res.data.list[i].openDoorPhotoList!=null&&res.data.list[i].openDoorPhotoList!=''){
						if(res.data.list[i].openDoorPhotoList.indexOf(',') >= 0){
							res.data.list[i].pic = 'http://'+res.data.list[i].photoHost+'/'+res.data.list[i].openDoorPhotoList.split(',')[0];
						}else{
							res.data.list[i].pic = 'http://'+res.data.list[i].photoHost+'/'+res.data.list[i].openDoorPhotoList;
						}
					}
				}
				_haha = res.data.list;
				if(''==_l){
					_l = new Vue({
						el:'#log',
						data:{
							logs:res.data.list
						}
					})
				}else{
					_l.logs = res.data.list;
				}
			}
		})
	}
}