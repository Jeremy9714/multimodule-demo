$(function () {
    var grid = new Datatable();
    grid.init({
        src: $("#user-table"),
        onsuccess: function (grid) {
        },
        onerror: function (grid) {
        },
        loadingMessage: '数据加载中......',
        isLoadPreviousState: true,
        dataTable: {
            "columns": [
                {
                    "data": "userId",
                    "sWidth": "15px"
                },
                {
                    "data": "userId",
                    "sWidth": "100px"
                },
                {
                    "data": "name",
                    "sWidth": "100px"
                },
                {
                    "data": "gender",
                    "sWidth": "50px"
                },
                {
                    "data": "mobile",
                    "sWidth": "120px"
                },
                {
                    "data": "departmentName",
                    "sWidth": "120px"
                },
                {
                    "data": "status",
                    "sWidth": "50px"
                },
                {
                    "data": "userId",
                    "sClass": "text-center",
                    "sWidth": "100px"
                }
            ],
            "columnDefs": [
                {
                    "targets": [0],
                    "render": function (data, type, full) {
                        return '<label class=mt-checkbox mt-checkbox-outline><input type="checkbox" value="' + data + '" name="check" /><span></span>';
                    }
                },
                {
                    "targets": [3],
                    "render": function (data, type, full) {
                        var html = '未定义';
                        if (data == '1') {
                            html = '男';
                        } else if (data == '2') {
                            html = '女';
                        }
                        return html;
                    }
                },
                {
                    "targets": [-2],
                    "render": function (data, type, full) {
                        var html = "";
                        if (data == '0') {
                            html = '未同步';
                        } else if (data == '1') {
                            html = '已同步';
                        } else if (data == '2') {
                            html = '待更新';
                        } else if (data == '3') {
                            html = '异常'
                        }
                        return html;
                    }
                },
                {
                    "targets": [-1],
                    "render": function (data, type, full) {
                        var html = '';
                        if (full.departmentName == undefined || full.departmentName == '') {
                            return '<a class="disabled">同步</a>'
                        }
                        if (full.status == '0') {
                            html = '<a data-target="' + data + '" data-status="' + full.status + '"  class="sync_user">同步</a>';
                        } else if (full.status == '2') {
                            html = '<a data-target="' + data + '" data-status="' + full.status + '" class="sync_user">更新</a>';
                        }
                        return html;
                    }
                }
            ],
            "drawCallback": function (settings) {
                $(".sync_user").click(function () {
                    $(this).attribute("disabled", "disabled"); // 防止多次点击
                    let status = $(this).data("status");
                    let userId = $(this).data("target");
                    var title = "同步用户";
                    if (status == '2' || status == 2) {
                        title = '更新用户';
                    }
                    layer.confirm("确认" + title + userId + "?", {title: title, offset: "100px"}, function (index) {
                        $.ajax({
                            url: getRootPath() + "/platform/sync/user",
                            type: 'POST',
                            dataType: 'json',
                            data: {'userId': userId},
                            success: function (result) {
                                layer.closeAll('loading');
                                if (result.statusCode == 200) {
                                    $('#user-table').DataTable().ajax.reload();
                                    layer.msg("同步成功");
                                } else {
                                    layer.msg("同步失败");
                                }
                            },
                            error: function (result) {
                                layer.closeAll("loading");
                                layer.msg("同步失败");
                            }
                        });
                        $(this).removeAttr("disabled");
                    })
                })
            },
            "pageLength": 10,
            "ajax": {
                "url": getRootPath() + '/platform/getUserList',
                "type": "POST",
                "dataType": "json",
                "data": function (d) {
                    d.userId = $("#user-id").val();
                    d.username = $("#username").val();
                    d.status = $("#status").val();
                }
            }
        }
    });

});
