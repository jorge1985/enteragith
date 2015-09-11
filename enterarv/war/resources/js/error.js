/**
 * Error page. 
 */

var menuLength=0;

var mainMenu = [
];

var slIndex=new Array();

function createMenu(m,c){
    list = '';
    var cl='';
    if (c==0){
    	cl='class="family_item"';
    }
    
    $.each(m, function(k,v){
        if (v.sub instanceof Array){
            list += '<li><a '+cl+' href="'+v.url+'">'+v.label+'</a><a class="mobile_submenu" data-column="'+c+'" data-index="'+k+'" href="#">'+' <i class="fa fa-angle-right fa-fw"></i></a></li>';
        }
        else{
            list += '<li><a '+cl+' data-column="'+c+'" data-index="'+k+'" href="'+v.url+'">'+v.label+'</a></li>';
        }
    })

    $("#menu-modal .row .menucol").eq(c).find("ul").html(list);
}

$(document).on("click","#menu-modal .menucol ul li a.mobile_submenu",function(e){

	column = $(this).data("column");
	index = $(this).data("index");

	$(this).parents("ul").find("a").removeClass("selected");
	$(this).addClass("selected");

	$(this).parents("ul").find("a.mobile_submenu").find("i").removeClass("fa-angle-left").addClass("fa-angle-right");
	$(this).find("i").removeClass("fa-angle-right").addClass("fa-angle-left");

	//console.log(column+" - "+index);
	e.preventDefault();

	switch(column){
	case 0:
		$("#menu-modal .row .menucol ul:gt(0)").each(function(ind, row){
			$(row).html('');
		});
		
		if(mainMenu[index].sub instanceof Array){
			createMenu(mainMenu[index].sub,1);
			e.preventDefault();
		}

		slIndex[0] = index;
		break;

	case 1:
		$("#menu-modal .row .menucol ul:gt(1)").each(function(ind, row){
			$(row).html('');
		});
		
		if(mainMenu[slIndex[0]].sub[index].sub instanceof Array){ 
			createMenu(mainMenu[slIndex[0]].sub[index].sub,2);
			e.preventDefault();
		}
		
		slIndex[1]=index;
		break;

	case 2:
		$("#menu-modal .row .menucol ul:gt(2)").each(function(ind, row){
			$(row).html('');
		});
		
		if(mainMenu[slIndex[0]].sub[slIndex[1]].sub[index].sub instanceof Array){ 
			createMenu(mainMenu[slIndex[0]].sub[slIndex[1]].sub[index].sub,3);
			e.preventDefault();
		}
		
		slIndex[2]=index;
		break;
		
	case 3:
		$("#menu-modal .row .menucol ul:gt(3)").each(function(ind, row){
			$(row).html('');
		});
		
		if(mainMenu[slIndex[0]].sub[slIndex[1]].sub[slIndex[2]].sub[index].sub instanceof Array){ 
			createMenu(mainMenu[slIndex[0]].sub[slIndex[1]].sub[slIndex[2]].sub[index].sub,4);
			e.preventDefault();
		}
		
		slIndex[3]=index;
		break;
		
	case 4:
		$("#menu-modal .row .menucol ul:gt(4)").each(function(ind, row){
			$(row).html('');
		});
		
		if(mainMenu[slIndex[0]].sub[slIndex[1]].sub[slIndex[2]].sub[slIndex[3]].sub[index].sub instanceof Array){ 
			createMenu(mainMenu[slIndex[0]].sub[slIndex[1]].sub[slIndex[2]].sub[slIndex[3]].sub[index].sub,5);
			e.preventDefault();
		}
		
		slIndex[4]=index;
		break;
		
	case 5:
		$("#menu-modal .row .menucol ul:gt(5)").each(function(ind, row){
			$(row).html('');
		});
		
		if(mainMenu[slIndex[0]].sub[slIndex[1]].sub[slIndex[2]].sub[slIndex[3]].sub[slIndex[4]].sub[index].sub instanceof Array){ 
			createMenu(mainMenu[slIndex[0]].sub[slIndex[1]].sub[slIndex[2]].sub[slIndex[3]].sub[slIndex[4]].sub[index].sub,6);
			e.preventDefault();
		}
		
		slIndex[5]=index;
		break;
		
	case 6:
		$("#menu-modal .row .menucol ul:gt(6)").each(function(ind, row){
			$(row).html('');
		});
		
		if(mainMenu[slIndex[0]].sub[slIndex[1]].sub[slIndex[2]].sub[slIndex[3]].sub[slIndex[4]].sub[slIndex[5]].sub[index].sub instanceof Array){ 
			createMenu(mainMenu[slIndex[0]].sub[slIndex[1]].sub[slIndex[2]].sub[slIndex[3]].sub[slIndex[4]].sub[slIndex[5]].sub[index].sub,7);
			e.preventDefault();
		}
		
		slIndex[6]=index;
		break;
		
	case 7:
		$("#menu-modal .row .menucol ul:gt(7)").each(function(ind, row){
			$(row).html('');
		});
		
		if(mainMenu[slIndex[0]].sub[slIndex[1]].sub[slIndex[2]].sub[slIndex[3]].sub[slIndex[4]].sub[slIndex[5]].sub[slIndex[6]].sub[index].sub instanceof Array){ 
			createMenu(mainMenu[slIndex[0]].sub[slIndex[1]].sub[slIndex[2]].sub[slIndex[3]].sub[slIndex[4]].sub[slIndex[5]].sub[slIndex[6]].sub[index].sub,8);
			e.preventDefault();
		}
		
		slIndex[7]=index;
		break;
		
	default:
		console.log("Error");
		break;
	}
});

