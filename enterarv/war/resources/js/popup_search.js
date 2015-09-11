/**
 * Popup Search page (public). 
 */

var search_temp_value="";

function go_content_page(id, blog){
	$("#edit_search_content_id").val(id);
	$("#edit_search_content_blog").val(blog);
	$("#form_search_move_content").submit();
}
function go_gallery_page(id, blog){
	$("#edit_search_content_id2").val(id);
	$("#edit_search_content_blog2").val(blog);
	$("#form_search_move_gallery").submit();
}

function get_search(p){
	$("#search_result_p").html('');
	$("#search_page_current").val('0');
	$("#search_page_total").val('0');
	
   	$.ajax({
        url: $("#basePath").val()+'public/searchContent.html',
        type: "POST",
        data: {
        	search_text: search_temp_value,
        	page: p
        },
        dataType: 'json', 
        success: function(data) {
        	if (data.errCode==0){
        		$("#search_page_current").val(data.page);
        		$("#search_page_total").val(data.pages);
				if(data.result.length == 0) {
					$('#search-not-found').show()
				}else {
					$.each(data.result, function (index, row) {
						if (row.type == '2') {
							$("#search_result_p").append('<li>' + row.no + '.&nbsp;<a href="javascript:go_content_page(\'' + row.id + '\', \'' + row.blog + '\')">' + row.name + '</a><br><small>' + row.date + '</small>' + '</li>');
						}
						if (row.type == '5') {
							$("#search_result_p").append('<li>' + row.no + '.&nbsp;<a href="javascript:go_gallery_page(\'' + row.id + '\', \'' + row.blog + '\')">' + row.name + '</a><br><small>' + row.date + '</small>' + '</li>');
						}
					});
				}

        		init_pagination();
        	}else{
        		init_pagination();
        	}
        },
        error: function() {
        	init_pagination();
        }
    });  	
}

function get_search_result(){
	var v = $('#search_text_p').val();
	$('#search_text_p').val('');
	$("#lbl_search").html(v);
	
	search_temp_value = v;
	get_search(1);
} 

function init_pagination(){
	$("#search_prev_p").hide();
	$("#search_next_p").hide();
	
	var c=parseInt($("#search_page_current").val());
	var t=parseInt($("#search_page_total").val());
	
	$("#search_prev_p").removeClass('disabled');
	$("#search_next_p").removeClass('disabled');
	
	if (t<=1){
		$("#search_prev_p").addClass('disabled');
		$("#search_next_p").addClass('disabled');
		
		return true;
	}
	
	$("#search_prev_p").show();
	$("#search_next_p").show();
	if (c<=1){
		$("#search_prev_p").addClass('disabled');
	}
	if (c>=t){
		$("#search_next_p").addClass('disabled');
	}
}

function next_search_result(){
	if ($("#search_next_p").hasClass('disabled'))
		return;
	
	var c=parseInt($("#search_page_current").val())+1;
	get_search(c);
}

function prev_search_result(){
	if ($("#search_prev_p").hasClass('disabled'))
		return;
	
	var c=parseInt($("#search_page_current").val())-1;
	get_search(c);
}

$(document).ready(function() {
	$("#search_prev_p").hide();
	$("#search_next_p").hide();
	
	$('#search_text_p').keypress(function(event){
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if (keycode==13){
			get_search_result();
		}
	});
	
//	get_search_result();
});

