<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>ElasticSearch 1.7.2 Query DSL Builder</title>

    <!-- Bootstrap core CSS -->
    <link href="/assets/js/dsl/bootstrap-social.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->

    <!-- Just for debugging purposes. Don't actually copy this line! -->
    <!--[if lt IE 9]>
    <script src="/assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <script type="text/javascript" src="/assets/js/dsl/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="/assets/js/dsl/bootstrap.min.js"></script>
    <script type="text/javascript" src="/assets/js/dsl/handlebars-v1.3.0.js"></script>
    <!--<script type="text/javascript" src="js/json2.js"></script>-->
    <!--<script type="text/javascript" src="js/jquery.storageapi.js"></script>-->
    <script type="text/javascript" src="/assets/js/dsl/qb-param-render-1.2.1.js"></script>
    <style>
        body {
        }

        .fields1 {
            padding-left: 30px;
        }

        .fields2 {
            padding-left: 60px;
        }

        .fields3 {
            padding-left: 90px;
        }

        .fields4 {
            padding-left: 120px;
        }

        .queryadd {
            padding-bottom: 10px;
        }

        .qfli {
            padding-top: 10px;
            border-bottom-width: 2px;
            border-bottom-style: dotted;
            border-bottom-color: #666;
        }
    </style>
</head>

<body>

<div class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">ElasticSearch 1.7.2 QueryBuilder</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="http://www.supermind.org/blog/1181/html5-elasticsearch-query-dsl-builder">About</a></li>
                <!--<li><a href="#about">About</a></li>-->
                <!--<li><a href="#contact">Contact</a></li>-->

            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <div class="ssb">
                        <!-- Twitter -->
                        <a href="http://twitter.com/home?status=http://www.supermind.org/elasticsearch/query-dsl-builder.html" title="Share on Twitter" target="_blank" class="btn btn-twitter"><i class="fa fa-twitter"></i> Twitter</a>
                        <!-- Facebook -->
                        <a href="https://www.facebook.com/sharer/sharer.php?u=http://www.supermind.org/elasticsearch/query-dsl-builder.html" title="Share on Facebook" target="_blank" class="btn btn-facebook"><i class="fa fa-facebook"></i> Facebook</a>
                        <!-- Google+ -->
                        <a href="https://plus.google.com/share?url=http://www.supermind.org/elasticsearch/query-dsl-builder.html" title="Share on Google+" target="_blank" class="btn btn-googleplus"><i class="fa fa-google-plus"></i> Google+</a>
                        <!-- StumbleUpon -->
                        <a href="http://www.stumbleupon.com/submit?url=http://www.supermind.org/elasticsearch/query-dsl-builder.html" title="Share on StumbleUpon" target="_blank" data-placement="top" class="btn btn-stumbleupon"><i class="fa fa-stumbleupon"></i> Stumbleupon</a>
                        <!-- LinkedIn -->
                        <a href="http://www.linkedin.com/shareArticle?mini=true&url=&title=&summary=http://www.supermind.org/elasticsearch/query-dsl-builder.html" title="Share on LinkedIn" target="_blank" class="btn btn-linkedin"><i class="fa fa-linkedin"></i> LinkedIn</a>
                    </div>
                </li>
            </ul>
        </div>
        <!--/.nav-collapse -->
    </div>
</div>

<div class="container-fluid">
    <div class="row">
        <form class="qbform form" role="form" id="qbform">
            <div class="col-sm-8">
                <h2>Query</h2>
                <select class="qb form-control rootquery" tgid="aaaaa" type="query">
                    <option value="">Select query type</option>
                </select>
                <select class="fb form-control" tgid="aaaaa" type="filter" style="display:none">
                    <option value="">Select filter type</option>
                </select>
                <div class="qbfields" id="qbfields_aaaaa"></div>
            </div>
        </form>
        <div class="col-sm-4">
            <h2>Query DSL JSON</h2>
            <textarea id="result" class="form-control" rows="20"></textarea> <br/>
            <label class="control-label">ES host</label>
            <input type="text" class="form-control" name="curl-host" id="curl-host" value="localhost:9200">
            <label class="control-label">Index name</label>
            <input type="text" class="form-control" name="curl-index" id="curl-index" value="twitter">
            <label class="control-label">Type</label>
            <input type="text" class="form-control" name="curl-type" id="curl-type" value="tweet">
            <h2>curl</h2>
            <textarea id="curl-result" class="form-control" rows="20"></textarea> <br/>
        </div>
    </div>
