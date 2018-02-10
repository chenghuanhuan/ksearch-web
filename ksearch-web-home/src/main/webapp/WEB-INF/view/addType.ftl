
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
                                <span class="title">添加属性（mapping）</span>
                            </li>

                           <#-- <li data-target="#step3">
                                <span class="step">3</span>
                                <span class="title">测试</span>
                            </li>-->

                        </ul>
                    </div>

                    <hr />
                    <div class="step-content row-fluid position-relative" id="step-container">
                        <div class="step-pane active" id="step1">
                            <h3 class="lighter block green">填写基本信息</h3>


                            <form class="form-horizontal" id="validation-form" method="get">
                                <div class="form-group">
                                    <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="type_name">名称（type）:</label>

                                    <div class="col-xs-12 col-sm-7">
                                        <div class="clearfix">
                                            <input type="text" name="type_name" id="type_name" class="col-xs-12 col-sm-12" />
                                        </div>
                                    </div>

                                </div>

                                <div class="space-2"></div>

                                <div class="form-group">
                                    <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="password">是否被加入到_all域(include_in_all):</label>

                                    <div class="col-xs-12 col-sm-7">
                                        <div class="clearfix">
                                            <input id="include_in_all" name="include_in_all" class="ace ace-switch ace-switch-2" type="checkbox" />
                                            <span class="lbl"></span>
                                            <#--<input type="password" name="password" id="password" class="col-xs-12 col-sm-4" />-->
                                        </div>
                                    </div>
                                    <div class="col-xs-12 col-sm-1" style="padding-top: 4px;">
                                        <a class="red" style="text-decoration: none" href="#" class="col-xs-12 col-sm-1" data-placement="top" data-trigger="hover" data-rel="popover" data-content="当选为YES时，所有字段被包含在_all字</br>段中，未指定字段匹配时将会被匹配
在</br>每个字段上的该设置将会覆盖此处设置。">
                                            <i class="icon-question-sign bigger-130"></i>
                                        </a>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="password">动态映射规则(dynamic):</label>

                                    <div class="col-xs-12 col-sm-7">
                                        <div class="clearfix">
                                            <select id="dynamic" name="dynamic" class="col-xs-12 col-sm-12" data-placeholder="Click to Choose...">
                                                <option value="true">true</option>
                                                <option value="false">false</option>
                                                <option value="strict">strict</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xs-12 col-sm-1" style="padding-top: 4px;">
                                        <a class="red" style="text-decoration: none" href="#" class="col-xs-12 col-sm-1" data-placement="top" data-trigger="hover" data-rel="popover" data-content="true：默认值。动态添加字段</br>false：忽略新字段</br>strict：如果碰到陌生字段，抛出异常">
                                            <i class="icon-question-sign bigger-130"></i>
                                        </a>
                                    </div>
                                </div>

                            </form>
                        </div>

                        <div class="step-pane" id="step2">
                            <div class="row-fluid">
                                <div class="dd dd-draghandle" id="dd_list">
                                    <ol class="dd-list">
                                    </ol>
                                </div>
                                <div class="center">
                                    <button type="button" class="btn btn-sm btn-success" id="add_mapping_btn">
                                        添加
                                    </button>
                                    <button type="button" class="btn btn-sm btn-primary" id="reset_field">
                                        重置
                                    </button>
                                    <button type="button" class="btn btn-sm btn-primary" id="json_view">
                                        JSON
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
                            上一步
                        </button>

                        <button class="btn btn-success btn-next" data-last="完成">
                            下一步
                            <i class="icon-arrow-right icon-on-right"></i>
                        </button>
                    </div>
                </div><!-- /widget-main -->
            </div><!-- /widget-body -->
        </div>
    </div>