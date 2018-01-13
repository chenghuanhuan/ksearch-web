<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>热修复 日志查询</title>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <!-- basic styles -->
<#include "/common/head_css.ftl"/>
    <link rel="stylesheet" href="assets/css/daterangepicker.css" />
    <style>

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
                            <form id="query-form">
                            </form>
                            <div class="col-xs-12" id="query-btn" style="padding-top: 10px;">
                                <div class="form-group">
                                    <div class="col-xs-13 col-sm-11" style="width: 88%;">
                                    </div>
                                    <div class="col-xs-13 col-sm-1" style="width: 6%;">
                                        <button class="btn btn-success btn-query">
                                            查询
                                        </button>
                                    </div>
                                    <div class="col-xs-13 col-sm-1" style="width: 6%;">
                                        <button class="btn btn-default btn-reset">
                                            重置
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

        // 获取查询条件
        var ajax = new $ax({url:"/common/getQueryCondition",async:true}, function (data) {
            if (data.status){
                var data = data.data;
                var html = "";
                var line = "";
                $.each(data,function (i,item) {
                        if((i%3)==0){
                             line =  '<div class="col-xs-12">'+
                                            '<div class="form-group">';
                        }
                        line +='<label class="control-label col-xs-12 col-sm-1 no-padding-right" for="'+item.field+'">'+item.field+':</label>';
                                line+='<div class="col-xs-12 col-sm-3">';
                                    line+='<div class="clearfix">';
                                        line+='<input type="text" id="'+item.field+'" name="'+item.field+'" placeholder="" class="col-xs-12 col-sm-12" />';
                                    line+='</div>';
                                line+='</div>';
                        if (((i+1)%3)==0||i==data.length-1){
                                line+='</div>';
                            line+='</div>';
                            if (i!=data.length-1){
                             line +='<div class="space"></div>';
                            }
                            html+=line;
                        }

                });

                $("#query-form").append(html);
                initDateInput();
            }

        });
        ajax.set("conditionKey","la.kaike.ksearch.model.vo.hotfix.HotFixVO")
        ajax.start();



        /**
         * 查询
         */
        $(".btn-query").on("click",function () {


            $('#indices_table').bootstrapTable("removeAll");
            $('#indices_table').bootstrapTable("selectPage",1);
            //$('#indices_table').bootstrapTable("refresh");
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
            url:"/hotfix/query",
            pageList:[10,20,30],
            pagination:true,
            //detailView:true,
            //showRefresh:true,
            showColumns:true,
            //showPaginationSwitch:true,
            //showFooter:true,
            pageSize:10,
            columns: [{
                field: 'userId',
                title: '<span class="text-primary">userId</span>'
            }, {
                field: 'datetime',
                title: '<span class="text-primary">datetime</span>',
                sortable:true
            }, {
                field: 'deviceId',
                title: '<span class="text-primary">deviceId</span>'
            },  {
                field: 'appVersion',
                title: '<span class="text-primary">appVersion</span>'
            },{
                field: 'patchId',
                title: '<span class="text-primary">patchId</span>'
            }, {
                field: 'systemVersion',
                title: '<span class="text-primary">systemVersion</span>'
            }, {
                field: 'brand',
                title: '<span class="text-primary">brand</span>'
            }, {
                field: 'model',
                title: '<span class="text-primary">model</span>'
            }, {
                field: 'event',
                title: '<span class="text-primary">event</span>'
            }, {
                field: 'extra1',
                title: '<span class="text-primary">extra1</span>'
            }, {
                field: 'extra2',
                title: '<span class="text-primary">extra2</span>'
            }, {
                field: 'extra3',
                title: '<span class="text-primary">extra3</span>'
            }, {
                field: 'extra4',
                title: '<span class="text-primary">extra4</span>'
            }, {
                field: 'extra5',
                title: '<span class="text-primary">extra5</span>'
            }],
            silentSort:false,
            queryParams:function (params) {

                params.type = "log";
                params.clusterName = clusterName;
                var t = $('form').serializeArray();
                $.each(t, function() {
                    params[this.name] = this.value.trim();
                });
                return params;
            }
        });


        $(".btn-reset").on("click",function () {
            $('#query-form')[0].reset();
            $("#appname").select2('val', '');
            $("#dateinfo").select2('val', '');
        });

    });


    function initDateInput() {
        $('input[name=datetime]').daterangepicker({
            timePicker : true,
            format : 'YYYY/MM/DD HH:mm:ss',
            timePickerIncrement : 1, //时间的增量，单位为分钟
            timePicker12Hour : false,
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
    }


</script>
</body>
</html>
