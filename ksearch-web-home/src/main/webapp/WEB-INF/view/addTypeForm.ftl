
            <form class="form-horizontal" id="add_field_form" method="get">
                <div class="form-group">
                    <label class="control-label col-xs-12 col-sm-2 no-padding-right" for="pro_name">名称:</label>

                    <div class="col-xs-12 col-sm-4">
                        <div class="clearfix">
                            <input type="text" name="pro_name" id="pro_name" class="col-xs-12 col-sm-12" />
                        </div>
                    </div>

                    <label class="control-label col-xs-12 col-sm-2 no-padding-right" for="password">是否分词:</label>

                    <div class="col-xs-12 col-sm-4">
                        <div class="clearfix">
                            <input id="pro_index" name="pro_index" class="ace ace-switch ace-switch-2" type="checkbox" />
                            <span class="lbl"></span>
                            <#--<input type="password" name="password" id="password" class="col-xs-12 col-sm-4" />-->
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-xs-12 col-sm-2 no-padding-right" for="type_name">能被索引的字符长度:</label>

                    <div class="col-xs-12 col-sm-4">
                        <div class="clearfix">
                            <input type="text" name="ignore_above" id="ignore_above"  class="col-xs-12 col-sm-12" />
                        </div>
                    </div>

                    <label class="control-label col-xs-12 col-sm-2 no-padding-right" for="type_name">类型名称:</label>

                    <div class="col-xs-12 col-sm-4">
                        <div class="clearfix">
                            <select id="pro_type" name="pro_type" class="width-100 select2" data-placeholder="Click to Choose...">
                                <option value="text">text</option>
                                <option value="keyword">keyword</option>
                                <option value="byte">byte</option>
                                <option value="short">short</option>
                                <option value="integer">integer</option>
                                <option value="long">long</option>
                                <option value="float">float</option>
                                <option value="double">double</option>
                                <option value="boolean">boolean</option>
                                <option value="date">date</option>
                                <option value="array">array</option>
                                <option value="object">object</option>
                            </select>
                        </div>
                    </div>
                </div>



                <div class="form-group">
                    <label class="control-label col-xs-12 col-sm-2 no-padding-right" for="type_name">分词器:</label>

                    <div class="col-xs-12 col-sm-4">
                        <div class="clearfix">
                            <select id="analyzer" name="analyzer" class="width-100 select2"  data-placeholder="Click to Choose...">
                                <option value=""></option>
                                <option value="standard">standard （标准分词器）</option>
                                <option value="english">english （英文分词）</option>
                                <option value="chinese">chinese （中文分词）</option>
                                <option value="pattern">pattern</option>
                                <option value="simple">simple</option>
                                <option value="whitespace">whitespace</option>
                                <option value="keyword">keyword</option>
                                <option value="fingerprint">fingerprint</option>
                            </select>
                        </div>
                    </div>

                    <label class="control-label col-xs-12 col-sm-2 no-padding-right" for="type_name">null_value:</label>

                    <div class="col-xs-12 col-sm-4">
                        <div class="clearfix">
                            <input type="text" name="null_value" id="null_value"  class="col-xs-12 col-sm-12" />
                        </div>
                    </div>
                </div>

                <#--<input type="hidden" name="is_children" id="is_children"/>-->
                <#--<div class="center">
                    <button type="button" class="btn btn-sm btn-success">
                        保存
                        <i class="icon-arrow-right icon-on-right bigger-110"></i>
                    </button>
                    <button type="button" class="btn btn-sm btn-success">
                        保存并继续添加
                        <i class="icon-arrow-right icon-on-right bigger-110"></i>
                    </button>
                </div>-->
            </form>
