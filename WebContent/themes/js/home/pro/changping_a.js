$(function(){
	var a = 
		    [{addr:101,x:40,y:335},
		     {addr:102,x:65,y:325},
		     {addr:103,x:90,y:315},
		     {addr:104,x:114,y:305},
		     {addr:105,x:138,y:295},
		     {addr:106,x:162,y:285},
		     {addr:107,x:187,y:275},
		     {addr:108,x:212,y:265},
		     {addr:109,x:236,y:255},
		     {addr:110,x:260,y:245},
		     {addr:111,x:285,y:235},
		     {addr:112,x:308,y:225},
		     {addr:113,x:332,y:215},
		     {addr:114,x:356,y:207},
		     {addr:115,x:380,y:197},
		     {addr:116,x:406,y:187},
		     {addr:117,x:430,y:177},
		     {addr:118,x:458,y:168},
		     {addr:119,x:478,y:159},
		     {addr:120,x:504,y:149},
		     {addr:121,x:528,y:140},
		     {addr:122,x:550,y:132},
		     {addr:123,x:578,y:123},
		     {addr:124,x:600,y:113},
		     {addr:125,x:626,y:103},
		     {addr:126,x:652,y:93},
		     {addr:127,x:676,y:83},
		     {addr:128,x:698,y:75},
		     {addr:129,x:722,y:65},
		     {addr:130,x:746,y:55},
		     {addr:131,x:772,y:45},
		     {addr:132,x:796,y:35},
		     ];
    bindMap(a);
    setInterval(function() {
    	 getData();
    }, 10000);
})
function bindMap(_map){
	for(var _index in _map){
		var x = _map[_index].x;
		var y = _map[_index].y;
		var addr = _map[_index].addr;
		var htmlStr = '<div class="car_a" style="top:'+y+'px;left:'+x+'px" id="_'+addr+'" ></div>'
		$('.a').append(htmlStr);
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