</div>

<footer class="footer"><hr/><center>
    <p>© Copyright 2015 <a href="http://www.supermind.org/">Kelvin Tan</a></p></center></footer>

<script type="text/javascript">
    /**
     * The querybuilder takes a json data model (qb-model-VERSION.json) and dynamically creates
     * a HTML form. When form elements change, a JSON representation of the form is built
     * which is then displayed on-screen.
     *
     * In the JSON model, queries (and filters) have fields. Fields have a name and a type.
     * Examples of types are STRING, INTEGER, LIST etc.
     *
     * Handlebars is used to build the HTML from the json model.
     *
     * qb-param-render-xxx.js is the Handlebars helper which renders field types to HTML.
     *
     */

    String.prototype.endsWith = function (suffix) {
        return this.indexOf(suffix, this.length - suffix.length) !== -1;
    };

    /**
     * Data model for queries.
     */
    var querybuilders;

    /**
     * Data model for filters.
     */
    var filterbuilders;

    /**
     * Load the data model.
     */
    $.getJSON("/assets/js/dsl/qb-model-1.7.2.json", function (data) {
        loadDataModel(data);
    });


    function loadDataModel(data) {
        querybuilders = data['query'];
        filterbuilders = data['filter'];
        for (var k in querybuilders) {
            $('.qb').append($('<option/>', {
                value: k,
                text: k,
                id: k
            }));
        }

        for (var k in filterbuilders) {
            $('.fb').append($('<option/>', {
                value: k,
                text: k,
                id: k
            }));
        }

        /*
            if ($.localStorage.isSet('querybuilder')) {
                $('#qbform').html($.localStorage.get('querybuilder'));
            }
        */

        qbOnChange();
    }


    var qbOnChangeActual = function () {
        var el = $(this);
        var dslType = el.val();
        var filterOrQuery = el.attr('type');
        var data = filterOrQuery == 'query' ? querybuilders[dslType] : filterbuilders[dslType];
        var docurl = getESDocURL(dslType, filterOrQuery);
        data['docurl'] = docurl;
        data['srcurl'] = getESSourceURL(dslType, filterOrQuery);
        data['queryType'] = dslType;

        // Determine the path prefix
        var prefix = dslType;
        if (typeof el.attr('_name') != 'undefined') {
            prefix = el.attr('_name') + "-" + prefix;
        }

        renderQueryBuilderParams(prefix, data, el.attr('tgid'), '#qbfields_' + el.attr('tgid'), filterOrQuery);
        updateRightPanel();

        var tgid = el.attr('tgid');

        // Ajax load documentation URL
        $.get(docurl, function (data) {
            var $data = $(data);
            $('.toc,.edit_me', $data).remove();
            // fix images
            $('img', $data).each(function () {
                var src = $(this).attr('src');
                $(this).attr('src', 'https://www.elastic.co/guide/en/elasticsearch/reference/current/' + src);
            });
            // fix links
            $('a', $data).each(function () {
                $(this).attr('target', '_blank');
                var href = $(this).attr('href');
                $(this).attr('href', 'https://www.elastic.co/guide/en/elasticsearch/reference/current/' + href);
            });
            var content = $("div.section", $data);
            $('#fields_' + tgid + '_doc').html(content.html());
        });
    };


    /**
     * The event handlers to bind when the form changes (new elements are getting added).
     */
    function qbOnChange() {
        $('.qb,.fb').change(qbOnChangeActual);

        var f = function () {
            updateRightPanel();
        };
        /**
         * Update JSON when user types in the forms
         */
        $('.qbform input, .qbform .ref').unbind('keyup').keyup(f).unbind('change').change(f);

        /**
         * event handler for the "Add Query" button for querybuilder lists
         */
        $('.qbform .queryadd').unbind('click').click(function () {
            var el = $(this);
            var fullname = el.attr('target');
            var filterOrQuery = el.attr('targettype');
            var sel = filterOrQuery == 'query' ? '.qb' : '.fb';
            var counter = parseInt(el.attr('counter'));
            el.attr('counter', counter+1);
            var indexedname = fullname + "[" + counter + "]";

            var randid = (rand_id());
            s = "<li class='qfli'><label class='control-label'>" + capitalize(filterOrQuery) + " Type</label><select _name='" + indexedname + "' class='qb form-control' tgid=" + randid + " type=" + filterOrQuery + ">" + $(sel).html() + "</select>";
            s += '<div class="qbfields" id="qbfields_' + randid + '"></div><br/><input type="button" class="btn btn-danger center-block querydel" value="Delete"><br/></li>';
            el.siblings('ol').append(s);
            qbOnChange();
        });

        /**
         * event handler for the "Delete Query" button for querybuilder lists
         */
        $('.qbform .querydel').unbind('click').click(function () {
            $(this).parents('.qfli').remove();
            updateRightPanel();
        });

        $('.qbform .namedArray').unbind('keyup').keyup(function(){
            $(this).siblings('.namedArray_values').first().keyup();
        });

        $('.qbform .namedArray_values').unbind('keyup').keyup(function(){
            var el = $(this);
            var elements = el.val().split(",");
            // clear out the container
            var namedObject = el.siblings('.namedArray').first().val();
            var prefix = el.attr('prefix');
            el.next().html('');
            var html = '';
            for(var i=0;i<elements.length;++i) {
                html += "<input type='hidden' name='"+prefix+"-"+namedObject+"[]' value='"+elements[i]+"'>";
            }
            el.next().html(html);
            updateRightPanel();
        });

        $('.qbform .list_values').unbind('keyup').keyup(function(){
            var elements = $(this).val().split(/,|\|/);
            // clear out the container
            var prefix = $(this).attr('prefix');
            $(this).next().html('');
            var html = '';
            for(var i=0;i<elements.length;++i) {
                if(!elements[i]) continue;
                html += "<input type='hidden' name='"+prefix+"[]' value='"+elements[i]+"'>";
            }
            $(this).next().html(html);
            updateRightPanel();
        });

        $('.qbform .map_values').unbind('keyup').keyup(function(){
            var el = $(this);
            var elements = el.val().split(/,|\|/);
            // clear out the container
            var prefix = el.attr('prefix');
            el.next().html('');
            var html = '';
            for(var i=0;i<elements.length;++i) {
                if(!elements[i]) continue;
                var key = elements[i];
                if (elements.length < i+1) continue;
                var val = elements[++i];
                html += "<input type='hidden' name='"+prefix+"-"+key+"' value='"+val+"'>";
            }
            el.next().html(html);
            updateRightPanel();
        });

        /**
         * Documentation and src toggle buttons
         */
        $('.doctog').unbind('click').click(function () {
            $('#' + $(this).attr('target')).toggle();
            $('#' + $(this).attr('target') + "_url").toggle();
        });
        $('.srctog').unbind('click').click(function () {
            var tg = $('#' + $(this).attr('target'));
            tg.toggle();
            if(tg.is(':visible')) {
                $.get($(this).attr('link'), function(data){
                    tg.html(data);
                });
            }
            $('#' + $(this).attr('target') + "_url").toggle();
        });


        /**
         * Adds a css class for nested queries for indentation purposes
         */
        $('.qbfields').each(function () {
            $(this).addClass('fields' + $(this).parents('.qbfields').length);
        });
    }

    function coerceType(type, val) {
        if(type == 'FLOAT') return parseFloat(val);
        if(type == 'INTEGER') return parseInt(val);
        if(type == 'BOOLEAN') return (val === 'false');
        return val;
    }

    /**
     * Function used in regex replace to sub '-' with '~' within namedobjects (which are delimited by @)
     */
    function replaceNamedObjectDelimiters(match, offset, string) {
        return match.replace(/-/g, '~');
    }

    function removeEmptyObjectsAndCompactArrays(obj){
        for (var k in obj){
            if (obj[k] instanceof Array) {
                obj[k] = obj[k].filter(function(val){return val});
            } else if (typeof obj[k]=="object") removeEmptyObjectsAndCompactArrays(obj[k]);
        }
    }

    function serializeFormToJson() {
        var obj = {};
        $('form').find(':input').each(function(){
            var o = $(this);
            var name = o.attr('name');
            var val = o.val();
            if(!name || !val) return;

            // replace periods within @ with a -
            name = name.replace(/@[^@]+@/g, replaceNamedObjectDelimiters);
            var arr = name.split("-");
            var current = obj;
            for(var i=0;i<arr.length-1;++i) {
                var prop = arr[i];

                //=========================================================
                // Handle querybuilder lists
                //=========================================================
                var match = prop.match(/([a-zA-Z_]+)\[([0-9])+\]/);
                if(match) {
                    prop = match[1];
                    var idx = parseInt(match[2]);

                    if(!current.hasOwnProperty(prop)) {
                        current[prop] = [];
                    }

                    if(current[prop][idx] == undefined) {
                        current[prop][idx] = {};
                    }
                    current = current[prop][idx];
                    continue;
                }

                //=========================================================
                // Handle namedObject references
                //=========================================================
                if(prop.charAt(0) == '@') {
                    var s = '#' + prop.replace(/@/g, '').replace(/~/g, '-') + "_namedObject";
                    prop = $(s).val();
                    if (!prop) prop = '_placeholder';
                }
                if(!current.hasOwnProperty(prop)) {
                    current[prop] = {};
                }
                current = current[prop];
            }
            var finalprop = arr[arr.length - 1];
            if(finalprop.charAt(0) == '_' && finalprop !== '_name') return;

            val = coerceType(o.attr('ttype'), val);

            //=========================================================
            // Handle namedArrays
            //=========================================================
            if(finalprop.indexOf('[]') > -1) { // we have an array
                finalprop = finalprop.replace(/\[\]/g,'');
                if(!current.hasOwnProperty(finalprop)) current[finalprop] = [];
                current[finalprop].push(val);
            } else {
                current[finalprop] = val;
            }
        });

        removeEmptyObjectsAndCompactArrays(obj);

        var str = JSON.stringify(obj, null, '  ');
        str += "\n";

        // DEBUG KEYS
        /*
            $('form').find(':input').each(function(){
                var o = $(this);
                var name = o.attr('name');
                var val = o.val();
                if(!name || !val) return;
                str += name + ":" + o.val() + "\n";
            });
        */
        return str;
    }

    /**
     * Convert the form into json, and populate the relevant fields
     */
    function updateRightPanel() {
        // convert form
        var json = serializeFormToJson();
        $('#result').text(json);

        // update curl panel
        var host = $('#curl-host').val();
        var index = $('#curl-index').val();
        var type = $('#curl-type').val();
        var curl = "curl -XGET 'http://" + host + "/" + index + "/" + type + "/_search' -d ";
        curl += "'\n{\n  \"query\": " + json + "}\n'";
        $('#curl-result').text(curl);

//    $.localStorage.set('querybuilder', $('#qbform').html());
    }


    /**
     * Render query builder parameters into HTML via Handlebars.
     * @param prefix
     * @param qb
     * @param id
     * @param targetSelector
     * @param filterOrQuery
     */
    function renderQueryBuilderParams(prefix, qb, id, targetSelector, filterOrQuery) {
        var source = $("#fields-template").html();
        var template = Handlebars.compile(source);
        qb['prefix'] = prefix;
        qb['id'] = id;
        qb['filterOrQuery'] = filterOrQuery;
        var html = template(qb);
        $(targetSelector).html(html);
        qbOnChange();
    }

    /**
     * Start of misc functions.
     */
    function getESDocURL(queryType, filterOrQuery) {
        queryType = queryType.replace(/_/g, '-');
        if (queryType == 'common') queryType = 'common-terms';
        else if (queryType == 'geo-bbox') queryType = 'geo-bounding-box';
        return "https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-" + queryType + "-" + filterOrQuery + ".html";
    }

    function getESSourceURL(queryType, filterOrQuery) {
        queryType = capitalize(toCamelCase(queryType));
        filterOrQuery = capitalize(filterOrQuery);
        if (queryType == 'common') queryType = 'CommonTerms';
        else if (queryType == 'geo-bbox') queryType = 'GeoBoundingBox';
        return "https://cdn.rawgit.com/elastic/elasticsearch/v1.7.2/src/main/java/org/elasticsearch/index/query/" + queryType + filterOrQuery + "Builder.java";
    }

    function toCamelCase( string ){
        return string.toLowerCase().replace(/(_|-)([a-z])/g, toUpperCase );
    }

    function toUpperCase( string ){
        return string[1].toUpperCase();
    }

    function rand_id() {
        var text = "";
        var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (var i = 0; i < 5; i++)
            text += possible.charAt(Math.floor(Math.random() * possible.length));

        return text;
    }

    var capitalize = function (string) {
        if (typeof string == 'undefined') return '';
        return string.charAt(0).toUpperCase() + string.slice(1);
    };
    Handlebars.registerHelper('capitalize', capitalize);


