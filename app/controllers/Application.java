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

	public static Result index() {
		List<Test> tests = new Model.Finder<>(long.class, Test.class).all();
		return ok(index.render(tests));
	}

	public static Result createTest() {
		Test test = Form.form(Test.class).bindFromRequest().get();
		test.save();
		return ok(createTest.render(test));
	}

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

	public static Result addQuestion(long id) { // creates
		Question question = Form.form(Question.class).bindFromRequest().get();
		Test test = new Model.Finder<>(long.class, Test.class).byId(id);
		test.numQuestions++;
		question.assignTest(test);
		question.save();
		test.save();
		return ok(createTest.render(test));
	}

	public static Result beginTest(long id) {
		TestAnswer testAnswer = new TestAnswer(0, id);
		testAnswer.save();
		return ok(takeTest.render(1, testAnswer));
	}

	public static Result markQuestion(int answer, long id) {
		flash("success", "Computer has been deleted");
		TestAnswer testAnswer = new Model.Finder<>(long.class, TestAnswer.class)
				.byId(id);
//		testAnswer.studentAnswer.add(answer);
//		if (testAnswer.questions.get(testAnswer.current).correctAnswer == answer + 1) {
			testAnswer.current++;
			if (testAnswer.test.numQuestions <= testAnswer.current)
				return ok(testResult.render(testAnswer));
			testAnswer.save();
			return ok(takeTest.render(testAnswer.current, testAnswer));
//		}
//		flash("success", "Wrong answer bro");
//		return index();
	}

}
