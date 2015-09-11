/**
 * Opportunities.
 */

function support_image(){
	return Modernizr.addTest('svgasimg', document.implementation.hasFeature('http://www.w3.org/TR/SVG11/feature#Image', '1.1'));
}

test_support = support_image();

if(!test_support.svgasimg){
    $("img").each(function(){
        src = this.src;
        if(src.indexOf(".svg") > -1){
            //console.log(src)
           // alert(src)
            $(this).attr("src",src.replace(".svg",".png")).css("width","auto")
        }
    })

    $(".row#mobile-menu .small-16 .columns.tools").css("background","url('"+$("#resPath").val()+"images/bbva-color-hstrip.png') repeat-x");
    //console.log($(".row#mobile-menu .small-16 .columns.tools").css("background"))
}


$(document).ready(function() {

});
