/**
 * Add Content page. 
 */

function getSubTree(data){
	var has=false;
	var sub_tree = '';
	$.each(data, function(index, row){
		has=true;
		sub_tree += '<li id="channel_li'+row.parent.id+'" pos="'+row.parent.id+'" class="cjh-ui-nestedSortable-no-nesting">' + 
				'<div class="cjh-ui-nestedSortable-no-nesting"><div class="actions-wrapper cjh-ui-nestedSortable-no-nesting">' +
				'<input type="checkbox" class="channel_item" pos="'+row.parent.id+'" id="channel_check'+row.parent.id+'" style="margin:0px;"/>'+
//				'<a href="javascript:edit_channel(\'' + row.parent.id + '\')"><i class="fa fa-pencil fa-lg edit"></i></a>' +
//				'<a href="javascript:update_channel(\'delete\',\'' + row.parent.id + '\')"><i class="fa fa-times-circle fa-lg delete"></i></a>' +
				'</div>'+row.parent.name+'</div>'
				;
		
		var temp = '<ol class="cjh-ui-nestedSortable-no-nesting">';
		temp += getSubTree(row.child) + '</ol>';
		sub_tree += temp;
		sub_tree += '</li>';
	});
	
	if (has)
		return sub_tree;
	else
		return '';
}

function showData(data){
	$("#channel_tree").html('');
	$.each(data.list, function(index, row){
		var sub="";
		sub = '<dd class="accordion-navigation" style="margin-top:10px;"><a href="#sub_panel'+row.parent.id+'"><i class="fa fa-sitemap fa-2x"> '+row.parent.name+'</i></a>';
		
		var tree=getSubTree(row.child);
		if (tree!=''){
			sub+='<div id="sub_panel'+row.parent.id+'" class="content" style="padding-top:0px;">';
			sub+='<ol class="sortable channels-tree cjh-ui-nestedSortable-no-nesting">';
			sub+=tree;
			sub+='</ol>';
			sub+='</div>';
		}
		
		sub+='</dd>';
		$("#channel_tree").append(sub);
	});
	
    $('.channels-tree').nestedSortable({
    	disableNesting: 'cjh-ui-nestedSortable-no-nesting',
        forcePlaceholderSize: true,
			handle: 'div',
			helper:	'clone',
			items: 'li',
			opacity: .6,
			placeholder: 'placeholder',
			revert: 250,
			tabSize: 25,
//			tolerance: 'pointer',
//			toleranceElement: '> div',
			maxLevels: 0,
			isTree: true,
			expandOnHover: 700,
			startCollapsed: true,
			stop: function()	{	return false;	}
    });
    
   	$(".channel_item").click(function(){
   		if ($(this).prop('checked')){
   			var id=$(this).attr('pos');
   			$("#channel_li"+id+" input[type=checkbox]").prop('checked', false);
   			$('#channel_check'+id).prop('checked', true);
   			
   			var p_li = $("#channel_li"+id).parents('li');
   			var b=false;
   			
   			$.each(p_li, function(index, li){
   				var s_id=$(li).attr('pos');
   				if ($('#channel_check'+s_id).prop('checked')){
   					b=true;
   				}
   			});
   			
   			if (b)
   				$('#channel_check'+id).prop('checked', false);
   		}
   	});
   	
	if ($("#content_type").val()=='edit'){
		getContent();
	}
}

function init(){
	$("#btnReset").trigger('click');
}

function showExistingData(data){
	$("#content_title").val(data.content.name);
	$("#content_featured").val(data.content.type);
	$("#content_validity_start").val(data.content.validity_start);
	$("#content_validity_end").val(data.content.validity_end);

	if(data.content.show_in == '1')
		$("#show_destacado").prop('checked',true);
	else if (data.content.show_in == '2')
		$("#show_soloCanal").prop('checked',true);
	else
		$("#show_ambos").prop('checked',true);
	
	//$("#show_in"+data.content.show_in).prop('checked', true);
	if (data.content.active=='1')
		$("#activeYes").prop('checked', true);
	else
		$("#activeNo").prop('checked', true);
	
	$("#content_tag_list").html('');
	$.each(data.tags, function(index, row){
		$("#content_tag_list").append(
				'<span class="label round" code="'+row.value+'">'+row.value+' &nbsp;<a href="#"><i class="fa fa-times"></i></a></span>'
			);
	});
	
	$.each(data.channel, function(index, row){
		$("#channel_check"+row.id).prop('checked', true);
	});
}

