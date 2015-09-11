/**
 * Opportunities My Ads page. 
 */

$(document).foundation();

function publish(type, kind, id){
	$("#publish_type").val(type);
	$("#publish_kind").val(kind);
	$("#publish_id").val(id);
	$("#frmPublish").submit();
}

function update(kind, type, id){
	if (type=='edit'){
		$("#publish_type").val(type);
		$("#publish_kind").val(kind);
		$("#publish_id").val(id);
		$("#frmPublish").submit();
		
	}else{
		var t = confirm('¿Está usted seguro que desea eliminar este registro?');
		if (!t)
			return;

		$.ajax({
			type: "POST",
	        url: 'updatePublish.html',
	        data: {  
	        	publish_id: id,
	        	publish_kind: kind,
	        	publish_type : type
			},
	        dataType: 'json', 
	        success: function(data) {
	        	if (data.errCode==0){
	        		if (kind=='001'){
	        			$('#automovil').dataTable().api().ajax.reload();
	        		}
	        		
	        		if (kind=='002'){
	        			$('#inmueble').dataTable().api().ajax.reload();
	        		}
	        		
	        		if (kind=='002'){
	        			$('#varios').dataTable().api().ajax.reload();
	        		}
	        	}
	        },
	        error: function() {
	        }
	    });  		
	}
}

$(document).ready(function() {

	$('#automovil').dataTable( {
		"processing": false,
		"serverSide": true,
		"searching": false,
		"responsive": true,
		"info":false,
		"paging" : false,
        "pageLength": 100,
		"lengthChange": false,
		"ajax": {
            "url": "loadOpportunities.html",
            "type": "POST",
            "data":	{
            	'kind' : '001'
            }
        },		
        "language": {
            "emptyTable":     "No se encontraron registros",
            "paginate": {
                "first":        "Primero",
                "previous":     "Anterior",
                "next":         "Siguiente",
                "last":         "Último"
            },            
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
		"columns": [
		            { 
		            	"data": "name",
		            	"render": function(data, type, row, meta){
		            		var data = "";
	            			
		            		data = row.brand_name + ", " + row.model + ", " + row.today;
		            		
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
		            { "data": "mileage"  },	
		            { "data": "transmission_name" },
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
		            	"data": "action", 
		            	"render": function(data, type, row, meta){
		            		var data="";
		            		
		            		data+='&nbsp;<a href="javascript:update(\'001\', \'edit\', \''+row.id+'\')"><i class="fa fa-pencil fa-lg"></i></a>';
	            			data+='&nbsp;<a href="javascript:update(\'001\', \'delete\', \''+row.id+'\')"><i class="fa fa-times-circle fa-lg"></i></a>';
		            		
		            		return data;
		            	}
		            }
        ],
	} );
	
	$('#inmueble').dataTable( {
		"processing": false,
		"serverSide": true,
		"responsive": true,
		"searching": false,
		"info":false,
		"paging" : false,
        "pageLength": 100,
		"lengthChange": false,
		"ajax": {
            "url": "loadOpportunities.html",
            "type": "POST",
            "data":	{
            	'kind' : '002'
            }
        },		
//		"order": [[ 4, "desc" ]],
        "language": {
            "emptyTable":     "No se encontraron registros",
            "paginate": {
                "first":        "Primero",
                "previous":     "Anterior",
                "next":         "Siguiente",
                "last":         "Último"
            },            
        },        
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
		            { 
		            	"data": "action", 
		            	"render": function(data, type, row, meta){
		            		var data="";
		            		
		            		data+='&nbsp;<a href="javascript:update(\'002\', \'edit\', \''+row.id+'\')"><i class="fa fa-pencil fa-lg"></i></a>';
	            			data+='&nbsp;<a href="javascript:update(\'002\', \'delete\', \''+row.id+'\')"><i class="fa fa-times-circle fa-lg"></i></a>';
		            		
		            		return data;
		            	}
		            }
        ],
	} );	

	$('#varios').dataTable( {
		"processing": false,
		"serverSide": true,
		"responsive": true,
		"searching": false,
		"info":false,
		"paging" : false,
        "pageLength": 100,
		"lengthChange": false,
		"ajax": {
            "url": "loadOpportunities.html",
            "type": "POST",
            "data":	{
            	'kind' : '003'
            }
        },		
//		"order": [[ 4, "desc" ]],
        "language": {
            "emptyTable":     "No se encontraron registros",
            "paginate": {
                "first":        "Primero",
                "previous":     "Anterior",
                "next":         "Siguiente",
                "last":         "Último"
            },            
        },        
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
		            { "data": "varios_name"  },	
		            { "data": "model"  },	
		            { 
		            	"data": "price",
		            	"render": function(data, type, row, meta){
		            		var data = "";
		            		data = "$" + row.price;
		            		return data;
		            	}
	            	},
		            { 
		            	"data": "action", 
		            	"render": function(data, type, row, meta){
		            		var data="";
		            		
		            		data+='&nbsp;<a href="javascript:update(\'003\', \'edit\', \''+row.id+'\')"><i class="fa fa-pencil fa-lg"></i></a>';
	            			data+='&nbsp;<a href="javascript:update(\'003\', \'delete\', \''+row.id+'\')"><i class="fa fa-times-circle fa-lg"></i></a>';
		            		
		            		return data;
		            	}
		            }
        ],
	} );		
});

