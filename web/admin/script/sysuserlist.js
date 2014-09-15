$(document).ready(function() {
	$('#sysuserlist').dataTable({
		"processing": true,
		"ajax": "sysuser.do?a=list",
		"columns": [
			{ "data": "id" },
			{ "data": "username" },
			{ "data": "pwd" },
			{ "data": "createTimeStr"}
		]
	} );
} );