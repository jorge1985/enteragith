/**
 * Edit Content page. 
 */
var answer_count=0;
var file_count=0;
var gallery_count=0;
var news_editor=null;

function selectFromEditor(kind){
	$("#content_news_editor").myeditor().saveSelection();
	
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

function selectFromChannel(){
	
	debugger;
	
   	$.ajax({
   		type: "POST",
        url: 'getContentOfVideo.html',
        data: { content_id: $("#edit_content_id").val() },
        dataType: 'json', 
        success: function(data) {
        	if (data.errCode==0){
        		if (data.content.length==0){
        			alert("There is no video in same channel!")
        		}else{
        			$("#video_list").html('');
        			
        			debugger;
        			
        			$.each(data.content, function(index, row){
        				$("#video_list").append(
    						'<li><a href="javascript:setArticleData(\'article\', \''+row.video+'\', \''+row.image+'\')">'+row.name+'</a></li>'
						);
        			});
        			
        			$("#content_news_editor").myeditor().saveSelection();
        			$('#mediaVideoModal').foundation('reveal', 'open', {
        			    close_on_background_click: false,
        			    success: function(data) {
        			    },
        			    error: function() {
        			    }	    
        			});
        		}
        	}
        },
        error: function() {
        }
   	});
}

function setArticleData(kind, video, image){
	
	debugger;
	
	$('#mediaVideoModal').foundation('reveal', 'close');
	setEmbedDataToEditor(kind, video, image);
}

function setEmbedDataToEditor(kind, name, key){
	
	debugger;
	
	key = $("#basePath").val()+"serve?blob-key="+key;
	if (kind=="article"){
		name = $("#basePath").val()+"serve?blob-key="+name;
		$("#content_news_editor").myeditor().insertArticle(name, key);
	}
	if (kind=="image"){
		$("#content_news_editor").myeditor().insertImage(name, key);
	}
	if (kind=="video"){
		$("#content_news_editor").myeditor().insertVideo(name, key);
	}
	if (kind=="all"){
		$("#content_news_editor").myeditor().insertFile(name, key);
	}
	
	if (kind=="forum"){
		$("#content_news_editor").myeditor().insertForum(name, key);
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
	$("#content_news_editor").myeditor().saveSelection();
	
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
		toolbarExternal: '#content_news_toolbar'
	});
	
	$("#content_news_toolbar>ul").append(
		'<li class="myeditor_separator"></li>'+
		'<li><a href="javascript:selectFromEditor(\'image\')" title="Image" class="myeditor_btn_image"></a></li>' +
		'<li><a href="javascript:selectFromEditor(\'video\')" title="Video" class="myeditor_btn_video"></a></li>' +
		'<li><a href="javascript:selectFromEditor(\'all\')" title="File" class="myeditor_btn_file"></a></li>' +
		'<li class="myeditor_separator"></li>' +
		'<li><a href="javascript:selectForum()" title="Forum" class="myeditor_btn_forum"></a></li>' +
		'<li><a href="javascript:selectFromChannel()" title="Video Content" class="myeditor_btn_video_content"></a></li>' +
		'<li class="myeditor_separator"></li>'
	);
}

function editContent(){
	$("#form_move").submit();
}

function updateContentFAQs(){
   	$.ajax({
   		type: "POST",
        url: 'updateContentFAQs.html',
        data: { content_id: $("#edit_content_id").val(),  type: $("#content_faqs_type").val(),  content_faqs_id: $("#content_faqs_id").val(),  question: $("#content_faqs_question").val(), answer: $("#content_faqs_answer").val()  },
        dataType: 'json', 
        success: function(data) {
        	init();
        	showAlert(data.errMsg)
        	if (data.errCode==0){
        		showFAQsData(data.faqs);
        	}
        },
        error: function() {
        	init();
        }
   	});
}

