/**
 * 正则校验纯数字
 * @param _num
 * @returns {Boolean}
 */
function testNumber(_num){
	var reg = new RegExp("^[0-9]*$");
	if(reg.test(_num)){
		return true;
	}
	return false;
}