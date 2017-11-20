
            <form class="form-horizontal" id="add_field_form" method="get">
                <div class="form-group">
                    <label class="control-label col-xs-12 col-sm-2 no-padding-right" for="pro_name">名称:</label>

                    <div class="col-xs-12 col-sm-3 width-30">
                        <div class="clearfix">
                            <input type="text" name="pro_name" id="pro_name" class="col-xs-12 col-sm-12" />
                        </div>
                    </div>
                    <div class="col-xs-12 col-sm-1" style="width: 2%;" >
                        <div class="clearfix">
                        </div>
                    </div>
                    <#--<label class="control-label col-xs-12 col-sm-2 no-padding-right" for="password">是否分词:</label>

                    <div class="col-xs-12 col-sm-4">
                        <div class="clearfix">
                            <input id="pro_index" name="pro_index" class="ace ace-switch ace-switch-2" type="checkbox" />
                            <span class="lbl"></span>
                            &lt;#&ndash;<input type="password" name="password" id="password" class="col-xs-12 col-sm-4" />&ndash;&gt;
                        </div>
                    </div>-->
                    <label class="control-label col-xs-12 col-sm-2 no-padding-right" for="password">是否单独存储(store):</label>

                    <div class="col-xs-12 col-sm-3 width-30">
                        <div class="clearfix">
                            <input id="store" class="ace ace-switch ace-switch-2" type="checkbox" />
                            <span class="lbl"></span>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-xs-12 col-sm-2 no-padding-right" for="type_name">能被索引的字符长度:</label>

                    <div class="col-xs-12 col-sm-3 width-30">
                        <div class="clearfix">
                            <input type="text" name="ignore_above" id="ignore_above"  class="col-xs-12 col-sm-12" />
                        </div>
                    </div>
                    <div class="col-xs-12 col-sm-1" style="width: 2%;" >
                        <div class="clearfix">
                        </div>
                    </div>


                    <label class="control-label col-xs-12 col-sm-2 no-padding-right" for="type_name">类型名称:</label>

                    <div class="col-xs-12 col-sm-3 width-30">
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
                                <option value="nested">nested</option>
                                <option value="object">object</option>
                                <option value="completion">completion</option>
                            </select>
                        </div>
                    </div>
                </div>



                <div class="form-group">
                    <label class="control-label col-xs-12 col-sm-2 no-padding-right" for="type_name">分词器:</label>

                    <div class="col-xs-12 col-sm-3 width-30">
                        <div class="clearfix">
                            <select id="analyzer" name="analyzer" class="width-100 select2"  data-placeholder="Click to Choose...">
                                <option value=""></option>
                                <option selected value="standard">standard （标准分词器）</option>
                                <option value="english">english （英文分词）</option>
                                <option value="ik_max_word">ik_max_word（中文分词，细粒度）</option>
                                <option value="ik_smart">ik_smart（中文分词，粗粒度）</option>
                                <#--<option value="chinese">chinese （中文分词）</option>-->
                                <option value="ik_en_max_word">ik_en_max_word（中英文混合分词，细粒度）</option>
                                <option value="ik_en_smart">ik_en_smart（中英文混合分词，粗粒度）</option>
                                <option value="keyword_lowercase">keyword_lowercase</option>
                                <option value="pattern">pattern</option>
                                <option value="custom_pattern_point">custom_pattern_point</option>
                                <option value="simple">simple</option>
                                <option value="whitespace">whitespace</option>
                                <option value="keyword">keyword</option>
                                <option value="fingerprint">fingerprint</option>
                                <option value="stop">stop</option>
                            </select>
                        </div>
                    </div>

                    <div class="col-xs-12 col-sm-1" style="width: 2%;" >
                        <div class="clearfix">
                        </div>
                    </div>

                    <label class="control-label col-xs-12 col-sm-2 no-padding-right" for="type_name">null_value:</label>

                    <div class="col-xs-12 col-sm-3 width-30">
                        <div class="clearfix">
                            <input type="text" name="null_value" id="null_value"  class="col-xs-12 col-sm-12" />
                        </div>
                    </div>
                    <div class="col-xs-12 col-sm-1" style="width: 2%;" >
                        <a class="blue" style="text-decoration: none" href="#" data-placement="top" data-trigger="hover" data-rel="popover" data-content="null_value 参数允许你用一个特殊的值替</br>换一个显示的 null 值， 以确保这个字段</br>能被索引和搜索。">
                            <i class="icon-question-sign bigger-130"></i>
                        </a>
                    </div>
                </div>

                <div class="form-group">

                    <label class="control-label col-xs-12 col-sm-2 no-padding-right" for="fielddata">fielddata:</label>

                    <div class="col-xs-12 col-sm-3 width-30">
                        <div class="clearfix">
                            <input id="fielddata" name="fielddata" class="ace ace-switch ace-switch-2" type="checkbox" />
                            <span class="lbl"></span>
                        </div>
                    </div>
                    <div class="col-xs-12 col-sm-1" style="width: 2%;" >
                        <a class="blue" style="text-decoration: none" href="#" data-placement="top" data-trigger="hover" data-rel="popover" data-content="如果您尝试对text字段中的数据进行</br>排序、聚合等操作时
可启用此选项，</br>影响性能，默认不启用">
                            <i class="icon-question-sign bigger-130"></i>
                        </a>
                    </div>
                    <#--<label class="control-label col-xs-12 col-sm-2 no-padding-right" for="boost">boost:</label>-->

                  <#--  <div class="col-xs-12 col-sm-4">
                        <div class="clearfix">
                            <input type="text" name="boost" id="boost"  class="col-xs-12 col-sm-12" />
                        </div>
                    </div>-->

                    <label class="control-label col-xs-12 col-sm-2 no-padding-right" for="boost">include_in_all:</label>

                    <div class="col-xs-12 col-sm-3 width-30">
                          <div class="clearfix">
                              <input id="include_in_all_pro" checked name="include_in_all" class="ace ace-switch ace-switch-2" type="checkbox" />
                              <span class="lbl"></span>
                          </div>

                    </div>
                    <div class="col-xs-12 col-sm-1" style="width: 2%;" >
                        <a class="blue" style="text-decoration: none" href="#" data-placement="top" data-trigger="hover" data-rel="popover" data-content="控制字段是否要包含在 _all 字段中，</br>默认值是 true">
                            <i class="icon-question-sign bigger-130"></i>
                        </a>
                    </div>


                    </div>
            </form>