function showFAQsData(data){
	$("#content_faqs_list").html('');
	
	$.each(data, function(index, row){
		$("#content_faqs_list").append('<li>' + 
						'<a href="javascript:editFAQsData(\'edit\',\'' + row.id + '\')"><i class="fa fa-pencil fa-lg edit"></i></a>' +
						'&nbsp;'+
						'<a href="javascript:editFAQsData(\'delete\',\'' + row.id + '\')"><i class="fa fa-times-circle fa-lg delete"></i></a>' +
						'&nbsp;&nbsp;&nbsp;'+
						row.question+'</li>');
	});
}

function editFAQsData(kind, id){
	$("#content_faqs_type").val(kind);
	$("#content_faqs_id").val(id);

	if (kind=='delete'){
		var t = confirm('Are you sure to delete this?');
		if (!t)
			return;
		
		updateContentFAQs();
	}

   	$.ajax({
   		type: "POST",
        url: 'getContentFAQs.html',
        data: { content_faqs_id: id },
        dataType: 'json', 
        success: function(data) {
        	if (data.errCode==0){
        		$("#content_faqs_question").val(data.faqs.question);
        		$("#content_faqs_answer").val(data.faqs.answer);
        	}
        },
        error: function() {
        }
   	});
}

function loadContentFAQs(){
	showLoading();
	
   	$.ajax({
   		type: "POST",
        url: 'loadContentFAQs.html',
        data: { content_id: $("#edit_content_id").val() },
        dataType: 'json', 
        success: function(data) {
        	hideLoading();
        	if (data.errCode==0){
        		showFAQsData(data.faqs);
        	}
        },
        error: function() {
        	hideLoading();
        }
   	});
}


function updateContentPoll(){
	var answer=new Array();
	var i=0;
	$(".poll_answer").each(function(index, row){
		answer[i]=$(row).val();
		i+=1;
	});
	
   	$.ajax({
   		type: "POST",
        url: 'updateContentPoll.html',
        data: { content_id: $("#edit_content_id").val(),  type: $("#content_poll_type").val(),  content_question_id: $("#content_poll_id").val(),  question: $("#content_poll_question").val(),  answer: answer, open_question: $("#content_poll_open_question").prop('checked') ? '1' : '0'   },
        dataType: 'json', 
        success: function(data) {
        	init();
        	showAlert(data.errMsg)
        	if (data.errCode==0){
        		showPollData(data.question);
        	}
        },
        error: function() {
        	init();
        }
   	});
}

function editPollData(kind, id){
	$("#content_poll_type").val(kind);
	$("#content_poll_id").val(id);

	if (kind=='delete'){
		var t = confirm('Are you sure to delete this?');
		if (!t)
			return;
		
		updateContentPoll();
	}

   	$.ajax({
   		type: "POST",
        url: 'getContentPoll.html',
        data: { content_question_id: id },
        dataType: 'json', 
        success: function(data) {
        	if (data.errCode==0){
        		$("#content_poll_question").val(data.question.question);
        		
        		if (data.question.status=='1')
        			$("#content_poll_open_question").prop('checked', true);
        		else
        			$("#content_poll_open_question").prop('checked', false);
        		
    			if ($("#content_poll_open_question").prop('checked')){
    				$("#label_poll_answer").hide();
    				$("#btnAddPoll").hide();
    				$("#content_poll_answer").hide();
    				$("#content_poll_answer").html('');
    				answer_count = 0;
    			}else{
    				$("#label_poll_answer").show();
    				$("#btnAddPoll").show();
    				$("#content_poll_answer").show();
    			}
        		
    			$("#content_poll_result").hide();
        		$("#content_poll_answer").html('');
        		answer_count=0;
        		
        		$.each(data.answer, function(index, row){
        			addPollAnswer();
        			$("#content_poll_answer"+answer_count).val(row.answer);
        		});
        	}
        },
        error: function() {
        }
   	});
}

