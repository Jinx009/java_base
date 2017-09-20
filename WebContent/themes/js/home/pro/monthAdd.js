$(function(){
	$('.submit-btn').bind('click',function(){
		_save();
	})
})
function _save(){
	var _price = $('#price').val(),
		_type = $('#type').val();
	if(!validateFloat(_price)&&!isPInt(_price)){
		$('#errorMsg').html('价格格式不正确！');
	}else{
		$.ajax({
			url:'/home/d/saveMonthCard',
			type:'post',
			data:'price='+_price+'&type='+_type,
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					_open('6','/home/p/month')
				}else{
					layer.alert(res.msg);
				}
			}
		})
	}
}