$ ->
	$.get "/tests", (data) ->
		$.each data, (index,test) ->
			$("#tid").append $("<tr>").text test.id
			$("#tname").append $("<tr>").text test.name
			$("#tdesc").append $("<tr>").text test.testDesc	
			$("#tnum").append $("<tr>").text test.numQuestions
			$("#tselect").append $("<option>").text test.id
			
		
`$(document).ready(function()
{
    $("#tselect").change(function()
    {
        var id=$(this).val();
        var dataString = 'id='+ id;
        alert(id);
    });

});
`

@getId = ->
	@routes.Application.takeTest(1)