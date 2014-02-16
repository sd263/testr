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
    	List<Test> tests = new Model.Finder<>(long.class,Test.class).all();
    	return ok(index.render(tests));
    }
       
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
  
    public static Result addQuestion(long id){ // creates
    	Question question = Form.form(Question.class).bindFromRequest().get(); 
    	Test test = new Model.Finder<>(long.class, Test.class).byId(id);
    	test.numQuestions++;
    	question.assignTest(test);
    	question.save();
    	test.save();
		return ok(createTest.render(test));
    }
    
    public static Result takeTest(int questionNum, long id){
    	Test test = new Model.Finder<>(long.class,Test.class).byId(id);
    	Question question = getQuestion(questionNum,test);
    	return ok(takeTest.render(questionNum,question));
    }
        
	public static Question getQuestion(int questionNum,Test test){
		List<Question> questions = Question.testQuestion(test);
		return questions.get(questionNum);
	}
    
    
    public static Result markQuestion( int answer, int question, long id){
    	return TODO;
    }
	
    
    public static Result addAnswers(long questionID, String[] answers, boolean[]correct ){ // will be added with the 
    	return TODO;
    }
    
    
   

}
