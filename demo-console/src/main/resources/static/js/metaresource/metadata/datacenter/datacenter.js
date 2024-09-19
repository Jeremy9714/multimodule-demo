var grid = null;
var select_datacenter_code = "";
var select_datacenter_name = "";

$(function () {
    // 初始化表格
    grid = new Datatable();
    grid.init({
        src: $("#data-table"),
        onSuccess: function (grid) {
        },
        onError: function (gird) {
        },
        loadingMessage: "数据加载汇总。。。",
        dataTable: {
            "columns": [
                {
                    "data": "areaName",
                    "sClass": "text-center"
                },
                {
                    "data": "remark",
                    "sClass": "text-center"
                },
                {
                    "data": "orgName",
                    "sClass": "text-center"
                },
                {
                    "data": "areaId",
                    "sClass": "text-center"
                }
            ],
            "columnDefs": [
                {
                    "defaultContents": "",
                    "targets": "_all"
                },
                {
                    "targets": [0],
                    "render": function (data, type, full) {
                        var result = "<span title='" + data + "'>" + data + "</span>";
                        return result;
                    }
                },
                {
                    "targets": [1],
                    "render": function (data, type, full) {
                        var result = "<span title='" + data + "'>" + data + "</span>;"
                        return result;
                    }
                },
                {
                    "targets": [2],
                    "render": function (data, type, full) {
                        if (data == null || data == "null") {
                            data = "";
                        }
                        var result = "<span title='" + data + "'>" + data + "</span>";
                        return result;
                    }
                },
                {
                    "targets": [-1],
                    "render": function (data, type, full) {
                        var result = "<a href='#' style='text-decoration:blink' onclick=editDataCenter('" + full.areaId + "')>" + "编辑 " + "</a>";
                        if (!(full.areaId.length == 2 && full.areaId <= '05')) {
                            result += "<a href='#' style='text-decoration:blink' onclick=delDataCenter('" + full.areaId + "')>" + "删除" + "</a>";
                        }
                        return result;
                    }
                }
            ],
            "pageLength": 10,
            "ajax": {
                "url": DEFAULT_CONTEXT_PATH + "/datacenter/query",
                "type": "post",
                "contentType": "application/json;charset=UTF-8",
                "data": function (d) {
                    d.datacenterName = $("#datacenterName").val().trim();
                    d.select_datacenter_code = select_datacenter_code;
                    return JSON.stringify(d);
                }
            }
        }
    });

    $('input').on("keypress", function (event) {
        if (event.keyCode == 13) {
            ("#searchBtn").trigger("click");
            return false;
        }
    });
    
    
});
