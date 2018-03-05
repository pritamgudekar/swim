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