function showPollData(data){
	$("#content_poll_list").html('');
	
	$.each(data, function(index, row){
		$("#content_poll_list").append('<li>' + 
						'<a href="javascript:editPollData(\'edit\',\'' + row.id + '\')"><i class="fa fa-pencil fa-lg edit"></i></a>' +
						'&nbsp;'+
						'<a href="javascript:editPollData(\'delete\',\'' + row.id + '\')"><i class="fa fa-times-circle fa-lg delete"></i></a>' +
						'&nbsp;&nbsp;&nbsp;'+
						row.question+'</li>');
	});
}

function loadContentPoll(){
	showLoading();
	
   	$.ajax({
   		type: "POST",
        url: 'loadContentPoll.html',
        data: { content_id: $("#edit_content_id").val() },
        dataType: 'json', 
        success: function(data) {
        	hideLoading();
        	if (data.errCode==0){
        		showPollData(data.question);
        	}
        },
        error: function() {
        	hideLoading();
        }
   	});
}

function addPollAnswer(){
	answer_count+=1;
	$("#content_poll_answer").append(''+
			'<div class="row" id="content_poll_answer_div'+answer_count+'">' +
			'<label>' + 
			'<input type="text" id="content_poll_answer'+answer_count+'" class="poll_answer" placeholder="'+$("#content_poll_answer_placeholder_msg").val()+'" required="required" />' +
			'</label>' +
			'<small class="error">'+$("#content_poll_answer_validate_msg").val()+'</small>' +
			'<a href="javascript:removePollAnswer(\''+answer_count+'\')">Remove</a>' + 
			'</div>' +
			'');
}

function removePollAnswer(index){
	$("#content_poll_answer_div"+index).remove();
}

function showPollResult(){
	var max_axis = 1000;
	var dataset = new Array();
	var c=0;
	var l=$("input.poll_answer").length;
	$("input.poll_answer").each(function(index, row){
		if ($(row).val()!=''){
			dataset[c]=	{	
					label : $(row).val(),
					votes : 0,
					strokeColor: randomColor(),
					data : [ max_axis-(c/l*0.1), max_axis-(c/l*0.1), max_axis-c*5, max_axis-c*5, max_axis-c*5]
			};
			c+=1;
		}
	});
	
	if (c==0){
		alert($("#validate_no_result_msg").val());
		return;
	}
	
	var data = {
	    labels: ["1", "2", "3", "4", "5"],
	    datasets: dataset
	};	

	$("#content_poll_result").show();
	var ctx = $("#myChart").get(0).getContext("2d");
	
	var myLineChart = new Chart(ctx).Line(data, {
		showScale: false,
		animationEasing: "easeOutQuart",
		bezierCurve : true,
		bezierCurveTension : 0.2,
		scaleShowLabels : true,
		scaleShowGridLines : false,
		maintainAspectRatio: false,
		showTooltips: false,
		pointDot : false,
		datasetFill : false,
		legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span class=\"label\" style=\"background-color:<%=datasets[i].strokeColor%>\">&nbsp;</span>&nbsp;<%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>",
		responsive: false
	});
	
	$("#myChart_legend").html(myLineChart.generateLegend());
	$("#myChart_legend ul li").each(function(index, row){
		$(row).append("&nbsp;---&nbsp;<span class='label describe'>" + dataset[index].votes + " Votes</span>");
	});
}


function updateContentFile(){
	var filekey=new Array();
	var filename=new Array();
	var i=0;
	$(".file_content_data").each(function(index, row){
		filekey[i]=$(row).attr('filekey');
		filename[i]=$(row).attr('filename');
		i+=1;
	});
	
   	$.ajax({
   		type: "POST",
        url: 'updateContentFile.html',
        data: { content_id: $("#edit_content_id").val(),  description: $("#content_file_description").val(),  filekey: filekey, filename: filename },
        dataType: 'json', 
        success: function(data) {
        	showAlert(data.errMsg)
        	if (data.errCode==0){
        		if (data.move_to=='0'){
	    			$("#form_move").attr('action', $("#basePath").val()+"content/recycle.html");
	    		}else if (data.move_to=='1'){
	    			$("#form_move").attr('action', $("#basePath").val()+"content/active.html");
	    		}else{
	    			$("#form_move").attr('action', $("#basePath").val()+"content/expired.html");
	    		}
	    		
	    		$("#form_move").submit();
        	}
        },
        error: function() {
        }
   	});
}

