package controllers;

import play.*;
import play.mvc.*;
import views.html.*;
import models.*;

public class Application extends Controller {
	
	
    public static Result GO_HOME = redirect(
            routes.Application.list(0, "name", "asc", "")
        );

    public static Result index() {
    	return GO_HOME;
    }
    
    public static Result createTest() {
		return TODO;
    	
    }
    
    
    
    public static Result list(int page, String sortBy, String order, String filter) {
        return ok(
            list.render(
                Test.page(page, 10, sortBy, order, filter),
                sortBy, order, filter
            )
        );
    }

}
