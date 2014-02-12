package controllers;
import static play.data.Form.form;

import java.util.List;

import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.*;
import models.*;
import views.html.*;

public class Application extends Controller {
	

    public static Result index() {
    	return ok(index.render("Testr"));
    }
    
//    public static Result createTest() {
//    	Test test = Form.form(Test.class).bindFromRequest().get();
//    	test.save();
//    	return ok(index.render(test.name));
//    } 
    
    public static Result createTest() {
    	Test test = Form.form(Test.class).bindFromRequest().get();
    	test.save();
        return ok(
            createTest.render(test)
        );
    } 
    
    public static Result getTests(){
    	List<Test> tests = new Model.Finder<>(long.class,Test.class).all();
    	return ok(Json.toJson(tests));
    }
    
    public static Result getQuestions(){
       	List<Question> questions = new Model.Finder<>(long.class,Question.class).all();
    	return ok(Json.toJson(questions));
    }
    
//    public static Result save() {
//        Form<Test> testForm = form(Test.class).bindFromRequest();
//        testForm.get().save();
//        flash("success", "Computer " + testForm.get().name + " has been created");
//        return ok(index.render("Testr"));
//    }
    
  
    public static Result addQuestion(long id){ // creates
    	Question question = Form.form(Question.class).bindFromRequest().get(); 
    	Test test = new Model.Finder<>(long.class, Test.class).byId(id);
    	test.numQuestions++;
    	question.assignTest(test);
    	question.save();
    	test.save();
		return ok(createTest.render(test));
    }
    
    
    public static Result takeTest(long id){
    	Test test = new Model.Finder<>(long.class,Test.class).byId(id);
    	return ok(takeTest.render(test));
    }
    
	public Question getQuestion(){
		List<Question> question = new Model.Finder<>(long.class,Question.class).all();
		for(int i = 0; i <= question.size();i++){
			Question q = question.get(i);
			if(null == q.test){ // null should be a test
				return q;
			}
		}
		return null;
	}
    
    
    public static Result addAnswers(long questionID, String[] answers, boolean[]correct ){
    	return TODO;
    }
    
    
   

}
