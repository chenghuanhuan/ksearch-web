<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>nginx access日志查询</title>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <!-- basic styles -->
<#include "/common/head_css.ftl"/>
    <link rel="stylesheet" href="/assets/css/daterangepicker.css" />
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

        .space {
            width: 1px;
            max-height: 1px;
            min-height: 1px;
            overflow: hidden;
            margin: 6px 0
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
                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">时间:</label>

                                    <div class="col-xs-12 col-sm-5">
                                        <div class="clearfix">
                                            <input class="form-control" type="text" name="timestamp" id="timestamp" />
                                        </div>
                                    </div>

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">来源:</label>

                                    <div class="col-xs-12 col-sm-5">
                                        <div class="clearfix">
                                            <input class="form-control" type="text" name="source" id="source" />
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <div class="space"></div>

                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">IP:</label>

                                    <div class="col-xs-12 col-sm-5">
                                        <div class="clearfix">
                                            <input type="text" id="ip"  class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">机器名:</label>

                                    <div class="col-xs-12 col-sm-5">
                                        <div class="clearfix">
                                            <input type="text" id="hostname"  class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="space"></div>



                            <div class="col-xs-12">
                                <div class="form-group">

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">message:</label>

                                    <div class="col-xs-12 col-sm-5">
                                        <div class="clearfix">
                                            <input type="text" id="message"  class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">错误码:</label>

                                    <div class="col-xs-12 col-sm-5">
                                        <div class="clearfix">
                                            <input type="text" id="responseCode"  class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="space"></div>


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
<script src="/assets/js/date-time/daterangepicker.min.js"></script>
<script src="/assets/js/date-time/moment.min.js"></script>
<!-- inline scripts related to this page -->
<script type="text/javascript">
    var clusterName = Util.cookie.get("cluster-name");
    jQuery(function($) {

        /**
         * 查询
         */
        $(".btn-query").on("click",function () {
            $('#indices_table').bootstrapTable("removeAll");
            $('#indices_table').bootstrapTable("refresh");
        });

        $('input[name=timestamp]').daterangepicker({
            timePicker : true,
            format : 'YYYY-MM-DD HH:mm:ss',
            dateLimit : {
                days : 7
            }, //起止时间的最大间隔
            ranges : {
                //'最近1小时': [moment().subtract('hours',1), moment()],
                '今日': [moment().startOf('day'), moment()],
                '昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
                '最近7日': [moment().subtract('days', 6), moment()]//,
                //'最近30日': [moment().subtract('days', 29), moment()]
            },
            locale : {
                applyLabel : '确定',
                cancelLabel : '取消',
                fromLabel : '起始时间',
                toLabel : '结束时间',
                customRangeLabel : '自定义',
                daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
                monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',
                    '七月', '八月', '九月', '十月', '十一月', '十二月' ],
                firstDay : 1
            }
        }).prev().on(ace.click_event, function(){
            $(this).next().focus();
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
            method:"post",
            contentType:"application/x-www-form-urlencoded; charset=UTF-8",
            url:"/nginx/access/list",
            pageList:[10, 20,30],
            pagination:true,
            //detailView:true,
            //showRefresh:true,
            showColumns:true,
            //showPaginationSwitch:true,
            //showFooter:true,
            pageSize:10,
            columns: [{
                field: '@timestamp',
                sortable:true,
                title: '<span class="text-primary">时间</span>'
            }, {
                field: 'nginx.access.url',
                sortable:true,
                title: '<span class="text-primary">请求路径</span>'
            }, {
                field: 'beat.hostname',
                title: '<span class="text-primary">机器名</span>'
            }, {
                field: 'nginx.access.body_sent.bytes',
                sortable:true,
                title: '<span class="text-primary">body数据量（byte）</span>'
            }, {
                field: 'message',
                title: '<span class="text-primary">message</span>'
            }, {
                field: 'nginx.access.geoip.city_name',
                title: '<span class="text-primary">所在城市</span>'
            }, {
                field: 'nginx.access.geoip.country_name',
                title: '<span class="text-primary">所在国家</span>'
            }, {
                field: 'source',
                title: '<span class="text-primary">来源</span>'
            }, {
                field: 'nginx.access.remote_ip',
                title: '<span class="text-primary">ip地址</span>'
            }, {
                field: 'nginx.access.response_code',
                title: '<span class="text-primary">返回码</span>'
            }, {
                field: 'nginx.access.remote_ip',
                title: '<span class="text-primary">ip地址</span>'
            }, {
                field: 'nginx.access.referrer',
                title: '<span class="text-primary">referrer</span>'
            }, {
                field: 'nginx.access.user_agent',
                title: '<span class="text-primary">user_agent</span>',
                formatter: function (value, row, index) {
                    if (value==null||value==""){
                        return value;
                    }
                    return JSON.stringify(value);
                }
            }, {
                field: 'nginx.access.http_x_forwarded_for',
                title: '<span class="text-primary">http_x_forwarded_for</span>'
            }, {
                field: 'nginx.access.upstream_addr',
                title: '<span class="text-primary">upstream_addr</span>'
            }, {
                field: 'nginx.access.upstream_response_time',
                sortable:true,
                title: '<span class="text-primary">upstream_response_time</span>'
            }, {
                field: 'nginx.access.request_time',
                sortable:true,
                title: '<span class="text-primary">request_time</span>'
            }],
            silentSort:false,
            queryParams:function (params) {
                params.timestamp = $.trim($("#timestamp").val());
                params.hostname = $.trim($("#hostname").val());
                params.ip = $.trim($("#ip").val());
                params.message = $.trim($("#message").val());
                params.responseCode = $.trim($("#responseCode").val());
                params.source = $.trim($("#source").val());
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
