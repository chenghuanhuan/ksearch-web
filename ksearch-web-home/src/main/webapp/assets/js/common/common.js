(function () {
    var $myNotify = function () {
    };


    //var options = {message: ""};
    var settings = {
        //type: 'danger',
        allow_dismiss: true,//是否可关闭
        newest_on_top: true,// 从上往下
        //showProgressbar: true
        z_index: 9999,
        delay: 3000,
        placement: {
            from: "top",
            align: "center"
        }
    };
    $myNotify.prototype = {
            info:function (msg) {
                var me = this;
                var options = {message: msg};
                settings.type = "info";
                $.notify(
                    options,
                    settings
                );
            }
            ,warn:function (msg) {
            var me = this;
            var options = {message: msg};
            settings.type = "warning";
            $.notify(
                options,
                settings
            );
            }
            ,success:function (msg) {
                var me = this;
                var options = {message: msg};
                settings.type = "success";
                $.notify(
                    options,
                    settings
                );
            }
            ,danger:function (msg) {
            var me = this;
            var options = {message: msg};
            settings.type = "danger";
            $.notify(
                options,
                settings
            );
        }
    };

    window.$myNotify = $myNotify;
} ());