/**
 * Featured_News page. 
 */

function selectFromEditor(kind){
	$("#features_editor").myeditor().saveSelection();
	
	$('#mediaManagerModal').foundation('reveal', 'open', {
	    close_on_background_click: false,
	    url: $('#resPath').val()+'popup_mediamanager.jsp',	
	    data: { page: 'content_editor',	type: kind  , select: 'single'  },
	    success: function(data) {
	    },
	    error: function() {
	    }	    
	});
}

function setEmbedDataToEditor(kind, name, key){
	key = $("#basePath").val()+"serve?blob-key="+key;

	if (kind=="image"){
		$("#features_editor").myeditor().insertImage(name, key);
	}
	if (kind=="video"){
		$("#features_editor").myeditor().insertVideo(name, key);
	}
	if (kind=="all"){
		$("#features_editor").myeditor().insertFile(name, key);
	}
	
	if (kind=="forum"){
		$("#features_editor").myeditor().insertForum(name, key);
	}
}

function addForum(){
	var url = $("#media_forum_url").val();
	var width = $("#media_forum_width").val();
	var height = $("#media_forum_height").val();
	if (url==''){
		alert($("#validate_url_msg").val());
		return;
	}
	
	if (width=='' || (width.indexOf("%")==-1 && width.indexOf("px")==-1) ){
		alert($("#validate_length_msg").val());
		return;
	}
	
	if (height=='' || (height.indexOf("%")==-1 && height.indexOf("px")==-1) ){
		alert($("#validate_length_msg").val());
		return;
	}

	var st = "width:" + width+ "; height:"+ height;
	setEmbedDataToEditor('forum', url, st);
	$('#forumModal').foundation('reveal', 'close');
}

function selectForum(){
	$("#features_editor").myeditor().saveSelection();
	
	$('#forumModal').foundation('reveal', 'open', {
	    close_on_background_click: false,
	    success: function(data) {
	    },
	    error: function() {
	    }	    
	});
}

function createEditor(id) {
	$('#'+id).myeditor({
		focus: true,
		toolbarExternal: '#features_editor_toolbar'
	});

	$("#features_editor_toolbar>ul").append(
			'<li class="myeditor_separator"></li>'+
			'<li><a href="javascript:selectFromEditor(\'image\')" title="Image" class="myeditor_btn_image"></a></li>' +
			'<li><a href="javascript:selectFromEditor(\'video\')" title="Video" class="myeditor_btn_video"></a></li>' +
			'<li><a href="javascript:selectFromEditor(\'all\')" title="File" class="myeditor_btn_file"></a></li>' +
			'<li class="myeditor_separator"></li>' +
			'<li><a href="javascript:selectForum()" title="Forum" class="myeditor_btn_forum"></a></li>' +
			'<li class="myeditor_separator"></li>'
		);
}

function update(){
	
	var features_id = $("#features_template").val();
	if (features_id=='' || features_id=='0'){
		alert($("#alert_select_template").val());
		return;
	}
	
	var sub_features_id = '1';
	
	if (features_id=='5'){
		if ($("div.features_window.setting").length==0){
			alert($("#alert_select_window").val());
			return;
		}
		
		sub_features_id = $("div.features_window.setting").attr('features');
	}
	
   	$.ajax({
        url: 'saveFeatures.html',
        type: "POST",
        data: {  
        	features : features_id,
			sub_features : sub_features_id,
			title : $('#features_title').val(),
			content_id: $('#features_title').attr('content'),
			date : $('#features_date').val(),
			image : $('#features_image').attr('path'),
			content : $('#features_editor').myeditor().getCode()
        },
        dataType: 'json', 
        success: function(data) {
        	showAlert(data.errMsg);
        	if (data.errCode==0){
        		if (features_id=='5'){
    				$("#features"+features_id+"_"+sub_features_id+"_image").attr('src', $("#features_image").attr('src'));
    				$("#features"+features_id+"_"+sub_features_id+"_image").attr('path', $("#features_image").attr('path'));
    				$("#features"+features_id+"_"+sub_features_id+"_title").val( $("#features_title").val() );
    				$("#features"+features_id+"_"+sub_features_id+"_title").attr('content', $("#features_title").attr('content') );
    				$("#features"+features_id+"_"+sub_features_id+"_date").val( $("#features_date").val() );
    				$("#features"+features_id+"_"+sub_features_id+"_editor").html( $('#features_editor').myeditor().getCode() );	
        		}else{
        			$("#features"+features_id+"_image").attr('src', $("#features_image").attr('src'));
        			$("#features"+features_id+"_image").attr('path', $("#features_image").attr('path'));
        			$("#features"+features_id+"_title").val( $("#features_title").val() );
        			$("#features"+features_id+"_title").attr('content', $("#features_title").attr('content') );
        			$("#features"+features_id+"_date").val( $("#features_date").val() );
        			$("#features"+features_id+"_editor").html( $('#features_editor').myeditor().getCode() );	
        		}
        	}
        },
        error: function() {
        }
    });  	
}

