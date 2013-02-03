$(document).ready(function(){
	if (null == getURLParameter("oauth_token") || null == getURLParameter("oauth_verifier")){
		window.location = "TestTwitter4j.html";
    }
	else {
		$.ajax({url : "t4j/",
			   data : {operation: "getAccessToken", 
				   	   oauthtoken : getURLParameter("oauth_token"), 
				   	   oauthverifier: getURLParameter("oauth_verifier")},
			   type: "POST",
			   dataType : "json"});
	}
});

function getURLParameter(name) {
	return decodeURIComponent((location.search.match(RegExp("[?&]"+name+"=([^&]*)"))||[,null])[1]);  
}