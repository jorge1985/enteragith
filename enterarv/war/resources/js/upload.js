/**
 * Media Manager-Upload page. 
 */

var validFilesTypes = "zip, tgz, far, jpg, jpeg, png, gif, pdf, doc, docx, xls, xlsx, ppt, pptx, txt, rtf, mp4, m4v, mov, avi, flv, f4v";

function addFile(name, key, size, kind, thumb){
	if (folder_id!=''){
	   	$.ajax({
	        url: 'updateFile.html',
	        data: { type: 'add', folder_id: $('#folder_selected_id').val(), file_id: '', name: name, key: key, size:size, kind:kind, thumb: thumb },
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

function closeModal(){
	$('#uploadFileModal').foundation('reveal', 'close');
}

//function CheckExtension(file) {
//    /*global document: false */
//    var filePath = file.value;
//    var ext = filePath.substring(filePath.lastIndexOf('.') + 1).toLowerCase();
//    var isValidFile = false;
//
//    for (var i = 0; i < validFilesTypes.length; i++) {
//        if (ext == validFilesTypes[i]) {
//            isValidFile = true;
//            break;
//        }
//    }
//
//    if (!isValidFile) {
//        file.value = null;
//        alert("Invalid File. Valid extensions are:\n\n" + validFilesTypes.join(", "));
//    }
//
//    return isValidFile;
//}

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
				addFile(data.result.name, data.result.key, data.result.size, data.result.type, data.result.thumbnail);
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