function getSubTree2(data, level){
	var has=false;
	var sub_tree = [];
	
	if (menuLength<level)
		menuLength=level;
	
	$.each(data, function(index, row){
		has=true;
		
		var temp = getSubTree2(row.child, level+1);
		sub_tree.push(	{	label: row.parent.name, url: 'javascript:channel(\''+row.parent.id+'\')', sub: temp } );
	});
	
	if (has)
		return sub_tree;
	else
		return null;
}

$(document).foundation();

function support_image(){
	return Modernizr.addTest('svgasimg', document.implementation.hasFeature('http://www.w3.org/TR/SVG11/feature#Image', '1.1'));
}

function menuModalPos(){
    arrowOffset = $(".arrow-menu").offset();
    $('#menu-modal').css({"right":"auto","left":arrowOffset.left+$(".arrow-menu").width()});
}

$(".arrow-menu").click(function(){
    $(this).parent("#site-tools-desktop ul li").css({"z-index":"100000","position":"relative"});
    $('#menu-modal').foundation('reveal', 'open',{ animation: 'fade' });

    newimage_src = (support_image().svgasimg)?"arrow-left.svg":"arrow-left.png"; 
    //console.log(newimage_src) 
    $(this).find("img").attr("src",$("#resPath").val()+"images/icons/"+newimage_src);
});


$(document).on('closed.fndtn.reveal', '[data-reveal]', function () {
  var modal = $(this);
  //console.log(modal.attr("id"));
  if(modal.attr("id") == "menu-modal"){
    newimage_src = (support_image().svgasimg)?"arrow-right.svg":"arrow-right.png";
    $(".arrow-menu").find("img").attr("src",$("#resPath").val()+"images/icons/"+newimage_src);
  }
});

$("img").each(function(){
    src = this.src;
    if(src.indexOf(".svg") > -1){
        //console.log(src)
       // alert(src)
        $(this).attr("src",src.replace(".svg",".png"))
    }
});

$(".row#mobile-menu .small-16 .columns.tools").css("background","url("+$("#resPath").val()+"'images/bbva-color-hstrip.png') repeat-x");

$(window).resize(function(){ menuModalPos(); });

$("#mobile-menu .tools a.a-button").click(function(){
    w = $(this).css("width");
    $("#mobile-site-tools").css("width",w);
    $("#mobile-site-tools").toggle();
    return false;
});


$("a.mm-button").click(function(){
    w = $(this).parent(".columns").css("width");
    $menu = $("#mobile-menu nav.main-menu");
    $menu.css("width",w);
    $menu.slideToggle();
});


function getSubTree(data){
	var has=false;
	var sub_tree = '';
	
	$.each(data, function(index, row){
		has=true;
		sub_tree += '<li>' + 
				'<div><a href="javascript:channel(\'' + row.parent.id + '\')">' + 
				''+row.parent.name+'</a>'
				;
		
		var temp = '';
		temp = getSubTree(row.child);
		if (temp!=''){
			sub_tree += '<a class="mobile_submenu" href="#"><i class="fa fa-angle-right fa-fw"></i></a>';
			sub_tree += '</div>';
			sub_tree += '<ul>'+ temp + '</ul>';
		}else{
			sub_tree += '</div>';
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
		
		var tree=getSubTree(row.child);
		sub=tree;
		
		$("#category_mobile_"+row.parent.id).html('');
		$("#category_mobile_"+row.parent.id).append(sub);

		var sub2=getSubTree2(row.child, 1);
		mainMenu.push(	{	label: row.parent.name, url: '#', sub: sub2	} );
	});
	
	for (var i=1; i<menuLength; i++){
		$("#menu-modal >div.row").append('<div class="menucol"><ul></ul></div>');
	}
	$("#menu-modal .row .menucol").css('width', 100/menuLength + '%');

    menuModalPos();
    createMenu(mainMenu, 0);
	
	$("#mobile-menu nav.main-menu ul a.mobile_submenu, nav.main-menu.aside-menu ul a.mobile_submenu").click(function(){
        $(this).parent().parent().find(">ul").slideToggle();
	});
}

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

function play_video(path){
	if (path=='')
		return;
	
	$.fancybox.open(  {
		type: 'iframe',
		href:  path
	} , {
		autoSize	: true,
		openEffect	: 'elastic',
		closeEffect	: 'fade' 
	}  );
}

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
        		showData(data);
        	}
        },
        error: function() {
//        	hideLoading();
        }
    });
	
});