function addFileData(media, name){
	file_count+=1;
	$("#content_file_list").append('<li id="content_file_li'+file_count+'" class="file_content_data" pos="'+file_count+'" filekey="'+media+'" filename="'+name+'">' + 
					'<a href="javascript:removeFileData(\'' + file_count + '\')"><i class="fa fa-times-circle fa-lg delete"></i></a>' +
					'&nbsp;&nbsp;&nbsp;'+
					name+'</li>');
}

function removeFileData(id){
	$("#content_file_li"+id).remove();
}

function showFileData(data){
	$("#content_file_list").html('');
	file_count=0;
	
	$("#content_file_description").val(data.file.description);
	$.each(data.media, function(index, row){
		file_count+=1;
		$("#content_file_list").append('<li id="content_file_li'+file_count+'" class="file_content_data" pos="'+file_count+'" filekey="'+row.media+'" filename="'+row.name+'">' + 
						'<a href="javascript:removeFileData(\'' + file_count + '\')"><i class="fa fa-times-circle fa-lg delete"></i></a>' +
						'&nbsp;&nbsp;&nbsp;'+
						row.name+'</li>');
	});
}

function loadContentFile(){
	showLoading();
	
   	$.ajax({
   		type: "POST",
        url: 'loadContentFile.html',
        data: { content_id: $("#edit_content_id").val() },
        dataType: 'json', 
        success: function(data) {
        	hideLoading();
        	if (data.errCode==0){
        		showFileData(data);
        	}
        },
        error: function() {
        	hideLoading();
        }
   	});
}

function selectFile(kind){
	if (kind=='media'){
		$('#mediaManagerModal').foundation('reveal', 'open', {
		    close_on_background_click: false,
		    url: $('#resPath').val()+'popup_mediamanager.jsp',	
		    data: { page: 'content_file',	type: 'all'   , select: 'multiple'  },
		    success: function(data) {
		    },
		    error: function() {
		    }	    
		});
	}
	
	if (kind=='upload'){
		$('#uploadManagerModal').foundation('reveal', 'open', {
		    close_on_background_click: false,
		    url: $('#resPath').val()+'popup_upload.jsp',	
		    data: {	page: 'content_file', type: 'all' },
		    success: function(data) {
		    },
		    error: function() {
		    }	    
		});
	}
}


function updateContentGallery(){
	var filekey=new Array();
	var filename=new Array();
	var i=0;
	$(".gallery-image").each(function(index, row){
		filekey[i]=$(row).attr('filekey');
		filename[i]=$(row).attr('filename');
		i+=1;
	});
	
   	$.ajax({
   		type: "POST",
        url: 'updateContentGallery.html',
        data: { content_id: $("#edit_content_id").val(),  detail: $("#content_gallery_detail").val(),  filekey: filekey, filename: filename },
        dataType: 'json', 
        success: function(data) {
        	showAlert(data.errMsg)
        	if (data.errCode==0){
        		if (data.move_to=='0'){
	    			$("#form_move").attr('action', $("#basePath").val()+"content/recycle.html");
	    		}else if (data.move_to=='1'){
	    			$("#form_move").attr('action', $("#basePath").val()+"content/active.html");
	    		}else{
	    			$("#form_move").attr('action', $("#basePath").val()+"content/expired.html");
	    		}
	    		
	    		$("#form_move").submit();
        	}
        },
        error: function() {
        }
   	});
}

