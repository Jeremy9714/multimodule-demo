$(function () {
    // $('body').on('click', '#meta > li', function (e) {
    //     $.ajax({
    //         url: getRootPath() + "/multidb/checkLogin",
    //         type: "POST",
    //         dataType: "json",
    //         success: function (ret) {
    //             debugger;
    //             if (200 == ret.code) {
    //                 var targetObj = $(e.currentTarget);
    //                 var id = targetObj.attr("id");
    //                 layer.msg("已登录 " + id);
    //             } else {
    //                 layer.msg("未登录");
    //             }
    //         }, error: function (e) {
    //             layer.msg("网络异常");
    //         }
    //     });
    // });

    $('body').on('click', '#adviceMake > a', function (e) {
        $.ajax({
            url: getRootPath() + "/multidb/checkLogin",
            type: "POST",
            dataType: "json",
            success: function (ret) {
                debugger;
                if (200 == ret.code) {
                    var targetObj = $(e.currentTarget);
                    var id = targetObj.attr("id");
                    layer.msg("已登录 " + id);
                } else {
                    layer.msg("未登录");
                }
            }, error: function (e) {
                layer.msg("网络异常");
            }
        });
    });
})
