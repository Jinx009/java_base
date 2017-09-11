$(function(){
	var a = 
		    [{addr:101,x:120,y:362},
		     {addr:102,x:148,y:352},
		     {addr:103,x:177,y:340},
		     {addr:104,x:207,y:328},
		     {addr:105,x:235,y:315},
		     {addr:106,x:265,y:303},
		     {addr:108,x:294,y:292},
		     {addr:108,x:325,y:280},
		     {addr:107,x:354,y:269},
		     {addr:109,x:529,y:200},
		     {addr:110,x:558,y:188},
		     {addr:110,x:588,y:178},
		     {addr:110,x:616,y:166},
		     {addr:110,x:645,y:155},
		     {addr:110,x:675,y:141},
		     {addr:110,x:704,y:130},
		     {addr:110,x:735,y:118},
		     {addr:110,x:765,y:106},
		     {addr:110,x:795,y:93},
		     {addr:110,x:823,y:82},
		     ];
    bindMap(a);
    setInterval(function() {
//    	 getData();
    }, 10000);
})
function bindMap(_map){
	for(var _index in _map){
		var x = _map[_index].x;
		var y = _map[_index].y;
		var addr = _map[_index].addr;
		var htmlStr = '<div class="car_a" style="top:'+y+'px;left:'+x+'px" id="_'+addr+'" ></div>'
		$('.b').append(htmlStr);
	}
}
function getData(){
	$.ajax({
		url:'/home/d/_view?areaId=1',
		type:'get',
		dataType:'json',
		success:function(res){
			var params = res.data;
			for(var i in params){
				if(params[i].available=='1'){
					$('#_'+params[i].addr).show();
				}else{
					$('#_'+params[i].addr).hide();
				}
			}
		}
	})
}