function showFeaturesData(data){
	$.each(data, function(index, row){
		if (row.id=='5'){
			$("#features"+row.id+"_"+row.sub_id+"_title").val(row.title);
			$("#features"+row.id+"_"+row.sub_id+"_title").attr('content', row.content_id);
			$("#features"+row.id+"_"+row.sub_id+"_date").val(row.date);
			$("#features"+row.id+"_"+row.sub_id+"_editor").html(row.content);
			$("#features"+row.id+"_"+row.sub_id+"_image").attr('path', row.image); 
		}else{
			$("#features"+row.id+"_title").val(row.title);
			$("#features"+row.id+"_title").attr('content', row.content_id);
			$("#features"+row.id+"_date").val(row.date);
			$("#features"+row.id+"_editor").html(row.content);
			$("#features"+row.id+"_image").attr('path', row.image);
		}

		if (row.image==''){
			if (row.id=='5'){
				$("#features"+row.id+"_"+row.sub_id+"_image").attr('src', $("#resPath").val()+"images/bbva-compass-logo.svg");
			}else{
				$("#features"+row.id+"_image").attr('src', $("#resPath").val()+"images/bbva-compass-logo.svg");
			}
		}else{
			$.ajax({
		        url: $('#basePath').val()+'image',
		        data: { 'blob-key': row.image },
		        dataType: 'json', 
		        success: function(data) {
		        	if (row.id=='5'){
						$("#features"+row.id+"_"+row.sub_id+"_image").attr('src', data.key+'=s400');
					}else{
						$("#features"+row.id+"_image").attr('src', data.key+'=s400');
					}
		        },
		        error: function() {
		        }
		    });
		}
	});
}

function showData(data){
	$('#features_image').attr('path', data.image);

	if (data.image==''){
		if (data.id=='5'){
			$("#features"+data.id+"_"+data.sub_id+"_image").attr('src', $("#resPath").val()+"images/bbva-compass-logo.svg");
		}else{
			$("#features"+data.id+"_image").attr('src', $("#resPath").val()+"images/bbva-compass-logo.svg");
		}

		$('#features_image').attr('src',$("#resPath").val()+"images/bbva-compass-logo.svg");
	}else{
		
	   	$.ajax({
	        url: $('#basePath').val()+'image',
	        data: { 'blob-key': data.image },
	        dataType: 'json', 
	        success: function(i_data) {
	        	if (data.id=='5'){
					$("#features"+data.id+"_"+data.sub_id+"_image").attr('src', i_data.key+'=s400');
				}else{
					$("#features"+data.id+"_image").attr('src', i_data.key+'=s400');
				}
	        	$("#features_image").attr('src', i_data.key+'=s400');
	        },
	        error: function() {
	        }
	    });
	   	
	}
	
	$('#features_title').val(data.title);
	if (data.id=='5'){
		$("#features"+data.id+"_"+data.sub_id+"_title").val(data.title);
	}else{
		$("#features"+data.id+"_title").val(data.title);
	}
	
	$('#features_date').val(data.date);
	if (data.id=='5'){
		$("#features"+data.id+"_"+data.sub_id+"_date").val(data.date);
	}else{
		$("#features"+data.id+"_date").val(data.date);
	}
	
	$('#features_editor').myeditor().setCode(data.content);
	if (data.id=='5'){
		$("#features"+data.id+"_"+data.sub_id+"_editor").html(data.content);
	}else{
		$("#features"+data.id+"_editor").html(data.content);
	}
}

function showLatest(data){
	var c=1;
	var features_id  = $('#features_template').val();
	var vvv = data.length;
	
	$.each(data, function(index, row){
//		console.log(data);
		
		if (features_id=='5'){
			$("#features"+features_id+"_"+c+"_title").val(row.name);
			$("#features"+features_id+"_"+c+"_title").attr('content', row.id);
			$("#features"+features_id+"_"+c+"_date").val(row.updated_at);
			$("#features"+features_id+"_"+c+"_editor").html(row.html);
			$("#features"+features_id+"_"+c+"_image").attr('path', row.image);
			
		}else{
			if (c==1){
				$("#features"+features_id+"_title").val(row.name);
				$("#features"+features_id+"_title").attr('content', row.id);
				$("#features"+features_id+"_date").val(row.updated_at);
				$("#features"+features_id+"_editor").html(row.html);
				$("#features"+features_id+"_image").attr('path', row.image);
			}
		}
		
		if (row.image==''){
			if (features_id=='5'){
				$("#features"+features_id+"_"+c+"_image").attr('src', $("#resPath").val()+"images/bbva-compass-logo.svg");
			}else{
				if (c==1){
					$("#features"+features_id+"_image").attr('src', $("#resPath").val()+"images/bbva-compass-logo.svg");
				}
			}
		}else{
			$.ajax({
		        url: $('#basePath').val()+'image',
		        data: { 'blob-key': row.image },
		        dataType: 'json', 
		        success: function(data) {
		        	if (features_id=='5'){
						$("#features"+features_id+"_"+c+"_image").attr('src', data.key+'=s400');
					}else{
						if (c==1){
							$("#features"+features_id+"_image").attr('src', data.key+'=s400');
						}
					}
		        },
		        error: function() {
		        }
		    });
		}
		
		
		c=c+1;
		vvv-=1;
		if (vvv==0){
			if (features_id=='5'){
				$('div.features_window').removeClass('setting');
				init();
			}else{
				getFeaturesData(features_id, '1');
			}
		}
	});
}

