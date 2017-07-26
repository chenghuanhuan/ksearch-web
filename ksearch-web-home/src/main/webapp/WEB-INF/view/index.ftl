<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>索引管理</title>
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<!-- basic styles -->
		<#include "/common/head_css.ftl"/>
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
							<li class="active">索引管理</li>
						</ul><!-- .breadcrumb -->

						<div class="nav-search" id="nav-search">
							<form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="icon-search nav-search-icon"></i>
								</span>
							</form>
						</div><!-- #nav-search -->
					</div>

					<div class="page-content">
				<#--		<div class="page-header">
							<h1>
								索引管理
								<small>
									<i class="icon-double-angle-right"></i>
									索引列表
								</small>
							</h1>
						</div>-->
						<!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->

								<div class="row">


									<div class="col-sm-12">
										<div class="widget-box">
											<div class="widget-header widget-header-flat">
												<h4>索引列表</h4>
											</div>

											<div class="widget-body">
												<div class="widget-main">

                                                    <#--<hr />-->
													<div class="row">
														<div class="col-xs-12">
                                                         <div id="toolbar">
                                                             <button id="addIndex" class="btn btn-sm btn-success">
                                                                 <i class="icon-ok bigger-110"></i>
                                                                 添加索引
                                                             </button>
                                                         </div>
                                                            <table id="indices_table"></table>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div><!-- /span -->
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

		<!-- inline scripts related to this page -->
        <script src="/assets/js/fuelux/fuelux.wizard.min.js"></script>
        <script src="/assets/js/jquery.nestable.js"></script>
		<script type="text/javascript">
			jQuery(function($) {

                $('#indices_table').bootstrapTable({
                    striped:true,
                    //classes:"table table-no-bordered",
                    //showHeader:false,
                    //cardView:true,
                    //checkboxEnable:true,
                    search:true,
                    toolbar:"#toolbar",
                    url:"/console/cluster/indeices",
                    detailView:true,
                    columns: [{
                        field: 'index',
                        title: '<span class="text-primary">索引</span>'
                    }, {
                        field: 'docs',
                        title: '<span class="text-primary">文档</span>'
                    }, {
                        field: 'primarySize',
                        title: '<span class="text-primary">占用空间</span>'
                    }, {
                        field: 'shards',
                        title: '<span class="text-primary">主分片个数</span>'
                    }, {
                        field: 'replicas',
                        title: '<span class="text-primary">副分片个数</span>'
                    }, {
                        field: 'status',
                        title: '<span class="text-primary">状态</span>',
                        formatter: function (value, row, index) {
                            if (value === "OPEN"){
                                var ret = '<span class="label label-sm label-success">'+value+'</span>';
                                return ret;
                            }else{
                                var ret = '<span class="label label-sm label-default">'+value+'</span>';
                                return ret;
                            }
                        }
                    }, {
                        field: 'operate',
                        title: '操作',
                        align: 'center',
                        events: operateEvents,
                        width:165,
                        formatter: function (value, row, index) {
							var btns = '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">'
                                    +'<button class="btn btn-xs btn-success tooltip-success refresh" data-rel="tooltip" title="刷新">'
                                    +'<i class="icon-ok bigger-120"></i>'
                                    +'</button>'

                                    +'<button class="btn btn-xs btn-info tooltip-info close-index" data-rel="tooltip" title="关闭">'
                                    +'<i class="icon-edit bigger-120"></i>'
                                    +'</button>'

                                    +'<button class="btn btn-xs btn-danger tooltip-danger del" data-rel="tooltip" title="删除">'
                                    +'<i class="icon-trash bigger-120"></i>'
                                    +'</button>'

                                    +'<button class="btn btn-xs btn-warning tooltip-warning addType" data-rel="tooltip" title="新增类型">'
                                    +'<i class="icon-flag bigger-120"></i>'
                                    +'</button>'

                                    +''
                                    +'   <button data-toggle="dropdown" class="btn btn-xs btn-default tooltip-default" data-rel="tooltip" title="更多">'
                                    +'..'
                                    +'     <span class="icon-caret-down icon-on-right"></span>'
                                    +'  </button>'

                                    +'<ul class="dropdown-menu dropdown-info pull-right">'
                                    +'  <li>'
                                    +'   <a class="flush-index" href="#">flush</a>'
                                    +'  </li>'

                                    +'  <li>'
                                    +'   <a class="optimize-index" href="#">优化</a>'
                                    +'  </li>'
                                    +'  <li>'
                                    +'   <a class="alias-index" href="#">新建别名</a>'
                                    +'  </li>'
                                    +'</ul>'
                            		+''

                                    +'</div>';
							return btns;
                        }
                    }],
                    onLoadSuccess:function () {
                        $('[data-rel=tooltip]').tooltip();
                    },
                    onExpandRow: function (index, row, $detail) {
                        expandTypeTable(index,row,$detail);
                    }
                });

                // 添加索引

                $("#addIndex").on(ace.click_event, function() {

                    BootstrapDialog.show({
                        type:BootstrapDialog.TYPE_PRIMARY,
                        title: '添加索引',
                        closeByBackdrop: false,
                        closeByKeyboard: false,
                        //size:BootstrapDialog.SIZE_LARGE,
                        message: function(dialog) {
                            var form ='<div class="widget-main">'
                                        +'<form class="form-horizontal" role="form">'
                                            +'<div class="form-group">'
                                                +'<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 索引名称 :</label>'
                                                +'<div class="col-sm-10">'
                                                     +'<input class="form-control input-mask-date" placeholder="请输入英文或数字" type="text" id="index" />'
                                                +'</div>'
                                            +'</div>'
                                            +'<div class="form-group">'
                                                +'<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 分片数 :</label>'
                                                +'<div class="col-sm-4">'
                                                    +'<input type="text" class="input-mini" id="number_of_shards" />'
                                                +'</div>'
                                                +'<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 副本数 :</label>'
                                                +'<div class="col-sm-4">'
                                                    +'<input type="text" class="input-mini" id="number_of_replicas" />'
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
                                dialogRef.enableButtons(false);
                                dialogRef.setClosable(false);
                                //dialogRef.getModalBody().html('Dialog closes in 5 seconds.');
                                var index = $.trim($("#index").val());
                                var number_of_shards = parseInt($("#number_of_shards").val());
                                var number_of_replicas= parseInt($("#number_of_replicas").val());

                                // TODO 校验
                                var ajax = new $ax("/index/add", function (data) {
                                    // 成功
                                    if (data.status===true){
                                        //$myNotify.success("保存成功");
                                        $myNotify.success("保存成功")
                                        // 刷新表格
                                        $('#indices_table').bootstrapTable('refresh');
                                        // 关闭窗口
                                        dialogRef.close();
                                    }else {
                                        $myNotify.danger(data.msg);
                                        dialogRef.enableButtons(true);
                                        dialogRef.setClosable(true);
                                    }
                                },function (data) {
                                    
                                });
                                ajax.set("index",index);
                                ajax.set("numberOfShards",number_of_shards);
                                ajax.set("numberOfReplicas",number_of_replicas);
                                ajax.set("clusterName","");
                                ajax.start();

                            }
                        }, {
                            icon:'icon-remove',
                            label: '关闭',
                            action: function(dialogRef){
                                dialogRef.close();
                            }
                        }],
                        onshown:function () {
                            $('#number_of_shards').ace_spinner({value:1,min:1,max:100,step:1, on_sides: true, icon_up:'icon-plus smaller-75', icon_down:'icon-minus smaller-75', btn_up_class:'btn-success' , btn_down_class:'btn-danger'});
                            $('#number_of_replicas').ace_spinner({value:1,min:1,max:100,step:1, on_sides: true, icon_up:'icon-plus smaller-75', icon_down:'icon-minus smaller-75', btn_up_class:'btn-success' , btn_down_class:'btn-danger'});
                        }
                    });

                });


			/*	window.prettyPrint && prettyPrint();
				$('#id-check-horizontal').removeAttr('checked').on('click', function(){
					$('#dt-list-1').toggleClass('dl-horizontal').prev().html(this.checked ? '&lt;dl class="dl-horizontal"&gt;' : '&lt;dl&gt;');
				});*/
			
			});

			function expandTypeTable(index,row,$detail) {
                $detail.html('<table id="type_table_'+row.index+'"></table>').find('table').bootstrapTable({
                    striped:true,
                    //classes:"table table-no-bordered",
                    //showHeader:false,
                    //cardView:true,
                    //checkboxEnable:true,
                    detailView:true,
                    url:"/index/getAllMapping",
                    queryParams:function (params) {
                        params.index = row.index;
                        params.clusterName = "";
                        return params;
                    },
                    columns: [{
                        field: 'type',
                        title: '类型'
                    },{
                        field: 'include_in_all',
                        title: 'include_in_all'
                     }, {
                        field: 'primarySize',
                        title: '操作',
                        events: operateEvents,
                        formatter: function (value, row, index) {
                            var btn = '<button class="btn btn-xs btn-success tooltip-success modifyType" data-rel="tooltip" title="修改类型">'
                                    +'<i class="icon-ok bigger-120"></i>'
                                    +'</button>';
                            return btn;
                        }
                    }],
                    onExpandRow: function (index, row, $detail) {
                        expandMapping(index,row,$detail);
                    }
                });

            }

            function expandMapping(index,row,$detail) {
                $detail.html('<table></table>').find('table').bootstrapTable({
                    striped:true,
                    //url:"/console/cluster/indeices",
                    data:row.properties,
                    columns: [{
                        field: 'name',
                        title: '属性名称'
                    }, {
                        field: 'type',
                        title: '类型'
                    }, {
                        field: 'index',
                        title: '是否分词'
                    }, {
                        field: 'analyzer',
                        title: '分词器'
                    }, {
                        field: 'ignore_above',
                        title: '索引最大长度'
                    }, {
                        field: 'primarySize',
                        title: '操作',
                        formatter: function (value, row, index) {
                            var btn = '<button class="btn btn-xs btn-success tooltip-success" data-rel="tooltip" title="修改类型">'
                                        +'<i class="icon-ok bigger-120"></i>'
                                     +'</button>';
                            return btn;
                        }
                    }]
                });
            }

            window.operateEvents = {
                'click .del': function (e, value, row, index) {
                    var msg = '确认要删除索引:<strong style="color: red;">'+row.index+'</strong>吗？删除后数据无法恢复，请确认后再操作！！！';

                    $myDialog.confirm(msg,BootstrapDialog.TYPE_DANGER,
                            function(result) {
                                // result will be true if button was click, while it will be false if users close the dialog directly.
                                if(result) {
                                    var ajax = new $ax("/index/del", function (data) {
                                        // 成功
                                        if (data.status===true){
                                            $myNotify.success("删除成功");
                                            // 刷新表格
                                            $('#indices_table').bootstrapTable('refresh');
                                        }else {
                                            $myNotify.danger(data.msg);
                                        }
                                    },function (data) {

                                    });
                                    ajax.set("indices",row.index);
                                    ajax.set("clusterName","");
                                    ajax.start();
                                }
                            }
                    );
                },
                'click .refresh': function (e, value, row, index) {

                    var msg = '确认刷新索引：<strong style="color: #ffb752;">'+row.index+'</strong>吗？';
                    $myDialog.confirm(msg,BootstrapDialog.TYPE_WARNING,
                            function(result) {
                                // result will be true if button was click, while it will be false if users close the dialog directly.
                                if(result) {
                                    var ajax = new $ax("/index/refresh", function (data) {
                                        // 成功
                                        if (data.status===true){
                                            $myNotify.success("刷新成功");
                                            // 刷新表格
                                            $('#indices_table').bootstrapTable('refresh');
                                        }else {
                                            $myNotify.danger(data.msg);
                                        }
                                    },function (data) {

                                    });
                                    ajax.set("indices",row.index);
                                    ajax.set("clusterName","");
                                    ajax.start();
                                }
                            }
                    );
                },
                'click .close-index': function (e, value, row, index) {
                    var msg = '确认关闭索引:<strong style="color: #ffb752;">'+row.index+'</strong>吗？';
                    $myDialog.confirm(msg,BootstrapDialog.TYPE_WARNING,
                            function(result) {
                                // result will be true if button was click, while it will be false if users close the dialog directly.
                                if(result) {
                                    var ajax = new $ax("/index/close", function (data) {
                                        // 成功
                                        if (data.status===true){
                                            $myNotify.success(data.data);
                                            // 刷新表格
                                            $('#indices_table').bootstrapTable('refresh');
                                        }else {
                                            $myNotify.danger(data.msg);
                                        }
                                    },function (data) {

                                    });
                                    ajax.set("indices",row.index);
                                    ajax.set("clusterName","");
                                    ajax.start();
                                }
                            }
                    );
                },
                'click .flush-index': function (e, value, row, index) {

                    var msg = '确认flush索引:<strong style="color: #ffb752;">'+row.index+'</strong>吗？';
                    $myDialog.confirm(msg,BootstrapDialog.TYPE_WARNING,
                            function(result) {
                                // result will be true if button was click, while it will be false if users close the dialog directly.
                                if(result) {
                                    var ajax = new $ax("/index/flush", function (data) {
                                        // 成功
                                        if (data.status===true){
                                            $myNotify.success(data.data);
                                            // 刷新表格
                                            $('#indices_table').bootstrapTable('refresh');
                                        }else {
                                            $myNotify.danger(data.msg);
                                        }
                                    },function (data) {
                                    });
                                    ajax.set("indices",row.index);
                                    ajax.set("clusterName","");
                                    ajax.start();
                                }
                            }
                    );
                },
                'click .alias-index': function (e, value, row, index) {
                    BootstrapDialog.show({
                        type:BootstrapDialog.TYPE_PRIMARY,
                        title: '优化',
                        closeByBackdrop: false,
                        closeByKeyboard: false,
                        message: function(dialog) {
                            var form ='<div class="widget-main">'
                                    +'<form class="form-horizontal" role="form">'

                                        +'<div class="form-group">'
                                            +'<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 别名 :</label>'
                                            +'<div class="col-sm-10">'
                                                +'<input type="text" class="form-field-2" name="tags" id="aliases" value="" placeholder="请输入别名，多个请使用回车键" />'
                                                +'<input type="hidden" id="aliases_server" value=""/>'
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
                                var aliases = $.trim($("#aliases").val());
                                if (!aliases){
                                    $myNotify.danger("请输入别名，多个使用回车键。");
                                }
                                var index = row.index;
                                dialogRef.enableButtons(false);
                                dialogRef.setClosable(false);
                                var ajax = new $ax("/index/addAlias", function (data) {
                                    // 成功
                                    if (data.status===true){
                                        $myNotify.success(data.msg);
                                        // 刷新表格
                                        $('#indices_table').bootstrapTable('refresh');
                                        // 关闭窗口
                                        dialogRef.close();
                                    }else {
                                        $myNotify.danger(data.msg);
                                        dialogRef.enableButtons(true);
                                        dialogRef.setClosable(true);
                                    }
                                },function (data) {

                                });
                                ajax.set("index",index);
                                ajax.set("aliases",aliases)
                                ajax.set("clusterName","");
                                ajax.start();

                            }
                        }, {
                            icon:'icon-remove',
                            label: '关闭',
                            action: function(dialogRef){
                                dialogRef.close();
                            }
                        }],
                        onshown:function () {
                            // 初始化tags
                            var tag_input = $('#aliases');
                            var aliases = row.aliases;
                            tag_input.val(aliases.join(","));
                            $("#aliases_server").val(aliases.join(","));

                            var index = row.index;
                            tag_input.tag(
                                    {
                                        placeholder:tag_input.attr('placeholder'),
                                        //enable typeahead by specifying the source array
                                        //source: ace.variable_US_STATES,//defined in ace.js >> ace.enable_search_ahead
                                        afterRemove:function (e) {
                                            // 判断是否存在，存在则删除，不存在则只前端删除

                                            var aliasesServer = $("#aliases_server").val();
                                            if(aliasesServer){
                                                var arr = aliasesServer.split(",");
                                                var f = false;
                                                // 判断是否存在，不存在则不删除
                                                $.each(arr,function (i,item) {
                                                    if (item===e){
                                                        f = true;
                                                        return false;
                                                    }
                                                });
                                            }
                                            if (f) {


                                                // 删除提醒
                                                var ajax = new $ax("/index/delAlias", function (data) {
                                                    // 成功
                                                    if (data.status === true) {
                                                        //$myNotify.success("保存成功");
                                                        $myNotify.success(data.msg)
                                                        // 刷新表格
                                                        $('#indices_table').bootstrapTable('refresh');
                                                        var arr = aliasesServer.split(",");
                                                        $.each(arr,function (i,item) {
                                                            if (item===e){
                                                                arr.remove(i);
                                                               return false;
                                                            }
                                                        });
                                                        aliasesServer.val(arr.join(","));

                                                    } else {
                                                        $myNotify.danger(data.msg);
                                                    }
                                                }, function (data) {

                                                });
                                                ajax.set("index", index);
                                                ajax.set("alias", e)
                                                ajax.set("clusterName", "");
                                                ajax.start();
                                            }
                                        }
                                    }
                            );

                        }
                    });
                },
                'click .optimize-index': function (e, value, row, index) {

                    BootstrapDialog.show({
                        type:BootstrapDialog.TYPE_PRIMARY,
                        title: '优化',
                        closeByBackdrop: false,
                        closeByKeyboard: false,
                        cssClass:'modal-optimize',
                        message: function(dialog) {
                            var form ='<div class="widget-main">'
                                        +'<form class="form-horizontal" role="form">'
                                            +'<div class="form-group">'
                                                +'<label class="col-sm-5 control-label no-padding-right" for="form-field-1"> 最大索引段数 :</label>'
                                                +'<div class="col-sm-7">'
                                                    +'<input type="text" class="input-mini" id="max_num_segments" />'
                                                +'</div>'
                                            +'</div>'

                                            +'<div class="form-group">'
                                                +'<label class="col-sm-5 control-label no-padding-right" for="form-field-1"> 只删除被标记为删除的 :</label>'
                                                +'<div class="col-sm-7">'
                                                    +'<label>'
                                                        +' <input id="only_expunge_deletes" class="ace ace-switch ace-switch-2" type="checkbox" />'
                                                        +' <span class="lbl"></span>'
                                                    +'</label>'
                                                +'</div>'
                                            +'</div>'

                                            +'<div class="form-group">'
                                                +'<label class="col-sm-5 control-label no-padding-right" for="form-field-1"> 优化后刷新 :</label>'
                                                +'<div class="col-sm-7">'
                                                    +'<label>'
                                                        +' <input id="flush" class="ace ace-switch ace-switch-2" type="checkbox" checked/>'
                                                        +' <span class="lbl"></span>'
                                                    +'</label>'
                                                +'</div>'
                                            +'</div>'

                                            +'<div class="form-group">'
                                                +'<label class="col-sm-5 control-label no-padding-right" for="form-field-1"> 等待合并 :</label>'
                                                +'<div class="col-sm-7">'
                                                    +'<label>'
                                                        +' <input id="wait_for_merge" class="ace ace-switch ace-switch-2" type="checkbox" />'
                                                        +' <span class="lbl"></span>'
                                                    +'</label>'
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
                                dialogRef.enableButtons(false);
                                dialogRef.setClosable(false);
                                var index = row.index;

                                var max_num_segments = parseInt($("#max_num_segments").val());
                                var only_expunge_deletes= $("#only_expunge_deletes").prop("checked");
                                var flush= $("#flush").prop("checked");
                                var wait_for_merge= $("#wait_for_merge").prop("checked");

                                // TODO 校验
                                var ajax = new $ax("/index/optimize", function (data) {
                                    // 成功
                                    if (data.status===true){
                                        //$myNotify.success("保存成功");
                                        $myNotify.success(data.msg)
                                        // 刷新表格
                                        $('#indices_table').bootstrapTable('refresh');
                                        // 关闭窗口
                                        dialogRef.close();
                                    }else {
                                        $myNotify.danger(data.msg);
                                        dialogRef.enableButtons(true);
                                        dialogRef.setClosable(true);
                                    }
                                },function (data) {

                                });
                                ajax.set("index",index);
                                ajax.set("maxNumSegments",max_num_segments);
                                ajax.set("onlyExpungeDeletes",only_expunge_deletes);
                                ajax.set("flush",flush);
                                ajax.set("waitForMerge",wait_for_merge);
                                ajax.set("clusterName","");
                                ajax.start();

                            }
                        }, {
                            icon:'icon-remove',
                            label: '关闭',
                            action: function(dialogRef){
                                dialogRef.close();
                            }
                        }],
                        onshown:function () {
                            $('#max_num_segments').ace_spinner({value:1,min:1,max:100,step:1, on_sides: true, icon_up:'icon-plus smaller-75', icon_down:'icon-minus smaller-75', btn_up_class:'btn-success' , btn_down_class:'btn-danger'});
                        }
                    });
                },
                'click .addType': function (e, value, row, index) {

                    BootstrapDialog.show({
                        type:BootstrapDialog.TYPE_PRIMARY,
                        title: '添加类型',
                        closeByBackdrop: false,
                        closeByKeyboard: false,
                        cssClass:'modal-add-type',
                        message: $('<div class="row-fluid"></div>').load('/index/addTypeHtml'),
                        onshown:function (dialogRef) {
                            initAddType(row,dialogRef,1);
                        }
                    });
                },
                'click .modifyType': function (e, value, row, index) {

                    BootstrapDialog.show({
                        type:BootstrapDialog.TYPE_PRIMARY,
                        title: '修改类型',
                        closeByBackdrop: false,
                        closeByKeyboard: false,
                        cssClass:'modal-add-type',
                        message: $('<div class="row-fluid"></div>').load('/index/addTypeHtml'),
                        onshown:function (dialogRef) {
                            initAddType(row,dialogRef,2);

                        }
                    });
                }
            };


            /**
             * 初始化添加类型窗口信息
             * @param row
             * @param dialogRef
             * @param type 1:添加 2：修改
             */
			function initAddType(row,dialogRef,type){

			    // 初始化数据
                if (type===2){
                    $("#type_name").val(row.type);
                    $("#include_in_all").attr("checked",row.include_in_all);
                }

                var $validation = false;
                $('#fuelux-wizard').ace_wizard().on('change' , function(e, info){
                    if(info.step == 1) {
                        //if(!$('#validation-form').valid()) return false;
                    }else if (info.step == 2){
                        var index=row.index;
                        var type_name = $("#type_name").val();
                        var include_in_all = $("#include_in_all").prop("checked");
                        var mappingsJson = JSON.stringify($("#dd_list").nestable('serialize'));
                        var ajax = new $ax("/index/addMapping", function (data) {
                            // 成功
                            if (data.status===true){

                                $myNotify.success(data.msg)
                            }else {
                                $myNotify.danger(data.msg);
                            }
                        },function (data) {

                        });

                        ajax.set("index",index);
                        ajax.set("type",type_name);
                        ajax.set("include_in_all",include_in_all);
                        ajax.set("mappingsJson",mappingsJson);
                        ajax.start();
                    }
                    console.log("change");
                }).on('finished', function(e) {
                    // 刷新
                    var table = $("#type_table_"+row.index);
                    if (table){
                        table.bootstrapTable('refresh');
                    }
                    dialogRef.close();
                }).on('stepclick', function(e){
                    //return false;//prevent clicking on steps
                    console.log("stepclick");
                });

                // 拖拽
                $('.dd').nestable();

                $('.dd-handle a').on('mousedown', function(e){
                    e.stopPropagation();
                });



                $("#add_mapping_btn").on("click",function () {
                    BootstrapDialog.show({
                        type:BootstrapDialog.TYPE_PRIMARY,
                        title: '添加属性',
                        closeByBackdrop: false,
                        closeByKeyboard: false,
                        draggable: true,
                        cssClass:'modal-add-type',
                        message: $('<div class="row-fluid"></div>').load('/index/addTypeForm'),
                        buttons: [{
                            icon: 'icon-ok',
                            label: '添加',
                            cssClass: 'btn-success',
                            //autospin: true,
                            action: function(dialogRef){
                                var type_name = $("#type_name").val();
                                var pro_name = $("#pro_name").val();
                                var analyzer = $("#analyzer").val();
                                var pro_type = $("#pro_type").val();
                                var pro_index = $("#pro_index").prop("checked");


                                var template =
                                        '<li class="dd-item dd2-item" ' +
                                        'data-type="'+pro_type+'" ' +
                                        'data-analyzer="'+analyzer+'" ' +
                                        'data-index="'+pro_index+'" ' +
                                        'data-name="'+pro_name+'">'

                                            +'<div class="dd-handle dd2-handle">'
                                                +'<i class="normal-icon icon-reorder blue bigger-130"></i>'
                                                +'<i class="drag-icon icon-move bigger-125"></i>'
                                            +'</div>'
                                            +'<div class="dd2-content">Menu</div>'
                                        +'</li>';
                                $($(".dd .dd-list")[0]).prepend(template);
                            }
                        }, {
                            icon: 'icon-ok',
                            label: '添加并继续',
                            cssClass: 'btn-success',
                            //autospin: true,
                            action: function(dialogRef){

                            }
                        },{
                            icon:'icon-remove',
                            label: '关闭',
                            action: function(dialogRef){
                                dialogRef.close();
                            }
                        }],
                        onshown:function () {
                              $(".select2").select2({
                                  allowClear:true
                              }).on('change', function(){

                              });

                            $('#ignore_above').ace_spinner({value:1,min:1,max:100,step:1, on_sides: true, icon_up:'icon-plus smaller-75', icon_down:'icon-minus smaller-75', btn_up_class:'btn-success' , btn_down_class:'btn-danger'});

                        }
                    });
                });
            }

		</script>
	</body>
</html>
