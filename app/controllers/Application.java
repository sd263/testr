package controllers;
import java.util.List;

import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.*;
import models.*;
import views.html.*;

public class Application extends Controller {
	

    public static Result index() {
    	return ok(index.render("hey"));
    }
    
    public static Result createTest() {
    	Test test = Form.form(Test.class).bindFromRequest().get();
    	test.save();
    	return redirect(routes.Application.index());
    } 
    
    public static Result getTests(){
    	List<Test> tests = new Model.Finder<>(long.class,Test.class).all();
    	return ok(Json.toJson(tests));
    }
    
  
    public static Result addQuestion(long testID){ // creates
    	return TODO;
    }
    
    public static Result addAnswers(long questionID, String[] answers, boolean[]correct ){
    	return TODO;
    }
    
    
   

}
