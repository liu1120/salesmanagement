
function clearLoginInfo() {
    var login_username=localStorage.getItem("login_username");
    if(login_username!=null){
        localStorage.removeItem('login_username');
    }
    self.location.href="login"
    console.log("self.location.href");
}
function checkLoginInfo() {//检查登录信息
    var login_username=localStorage.getItem("login_username");
    console.log("checkLoginInfo==login_username:"+login_username);//存在信息
    if(login_username!=null){//加载登录页面，不为空，存在登录信息
        document.write("<form action='/admin/index' method=post name=manageDepForm style='display:none'>");
        document.write("<input type=hidden name='username' value=''/>");//参数1
        document.write("<input type=hidden name='login_username' value='"+login_username+"'/>");//参数1
        document.write("<input type=hidden name='password' value=''/>");//参数2
        document.write("</form>");
        document.manageDepForm.submit();
    }
}

