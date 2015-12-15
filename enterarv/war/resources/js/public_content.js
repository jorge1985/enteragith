/**
 * Public content page. 
 */

var bFound=false;
var menuLevel=new Array();
var lastLevel=0;
var lastMenu=new Array();



function findMenu(menu, channel, level){
	for (var i=0; i<menu.length; i++){
		if (bFound)
			return true;
		
		if (menu[i].sub!=null){
			menuLevel[level]=i;
			findMenu(menu[i].sub, channel, level+1);
		}
		
		if (menu[i].pos==channel){
			menuLevel[level]=i;
			lastLevel=level;
			bFound = true;
			return true;
		}
	}
}

function expandMenu(menu_id, channel){
	$("#"+menu_id + " li[pos="+channel+"]").parent('ul').css('display', 'block');
	var row = $("#"+menu_id + " li[pos="+channel+"]").parent('ul').parent('li');
	if (row.length>0){
		var pos = row.attr('pos');
		if (pos!=''){
			expandMenu(menu_id, pos);
		}
	}
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
			sub_tree.push(	{	pos: row.parent.id,  label: row.parent.name, url: 'javascript:channel(\''+row.parent.id+'\')', sub: temp } );
		}else{
			sub_tree.push(	{	pos: row.parent.id,  label: row.parent.name, url: '#', sub: temp } );
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

//function showData(data){
//	$.each(data.list, function(index, row){
//		var sub="";
//		
//		var tree=getSubTree(row.child);
//		sub=tree;
//		
//		$("#category_mobile_"+row.parent.id).html('');
//		$("#category_mobile_"+row.parent.id).append(sub);
//
//		var sub2=getSubTree2(row.child, 1);
//		mainMenu.push(	{	label: row.parent.name, pos:'', url: '#', sub: sub2	} );
//	});
//	
////	for (var i=1; i<menuLength; i++){
////		$("#menu-modal >div.row").append('<div class="menucol"><ul></ul></div>');
////	}
////	$("#menu-modal .row .menucol").css('width', 100/menuLength + '%');
//
//    menuModalPos();
//    createMenu(mainMenu, 0);
//	
//    $("#mobile-menu nav.main-menu ul a.have-submenu").click(function(){
//        $(this).parent().find(">ul").slideToggle();
//    });
//    
////	$("#mobile-menu nav.main-menu ul a.mobile_submenu, nav.main-menu.aside-menu ul a.mobile_submenu").click(function(){
////        $(this).parent().parent().find(">ul").slideToggle();
////	});
//	
//	var channel=$("#edit_channel_id").val();
//	if (channel!=''){
//		findMenu(mainMenu, channel, 0);
//		
//		if (bFound){
//			var mmm=mainMenu;
//			for (var i=0; i<=lastLevel; i++){
//				slIndex[i]=menuLevel[i];
//				
//				if (i==lastLevel){
//					$("#menu-modal .menucol ul li a[pos="+channel+"]").addClass('family_item').addClass('marked');
//				}else{
//					mmm=mmm[menuLevel[i]].sub;
//					createMenu(mmm, i+1);
//				}
//			}
//			
//			for (var i=0; i<lastLevel; i++){
//				$("#menu-modal .menucol ul").eq(i).find('li').eq(menuLevel[i]).find('a.have-menu').addClass('selected');
//				$("#menu-modal .menucol ul").eq(i).find('li').eq(menuLevel[i]).find('a.have-menu i').removeClass("fa-angle-right").addClass("fa-angle-left");
//			}
//			
//		}
//		
////		$("#"+"sidebar_main_menu" + " li[pos="+channel+"] a").first().addClass('family_item').addClass('selected').attr('href','#');
////		expandMenu("sidebar_main_menu", channel);
////		expandMenu("sidebar_mobile_menu", channel);
////		$("a.mm-button").trigger('click');
//	}
//}

function channel(id){
	$("#edit_channel_id").val(id);
	$("#form_move_channel").submit();
}

function go_back(){
	$("#form_move_home").submit();	
}

function file_download(url){
	$.fileDownload(url);	
}

//function play_video(path){
//	if (path=='')
//		return;
//	
//	$.fancybox.open(  {
//		type: 'iframe',
//		href:  path
//	} , {
//		autoSize	: true,
//		openEffect	: 'elastic',
//		closeEffect	: 'fade' 
//	}  );
//}

function poll_submit(){
	var ans = new Array();
	var c = 0;
	$("input.poll_answer").each(function(index, row){
		var v = $(this).val();
		if (v!=''){
			ans[c]={ question: $(this).attr('poll'), answer : v, answer_id: '0'	};
			c++;
		}
	});
	
	$("input[type=radio]:checked").each(function(index, row){
		var v = $(this).attr('answer');
		var q = $(this).attr('question');
		
		if (v!=''){
			ans[c]={ question: q, answer_id : v	, answer: '' };
			c++;
		}
	});
	

	if (c>0){
	   	$.ajax({
	   		type: "POST",
	        url: 'insertAnswer.html',
	        data: { content_id: $("#edit_content_id").val(), count: c, question: ans  },
	        dataType: 'json', 
	        success: function(data) {
//	        	showAlert(data.errMsg)
	        	if (data.errCode==0){
	        		$("#form_move_content").submit();
	        	}
	        },
	        error: function() {
	        }
	   	});
	}
}

$(document).ready(function() {
	$.ajax({
        url: 'loadChannels.html',
        data: {  },
        dataType: 'json', 
        success: function(data) {
//        	console.log(data);
        	if (data.errCode==0){
//        		showData(data);
        	}
        },
        error: function() {
//        	hideLoading();
        }
    });
});



