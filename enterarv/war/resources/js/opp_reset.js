/**
 * Opportunities Password reset page. 
 */

$(document).foundation();

function save(){
	var p1= $('#password').val();
	var p2= $('#password2').val();
	if (p1=='' || p2==''){
		alert($("#password_empty_msg").val())
		return;
	}
	
	if (p1!=p2){
		alert($("#password_same_msg").val())
		return;
	}
	
	$("#login-form").submit();
}

$(document).ready(function() {
	$('#password').keypress(function(event){
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if (keycode==13){
			save();
		}
	});
	$('#password2').keypress(function(event){
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if (keycode==13){
			save();
		}
	});
});
