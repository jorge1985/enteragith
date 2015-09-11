/**
 * User page. 
 */

function showEditData(data){
	$("#user_id").attr('readonly', 'readonly');
	$("#musuario").attr('readonly', 'readonly');
	
	$("#user_id").val(data.user.employeeNumber);
	$("#user_password").val('');
	
	if (data.user.level!='')
		$("#user_level").val(data.user.level);
	
	$("#user_firstname").val(data.user.firstname);
	$("#user_lastname").val(data.user.lastname);
	$("#user_name").val(data.user.name);
	$("#user_token").val(data.user.token);
	$("#user_email").val(data.user.email)

	if (data.user.direccion!='0')
		$("#user_direccion").val(data.user.direccion);
	else
		$("#user_direccion").val('all');

	if (data.user.empresa!='0')
		$("#user_empresa").val(data.user.empresa);
	else
		$("#user_empresa").val('all');

	if (data.user.ciudad!='0')
		$("#user_ciudad").val(data.user.ciudad);
	else
		$("#user_ciudad").val('all');
	
	if (data.user.gender!='')
		$("#user_gender").val(data.user.gender);
	
	$("#user_arbol").val(data.user.arbol);
	$("#user_entered").val(data.user.entered);	
	$("#user_horario").val(data.user.horario);
	
	$("#user_admission").val(data.user.admission);
	$('#user_admission').fdatepicker('update', data.user.admission);
	
	$("#user_nombramiento").val(data.user.nombramiento);	
	$("#user_location").val(data.user.location);	
	
	$("#user_birthday").val(data.user.birthday);
	$('#user_birthday').fdatepicker('update', data.user.birthday);
	
	$("#user_jobarea").val(data.user.jobarea_code);	
	$("#user_employee").val(data.user.employment_code);	
	$("#musuario").val(data.user.musuario);	
	
//	if (data.user.parent_division!='0')
//		$("#user_division").val(data.user.parent_division);
//	else
//		$("#user_division").val('all');
//	
//	if (data.user.parent_city!='0')
//		$("#user_geographical").val(data.user.parent_city);
//	else
//		$("#user_geographical").val('all');
//
//	getSubDivision(data.user.division);
//	getCity(data.user.city);
//	
//	if (data.user.people_manager=='001'){
//		$("#peopleOnly").prop('checked', true);
//	}else{
//		$("#peopleAll").prop('checked', true);
//	}
//
//	if (data.user.new_hire=='001'){
//		$("#newhireYes").prop('checked', true);
//	}else{
//		$("#newhireNo").prop('checked', true);
//	}
	
	if (data.user.active=='1'){
		$("#activeYes").prop('checked', true);
	}else{
		$("#activeNo").prop('checked', true);
	}
	
//	if (data.user.promote==''){
//		$("#user_promote").val('all');
//	}else{
//		$("#user_promote").val(data.user.promote);
//	}
//	
//	if (data.user.jobgrade==''){
//		$("#user_jobgrade").val('all');
//	}else{
//		$("#user_jobgrade").val(data.user.jobgrade);
//	}
//		
//	if (data.user.payowner==''){
//		$("#user_payowner").val('all');
//	}else{
//		$("#user_payowner").val(data.user.payowner);
//	}
	
	if (data.user.security_level!='' && data.user.security_level!='000' ){
		$("#user_security").val(data.user.security_level);
	}else{
		$("#user_security").val('000');
	}
	
	openUserModal();
}

function init(){
	$("#btnReset").trigger('click');
}

function edit_user(id){
	$.ajax({
		type: "POST",
        url: 'getUser.html',
        data: {  
        	user_id: id
		},
        dataType: 'json', 
        success: function(data) {
        	if (data.errCode==0){
        		$("#type").val('edit');
        		$("#edit_user_id").val(id);
        		
        		showEditData(data);
        	}
        },
        error: function() {
        	init();
        }
    });  		
}

function showCity(data, somedata){
	$("#user_city").append('<option value="">'+$("#placeholder_select").val()+'</option>');
	$.each(data, function(index, row){
		$.each(row.child, function(index2, row2){
			$("#user_city").append('<option value="'+row2.id+'">'+row2.name+'</option>');
		});
	});
	
	if (somedata!='' && somedata!='0'){
		$("#user_city").val(somedata);
	}
}

function getCity(somedata){
	var temp="";
	temp = $("#user_geographical").val();
	$("#user_city").html('');
	if (temp=="all")
		return;
	
	$.ajax({
		type: "POST",
        url: $("#basePath").val()+'category/loadCity.html',
        data: {  
        	geographical: temp
		},
        dataType: 'json', 
        success: function(data) {
        	if (data.errCode==0){
        		showCity(data.list, somedata);
        	}
        },
        error: function() {
        }
    });  	
}

