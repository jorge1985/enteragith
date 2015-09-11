/**
 * Active Content page. 
 */

function editContent(content_id, type){
	$("#edit_content_id").val(content_id);
	$("#edit_content_type").val(type);
	$("#form_move").submit();
}

function deleteContent(type, content_id){
	var t = confirm('Are you sure?');
	if (!t)
		return;
	
	$.ajax({
		url: 'deleteContent.html',
		type: "POST",
		data: {  type: type, content_id : content_id },
		dataType: 'json', 
		success: function(data) {
			showAlert(data.errMsg);
			if (data.errCode==0){
				$('#content_table').dataTable().api().ajax.reload();
			}
		},
		error: function() {
		}
	});  	
}


$(document).ready(function() {
	
	$('#active_content_table').dataTable( {
		"processing": false,
		"serverSide": true,
		"responsive": true,
		"searching": false,
		"ajax":	{
			"url": $("#basePath").val()+"content/loadActiveContent.html?all_search="+$("#global_all_search_value").val(),
			"type": "POST"
		},
		"order": [[ 3, "desc" ]],
        "language": {
            "emptyTable":     "No se encontraron contenidos",
            "paginate": {
                "first":        "Primero",
                "previous":     "Anterior",
                "next":         "Siguiente",
                "last":         "Último"
            },            
        },        
		"columnDefs": [ 
           {
			"targets": [ 2, -1, -2 ],
			"orderable": false
			},
			{
				"targets": "_all",
				"searchable": false
			}
		],		
		"columns": [
		            { "data": "name" },
		            { "data": "type_name" },
		            { "data": "channel"  },	
		            { "data": "validity" },
		            { 
		            	"data": "show_in",
		            	"render": function(data, type, row, meta){
		            		var data = "";
		            		
		            		if (row.active=='1')
		            			data += "&nbsp;<span class='success label'>" + $("#status_active_msg").val()+ "</span>";

		            		if (row.show_in=='001' || row.show_in=='003')
		            			data += "&nbsp;<span class='label'>" + $("#status_featured_msg").val()+ "</span>";

		            		if (row.status=='1')
		            			data += "&nbsp;<span class='label alert'>" + $("#status_deleted_msg").val()+ "</span>";

		            		if (data=="")
		            			data="&nbsp;<span class='label secondary'>"+$("#status_none_msg").val()+"</span>";
		            		
		            		return data;
		            	}
		            },
		            { 
		            	"data": "status", 
		            	"render": function(data, type, row, meta){
		            		var data="";
		            		
		            		data+='&nbsp;<a href="javascript:editContent(\''+row.id+'\',\''+row.type_name+'\')"><i class="fa fa-pencil fa-lg"></i></a>';
		            		
		            		if (row.status=='0')
		            			data+='&nbsp;<a href="javascript:deleteContent(\'remove\',\''+row.id+'\')"><i class="fa fa-times-circle fa-lg"></i></a>';
		            		else{
		            			data+='&nbsp;<a href="javascript:deleteContent(\'delete\',\''+row.id+'\')"><i class="fa fa-trash-o fa-lg"></i></a>';
		            			data+='&nbsp;<a href="javascript:deleteContent(\'reload\',\''+row.id+'\')"><i class="fa fa-history fa-lg"></i></a>';
		            		}
		            		
		            		return data;
		            	}
		            }
		            ],

	} );

	$('#expired_content_table').dataTable( {
		"processing": false,
		"serverSide": true,
		"responsive": true,
		"searching": false,
		"ajax":	{
			"url": $("#basePath").val()+"content/loadExpiredContent.html?all_search="+$("#global_all_search_value").val(),
			"type": "POST"
		},
		"order": [[ 3, "desc" ]],
        "language": {
            "emptyTable":     "No se encontraron contenidos",
            "paginate": {
                "first":        "Primero",
                "previous":     "Anterior",
                "next":         "Siguiente",
                "last":         "Último"
            },            
        },        
		"columnDefs": [ 
           {
			"targets": [ 2, -1, -2 ],
			"orderable": false
			},
			{
				"targets": "_all",
				"searchable": false
			}
		],		
		"columns": [
		            { "data": "name" },
		            { "data": "type_name" },
		            { "data": "channel"  },	
		            { "data": "validity" },
		            { 
		            	"data": "show_in",
		            	"render": function(data, type, row, meta){
		            		var data = "";
		            		
		            		if (row.active=='1')
		            			data += "&nbsp;<span class='success label'>" + $("#status_active_msg").val()+ "</span>";

		            		if (row.show_in=='001' || row.show_in=='003')
		            			data += "&nbsp;<span class='label'>" + $("#status_featured_msg").val()+ "</span>";

		            		if (row.status=='1')
		            			data += "&nbsp;<span class='label alert'>" + $("#status_deleted_msg").val()+ "</span>";

		            		if (data=="")
		            			data="&nbsp;<span class='label secondary'>"+$("#status_none_msg").val()+"</span>";
		            		
		            		return data;
		            	}
		            },
		            { 
		            	"data": "status", 
		            	"render": function(data, type, row, meta){
		            		var data="";
		            		
		            		data+='&nbsp;<a href="javascript:editContent(\''+row.id+'\',\''+row.type_name+'\')"><i class="fa fa-pencil fa-lg"></i></a>';
		            		
		            		if (row.status=='0')
		            			data+='&nbsp;<a href="javascript:deleteContent(\'remove\',\''+row.id+'\')"><i class="fa fa-times-circle fa-lg"></i></a>';
		            		else{
		            			data+='&nbsp;<a href="javascript:deleteContent(\'delete\',\''+row.id+'\')"><i class="fa fa-trash-o fa-lg"></i></a>';
		            			data+='&nbsp;<a href="javascript:deleteContent(\'reload\',\''+row.id+'\')"><i class="fa fa-history fa-lg"></i></a>';
		            		}
		            		
		            		return data;
		            	}
		            }
		            ],

	} );
	
	$('#recycle_content_table').dataTable( {
		"processing": false,
		"serverSide": true,
		"searching": false,
		"responsive": true,
		"ajax":	{
			"url": $("#basePath").val()+"content/loadRecycleContent.html?all_search="+$("#global_all_search_value").val(),
			"type": "POST"
		},
		"order": [[ 3, "desc" ]],
        "language": {
            "emptyTable":     "No se encontraron contenidos",
            "paginate": {
                "first":        "Primero",
                "previous":     "Anterior",
                "next":         "Siguiente",
                "last":         "Último"
            },            
        },        
		"columnDefs": [ 
           {
			"targets": [ 2, -1, -2 ],
			"orderable": false
			},
			{
				"targets": "_all",
				"searchable": false
			}
		],		
		"columns": [
		            { "data": "name" },
		            { "data": "type_name" },
		            { "data": "channel"  },	
		            { "data": "validity" },
		            { 
		            	"data": "show_in",
		            	"render": function(data, type, row, meta){
		            		var data = "";
		            		
		            		if (row.active=='1')
		            			data += "&nbsp;<span class='success label'>" + $("#status_active_msg").val()+ "</span>";

		            		if (row.show_in=='001' || row.show_in=='003')
		            			data += "&nbsp;<span class='label'>" + $("#status_featured_msg").val()+ "</span>";

		            		if (row.status=='1')
		            			data += "&nbsp;<span class='label alert'>" + $("#status_deleted_msg").val()+ "</span>";

		            		if (data=="")
		            			data="&nbsp;<span class='label secondary'>"+$("#status_none_msg").val()+"</span>";
		            		
		            		return data;
		            	}
		            },
		            { 
		            	"data": "status", 
		            	"render": function(data, type, row, meta){
		            		var data="";
		            		
		            		data+='&nbsp;<a href="javascript:editContent(\''+row.id+'\',\''+row.type_name+'\')"><i class="fa fa-pencil fa-lg"></i></a>';
		            		
		            		if (row.status=='0')
		            			data+='&nbsp;<a href="javascript:deleteContent(\'remove\',\''+row.id+'\')"><i class="fa fa-times-circle fa-lg"></i></a>';
		            		else{
		            			data+='&nbsp;<a href="javascript:deleteContent(\'delete\',\''+row.id+'\')"><i class="fa fa-trash-o fa-lg"></i></a>';
		            			data+='&nbsp;<a href="javascript:deleteContent(\'reload\',\''+row.id+'\')"><i class="fa fa-history fa-lg"></i></a>';
		            		}
		            		
		            		return data;
		            	}
		            }
		            ],

	} );
		
});
