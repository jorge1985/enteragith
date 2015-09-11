/**
 * Family page. 
 */

function showData(data){
	$("#families-tree").html('');
	$.each(data.list, function(index, row){
		$("#families-tree").append(
			$("<li pos='"+row.id+"'>").append(
				$("<div>").html(
						'<div class="actions-wrapper">' + 
						'<a href="javascript:edit_family(\'edit\', \'' + row.id + '\')"><i class="fa fa-pencil fa-lg edit"></i></a>' +
						'<a href="javascript:update_family(\'delete\', \'' + row.id + '\', \'\')"><i class="fa fa-times-circle fa-lg delete"></i></a>' +
						'</div>' + row.name
				)
			)
		);
	});
}

function edit_family(type, id){
   	$.ajax({
        url: 'updateFamily.html',
        type: "POST",
        data: {  type: 'get', family_id: id  },
        dataType: 'json', 
        success: function(data) {
        	if (data.errCode==0){
        		$("#type").val(type);
        		$("#family_id").val(id);
        		$("#family").val(data.family.name);
        		
        		if (data.family.visible=='1'){
        			$("#visibleYes").prop('checked', true);
        		}else{
        			$("#visibleNo").prop('checked', true);
        		}
        	}
        },
        error: function() {
        }
    });  	
	
}

function init(){
	$("#type").val('add');
	$("#family_id").val('');
	$("#btnReset").trigger('click');
//	$("#family").val('');
}

function update_family(type, id, name){
	if (type=='delete'){
		var t = confirm('Are you sure to delete this family?');
		if (!t)
			return;
	}else if (type=='edit'){
//		name = prompt('Edit Family', name);
		name = $("#family").val();
		id = $("#family_id").val();
	}else if (type=='add'){
		name = $("#family").val();
	}else{
		return;
	}	
	
   	$.ajax({
        url: 'updateFamily.html',
        type: "POST",
        data: {  type: type, family_id: id, family_name: name, visible: $("#visibleYes").prop('checked') ? '1': '2'  },
        dataType: 'json', 
        success: function(data) {
    		showAlert(data.errMsg);
    		init();
        	if (data.errCode==0){
        		showData(data);
        	}
        },
        error: function() {
        	init();
        }
    });  	
}

function saveOrder(){
	var pos = "";
	$("#families-tree li").each(function(index, row){
		if (pos!='')
			pos += ",";
		pos += $(row).attr('pos');
	});
	
   	$.ajax({
        url: 'updatePosition.html',
        type: "POST",
        data: {  type: 'family', position: pos},
        dataType: 'json', 
        success: function(data) {
        	showAlert(data.errMsg);
        },
        error: function() {
        }
    });  	
}

$(document).ready(function() {

    $('#families-tree').nestedSortable({
        forcePlaceholderSize: true,
			handle: 'div',
			helper:	'clone',
			items: 'li',
			opacity: .6,
			placeholder: 'placeholder',
			revert: 250,
			tabSize: 25,
			tolerance: 'pointer',
			toleranceElement: '> div',
			maxLevels: 1,
			isTree: true,
			expandOnHover: 700,
			startCollapsed: true,
			stop: function()	{	saveOrder();	}
    });
    
	showLoading();
	
   	$.ajax({
        url: 'loadFamilies.html',
        data: {  },
        dataType: 'json', 
        success: function(data) {
        	hideLoading();
        	if (data.errCode==0){
        		showData(data);
        	}
        },
        error: function() {
        	hideLoading();
        }
    });

   	$("#family_form").on("valid invalid submit", function(event) {
   		event.stopPropagation();
   		event.preventDefault();
   		
   		if (event.type=='valid'){
   			update_family($("#type").val(),'','');
   		}
   	});
   	
   	$("#btnReset").click(function(){
   		$("#type").val('add');
   	});
   	
});

