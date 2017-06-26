$(function(){
   $('#datepicker').datepicker({
      autoclose: true,
      format: 'yyyy-mm-dd',
      lang: 'ch'
    });
    $("#datepicker").val(new Date().Format("yyyy-MM-dd"));
	_i = showLoad();
	$('._b').bind('click',function(){
		var _d = $(this).attr('data-ref');
		$('._b').removeClass('btn-info');
		$('._b').addClass('btn-default');
		$(this).addClass('btn-info');
		app.type = _d;
		getData();
	})
	getData();
})
var app = app || {};
app.type = 0;
window.app = app;
app.chartColors = ['#D3ECF8', '#f0ad4e', '#444', '#888', '#555', '#999', '#bbb', '#ccc', '#eee'];
var _i = -1;
function getData(){
	if(-1==_i){
		_i = showLoad();
	}
	var date = $('#datepicker').val();
	var params = 'locationId=14&op='+app.type+'&dateStr='+date;
	$.ajax({
		url:'/home/d/locationStatus',
		type:'post',
		dataType:'json',
		data:params,
		success:function(res){
			closeLoad(_i);
			if('200'==res.code){
				if(res.data!=null){
					 window.app.bufferedData = handle(app.type,date, res.data);
					 draw();
					 $(window).resize(app.debounce(draw,250));
				}else{
					layer.alert('暂无数据！');
				}
			}else if('8001'==res.code){
				layer.alert('您无权限操作此项！');
			}else{
				layer.msg('以下测试数据');
				res = {"code":"200","msg":"请求成功","data":[{"period":"06","利用率":"3.0"},{"period":"07","利用率":"5.0"},{"period":"08","利用率":"8.0"},{"period":"09","利用率":"24.0"},{"period":"10","利用率":"62.0"},{"period":"11","利用率":"76.0"},{"period":"12","利用率":"75.0"},{"period":"13","利用率":"77.0"},{"period":"14","利用率":"78.0"},{"period":"15","利用率":"74.0"},{"period":"16","利用率":"71.0"},{"period":"17","利用率":"60.0"},{"period":"18","利用率":"37.0"},{"period":"19","利用率":"21.0"},{"period":"20","利用率":"14.0"},{"period":"21","利用率":"8.0"},{"period":"22","利用率":"5.0"}]};
				window.app.bufferedData = handle(app.type,date, res.data);
				draw();
				$(window).resize(app.debounce(draw,250));
//				layer.alert(res.msg);
			}
		}
	})
}

function handle(op, date, params){
    var hourMock = [];
    if (op == 0) {
        for (var i = 1; i <= 24; i++) {
            var unit = {period: i, "利用率": 0};
            for (idx in params) {
                if (i == params[idx].period) {
                    unit = params[idx];
                    break;
                }
            }
            hourMock.push(unit);
        }
    }else if(op == 1){
        var array = new Array();
        for(i=7;i>=1;i--){
            var d = addDate(date, -i);
            array.push(d);
        }
        array.push(date);
        for(var i=0;i<array.length;i++){
            var unit = {period: array[i], "利用率": 0};
            for (var idx in params) {
                if (array[i] == params[idx].period) {
                    unit = params[idx];
                    break;
                }
            }
            hourMock.push(unit);
        }
    }else if(op ==2){
        var array = new Array();
        var startDate = addMonth(date, -1);
        while(startDate != date){
            array.push(startDate);
            startDate = addDate(startDate, 1);
        }
        array.push(date);
        for(var i=0;i<array.length;i++){
            var unit = {period: array[i], "利用率": 0};
            for (var idx in params) {
                if (array[i] == params[idx].period) {
                    unit = params[idx];
                    break;
                }
            }
            hourMock.push(unit);
        }
    }else if(op == 3){
        var array = new Array();
        var startDate = addYear(date, -1);
        date = new Date(Date.parse(date.replace(/-/g,"/"))).Format("yyyy-MM");
        while(startDate != date){
            array.push(startDate);
            startDate = addMonthYM(startDate, 1);
        }
        array.push(date);
        for(var i=0;i<array.length;i++) {
            var unit = {period: array[i], "利用率": 0};
            for (var idx in params) {
                if (array[i] == params[idx].period) {
                    unit = params[idx];
                    break;
                }
            }
            hourMock.push(unit);
        }
    }
    return hourMock;
}

function draw(){
    $('#area-chart').empty();
    Morris.Area({
        element: 'area-chart',
        data: window.app.bufferedData,
        xkey: 'period',
        ykeys: ['利用率'],
        labels: ['利用率'],
        pointSize: 3,
        ymax: 100,
        ymin: 0,
        parseTime: false,
        hideHover: 'auto',
        postUnits: '%',
        lineColors: [app.chartColors[0]],
        xLabelFormat: function (x) {
            if (0==app.type) {
                return x.label + ":00";
            } else {
                return x.label;
            }
        }
    });
}

app.debounce = function (func, wait, immediate) {
    var timeout, args, context, timestamp, result;
    return function () {
        context = this;
        args = arguments;
        timestamp = new Date();
        var later = function () {
            var last = (new Date()) - timestamp;
            if (last < wait) {
                timeout = setTimeout(later, wait - last);
            } else {
                timeout = null;
                if (!immediate) result = func.apply(context, args);
            }
        };
        var callNow = immediate && !timeout;
        if (!timeout) {
            timeout = setTimeout(later, wait);
        }
        if (callNow) result = func.apply(context, args);
        return result;
    };
}

function addDate(date,days){
    var d=new Date(Date.parse(date.replace(/-/g,   "/")));
    d.setDate(d.getDate()+days);
    var m=d.getMonth()+1;
    return new Date(d.getFullYear()+'/'+m+'/'+d.getDate()).Format("yyyy-MM-dd");
}

function addMonth(date,month){
    var d=new Date(Date.parse(date.replace(/-/g,   "/")));
    var m=d.getMonth() + month;
    d.setMonth(m);
    m = d.getMonth() + 1;
    return new Date(d.getFullYear()+'/'+m+'/'+d.getDate()).Format("yyyy-MM-dd");
}
function addMonthYM(date, month){
    date += "-01";
    var d=new Date(Date.parse(date.replace(/-/g,   "/")));
    var m=d.getMonth()+month;
    d.setMonth(m);
    m = d.getMonth() + 1;
    return new Date(d.getFullYear()+'/'+m+'/'+d.getDate()).Format("yyyy-MM");
}
function addYear(date, year){
    var d=new Date(Date.parse(date.replace(/-/g,   "/")));
    var m=d.getMonth()+1;
    var y= d.getFullYear() + year;
    d.setFullYear(y);
    return new Date(d.getFullYear()+'/'+m+'/'+d.getDate()).Format("yyyy-MM");
}