$(function() {
	getData();
})
var _data_ = [{value:335, name:'摩拜单车'},
			  {value:310, name:'OFO'},
			  {value:234, name:'其他'}];
var _mb = 0,_ofo =0,_other = 0;
function getData() {
	$.ajax({
		url : '/home/d/bikes',
		type : 'post',
		dataType : 'json',
		data : 'mac=mac22222',
		success : function(res) {
			if('200'==res.code){
				for(var i in res.data){
					if(isContains(res.data[i].uuid,'ofo')){
						_ofo++;
					}else if(isContains(res.data[i].uuid,'mb')){
						_mb++;
					}else{
						_other++;
					}
				}
				$('#text').html('摩拜单车：<font style="color:red;">'+_mb+' </font>辆；OFO： <font style="color:red;">'+_ofo+' </font>辆；其他：<font style="color:red;"> '+_other+'  </font>辆。');
				_data_[0].value = _mb;
				_data_[1].value = _ofo;
				_data_[2].value = _other;
				myChart.setOption(option,true);
			}
		}
	})
}
var myChart = echarts.init(document.getElementById('picturePlace'));
var option = {
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient : 'vertical',
        x : 'left',
        data:['摩拜单车','OFO','其他']
    },
    toolbox: {
        show : true,
        feature : {
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    series : [
        {
            name:'车辆来源',
            type:'pie',
            radius : ['50%', '70%'],
            itemStyle : {
                normal : {
                    label : {
                        show : false
                    },
                    labelLine : {
                        show : false
                    }
                },
                emphasis : {
                    label : {
                        show : true,
                        position : 'center',
                        textStyle : {
                            fontSize : '30',
                            fontWeight : 'bold'
                        }
                    }
                }
            },
            data:_data_
        }
    ]
};
	              
function isContains(str, substr) {
    return str.indexOf(substr) >= 0;
}