function _newRole(){
	 _open_('4','/home/p/order/role','/home/p/order/rule_add');
}
$(function(){
	_getData();
})
function _getData(){
	$.ajax({
		url:'/home/d/mofang/rule',
		type:'get',
		dataType:'json',
		success:function(res){
			if(res.data!=null){
				new Vue({
	   				  el: '#data',
	   				  data:{datas:res.data.data.chargingRules}
	    		})
			}
		}
	})
}

function _update(_e){
	var ruleId = $(_e).attr('id');
	var amountOfMoneyForNotEnough = $('#'+ruleId+'_amountOfMoneyForNotEnough').val();
	var amountOfMoney = $('#'+ruleId+'_amountOfMoney').val();
	var period = $('#'+ruleId+'_period').val();
	
	var reg_zs = /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
	var reg = /^[0-9]*$/;
	var params = 'ruleId='+ruleId+'&amountOfMoneyForNotEnough='+amountOfMoneyForNotEnough+'&amountOfMoney='+amountOfMoney+'&period='+period;
	console.log(amountOfMoneyForNotEnough+'--'+amountOfMoney+'--'+period)
	console.log(reg_zs.test(amountOfMoneyForNotEnough)+'--'+reg_zs.test(amountOfMoney)+'--'+reg.test(period))
	if(period==null||period==''||
	   amountOfMoneyForNotEnough==null||amountOfMoneyForNotEnough==''||
	   amountOfMoney==null||amountOfMoney==''){
		layer.alert('请完善规则信息！');
	}else if(!reg_zs.test(amountOfMoneyForNotEnough)||!reg_zs.test(amountOfMoney)||!reg.test(period)){
		layer.alert('请确保正确的数据格式！');
	}else{
		layer.confirm('您确定要更新该收费规则？', {
			  btn: ['是','返回']
			}, function(){
			    $.ajax({
			    	url:'/home/d/mofang/update_rule',
			    	data:params,
			    	dataType:'json',
			    	type:'post',
			    	success:function(res){
			    		if('200'==res.code){
			    			layer.alert('操作成功！',function(){
			    				location.reload();
			    			})
			    		}
			    	}
			    })
			}, function(){
			    layer.closeAll();
			});
	}
	
}