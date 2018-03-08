/*
 * Collections for retriving elements from backend to fillup models using views
 */

/*
 * Collection - login
 */
var ListLogin = Backbone.Collection.extend({
	initialize: function(models, options) 
	{
		this.url = "";
	},
	model: ModelLogin,
	parse: function(response) {
		var responseArrays = response.response;
        return responseArrays;
    },
    sync: function(method, model, options) {
        return '';
    	/*var that = this;
            var params = _.extend({
                type: 'GET',
                cache:false,
                contentType: "application/json",
                dataType: 'json',
                url: that.url,
                processData: false,
                timeout: ajaxTimoutCounter
            }, options);

        return $.ajax(params);*/
    }
});
