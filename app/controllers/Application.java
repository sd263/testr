package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.List;

import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.*;
import models.*;
import views.html.*;

public class Application extends Controller {
	
	public static Result loginScreen(){
		return ok(login.render());
	}
	
	public static Result studentHome(){
		List<Test> tests = new Model.Finder<>(long.class, Test.class).all();
		return ok(studentHome.render(tests));
	}
	
	public static Result teacherHome(){
		List<TestReview> tests = new Model.Finder<>(long.class, TestReview.class).all();
		return ok(teacherHome.render(tests));
	}

	public static Result createTest() {
		Test test = Form.form(Test.class).bindFromRequest().get();
		test.save();
		return ok(createTest.render(test));
	}
	
	public static Result publishTest(long id){
		Test test = new Model.Finder<>(long.class, Test.class).byId(id);

		if(test.numQuestions <= 0){
			test.delete();
			flash("notcreated", "your test has not been made");
		} else{
			flash("published", test.name);
			TestReview testReview = new TestReview(test);
			testReview.save();
		}
		return teacherHome();
	}
	
	public static Result addQuestion(long id) { // creates
		Question question = Form.form(Question.class).bindFromRequest().get();
		Test test = new Model.Finder<>(long.class, Test.class).byId(id);
		test.numQuestions++;
		test.addQuestion(question);
		test.save();
		return ok(createTest.render(test));
	}

	public static Result beginTest(long id) {
		TestReview testReview = new Model.Finder<>(long.class, TestReview.class)
				.byId(id);
		TestAnswer testAnswer = new TestAnswer(0, id);
		testReview.studentAnswers.add(testAnswer);
		testReview.save();
		testAnswer.save();
	
		return ok(takeTest.render(1, testAnswer));
	}

	public static Result markQuestion(int answer, long id) {

		TestAnswer testAnswer = new Model.Finder<>(long.class, TestAnswer.class)
				.byId(id);
//		testAnswer.addAnswer(answer);
		if (testAnswer.test.questions.get(testAnswer.current).correctAnswer == answer + 1){
			flash("correct", "");
			testAnswer.markCorrect();
		} else{
			flash("wrong",Question.getAnswer(testAnswer.test.questions.get(testAnswer.current), testAnswer.test.questions.get(testAnswer.current).correctAnswer-1));
		}

			testAnswer.current++;
			if (testAnswer.test.numQuestions <= testAnswer.current)
				return ok(testResult.render(testAnswer));
			testAnswer.save();
			return ok(takeTest.render(testAnswer.current, testAnswer));

	}
	
	// JSON USED FOR DEBUGGING

	public static Result getTests() {
		List<Test> tests = new Model.Finder<>(long.class, Test.class).all();
		return ok(Json.toJson(tests));
	}

	public static Result getTestAnswers() {
		List<TestAnswer> data = new Model.Finder<>(long.class, TestAnswer.class)
				.all();
		return ok(Json.toJson(data));
	}

	public static Result getQuestions() {
		List<Question> questions = new Model.Finder<>(long.class,
				Question.class).all();
		// Test test = new Model.Finder<>(long.class,Test.class).byId((long) 1);
		// List<Question> quest = Question.testQuestion(test);
		return ok(Json.toJson(questions));
	}
	
	public static Result getTestReview() {
		List<TestReview> testreview = new Model.Finder<>(long.class,
				TestReview.class).all();
		return ok(Json.toJson(testreview));
	}

}
