/**
 * Content Log page. 
 */

function search(){
	var start = $("#log_date_start").val();
	var end = $("#log_date_end").val();
	
	if (start!='' && end!='' && start>end){
		alert( $("#validate_date_msg").val() );
		return;
	}

	$('#log_table').dataTable().api().ajax.url('loadContentLog.html?start_day='+start+"&end_day="+end).load();
}

function export_csv(){
	var start = $("#log_date_start").val();
	var end = $("#log_date_end").val();
	
	if (start!='' && end!='' && start>end){
		alert( $("#validate_date_msg").val() );
		return;
	}

	$.fileDownload($("#basePath").val()+"export?type=content&start_day=" + start + "&end_day=" + end);
}

$(document).ready(function() {
	$('.fdatepicker').fdatepicker();
	$("#log_date_start").val(getToday());
	$("#log_date_end").val(getToday());
	
	$('#log_table').dataTable( {
		"processing": false,
		"serverSide": true,
		"responsive": true,
		"searching": false,
		"ajax":	{
			"url": "loadContentLog.html?start_day="+getToday()+"&end_day="+getToday(),
			"type": "POST"
		},
		"order": [[ 1, "desc" ]],
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
        	   "targets": [ 0 ],
        	   "orderable": false
			},
			{
				"targets": "_all",
				"searchable": false
			}
		],		
		"columns": [
            { "data": "username" },
            { 
            	"data": "date", 
            	"render": function(data, type, row, meta){
            		return data;//.substring(0,4)+"-"+data.substring(4,6)+"-"+data.substring(6,8)+" "+data.substring(8,10)+":"+data.substring(10,12)+":"+data.substring(12,14);
            	}
        	},
            { 
            	"data": "ip_addr",
            	"render": function(data, type, row, meta){
            		return data;
            	}
            },
            { "data": "access_comment" }
        ]
	} );
	
//	search();
});