function showSubDivision(data, somedata){
	$("#user_subdivision").append('<option value="">'+$("#placeholder_select").val()+'</option>');
	$.each(data, function(index, row){
		$.each(row.child, function(index2, row2){
			$("#user_subdivision").append('<option value="'+row2.id+'">'+row2.name+'</option>');
		});
	});
	
	if (somedata!='' && somedata!='0'){
		$("#user_subdivision").val(somedata);
	}
}

function getSubDivision(somedata){
	var temp="";
	temp = $("#user_division").val();
	$("#user_subdivision").html('');
	if (temp=="all" || temp=="0")
		return;
	
	$.ajax({
		type: "POST",
        url: $("#basePath").val()+'category/loadDivision.html',
        data: {  
        	division: temp
		},
        dataType: 'json', 
        success: function(data) {
        	if (data.errCode==0){
        		showSubDivision(data.list, somedata);
        	}
        },
        error: function() {
        }
    });  	
}

function update_user(type, id){
	if (type=='delete'){
		var t = confirm('Are you sure to delete this user?');
		if (!t)
			return;
	}else if (type=='edit'){
		id = $("#edit_user_id").val();
	}else if (type=='add'){
		id = $("#user_id").val();
	}else{
		return;
	}	
	
	$.ajax({
		type: "POST",
        url: 'updateUser.html',
        data: {  
			type: type, user_id: id,
			musuario : $("#musuario").val(),
			gender: $("#user_gender").val(),
			birthday: $("#user_birthday").val(),
			
			user_name: $("#user_name").val(),
			firstname: $("#user_firstname").val(),
			lastname: $("#user_lastname").val(),
			email: $("#user_email").val(),
			password: $("#user_password").val(),
			employee: $("#user_employee").val(),
			jobarea: $("#user_jobarea").val(),
			admission: $("#user_admission").val(),
			location: $("#user_location").val(),
			
			nombramiento: $("#user_nombramiento").val(),
			horario: $("#user_horario").val(),
			entered: $("#user_entered").val(),
			arbol: $("#user_arbol").val(),
			token: $("#user_token").val(),
			
//			division: $("#user_division").val()=='all' ? '0' : $("#user_division").val(),
//			sub_division: $("#user_subdivision").val()=='' ? '0' : $("#user_subdivision").val(),
//			
//			geographical: $("#user_geographical").val()=='all' ? '0' : $("#user_geographical").val(),
//			city: $("#user_city").val()=='' ? '0' : $("#user_city").val(),
//					
//			promote : $("#user_promote").val()=='all' ? '' : $("#user_promote").val(),
//			jobgrade : $("#user_jobgrade").val()=='all' ? '' : $("#user_jobgrade").val(),
//			payowner : $("#user_payowner").val()=='all' ? '' : $("#user_payowner").val(),
//					
//			people_manager: $("#peopleOnly").prop('checked') ? '001' : '002',
//			newhire: $("#newhireYes").prop('checked') ? '001' : '002',
			
			direccion: $("#user_direccion").val()=='all' ? '0' : $("#user_direccion").val(),
			empresa: $("#user_empresa").val()=='all' ? '0' : $("#user_empresa").val(),
			ciudad: $("#user_ciudad").val()=='all' ? '0' : $("#user_ciudad").val(),
			
			level : $("#user_level").val(),
			security_level : $("#user_security").val(),
			active: $("#activeYes").prop('checked') ? '1' : '0'
		},
        dataType: 'json', 
        success: function(data) {
        	if (data.errCode==0){
        		if (type!='delete'){
        			closeUserModal();
        		}

//        		location.reload();
        		$('#user_table').dataTable().api().ajax.reload();
        		showAlert(data.errMsg);
        	}else{
        		alert(data.errMsg);
        	}
        },
        error: function() {
        	closeUserModal();
        }
    });  	
}

function openUserModal(){
	$('#userModal').foundation('reveal', 'open', {
	    close_on_background_click: false,
	    success: function(data) {
	    },
	    error: function() {
	    }	    
	});
}
function closeUserModal(){
	$('#userModal').foundation('reveal', 'close');
}

function updateUser(kind, id){
	init();
	$("#type").val(kind);
	$("#edit_user_id").val(id);

	$("#user_id").removeAttr('readonly');
	$("#musuario").removeAttr('readonly');
	
	if (kind=='edit'){
		edit_user(id);
	}else{
		openUserModal();
	}
}

