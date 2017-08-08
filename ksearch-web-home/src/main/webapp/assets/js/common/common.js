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

/**
 * 左侧菜单栏事件
 */
var userInfo;
$(function () {
    $(".left-menu").each(function () {
        console.log($(this).attr("href"));
        console.log(window.location.pathname);
        if($(this).attr("href").indexOf(window.location.pathname)>-1){
            $(this).parent().addClass("active");
            return false;
        }
    });


    // 获取用户信息
    var ajax = new $ax("/login/user", function (data) {
        // 成功
        if (data.status===true&&data.data){
            var info = '<small>欢迎光临,</small>'+data.data.username;
            $("#user-info").html(info);
            userInfo = data.data;
        }
    },function (data) {

    });

    ajax.start();


    $("#modify-pwd").on("click",function () {
        BootstrapDialog.show({
            type:BootstrapDialog.TYPE_PRIMARY,
            title: '修改密码',
            closeByBackdrop: false,
            closeByKeyboard: false,
            //size:BootstrapDialog.SIZE_LARGE,
            message: function(dialog) {
                var form ='<div class="widget-main">'
                    +'<form class="form-horizontal" role="form">'
                    +'<div class="form-group">'
                    +'<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 原密码 :</label>'
                    +'<div class="col-sm-9">'
                    +'<input class="form-control input-mask-date" placeholder="请输入工号" type="text" id="currPwd" />'
                    +'</div>'
                    +'</div>'

                    +'<div class="form-group">'
                    +'<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 新密码 :</label>'
                    +'<div class="col-sm-9">'
                    +'<input class="form-control input-mask-date" placeholder="请输入用户名" type="password" id="password" />'
                    +'</div>'
                    +'</div>'

                    +'<div class="form-group">'
                    +'<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 重复新密码 :</label>'
                    +'<div class="col-sm-9">'
                    +'<input class="form-control input-mask-date" placeholder="请输入用户名" type="password" id="secondPwd" />'
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
                    var aj= new $ax("/user/password", function (data) {
                        if (data.status){

                            $myNotify.success("保存成功");
                            dialogRef.close();

                        }else {
                            $myNotify.danger(data.msg);
                        }
                    });

                    var currPwd = $.trim($("#currPwd").val());
                    if (!currPwd){
                        $myNotify.danger("请输入原密码！");
                        return;
                    }

                    var password = $.trim($("#password").val());
                    if (!password){
                        $myNotify.danger("请输入新密码！");
                        return;
                    }
                    var secondPwd = $.trim($("#secondPwd").val());
                    if (!secondPwd){
                        $myNotify.danger("请重复输入新密码！");
                        return;
                    }

                    aj.set("userId",userInfo.userId);
                    aj.set("currPwd",currPwd);
                    aj.set("password",password);
                    aj.set("secondPwd",secondPwd);
                    aj.start();
                }
            }, {
                icon:'icon-remove',
                label: '关闭',
                action: function(dialogRef){
                    dialogRef.close();
                }
            }],
            onshown:function () {
                // 获取角色信息
                var aj= new $ax("/common/role/select", function (data) {
                    if (data.status){
                        $("#roleId").select2({
                            allowClear: true,
                            placeholder: "请选择类型",
                            data:data.data
                        });
                    }
                });
                aj.start();

                if(row){
                    // 初始化数据
                    $("#userId").val(row.userId);
                    $("#username").val(row.username);
                    $("#roleId").val(row.role.roleId);
                    $("#userId").attr("readOnly",true);
                }

            }
        });
    });

});