$(function() {
	_getArea();
	laydate.render({
		elem : '#time',
		type : 'time',
		range : true
	});
})

function _getArea() {
	$.ajax({
		url : '/home/d/area/list?id=26',
		type : 'get',
		dataType : 'json',
		success : function(res) {
			if (res.data != null) {
				new Vue({
					el : '#area',
					data : {
						datas : res.data
					}
				})
			}
		}
	})
}
function _save(){
    var period = $('#period').val(),
    amountOfMoney = $('#amountOfMoney').val(),
    amountOfMoneyForNotEnough = $('#amountOfMoneyForNotEnough').val(),
    type = $('#type').val(),
    beginTime = $('#beginTime').val(),
    endTime = $('#endTime').val(),
    freeMoneyAmountPeriod = $('#freeMoneyAmountPeriod').val(),
    maxAmountOfMoney = $('#maxAmountOfMoney').val(),
    chargingRuleGroup = $('#chargingRuleGroup').val();
	if(period==null||period==''||amountOfMoney==null||amountOfMoney==''||amountOfMoneyForNotEnough==null||amountOfMoneyForNotEnough==''){
		layer.alert('请完善收费规则！');
	}else{
		var params = 'period='+period+'&amountOfMoney='+amountOfMoney+'&amountOfMoneyForNotEnough='+amountOfMoneyForNotEnough+'&type='+type+
		'&beginTime='+beginTime+'&endTime='+endTime+"&freeMoneyAmountPeriod="+freeMoneyAmountPeriod+'&maxAmountOfMoney='+maxAmountOfMoney+
		'&chargingRuleGroup='+chargingRuleGroup;
		$.ajax({
			url:'/home/d/mofang/add_rule',
			type:'post',
			data:params,
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					layer.alert('操作成功！',function(){
						_open('4','/home/p/order/rule');
					})
				}
			}
		})
	}
}