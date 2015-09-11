/**
 * Public Menu page. 
 */

$(document).foundation('reveal', 'reflow');

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
            list += '<li><a pos="'+v.pos+'" class="have-menu" data-column="'+c+'" data-index="'+k+'" href="'+v.url+'">'+v.label+' <i class="fa fa-angle-right fa-fw"></i></a></li>';
        }
        else{
            list += '<li><a pos="'+v.pos+'" '+cl+' data-column="'+c+'" data-index="'+k+'" href="'+v.url+'">'+v.label+'</a></li>';
        }
    });

    $("#menu-modal .row .menucol").eq(c).find("ul").html(list);
}

function support_image(){
	return Modernizr.addTest('svgasimg', document.implementation.hasFeature('http://www.w3.org/TR/SVG11/feature#Image', '1.1'));
}

function menuModalPos(){
    arrowOffset = $(".arrow-menu").offset();
    $('#menu-modal').css({"right":"auto","left":arrowOffset.left+$(".arrow-menu").width()});
}

function open_search(){
	$('#search-modal').foundation('reveal', 'open', {
	    close_on_background_click: false,
	    url: $('#resPath').val()+'popup_search.jsp',	
	    data: {   },
	    success: function(data) {
	    },
	    error: function() {
	    }	    
	});
}

function close_search(){
	$('#search-modal').foundation('reveal', 'close');
	$('#search-not-found').hide()
}

$("#mobile-menu .tools a.a-button").click(function(){
    w = $(this).css("width");
     $("#mobile-site-tools").css("width",w);
    $("#mobile-site-tools").toggle();
    return false;
});


$("a.mm-button").click(function(){
    //console.log("hi")
    w = $(this).parent(".columns").css("width");
    $menu = $("#mobile-menu nav.main-menu");
    $menu.css("width",w);
    $menu.slideToggle();
});

$(document).on("click","#menu-modal .menucol ul li a.have-menu",function(e){
	
	column = $(this).data("column");
	index = $(this).data("index");

	$(this).parents("ul").find("a").removeClass("selected");
	$(this).addClass("selected");

	$(this).parents("ul").find("a").find("i").removeClass("fa-angle-left").addClass("fa-angle-right");
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
		
	default:
		console.log("Error");
		break;
	}
});

$(".arrow-menu").click(function(){
	arrow_src = $(this).find("img").attr("src");

	if(arrow_src.indexOf("right") > -1){
        $(this).parent("#site-tools-desktop ul li").css({"z-index":"100000","position":"relative"});
        $('#menu-modal').foundation('reveal', 'open',{ animation: 'fade' });
        newimage_src = (support_image().svgasimg)?"arrow-left.svg":"arrow-left.png"; 
        $(this).find("img").attr("src",$("#resPath").val()+"images/icons/"+newimage_src);
    }
    else{
        //console.log("cerrado")
        newimage_src = (support_image().svgasimg)?"arrow-right.svg":"arrow-right.png";
        $(this).find("img").attr("src",$("#resPath").val()+"images/icons/"+newimage_src);
        $('#menu-modal').foundation('reveal', 'close',{ animation: 'fade' });
    }
});

$(document).on('closed.fndtn.reveal', '[data-reveal]', function () {
	  var modal = $(this);
	  //console.log(modal.attr("id"));
	  if(modal.attr("id") == "menu-modal"){
		  newimage_src = (support_image().svgasimg)?"arrow-right.svg":"arrow-right.png";
		  $(".arrow-menu").find("img").attr("src",$("#resPath").val()+"images/icons/"+newimage_src);
		  $(".arrow-menu").parent("#site-tools-desktop ul li").css({"z-index":"1"});
	  }
	});

/*$(document).on('opened.fndtn.reveal', '[data-reveal]', function () {
            
    $(".menucol:eq(1)").css("height",$(".menucol:eq(0)").height()+"px")
    $(".menucol:eq(2)").css("height",$(".menucol:eq(0)").height()+"px")

});*/

test_support = support_image();

if(!test_support.svgasimg){
    $("img").each(function(){
        src = this.src;
        if(src.indexOf(".svg") > -1){
            //console.log(src)
           // alert(src)
            $(this).attr("src",src.replace(".svg",".png"))
        }
    })

    $(".row#mobile-menu .small-16 .columns.tools").css("background","url('"+$("#resPath").val()+"images/bbva-color-hstrip.png') repeat-x");
    //console.log($(".row#mobile-menu .small-16 .columns.tools").css("background"))
}

$(document).ready(function() {
	$(window).resize(function(){ menuModalPos(); });
});

