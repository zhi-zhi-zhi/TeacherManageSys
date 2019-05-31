<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: t0106
  Date: 2019/5/9
  Time: 19:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>

    <%-- jQuery--%>
    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>

    <%-- bootstrap --%>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>

    <%-- bootstrap-table --%>
    <link href="https://cdn.bootcss.com/bootstrap-table/1.14.2/bootstrap-table.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/bootstrap-table/1.14.2/bootstrap-table.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap-table/1.14.2/locale/bootstrap-table-zh-CN.min.js"></script>

    <%-- bootstrap-validator 基于 bootstrap3 --%>
    <link href="https://cdn.bootcss.com/jquery.bootstrapvalidator/0.5.3/css/bootstrapValidator.min.css"
          rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery.bootstrapvalidator/0.5.3/js/bootstrapValidator.min.js"></script>

    <%-- bootstrap4 dropdown所需的依赖 --%>
    <script src="https://cdn.bootcss.com/popper.js/1.15.0/umd/popper.js"></script>

    <%-- 字体图表 --%>
    <link href="https://cdn.bootcss.com/font-awesome/5.8.1/css/all.min.css" rel="stylesheet">

    <%-- 弹框 --%>
    <link href="https://cdn.bootcss.com/toastr.js/latest/css/toastr.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/toastr.js/latest/js/toastr.min.js"></script>

    <script src="../js/teacherConfig.js" type="text/javascript"></script>
    <script src="../js/teacherManageModal.js" type="text/javascript" async="async"></script>
    <script src="../js/teacherManageTable.js" type="text/javascript" async="async"></script>
</head>
<body>

<br>
<div>
    <h1 class="" style="text-align: center">教师管理系统</h1>
    <div class="container" style="height: 300px;">
        <div id="toolbar">
            <button id="remove" class="btn btn-danger" disabled>
                <i class="glyphicon glyphicon-remove"></i> Delete
            </button>
            <button id="update" class="btn btn-primary" data-toggle="modal" data-target="#myModal"
                    data-whatever="更新" disabled>
                <i class="fas fa-edit"></i> edit
            </button>
            <button id="add" class="btn btn-danger" data-toggle="modal" data-target="#myModal"
                    data-whatever="新增">
                <i class="fas fa-plus"></i> add
            </button>
        </div>
        <%-- table-striped: 间隔行变色 --%>
        <table id="table" class="table-striped"></table>
    </div>


    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h2 class="modal-title" id="exampleModalLabel"></h2>
                </div>
                <div class="modal-body">
                    <form id="updateAndAddForm" method="post" class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">姓名</label>
                            <div class="col-sm-5">
                                <input id="name" type="text" class="form-control" name="name" placeholder="谭强"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">性别</label>
                            <div class="col-sm-5">
                                <div class="radio">
                                    <label>
                                        <input id="male" type="radio" name="gender" value="男"/> 男
                                    </label>
                                </div>
                                <div class="radio">
                                    <label>
                                        <input id="female" type="radio" name="gender" value="女"/> 女
                                    </label>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">年龄</label>
                            <div class="col-sm-5">
                                <input id="age" type="text" class="form-control" name="age" placeholder="20"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">学院</label>
                            <div class="col-sm-5">
                                <select id="academy" class="form-control" name="academy"></select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">专业</label>
                            <div class="col-sm-5">
                                <select id="dept" class="form-control" name="dept">
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">薪水</label>
                            <div class="col-sm-5">
                                <input id="salary" type="text" class="form-control" name="salary" placeholder="8888"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-9 col-sm-offset-3">
                                <button type="submit" class="btn btn-default">Submit</button>
                                <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>


</html>
