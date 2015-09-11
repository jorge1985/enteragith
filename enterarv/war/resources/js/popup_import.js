/**
 * Import csv page. 
 */

var validFilesTypes = "csv";

function insert(h, d, l, end){
	if (d!=0){
		$.ajax({
			type: "POST",
	        url: $("#basePath").val()+'system/insertUser.html',
	        data: {  
	        	header: h,
	        	data : d,
	        	length: l
			},
	        dataType: 'json', 
	        success: function(data) {
	    		if (end){
	    			closeImportModal();		
	    		}
	        },
	        error: function() {
	    		if (end){
	    			closeImportModal();		
	    		}
	        }
	    });
		
	}else{
		if (end){
			closeImportModal();		
		}
	}
	
}

$(document).on('closed.fndtn.reveal', '[data-reveal]', function () {
	
});

$(document).ready(function() {
	$('#takeFileUpload').fileupload({
		dataType: 'json',
		formData: { },
		beforeSend: function(){
//			$('div#upload_progressbar>span').css('width','0%');
			$("#import_progress").show();
		},
		done: function (e, data) {
			if (data.result.errCode==0){
				var header="";
				var c=0;
				var row2;
				var d=0;
				var l = parseInt(data.result.len);
				
//				$.ajax({
//					type: "POST",
//			        url: $("#basePath").val()+'system/insertUser.html',
//			        data: {  
////			        	header: h,
//			        	data : data.result.data,
//			        	length: data.result.len
//					},
//			        dataType: 'json', 
//			        success: function(data) {
//						$("#import_progress").hide();
//						showAlert(data.result.errMsg);
//						
//						closeImportModal();
//			        },
//			        error: function() {
//						closeImportModal();
//			        }
//			    });  		
				
				$.each(data.result.data, function(index, row){
					if (c==0)
						header=row;
					
					if (c!=0){
						if (d==0){
							row2=new Array();
						}
						
						row2[d]={	line: row	};
						d=d+1;
						if (d==100){
							setTimeout(insert(header,row2, d, false), 500);
							d=0;
						}
						
						l=l-1;
						if (l<=1){
							setTimeout(insert(header,row2, d, true), 500);
						}
					}
					
					c=c+1;
				});
				
			}else{
				alert($("#alert_invalid_format").val() + "\n\n" + "csv" );
				closeImportModal();
			}
		},
		progressall: function (e, data) {
//			var progress = parseInt(data.loaded / data.total * 100 , 10);
//			var percentVal = progress + '%'; 
//			$('div#upload_progressbar>span').css('width', percentVal);
		}    
	});		
	
	$("#btnUpload").click(function(){
		$('#takeFileUpload').trigger('click');
	});
	
});

