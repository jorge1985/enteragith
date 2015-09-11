/**
 * Footer page. 
 */

function search_global(search){
	if (search==''){
		return;
	}
	
	$("#edit_search_id").val(search);
	$("#form_move_search").submit();
}

function global_preview(){
	$("#form_move_home").submit();
}

$(document).ready(function() {
	$("#global_search").keypress(function(event){
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if (keycode==13){
			search_global($("#global_search").val());
		}
	});
});