function getContent(){
   	$.ajax({
   		type: "POST",
        url: 'getContent.html',
        data: { content_id: $("#content_id").val() },
        dataType: 'json', 
        success: function(data) {
        	if (data.errCode==0){
        		showExistingData(data);
        	}else{
            	init();
        	}
        },
        error: function() {
        	init();
        }
   	});
}

function addContent(){
	var show_in="1";
	if ($("#show_soloCanal").prop('checked'))
		show_in="2";
	if ($("#show_ambos").prop('checked'))
		show_in="3";
	
	 var active="1";
	 if ($("#activeNo").prop('checked'))
	  active="0";
	 
	var channel="";
	$("#channel_tree input:checked").each(function(index, row){
		if (channel!='')
			channel+=",";
		channel+=$(row).attr('pos');
	});
	
	var tags=new Array();
	var c=0;
	$("#content_tag_list span").each(function(index, row){
		tags[c]=$(row).attr('code');
		c=c+1;
	});
	
	
   	$.ajax({
   		type: "POST",
        url: 'updateContent.html',
        data: { 	type: $("#content_type").val(), content_id: $("#content_id").val(), title: $("#content_title").val(), validity_start: $("#content_validity_start").val(), validity_end: $("#content_validity_end").val() , show_in: show_in , featured: $("#content_featured").val(), active: active, channel: channel , tag: tags	},
        dataType: 'json', 
        success: function(data) {
        	showAlert(data.errMsg)
        	if (data.errCode==0){
        		$("#edit_content_id").val(data.contentID);
        		
//        		if (data.move_to=='0'){
//        			$("#form_move").attr('action', $("#basePath").val()+"content/recycle.html");
//        		}else if (data.move_to=='1'){
//        			$("#form_move").attr('action', $("#basePath").val()+"content/active.html");
//        		}else{
//        			$("#form_move").attr('action', $("#basePath").val()+"content/expired.html");
//        		}
        		
        		$("#form_move").submit();
        	}else{
            	init();
        	}
        },
        error: function() {
        	init();
        }
   	});
}

$(document).ready(function() {
	$('.fdatepicker').fdatepicker();
	
	$("#content_tag" ).autocomplete({
	      source: $.parseJSON($("#availableTags").val())
    });
	
	$('#content_tag').keypress(function(event){
		var v=$("#content_tag").val();
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if (keycode==13){
			if (v!=''){
				if ($("#content_tag_list span[code='"+v+"']").length==0){
					$("#content_tag_list").append(
						'<span class="label round" code="'+v+'">'+v+' &nbsp;<a href="#"><i class="fa fa-times"></i></a></span>'
					);
				}
				
				$("#content_tag").val('')
				$("#content_tag").focus();
			}
			
			event.preventDefault();
		}
	});
	
	$("#content_tag_list").on('click', 'span>a', function(){
		$(this).parent('span').remove();
	});
	
	
	$("#btnReset").click(function(){
		document.getElementById("content_form").reset();
		
		$("#content_tag_list").html('');
		$("#content_tag").val('');
		$("#channel_tree input[type=checkbox]").prop('checked',false);
		$("#content_title").val('');
		$('#content_validity_start').val(getToday());
		$('#content_validity_end').val(getTodayAfterYear(5));
		$("#show_destacado").prop('checked', true);
   	});
   	
	showLoading();
	
   	$.ajax({
        url: $('#basePath').val() + 'category/loadChannels.html',
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

   	if ($("#content_type").val()=='add')
   		init();
   	
   	$("#content_form").on("valid invalid submit", function(event) {
   		event.stopPropagation();
   		event.preventDefault();
   		
   		if (event.type=='valid'){
   			if ($("#channel_tree input:checked").length==0){
   				showAlert($("#channel_empty_msg").val());
   				return;
   			}
   			
   			if ($("#content_validity_start").val()=='' || $("#content_validity_end").val()==''){
   				showAlert($("#date_empty_msg").val());
   				return;
   			}
   			
   			if ($("#content_validity_start").val()>$("#content_validity_end").val()){
   				showAlert($("#date_empty_msg").val());
   				return;
   			}
   			
   			if ($("#content_tag_list span").length==0){
   				showAlert($("#tag_empty_msg").val());
   				return;
   			}

   			addContent();
   		}
   	});
   	
});