function getFeaturesData(features_id, sub_features_id){
//   	$.ajax({
//        url: 'getFeatures.html',
//        type: "POST",
//        data: {  
//        	features : features_id,
//			sub_features : sub_features_id
//        },
//        dataType: 'json', 
//        success: function(data) {
//        	if (data.errCode==0){
////        		console.log(data);
////        		showData(data.features);
//        	}
//        },
//        error: function() {
//        }
//    });
	if (features_id=='5'){
		$('#features_title').val($("#features"+features_id+"_"+sub_features_id+"_title").val());
		$('#features_title').attr('content', $("#features"+features_id+"_"+sub_features_id+"_title").attr('content'));
		$('#features_date').val($("#features"+features_id+"_"+sub_features_id+"_date").val());
		$('#features_editor').myeditor().setCode($("#features"+features_id+"_"+sub_features_id+"_editor").html());
		$('#features_image').attr('path', $("#features"+features_id+"_"+sub_features_id+"_image").attr('path'));
		$('#features_image').attr('src', $("#features"+features_id+"_"+sub_features_id+"_image").attr('src'));
	}else{
		$('#features_title').val($("#features"+features_id+"_title").val());
		$('#features_title').attr('content', $("#features"+features_id+"_title").attr('content'));
		$('#features_date').val($("#features"+features_id+"_date").val());
		$('#features_editor').myeditor().setCode($("#features"+features_id+"_editor").html());
		$('#features_image').attr('path', $("#features"+features_id+"_image").attr('path')); 
		$('#features_image').attr('src', $("#features"+features_id+"_image").attr('src'));
	}
}

function getLatest(){
   	$.ajax({
        url: 'getLatestNews.html',
        type: "POST",
        data: {  
        },
        dataType: 'json', 
        success: function(data) {
        	showAlert(data.errMsg);
        	if (data.errCode==0){
        		showLatest(data.list);
        	}
        },
        error: function() {
        }
    });  	
}

function init(){
	$('#features_image').attr('path','');
	$('#features_image').attr('src',$("#resPath").val()+"images/bbva-compass-logo.svg");
//	$("img.features_image").attr('src',$("#resPath").val()+"images/bbva-compass-logo.svg");
	$('#features_title').val('');
	$('#features_title').attr('content', '');
//	$("input.features_title").val('');
	$('#features_date').val('');
//	$("input.features_date").val('');
//	$('#features_date').val(getToday());
//	$("input.features_date").val(getToday());
	$("#features_editor").myeditor().setCode('');
//	$("div.features_editor").html('');
}

function selectImage(){
	$('#mediaManagerModal').foundation('reveal', 'open', {
	    close_on_background_click: false,
	    url: $('#resPath').val()+'popup_mediamanager.jsp',	
	    data: { page: 'features',	type: 'image' , select: 'single' },
	    success: function(data) {
	    },
	    error: function() {
	    }	    
	});
}

function setFeaturesData(key, thumb){
	$("#features_image").attr('path', key);
	$("#features_image").attr('src', thumb+'=s200');
}

$(document).ready(function() {
	createEditor('features_editor');
	$('.fdatepicker').fdatepicker();
	$("div.features").hide();
	
//	init();
	
	$('#features_template').change(function(){
		$("div.features").hide();
		init();

		var features_id  = $('#features_template').val();
	   	$.ajax({
	        url: 'saveTemplate.html',
	        type: "POST",
	        data: {  kind: 'features', template:  features_id  },
	        dataType: 'json', 
	        success: function(data) {
	        	if (data.errCode==0){
	        		
	        		$.ajax({
	        	        url: 'loadAllFeatures.html',
	        	        type: "POST",
	        	        data: {  features:  features_id  },
	        	        dataType: 'json', 
	        	        success: function(data) {
	        	        	if (data.errCode==0){
	        	        		showFeaturesData(data.features);
	        	    			
	        	        		if (features_id=='5'){
	        	        			$('div.features_window').removeClass('setting');
	        	        			init();
	        	    			}else{
	        	    				getFeaturesData(features_id, '1');
	        	    			}
	        	    			
	        	        		$("#features"+features_id).show();
	        	        	}else{
	        	        	}
	        	        },
	        	        error: function() {
	        	        }
	        	    });
	        		
	        	}else{
	        	}
	        },
	        error: function() {
	        }
	    });
	});
	
	$('div.features_window').click(function(){
		$('div.features_window').removeClass('setting');
		$(this).addClass('setting');
		
		var features_id = $("#features_template").val();
		var sub_features_id = $(this).attr('features');
		
		if (features_id=='5'){
			getFeaturesData(features_id, sub_features_id);
		}else{
			getFeaturesData(features_id, '1');
		}
	});
	
   	$('#btnSubmit').click(function(){
   		update();
   	});
   	
   	$('#features_template').trigger('change');
});

