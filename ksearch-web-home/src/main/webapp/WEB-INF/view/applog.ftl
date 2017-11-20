<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>手机app日志查询</title>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <!-- basic styles -->
<#include "/common/head_css.ftl"/>
    <link rel="stylesheet" href="assets/css/daterangepicker.css" />
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
                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">app版本号:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="text" id="version" placeholder="支持模糊匹配（version）" class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">操作系统:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <select id="platform" name="platform" class="width-100 select2"  data-placeholder="platform">
                                                <option value="" selected> </option>
                                                <option value="1">IOS</option>
                                                <option value="2">Android</option>
                                            </select>
                                        </div>
                                    </div>

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">操作系统版本:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="text" id="osVersion" placeholder="支持前缀模糊查询（osVersion）"  class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="space"></div>

                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">唯一标识符:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="text" id="bundleIdentifier" placeholder="bundleIdentifier" class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">用户令牌:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="text" id="clientToken" placeholder="clientToken"  class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">日志内容:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="text" id="contentData" placeholder="支持模糊查询(contentData)" class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="space"></div>

                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">手机品牌:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="text" id="brand" name="brand" placeholder="支持前缀模糊查询(brand)" class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">屏幕尺寸:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="text" id="resolution" name="resolution" placeholder="resolution" class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">安装来源:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="text" id="channel" name="channel" placeholder="channel" class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="space"></div>


                            <div class="col-xs-12">
                                <div class="form-group">

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">手机型号:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="text" id="deviceModel" name="deviceModel" placeholder="支持前缀模糊查询(deviceModel)" class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">imei:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="text" id="imei" name="imei" placeholder="imei" class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">上报时间:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input class="form-control" type="text" name="uploadDate" id="uploadDate" placeholder="uploadDate" />
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="space"></div>

                            <div class="col-xs-12">
                                <div class="form-group">

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">用户账号:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="text" id="clientAccount" name="clientAccount" placeholder="clientAccount" class="col-xs-12 col-sm-12" />
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

        $('input[name=uploadDate]').daterangepicker({
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
            url:"/applog/query",
            pageList:[5, 10],
            pagination:true,
            detailView:true,
            //showRefresh:true,
            showColumns:true,
            //showPaginationSwitch:true,
            //showFooter:true,
            pageSize:5,
            columns: [{
                field: 'clientAccount',
                title: '<span class="text-primary">用户账号</span>'
            },{
                field: 'version',
                title: '<span class="text-primary">app版本号</span>'
            }, {
                field: 'platform',
                title: '<span class="text-primary">操作系统</span>',
                formatter: function (value, row, index) {
                    if(row.platform===1){
                        return "IOS";
                    }else {
                        return "Android";
                    }
                }
            }, {
                field: 'osVersion',
                title: '<span class="text-primary">操作系统版本</span>'
            },  {
                field: 'bundleIdentifier',
                title: '<span class="text-primary">唯一标识符</span>'
            }, {
                field: 'clientToken',
                title: '<span class="text-primary">用户令牌</span>'
            }, {
                field: 'brand',
                title: '<span class="text-primary">手机品牌</span>'
            }, {
                field: 'resolution',
                title: '<span class="text-primary">屏幕尺寸</span>'
            }, {
                field: 'channel',
                title: '<span class="text-primary">安装来源</span>'
            }, {
                field: 'deviceModel',
                title: '<span class="text-primary">手机型号</span>'
            }, {
                field: 'imei',
                title: '<span class="text-primary">imei</span>'
            }, {
                field: 'uploadDate',
                title: '<span class="text-primary">上报时间</span>',
                formatter: function (value, row, index) {
                    return formatDateTime(row.uploadDate);
                }
            }],
            silentSort:false,
            queryParams:function (params) {
                params.version = $.trim($("#version").val());
                params.osVersion = $.trim($("#osVersion").val());
                params.platform = $.trim($("#platform").val());
                params.bundleIdentifier = $.trim($("#bundleIdentifier").val());
                params.clientToken = $.trim($("#clientToken").val());
                params.contentData = $.trim($("#contentData").val());
                params.uploadDate = $.trim($("#uploadDate").val());
                params.brand = $.trim($("#brand").val());
                params.resolution = $.trim($("#resolution").val());
                params.channel = $.trim($("#channel").val());
                params.deviceModel = $.trim($("#deviceModel").val());
                params.imei = $.trim($("#imei").val());
                params.clusterName = clusterName;
                params.clientAccount = $.trim($("#clientAccount").val());
                return params;
            },
            onExpandRow: function (index, row, $detail) {


                $detail.html('<pre>正在加载...</pre>');
                var id = row.id;
                var type_aj = new $ax("/applog/detail",function (data) {
                    if (data.status){
                        contentData = data.data.contentData;
                        var displayData = "";
                        var more = '';
                        if (contentData!==null) {
                            if (contentData.length > limit) {
                                displayData = contentData.substr(0, limit);
                                more = '<a data-start=' + limit + ' class="load-data">&nbsp;更多...</a>&nbsp;<a class="load-all">&nbsp;展开全部</a>';
                            } else {
                                displayData = contentData;
                            }

                            $detail.html('<pre>' + displayData + '</pre>' + more + '&nbsp;<a class="download" href="/applog/download?id=' + id + '&clusterName=' + clusterName + '" >&nbsp;下载</a>');
                        }else {
                            $detail.html('<pre></pre>');
                        }
                    }
                });
                type_aj.set("id",row.id);
                type_aj.set("clusterName",clusterName);
                type_aj.start();
            },
            onLoadSuccess:function () {
            }

        });

        $(document).on("click",".load-data",function () {
            var start = parseInt($(this).data("start"));
            console.log(start);
            var preNode = $(this).prev();
            preNode.append(contentData.substr(start,limit));
            if(start+limit>=contentData.length){
                $(this).next().remove();
                $(this).remove();
            }
            $(this).data("start",start+limit);
        });


        $(document).on("click",".load-all",function () {
            var preNode = $(this).prev();
            var start = parseInt(preNode.data("start"));
            $(this).after('<span>请稍后,正在玩命加载中...</span>');
            var next = $(this).next();
            //preNode.prev().text("请稍后,正在玩命加载...");
            var  size= Math.ceil((contentData.length-start)/limit);
            var context = preNode.prev();
            var i=0;
            var timmer = setInterval(function () {
                                    context.append(contentData.substr(start+i*limit,limit));
                                    i++;
                                if(i==size){
                                    clearInterval(timmer);
                                    next.text('加载完毕');
                                }
                            },1000);




            preNode.remove();
            $(this).remove();
        });
    });

    var contentData = "";
    var limit =5000;

    function formatDateTime(inputTime) {
        var date = new Date(inputTime);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        var h = date.getHours();
        h = h < 10 ? ('0' + h) : h;
        var minute = date.getMinutes();
        var second = date.getSeconds();
        minute = minute < 10 ? ('0' + minute) : minute;
        second = second < 10 ? ('0' + second) : second;
        return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
    };
</script>
</body>
</html>
