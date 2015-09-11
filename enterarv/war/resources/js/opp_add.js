/**
 * Opportunities add page.
 */

$(document).foundation('reveal', 'reflow');

function update(kind){
	if (kind=='car'){
		$.ajax({
			type: "POST",
	        url: 'updatePublish.html',
	        data: {  
				publish_type: 'add', publish_kind: $("#publish_kind").val(),  publish_id: '',
				brand : $("#car_brand").val(),
				model : $("#car_model").val(),
				price : $("#car_price").val(),
				
				state : $("#car_state").val(),
				city : $("#car_city").val(),
				
				today : $("#car_today").val(),
				transmission : $("#car_transmission").val(),
				mileage : $("#car_mileage").val(),
				
//				public_date : $("#car_date").val(),
				employee : '000111',//$("#car_employee").val(),
				
				color: $("#car_color").val(),
				door : $("#car_door").val(),
				obs : $("#car_obs").val(),
				
				telephone: $("#car_telephone").val(),
				mobilephone: $("#car_mobilephone").val(),
				file : $("#car_file").attr('path')
			},
	        dataType: 'json', 
	        success: function(data) {
	        	showAlert(data.errMsg);
	        	if (data.errCode==0){
	        		$("#form_move_myads").submit();
	        	}
	        },
	        error: function() {
	        }
	    });  	
	}
	
	if (kind=='fur'){
		$.ajax({
			type: "POST",
	        url: 'updatePublish.html',
	        data: {  
				publish_type: 'add', publish_kind: $("#publish_kind").val(),  publish_id: '',

				property : $("#fur_property").val(),
				model : $("#fur_building").val(),
				price : $("#fur_price").val(),

				state : $("#fur_state").val(),
				city : $("#fur_city").val(),
				
				serve_type: $("#fur_serve").val(),
				mileage : $("#fur_meter").val(),

//				public_date : $("#fur_date").val(),
				employee : '000111',//employee : $("#fur_employee").val(),

				amueblado: $("#fur_amueblado").val(),
				plants: $("#fur_plants").val(),
				rooms: $("#fur_rooms").val(),
				
				telephone: $("#fur_telephone").val(),
				mobilephone: $("#fur_mobilephone").val(),
				
				file : $("#fur_file").attr('path')
			},
	        dataType: 'json', 
	        success: function(data) {
	        	showAlert(data.errMsg);
	        	if (data.errCode==0){
	        		$("#form_move_myads").submit();
	        	}
	        },
	        error: function() {
	        }
	    });  	
	}
	
	if (kind=='srv'){
		$.ajax({
			type: "POST",
	        url: 'updatePublish.html',
	        data: {  
				publish_type: 'add', publish_kind: $("#publish_kind").val(),  publish_id: '',

				varios : $("#srv_varios").val(),
				model : $("#srv_article").val(),
				price : $("#srv_price").val(),

				state : $("#srv_state").val(),
				city : $("#srv_city").val(),
				
				obs : $("#srv_desc").val(),
				
//				public_date : $("#srv_date").val(),
				employee : '000111',//employee : $("#srv_employee").val(),

				telephone: $("#srv_telephone").val(),
				mobilephone: $("#srv_mobilephone").val(),
				
				file : $("#srv_file").attr('path')
			},
	        dataType: 'json', 
	        success: function(data) {
	        	showAlert(data.errMsg);
	        	if (data.errCode==0){
	        		$("#form_move_myads").submit();
	        	}
	        },
	        error: function() {
	        }
	    });  	
	}
}

function selectImage(){
	$('#uploadModal').foundation('reveal', 'open', {
	    close_on_background_click: false,
	    url: $('#resPath').val()+'opp_upload.jsp',	
	    data: {	page: 'opport', type: 'image' },
	    success: function(data) {
	    },
	    error: function() {
	    }	    
	});
}

function setOpportFile(name, key){
	var k = $("#publish_kind").val();
	if (k=='001'){
		$("#car_file").attr('path', key);
		
		if (key=='')
			return;
		
	   	$.ajax({
	        url: $('#basePath').val()+'image',
	        data: { 'blob-key': key },
	        dataType: 'json', 
	        success: function(data) {
	        	$("#car_file").attr('src', data.key);
	        },
	        error: function() {
	        	$("#car_file").attr('src', '');
	        }
	    });
	}
	
	if (k=='002'){
		$("#fur_file").attr('path', key);
		
		if (key=='')
			return;
		
	   	$.ajax({
	        url: $('#basePath').val()+'image',
	        data: { 'blob-key': key },
	        dataType: 'json', 
	        success: function(data) {
	        	$("#fur_file").attr('src', data.key);
	        },
	        error: function() {
	        	$("#fur_file").attr('src', '');
	        }
	    });
	}
	
	if (k=='003'){
		$("#srv_file").attr('path', key);
		
		if (key=='')
			return;
		
	   	$.ajax({
	        url: $('#basePath').val()+'image',
	        data: { 'blob-key': key },
	        dataType: 'json', 
	        success: function(data) {
	        	$("#srv_file").attr('src', data.key);
	        },
	        error: function() {
	        	$("#srv_file").attr('src', '');
	        }
	    });
	}
}

