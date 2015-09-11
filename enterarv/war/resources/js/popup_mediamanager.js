/**
 * Media Manager page. 
 */

var folder_id='0';
var file_id='';
var panel="";

function getList(id){
   	$.ajax({
        url: $('#basePath1').val()+'media/' + 'getMedia.html',
        data: { folder_id: id },
        dataType: 'json', 
        success: function(data) {
        	$("#folder"+id).html('');
        	$("#filelist").html('');
        	if (data.errCode==0){
        		$("#explorer a").removeClass('active');
        		$("#parent_folder"+id).addClass('active');

        		folder_id = id;
        		showFolder('folder'+id, data.folder);
        		showFile(data.file);
        	}
        },
        error: function() {
        	$("#folder"+id).html('');
        	$("#filelist").html('');
        }
    });
}

function showFolder(subid, data){
	file_id='';
	
	$.each(data, function(index, row){
		$("#"+subid).append(
					'<li>' +
					'<a href="javascript:getList(\'' + row.id + '\')" id="parent_folder'+row.id+'">' + 
//					'<img id="folder'+row.id+'_progress" class="folder_progress" src="'+$("#resPath").val()+'images/progress.gif">' + 
					'<i class="fa fa-folder fa-2x">&nbsp;' + row.name + '</i></a>' +
					'<ul id="folder' + row.id + '"></ul>' + 
					'</li>'
		);
	});
}

function showFile(data){
	file_id="";
	$("#table_container").html(panel);

	$.each(data, function(index, row){
		$("#filelist").append(
				'<tr class="file_row" file_id="' + row.id + '">' + 
				'<td>' + row.name +'</td>' +
				'<td>' + row.size +'</td>' +
				'<td>' + row.create_time +'</td>' +
				'</tr>'
		);
	});
	
	var table = $('#media_table').DataTable();
	
	$('#media_table tbody').on('click', 'tr', function(){
		file_id = $(this).attr('file_id');
		
		if ($("#selection_method").val()=='single'){
			$('#media_table tbody tr').removeClass('active');
			$(this).addClass('active');
		}
		
		if ($("#selection_method").val()=='multiple'){
			$(this).toggleClass('active');
		}
	});
	
	$('#media_table tbody').on('dblclick', 'tr', function(){
		downloadFile($(this).attr('file_id'));
	});
}

function closeModal(){
	$('#mediaManagerModal').foundation('reveal', 'close');
}

