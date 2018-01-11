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
    	//'click #btnLogin': 'doLogin'
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

$(document).on("click", "#btnLogin", function(){
	var form = $("#formLogin")[0].elements;
	if(form.username.value == "pritam" && form.username.value == "pritam"){
		var url = "http://n-flat.noodletools.com/logon/api/v1.0/user?return_subscriber_info=1&return_details=1&_=1515657827737";
		doAjaxCall(url);
		//moveToPage("dashboard.html");
	}else{
		alert("Please enter valid username and/or password.");
		return false;
	}
});

$(document).ready(function() {
	var loginView = new ViewLogon();
	var loginView = new ViewLogin();
	var loginView = new ViewRegister();
});

function moveToPage(page){
	document.location.href = page;
}