function addGalleryData(media, name, link){
	gallery_count+=1;
	$("#content_gallery_list").append('' +
					'<a id="content_gallery_ele'+gallery_count+'" class="gallery-image-link gallery_margin" href="'+link+'=s600" data-lightbox="gallery-set" data-title="'+name+'">' +
					'<img class="gallery-image" src="'+link+'=s200" alt="" filekey="'+media+'" filename="'+name+'" /></a>' + 
					'<a id="content_gallery_li'+gallery_count+'" class="gallery_link_a" href="javascript:removeGalleryData(\'' + gallery_count + '\')"><i class="fa fa-times-circle fa-lg delete"></i></a>' +
					'');
}

function removeGalleryData(id){
	$("#content_gallery_ele"+id).remove();
	$("#content_gallery_li"+id).remove();
}

function showGalleryData(data){
	$("#content_gallery_list").html('');
	gallery_count=0;
	
	$("#content_gallery_detail").val(data.file.description);
	$.each(data.media, function(index, row){
	   	
		$.ajax({
	        url: $('#basePath').val()+'image',
	        data: { 'blob-key': row.media },
	        dataType: 'json', 
	        success: function(data) {
	    		gallery_count+=1;
	    		$("#content_gallery_list").append('' +
	    				'<a id="content_gallery_ele'+gallery_count+'" class="gallery-image-link gallery_margin" href="'+data.key+'=s600" data-lightbox="gallery-set" data-title="'+row.name+'">' +
	    				'<img class="gallery-image" src="'+data.key+'=s200" alt="" filekey="'+row.media+'" filename="'+row.name+'" /></a>' + 
	    				'<a id="content_gallery_li'+gallery_count+'"  class="gallery_link_a" href="javascript:removeGalleryData(\'' + gallery_count + '\')"><i class="fa fa-times-circle fa-lg delete"></i></a>' +
	    				'');
	        },
	        error: function() {
	        }
	    });
	});
}

function loadContentGallery(){
	showLoading();
	
   	$.ajax({
   		type: "POST",
        url: 'loadContentGallery.html',
        data: { content_id: $("#edit_content_id").val() },
        dataType: 'json', 
        success: function(data) {
        	hideLoading();
        	if (data.errCode==0){
        		showGalleryData(data);
        	}
        },
        error: function() {
        	hideLoading();
        }
   	});
}

function selectGallery(kind){
	if (kind=='media'){
		$('#mediaManagerModal').foundation('reveal', 'open', {
		    close_on_background_click: false,
		    url: $('#resPath').val()+'popup_mediamanager.jsp',	
		    data: { page: 'content_gallery',	type: 'image'  , select: 'multiple'  },
		    success: function(data) {
		    },
		    error: function() {
		    }	    
		});
	}
	
	if (kind=='upload'){
		$('#uploadManagerModal').foundation('reveal', 'open', {
		    close_on_background_click: false,
		    url: $('#resPath').val()+'popup_upload.jsp',	
		    data: {	page: 'content_gallery', type: 'image' },
		    success: function(data) {
		    },
		    error: function() {
		    }	    
		});
	}
}


function selectNews(kind){
	if (kind=='media'){
		$('#mediaManagerModal').foundation('reveal', 'open', {
		    close_on_background_click: false,
		    url: $('#resPath').val()+'popup_mediamanager.jsp',	
		    data: { page: 'content_news',	type: 'image' , select: 'single'  },
		    success: function(data) {
		    },
		    error: function() {
		    }	    
		});
	}
	
	if (kind=='upload'){
		$('#uploadManagerModal').foundation('reveal', 'open', {
		    close_on_background_click: false,
		    url: $('#resPath').val()+'popup_upload.jsp',	
		    data: {	page: 'content_news', type: 'image' },
		    success: function(data) {
		    },
		    error: function() {
		    }	    
		});
	}
}

function setContentNewsData(key, link){
	$("#content_news_image").attr('key', key);
	if (link!='')
		$("#content_news_image").attr('src', link+"=s500");
	else
		$("#content_news_image").attr('src', '');
}

