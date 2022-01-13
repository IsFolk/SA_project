<!--從URL接收參數-->
<!--<script src="statics/js/link.js"></script>-->

	var url_string = window.location.href;
	var url = new URL(url_string);
	var id = url.searchParams.get("id");
	function getValue(varname){
	  var url = window.location.href;
	  var qparts = url.split("?");
	  if (qparts.length == 0){
		return "";
		}
		  var query = qparts[1];
		  var vars = query.split("&amp;");
		  var value = "";
		  for (i=0; i<vars.length; i++){
			var parts = vars[i].split("=");
			if (parts[0] == varname){
			  value = parts[1];
			  break;
			}
		  }
	  value = unescape(value);
	  value.replace(/\+/g," ");
	  return value;
	}
	
	var id = getValue("id");
