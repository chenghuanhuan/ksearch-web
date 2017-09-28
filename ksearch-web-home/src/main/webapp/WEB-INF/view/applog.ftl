<!DOCTYPE html>
<html>
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
                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">version:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="text" id="version"  class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">platform:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <select id="platform" name="platform" class="width-100 select2"  data-placeholder="Click to Choose...">
                                                <option value=""></option>
                                                <option value="1">IOS</option>
                                                <option value="2">Android</option>
                                            </select>
                                        </div>
                                    </div>

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">osVersion:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="text" id="osVersion"  class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <hr>

                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">bundleIdentifier:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="text" id="bundleIdentifier"  class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">userToken:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="text" id="userToken"  class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">log:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="text" id="contentData"  class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <hr>


                            <div class="col-xs-12" id="query-btn" style="padding-top: 10px;">
                                <div class="form-group">
                                    <div class="col-xs-13 col-sm-11" style="width: 94%;">
                                    </div>
                                    <div class="col-xs-13 col-sm-1" style="width: 6%;">
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
    var clusterName = Util.cookie.get("cluster-name");
    var template = "";


    jQuery(function($) {
        template = $("#query_1").html();

        $(document).on("click",".add-query",function () {
            var af = $('<div class="col-xs-12 hide-form" style="padding-top: 10px;"><div class="form-group"></div></div>');
            af.html(template);
            $("#query-btn").before(af);
            // 更新下拉框
            var select = $("#query-btn").prev().find('select[name="field"]');
            initFieldSelect(select);
        });

        $(document).on("click",".del-query",function () {
            if($(".del-query").length===1){
                return;
            }
            $(this).parent().parent().parent().remove();
        });

        $(document).on("change","select[name='field']",function () {
            if($(this).val()==="_all"){
                $($(this).parent().parent().parent().find("select[name='op']")[0]).html("<option value='query_string'>query_string</option>");
            }else {
                $($(this).parent().parent().parent().find("select[name='op']")[0]).html('<option value="term">term</option> <option value="prefix">prefix</option> <option value="query_string">query_string</option>');
            }
        });


        var ajIndex = new $ax("/common/index/select", function (data) {
            if(data.status){
                $("#select_index").select2({
                    allowClear: true,
                    placeholder: "请选择索引",
                    data:data.data
                });

                /*  索引变更**/
                $("#select_index").on("change", function (e) {
                    console.log("select2:select", e);
                    resetDetailQuery();
                    var aj= new $ax("/common/type/select", function (data) {
                        if(data.status){
                            $("#select_type").select2({
                                allowClear: true,
                                placeholder: "请选择类型",
                                data:data.data
                            });
                            // 类型下拉事件
                            $("#select_type").on("change",function () {
                                resetDetailQuery();
                                var type = $("#select_type").select2("val");

                                var index = $("#select_index").select2("val");
                                var type_aj = new $ax("/common/fields",function (data) {
                                    if (data.status){
                                        fieldData = [{id:"_all",text:"_all"}];
                                        getFieldData(data.data,"");
                                        initFieldSelect($($("#query_1").find("select[name='field']")[0]));

                                        // 初始化表格信息
                                        columns=[];
                                        $.each(data.data,function (i,item) {
                                            var column = {field:item.fieldName,title:item.fieldName}
                                            if (item.type !="object" && item.type !="array"){
                                                column.sortable=true;
                                            }
                                            columns.push(column);
                                        });

                                    }
                                });
                                type_aj.set("type",type);
                                type_aj.set("index",index);
                                type_aj.set("clusterName",clusterName);
                                type_aj.start();
                            });
                        }
                    });
                    aj.set("clusterName",clusterName);
                    aj.set("index",$(this).val());
                    aj.start();

                });
            }
        });
        ajIndex.set("clusterName",clusterName);
        ajIndex.start();


        //$('#indices_table').bootstrapTable();
        /**
         * 查询
         */
        $(".btn-query").on("click",function () {
            $('#indices_table').bootstrapTable("removeAll");
            $('#indices_table').bootstrapTable("refresh");
        });

        $('#indices_table').bootstrapTable({
            striped:true,
            //classes:"table table-no-bordered",
            //showHeader:false,
            //cardView:true,
            //checkboxEnable:true,
            search:false,
            //toolbar:"#toolbar",
            sidePagination:'server',
            url:"/applog/query",
            pageList:[10, 20, 40, 60],
            pagination:true,
            //detailView:true,
            //showRefresh:true,
            showColumns:true,
            //showPaginationSwitch:true,
            //showFooter:true,
            pageSize:10,
            columns: [{
                field: 'version',
                title: '<span class="text-primary">version</span>'
            }, {
                field: 'platform',
                title: '<span class="text-primary">platform</span>'
            }, {
                field: 'osVersion',
                title: '<span class="text-primary">osVersion</span>'
            }, {
                field: 'bundleIdentifier',
                title: '<span class="text-primary">bundleIdentifier</span>'
            }, {
                field: 'userToken',
                title: '<span class="text-primary">userToken</span>'
            }, {
                field: 'contentData',
                title: '<span class="text-primary">log</span>'
            }],
            silentSort:false,
            queryParams:function (params) {
                params.version = $.trim($("#version").val());
                params.osVersion = $.trim($("#osVersion").val());
                params.platform = $.trim($("#platform").val());
                params.bundleIdentifier = $.trim($("#bundleIdentifier").val());
                params.userToken = $.trim($("#userToken").val());
                params.contentData = $.trim($("#contentData").val());
                params.clusterName = clusterName;
                return params;
            },
            onLoadSuccess:function () {
            }

        });

    });


</script>
</body>
</html>
