/**
 * Public home page. 
 */

$('.slider-for').slick({
    slidesToShow: 1,
    slidesToScroll: 1,
    arrows: false,
    fade: true,
    asNavFor: '.slider-nav',
    onAfterChange: afterChange,
    onBeforeChange : beforeChange
});
 
$('.slider-nav').slick({
    slidesToShow: 4,
    slidesToScroll: 1,
    asNavFor: '.slider-for',
    dots: false,
    centerMode: false,
    arrows: false,
    focusOnSelect: true,
    onAfterChange: afterChange,
    onBeforeChange : beforeChange
});

var beforeChange = function(slider,t, s) {
	if ($(".slider object").length>0){
		swfobject.getObjectById('content_video_'+t).GotoFrame(1);
	}
	if ($(".slider video").length>0){
//		var video = document.getElementById('content_video_'+t);
//		video.pause();
//		video.currentTime=0;
	}
};

var afterChange = function(slider,t) {
	if ($(".slider object").length>0){
		swfobject.getObjectById('content_video_'+t).Play();
	}
};

function content(id, blog){
	$("#edit_content_id").val(id);
	$("#edit_content_blog").val(blog);
	$("#form_move_content").submit();
}

function channel(id){
	$("#edit_channel_id").val(id);
	$("#form_move_channel").submit();
}

function getSubTree2(data, level){
	var has=false;
	var sub_tree = [];
	
	if (menuLength<level)
		menuLength=level;
	
	$.each(data, function(index, row){
		has=true;
		
		var temp = getSubTree2(row.child, level+1);
		if (temp==null){
			sub_tree.push(	{	pos: row.parent.id, label: row.parent.name, url: 'javascript:channel(\''+row.parent.id+'\')', sub: temp } );
		}else{
			sub_tree.push(	{	pos: row.parent.id, label: row.parent.name, url: '#', sub: temp } );
		}
	});
	
	if (has)
		return sub_tree;
	else
		return null;
}

function getSubTree(data){
	var has=false;
	var sub_tree = '';
	
	$.each(data, function(index, row){
		has=true;
		sub_tree += '<li pos="'+ row.parent.id +'">' + 
				''
				;
		
		var temp = '';
		temp = getSubTree(row.child);
		if (temp!=''){
			sub_tree += '<a class="have-submenu" href="#">'+row.parent.name+'<i class="fa fa-angle-right fa-fw"></i></a>';
			sub_tree += '<ul>'+ temp + '</ul>';
		}else{
			sub_tree += '' + 
			'<a href="javascript:channel(\'' + row.parent.id + '\')">' + 
			''+row.parent.name+'</a>' +  
			'';
//			sub_tree += '</li>';
		}
		
		sub_tree += '</li>';
	});
	
	if (has)
		return sub_tree;
	else
		return '';
}

function showData(data){
	$.each(data.list, function(index, row){
		var sub="";
		var sub2="";
		
		$("#category_mobile_"+row.parent.id).html('');
		
		var tree=getSubTree(row.child);
		sub = tree;
		$("#category_mobile_"+row.parent.id).append(sub);

		var sub2=getSubTree2(row.child, 1);
		mainMenu.push(	{	label: row.parent.name, url: '#', sub: sub2	} );
	});
	
//	for (var i=1; i<menuLength; i++){
//		$("#menu-modal >div.row").append('<div class="menucol"><ul></ul></div>');
//	}
//	$("#menu-modal .row .menucol").css('width', 100/menuLength + '%');

    menuModalPos();
    createMenu(mainMenu, 0);
	
    $("#mobile-menu nav.main-menu ul a.have-submenu").click(function(){
        $(this).parent().find(">ul").slideToggle();
    });
}

//function opport(){
//	$("#form_move_opport").submit();
//}

var flashvars;

var params = {
  menu: "true",
  allowfullscreen: "true",
  allowscriptaccess: "always"
};

	function doFlashVars(w,h, v, i){
	vars = {
	  vidWidth: 473,//w,
	  vidHeight: 245,//h,
	  vidPath: $("#basePath").val()+"serve?blob-key="+v,
	  thumbPath: $("#basePath").val()+"serve?blob-key="+i,
	  autoPlay: "false",
	  autoLoop: "true",
	  watermark: "hide",
	  seekbar: "show",
	  vidAspectRatio: "fit"
	};

	return vars;
}

function placeVideo(c_id){
	// Replace $("#wrapper") with the correct div container ID
	flashvars = doFlashVars($("#content_video_"+c_id).parent('h3').width(), $("#temas").height()-$("div.slider-nav").height(), $("#content_video_path_"+c_id).val(), $("#content_image_path_"+c_id).val());
	swfobject.embedSWF($("#resPath").val()+"swf/playerLite.swf", "content_video_"+c_id, flashvars.vidWidth, flashvars.vidHeight, "9.0.0",$("#resPath").val()+"swf/expressInstall.swf", flashvars, params, { id: "content_video_"+c_id, name: "content_video_"+c_id } );
}

function placeVideo_First(c_id){
	// Replace $("#wrapper") with the correct div container ID
	flashvars = doFlashVars($("#content_video_"+c_id).parent('h3').width(), $("#temas").height()-$("div.slider-nav").height(), $("#content_video_path_"+c_id).val(), $("#content_image_path_"+c_id).val());
	swfobject.embedSWF($("#resPath").val()+"swf/playerLite.swf", "content_video_"+c_id, flashvars.vidWidth, flashvars.vidHeight, "9.0.0",$("#resPath").val()+"swf/expressInstall.swf", flashvars, params, { id: "content_video_"+c_id, name: "content_video_"+c_id } );
}

$(document).ready(function() {
	
	var ll = $("div.temp_variable").length;
	if (ll>0){
		$("div.temp_variable").each(function(index, row){
			var c_id = $(row).attr('index');
			placeVideo_First(c_id);
			
			ll--;
			if (ll==0){
				$(".slider object").each(function(index, row){
					var t=$(this).attr('id');
					if (index!=0)
						swfobject.getObjectById(t).GotoFrame(1);
				});
				
				$('.slider-for').unslick();
				$('.slider-nav').unslick();
				
				$('.slider-for').slick({
				    slidesToShow: 1,
				    slidesToScroll: 1,
				    arrows: false,
				    fade: true,
				    asNavFor: '.slider-nav',
				    onAfterChange: afterChange,
				    onBeforeChange : beforeChange
				});
				 
				$('.slider-nav').slick({
				    slidesToShow: 4,
				    slidesToScroll: 1,
				    asNavFor: '.slider-for',
				    dots: false,
				    centerMode: false,
				    arrows: false,
				    focusOnSelect: true,
				    onAfterChange: afterChange,
				    onBeforeChange : beforeChange
				});
			}
		});
	}
	
	debugger
	
	$.ajax({
		
        url: 'loadChannels.html',
        data: {  },
        dataType: 'json', 
        success: function(data) {
//        	console.log(data);
        	if (data.errCode==0){
        		showData(data);
        	}
        },
        error: function() {
//        	hideLoading();
        }
    });
	
	$(window).resize(function(){
		if ($("div.temp_variable").length>0){
			var c_id = $("div.slider-for div.slick-active").attr('index');
			placeVideo(c_id);
		}
	});
	
});