function updateContentNews(){
	var ppp = $("#content_news_image").attr('key');
		
   	$.ajax({
   		type: "POST",
        url: 'updateContentNews.html',
        data: { content_id: $("#edit_content_id").val(),  content: $("#content_news_editor").myeditor().getCode(),  image: ppp  },
        dataType: 'json', 
        success: function(data) {
        	showAlert(data.errMsg)
        	if (data.errCode==0){
        		if (data.move_to=='0'){
	    			$("#form_move").attr('action', $("#basePath").val()+"content/recycle.html");
	    		}else if (data.move_to=='1'){
	    			$("#form_move").attr('action', $("#basePath").val()+"content/active.html");
	    		}else{
	    			$("#form_move").attr('action', $("#basePath").val()+"content/expired.html");
	    		}
	    		
	    		$("#form_move").submit();
        	}
        },
        error: function() {
        }
   	});
}

function showNewsData(data){
	$("#content_news_editor").myeditor().setCode(data.content);
	
	if (data.image!=''){
		$.ajax({
	        url: $('#basePath').val()+'image',
	        data: { 'blob-key': data.image },
	        dataType: 'json', 
	        success: function(row) {
	        	setContentNewsData(data.image, row.key);
	        },
	        error: function() {
	        }
	    });
	}
}

function getContentNews(){
	showLoading();
	
   	$.ajax({
   		type: "POST",
        url: 'getContentNews.html',
        data: { content_id: $("#edit_content_id").val() },
        dataType: 'json', 
        success: function(data) {
        	hideLoading();
        	if (data.errCode==0){
        		showNewsData(data.news);
        	}
        },
        error: function() {
        	hideLoading();
        }
   	});
}

function selectVideo(kind, t){
	if (kind=='media'){
		$('#mediaManagerModal').foundation('reveal', 'open', {
		    close_on_background_click: false,
		    url: $('#resPath').val()+'popup_mediamanager.jsp',	
		    data: { page: 'content_video',	type: t , select: 'single' },
		    success: function(data) {
		    },
		    error: function() {
		    }	    
		});
	}
	
	if (kind=='upload'){
		$('#uploadManagerModal').foundation('reveal', 'open', {
		    close_on_background_click: false,
		    url: $('#resPath').val()+'popup_upload.jsp',	
		    data: {	page: 'content_video', type: t },
		    success: function(data) {
		    },
		    error: function() {
		    }	    
		});
	}
}

function setVideoPlayer(v_id, path){
	var flashvars = {
			vidWidth: "473",
			vidHeight: "245",
			vidPath: path,
			//thumbPath: $("#resPath").val()+"images/player_logo.png",
			thumbPath: $("#resPath").val(),
			autoPlay: "false",
			autoLoop: "false",
			watermark: "hide",
			vidAspectRatio: "fit",
			seekbar: "show"
	};
	var params = {
			menu: "true",
			allowfullscreen: "true",
			allowscriptaccess: "always"
	};
	var attributes = {
			id: v_id,
			name: v_id
	};

	swfobject.embedSWF($("#resPath").val()+"swf/playerLite.swf", v_id, flashvars.vidWidth, flashvars.vidHeight, "9.0.0",$("#resPath").val()+"swf/expressInstall.swf", flashvars, params, attributes);
}

function updateContentVideo(){
   	$.ajax({
   		type: "POST",
        url: 'updateContentVideo.html',
        data: { content_id: $("#edit_content_id").val(),  video: $("#content_video_container").attr('key'),  image: $("#content_video_image").attr('key')  },
        dataType: 'json', 
        success: function(data) {
        	showAlert(data.errMsg)
        	if (data.errCode==0){
        		if (data.move_to=='0'){
	    			$("#form_move").attr('action', $("#basePath").val()+"content/recycle.html");
	    		}else if (data.move_to=='1'){
	    			$("#form_move").attr('action', $("#basePath").val()+"content/active.html");
	    		}else{
	    			$("#form_move").attr('action', $("#basePath").val()+"content/expired.html");
	    		}
	    		
	    		$("#form_move").submit();
        	}
        },
        error: function() {
        }
   	});
}

