$(document).ready(function () {
	 
	$("#sl-city").popover({
		container:"body",
		title:"选择订阅城市",
		html:true,
		content:$("#morecity").html(),
		trigger:"click"
	});
	 
	
});

function selectcity(){
    $("#sl-city").trigger("click");
    var id= $(event.srcElement).attr("data-id");
    var name = $(event.srcElement).attr("data-name");
    $("#city").text(name);
    $("#hcityid").va(id);
    $("#hcityname").va(name);
}

function selectrec(){
    var id= $(event.srcElement).attr("data-id");
    var name = $(event.srcElement).attr("data-name");
    $("#sl-rec").text(name);
    $("#hrecid").va(id);
    $("#hrecname").va(name);
}