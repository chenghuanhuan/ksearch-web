<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>登录</title>
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<!-- basic styles -->

		<link href="/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="/assets/css/font-awesome.min.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->

		<!-- fonts -->



		<!-- ace styles -->

		<link rel="stylesheet" href="/assets/css/ace.min.css" />
		<link rel="stylesheet" href="/assets/css/ace-rtl.min.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="/assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="/assets/js/html5shiv.js"></script>
		<script src="/assets/js/respond.min.js"></script>
		<![endif]-->
	</head>

	<body class="login-layout">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									<i class="icon-leaf green"></i>
									<span class="red">K-Search</span>
									<span class="white">控制台</span>
								</h1>
								<h4 class="blue">&copy; 开课啦基础设施</h4>
							</div>

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="icon-coffee green"></i>
												Please Enter Your Information
											</h4>

											<div class="space-6"></div>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" id="userId" class="form-control" placeholder="Username" />
															<i class="icon-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" id="password" class="form-control" placeholder="Password" />
															<i class="icon-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
														<label class="inline">
															<input type="checkbox" class="ace" />
															<span class="lbl">记住我</span>
														</label>

														<button type="button" class="width-35 pull-right btn btn-sm btn-primary" id="login">
															<i class="icon-key"></i>
															登录
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>


										</div><!-- /widget-main -->


									</div><!-- /widget-body -->
								</div><!-- /login-box -->

								<!-- /forgot-box -->


							</div><!-- /position-relative -->
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div>
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->

		<script src="/assets/js/jquery.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
		<![endif]-->

		<!--[if !IE]> -->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='/assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script type="text/javascript">
		 window.jQuery || document.write("<script src='/assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
		</script>
		<![endif]-->

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>

		<!-- inline scripts related to this page -->
		<script src="/assets/js/bootstrap-notify/bootstrap-notify.min.js"></script>
        <script src="/assets/js/common/common.js"></script>
        <script src="/assets/js/common/ajax-object.js"></script>
		<script type="text/javascript">
			function show_box(id) {
			 jQuery('.widget-box.visible').removeClass('visible');
			 jQuery('#'+id).addClass('visible');
			}
			
			$(function () {
				$("#login").on("click",function () {
                    var ajax = new $ax("/login/do", function (data) {
                        // 成功
                        if (data.status===true){
                            window.location.href="/console";
                        }else {
                            $myNotify.danger(data.msg);
                        }
                    },function (data) {

                    });
                    var userId = $("#userId").val();
                    var password = $("#password").val();
                    ajax.set("userId",userId);
                    ajax.set("password",password);
                    ajax.start();
                });
            });
		</script>
	</body>
</html>
