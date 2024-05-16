function getRootPath() {
    var protocol = window.location.protocol + '//',
        host = window.location.host + '/',
        pathName = window.location.pathname.split('/');
    var projectName = pathName[1];
    //return (protocol + host );
    return (protocol + host + projectName );
}
//图片logo的目录
function getLogoPath() {
    return getRootPath() + '/static/img/';
}
$(function () {
    $(".DTFC_LeftBodyWrapper").he
});

function formatDate(timestamp, fmt) {
    if (timestamp == undefined || timestamp == null) {
        return "--";
    }
    if(typeof(fmt) == "undefined" || fmt == ""){
        fmt = "yyyy-MM-dd hh:mm:ss";
    }
    if(typeof(timestamp) == 'string'){
        timestamp = parseInt(timestamp);
    }
    var date = new Date(timestamp);
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    let o = {
        'M+': date.getMonth() + 1,
        'd+': date.getDate(),
        'h+': date.getHours(),
        'm+': date.getMinutes(),
        's+': date.getSeconds()
    };
    for (let k in o) {
        if (new RegExp(`(${k})`).test(fmt)) {
            let str = o[k] + '';
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : padLeftZero(str));
        }
    }
    return fmt;
}
function padLeftZero(str) {
    return ('00' + str).substr(str.length);
}

function humanReadableByteCount($size){
    var size  = parseFloat($size);
    var rank =0;
    var rankchar ='Bytes';
    while(size>1024 && rank < 3){
        size = size/1024;
        rank++;
    }
    if(rank==1){
        rankchar="KB";
    }
    else if(rank==2){
        rankchar="MB";
    }
    else if(rank==3){
        rankchar="GB";
    }
    return size.toFixed(2)+ " "+ rankchar;
}

/**
 * 特殊字符转义
 * @param pageStr
 * @returns
 */
function transSpecialChar(pageStr){
    if(pageStr != undefined && pageStr != "" && pageStr != 'null'){
        pageStr = pageStr. replace(/\r/g, "\\r");
        pageStr = pageStr.replace(/\n/g, "\\n");
        pageStr = pageStr.replace(/\t/g, "\\t");
        pageStr = pageStr.replace(/\\/g, "\\\\");
        pageStr = pageStr. replace(/("")+/g, '"');
        pageStr = pageStr.replace(/\'/g, "&#39;");
        pageStr = pageStr.replace(/ /g, "&nbsp;");
        pageStr = pageStr. replace(/</g, "&lt;");
        pageStr = pageStr.replace(/>/g, "&gt;");
    }
    return pageStr;
}