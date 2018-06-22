//$(function() {
//	_setMap();
//})
//
//function _setMap() {
//	for ( var i in _data) {
//		var _item = _data[i];
//		var _htmlStr = '';
//		_htmlStr = '<img id="'
//				+ _item.lot
//				+ '"  src="/themes/img/A-car.png" width="20px;" style="position: absolute;top: '
//				+ _item._top + 'px;left: ' + _item._left
//				+ 'px;" class=" car none" role="button" type="button"   />';
//		$('.location').append(_htmlStr);
//	}
//	run();
//
//	setInterval(function() {
//
//		run();
//
//	}, 15000);
//}
//function run() {
//	$.get('/interface/sfc/device', function(res) {
//		var _num = 0;
//		var params = res.data.result;
//		for (idx in params) {
//			var sensorVo = params[idx];
//			if (sensorVo.available == 1) {
//				$('#' + sensorVo.description).attr('data-toggle','popover');
//				$('#' + sensorVo.description).attr('data-trigger','hover');
//				$('#' + sensorVo.description).attr('data-container','body');
//				if(parseInt(sensorVo.description)>=24){
//					$('#' + sensorVo.description).attr('data-placement','right');
//				}else if(parseInt(sensorVo.description)<=11){
//					$('#' + sensorVo.description).attr('data-placement','right');
//				}else{
//					$('#' + sensorVo.description).attr('data-placement','right');
//				}
//				var _carNo = '未知',_bluetooth = '无';
//				var _detail = '<b>驶入时间：</b>'+sensorVo.lastSeenTime+'</br><b>车位号：</b>'+sensorVo.description+'</br><b>Mac地址：</b>'+sensorVo.mac;
//				if(sensorVo.bluetooth!=null&&''!=sensorVo.bluetooth){
//					$.ajax({
//						url:'/common/bluetooth/findByUuid?uuid='+sensorVo.bluetooth,
//						type:'get',
//						data:'json',
//						async:false,
//						success:function(res){
//							if(res.data!=null){
//								_bluetooth = sensorVo.bluetooth;
//								_carNo = res.data.carNo;
//								if('未知'!=_carNo&&'无'!=_bluetooth){
//									_detail += '</br><b>蓝牙设备：</b>'+_bluetooth+'</br><b>车牌号码：</b>'+_carNo;
//								}
//							}
//						}
//					})
//				}
//				$('#' + sensorVo.description).attr('title','基本信息');
//				$('#' + sensorVo.description).attr('data-content',_detail);
//				$('#' + sensorVo.description).show();
//				$("[data-toggle='popover']").popover({html: true});
//			}else{
//				if($('#' + sensorVo.description).length>0){
//					$('#' + sensorVo.description).removeAttr('data-toggle');
//					_num ++;
//				}
//				$('#' + sensorVo.description).hide();
//			}
//			$('#leftLot').html(_num);
//		}
//	})
//}

$(function(){
	 var a = JSON.parse('[{"area":"A","lotNo":"1","x":"136","y":"256","direction":"vertical"},{"area":"A","lotNo":"2","x":"136","y":"297","direction":"vertical"},{"area":"A","lotNo":"3","x":"136","y":"340","direction":"vertical"},{"area":"A","lotNo":"4","x":"136","y":"381","direction":"vertical"},{"area":"A","lotNo":"5","x":"136","y":"424","direction":"vertical"},{"area":"A","lotNo":"10006","x":"136","y":"465","direction":"vertical"}]');

     buildMap(a);
     
     run();
     
     setInterval(function() {
    	 
         run();
         
     }, 10000);
})
function buildMap(baseData) {
    for (var idx in baseData) {
        var lotNo = baseData[idx].lotNo;
        var x = baseData[idx].x;
        var y = baseData[idx].y;
        var direction = baseData[idx].direction;
        $('.location').append($('<div class="car"  style="">').css({
            'top': x + 'px',
            'left': y + 'px'
        }).attr('data-ref', lotNo).attr('data-direction', direction));
    }
}

function run() {
    $.get('/interface/boken/view?areaId=20', function (response) {
    	if(response==null||response.data==null){
    		layer.msg('以下为测试数据');
    		response = {"code":"200","msg":"请求成功","data":[{"bluetooth":"2222222","available":1,"id":397,"addr":"10001"},{"bluetooth":"","available":0,"id":398,"addr":"10002"},{"bluetooth":"","available":1,"id":399,"addr":"10003"},{"bluetooth":"","available":1,"id":400,"addr":"10004"},{"bluetooth":"","available":1,"id":406,"addr":"10005"},{"bluetooth":"","available":1,"id":408,"addr":"10006"}]};
    	}
        var params = response.data;
        var totalCnt = params.length;
        $('.block-value').text(totalCnt);
        for (idx in params) {
            var sensorVo = params[idx];
            var target = $('.location').find('[data-ref="'+ sensorVo.addr + '"]');
            $(target).attr('onmouseover','getData("'+sensorVo.bluetooth+'")');
            var direction = target.attr('data-direction');
            if (sensorVo.available == 1) {
                target.addClass('occupied-' + direction);
            }else{
                target.removeClass('occupied-' + direction);
            }
        }
    })
}
