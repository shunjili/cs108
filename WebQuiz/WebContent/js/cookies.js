/**
 * 
 */
function setCookie(cname, cvalue, exdays){
	var d = new Date();
	d.setTime(d.getTime() + (exdays * 24 * 60 * 60 *1000));
	var expires = "expires=" + d.toGMTString();
	document.cookie = cname + "=" + cvalue + "; " + expires;
}

function getCookie(cname){
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for(var i = 0; i < ca.length; i++){
		var c = ca[i].trim();
		if(c.indexOf(name)==0)
			return c.substring(name.length, c.length);
	}
	return "";
}

function checkCookie(){
	var username = getCookie("username");
	if(username!="" && username!=null){
		//window.location.href = "/WebQuiz/ViewMyAccount.jsp" + "&sender = username";//"welcome.jsp?name="+username + "&sender=" + username;
	}
}