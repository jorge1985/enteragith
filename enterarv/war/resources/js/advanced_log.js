/**
 * Advanced Log page. 
 */

function search(){
	var start = $("#log_date_start").val();
	var end = $("#log_date_end").val();
	
	if (start!='' && end!='' && start>end){
		alert( $("#validate_date_msg").val() );
		return;
	}
	
	var exact = $("#log_option_exact").prop('checked') ? '1' : '0'; 
	var option = "1";
	if ($("#log_option_user").prop('checked'))
		option="2";
	if ($("#log_option_channel").prop('checked'))
		option="3";
	if ($("#log_option_content").prop('checked'))
		option="4";
	
	$('#log_table').dataTable().api().ajax.url('loadAdvancedLog.html?start_day='+start+"&end_day="+end+"&search="+$("#log_search_text").val()+"&exact="+exact+"&option="+option).load();
}

function export_csv(){
	var start = $("#log_date_start").val();
	var end = $("#log_date_end").val();
	
	if (start!='' && end!='' && start>end){
		alert( $("#validate_date_msg").val() );
		return;
	}

	var exact = $("#log_option_exact").prop('checked') ? '1' : '0'; 
	var option = "1";
	if ($("#log_option_user").prop('checked'))
		option="2";
	if ($("#log_option_channel").prop('checked'))
		option="3";
	if ($("#log_option_content").prop('checked'))
		option="4";
	
	$.fileDownload($("#basePath").val()+"export?type=advanced&start_day=" + start + "&end_day=" + end+"&search="+$("#log_search_text").val()+"&exact="+exact+"&option="+option);
}

$(document).ready(function() {
	$('.fdatepicker').fdatepicker();
	$("#log_date_start").val(getToday());
	$("#log_date_end").val(getToday());
	
	$("input[type=radio]").click(function(){
		var column='';
		if ($("#log_option_channel").prop('checked'))
			column=$("#table_column_channel").val();
		if ($("#log_option_content").prop('checked'))
			column=$("#table_column_content").val();
		
		$("#log_table_detail_column").html(column);
	});
	
	$('#log_table').dataTable( {
		"processing": false,
		"serverSide": true,
		"searching": false,
		"responsive": true,
		"ajax":	{
			"url": "loadAdvancedLog.html?start_day="+getToday()+"&end_day="+getToday()+"&option=1&exact=0",
			"type": "POST",
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
        	   "targets": [ 0, -1 ],
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
            { "data": "access_name" }
        ]
	} );
	
//	search();
});
