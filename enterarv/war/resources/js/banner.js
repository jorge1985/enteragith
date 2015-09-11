/**
 * Banner page. 
 */

function showBannerData(data){
	$.each(data, function(index, row){
		if (row.image==''){
			$("#banner_image"+row.id).attr('src', $("#resPath").val()+"images/logo-bbva-tipo.svg");
			$("#banner_image"+row.id).attr('before', $("#resPath").val()+"images/logo-bbva-tipo.svg");
		}else{
			$("#banner_image"+row.id).attr('src', row.image);
			$("#banner_image"+row.id).attr('before', row.image);
		}
		
		$("#banner_link"+row.id).val(row.url);
		$("#banner_target"+row.id).val(row.target);
	});
}

function loadAllData(){
	$.ajax({
        url: 'loadAllBanner.html',
        type: "POST",
        data: {   },
        dataType: 'json', 
        success: function(data) {
        	if (data.errCode==0){
        		showBannerData(data.banner);
        	}else{
        	}
        },
        error: function() {
        }
    });
}

function update(){
	var banner = new Array();
	var i=1;
	for (i=1; i<=19; i++){
		banner[i-1] = {
			"image": $("#banner_image"+i).attr('src'),
			"url": $("#banner_link"+i).val(),
			"target" : $("#banner_target"+i).val()
		};
	}
	
   	$.ajax({
        url: 'saveBanner.html',
        type: "POST",
        data: {  
			data : banner
        },
        dataType: 'json', 
        success: function(data) {
        	showAlert(data.errMsg);
        },
        error: function() {
        }
    });  	
}

function setBannerData(item, key){
	$("#banner_image"+item).attr('src', $("#basePath").val()+"serve?blob-key="+key);
}

function selectImage(d){
	$('#mediaManagerModal').foundation('reveal', 'open', {
	    url: $('#resPath').val()+'popup_mediamanager.jsp',	
	    data: { page: 'banner',	 type: 'image'  , select: 'single' , detail: d },
	    close_on_background_click: false,
	    success: function(data) {
	    },
	    error: function() {
	    }	    
	});
}

function resetImage(item){
	$("#banner_image"+item).attr('src', $("#resPath").val()+"images/logo-bbva-tipo.svg");
//	$("#banner_image"+item).attr('src', $("#banner_image"+item).attr('before'));
}

function setImageSrc(index){
	if ($("#banner_url"+index).val()=='')
		$("#banner_image"+index).attr('src', $("#resPath").val()+"images/logo-bbva-tipo.svg");
	else
		$("#banner_image"+index).attr('src', $("#banner_url"+index).val());
}

$(document).ready(function() {
	
	loadAllData();
	
//	$("input.banner-url").on('keyup', function(event){
//		var p = $(this).attr('pos');
//		setTimeout(setImageSrc(p), 100);
//	}).on('change', function(event){
//		var p = $(this).attr('pos');
//		setTimeout(setImageSrc(p), 100);
//	});
	
   	$('#btnSubmit').click(function(){
   		update();
   	});
});
