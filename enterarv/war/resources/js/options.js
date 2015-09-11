/**
 * Options page. 
 */

function showData(data){
	if (data.online=='001'){
		$("#onlineYes").prop('checked', true);
	}else{
		$("#onlineNo").prop('checked', true);
	}
	
	$('#allow_ips').html('');
	$.each(data.ip, function(index, row){
		$('#allow_ips').append('<option value="'+row.value+'">'+row.value+'</option>')
	});

	$('#allow_hosts').html('');
	$.each(data.host, function(index, row){
		$('#allow_hosts').append('<option value="'+row.value+'">'+row.value+'</option>')
	});
}

function add(type){
	console.log(type);
	
	if (type=='ip'){
		var v = $('#allow_ip').val();
		if (v=='')
			return;
		
		if ($("#allow_ips option[value='"+v+"']").length>0)
			return;

		$('#allow_ips').append('<option value="'+v+'">'+v+'</option>')
	}
	
	if (type=='host'){
		var v = $('#allow_host').val();
		if (v=='')
			return;
		
		if ($("#allow_hosts option[value='"+v+"']").length>0)
			return;

		$('#allow_hosts').append('<option value="'+v+'">'+v+'</option>')
	}
}

function update(){
	var ip="";
	$("#allow_ips option").each(function(index, row){
		if (ip!='')
			ip+=",";
		ip+=row.value;
	});

	var host="";
	$("#allow_hosts option").each(function(index, row){
		if (host!='')
			host+=",";
		host+=row.value;
	});
	
   	$.ajax({
        url: 'saveOptions.html',
        type: "POST",
        data: {  
        	online: $("#onlineYes").prop('checked') ? '001' : '002',
			hosts : host, ips: ip
        },
        dataType: 'json', 
        success: function(data) {
    		showAlert(data.errMsg);
        },
        error: function() {
        }
    });  	
}


$(document).ready(function() {
    
	showLoading();
	
   	$.ajax({
        url: 'loadOptions.html',
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

   	$('#allow_ips').dblclick(function(){
   		$('#allow_ips option:selected').remove();
   	});

   	$('#allow_hosts').dblclick(function(){
   		$('#allow_hosts option:selected').remove();
   	});
   	
   	$('#btnSubmit').click(function(){
   		update();
   	});
   	
});

