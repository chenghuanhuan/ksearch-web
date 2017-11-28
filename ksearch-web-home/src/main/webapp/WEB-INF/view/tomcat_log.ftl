<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>应用日志查询</title>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <!-- basic styles -->
<#include "/common/head_css.ftl"/>
    <link rel="stylesheet" href="/assets/css/bootstrap-timepicker.css" />
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
                            <form id="query-form">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="select_index">应用名称:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="hidden" id="appname" name="appname" class="width-100 select2">
                                            </input>
                                        </div>
                                    </div>

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">文件名称:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="hidden" id="logtype" name="logtype" class="width-100 select2">
                                            </input>
                                        </div>
                                    </div>

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">日期:</label>
                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="hidden" id="dateinfo" name="dateinfo" class="width-100 select2">
                                            </input>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="space"></div>


                            <div class="col-xs-12">
                                <div class="form-group">

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">level:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <select id="level" name="level" class="width-100 select2"  data-placeholder="Click to Choose...">
                                                <option value="" selected> </option>
                                                <option value="ERROR">ERROR</option>
                                                <option value="INFO">INFO</option>
                                                <option value="WARN">WARN</option>
                                                <option value="DEBUG">DEBUG</option>
                                            </select>
                                        </div>
                                    </div>

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">host:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="text" id="host" name="host" placeholder="" class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">时间:</label>

                                    <div class="col-xs-12 col-sm-1" style="width: 12.5%;">
                                        <div class="input-group bootstrap-timepicker">
                                            <input id="startTime" name="startTime" type="text" class="form-control" />
                                        </div>
                                    </div>
                                    <div class="col-xs-12 col-sm-1" style="width: 12.5%;">
                                        <div class="input-group bootstrap-timepicker">
                                            <input id="endTime" name="endTime" type="text" class="form-control" />
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="space"></div>

                            <div class="col-xs-12">
                                <div class="form-group">

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">className:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="text" id="className" name="className" placeholder="" class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>

                                    <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="message">message:</label>

                                    <div class="col-xs-12 col-sm-3">
                                        <div class="clearfix">
                                            <input type="text" id="message" name="message" placeholder="" class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>
                                </div>
                            </div>

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
                                    <div class="col-xs-13 col-sm-1" style="width: 6%;">
                                        <button class="btn btn-primary btn-refresh">
                                            自动刷新
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
<script src="/assets/js/date-time/bootstrap-timepicker.min.js"></script>
<script src="/assets/js/date-time/moment.min.js"></script>
<!-- inline scripts related to this page -->
<script type="text/javascript">
    var clusterName = Util.cookie.get("cluster-name");
    var indexInfo = null;
    var appNameArr = [];
    var timmer1 = null;
    function getLogType(appname) {
        var loginfos = indexInfo[appname];
        var loginfoArrs =[];
        for(var key in loginfos){
            loginfoArrs.push({id:key,text:key});
        }

        return loginfoArrs;
    }

    function getLogDate(appname,loginfo) {
        var loginfos = indexInfo[appname];
        var dateinfos = loginfos[loginfo];
        var dateinfoArr =[];
        for(var v in dateinfos){
            dateinfoArr.push({id:dateinfos[v],text:dateinfos[v]})
        }
        return dateinfoArr;
    }

    $('#startTime').timepicker({
        minuteStep: 1,
        showSeconds: true,
        showMeridian: false,
        defaultTime: false
    }).next().on(ace.click_event, function(){
        $(this).prev().focus();
    });

    $('#endTime').timepicker({
        minuteStep: 1,
        showSeconds: true,
        showMeridian: false,
        defaultTime: false
    }).next().on(ace.click_event, function(){
        $(this).prev().focus();
    });


    jQuery(function($) {


        // 应用名称
        var ajIndex = new $ax("/tomcat/appname", function (data) {
            if(data.status){

                indexInfo = data.data;

                if (indexInfo){
                    for(var key in indexInfo){
                        appNameArr.push({id:key,text:key});
                    }

                }


                $("#appname").select2({
                    allowClear: true,
                    placeholder: "请选择应用名称",
                    data:appNameArr
                });

                $("#logtype").select2({
                    allowClear: true,
                    placeholder: "请选择文件名",
                    data:[]
                });

                $("#dateinfo").select2({
                    allowClear: true,
                    placeholder: "请选择日期",
                    data:[]
                });

                /*  应用名变更**/
                $("#appname").on("change", function (e) {
                   var logtypeArrs = getLogType($(this).val());
                    $("#logtype").select2({
                        allowClear: true,
                        placeholder: "请选择文件名",
                        data:logtypeArrs
                    });
                });

                /*  文件名变更**/
                $("#logtype").on("change", function (e) {
                    var appname = $("#appname").val();
                    var logtype = $("#logtype").val();
                    var dateArr = getLogDate(appname,logtype);
                    $("#dateinfo").select2({
                        allowClear: true,
                        placeholder: "请选择日期",
                        data:dateArr
                    });
                });
            }
        });
        ajIndex.set("clusterName",clusterName);
        ajIndex.start();

        /**
         * 查询
         */
        $(".btn-query").on("click",function () {
            if(!$("#appname").val()){
                $myNotify.danger("请选择应用名称");
                return;
            }
            if(!$("#logtype").val()){
                $myNotify.danger("请选择文件名称");
                return;
            }
            if(!$("#dateinfo").val()){
                $myNotify.danger("请选择日期");
                return;
            }

            $('#indices_table').bootstrapTable("removeAll");
            $('#indices_table').bootstrapTable("refresh");
        });

        $(".btn-refresh").on("click",function () {
            var r = $(this).attr("refresh");
            if(r=="ok"){
                window.clearInterval(timmer1);
                $(this).attr("refresh","");
                $(this).text("自动刷新");
            }else {
                $(this).text("停止刷新");
                $(this).attr("refresh","ok");
                timmer1 = window.setInterval(function () {
                    $('#indices_table').bootstrapTable("refresh");
                },5000);
            }
        });

        $(".btn-reset").on("click",function () {
            $('#query-form')[0].reset();
            $("#appname").select2('val', '');
            $("#logtype").select2('val', '');
            $("#dateinfo").select2('val', '');
            $("#logtype").select2({
                allowClear: true,
                placeholder: "请选择文件名",
                data:[]
            });

            $("#dateinfo").select2({
                allowClear: true,
                placeholder: "请选择日期",
                data:[]
            });
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
            url:"/tomcat/query",
            pageList:[5, 10],
            pagination:true,
            //detailView:true,
            //showRefresh:true,
            showColumns:true,
            //showPaginationSwitch:true,
            //showFooter:true,
            pageSize:5,
            columns: [{
                field: 'datetime',
                title: '<span class="text-primary">datetime</span>',
                formatter: function (value, row, index) {
                    return formatDateTime(row.datetime);
                }
            }, {
                field: 'host',
                title: '<span class="text-primary">host</span>'
            },  {
                field: 'level',
                title: '<span class="text-primary">level</span>'
            },{
                field: 'className',
                title: '<span class="text-primary">className</span>'
            }, {
                field: 'message',
                title: '<span class="text-primary">message</span>'
            }, {
                field: 'remoteAddr',
                title: '<span class="text-primary">remoteAddr</span>'
            }, {
                field: 'requestURIWithQueryString',
                title: '<span class="text-primary">requestURIWithQueryString</span>'
            }, {
                field: 'userId',
                title: '<span class="text-primary">userId</span>'
            }],
            silentSort:false,
            queryParams:function (params) {

                var index = $("#appname").val()+"."+$("#logtype").val()+"."+$("#dateinfo").val();

                params.index = index;
                params.type = "log";
                params.host = $.trim($("#host").val());
                if($.trim($("#startTime").val())) {
                    params.startTime = $("#dateinfo").val() + " " + $.trim($("#startTime").val());
                }
                $.trim($("#endTime").val())
                {
                    params.endTime = $("#dateinfo").val() + " " + $.trim($("#endTime").val());
                }
                params.className = $.trim($("#className").val());
                params.level = $.trim($("#level").val());
                params.message = $.trim($("#message").val());
                params.clusterName = clusterName;
                return params;
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
        var sss = date.getMilliseconds();
        minute = minute < 10 ? ('0' + minute) : minute;
        second = second < 10 ? ('0' + second) : second;
        return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second+","+sss;
    };
</script>
</body>
</html>
