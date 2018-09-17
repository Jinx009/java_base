$(function() {
	setSessionStorage('_class', 'icon-star-');
	_loadClass();
	_getData();
})
function _go(_e){
	var _id = $(_e).attr('id');
	location.href = '/front/p/detail?id='+_id;
}

var _index = 1;
function _getData() {
	$
			.ajax({
				url : '/front/d/pro_task/doneList?p=' + _index+'&status=1',
				type : 'get',
				dataType : 'json',
				success : function(res) {
					if (res.data != null && res.data.list != null) {
						new Vue({
							el:'body',
							data:{
								datas:res.data
							}
						})
//						var htmlStr = '';
//						for ( var i in res.data.list) {
//							var obj = res.data.list[i];
//							htmlStr += '<div class="content-block-title"></div>'
//									+ '<div class="list-block" onclick="_go(this);" id="'
//									+ obj.id
//									+ '"  >'
//									+ '<ul>'
//									+ '<li class="item-content">'
//									+ '<div class="item-media">'
//									+ '<i class="icon icon-f7"></i>'
//									+ '</div>'
//									+ '<div class="item-inner">'
//									+ '<div class="item-title">Name</div>'
//									+ '<div class="item-after" v-text="item.name" >'
//									+ obj.name
//									+ '</div>'
//									+ '</div>'
//									+ '</li>'
//									+ '<li class="item-content">'
//									+ '<div class="item-media">'
//									+ '<i class="icon icon-f7"></i>'
//									+ '</div>'
//									+ '<div class="item-inner">'
//									+ '<div class="item-title">Descriptation</div>'
//									+ '<div class="item-after" v-text="item.description" >'
//									+ obj.description
//									+ '</div>'
//									+ '</div>'
//									+ '</li>'
//									+ '<li class="item-content">'
//									+ '<div class="item-media">'
//									+ '<i class="icon icon-f7"></i>'
//									+ '</div>'
//									+ '<div class="item-inner">'
//									+ '<div class="item-title">F/T,SHG(参考）</div>'
//									+ '<div class="item-after" v-text="item.pickTime" >'
//									+ obj.pickTime
//									+ '</div>'
//									+ '</div>'
//									+ '</li>'
//									+ '<li class="item-content">'
//									+ '<div class="item-media">'
//									+ '<i class="icon icon-f7"></i>'
//									+ '</div>'
//									+ '<div class="item-inner">'
//									+ '<div class="item-title">pick up time</div>'
//									+ '<div class="item-after" v-text="item.pickedTime" >'
//									+ obj.pickedTime
//									+ '</div>'
//									+ '</div></li></ul></div>';
//						}
//						$('#data').append(htmlStr);
					}
				}
			})
}
$(document).on('refresh', '.pull-to-refresh-content', function(e) {
	_index++;
	_getData();
});
function _go(_e){
	var _id = $(_e).attr('id');
	location.href = '/front/p/detail?id='+_id;
}