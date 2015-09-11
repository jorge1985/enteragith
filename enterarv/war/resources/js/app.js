$(document).foundation();

// Menu actions -----------------------------------------

var submenu_height = [];
var submenu_toggle = [];
var submenu_last_toggle;

$("ul.side-nav .submenu-wrapper").each(function(i){
	el_id = $(this).attr("id");
	el_height = $(this).css("height");
	submenu_height[el_id] = el_height;
	submenu_toggle[el_id] =  false
	$(this).css("height","0");

})


$("ul.side-nav label").click(function(){
	
	if($(this).not(":has(a)").length){

		el = $(this).next(".submenu-wrapper");
		el_id = el.attr("id");
		//console.log("actual: "+el_id)
		//console.log(submenu_toggle[el_id]);

		switch(submenu_toggle[el_id]){

			case false: 
				//console.log("expandir")
				el.css("height",submenu_height[el_id])
				submenu_toggle[el_id] = true;
			break;

			case true:
				el.css("height","0");
				submenu_toggle[el_id] = false;
			break;

		}


		if(submenu_last_toggle != el_id){
			if(typeof submenu_last_toggle !== 'undefined'){
				$("#"+submenu_last_toggle).css("height","0");
				submenu_toggle[submenu_last_toggle] = false;
				//console.log("anterior: "+submenu_last_toggle);
				//console.log("____________________")
			}
			submenu_last_toggle = el_id;
		}	

	}

});

// --------------------------------------------------------

