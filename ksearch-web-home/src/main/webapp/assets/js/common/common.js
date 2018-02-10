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

    $myNotify.info=function (msg) {
        var options = {message: msg};
        settings.type = "info";
        $.notify(
            options,
            settings
        );
    }

    $myNotify.warn=function (msg) {
        var me = this;
        var options = {message: msg};
        settings.type = "warning";
        $.notify(
            options,
            settings
        );
    }
    $myNotify.success=function (msg) {
        var options = {message: msg};
        settings.type = "success";
        $.notify(
            options,
            settings
        );
    }


    $myNotify.danger=function (msg) {
        var options = {message: msg};
        settings.type = "danger";
        $.notify(
            options,
            settings
        );
    }

    window.$myNotify = $myNotify;



    var $myDialog={};
    $myDialog.confirm = function (message,type,callback) {
        var css = type.substr("type-".length-1,type.length-1);
        BootstrapDialog.confirm({
            title: '提示',
            message: message,
            type: type,
            btnCancelLabel: '取消',
            btnOKLabel: '确定',
            btnOKClass: 'btn-'+css,
            callback: callback
        });
    }
    window.$myDialog=$myDialog;
} ());

