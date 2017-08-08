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
                            <div class="col-sm-6">
                                <h4 class="header blue">查询语句</h4>
                                <div class="ace-editor" id="query"></div>

                            </div>
                            <div class="col-sm-6">
                                <h4 class="header blue">搜索结果</h4>
                                <div class="ace-editor" id="result"></div>

                            </div>
						</div>
                        <br>
                        <div class="row">
                            <div class="col-sm-6">
                                    <button type="button" class="btn btn-sm btn-success" id="execute">
                                        执行
                                        <i class="icon-arrow-right icon-on-right bigger-110"></i>
                                    </button>
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
<script src="/assets/js/jsonFormater.js"></script>
<!-- inline scripts related to this page -->
<script src="/assets/js/ace/ace.js"></script>
<script type="text/javascript">
    var clusterName = Util.cookie.get("cluster-name");
    var query;
    var result;
    jQuery(function($) {

        query = ace.edit("query");
        query.setTheme("ace/theme/monokai");
        query.$blockScrolling = Infinity;
        //document.getElementById('query').style.fontSize='12px';
        //editor.getSession().setMode("ace/mode/javascript");

        result = ace.edit("result");
        result.setTheme("ace/theme/github");
        result.getSession().setMode("ace/mode/json");
        result.setReadOnly(true);
        result.$blockScrolling = Infinity
        $("#execute").on("click",function () {
            var ajax = new $ax("/query/execute", function (data) {
                // 成功
                if (data.status===true){
                    var o = JSON.parse(data.data);
                    var val = JSON.stringify(o, null, 4);
                    result.setValue(val);

                }else {
                    $myNotify.danger(data.msg);
                }
            },function (data) {

            });
            var dsl = query.getValue().trim();
            ajax.set("query",dsl);
            ajax.set("clusterName",clusterName);
            ajax.start();
        });
    });

</script>
</body>
</html>
