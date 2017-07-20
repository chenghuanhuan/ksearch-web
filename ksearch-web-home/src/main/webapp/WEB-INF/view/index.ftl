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

		<!-- ace settings handler -->

		<script src="assets/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="assets/js/html5shiv.js"></script>
		<script src="assets/js/respond.min.js"></script>
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
                        field: 'indexName',
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
                        title: '<span class="text-primary">状态</span>'
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

                                    +'<button class="btn btn-xs btn-warning tooltip-warning" data-rel="tooltip" title="新增类型">'
                                    +'<i class="icon-flag bigger-120"></i>'
                                    +'</button>'

                                    +''
                                    +'   <button data-toggle="dropdown" class="btn btn-xs btn-default tooltip-default" data-rel="tooltip" title="更多">'
                                    +'..'
                                    +'     <span class="icon-caret-down icon-on-right"></span>'
                                    +'  </button>'

                                    +'<ul class="dropdown-menu dropdown-info pull-right">'
                                    +'  <li>'
                                    +'   <a href="#">flush</a>'
                                    +'  </li>'

                                    +'  <li>'
                                    +'   <a href="#">优化</a>'
                                    +'  </li>'
                                    +'  <li>'
                                    +'   <a href="#">新建别名</a>'
                                    +'  </li>'
                                    +'  <li>'
                                    +'   <a href="#">清空缓存</a>'
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
                                //dialogRef.enableButtons(false);
                                //dialogRef.setClosable(false);
                                //dialogRef.getModalBody().html('Dialog closes in 5 seconds.');
                                var index = $.trim($("#index").val());
                                var number_of_shards = parseInt($("#number_of_shards").val());
                                var number_of_replicas= parseInt($("#number_of_replicas").val());

                                // TODO 校验
                                var ajax = new $ax("/index/add", function (data) {
                                    // 成功
                                    if (data.status===true){
                                        new $myNotify().success("保存成功");
                                        // 刷新表格
                                        $('#indices_table').bootstrapTable('refresh');
                                        // 关闭窗口
                                        dialogRef.close();
                                    }else {
                                        new $myNotify().danger(data.msg);
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
                $detail.html('<table></table>').find('table').bootstrapTable({
                    striped:true,
                    //classes:"table table-no-bordered",
                    //showHeader:false,
                    //cardView:true,
                    //checkboxEnable:true,
                    detailView:true,
                    url:"/console/cluster/indeices",
                    columns: [{
                        field: 'indexName',
                        title: '类型'
                    }, {
                        field: 'docs',
                        title: '类型名称'
                    }, {
                        field: 'primarySize',
                        title: '操作',
                        formatter: function (value, row, index) {
                            var btn =  '<button class="btn btn-xs btn-danger tooltip-danger" data-rel="tooltip" title="删除">'
                                    +'<i class="icon-trash bigger-120"></i>'
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
                    //classes:"table table-no-bordered",
                    //showHeader:false,
                    //cardView:true,
                    //checkboxEnable:true,
                    url:"/console/cluster/indeices",
                    columns: [{
                        field: 'indexName',
                        title: '属性名称'
                    }, {
                        field: 'docs',
                        title: '类型'
                    }, {
                        field: 'primarySize',
                        title: '格式'
                    }, {
                        field: 'primarySize',
                        title: 'store'
                    }, {
                        field: 'primarySize',
                        title: '操作',
                        formatter: function (value, row, index) {
                            var btn =  '<button class="btn btn-xs btn-danger tooltip-danger" data-rel="tooltip" title="删除">'
                                    +'<i class="icon-trash bigger-120"></i>'
                                    +'</button>';
                            return btn;
                        }
                    }]
                });
            }

            window.operateEvents = {
                'click .del': function (e, value, row, index) {

                    BootstrapDialog.confirm({
                        title: '提示',
                        message: '确认要删除索引吗？删除后数据无法恢复，请确认后再操作！！！',
                        type: BootstrapDialog.TYPE_DANGER, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
                        btnCancelLabel: '取消', // <-- Default value is 'Cancel',
                        btnOKLabel: '确定', // <-- Default value is 'OK',
                        btnOKClass: 'btn-danger', // <-- If you didn't specify it, dialog type will be used,
                        callback: function(result) {
                            // result will be true if button was click, while it will be false if users close the dialog directly.
                            if(result) {
                                var ajax = new $ax("/index/del", function (data) {
                                    // 成功
                                    if (data.status===true){
                                        new $myNotify().success("删除成功");
                                        // 刷新表格
                                        $('#indices_table').bootstrapTable('refresh');
                                    }else {
                                        new $myNotify().danger(data.msg);
                                    }
                                },function (data) {

                                });
                                ajax.set("indices",row.indexName);
                                ajax.set("clusterName","");
                                ajax.start();
                            }
                        }
                    });
                },
                'click .refresh': function (e, value, row, index) {

                    BootstrapDialog.confirm({
                        title: '提示',
                        message: '确认刷新？',
                        type: BootstrapDialog.TYPE_WARNING, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
                        btnCancelLabel: '取消', // <-- Default value is 'Cancel',
                        btnOKLabel: '确定', // <-- Default value is 'OK',
                        btnOKClass: 'btn-warning', // <-- If you didn't specify it, dialog type will be used,
                        callback: function(result) {
                            // result will be true if button was click, while it will be false if users close the dialog directly.
                            if(result) {
                                var ajax = new $ax("/index/refresh", function (data) {
                                    // 成功
                                    if (data.status===true){
                                        new $myNotify().success("刷新成功");
                                        // 刷新表格
                                        $('#indices_table').bootstrapTable('refresh');
                                    }else {
                                        new $myNotify().danger(data.msg);
                                    }
                                },function (data) {

                                });
                                ajax.set("indices",row.indexName);
                                ajax.set("clusterName","");
                                ajax.start();
                            }
                        }
                    });
                }
            };

		</script>
	</body>
</html>
