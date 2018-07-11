$(function(){
	_getData();
})
function _getData(){
	var _id = GetUrlParam('id');
	$.ajax({
		url:'/front/d/pro_task/detail?id='+_id,
		type:'get',
		dataType:'json',
		success:function(res){
			res.data.pickTime = toDateTime(res.data.pickTime);
			if(res.data.pickedTime!=null&&res.data.pickedTime!=''){
				res.data.pickedTime = toDateTime(res.data.pickedTime);
			}else{
				res.data.pickedTime = '';
			}
			new Vue({
				el:'body',
				data:{
					item:res.data
				}
			})
		}
	})
}
function GetUrlParam(paraName) {
	var url = document.location.toString();
	var arrObj = url.split('?');
if(arrObj.length>1){
	  var arrPara = arrObj[1].split('&');
	  var arr;
	  for(var i in arrPara){
		  arr = arrPara[i].split('=');
		  if(arr!=null&&arr[0]==paraName){
			  return arr[1];
		  }
	  }
}
  return '';
}


function _edit(){
	var _id = $('#id').val();
	$.ajax({
		url:'/front/d/pro_task/changeStatus?id='+_id,
		type:'post',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				layer.open({
				    content: '状态更改成功！'
				    ,btn: '好'
				  });
				location.reload();
			}
		}
	})
}