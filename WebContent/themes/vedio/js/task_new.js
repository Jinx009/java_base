$(function() {
    _getArea();
    laydate.render({
        elem: '#vedioStart'
        ,type: 'datetime'
    });
    loadForms();
})
function _showUpload(){
    $('#file').click();
}

function _changeArea(){
    $('#areaId').val($('#areaId2').val());
}

function _getArea(){
    $.ajax({
        url:'/d/vedioArea/list',
        type:'get',
        dataType:'json',
        success:function(res){
            $('#areaId').val(res.data[0].id);
            new Vue({
                el:'#areaId',
                data:{
                    datas:res.data
                }
            })
        }
    })
}


/**
 * 初始加载表单
 */
function loadForms() {
    $('#form').ajaxForm({
        dataType:'json',
        success : function(res) {
            if ('200' == res.code) {
                layer.alert('新建完成！')
            }else{
                layer.alert(res.msg);
            }
        },complete : function(t) {}
    });
}
/**
 * 按钮提交事件
 */
function _upload() {
    $('#form').submit();
}
/**
 * 显示上传文件路径
 */
$('input[id=file]').change(function() {
    $('#fileShow').val($(this).val());
});