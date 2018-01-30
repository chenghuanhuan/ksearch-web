<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>控制台</title>
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<!-- basic styles -->
		<#include "/common/head_css.ftl"/>

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
							<li class="active">控制台</li>
						</ul><!-- .breadcrumb -->

						<!-- #nav-search -->
					</div>

					<div class="page-content">
					<#--	<div class="page-header">
							<h1>
								控制台
								<small>
									<i class="icon-double-angle-right"></i>
									 查看
								</small>
							</h1>
						</div>-->
						<!-- /.page-header -->

						<div class="row">

							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->

								<div class="row" id="cluster_info">

								</div>
                                <div class="hr hr32 hr-dotted"></div>

								<div class="row">
									<div class="space-6"></div>

									<div class="col-sm-12 infobox-container">
										<div class="infobox infobox-green  ">
											<div class="infobox-icon">
												<i class="icon-comments"></i>
											</div>

											<div class="infobox-data">
												<span class="infobox-data-number" id="nodeSize"></span>
												<div class="infobox-content">节点</div>
											</div>
											<#--<div class="stat stat-success">8%</div>-->
										</div>

										<div class="infobox infobox-blue  ">
											<div class="infobox-icon">
												<i class="icon-twitter"></i>
											</div>

											<div class="infobox-data">
												<span class="infobox-data-number" id="totalShards"></span>
												<div class="infobox-content">总分片数</div>
											</div>
										</div>

                                        <div class="infobox infobox-blue2  ">
                                            <div class="infobox-progress">
                                                <div class="easy-pie-chart percentage" id="successfulShardsPercent" data-percent="0" data-size="46">

                                                </div>
                                            </div>

                                            <div class="infobox-data">
                                                <span class="infobox-text" id="successfulShards"></span>

                                                <div class="infobox-content">
                                                    <#--<span class="bigger-110">~</span>-->
                                                    可用分片数
                                                </div>
                                            </div>
                                        </div>

										<div class="infobox infobox-pink  ">
											<div class="infobox-icon">
												<i class="icon-shopping-cart"></i>
											</div>

											<div class="infobox-data">
												<span class="infobox-data-number" id="indices"></span>
												<div class="infobox-content">索引个数</div>
											</div>
										</div>

										<div class="infobox infobox-red  ">
											<div class="infobox-icon">
												<i class="icon-camera-retro icon-large"></i>
											</div>

											<div class="infobox-data">
												<span class="infobox-data-number" id="documents"></span>
												<div class="infobox-content">文档个数</div>
											</div>
										</div>

										<div class="infobox infobox-orange2  ">
											<div class="infobox-chart">
												<span class="sparkline" data-values="196,128,202,177,154,94,100,170,224"></span>
											</div>

											<div class="infobox-data">
												<span class="infobox-data-number" id="size"></span>
												<div class="infobox-content">占用空间</div>
											</div>
										</div>



										<#--<div class="space-6"></div>-->


									</div>

									<div class="vspace-sm"></div>

									<!-- /span -->
								</div><!-- /row -->

								<div class="hr hr32 hr-dotted"></div>

								<div class="row">
									<div class="col-sm-4">
										<div class="widget-box transparent">
											<div class="widget-header widget-header-flat">
												<h4 class="lighter">
													<i class="icon-star orange"></i>
													健康状况
												</h4>

												<div class="widget-toolbar">
													<a href="#" data-action="collapse">
														<i class="icon-chevron-up"></i>
													</a>
												</div>
											</div>

											<div class="widget-body">
												<div class="widget-main no-padding">
                                                    <table id="cluster_health_table"></table>

												</div><!-- /widget-main -->
											</div><!-- /widget-body -->
										</div><!-- /widget-box -->
									</div>


                                    <div class="col-sm-8">
                                        <div class="widget-box transparent">
                                            <div class="widget-header widget-header-flat">
                                                <h4 class="lighter">
                                                    <i class="icon-star orange"></i>
                                                    索引
                                                </h4>

                                                <div class="widget-toolbar">
                                                    <a href="#" data-action="collapse">
                                                        <i class="icon-chevron-up"></i>
                                                    </a>
                                                </div>
                                            </div>

                                            <div class="widget-body">
                                                <div class="widget-main no-padding">
                                                    <table id="indices_table"></table>
                                                </div><!-- /widget-main -->
                                            </div><!-- /widget-body -->
                                        </div><!-- /widget-box -->
                                    </div>
								</div>

								<div class="hr hr32 hr-dotted"></div>



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

			var globalClusterName = Util.cookie.get("cluster-name");
			jQuery(function($) {

				// 获取所有的集群名称
                var ajax = new $ax("/console/clusters", function (data) {
                    if (data.status){
                        var clusterNames = data.data.split(",");
                        $.each(clusterNames,function (i,item) {
                            if (!globalClusterName&&i===0){
                                globalClusterName = item;
                                Util.cookie.set("cluster-name",globalClusterName,604800000);
							}
                            initClusterNodeInfo(i,item);
                        });

                        // 初始化健康信息
                        initClusterHealth(globalClusterName);
                        initClusterStatistics(globalClusterName);
					}

				});
                ajax.start();

				$(document).on("click",".cluster-name",function () {
                        //$(this).children("i").remove();
					$("#cluster_info").find(".icon-ok").remove();
					$(this).prepend('<i class="icon-ok"></i>');
					var clusterName = $(this).text();
					globalClusterName = clusterName;
					Util.cookie.set("cluster-name",clusterName,604800000);
                    // 初始化健康信息
                    //initClusterHealth($(this).text());
                    $('#cluster_health_table').bootstrapTable('refresh');
                    $('#indices_table').bootstrapTable('refresh');
                    initClusterStatistics(clusterName);
                    $("#nodeSize").html($(this).parent().children("button").length-1);
                });



				$('.sparkline').each(function(){
					var $box = $(this).closest('.infobox');
					var barColor = !$box.hasClass('infobox-dark') ? $box.css('color') : '#FFF';
					$(this).sparkline('html', {tagValuesAttribute:'data-values', type: 'bar', barColor: barColor , chartRangeMin:$(this).data('min') || 0} );
				});






				var d1 = [];
				for (var i = 0; i < Math.PI * 2; i += 0.5) {
					d1.push([i, Math.sin(i)]);
				}

				var d2 = [];
				for (var i = 0; i < Math.PI * 2; i += 0.5) {
					d2.push([i, Math.cos(i)]);
				}

				var d3 = [];
				for (var i = 0; i < Math.PI * 2; i += 0.2) {
					d3.push([i, Math.tan(i)]);
				}



				$('#recent-box [data-rel="tooltip"]').tooltip({placement: tooltip_placement});
				function tooltip_placement(context, source) {
					var $source = $(source);
					var $parent = $source.closest('.tab-content')
					var off1 = $parent.offset();
					var w1 = $parent.width();

					var off2 = $source.offset();
					var w2 = $source.width();

					if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
					return 'left';
				}


				$('.dialogs,.comments').slimScroll({
					height: '300px'
			    });


				//Android's default browser somehow is confused when tapping on label which will lead to dragging the task
				//so disable dragging when clicking on label
				var agent = navigator.userAgent.toLowerCase();
				if("ontouchstart" in document && /applewebkit/.test(agent) && /android/.test(agent))
				  $('#tasks').on('touchstart', function(e){
					var li = $(e.target).closest('#tasks li');
					if(li.length == 0)return;
					var label = li.find('label.inline').get(0);
					if(label == e.target || $.contains(label, e.target)) e.stopImmediatePropagation() ;
				});

				$('#tasks').sortable({
					opacity:0.8,
					revert:true,
					forceHelperSize:true,
					placeholder: 'draggable-placeholder',
					forcePlaceholderSize:true,
					tolerance:'pointer',
					stop: function( event, ui ) {//just for Chrome!!!! so that dropdowns on items don't appear below other items after being moved
						$(ui.item).css('z-index', 'auto');
					}
					}
				);
				$('#tasks').disableSelection();
				$('#tasks input:checkbox').removeAttr('checked').on('click', function(){
					if(this.checked) $(this).closest('li').addClass('selected');
					else $(this).closest('li').removeClass('selected');
				});


			})

            /**
             * 健康状况
             */
			function initClusterHealth(clusterName) {

                $('#cluster_health_table').bootstrapTable({
                    striped:true,
                    classes:"table table-no-bordered",
                    showHeader:false,
                    //cardView:true,
                    url:"/console/cluster/health",
                    queryParams:function (params) {
                        if (globalClusterName){
                            params.clusterName = globalClusterName;
                        }else {
                            params.clusterName = clusterName;
                        }
                        return params;
                    },
                    columns: [{
                        field: 'name',
                        title: '参数'
                    }, {
                        field: 'value',
                        title: '值'
                    }]
                });

                $('#indices_table').bootstrapTable({
                    striped:true,
                    classes:"table table-no-bordered",
                    //showHeader:false,
                    //cardView:true,
                    url:"/console/cluster/indeices",
                    pagination:true,
                    pageSize:12,
                    pageList:[12,20,40],
                    queryParams:function (params) {
                        if (globalClusterName){
                            params.clusterName = globalClusterName;
						}else {
                            params.clusterName = clusterName;
						}

                        return params;
                    },
                    columns: [{
                        field: 'index',
                        title: '索引'
                    }, {
                        field: 'docs',
                        title: '文档'
                    }, {
                        field: 'primarySize',
                        title: '主节点大小'
                    }, {
                        field: 'shards',
                        title: '主分片个数'
                    }, {
                        field: 'replicas',
                        title: '副分片个数'
                    }, {
                        field: 'status',
                        title: '状态'
                    }]
                });
            }

            /**
			 * 数据统计
             */
            function initClusterStatistics(clusterName) {
                var ajax = new $ax("/console/cluster/statistics", function (data) {
                    if (data.status===true) {
                        var value = data.data;
                        for (var v in value) {
                            $("#" + v).html(value[v]);
                        }
						var percent = Math.round(value.successfulShards/value.totalShards*100);
						if (isNaN(percent)){
						    percent=0;
						}
                        var html = '<span class="percent">'+percent+'</span>%';
                        var successfulShardsPercent = $("#successfulShardsPercent").attr("data-percent",percent);
                        successfulShardsPercent.html(html);
                        // 百分比图
                        $('.easy-pie-chart.percentage').each(function () {
                            var $box = $(this).closest('.infobox');
                            var barColor = $(this).data('color') || (!$box.hasClass('infobox-dark') ? $box.css('color') : 'rgba(255,255,255,0.95)');
                            var trackColor = barColor == 'rgba(255,255,255,0.95)' ? 'rgba(255,255,255,0.25)' : '#E2E2E2';
                            var size = parseInt($(this).data('size')) || 50;
                            $(this).easyPieChart({
                                barColor: barColor,
                                trackColor: trackColor,
                                scaleColor: false,
                                lineCap: 'butt',
                                lineWidth: parseInt(size / 10),
                                animate: /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase()) ? false : 1000,
                                size: size
                            });
                        });

                    }
                }, function (data) {

                });
				ajax.set("clusterName",clusterName);
                ajax.start();
            }



            /**
			 * 初始化集群节点信息
			 */
            function initClusterNodeInfo(index,clusterName) {
                /*
                 * 集群信息
                 */
                var ajax = new $ax({url:"/console/cluster/state/nodes",async:false}, function (data) {
                    if (data.status===true) {
                        var datalist = data.data;
                        if (datalist.length>0){
                            var html =' <div class="col-xs-12"><p>';
                            $.each(datalist,function (i,t) {
                                var cls = "success";
                                if (t.status=="yellow"){
                                    cls = "warning";
                                }else if (t.status=="red"){
                                    cls = "danger";
                                }
                                var content ='<span style=\'width: 45px;\' class=\'label label-sm label-success arrowed\'>green</span>'+ ':最健康的状态，说明所有的分片包括备份都可用</br>'+
                                        '<span style=\'width: 45px;\' class=\'label label-sm label-yellow arrowed\'>yellow</span>:基本的分片可用，但是备份不可用（或者是没有备份）</br>'+
                                        '<span style=\'width: 45px;\'  class=\'label label-sm label-danger arrowed\'>red</span>:部分的分片可用，表明分片有一部分损坏。此时执行查询部分数据仍然可以查到，遇到这种情况，还是赶快解决比较好</br>';
                                html +='集群：'+'<button id="cluster_name_'+clusterName+'" data-trigger="hover" data-rel="popover" class="cluster-name btn btn-'+cls+' tooltip-"'+cls+' data-content="'+content+'">';
										if(globalClusterName===clusterName){
										    html+='<i class="icon-ok"></i>';
										}
										html += t.name+'</button>&nbsp;';
                                html +='&nbsp;节点：';
                                var nodes = t.nodes;

                                $.each(nodes,function (j,d) {
                                    html +='<button data-rel="tooltip" title="'+d.transport_address+'" class="btn btn-xs btn-primary tooltip-success">'+d.name+'</button>&nbsp;';
                                });
                            });
                            html+='</p></div>';
                            $("#cluster_info").append(html);


                            $("#nodeSize").html(datalist[0].nodes.length);

                            // 初始化tooltip
                            $('[data-rel=tooltip]').tooltip();
                            $('[data-rel=popover]').popover({html:true});
                        }
                    }
                }, function (data) {

                });

                ajax.set("clusterName",clusterName);
                ajax.start();

            }
		</script>
		<div style="text-align:center;">
</div>
	</body>
</html>