function setContentVideo_ImageData(key, link){
	$("#content_video_image").attr('key', key);
	if (link!=''){
		$("#content_video_image").show();
		$("#content_video_image").attr('src', link+"=s500");
	}else{
		$("#content_video_image").hide();
		$("#content_video_image").attr('src', '');
	}
}

function setContentVideo_VideoData(key){
	$("#content_video_container").attr('key', key);
	if (key!=''){
		$("#content_video_container").html('');
		$("#content_video_container").html('<div id="content_video_video"><p>Alternative</p></div>');
		$("#content_video_container").show();
		setVideoPlayer("content_video_video", $("#basePath").val()+"serve?blob-key="+key);
	}
	else{
		$("#content_video_container").html('');
		$("#content_video_container").hide();
	}
}

function showVideoData(data){
	setContentVideo_VideoData(data.video);
	
	if (data.image==''){
		setContentVideo_ImageData('', '');
		return;
	}
	
	$.ajax({
        url: $('#basePath').val()+'image',
        data: { 'blob-key': data.image },
        dataType: 'json', 
        success: function(row) {
        	setContentVideo_ImageData(data.image, row.key);
        },
        error: function() {
        }
    });
}

function getContentVideo(){
	showLoading();
	
   	$.ajax({
   		type: "POST",
        url: 'getContentVideo.html',
        data: { content_id: $("#edit_content_id").val() },
        dataType: 'json', 
        success: function(data) {
        	hideLoading();
        	if (data.errCode==0){
        		showVideoData(data.video);
        	}
        },
        error: function() {
        	hideLoading();
        }
   	});
}

function init(){
	$("#btnResetFAQs").trigger('click');
	$("#btnResetPoll").trigger('click');
	$("#btnResetFile").trigger('click');
	$("#btnResetGallery").trigger('click');
}


