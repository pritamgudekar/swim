/**
 * ----- VIEW: Load tabs layout -----
 */
var ViewTabsLayout = Backbone.View.extend({
	el: '#container-main',
	
	initialize: function(){
		this.render();
	},
	
	render: function(){
		var template = _.template($("#template-tabs-layout").html(), {});
		this.$el.append(template);
		$( "#containerTabsLayout" ).tabs().addClass('ui-tabs-vertical ui-helper-clearfix');
		template = _.template($("#template-list-appointments").html(), {});
		$("#tab-1").append(template);
	}
});

function doLogout(){
	removeDataStorageValue("username");
	moveToPage("index.html");
}

/**
 * ----- Initialize dashboard page -----
 */
$(document).ready(function() {
	var tabsView = new ViewTabsLayout();
	$("#banner-welcome #userName").text(getDataStorageValue("username"));
});

$(document).on("click", "#linkLogout", doLogout);

$(document).on("click", "#btnNewAppointment", function(){
	var template = _.template($("#template-create-appointment").html(), {});
	$("#tab-1").html(template);
});

$(document).on("click", "#btnCreateAppointment, #btnCancelAppointment", function(){
	var template = _.template($("#template-list-appointments").html(), {});
	$("#tab-1").html(template);
});