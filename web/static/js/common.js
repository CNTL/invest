$(function () {
    $('#changeCode').click(function () {
        var codeImg = $('#code');
        codeImg.attr('src', codeImg.attr('src') + '?' + Math.random());
    });

    $('#roleSelect>li').click(function () {
        $('#roleSelect>li').removeClass('current');
        $(this).addClass('current');
        $('#role').val($(this).attr('data'));
    });

    $('.cate').mouseover(function() {
        $(this).addClass('cate_current');
        var expand = $(this).find('.expand');
        if (expand.length > 0) {
            expand.find('.blank').css('height', ($(this).height()+25)+'px');
            expand.show();
        }
    }).mouseout(function () {
        $(this).removeClass('cate_current');
        $(this).find('.expand').hide();
    });
    $("#hzusername").click(function(){
    	if(!$('.menu').hasClass("show")){
    	   $('.menu').addClass("show");
    	}
    	event.stopPropagation();

    });
    $("body").click(function(){
    	if($('.menu').hasClass("show")){
    		$('.menu').removeClass("show");
    	}
    });
//    $(".username").mouseover(function () {
//    	$('.menu').show();
//    });
//    $(".hzmini_member").mouseover(function () {
//    	$('.menu').show();
//    });
    $("#menulist").mouseout(function(){
    	//$('.menu').hide();
    });
    $('#k').focus(function() {
        $(this).val('');
    });
});