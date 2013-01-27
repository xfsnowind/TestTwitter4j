var FAIL = false;

$(document).ready(function(){

   if ("fail" == getURLParameter("login")){

      $("#userName").val("");
      $("#userPassword").val("");

      $("#message").removeClass("hide");
      $("#message").html("Wrong username or password!").show().fadeOut(1500);
   }

   $("#loginForm").submit(function(){
      var username = $("#userName").val();
      var password = $("#userPassword").val();

      detectNotNull(username, "username");
      if (false == FAIL){
         detectNotNull(password, "password");
         if (false == FAIL){
            detectWhitespace(username);
         }
      }

      if (false == FAIL){
         return true;
      }
      else{
         FAIL = false;
         return false;
      }
   });
   
   $("#userName").focus();

});

function getURLParameter(name) {
   return decodeURIComponent(
         (location.search.match(RegExp("[?&]"+name+"=([^&]*)"))||[,null])[1]
         );  
}

function detectWhitespace(content) {
   for (var i=0; i != content.length; i++) {
      if (content.charAt(i) == " ") {
         FAIL  = true;
      }
      if (content.charAt(i) == "@") {
         FAIL  = true;
      }
   }
   if (FAIL == true) alert("no characters as space or '@' allowed in username");
}

function detectNotNull(content, name) {
   if (content == "") {
      FAIL = true;
   }

   if (true == FAIL){
      alert("please enter " + name);
   }
}