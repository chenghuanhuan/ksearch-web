<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>数据浏览</title>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <!-- basic styles -->
    <#include "/common/head_css.ftl"/>
    <link rel="stylesheet" href="/assets/css/jsonFormater.css" />
    <style>
        .modal-optimize .modal-dialog{
            width: 500px;
        }
        .tags{
            width:400px;
        }

        .modal-add-type .modal-dialog{
            width: 1024px;
        }
    </style>
    <!-- ace settings handler -->

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

            <div class="sidebar-shortcuts" id="sidebar-shortcuts">
                <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                    <button class="btn btn-success">
                        <i class="icon-signal"></i>
                    </button>

                    <button class="btn btn-info">
                        <i class="icon-pencil"></i>
                    </button>

                    <button class="btn btn-warning">
                        <i class="icon-group"></i>
                    </button>

                    <button class="btn btn-danger">
                        <i class="icon-cogs"></i>
                    </button>
                </div>

                <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                    <span class="btn btn-success"></span>

                    <span class="btn btn-info"></span>

                    <span class="btn btn-warning"></span>

                    <span class="btn btn-danger"></span>
                </div>
            </div><!-- #sidebar-shortcuts -->
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
                    <li class="active">数据浏览</li>
                </ul><!-- .breadcrumb -->

                <!-- #nav-search -->
            </div>

            <div class="page-content">
                <!-- /.page-header -->
                <hr />
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->

                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="select_index">索引（index）:</label>

                                    <div class="col-xs-12 col-sm-4">
                                        <div class="clearfix">
                                            <input type="hidden" id="select_index" class="width-100 select2">
                                            </input>
                                        </div>
                                    </div>
                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="select_type">类型（type）:</label>

                                    <div class="col-xs-12 col-sm-4">
                                        <div class="clearfix">
                                            <input type="hidden" id="select_type" class="width-100 select2">
                                            </input>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <div class="col-xs-12 col-sm-10">
                                    </div>
                                    <div class="col-xs-12 col-sm-2">
                                        <button class="btn btn-success btn-query">
                                           查询
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="hr hr32 hr-dotted"></div>
                        <div class="row">
                            <div class="col-xs-12">
                            <#--<div id="toolbar">
                                <button id="addIndex" class="btn btn-sm btn-success">
                                    <i class="icon-ok bigger-110"></i>
                                    添加索引
                                </button>
                            </div>-->
                                <table id="indices_table" data-search="true"></table>
                            </div>
                        </div>

                        <hr />
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
<script type="text/javascript">
    var columns=[];
    jQuery(function($) {

        new $ax("/common/index/select", function (data) {
            if(data.status){
                $("#select_index").select2({
                    allowClear: true,
                    placeholder: "请选择索引",
                    data:data.data
                });

                $("#select_index").on("change", function (e) {
                    console.log("select2:select", e);

                    var aj= new $ax("/common/type/select", function (data) {
                        if(data.status){
                            $("#select_type").select2({
                                allowClear: true,
                                placeholder: "请选择类型",
                                data:data.data
                            });
                        }
                    });
                    aj.set("clusterName","");
                    aj.set("index",$(this).val());
                    aj.start();

                });
            }
        }).start();

        $("#select_type").select2({
            allowClear: true,
            placeholder: "请选择类型",
            data:[]
        });

        //$('#indices_table').bootstrapTable();
        /**
         * 查询
         */
        $(".btn-query").on("click",function () {

            var type = $("#select_type").select2("val");

            var index = $("#select_index").select2("val");
            if (index==""){
                $myNotify.warn("请选择一个索引");
                return;
            }
            if (type==""){
                $myNotify.warn("请选择一个类型");
                return;
            }
            // 获取表头配置
            var aj = new $ax("/common/fields",function (data) {
                if (data.status){
                    var data = data.data;
                    var columns = [];
                    $.each(data,function (i,item) {
                        columns.push({field:item,title:item,formatter:formatter});
                    });

                    // 初始化表格

                    $('#indices_table').bootstrapTable("destroy");
                    $('#indices_table').bootstrapTable({
                        striped:true,
                        //classes:"table table-no-bordered",
                        //showHeader:false,
                        //cardView:true,
                        //checkboxEnable:true,
                        search:false,
                        //toolbar:"#toolbar",
                        sidePagination:'server',
                        url:"/dataview/query",
                        pageList:[10, 25, 50, 100],
                        pagination:true,
                        detailView:true,
                        //showRefresh:true,
                        showColumns:true,
                        //showPaginationSwitch:true,
                        //showFooter:true,
                        //pageSize:1,
                        columns:columns,
                        queryParams:function (params) {
                            params.indices = index;
                            params.types = type;
                            return params;
                        },
                        onExpandRow: function (index, row, $detail) {
                            ///$detail.html('<div>'+JSON.stringify(row,null,4)+'</div>')
                            //$detail.html('<div></div>').find("div").JSONView(row);
                            var options = {
                                dom: $detail.html('<div></div>').find("div"),
                                isCollapsible: true,
                                quoteKeys: true,
                                imgCollapsed: "/assets/images/Collapsed.gif", //收起的图片路径
                                imgExpanded: "/assets/images/Expanded.gif",  //展开的图片路径
                                tabSize: 2
                            };
                            window.jf = new JsonFormater(options);
                            jf.doFormat(row);
                        },
                        onLoadSuccess:function () {
                            //$('[data-rel=tooltip]').tooltip();
                        }

                    });


                }
            });

            aj.set("type",type);
            aj.set("index",index);
            aj.set("clusterName","");
            aj.start();
        });



        var formatter = function (value, row, index) {

            return value;
        }

    });






</script>
</body>
</html>