</script>

<script id="fields-template" type="text/x-handlebars-template">
    <div class="fields_{{id}}">
        {{#if namedObject}}
        <h4>Name of field to query</h4>
        <input type="text" class="form-control namedObject" name="{{prefix}}-_namedObject" id="{{prefix}}_namedObject">
        {{#if namedObjectValue}}
        <h4>Field value</h4>
        <input type="text" class="form-control namedObjectValue" name="{{prefix}}-@{{prefix}}@-value" prefix="{{prefix}}" id="{{prefix}}_namedObjectValue">
        {{/if}}
        {{/if}}
        {{#if namedArray}}
        <h4>Name of field to query</h4>
        <input type="text" class="form-control namedArray" name="{{prefix}}-_namedArray" id="{{id}}_namedArray">
        <h4>Comma- or pipe-separated values {{#if namedArrayValues}}[{{namedArrayValues}}]{{/if}}</h4>
        <input type="text" class="form-control namedArray_values" name="{{prefix}}-_namedArrayValues" prefix="{{prefix}}" id="{{id}}_namedArray_values" order="{{namedArrayValues}}" >
        <div class="namedArray_valuesContainer"></div>
        {{/if}}

        <h4>{{capitalize filterOrQuery}} Parameters</h4>
        <!-- DO NOT REMOVE. This __MARKER is used as a workaround for form2js' serialization. -->
        <!--<input type="hidden" name="{{prefix}}.__MARKER" value="__MARKER">-->
        {{#each fields}}
        <div class="form-group">
            {{#if label}}
            <label for="{{name}}" class="control-label">{{label}} </label>
            {{else}}
            <label for="{{name}}" class="control-label">{{name}} </label>
            {{/if}}
            <small>({{type}})</small>
            {{{render_param ../prefix name type ../id ../namedObject}}}
        </div>
        {{/each}}
        <input type="button" id="{{queryType}}_doctog" class="btn btn-primary doctog" value="Toggle {{queryType}} {{filterOrQuery}} documentation" target="fields_{{id}}_doc">
        <input type="button" id="{{queryType}}_srctog" class="btn btn-primary srctog" value="Toggle {{queryType}} {{filterOrQuery}} builder source" target="fields_{{id}}_src" link="{{srcurl}}">
        <div id="fields_{{id}}_doc" style="display:none"></div>
        <div id="fields_{{id}}_doc_url" style="display:none"><a href="{{docurl}}">{{docurl}}</a><br/></div>
        <div id="fields_{{id}}_src_url" style="display:none"><a href="{{srcurl}}">{{srcurl}}</a><br/></div>
        <pre id="fields_{{id}}_src" style="display:none"></pre>
        <br/>
    </div>
</script>
</body>
</html>