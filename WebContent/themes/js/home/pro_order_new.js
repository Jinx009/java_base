$(function(){
		laydate.render({
		elem : '#date',
		done:function(value,date){
			var date = $('#date').val();
			week = new Date(date).getDay();
			if(week==0||week==6){
				$('#time').hide();
				$('#time1').show();
				time = $('#time1').val();
			}else{
				$('#time1').hide();
				$('#time').show();
				time = $('#time').val();
			}
	    }
	});
})
var week = 0;
var time = '';
function check(){
	if(week==0||week==6){
		time = $('#time1').val();
	}else{
		time = $('#time').val();
	}
	if(time!=0){
		$.ajax({
			url:'/h/pro_price/findByTime?time='+time,
			dataType:'json',
			type:'get',
			success:function(res){
				var htmlStr = '';
				for(var i in res.data){
					htmlStr+= '<option value="'+res.data[i].id+'" >'+res.data[i].name+'</option>'
				}
				$('#address').html(htmlStr);
			}
		})
	}
}
function _save(){
	var date = $('#date').val();
	var id = $('#address').val();
	var _mobilePhone = $('#mobilePhone').val();
	var _userName = $('#userName').val();
	var openid = $('#openid').val();
	var _params = 'id='+id+'&date='+date+'&mobilePhone='+_mobilePhone+'&userName='+_userName+'&openid='+openid;
	if(date==''||id==''){
		layer.alert('请选择日期或场地！');
	}else{
		$.ajax({
			url:'/h/pro_order/save',
			type:'post',
			data:_params,
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					layer.alert('添加成功！',function(){
						_openC('l5','/home/p/pro_order');
					})
				}else{
					layer.alert(res.msg);
				}
			}
		})
	}
}
function testNumber(_num){
	var reg = new RegExp("^[0-9]*$");
	if(reg.test(_num)&&_num.length==11){
		return true;
	}
	return false;
}