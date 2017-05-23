var _menuIconList = ['fa fa-bell-slash-o','fa fa-bicycle','fa fa-binoculars','fa fa-birthday-cake','fa fa-bolt fa-bomb','fa fa-book','fa fa-bookmark','fa fa-briefcase'];
var _menuParent = new Array();
var _sonMenuList = new Array();
var _menuIconIndex = 0;
/**
 * 导航数据
 * @param _activeName
 */
function getNav(_activeName){
	$('#warningInput').val(new Date().Format("yyyy年MM月dd日"))
	if(_activeName!=''){
		$('.treeview').removeClass('active');
	}
	$.ajax({
		url:'/home/d/menu?t='+getTimestamp(),
		type:'post',
		dataType:'json',
		success:function(res){
			if(res.data!=null){
				for(var i in res.data){
					if(0==res.data[i].type&&0==res.data[i].parentId){
						var obj = {};
						obj.icon = _menuIconList[_menuIconIndex];
						obj.name = res.data[i].name;
						obj.id = res.data[i].id;
						obj.son = new Array();
						_menuParent.push(obj);
						_menuIconIndex++;
					}else if(0==res.data[i].type&&0!=res.data[i].parentId){
						_sonMenuList.push(res.data[i]);
					}
				}
				if(_sonMenuList.length!=0){
					for(var i in _sonMenuList){
						for(var j in _menuParent){
							if(_menuParent[j].id==_sonMenuList[i].parentId){
								_menuParent[j].son.push(_sonMenuList[i]);
							}
						}
					}
				}
				new Vue({
	   				  el: '.sidebar-menu',
	   				  data:{navs:_menuParent}
	    		})
				$('.treeview').each(function(){
					if($(this).attr('data-info')==_activeName){
						$(this).addClass('active');
					}
				})
			}
		}
	})
}
/**
 * 获取时间戳
 * @returns
 */
function getTimestamp(){
	return Date.parse(new Date());
}  
/**
 * 登出
 */
function loginOut(){
	$.ajax({
		url:'/home/config/loginOut',
		dataType:'json',
		type:'post',
		success:function(res){
			if('200'==res.code){
				layer.alert('账号退出成功！',function(){
					location.href = '/home/index';
				})
			}
		}
	})
}
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
/**
 * 时间戳转换标准时间
 * @param unixtime
 * @returns
 */
function toDateTime(unixtime)  {  
	 var date = new Date(unixtime);
	 return  date.Format("yyyy-MM-dd hh:mm:ss"); 
}  
/**
 * 时间戳转换标准时间
 * @param unixtime
 * @returns
 */
function toDate(unixtime)  {  
	 var date = new Date(unixtime);
	 return  date.Format("yyyy-MM-dd"); 
} 