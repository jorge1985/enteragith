/**
 * Standalone Upload page. 
 */

var validFilesTypes = "zip, tgz, far, jpg, jpeg, png, gif, pdf, doc, docx, xls, xlsx, ppt, pptx, txt, rtf, mp4, m4v, mov, avi, flv, f4v";

function addFile(name, key, size, kind, thumb){
	var folder_id=$("#folder_selected_id").val();
	if (folder_id!=''){
	   	$.ajax({
	        url: $("#basePath1").val()+'media/updateFile.html',
	        data: { type: 'add', folder_id: folder_id, file_id: '', name: name, key: key, size:size, kind:kind, thumb: thumb },
	        dataType: 'json', 
	        success: function(data) {
	        	if (data.errCode==0){
		        	$("#folder"+folder_id).html('');
		        	$("#filelist").html('');
	        		showFolder('folder'+folder_id, data.folder);
	        		showFile(data.file);
	        	}
	        },
	        error: function() {
	        	$("#folder"+id).html('');
	        	$("#filelist").html('');
	        }
	    });
	}
}

$(document).ready(function() {
	if ($("#selection_type").val()=='image'){
		$("#takeFileUpload").attr('accept', '.jpg,.jpeg,.png,.gif');
	} else if ($("#selection_type").val()=='video'){
			$("#takeFileUpload").attr('accept', '.mp4,.avi,.m4v,.flv,.mov,.f4v');
	}else{
		$("#takeFileUpload").attr('accept', '.pdf,.zip,.tgz,.far,.jpg,.jpeg,.png,.gif,.doc,.docx,.xls,.xlsx,.ppt,.pptx,.txt,.rtf,.mp4,.avi,.m4v,.flv,.mov,.f4v');
	}
	
	$('#takeFileUpload').fileupload({
		dataType: 'json',
		formData: {
		},
		beforeSend: function(){
			$('div#upload_progressbar>span').css('width','0%');
		},
		done: function (e, data) {
			if (data.result.key!=''){
				addFile(data.result.name, data.result.key, data.result.size, data.result.type, data.result.thumbnail);
			}else{
				alert($("#alert_invalid_format").val() + "\n\n" + validFilesTypes );
			}
			
			$.pgwModal('close');
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

