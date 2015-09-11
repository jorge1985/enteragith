/**
 * RecycleBin Content page. 
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

function previewContent(id, blog){
	$("#edit2_content_id").val(id);
	$("#edit2_content_blog").val(blog);
	$("#form_move_content").submit();
}

$(document).ready(function() {

	$('#content_table').dataTable( {
		"processing": false,
		"responsive": true,
		"serverSide": true,
		"ajax":	{
			"url": "loadRecycleContent.html",
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
   			"targets": [ 2, 3, -1, -2 ],
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
		            { "data": "tag" },
		            { "data": "channel"  },	
		            { "data": "validity" },
		            { 
		            	"data": "show_in",
		            	"render": function(data, type, row, meta){
		            		var data = "";
		            		
		            		if (row.active=='1')
		            			data += "&nbsp;<span class='success label'>" + $("#status_active_msg").val()+ "</span>";

		            		if (row.show_in=='1' || row.show_in=='3')
		            			data += "&nbsp;<span class='label'>" + $("#status_featured_msg").val()+ "</span>";

		            		if (row.status=='D')
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
		            		
//		            		if (row.type=='003' || row.type=='004' || row.type=='005'){
	            			data += '&nbsp;<a href="javascript:previewContent(\''+row.id+'\', \'' + row.blog +'\')"><i class="fa fi-magnifying-glass fa-lg"></i></a>';
//		            		}

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

