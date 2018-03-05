/**
 * ----- VIEW: {Left: Login form, Right: Register form} -----
 */
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

/**
 * ----- VIEW: Login form -----
 */
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

/**
 * ----- VIEW: Register form -----
 */
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

/**
 * ----- Submit for login ----- 
 */
$(document).on("click", "#btnLogin", function(){
	var form = $("#formLogin");
	$(form).validate({
		rules: {
			username: "required",
			password: "required"
		},
		messages: {
			username: {
				required: "Please enter username."
			},
			password: {
				required: "Please enter password."
			}
		},
		submitHandler: function(form) {
			if(form.username.value == "pritam" && form.password.value == "pritam"){
				var url = "https://httpbin.org/get";
				doAjaxCall(url, function(){
					setDataStorageValue("username", form.username.value);
					setDataStorageValue("password", form.password.value);
					alert("Login successfull!");
				});
				//moveToPage("dashboard.html");
			}else{
				alert("Please enter valid username and/or password.");
				return false;
			}
	  	}
	});
});

/**
 * ----- Submit for registration ----- 
 */
$(document).on("click", "#btnRegister", function(){
	var form = $("#formRegister");
	$(form).validate({
		rules: {
			username: "required",
			password: "required",
			confirmPassword: {
				required: true,
				matchConfirmPassword: true
			},
			email: {
				required: true,
				email: true
			},
			phone: {
				required: true,
				minlength: 10,
				digits: true
			}
		},
		messages: {
			username: {
				required: "Please enter username."
			},
			password: {
				required: "Please enter password."
			},
			confirmPassword: {
				required: "Please enter confirm password.",
			},
			email: {
				required: "Please enter email address.",
				email: "Please enter valid email address"
			},
			phone: {
				required: "Please enter phone number.",
				digits: "Please enter numbers[0-9] only.",
				minlength: "Please enter valid phone number."
			}
		},
		submitHandler: function(form) {
			alert("Congratulations!!!");
	  	}
	});
});

$.validator.addMethod("matchConfirmPassword", function(val, el){
	return $("#txtRegPassword").val() === val;
},
"Confirm password did not matched.");

/**
 * ----- Initialize the application -----
 */
$(document).ready(function() {
	var loginView = new ViewLogon();
	var loginView = new ViewLogin();
	var loginView = new ViewRegister();
});

/**
 * ----- Route to the page -----
 * @param page
 * @returns
 */
function moveToPage(page){
	document.location.href = page;
}