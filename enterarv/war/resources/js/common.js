/**
 * Common Script. 
 */

function showAlert(msg){
	$('html, body').animate({scrollTop : 0}, 400);
	$("#msg_alert_progress").hide();
		
	$("#msg_alert span").html(msg);
	$("#msg_alert").fadeIn(100);
	setTimeout(function(){
		$("#msg_alert").fadeOut(1000);
	}, 1000);
}

function showLoading(){
	$("#msg_alert_progress").show();
	$("#msg_alert span").html('Loading...');
	$("#msg_alert").show();
}

function hideLoading(){
	$("#msg_alert").fadeOut(1000);
}

function getToday(){
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1;//January is 0!`

	var yyyy = today.getFullYear();
	if(dd<10){dd='0'+dd;}
	if(mm<10){mm='0'+mm;}
	today = yyyy + "-" + mm+'-'+dd;
	
	return today;
}

function getCurrentYear(){
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1;//January is 0!`

	var yyyy = today.getFullYear();
	return yyyy;
}

function getTodayAfterYear(year){
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1;//January is 0!`

	var yyyy = parseInt(today.getFullYear())+year;
	if(dd<10){dd='0'+dd;}
	if(mm<10){mm='0'+mm;}
	today = yyyy + "-" + mm+'-'+dd;
	
	return today;
}

function randomColor(){
	return "rgb("+parseInt(Math.random()*1000 % 256)+","+parseInt(Math.random()*1000 % 256)+","+parseInt(Math.random()*1000 % 256)+")"
}

