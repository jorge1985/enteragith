/**
 * Opportunities Login page. 
 */
$(document).foundation();

function home(){
	$("#login-form").submit();
}

function send(){
	var u = $("#user").val();
	
	if (u==''){
		alert($("#username_empty_msg").val());
		return;
	}

   	$.ajax({
   		type: "POST",
        url: 'resetPassword.html',
        data: { username: u },
        dataType: 'json', 
        success: function(data) {
        	$("#lostPasswordModal a.close-reveal-modal").trigger('click');
        },
        error: function() {
        	init();
        }
   	});
}

$(document).ready(function() {
	$('#password').keypress(function(event){
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if (keycode==13){
			home();
		}
	});
});