function getCity(somedata){
	var k = $("#publish_kind").val();
	var v = '0';
	if (k=='001'){
		$("#car_city").html('<option value="" selected>Selecciona</option>');
		v=$("#car_state").val();
	}else if (k=='002'){
		$("#fur_city").html('<option value="" selected>Selecciona</option>');
		v = $("#fur_state").val();
	}else if (k=='003'){
		$("#srv_city").html('<option value="" selected>Selecciona</option>');
		v = $("#srv_state").val();
	}
	
	$.ajax({
		type: "POST",
        url: 'getCityList.html',
        data: {  
        	state_id: v
		},
        dataType: 'json', 
        success: function(data) {
        	if (data.errCode==0){
        		$.each(data.list, function(index, row){
        			if (k=='001'){
        				$("#car_city").append('<option value="'+row.id+'">'+row.name+'</option>');
        			}
        			if (k=='002'){
        				$("#fur_city").append('<option value="'+row.id+'">'+row.name+'</option>');
        			}
        			if (k=='003'){
        				$("#srv_city").append('<option value="'+row.id+'">'+row.name+'</option>');
        			}
        		});
        	}
        },
        error: function() {
        	init();
        }
    });  		
	
	if (somedata!=''){
		if (k=='001'){
			$("#car_city").val(somedata);
		}
		if (k=='002'){
			$("#fur_city").val(somedata);
		}
		if (k=='003'){
			$("#srv_city").val(somedata);
		}
	}
	
}

$("#publi-content .selecciona select").on("change", function() {
	form = $(this).val();
	if (form==''){
		$("#form-loader").html('');
		$("#publish_kind").val('');
		return;
	}
	
	if (form=='automovil'){
		$("#publish_kind").val('001');
	}
	if (form=='inmueble'){
		$("#publish_kind").val('002');
	}
	if (form=='varios'){
		$("#publish_kind").val('003');
	}

	$.ajax({
		url : "form_" + form + ".html",
		context : document.body,
		beforeSend : function() {
			$("#publi-content .preloader-image").css("display", "block");
			$("#form-loader").html("");
		}
	
	}).done(function(html) {
		$("#publi-content .preloader-image").css("display", "none");
		$("#form-loader").html(html);

//		$('.fdatepicker').fdatepicker();
		
		if (form=='automovil'){
			var y = parseInt(getCurrentYear());
			for (var i=y; i>=1940; i--){
				$("#car_today").append('<option value="'+i+'">'+i+'</option>')
			}
			
		   	$("#car_state").change(function(){
		   		var v=$("#car_state").val();
		   		$("#car_city").html('<option value="" selected>Selecciona</option>');
//		   		$("#fur_city").html('');
//		   		$("#srv_city").html('');
		   		if (v!='0' && v!=''){
		   			getCity('');
		   		}
		   	});

		   	$("#car_form").on("valid invalid submit", function(event) {
		   		event.stopPropagation();
		   		event.preventDefault();
		   		
		   		if (event.type=='valid'){
		   			if ($("#car_file").attr('path')==''){
		   				showAlert($("#validate_file_empty_msg").val());
		   				return;
		   			}
		   			
		   			update('car');
		   		}
		   	});
		}
		
		if (form=='inmueble'){
		   	$("#fur_state").change(function(){
		   		var v=$("#fur_state").val();
//		   		$("#car_city").html('');
		   		$("#fur_city").html('<option value="" selected>Selecciona</option>');
//		   		$("#srv_city").html('');
		   		if (v!='0' && v!=''){
		   			getCity('');
		   		}
		   	});
		   	
		   	$("#fur_form").on("valid invalid submit", function(event) {
		   		event.stopPropagation();
		   		event.preventDefault();
		   		
		   		if (event.type=='valid'){
		   			if ($("#fur_file").attr('path')==''){
		   				showAlert($("#validate_file_empty_msg").val());
		   				return;
		   			}
		   			
		   			update('fur');
		   		}
		   	});
		}
		
		if (form=='varios'){
		   	$("#srv_state").change(function(){
		   		var v=$("#srv_state").val();
//		   		$("#car_city").html('');
//		   		$("#fur_city").html('');
		   		$("#srv_city").html('<option value="" selected>Selecciona</option>');
		   		if (v!='0' && v!=''){
		   			getCity('');
		   		}
		   	});
		   	
		   	$("#srv_form").on("valid invalid submit", function(event) {
		   		event.stopPropagation();
		   		event.preventDefault();
		   		
		   		if (event.type=='valid'){
		   			if ($("#srv_file").attr('path')==''){
		   				showAlert($("#validate_file_empty_msg").val());
		   				return;
		   			}
		   			
		   			update('srv');
		   		}
		   	});
		}
		
	});
})


$(document).ready(function() {
	$('#condiciones').foundation('reveal', 'open',{ close_on_background_click: false });
});
