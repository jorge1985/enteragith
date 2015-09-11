/**
 * Opportunities Inmueble Results page. 
 */

$(document).ready(function() {

	$('#inmueble').dataTable( {
		"processing": false,
		"serverSide": true,
		"responsive": true,
		"ajax": {
            "url": "loadOpportunitiesResults.html",
            "type": "POST",
            "data":	{
            	'kind' : '002',
            	'serve_type' : $("#inmueble_2").val(),
            	'state_id' : $("#inmueble_3").val(),
            	'city_id' : $("#inmueble_3_2").val(),
            }
        },		
//		"order": [[ 4, "desc" ]],
		"columnDefs": [ 
           {
   			"targets": "_all",//[ 1, -1 ],
			"orderable": false
			},
			{
				"targets": "_all",
				"searchable": false
			}
		],		
		"searching": false,
		"info":false,
        "pageLength": 100,
		"lengthChange": false,
        "language": {
            "emptyTable":     "No se encontraron registros",
            "paginate": {
                "first":        "Primero",
                "previous":     "Anterior",
                "next":         "Siguiente",
                "last":         "Último"
            },            
        },        
		"columns": [
		            { "data": "location",
		            	"render": function(data, type, row, meta){
		            		var data="";
		            		data = row.state_name;
		            		if (row.city_name!='')
		            			data += ", ";
		            		data += row.city_name;
		            		return data;
		            	}
	            	},
		            { 
		            	"data": "price",
		            	"render": function(data, type, row, meta){
		            		var data = "";
		            		data = "$" + row.price;
		            		return data;
		            	}
	            	},
		            { "data": "property_name"  },	
		            { "data": "model"  },	
		            { "data": "rooms"  },	
		            { "data": "plants"  },	
//		            { 
//		            	"data": "action", 
//		            	"render": function(data, type, row, meta){
//		            		var data="";
//		            		
//		            		data+='&nbsp;<a href="javascript:update(\'002\', \'edit\', \''+row.id+'\')"><i class="fa fa-pencil fa-lg"></i></a>';
//	            			data+='&nbsp;<a href="javascript:update(\'002\', \'delete\', \''+row.id+'\')"><i class="fa fa-times-circle fa-lg"></i></a>';
//		            		
//		            		return data;
//		            	}
//		            }
        ],
	} );

	$('#inmueble').on('click', 'tr', function(){
		var sTable = $("#inmueble").dataTable();
		var sData = sTable.fnGetData(this);
		view_detail(sData.id);
	});
});

