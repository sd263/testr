$ ->
	$.get "/tests", (data) ->
		$.each data, (index,test) ->
			$("#tid").append $("<tr>").text test.id
			$("#tname").append $("<tr>").text test.name
			$("#tdesc").append $("<tr>").text test.testDesc	
			$("#tnum").append $("<tr>").text test.numQuestions
			
			$("#tselect").append $("<option>").text test.id
			
		
