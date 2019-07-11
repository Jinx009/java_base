/**
 * Created by jinx on 2019/7/11.
 */
document.onkeydown = function(e) {
    if (!e) {
        e = window.event;
    }
    if ((e.keyCode || e.which) == 13) {
        login();
    }
}

/**
 * 登录
 */
function login() {
   location.href = 'index.html';
};