Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
$(function(){
	$('#datepicker').datepicker({
      autoclose: true,
      format: 'yyyy-mm-dd',
      lang: 'ch'
    });
    $("#datepicker").val(new Date().Format("yyyy-MM-dd"));
	$('._b').bind('click',function(){
		var _d = $(this).attr('data-ref');
		$('._b').removeClass('btn-info');
		$('._b').addClass('btn-default');
		$(this).addClass('btn-info');
		_type = _d;
		getData();
	})
	getData();
})
var _model = [{"id":32472,"inTime":1498109844000,"outTime":1498148520000,"areaId":5,"createTime":null,"description":"2027","mac":"020000fffe000118","times":644,"logId":null},{"id":32473,"inTime":1498125536000,"outTime":1498148520000,"areaId":5,"createTime":null,"description":"2026","mac":"020000fffe0000ef","times":383,"logId":null},{"id":32493,"inTime":1498175497000,"outTime":1498176040000,"areaId":5,"createTime":null,"description":"3012","mac":"020000fffe00011d","times":9,"logId":null},{"id":32494,"inTime":1498175497000,"outTime":1498176040000,"areaId":5,"createTime":null,"description":"3012","mac":"020000fffe00011d","times":9,"logId":null},{"id":32495,"inTime":1498175497000,"outTime":1498176040000,"areaId":5,"createTime":null,"description":"3013","mac":"020000fffe000153","times":9,"logId":null},{"id":32504,"inTime":1498178378000,"outTime":1498178455000,"areaId":5,"createTime":null,"description":"2074","mac":"020000fffe0001c9","times":1,"logId":null},{"id":32507,"inTime":1498174418000,"outTime":1498178621000,"areaId":5,"createTime":null,"description":"3033","mac":"020000fffe0001bd","times":70,"logId":null},{"id":32513,"inTime":1498179862000,"outTime":1498179906000,"areaId":5,"createTime":null,"description":"3038","mac":"020000fffe0000af","times":0,"logId":null},{"id":32516,"inTime":1498181432000,"outTime":1498181465000,"areaId":5,"createTime":null,"description":"3085","mac":"020000fffe000160","times":0,"logId":null},{"id":32518,"inTime":1498181488000,"outTime":1498181584000,"areaId":5,"createTime":null,"description":"3108","mac":"020000fffe000075","times":1,"logId":null},{"id":32521,"inTime":1498178801000,"outTime":1498183176000,"areaId":5,"createTime":null,"description":"3018","mac":"020000fffe000166","times":72,"logId":null}];
var optionData = [{value:0, name:'小于15分钟'},{value:0, name:'15分至1小时'}, {value:0, name:'1至2小时'}, {value:0, name:'2至4小时'}, {value:0, name:'4至8小时'}, {value:0, name:'8至24小时'}, {value:0, name:'大于24小时'}],resData = [];
var areaId = '19',_type = 1;
var myChart = echarts.init(document.getElementById('picturePlace'));
var option = {};
function getData(){
	var _date = $('#datepicker').val();
    optionData = [{value:0, name:'小于15分钟'},{value:0, name:'15分至1小时'}, {value:0, name:'1至2小时'}, {value:0, name:'2至4小时'}, {value:0, name:'4至8小时'}, {value:0, name:'8至24小时'}, {value:0, name:'大于24小时'}];
    resData = [];_data_[0]=0;_data_[1]=0;_data_[2]=0;_data_[3]=0;_data_[4]=0;_data_[5]=0; _data_[6]=0;
    _type = $('#_type').val();
    $.ajax({
        url:'/interface/suzhou/car?areaId='+areaId+'&dateStr='+_date+'&type='+_type,
        type:'get',
        dataType:'json',
        success:function(res){
            if(res.data!=null){
                for(var i in res.data){
                    resData.push(res.data[i]);
                }
            }
            if(resData.length==0){
//            	resData = _model;
            	changeData();
            }else{
                changeData();
            }
        }
    })
}
var _data_ = new Array();

function changeData(){
    var max = 0;var times = resData.length;
    for(var i in resData){
        max += resData[i].times;
        if(resData[i].times<15){
            optionData[0].value ++;
        }
        if(resData[i].times>=15&&resData[i].times<60){
            optionData[1].value ++;
        }
        if(resData[i].times>=60&&resData[i].times<120){
            optionData[2].value ++;
        }
        if(resData[i].times>=120&&resData[i].times<240){
            optionData[3].value ++;
        }
        if(resData[i].times>=240&&resData[i].times<480){
            optionData[4].value ++;
        }
        if(resData[i].times<1440&&resData[i].times>=480){
            optionData[5].value ++;
        }
        if(resData[i].times>=1440){
            optionData[6].value ++;
        }
    }
    _data_[0]=optionData[0].value;
    _data_[1]=optionData[1].value;
    _data_[2]=optionData[2].value;
    _data_[3]=optionData[3].value;
    _data_[4]=optionData[4].value;
    _data_[5]=optionData[5].value;
    _data_[6]=optionData[6].value;
    if(_type==2){
        $('#text').html('当月总停车次数:<font style="color: rgb(35,159,171);" >&nbsp;&nbsp;'+times+'&nbsp;&nbsp;</font>,平均时长:<font style="color: rgb(35,159,171);" >&nbsp;&nbsp;'+parseInt(max/times)+'&nbsp;&nbsp;</font>分钟');
    }else{
        $('#text').html('当日总停车次数:<font style="color: rgb(35,159,171);" >&nbsp;&nbsp;'+times+'&nbsp;&nbsp;</font>,平均时长:<font style="color:  rgb(35,159,171);" >&nbsp;&nbsp;'+parseInt(max/times)+'&nbsp;&nbsp;</font>分钟');
    }
    draw();
}

function draw(){
    option = {
        title: {
            x: 'center',
            text: '',
            subtext: '',
            link: '#'
        },
        tooltip: {
            trigger: 'item'
        },
        toolbox: {
            show: true,
            feature: {
                dataView: {show: false, readOnly: false},
                restore: {show: false},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        grid: {
            borderWidth: 0,
            y: 80,
            y2: 60
        },
        xAxis: [
            {
                type: 'category',
                show: false,
                data: ['小于15分钟', '15分钟至1小时', '1小时至2小时', '2小时至4小时', '4小时至8小时', '8小时至24小时', '大于24小时']
            }
        ],
        yAxis: [
            {
                type: 'value',
                show: false
            }
        ],
        series: [
            {
                name: '不同时间区间停车次数:',
                type: 'bar',
                itemStyle: {
                    normal: {
                        color: function(params) {
                            var colorList = [
                                '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                                '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                                '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
                            ];
                            return colorList[params.dataIndex]
                        },
                        label: {
                            show: true,
                            position: 'top',
                            formatter: '{b}\n\n{c}辆'
                        }
                    }
                },
                data:_data_

            }
        ]
    };
    myChart.setOption(option,true);
}