function openImportModal(){
	$('#importModal').foundation('reveal', 'open', {
	    close_on_background_click: false,
		url: $('#resPath').val()+'popup_import.jsp',	
	    success: function(data) {
	    },
	    error: function() {
	    }	    
	});
}

function reloadData(){
//	$('#user_table').dataTable().api().ajax.reload();
	$("#frm_user_reload").submit();
//	window.location.reload();
}

function closeImportModal(){
	$('#importModal').foundation('reveal', 'close');
	reloadData();
}

function importUser(){
	openImportModal();
}

$(document).ready(function() {
	$('.fdatepicker').fdatepicker({
		format: 'yyyy-mm-d',
	});
	
	$('#user_table').dataTable( {
		"processing": false,
		"serverSide": true,
		"responsive": true,
		"ajax": {
            "url": "loadUser.html",
            "type": "POST"
        },		
		"order": [[ 7, "desc" ]],
		"columnDefs": [ 
           {
			"targets": [  -1, -2 ],
			"orderable": false
			},
			{
				"targets": "_all",
				"searchable": false
			}
		],		
		"columns": [
		            { "data": "id" },
		            { 
		            	"data": "level",
		            	"render": function(data, type, row, meta){
		            		var data = "";
	            			
		            		if (row.level=="1"){
		            			data = $("#role_administrator_value").val();
		            		}else{
		            			data = $("#role_user_value").val();
		            		}
		            		
		            		return data;
		            	}
	            	},
	            	{ "data": "username" },
	            	{ "data": "firstname" },
	            	{ "data": "lastname" },
//		            { 
//		            	"data": "username",
//		            	"render": function(data, type, row, meta){
//		            		var data = "";
//		            		
////		            		if (row.username!='')
////		            			data += row.username;
//		            		
//		            		if (row.firstname!='' || row.lastname!=''){
////		            			data += " ( ";
//		            			data+=row.firstname;
//		            			
//		            			if (row.lastname!=''){
//			            			if (row.firstname!=''){
//			            				data += " ";
//			            			}
//			            			data += row.lastname;
//		            			}
//		            			
////		            			data += " ) ";
//		            		}
//		            		
//		            		return data;
//		            	}
//	            	},
		            { "data": "email"  },	
		            { "data": "musuario"  },
//		            { "data": "division_name" },
//		            { "data": "city_name" },
		            { 
		            	"data": "status", 
		            	"render": function(data, type, row, meta){
		            		var data="";
		            		
//		            		if (row.people_manager=='001')
//		            			data += "&nbsp;<span class='label'>" + $("#status_manager_msg").val()+ "</span>";
//
//		            		if (row.new_hire=='001')
//		            			data += "&nbsp;<span class='warning label'>" + $("#status_newhire_msg").val()+ "</span>";

		            		if (row.active=='1')
		            			data = "&nbsp;<span class='success label'>" + $("#status_active_msg").val()+ "</span>";
		            		else
		            			data ="&nbsp;<span class='label secondary'>"+$("#status_active_msg").val()+"</span>";
		            		
		            		return data;
		            	}
		            },
		            { 
		            	"data": "action", 
		            	"render": function(data, type, row, meta){
		            		var data="";
		            		
		            		data+='&nbsp;<a href="javascript:updateUser(\'edit\', \''+row.id+'\')"><i class="fa fa-pencil fa-lg"></i></a>';
	            			data+='&nbsp;<a href="javascript:update_user(\'delete\', \''+row.id+'\')"><i class="fa fa-times-circle fa-lg"></i></a>';
		            		
		            		return data;
		            	}
		            }
		            ],

	} );
	
   	$("#user_form").on("valid invalid submit", function(event) {
   		event.stopPropagation();
   		event.preventDefault();
   		
   		if (event.type=='valid'){
   			update_user($("#type").val(),'');
   		}
   	});
   	
   	$("#btnReset").click(function(){
//   		$("#user_division").val('all');
//   		$("#user_geographical").val('all');
//   		$("#user_promote").val('all');
//   		$("#user_jobgrade").val('all');
//   		$("#user_payowner").val('all');
   		
//   		$("#user_subdivision").html('');
   		$("#user_security").val('000');
   		
		$("#user_direccion").val('all');
		$("#user_empresa").val('all');
		$("#user_ciudad").val('all');
   		
//   		$("#user_city").html('');
   	});
   	
//   	$("#user_division").change(function(){
//   		getSubDivision('');
//   	});
//   	
//   	$("#user_geographical").change(function(){
//   		getCity('');
//   	});

});

