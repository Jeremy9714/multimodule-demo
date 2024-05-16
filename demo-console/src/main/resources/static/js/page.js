$(function () {
    try
    {
        if(window.parent && window.parent.resizeIframe){
            var document_height = 0,
                document_height_interval;
            //检测页面高度
            function check_document_height() {
                //获取当前页面高度，与原有高度进行比较，若不相等则向父级报告修改iframe高度
                var _body = $('body'),
                    current_document_height = $(document.body).height() +
                        parseFloat(_body.css('margin-top')) +
                        parseFloat(_body.css('margin-bottom')) +
                        parseFloat(_body.css('padding-top')) +
                        parseFloat(_body.css('margin-bottom'));
                var current_modal_height = 0;
                $('.modal.in').each(function () {
                    var modal_dom = $(this).find('.modal-dialog'),
                        modal_height = modal_dom.height() +
                            parseFloat(modal_dom.css('margin-top')) +
                            parseFloat(modal_dom.css('margin-bottom')) +
                            parseFloat(modal_dom.css('padding-top')) +
                            parseFloat(modal_dom.css('margin-bottom'));
                    if(modal_height > current_modal_height){
                        current_modal_height = modal_height;
                    }
                });
                if(current_modal_height > current_document_height){
                    current_document_height = current_modal_height;
                }
                if(current_document_height !== document_height){
                    document_height = current_document_height;
                    window.parent.resizeIframe(document_height);
                }
                document_height_interval = setTimeout(function() {
                    check_document_height();
                }, 500);
            }
            check_document_height();
        }
    } catch(err) {
        console.log("跨域，无法调用父页面调整大小方法");
    }
});