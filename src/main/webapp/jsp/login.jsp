<%--
  Created by IntelliJ IDEA.
  User: t0106
  Date: 2018/8/17
  Time: 20:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>login</title>
    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>

    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>

    <link href="https://cdn.bootcss.com/font-awesome/5.8.1/css/all.min.css" rel="stylesheet">

    <script src="../js/login.js" async="async"></script>
    <link href="../css/login.css" rel="stylesheet" type="text/css">
</head>
<body style="background-color: #008ead">

<h3 class="system-name" style="opacity: 1"><span class="system-name">学生成绩管理系统</span></h3>


<div class="login-container">
    <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseExample"
            aria-expanded="false" aria-controls="collapseExample"
            style="margin-top: 10px;">
        Login / Register
    </button>
    <div id="collapseExample" class="in">
        <h1 class="login">login</h1>

        <div class="input-group ipt">
            <span class="input-group-addon"><i class="fa fa-user"></i></span>
            <input class="form-control" type="text" id="username" placeholder="请输入账号。"
                   maxlength="10em" autocomplete="off">
        </div>
        <p class="remind user">&nbsp;</p>

        <div class="input-group ipt">
            <span class="input-group-addon"><i class="fa fa-lock"></i></span>
            <input class="form-control" type="password" id="password" placeholder="请输入密码。"
                   maxlength="16em" autocomplete="off">
        </div>
        <p class="remind pwd">&nbsp;</p>

        <div class="two-check">
            <label class="checkbox-inline">
                <input type="checkbox" id="inlineCheckbox1" value="option1"> 自动登陆
            </label>
            <label class="checkbox-inline">
                <input type="checkbox" id="inlineCheckbox2" value="option2"> 记住密码
            </label>
        </div>

        <button id="loginBtn" class="login-btn btn btn-success" data-toggle="modal" data-target="">登陆</button>
    </div>

    <div class="register">
        <h1>Register</h1>

        <div class="input-group ipt">
            <span class="input-group-addon"><i class="fa fa-lock"></i></span>
            <input class="form-control" type="password" id="passwordReg" placeholder="请输入密码。" maxlength="16em"
                   data-container="body" data-placement="right"
                   data-content="长度为8-16个字符不能使用空格、中文">
        </div>
        <p class="remind pwd-remind">&nbsp;</p>

        <div class="input-group ipt">
            <span class="input-group-addon"><i class="fa fa-lock"></i></span>
            <input class="form-control" type="password" id="passwordRegCfm" placeholder="请再次输入密码。" maxlength="16em">
        </div>
        <p class="remind pwd-remind-cfm">&nbsp;</p>

        <button id="registerBtn" class="register-btn btn btn-success" data-toggle="modal" data-target="">注册</button>
    </div>


</div>

<!--登陆模态框-->
<div class="modal fade" id="Modal" tabindex="-1" role="dialog" aria-labelledby="ModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                    <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title" id="ModalLabel">错误！</h4>
            </div>
            <div class="modal-body">
                <p id="LoginRemind">登陆失败，请检查您的账户和密码</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<!--注册模态框-->
<div class="modal fade" id="ModalReg" tabindex="-1" role="dialog" aria-labelledby="ModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                    <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title" id="Label">结果！</h4>
            </div>
            <div class="modal-body">
                <p id="RegRemind"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>


</body>

</html>