$(document).ready(function() {
	
	$("#btnResetPoll").click(function(){
		document.getElementById("content_poll_form").reset();
		
		$("#content_poll_id").val("");
		$("#content_poll_type").val("add");
		
		$("#content_poll_answer").html('');
		$("#content_poll_result").hide();
		
		if ($("#content_poll_open_question").prop('checked')){
			$("#label_poll_answer").hide();
			$("#btnAddPoll").hide();
			$("#content_poll_answer").hide();
			answer_count = 0;
		}else{
			$("#label_poll_answer").show();
			$("#btnAddPoll").show();
			$("#content_poll_answer").show();
			answer_count = 1;
			$("#content_poll_answer").html(''+
					'<div class="row">' +
					'<label>' + 
					'<input type="text" id="content_poll_answer1" class="poll_answer" placeholder="'+$("#content_poll_answer_placeholder_msg").val()+'" required="required" />' +
					'</label>' +
					'<small class="error">'+$("#content_poll_answer_validate_msg").val()+'</small>' +
					'</div>' +
					'');
		}		
	});

	$("#btnResetFAQs").click(function(){
		document.getElementById("content_faqs_form").reset();
		
		$("#content_faqs_id").val("");
		$("#content_faqs_type").val("add");
	});
	
	$("#btnResetFile").click(function(){
		document.getElementById("content_file_form").reset();
		
		file_count=0;
		$("#content_file_list").html("");
	});

	$("#btnResetGallery").click(function(){
		document.getElementById("content_gallery_form").reset();
		
		gallery_count=0;
		$("#content_gallery_list").html("");
	});

	init();

	if ($("#content_type").val()=='2'){
		$("#content_video").show();

		$("#btnResetVideo").click(function(){
			$("#content_video_image").hide();
			$("#content_video_image").attr('src','');
			$("#content_video_image").attr('key','');
			
			$("#content_video_container").attr('key','');
			$("#content_video_container").html('');
			$("#content_video_container").hide();
		});

		$("#btnSubmitVideo").click(function(){
			if ($("#content_video_image").attr('key')==''){
				showAlert($("#content_video_validate_msg").val());
				return;
			}
				
			if ($("#content_video_container").attr('key')==''){
				showAlert($("#content_video_validate_msg").val());
				return;
			}

			updateContentVideo();
		});

		$("#btnResetVideo").trigger('click');

		getContentVideo();
	}
	
	if ($("#content_type").val()=='5'){
		createEditor('content_news_editor');
		$("#content_news").show();

		$("#btnResetNews").click(function(){
			$("#content_news_image").attr('src','');
			$("#content_news_image").attr('key','');
			$("#content_news_editor").val('');
		});

		$("#btnSubmitNews").click(function(){
			if ($("#content_news_editor").myeditor().getCode()==''){
				showAlert($("#content_news_validate_input_msg").val());
				return;
			}
			
//			if ($("#content_news_image").attr('key')==''){
//				showAlert($("#content_news_validate_msg").val());
//				return;
//			}
				
			updateContentNews();
		});

		$("#btnResetNews").trigger('click');

		getContentNews();
	}
	
	if ($("#content_type").val()=='003'){
		$("#content_poll").show();
		
		$("#content_poll_open_question").change(function(){
			if ($("#content_poll_open_question").prop('checked')){
				$("#label_poll_answer").hide();
				$("#btnAddPoll").hide();
				$("#content_poll_answer").hide();
				$("#content_poll_answer").html('');
				answer_count = 0;
			}else{
				$("#label_poll_answer").show();
				$("#btnAddPoll").show();
				$("#content_poll_answer").show();
				answer_count = 1;
				$("#content_poll_answer").html(''+
						'<div class="row">' +
						'<label>' + 
						'<input type="text" id="content_poll_answer1" class="poll_answer" placeholder="'+$("#content_poll_answer_placeholder_msg").val()+'" required="required" />' +
						'</label>' +
						'<small class="error">'+$("#content_poll_answer_validate_msg").val()+'</small>' +
						'</div>' +
						'');
			}
		});
		
		$("#content_poll_form").on("valid invalid submit", function(event) {
	   		event.stopPropagation();
	   		event.preventDefault();
	   		
	   		if (event.type=='valid'){
	   			updateContentPoll();
	   		}
	   	});
		
		loadContentPoll();
	}
	
	if ($("#content_type").val()=='004'){
		$("#content_faqs").show();
		
		$("#content_faqs_form").on("valid invalid submit", function(event) {
	   		event.stopPropagation();
	   		event.preventDefault();
	   		
	   		if (event.type=='valid'){
	   			updateContentFAQs();
	   		}
	   	});
		
		loadContentFAQs();
	}
	
	if ($("#content_type").val()=='3'){
		$("#content_gallery").show();
		
		$("#content_gallery_form").on("valid invalid submit", function(event) {
	   		event.stopPropagation();
	   		event.preventDefault();
	   		
	   		if (event.type=='valid'){
	   			if ($(".gallery-image").length==0){
	   				showAlert($("#content_gallery_validate_msg").val());
	   				return;
	   			}
	   			
	   			updateContentGallery();
	   		}
	   	});
		
		loadContentGallery();
	}
	
	if ($("#content_type").val()=='4'){
		$("#content_file").show();
		
		$("#content_file_form").on("valid invalid submit", function(event) {
	   		event.stopPropagation();
	   		event.preventDefault();
	   		
	   		if (event.type=='valid'){
	   			if ($(".file_content_data").length==0){
	   				showAlert($("#content_file_validate_msg").val());
	   				return;
	   			}
	   			
	   			updateContentFile();
	   		}
	   	});
		
		loadContentFile();
	}
	
});

