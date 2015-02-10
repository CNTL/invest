
var timeout         = 500;
var closetimer		= 0;
var ddmenuitem      = 0;
var backitem        = 0;
var hoveritem       = 0;
var whiteitem       = 0;
function jsddm_open()
{	
	jsddm_canceltimer();
	jsddm_close();
	ddmenuitem =$('#jsddm').find('.myinfo').show();
	ddmenuitem =$('#jsddm').find('.JS-myinfo').show();
	backitem = $('#jsddm').find('a.tit').addClass("active");
	hoveritem = $('#jsddm').find('a span.xm').css('color', '#190302');	
	whiteitem =$('.flinfo .white').css('visibility', 'hidden');
}

function jsddm_close()
{	if(ddmenuitem) ddmenuitem.hide();
	if(backitem) backitem.removeClass("active");
	if(hoveritem) hoveritem.css('color', '#190302');
	if(whiteitem) whiteitem.css('visibility', 'hidden');
}

function jsddm_timer()
{	
	closetimer = window.setTimeout(jsddm_close, timeout);
}

function jsddm_canceltimer()
{	if(closetimer)
	{	window.clearTimeout(closetimer);
		closetimer = null;
	}
}
$(document).ready(function(){
	$('#jsddm').bind('mouseover', jsddm_open);
	$('#jsddm').bind('mouseout',  jsddm_timer);	
});