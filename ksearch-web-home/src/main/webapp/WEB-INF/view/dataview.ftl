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
                            <form id="queryForm">
                                <div class="col-xs-12">
                                    <div class="form-group">
                                        <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="select_index">索引（index）:</label>

                                        <div class="col-xs-12 col-sm-3">
                                            <div class="clearfix">
                                                <input type="hidden" id="select_index" class="width-100 select2">
                                                </input>
                                            </div>
                                        </div>
                                        <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="select_type">类型（type）:</label>

                                        <div class="col-xs-12 col-sm-3">
                                            <div class="clearfix">
                                                <input type="hidden" id="select_type" class="width-100 select2">
                                                </input>
                                            </div>
                                        </div>

                                        <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="keyword">关键字:</label>

                                        <div class="col-xs-12 col-sm-3">
                                            <div class="clearfix">
                                                <input type="text" id="keyword"  class="col-xs-12 col-sm-12" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <hr>
                                <div class="col-xs-5" style="width: 47.5%;">
                                    <div class="hr hr32 hr-dotted" style="margin:12px 0"></div>
                                </div>
                                <div class="col-xs-2" style="width: 5%;">
                                    <div><a href="javascript:void (0);" style="color:#a7a6a6;text-decoration:none;" id="more">更多...</a></div>
                                </div>
                                <div class="col-xs-5" style="width: 47.5%;">
                                    <div class="hr hr32 hr-dotted" style="margin:12px 0"></div>
                                </div>

                                <div class="col-xs-12 hide-form" id="detail_query" style="display: none;">
                                </div>


                                <div class="col-xs-12" id="query-btn">
                                    <div class="form-group">
                                        <div class="col-xs-13 col-sm-11" style="width: 94%;">
                                        </div>
                                        <div class="col-xs-13 col-sm-1" style="width: 6%;">
                                            <input type="button" id="btn-query" value="查询" class="btn btn-success btn-query">
                                            </input>
                                        </div>
                                    </div>
                                </div>
                            </form>
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
<script src="/assets/js/date-time/daterangepicker.min.js"></script>
<script src="/assets/js/date-time/moment.min.js"></script>
<!-- inline scripts related to this page -->
<script type="text/javascript">
    var columns=[];
    var clusterName = Util.cookie.get("cluster-name");

    jQuery(function($) {



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


        $("#select_type").select2({
            allowClear: true,
            placeholder: "请选择类型",
            data:[]
        });

        $("#field").select2({
            allowClear: true,
            placeholder: "请选择字段",
            data:[]
        });

        //$('#indices_table').bootstrapTable();
        /**
         * 查询
         */
        $("#btn-query").on("click",function () {

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

            var keyword = $("#keyword").val();

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
                        //method: 'post',
                        //dataType: "json",
                        method:"post",
                        contentType:"application/x-www-form-urlencoded; charset=UTF-8",
                        url:"/dataview/query",
                        pageList:[10, 20, 40, 60],
                        pagination:true,
                        detailView:true,
                        //showRefresh:true,
                        showColumns:true,
                        //showPaginationSwitch:true,
                        //showFooter:true,
                        pageSize:10,
                        columns:columns,
                        silentSort:false,
                        queryParams:function (params) {
                            params.indices = index;
                            params.types = type;
                            params.keyword = keyword;
                            params.clusterName = clusterName;

                            var t = $('#queryForm').serializeArray();
                            var jsonString = {};
                            if (t.length>0) {
                                var has = false;
                                $.each(t, function () {
                                    var value = this.value.trim();
                                    if(value){
                                        jsonString[this.name] = value;
                                        has = true;
                                    }
                                });
                                if (has){
                                    params["source"] = JSON.stringify(jsonString);
                                }
                            }
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

        });

        
        $("#more").on("click",function () {
            if($(".hide-form").css("display")=="none") {
                $(".hide-form").show();
                $(this).text("收起...");
                $("#detail_query").html("");
                // 初始化查询条件
                initQueryCondition();
            }else {
                $(".hide-form").hide();
                $(this).text("更多...");
            }
        });


    });




    /**
     * 初始化查询条件
     */
    function initQueryCondition() {
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

                $("#detail_query").append(html);
                initDateInput();
            }

        });

        var type = $("#select_type").select2("val");
        var index = $("#select_index").select2("val");
        ajax.set("index",index);
        ajax.set("type",type);
        ajax.start();
    }

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

    /**
     * 解析字段
     * @type {[*]}
     */
    var fieldData = [{id:"_all",text:"_all"}];
    function getFieldData(data,prefix) {
        $.each(data,function (i,item) {
            if (item.type =="object" || item.type =="nested"){
                $.each(item.children,function (i,t) {
                    if (t.type =="object" || t.type =="nested"){
                        var pre = "";
                        if(prefix!=""){
                            pre=prefix+".";
                        }else {
                            pre=prefix;
                        }
                        pre = pre+item.fieldName+"."
                        var pre =pre+t.fieldName+".";
                        console.log(t);


                        getFieldData(t.children,pre)
                    }else {
                        var value ="";
                        if(prefix!=""){
                             value=prefix+".";
                        }else {
                            value = prefix;
                        }

                        value+=item.fieldName+".";
                        var value = value+t.fieldName;
                        fieldData.push({id:value,text:value});
                    }
                });

            }else {
                var value = prefix+item.fieldName;
                fieldData.push({id:value,text:value});
            }

        });

    }




    /**
     * 重置查询明细div
     */
    function resetDetailQuery() {

        $(".hide-form").each(function (i) {
            if(i==0){
                $($(this).find('select[name="field"]')[0]).html('');
            }
            if (i>0){
                $(this).remove();
            }
        });
    }
    
    function getDetailValue() {
        var fieldValues = [];
        $("select[name='field']").each(function () {
            fieldValues.push($(this).val());
        });
        var boolValues = [];
        $("select[name='bool']").each(function () {
            boolValues.push($(this).val());
        });
        var opValues = [];
        $("select[name='op']").each(function () {
            opValues.push($(this).val());
        });

        var queryValues = [];
        $("input[name='query_value']").each(function () {
            queryValues.push($(this).val());
        });

        var source = {"query":
                {"bool":{

                 }
                }
        };
        $.each(boolValues,function (i,value) {
            if(!source.query.bool[value]){
                source.query.bool[value]=[];
            }
            var fieldValue = fieldValues[i];
            var opValue = opValues[i];
            if(opValue==="query_string"){
                var query_string = {
                    "query_string":{
                    "default_field":fieldValue,
                            "query":queryValues[i]
                    }
                };
                source.query.bool[value].push(query_string);
            }
            else if(opValue==="prefix"){
                var prefix = {
                    "prefix":{
                    }
                };
                prefix.prefix[fieldValue]=queryValues[i];
                source.query.bool[value].push(prefix);
            }
            else if(opValue==="term"){
                var should = {
                    "term":{
                    }
                };
                should.term[fieldValue]=queryValues[i];
                source.query.bool[value].push(should);
            }

        });
        //var query = {"query":source};
        return source.query;
    }

</script>
</body>
</html>
