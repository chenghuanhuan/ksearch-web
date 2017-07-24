
    <div class="span12">
        <div class="widget-box">
            <div class="widget-header widget-header-blue widget-header-flat">
                <h4 class="lighter"></h4>
            </div>

            <div class="widget-body">
                <div class="widget-main">
                    <div id="fuelux-wizard" class="row-fluid" data-target="#step-container">
                        <ul class="wizard-steps">
                            <li data-target="#step1" class="active">
                                <span class="step">1</span>
                                <span class="title">基本信息</span>
                            </li>

                            <li data-target="#step2">
                                <span class="step">2</span>
                                <span class="title">添加数据结构（mapping）</span>
                            </li>

                            <li data-target="#step3">
                                <span class="step">3</span>
                                <span class="title">测试</span>
                            </li>

                        </ul>
                    </div>

                    <hr />
                    <div class="step-content row-fluid position-relative" id="step-container">
                        <div class="step-pane active" id="step1">
                            <h3 class="lighter block green">填写基本信息</h3>


                            <form class="form-horizontal" id="validation-form" method="get">
                                <div class="form-group">
                                    <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="type_name">名称（type）:</label>

                                    <div class="col-xs-12 col-sm-9">
                                        <div class="clearfix">
                                            <input type="text" name="type_name" id="type_name" class="col-xs-12 col-sm-6" />
                                        </div>
                                    </div>
                                </div>

                                <div class="space-2"></div>

                                <div class="form-group">
                                    <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="password">是否被加入到_all域(include_in_all):</label>

                                    <div class="col-xs-12 col-sm-9">
                                        <div class="clearfix">
                                            <input id="include_in_all" name="include_in_all" class="ace ace-switch ace-switch-2" type="checkbox" />
                                            <span class="lbl"></span>
                                            <#--<input type="password" name="password" id="password" class="col-xs-12 col-sm-4" />-->
                                        </div>
                                    </div>
                                </div>

                            </form>
                        </div>

                        <div class="step-pane" id="step2">
                            <div class="row-fluid">
                                <div class="dd dd-draghandle">
                                    <ol class="dd-list">
                                        <li class="dd-item dd2-item" data-id="13">
                                            <div class="dd-handle dd2-handle">
                                                <i class="normal-icon icon-comments blue bigger-130"></i>

                                                <i class="drag-icon icon-move bigger-125"></i>
                                            </div>
                                            <div class="dd2-content">Click on an icon to start dragging</div>
                                        </li>

                                        <li class="dd-item dd2-item" data-id="14">
                                            <div class="dd-handle dd2-handle">
                                                <i class="normal-icon icon-time pink bigger-130"></i>

                                                <i class="drag-icon icon-move bigger-125"></i>
                                            </div>
                                            <div class="dd2-content">Recent Posts</div>
                                        </li>

                                        <li class="dd-item dd2-item" data-id="15">
                                            <div class="dd-handle dd2-handle">
                                                <i class="normal-icon icon-signal orange bigger-130"></i>

                                                <i class="drag-icon icon-move bigger-125"></i>
                                            </div>
                                            <div class="dd2-content">Statistics</div>

                                            <ol class="dd-list">
                                                <li class="dd-item dd2-item" data-id="16">
                                                    <div class="dd-handle dd2-handle">
                                                        <i class="normal-icon icon-user red bigger-130"></i>

                                                        <i class="drag-icon icon-move bigger-125"></i>
                                                    </div>
                                                    <div class="dd2-content">Active Users</div>
                                                </li>

                                                <li class="dd-item dd2-item dd-colored" data-id="17">
                                                    <div class="dd-handle dd2-handle btn-info">
                                                        <i class="normal-icon icon-edit bigger-130"></i>

                                                        <i class="drag-icon icon-move bigger-125"></i>
                                                    </div>
                                                    <div class="dd2-content btn-info no-hover">Published Articles</div>
                                                </li>

                                                <li class="dd-item dd2-item" data-id="18">
                                                    <div class="dd-handle dd2-handle">
                                                        <i class="normal-icon icon-eye-open green bigger-130"></i>

                                                        <i class="drag-icon icon-move bigger-125"></i>
                                                    </div>
                                                    <div class="dd2-content">Visitors</div>
                                                </li>
                                            </ol>
                                        </li>

                                        <li class="dd-item dd2-item" data-id="19">
                                            <div class="dd-handle dd2-handle">
                                                <i class="normal-icon icon-reorder blue bigger-130"></i>

                                                <i class="drag-icon icon-move bigger-125"></i>
                                            </div>
                                            <div class="dd2-content">Menu</div>
                                        </li>
                                    </ol>
                                </div>
                                <div class="center">
                                    <button type="button" class="btn btn-sm btn-success" id="add_mapping_btn">
                                        添加
                                        <i class="icon-arrow-right icon-on-right bigger-110"></i>
                                    </button>
                                </div>
                            </div>
                        </div>

                        <div class="step-pane" id="step3">
                            <div class="center">
                                <h3 class="blue lighter">This is step 3</h3>
                            </div>
                        </div>

                        <div class="step-pane" id="step4">
                            <div class="center">
                                <h3 class="green">Congrats!</h3>
                                Your product is ready to ship! Click finish to continue!
                            </div>
                        </div>
                    </div>

                    <hr />
                    <div class="row-fluid wizard-actions">
                        <button class="btn btn-prev">
                            <i class="icon-arrow-left"></i>
                            Prev
                        </button>

                        <button class="btn btn-success btn-next" data-last="Finish ">
                            Next
                            <i class="icon-arrow-right icon-on-right"></i>
                        </button>
                    </div>
                </div><!-- /widget-main -->
            </div><!-- /widget-body -->
        </div>
    </div>