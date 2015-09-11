/**
 * Opportunities Upload page. 
 */

var validFilesTypes = "jpg, jpeg, png, gif";

function closeModal(){
	$('#uploadModal').foundation('reveal', 'close');
}

$(document).ready(function() {
	$('#takeFileUpload').fileupload({
		dataType: 'json',
		formData: {
		},
		beforeSend: function(){
			$('div#upload_progressbar>span').css('width','0%');
		},		
		done: function (e, data) {
			if (data.result.key!=''){
    			
				if (data.result.type!="image/jpeg" && data.result.type!="image/png" && data.result.type!="image/gif"){
    				alert($("#alert_invalid_format").val() + "\n\n" + validFilesTypes );
    			}else{
    				setOpportFile(data.result.name, data.result.key);
    			}
			}else{
				alert($("#alert_invalid_format").val() + "\n\n" + validFilesTypes );
			}
			
			closeModal();
		},
		progressall: function (e, data) {
			var progress = parseInt(data.loaded / data.total * 100 , 10);
			var percentVal = progress + '%'; 
			$('div#upload_progressbar>span').css('width', percentVal);
		}    
	});		
	
	$("#btnUpload").click(function(){
		$('#takeFileUpload').trigger('click');
	});
	
});

