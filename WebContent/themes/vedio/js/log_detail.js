$(function(){
	_getData();
})

function _getData(){
	var logDetailId = getSessionStorage('logDetailId');
	$.ajax({
		url:'/d/vedioLog/findById?id='+logDetailId,
		type:'get',
		dataType:'json',
		success:function(res){
			var taskId = res.data[0].taskId;
			var picNum = res.data[0].picNumber;
			_getData2(res.data,taskId,picNum);
		}
	})
}

function _getData2(_list,taskId,picNumber){
	$.ajax({
		url:'/d/vedioTask/find?id='+taskId,
		type:'get',
		dataType:'json',
		success:function(res){
			_getData3(_list,picNumber,res.data);
		}
	})
}

function _getData3(_list,num,task){
	$.ajax({
		url:'/d/vedioArea/find?id='+task.areaId,
		type:'get',
		dataType:'json',
		success:function(res){
			_draw(_list,num,task,res.data);
		}
	})
}
var paper, c_1, c_2, c_3, c_4, _path, c_arr = [];
var width, height;
function _draw(_list,num,task,area){
        width = parseInt(area.width) / 2;
        height = parseInt(area.height) / 2;
        var picPath = '/themes/upload_files/vedio/'+task.name+'/ffmpeg_'+num+'.jpg';
        var _htmlStr = '<div id="paper" style="background: url(' +picPath + '); width: ' + width + 'px; height: ' + height + 'px; background-repeat: no-repeat; background-size: 100% 100%; -moz-background-size: 100% 100%;" ></div>'
        $('#datas').html(_htmlStr);
        paper = new Raphael(document.getElementById("paper"), width, height);
        var _redPointAttr = { stroke: 'red','stroke-width': 2,fill: 'red'};
        var _redLinetAttr = { stroke: 'red','stroke-width': 2,'arrow-end': 'none'};
        var _bluePointAttr = { stroke: 'blue','stroke-width': 2,fill: 'blue'};
        var _blueLinetAttr = { stroke: 'blue','stroke-width': 2,'arrow-end': 'none'};
        var _greenPointAttr = { stroke: 'green','stroke-width': 2,fill: 'green'};
        var _greenLinetAttr = { stroke: 'green','stroke-width': 2,'arrow-end': 'none'};
        c_1 = paper.circle(parseInt(area.x1), parseInt(area.y1), 2).attr(_redPointAttr);
        c_2 = paper.circle(parseInt(area.x2), parseInt(area.y2),2).attr(_redPointAttr);
        c_3 = paper.circle(parseInt(area.x3), parseInt(area.y3), 2).attr(_redPointAttr);
        c_4 = paper.circle(parseInt(area.x4), parseInt(area.y4), 2).attr(_redPointAttr);
        var pathStr = 'M ' + c_1.attr('cx') + ' ' + c_1.attr('cy') + ' L '
                + c_2.attr('cx') + ' ' + c_2.attr('cy') + ' L '
                + c_3.attr('cx') + ' ' + c_3.attr('cy') + ' L '
                + c_4.attr('cx') + ' ' + c_4.attr('cy') + ' L '
                + c_1.attr('cx') + ' ' + c_1.attr('cy');
        _path = paper.path(pathStr).attr(_redLinetAttr);
        for(var i in _list){
        	var obj = _list[i];
        	var car1= paper.circle(parseInt(obj.carX)/2, parseInt(obj.carY)/2, 2).attr(_bluePointAttr);
        	var car2= paper.circle(parseInt(obj.carX2)/2, parseInt(obj.carY)/2, 2).attr(_bluePointAttr);
        	var car3= paper.circle(parseInt(obj.carX2)/2, parseInt(obj.carY2)/2, 2).attr(_bluePointAttr);
        	var car4= paper.circle(parseInt(obj.carX)/2, parseInt(obj.carY2)/2, 2).attr(_bluePointAttr);
        	var pathStr = 'M ' + car1.attr('cx') + ' ' + car1.attr('cy') + ' L '
            + car2.attr('cx') + ' ' + car2.attr('cy') + ' L '
            + car3.attr('cx') + ' ' + car3.attr('cy') + ' L '
            + car4.attr('cx') + ' ' + car4.attr('cy') + ' L '
            + car1.attr('cx') + ' ' + car1.attr('cy');
        	paper.path(pathStr).attr(_blueLinetAttr);
        	if(obj.cpX!=''){
        		var cp1= paper.circle(parseInt(obj.cpX)/2, parseInt(obj.cpY)/2, 2).attr(_greenPointAttr);
            	var cp2= paper.circle(parseInt(obj.cpX2)/2, parseInt(obj.cpY)/2, 2).attr(_greenPointAttr);
            	var cp3= paper.circle(parseInt(obj.cpX2)/2, parseInt(obj.cpY2)/2, 2).attr(_greenPointAttr);
            	var cp4= paper.circle(parseInt(obj.cpX)/2, parseInt(obj.cpY2)/2, 2).attr(_greenPointAttr);
            	var pathStr = 'M ' + cp1.attr('cx') + ' ' + cp1.attr('cy') + ' L '
                + cp2.attr('cx') + ' ' + cp2.attr('cy') + ' L '
                + cp3.attr('cx') + ' ' + cp3.attr('cy') + ' L '
                + cp4.attr('cx') + ' ' + cp4.attr('cy') + ' L '
                + cp1.attr('cx') + ' ' + cp1.attr('cy');
            	paper.path(pathStr).attr(_greenLinetAttr);
        	}
        }
}



function getSessionStorage(_key){
	if(window.sessionStorage){     
		return window.sessionStorage.getItem(_key);
	}
}