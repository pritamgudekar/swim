var ViewLogon = Backbone.View.extend({
	el: '#container-main',
	
	initialize: function(){
		this.render();
	},
	
	render: function(){
		var template = _.template($("#template-login-register").html(), {});
		this.$el.html(template);
	}
});

var ViewLogin = Backbone.View.extend({
    el: '#contLet',

    events: {
    	//'click ul.dropdown-menu li a': 'applyRemoveTags',
    },
    
    initialize: function(){
    	this.render();
    },
    
    render: function(){
    	var template = _.template($("#template-login").html(), {});
    	this.$el.html(template);
    }
});

var ViewRegister = Backbone.View.extend({
    el: '#contRight',

    events: {
    	//'click ul.dropdown-menu li a': 'applyRemoveTags',
    },
    
    initialize: function(){
    	this.render();
    },
    
    render: function(){
    	var template = _.template($("#template-register").html(), {});
    	this.$el.html(template);
    }
});

$(document).ready(function() {
	var loginView = new ViewLogon();
	var loginView = new ViewLogin();
	var loginView = new ViewRegister();
});