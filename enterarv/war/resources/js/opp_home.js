/**
 * Opportunities home page.
 */

$(document).foundation('reveal', 'reflow');

var current_selection = "";

function wizard_loader(paso_class, url, not_paso) {
	$.ajax({
		url : url,
		context : document.body,
		beforeSend : function() {
			$(".row#wizard .columns").not(not_paso).find(".loader").html("");
			$(".row#wizard .columns").not(not_paso).find("h5").slideUp();
			$(".paso4 .submit").fadeOut("fast");
			$(".row#results .preloader-image").css("display", "none");
			$(".row#results").slideUp();
			$(paso_class + " .preloader-image").css("display", "block");
		}
	}).done(function(html) {
		$(paso_class + " .preloader-image").css("display", "none");
		$(paso_class + " h5").slideDown();
		$(paso_class + " .loader").html(html);
	});
}

function wizard_loader_blank(paso_class, not_paso) {
	$(".row#wizard .columns").not(not_paso).find(".loader").html("");
	$(".row#wizard .columns").not(not_paso).find("h5").slideUp();
	$(".paso4 .submit").fadeOut("fast");
	$(".row#results .preloader-image").css("display", "none");
	$(".row#results").slideUp();
	$(paso_class + " .preloader-image").css("display", "block");
	$(paso_class + " .preloader-image").css("display", "none");
//		$(paso_class + " h5").slideDown();
//		$(paso_class + " .loader").html(html);
}

// Inmueble
$("#inmueble_btn").on("click", function() {
	$(".opport").removeClass('active');
	$(this).addClass('active');
	
	wizard_loader(".paso2", "inmueble-2.html", ".paso1");
	current_selection = "inmueble";
});

$(document).on("change", "#inmueble_2", function() {
	wizard_loader(".paso3", "inmueble-3.html", ".paso1, .paso2");
});

$(document).on("change", "#inmueble_3", function() {
	$.ajax({
		url : "inmueble-3_2.html",
		data : {
			state_id: $("#inmueble_3").val()
		},
		context : document.body,
		beforeSend : function() {
			$(".preloader-image-inside").css("display", "block");
		}
	}).done(function(html) {
		$(".preloader-image-inside").css("display", "none");
		$(".loader_inside").html(html);
	});
});

$(document).on("change", "#inmueble_3_2", function() {
	$(".paso4 .submit").css("display", "block");
	$(".paso4 h5").slideDown();
	$(".row#results").slideUp();

});


// Inmueble adjudicado
$("#inmueble_adjudicado_btn").on("click", function() {
	$(".opport").removeClass('active');
//	$(this).addClass('active');
//	
	wizard_loader_blank(".paso2", ".paso1");
//	current_selection = "inmueble";
////	current_selection = "inmueble_adjudicado";
});

//$(document).on("click", "#continue_btn", function(){
//	wizard_loader(".paso3", "inmueble-3.html", ".paso1, .paso2");
//});


// Automovil
$("#automovil_btn").on("click", function() {
	$(".opport").removeClass('active');
	$(this).addClass('active');
	
	wizard_loader(".paso2", "automovil-2.html", ".paso1");
	current_selection = "automovil";
});

$(document).on("change", "#automovil_2", function() {
	wizard_loader(".paso3", "inmueble-3.html", ".paso1, .paso2");
});

//$(document).on("change", "#automovil_3", function() {
//	$(".paso4 .submit").css("display", "block");
//	$(".paso4 h5").slideDown();
//	$(".row#results").slideUp();
//
//});

// Automovil adjudicado
$("#automovil_adjudicado_btn").on("click", function() {
	$(".opport").removeClass('active');
	$(this).addClass('active');
	
	wizard_loader(".paso2", "automovil-2.html", ".paso1");
//	wizard_loader(".paso2", "inmueble_adjudicado-2.html", ".paso1");
//	wizard_loader(".paso2", "automovil-2.html", ".paso1");
	current_selection = "automovil_adjudicado";
});

// Varios
$("#varios_btn").on("click", function() {
	$(".opport").removeClass('active');
	$(this).addClass('active');
	
	wizard_loader(".paso2", "varios-2.html", ".paso1");
	current_selection = "varios";
});

$(document).on("change", "#varios_2", function() {
	wizard_loader(".paso3", "inmueble-3.html", ".paso1, .paso2");
});

//$(document).on("change", "#varios_3", function() {
//
//	$.ajax({
//		url : "_parts/varios-3_2.html",
//		context : document.body,
//		beforeSend : function() {
//			$(".preloader-image-inside").css("display", "block");
//		}
//	}).done(function(html) {
//		$(".preloader-image-inside").css("display", "none");
//		$(".loader_inside").html(html);
//	});
//});
//
//$(document).on("change", "#varios_3_2", function() {
//	$(".paso4 .submit").css("display", "block");
//	$(".paso4 h5").slideDown();
//	$(".row#results").slideUp();
//
//});

// Submit
$(".paso4 .submit").on("click", function() {
	$(".row#results").slideDown();

	$.ajax({
		url : "results_" + current_selection + ".html",
		context : document.body,
		beforeSend : function() {
			$(".row#results .preloader-image").css("display", "block");
			$(".row#results .loader").css("display", "none");
		}
	}).done(function(html) {
		$(".row#results .preloader-image").css("display", "none");
		$(".row#results .loader").html(html);
		$(".row#results .loader").slideDown();
	});
});

function view_detail(id){
	$('#resultsModal').foundation('reveal', 'open', {
	    close_on_background_click: false,
		url : 'detail.html',
		data : {
			publish_id : id
		}
	});
}

$(document).ready(function() {

});
