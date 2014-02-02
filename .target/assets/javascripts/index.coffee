$ ->
	$.get "/tests", (data) ->
		$.each data, (index,test) ->
			$("#tname").append $("<tr>").text test.name
			$("#tdesc").append $("<tr>").text test.testDesc	
			
