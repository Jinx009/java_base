$(function(){
	$.ajax({
		url:'/common/job/all?serviceName=shangcheng',
		type:'get',
		dataType:'json',
		success:function(res){
			for(var i in res.data){
				res.data[i].createTime = formatDateTime(res.data[i].createTime);
			}
			new Vue({
				el:'#data',
				data:{
					datas:res.data
				}
			})
		}
	})
})
function formatDateTime(inputTime) {    
    var date = new Date(inputTime);  
    var y = date.getFullYear();    
    var m = date.getMonth() + 1;    
    m = m < 10 ? ('0' + m) : m;    
    var d = date.getDate();    
    d = d < 10 ? ('0' + d) : d;    
    var h = date.getHours();  
    h = h < 10 ? ('0' + h) : h;  
    var minute = date.getMinutes();  
    var second = date.getSeconds();  
    minute = minute < 10 ? ('0' + minute) : minute;    
    second = second < 10 ? ('0' + second) : second;   
    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;    
};  