function updateFolder(type){
	if (folder_id=='0' && (type=='edit' || type=='delete')){
		return;
	}

	if (folder_id!=''){
		var v = '';
		if (type=='add' || type=='edit'){
			v = prompt("Input Folder Name..", "New Folder");
			if (!v)
				return;
		}else{
			var t = confirm('Are you sure to delete this?');
			if (!t)
				return;
		}
		
	   	$.ajax({
	        url: $('#basePath1').val()+'media/' + 'updateFolder.html',
	        data: { type: type, folder_id: folder_id, name: v },
	        dataType: 'json', 
	        success: function(data) {
	        	if (data.errCode==0){
		        	var fid=data.FolderId;
		        	folder_id = fid;
		        	$("#folder"+fid).html('');
		        	$("#filelist").html('');
	        		showFolder('folder'+fid, data.folder);
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

function updateFile(type){
	if ((type=='edit' || type=='delete') && file_id=='')
		return;

	if (folder_id!=''){
		var v = '';
		if (type=='edit'){
			v = prompt("Input File Name..", "New File");
			if (!v)
				return;
		}if (type=='delete'){
			var t = confirm('Are you sure to delete this?');
			if (!t)
				return;
		}
		
	   	$.ajax({
	        url: $('#basePath1').val()+'media/' + 'updateFile.html',
	        data: { type: type, folder_id: folder_id, file_id: file_id, name: v },
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

function uploadFile(){
	$.pgwModal({
		url: $('#resPath1').val()+'popup_upload.jsp?param1='+folder_id,
		loadingContent: '<span style="text-align:center">Loading.....</span>',
	    target: '#uploadFileModal',
	    title: 'Select file',
	    maxWidth: '30%'
	});	
}

function select(){
	if (file_id!=''){
		if  ($("#selection_method").val()=='single'){
		   	$.ajax({
		        url: $('#basePath1').val()+'media/' + 'getFile.html',
		        data: { file_id: file_id },
		        dataType: 'json', 
		        success: function(data) {
		        	if (data.errCode==0){
		        		var type = $("#selection_type").val();
		        		var page=$("#selection_page").val();
		        		
		        		if (type=='image'){
		                    if (data.file.type!="1" ){
		                     alert($("#alert_select_image").val());
		                     return;
		                    }

		        			var cloud_key=data.file.cloud_key;
		        			var file_name=data.file.name;
		        			
		        			if (page=='content_editor'){
		        				setEmbedDataToEditor(type, data.file.name, data.file.cloud_key);
		        				closeModal();
		        				return;
		        			}
		        			
		        			if (page=='banner'){
		        				setBannerData($("#selection_detail").val(), cloud_key);
		        				closeModal();
		        				return;
		        			}
		        			
			        	   	$.ajax({
			        	        url: $('#basePath1').val()+'image',
			        	        data: { 'blob-key': data.file.cloud_key },
			        	        dataType: 'json', 
			        	        success: function(data) {
			        	        	if (page=='features'){
			        	        		setFeaturesData(cloud_key, data.key);
			        	        	}

			        	        	if (page=='content_gallery'){
				        				addGalleryData(cloud_key, file_name, data.key);
				        			}
				        			
				        			if (page=='content_news'){
				        				setContentNewsData(cloud_key, data.key);
				        			}
				        			
				        			if (page=='content_video'){
				        				setContentVideo_ImageData(cloud_key, data.key);
				        			}
			        	        },
			        	        error: function() {
			        	        }
			        	    });
			        	   	
		        		}
		        		
		        		if (type=='video'){
		        			
		        			//if (data.file.type!="application/x-vlc-plugin" && data.file.type!="video/x-msvideo" && data.file.type!="video/x-flv" && data.file.type!="video/x-m4v" && data.file.type!="video/x-f4v" && data.file.type!="video/mp4" && data.file.type!="application/mp4" && data.file.type!="video/quicktime"){
		        			if (data.file.type!="3"){	
		        				alert($("#alert_select_image").val());
		        				return;
		        			}
		        			
		        			if (page=='content_video'){
		        				setContentVideo_VideoData(data.file.cloud_key);
		        			}
		        			
		        			if (page=='content_editor'){
		        				setEmbedDataToEditor(type, data.file.name, data.file.cloud_key);
		        			}
		        		}
		        		
		        		if (type=='all'){
		        			if (page=='content_file'){
		        				addFileData(data.file.cloud_key, data.file.name);
		        			}

		        			if (page=='content_editor'){
		        				setEmbedDataToEditor(type, data.file.name, data.file.cloud_key);
		        			}
		        		}
		        		
		        	   	closeModal();
		        	}else{
		        	}
		        },
		        error: function() {
		        }
		    });
		}
		
		if ($("#selection_method").val()=='multiple'){
    		var type = $("#selection_type").val();
    		var page=$("#selection_page").val();
    		
    		$("#media_table tbody tr.active").each(function(index, row){
    			var f_id = $(this).attr('file_id');
    			
    		   	$.ajax({
    		        url: $('#basePath1').val()+'media/' + 'getFile.html',
    		        data: { file_id: f_id },
    		        dataType: 'json', 
    		        success: function(data) {
    		        	if (data.errCode==0){

		        			var cloud_key=data.file.cloud_key;
		        			var file_name=data.file.name;
    		        		
    		        		if (type=='image'){
    		   //     			if (data.file.type!="" && data.file.type!="image/png" && data.file.type!="image/gif"){
    		         			if (data.file.type!="1"){
    		        				return;
    		        			}

    			        	   	$.ajax({
    			        	        url: $('#basePath1').val()+'image',
    			        	        data: { 'blob-key': data.file.cloud_key },
    			        	        dataType: 'json', 
    			        	        success: function(data) {
    			        	        	
    			        	        	if (page=='content_gallery'){
    				        				addGalleryData(cloud_key, file_name, data.key);
    				        			}
    			        	        },
    			        	        error: function() {
    			        	        }
    			        	    });
    			        	   	
    		        		}
    		        		
    		        		if (type=='all'){
    		        			if (page=='content_file'){
    		        				addFileData(cloud_key, file_name);
    		        			}
    		        		}
    		        		
    		        	}else{
    		        	}
    		        },
    		        error: function() {
    		        }
    		    });
    			
    		});
    		
			closeModal();
		}
	}
}

function downloadFile(id){
	if (id!=''){
	   	$.ajax({
	        url: $('#basePath1').val()+'media/' + 'getFile.html',
	        data: { file_id: id },
	        dataType: 'json', 
	        success: function(data) {
	        	if (data.errCode==0){
	        		
	        		var content = data.file.type;
	        		//Refer to http://fancyapps.com/fancybox/#license.
	        		if (content=="1"){
	        			$.fancybox.open(  {
	        				type: 'image',
	        				href:  $("#basePath").val() + "serve?blob-key="+data.file.cloud_key
	        			} , {
	                		autoSize	: true,
	                		openEffect	: 'elastic',
	                		closeEffect	: 'fade' 
            			}  );
	        			
	        		} else if (content=="2" || content=="3" || content=="4" || content=="5")
	        		{
	        			$.fancybox.open(  {
	        				type: 'iframe',
	        				href:  $("#basePath").val() + "serve?blob-key="+data.file.cloud_key
	        			} , {
	                		autoSize	: true,
	                		openEffect	: 'elastic',
	                		closeEffect	: 'fade' 
            			}  );

	        		} else {
	                    $.fileDownload($("#basePath").val() + "serve?blob-key="+data.file.cloud_key+"&name="+data.file.name);
	        		}
	        		
	        	}
	        },
	        error: function() {
	        }
	    });
	}
}

$(document).ready(function() {
	panel = $("#table_container").html();
	getList('0');
	
//	$("#filelist").click(function(){
//		file_id='';
//		$("#filelist tr").removeClass('active');
//		if ($("tr.file_row :hover").length>0){
//			var item = $("tr.file_row :hover").parent('tr');
//			if (item){
//				file_id = $(item).attr('file_id');
//				$(item).addClass('active');
//			}
//		}
//	});
//
//	$("#filelist").dblclick(function(){
//		if ($("tr.file_row :hover").length>0){
//			var item = $("tr.file_row :hover").parent('tr');
//			if (item){
//				downloadFile($(item).attr('file_id'));
//			}
//		}
//	});
	
});

