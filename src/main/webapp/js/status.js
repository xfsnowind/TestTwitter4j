$(document).ready(function() {
	$.ajax({
		url: "t4j/",
		data : {operation:"showstatus"},
		type : "GET",
		dataType : "json",
		success : function(data) {
			$("#status").text(data);
		}
	});
});