// Foundation JavaScript
// Documentation can be found at: http://foundation.zurb.com/docs
$(document).foundation();

$('.slider-for').slick({
    slidesToShow: 1,
    slidesToScroll: 1,
    arrows: false,
    fade: true,
    asNavFor: '.slider-nav'
});
 
$('.slider-nav').slick({
    slidesToShow: 4,
    slidesToScroll: 1,
    asNavFor: '.slider-for',
    dots: false,
    centerMode: false,
    focusOnSelect: true
});


$("#content-wrapper nav.main-menu a").hover(function(){
    w = $(this).css("width")
    $(this).find("+ul").css("width",w)
});


$("#content-wrapper nav.main-menu.desktop-menu ul ul").hover(

    function(){
        $(this).parent().find(">a").addClass("alpha-white-bg");
    },
    function(){
        $(this).parent().find(">a").removeClass("alpha-white-bg");
    }
    )

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
})

