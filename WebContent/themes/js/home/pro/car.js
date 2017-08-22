$(function(){
	 var a = JSON.parse('[{"area":"A","lotNo":"10001","x":"136","y":"256","direction":"vertical"},{"area":"A","lotNo":"10002","x":"136","y":"297","direction":"vertical"},{"area":"A","lotNo":"10003","x":"136","y":"340","direction":"vertical"},{"area":"A","lotNo":"10004","x":"136","y":"381","direction":"vertical"},{"area":"A","lotNo":"10005","x":"136","y":"424","direction":"vertical"},{"area":"A","lotNo":"10006","x":"136","y":"465","direction":"vertical"}]');

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
    $.get('/home/d/view?areaId=17', function (response) {
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

function getData(_uuid){
//	if(_uuid!=''&&_uuid!=null){
//		$.ajax({
//			url:'/home/d/carNo?v='+_uuid,
//			type:'post',
//			data:'uuid='+_uuid,
//			dataType:'json',
//			ansyc:true,
//			success:function(res){
//				if('200'==res.code&&res.data!=null){
//					layer.alert('当前停靠车辆为：'+res.data);
//				}else{
//					layer.msg('未找到相关车牌信息！');
//				}
//			}
//		})
//	}else{
//		layer.msg('未找到相关车牌信息！');
//	}
}

