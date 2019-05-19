$("document").ready(function () {
    $("#inlineCheckbox1").click(function () {
        if ($("#inlineCheckbox1").prop("checked") === true) {
            $("#inlineCheckbox2").prop("checked", true);
        }
    });

    $("span.system-name").click(function () {
        alert("icode");
    })
});


// 逻辑上还是不完善，有待改进
$(function () {
    $(".login-btn").attr("disabled", true);
    $(".register-btn").attr("disabled", true);

    // 登陆检测
    $("#username").blur(function () {
        var account = $("#username").val();
        var password = $("#password").val();
        if (account === "") {
            $(".user").html("<strong>账户不能为空。</strong>");
            $(".login-btn").attr("disabled", true);
        } else if (!(/^[0-9]+$/).test(account)) {
            $(".user").html("<strong>账户仅由数字构成。</strong>");
            $(".login-btn").attr("disabled", true);
        } else {
            $(".user").html("&nbsp;");

            if (password !== "") {
                $(".login-btn").attr("disabled", false);
            }
        }
    });

    $("#password").mouseout(function () {
        var account = $("#username").val();
        var password = $("#password").val();
        if (password === "") {
            $(".pwd").html(    "<strong>密码不能为空。</strong>");
            $(".login-btn").attr("disabled", true);
        } else {
            $(".pwd").html     ("&nbsp;");
            if (account !== "") {
                $(".login-btn").attr("disabled", false);
            }
        }
    });

    // 注册检测

    $("#passwordReg").blur(function () {
        $("#passwordReg").popover('hide');
    });

    $("#passwordReg").focus(function () {
        $("#passwordReg").popover('show');
    });

    $("#passwordRegCfm").blur(function () {
        var account = $("#usernameReg").val();
        var password = $("#passwordReg").val();
        var passwordConfirm = $("#passwordRegCfm").val();

        if (password === "") {
            $(".pwd-remind").html("密码不能为空。");
            $(".register-btn").attr("disabled", true);
        } else if (passwordConfirm === "") {
            $(".pwd-remind-cfm").html("密码不能为空。");
            $(".register-btn").attr("disabled", true);
        } else if (password !== passwordConfirm) {
            $(".pwd-remind").html("两次输入的密码不一致！");
            $(".register-btn").attr("disabled", true);
        } else {
            if (account !== "") {
                $(".pwd-remind").html("&nbsp;");
                $(".pwd-remind-cfm").html("&nbsp;");
                $(".register-btn").attr("disabled", false);
            }
        }
    });

    $(".login-btn").click(function () {
        var username = $("#username").val();
        var password = $("#password").val();

        $.ajax({
            url:"/login",
            method:"get",
            dataType:"text",
            async: false,
            data: {
                username: username,
                password: password
            },
            success: function (result) {
                if (result.toString() === "success") {
                    $("#ModalLabel").text("success！");
                    $("#LoginRemind").html("登陆成功，1s后自动跳转");
                    $(".login-btn").attr("data-target", "#Modal");
                    setInterval(function () {
                        window.location.href = "/jsp/teacherManage.jsp";
                    }, 1000);
                } else {
                    $("#LoginRemind").html(result.toString());
                    $(".login-btn").attr("data-target", "#Modal");
                }
            },
            error: function () {
                alert("出bug了，赶紧联系我");
            }
        });
        });

    $(".register-btn").click(function () {
        var password = $("#passwordRegCfm").val();

        $.ajax({
            url: "/login",
            method: "post",
            async: false,
            data: {
                password: password
            },
            success: function (result) {
                if (result.toString() === "false") {
                    $("#RegRemind").html("注册失败！");
                    $(".register-btn").attr("data-target", "#ModalReg");

                } else {
                    $("#RegRemind").html("注册成功！您的账户为：<b style='font-family: 微软雅黑,serif'>" + result.toString()  +"</b>。");
                    $("#username").val(result.toString());
                    $("#password").val(password.toString());
                    $(".register-btn").attr("data-target", "#ModalReg");
                }
            }
        })
    })

});

