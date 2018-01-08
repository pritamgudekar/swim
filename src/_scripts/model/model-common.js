/*
 * MODEL - 
 */
var ModelLogin = Backbone.Model.extend({
	defaults: {
		"id": "101", // ubr.BibID
		"description": "Project 2",// b.Description
		"isDeleted": "1",// b.IsHidden - is project deleted by the user
	}
});