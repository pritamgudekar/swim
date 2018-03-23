/*
 * All cookies and sessioStorage are defined and operated here
 * 
 */

/**
 * set cookies by its name and value along with expiry days
 * @param cname, cvalue, exdays
 */
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "";//"expires=" + d.toGMTString();
    document.cookie = cname+"="+cvalue+"; " +
                    "path=/; " +
                    //"domain="+document.domain+"; " +
                    "expires="+expires;
}

/**
 * get cookie by name
 * @param cname
 * @returns
 */
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

/**
 * check for cookie has been set or not
 * @param cname
 * @returns {Boolean}
 */
function hasCookie(cname) {
    var key=getCookie(cname);
    if (key != "") {
        return true;
    } else {
        return false;
    }
}

/**
 * delete specific cookie
 * @param cname
 */
function deleteCookie(cname) {
    document.cookie = cname +'=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/; domain='+document.domain+';';
}

/**
 * clear entire cookies
 */
function clearAllCookies(){
    var cookies = document.cookie.split(";");
    for (var i = 0; i < cookies.length; i++){   
        var spcook =  cookies[i].split("=");
        deleteCookie(spcook[0]);
    }
}

/**
 * initial function to check whether cookie is set or not. If not then redirect to login page
 */
function checkUserLogin(){
}


/**
 * Redirect to specific page
 */
function moveToPage(path){
    window.location.href = path;
}

/**
 * Return session storage value
 * @param key
 * @returns
 */
function getDataStorageValue(key){
    return customStorage.getItem(key);
}
/**
 * Set session storage value for a key
 * @param key
 * @param value
 */
function setDataStorageValue(key, value){
    customStorage.setItem(key, value);
}

/**
 * Remove session storage key
 * @param key
 * @returns
 */
function removeDataStorageValue(key){
    customStorage.removeItem(key);
}

/**
 * Returns total count of storage
 * @returns
 */
function getDataStorageCount(){
    try{
        return customStorage.length();
    }catch(e){
        return customStorage.length;
    }
}

/**
 * Returns particular key from storage
 * @param index
 * @returns
 */
function getDataStorageKey(index){
    return customStorage.key(index);
}

/**
 * clear entire data storage
 */
function clearAllDataStorage(){
    customStorage.clear();
}

//TODO: Need to finalize the method for storing data
function getStorage() {
    var storageImpl;
    
    try { 
        sessionStorage.setItem("storage", ""); 
        sessionStorage.removeItem("storage");
        storageImpl = sessionStorage;
       // console.log('sessionStorage SUPPORTED');
    }
    catch(err) { storageImpl = new SessionStorageAlternative();//console.log('sessionStorage NOT Supported');
     }

    return storageImpl;
}

//This is an alternative for sessionStorage not supporting browsers like Safari in private mode
function SessionStorageAlternative() {

    var structureSessionStorage = {};

    this.setItem = function(key, value) {
        structureSessionStorage = JSON.parse(window.name);
        structureSessionStorage[key] = value;
        window.name = JSON.stringify(structureSessionStorage);
    }

    this.getItem = function(key) {
        if(window.name == '' || window.name == '"{}"'){
            window.name = JSON.stringify({});
        }
        structureSessionStorage = JSON.parse(window.name);
        if(typeof structureSessionStorage[key] != 'undefined' ) {
            return structureSessionStorage[key];
        }
        else {
            return null;
        }
    }

    this.removeItem = function(key) {
        structureSessionStorage = JSON.parse(window.name);
        structureSessionStorage[key] = undefined;
        window.name = JSON.stringify(structureSessionStorage);
    }
    
    this.clear = function(){
        window.name = "";
        structureSessionStorage = {};
    }
    
    this.length = function(){
        structureSessionStorage = JSON.parse(window.name);
        return _.size(structureSessionStorage);
    }
    
    this.key = function(index){
        structureSessionStorage = JSON.parse(window.name);
        var key = _.keys(structureSessionStorage)[index];
        return key;
    }
}

var customStorage = getStorage();