<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>查询控制台</title>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <!-- basic styles -->
<#include "/common/head_css.ftl"/>
    <link rel="stylesheet" href="/assets/css/jsonFormater.css" />
    <!-- ace settings handler -->

    <style>
        .ace-editor {
            max-height: 700px;
            height: 600px;
            background-color: #f7f8fa;
            border-collapse: separate;
            border: 1px solid #bbc0ca;
            padding: 4px;
            box-sizing: content-box;
            overflow-y: scroll;
            overflow-x: hidden;
            outline: 0;
    </style>
    <script src="/assets/js/ace-extra.min.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

    <!--[if lt IE 9]>
	<script src="/assets/js/html5shiv.js"></script>
	<script src="/assets/js/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<#include "/common/head_bar.ftl"/>

<div class="main-container" id="main-container">
    <script type="text/javascript">
        try{ace.settings.check('main-container' , 'fixed')}catch(e){}
    </script>

    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>

        <div class="sidebar" id="sidebar">
            <script type="text/javascript">
                try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
            </script>

		<#include "/common/left_menu.ftl"/>
            <!-- /.nav-list -->

            <div class="sidebar-collapse" id="sidebar-collapse">
                <i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
            </div>

            <script type="text/javascript">
                try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
            </script>
        </div>

        <div class="main-content">
            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
                </script>

                <ul class="breadcrumb">
                    <li>
                        <i class="icon-home home-icon"></i>
                        <a href="#">首页</a>
                    </li>
                    <li class="active">查询控制台</li>
                </ul><!-- .breadcrumb -->

                <!-- #nav-search -->
            </div>

            <div class="page-content">
                <!-- /.page-header -->

                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="widget-box">
                                    <div class="widget-header widget-header-flat">
                                        <h4>用户列表</h4>
                                    </div>

                                    <div class="widget-body">
                                        <div class="widget-main">

                                        <#--<hr />-->
                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <div id="toolbar">
                                                        <button id="addUser" class="btn btn-sm btn-success">
                                                            <i class="icon-ok bigger-110"></i>
                                                            添加用户
                                                        </button>
                                                    </div>
                                                    <table id="user_table"></table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->

	<#include "/common/settings.ftl" />

        <!-- /#ace-settings-container -->
    </div><!-- /.main-container-inner -->

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="icon-double-angle-up icon-only bigger-110"></i>
    </a>
</div><!-- /.main-container -->

<!-- basic scripts -->

<#include "/common/foot_js.ftl"/>
<!-- inline scripts related to this page -->
<script type="text/javascript">
    jQuery(function($) {

        $('#user_table').bootstrapTable({
            striped:true,
            //classes:"table table-no-bordered",
            //showHeader:false,
            //cardView:true,
            url:"/user/list",
            search:true,
            pageSize:15,
            sidePagination:'server',
            pagination:true,
            queryParams:function (params) {
                //params.clusterName = clusterName;
                return params;
            },
            columns: [{
                field: 'userId',
                title: '工号'
            }, {
                field: 'username',
                title: '用户名'
            }, {
                field: 'role',
                title: '角色',
                formatter: function (value, row, index) {
                    return value.roleName;
                }
            }, {
                field: 'updateTime',
                title: '更新时间'
            }, {
                field: 'updateBy',
                title: '更新人'
            }, {
                field: '',
                title: '操作',
                events: operateEvents,
                formatter: function (value, row, index) {
                    var btns = '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">'
                            +'<button class="btn btn-xs btn-success tooltip-success edit" data-rel="tooltip" title="编辑">'
                            +'<i class="icon-edit bigger-120"></i>'
                            +'</button>';

                    btns+='<button class="btn btn-xs btn-danger tooltip-danger del" data-rel="tooltip" title="删除">'
                            +'<i class="icon-trash bigger-120"></i>'
                            +'</button>';

                        btns +='</div>';
                        return btns;
                }
            }]
        });


        
        $("#addUser").on("click",function () {
            showDialog(null);
        });




    });

    window.operateEvents = {
        'click .del': function (e, value, row, index) {
            var msg = '确认要删除吗?';

            $myDialog.confirm(msg, BootstrapDialog.TYPE_DANGER,
                    function (result) {
                        // result will be true if button was click, while it will be false if users close the dialog directly.
                        if (result) {
                            var ajax = new $ax("/user/delete", function (data) {
                                // 成功
                                if (data.status === true) {
                                    $myNotify.success("删除成功");
                                    // 刷新表格
                                    $('#user_table').bootstrapTable('refresh');
                                } else {
                                    $myNotify.danger(data.msg);
                                }
                            }, function (data) {

                            });
                            ajax.set("userId", row.userId);
                            ajax.start();
                        }
                    }
            );
        },
        'click .edit': function (e, value, row, index) {
            showDialog(row);

        }
    };

    function showDialog(row) {
        BootstrapDialog.show({
            type:BootstrapDialog.TYPE_PRIMARY,
            title: '用户信息',
            closeByBackdrop: false,
            closeByKeyboard: false,
            //size:BootstrapDialog.SIZE_LARGE,
            message: function(dialog) {
                var form ='<div class="widget-main">'
                        +'<form class="form-horizontal" role="form">'
                        +'<div class="form-group">'
                        +'<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 工号 :</label>'
                        +'<div class="col-sm-10">'
                        +'<input class="form-control input-mask-date" placeholder="请输入工号" type="text" id="userId" />'
                        +'</div>'
                        +'</div>'

                        +'<div class="form-group">'
                        +'<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 用户名 :</label>'
                        +'<div class="col-sm-10">'
                        +'<input class="form-control input-mask-date" placeholder="请输入用户名" type="text" id="username" />'
                        +'</div>'
                        +'</div>'

                        +'<div class="form-group">'
                        +'<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 角色 :</label>'
                        +'<div class="col-sm-10">'
                        +'<input type="hidden" id="roleId" class="width-100 select2">'
                        +'</div>'
                        +'</div>'
                        +'</form>'
                        +'</div>';
                return form;
            },
            buttons: [{
                icon: 'icon-ok',
                label: '保存',
                cssClass: 'btn-success',
                //autospin: true,
                action: function(dialogRef){
                    var aj= new $ax("/user/save", function (data) {
                        if (data.status){
                            $('#user_table').bootstrapTable('refresh');
                            $myNotify.success("保存成功");
                            dialogRef.close();

                        }else {
                            $myNotify.danger(data.msg);
                        }
                    });

                    var userId = $.trim($("#userId").val());
                    if (!userId){
                        $myNotify.danger("请输入工号！");
                        return;
                    }

                    var flag = true;
                    if(!row){
                        // 验证工号是否存在
                        $.ajax({
                            type: "POST",
                            url: "/user/get",
                            dataType: "json",
                            async: false,
                            data: {userId:userId},
                            success: function(data) {
                                if(data.status){
                                    if(data.data){
                                        $myNotify.danger("工号已存在");
                                        flag = false;
                                    }
                                }else {
                                    $myNotify.danger(data.msg);
                                }
                            },
                            error: function(data) {
                                $myNotify.danger(data.msg);
                                flag = false;
                            }
                        });
                    }

                    if(flag){
                        var username = $.trim($("#username").val());

                        if (!username){
                            $myNotify.danger("请输入用户名！");
                            return;
                        }

                        var roleId = $.trim($("#roleId").val());
                        if (!roleId){
                            $myNotify.danger("请选择角色！");
                            return;
                        }
                        aj.set("userId",userId);
                        aj.set("roleId",roleId);
                        aj.set("username",username);
                        aj.start();
                    }

                }
            }, {
                icon:'icon-remove',
                label: '关闭',
                action: function(dialogRef){
                    dialogRef.close();
                }
            }],
            onshown:function () {
                // 获取角色信息
                var aj= new $ax("/common/role/select", function (data) {
                    if (data.status){
                        $("#roleId").select2({
                            allowClear: true,
                            placeholder: "请选择类型",
                            data:data.data
                        });
                    }
                });
                aj.start();

                if(row){
                    // 初始化数据
                    $("#userId").val(row.userId);
                    $("#username").val(row.username);
                    $("#roleId").val(row.role.roleId);
                    $("#userId").attr("readOnly",true);
                }

            }
        });
    }
</script>
</body>
</html>
