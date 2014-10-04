 jQuery(document).ready(function () {
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#btnSave").attr("disabled", true);
				$("#btnCancel").attr("btnCancel", true);
				
				from[0].submit();
			}
		}
	});
 });