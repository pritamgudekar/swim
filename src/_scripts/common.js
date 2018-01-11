

//Make a call to the $.ajax()
function doAjaxCall(param, ajaxSuccess_callback, queueAjaxCall, ajaxFail_callback){
//    if(getCookie("userName") != getDataStorageValue("userName")
//            && getCookie("userName") != ""
//            && getDataStorageValue("userName") != ""
//            && getDataStorageValue("userName") != null){
//        var sessionExpMsg = "Your session as " + getDataStorageValue("userName") + " has expired, likely due to a concurrent login (as user " + getCookie("userName") + ") in a different tab or window. Please click OK to continue.";
//
//        popups.alertMessage(sessionExpMsg, 'OK', '', 'USER_CHANGED');
//        setDataStorageValue("userName", "");
//        return;
//    }

    //param.timeout = ajaxTimoutCounter;
    //if(queueAjaxCall != undefined && !queueAjaxCall){
    //    param.timeout = 30000;
    //}
    if(param.dataType != 'text'){
        param.contentType = "application/json; charset=utf-8";
    }

    //Re-initiate the check user session timeout from last backend api called
    //if(param.url.match(/api\/v1.0/i) != null && param.url.match(/api\/v1.0\/ping/i) == null){
    //    clearTimeout(checkUserSessionActive);
    //    checkUserSessionActive = setTimeout(checkUserSession, checkUserSessionActiveTimer);
    //}

    var request = $.ajax(param);

    request.success(function( result,status,xhr ){
        alert("Success");
    	//ajaxSuccess_callback(result, status, xhr);
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
	//Add only required ajax calls and not for ping or for queueAjaxCall = false
	if((queueAjaxCall == undefined || queueAjaxCall) && jqXHR.status != 401){
		if(queueRender.param.data != undefined){
			var newMsg = JSON.parse(queueRender.param.data);
			var actions = _.map(failedAjaxCalls, function(queue, i){
				if(typeof queue.queueRender.param.data != "undefined"){
					var queueMsg = JSON.parse(queue.queueRender.param.data);
					if(queueMsg.id == newMsg.id){
						failedAjaxCalls.splice(i, 1);
					}
				}
			});
		}
		failedAjaxCalls.push({queueRender: queueRender, queueAjaxCall: queueAjaxCall});
	}else{
		//systemError(textStatus, jqXHR, '');
	}

	//NTN-2632 - Do not show error message for SSE API failuar
	var isSSECall = queueRender.param.url.indexOf("/sse/publish") >= 0;
	if(!isConnectionTimeoutEnabled && !isSSECall ){
		isConnectionTimeoutEnabled = true;
		systemError(textStatus, jqXHR, '');
	}
}