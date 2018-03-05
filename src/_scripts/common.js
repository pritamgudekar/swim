//Make a call to the $.ajax()
function doAjaxCall(param, ajaxSuccess_callback, queueAjaxCall, ajaxFail_callback){
    if(param.dataType != 'text'){
        param.contentType = "application/json; charset=utf-8";
    }

    var request = $.ajax(param);

    request.success(function( result,status,xhr ){
        //alert("Success");
    	ajaxSuccess_callback(result, status, xhr);
    });

    request.fail(function( jqXHR, textStatus ){
        if(ajaxFail_callback != undefined){
            ajaxFail_callback(jqXHR, textStatus);
        }
        console.log(jqXHR);
        //doAjaxErrorHandler(jqXHR, textStatus, {type: 'ajax', render: ajaxSuccess_callback, param: param}, queueAjaxCall);
    });
}

function doAjaxErrorHandler(jqXHR, textStatus, queueRender, queueAjaxCall){
	//If the APIs are accessing from public url then don't call app error messages
	if(document.location.href.indexOf('/public/') >=0 ){
		return false;
	}
	//systemError(textStatus, jqXHR, '');
}

/**
 * ----- Route to the page -----
 * @param page
 * @returns
 */
function moveToPage(page){
	document.location.href = page;
}