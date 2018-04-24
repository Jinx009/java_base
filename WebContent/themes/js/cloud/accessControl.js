$(function(){
	_getData(0,1);
	_getData1();
})
var _d = '',_l = '',_p = '';
var  _nowPage = 0,_max = 0,_haha;
function _getData(_type,_index){
	var _data = {};
	_data.p = _getPage(_type,_index);
	if(_data.p!=-1){
		_nowPage = _data.p;
		$.ajax({
			url:'/home/cloud/accessControl/log?p='+_data.p,
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
function testType(_type){
	if(1===_type){
		return '通话开锁';
	}
	if(2===_type){
		return '监视开锁';
	}
	if(3===_type){
		return '刷卡开锁';
	}
	if(4===_type){
		return '密码开锁';
	}
	if(5===_type){
		return '通知开锁';
	}
	if(6===_type){
		return '中心机通话开锁';
	}
	if(7===_type){
		return '室内机通话开锁';
	}
	if(8===_type){
		return '移动App通话开锁';
	}
	if(9===_type){
		return '通话开锁';
	}
	if(14===_type){
		return 'IC/ID卡刷卡开锁';
	}
	if(27===_type){
		return '人脸识别开锁';
	}
	return '其他开锁';
}
function _openLayer(_e){
	var _num = $(_e).attr('num');
	var _f = _haha[_num];
	layer.open({
		  type: 1,
		  title: false,
		  closeBtn: 0,
		  area: ['700px', '400px'],
		  shadeClose: true,
		  skin: 'yourclass',
		  content: '<fieldset class="layui-elem-field layui-field-title"style="margin-top: 30px;"><legend>记录详情</legend></fieldset><div class="layui-form" style="margin-left: 10px;width: 650px;"><table class="layui-table" >'+
    '<tbody><tr><th width="200px;" ><b>刷卡人</b></th><td width="200px;"  >'+_f.personnelName+'</td><td width="250px;"  rowspan="4" >'+
        	'<img width="100%;" style="max-width: 350px;" alt="" src="'+_f.pic+'"  >'+
        '</td></tr><tr><th><b>刷卡类型</b></th><td >'+_f.type+'</td></tr><tr><th><b>区域</b></th><td >'+_f.deviceName+'</td></tr><tr>'+
        '<th><b>刷卡时间</b></th><td >'+_f.createTime+'</td></tr></tbody></table></div>'
		});
}
function _getData1(){
	$.ajax({
		url:'/home/cloud/accessControl/log?p=1',
		dataType:'json',
		type:'post',
		success:function(res){
			for(var i in res.data.list){
				res.data.list[i].createTime = toDateTime(res.data.list[i].createTime);
			}
			if(res.data.list[0].openDoorPhotoList.indexOf(',') >= 0){
				$('#img').attr('src','http://'+res.data.list[0].photoHost+'/'+res.data.list[0].openDoorPhotoList.split(',')[0]);
			}else{
				$('#img').attr('src','http://'+res.data.list[0].photoHost+'/'+res.data.list[0].openDoorPhotoList);
			}
			$('#name').html(res.data.list[0].personnelName);
			var type = '进门';
			if(res.data.list[0].direction==2){
				type = '出门';
			}
			$('#way').html(type);
			$('#time').html(res.data.list[0].createTime);
			$('#deviceName').html(res.data.list[0].deviceName);
		}
	})
}