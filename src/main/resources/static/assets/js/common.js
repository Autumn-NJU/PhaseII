function $(objStr) {
   return document.getElementById(objStr);
}

window.onload = function () {
   //分析cookie值，显示上次的登陆信息
   var userNameValue = getCookieValue("username");
   // $("#loginName").text(userNameValue);
   document.getElementById('loginName').innerHTML = userNameValue;

}