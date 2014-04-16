package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.*;
import models.*;
import views.html.*;

public class Application extends Controller {

	// Home screen and login
	public static Result loginScreen() {
		List<Classroom> classes = new Model.Finder<>(long.class,
				Classroom.class).all();
		Form<Student> studentForm = form(Student.class);
		Form<Teacher> teacherForm = form(Teacher.class);
		List<Student> students = new Model.Finder<>(long.class, Student.class)
				.all();
		List<Teacher> teachers = new Model.Finder<>(long.class, Teacher.class)
				.all();
		return ok(login.render(classes, studentForm, teacherForm, students,
				teachers));
	}

	public static Result login() {
		Form<Student> studentLogin = form(Student.class).bindFromRequest();
		Form<Teacher> teacherLogin = form(Teacher.class).bindFromRequest();
		List<Teacher> teachers = new Model.Finder<>(long.class, Teacher.class)
				.all();
		List<Student> students = new Model.Finder<>(long.class, Student.class)
				.all();
		for (Teacher teacher : teachers) {	
			if (teacher.name.equals(teacherLogin.get().name) && teacher.password.equals(teacherLogin.get().password)) {
				return teacherHome(teacher.id);
			}
		}
		for (Student student : students) {
			if (student.name.equals(studentLogin.get().name) && student.password.equals(studentLogin.get().password)) {
				return studentHome(student.id);
			}
		}
		flash("wrongLogin", "Your username or password is incorrect!");
		return loginScreen();
	}

	public static Result studentHome(Long id) {
		Student student = new Model.Finder<>(long.class, Student.class)
				.byId(id);
		List<Classroom> inCroom= Classroom.getClassWithStudent(student);
		List<Test> tests = Test.getTestsForStudent(inCroom,student);
		List<Classroom> outCroom = Classroom.getClassWithoutStudent(student);
		return ok(studentHome.render(inCroom,outCroom,tests, student));
	}

	public static Result teacherHome(Long id) {
		Teacher teacher = new Model.Finder<>(long.class, Teacher.class)
				.byId(id);
		List<Test> tests = Test.getTestForTeacher(teacher);
		Form<Test> testForm = form(Test.class);
		return ok(teacherHome.render(tests,testForm, teacher));
	}

	// creating students and classrooms

	public static Result createClassroom(long id) {
		Classroom classroom = Form.form(Classroom.class).bindFromRequest()
				.get();
		Teacher teacher = new Model.Finder<>(long.class, Teacher.class)
				.byId(id);
		// breaks the program
		teacher.addClassroom(classroom);
		teacher.save();
		return teacherHome(id);
	}
	
	public static Result createTest() {
		Test test = Form.form(Test.class).bindFromRequest().get();	
		test.openTest();
		Classroom classroom = new Model.Finder<>(long.class, Classroom.class)
				.byId(test.classId);
		 classroom.addTest(test); // breaks the program
		classroom.save();
		return ok(createTest.render(test, classroom));
	}

	public static Result joinClassroom(long id, long classid) {
		Student student = new Model.Finder<>(long.class, Student.class)
				.byId(id);
		Classroom classroom = new Model.Finder<>(long.class, Classroom.class)
				.byId(classid);	
		classroom.addStudent(student);
		classroom.save();
		return studentHome(id);
	}

	public static Result createTeacher() {
		Form<Teacher> teacherForm = form(Teacher.class).bindFromRequest();
		if (!teacherForm.hasErrors()) {
			flash("accountCreated", "Teacher account created!");
			teacherForm.get().save();
		}
		return loginScreen();
	}

	public static Result createStudent() {
		Form<Student> studentForm = form(Student.class).bindFromRequest();
		if (!studentForm.hasErrors()) {
			flash("accountCreated", "Student account created!");
			studentForm.get().save();
		}
		return loginScreen();
	}
	
	public static Result addQuestion(long classroomId) { // creates
		Classroom classroom = new Model.Finder<>(long.class, Classroom.class)
				.byId(classroomId);
		Question question = Form.form(Question.class).bindFromRequest().get();
		Test test = classroom.tests.get(classroom.tests.size()-1);
		test.numQuestions++;
		test.addQuestion(question);
		test.save();
		return ok(createTest.render(test, classroom));
	}

	public static Result publishTest(long id, long classroomId) {
		Test test = new Model.Finder<>(long.class, Test.class).byId(id);
		Classroom classroom = new Model.Finder<>(long.class, Classroom.class)
				.byId(classroomId);
		if (test.numQuestions <= 0) {
			test.delete();
			flash("notcreated", "your test has not been made");
		} else {
			flash("published", test.name);
		}
		Teacher teacher = Teacher.findTeacherByClass(classroom);
		return teacherHome(teacher.id); // test.classroom.teacher.id
	}
	
	public static Result scrapTest(long id, long classroomId){
		Test test = new Model.Finder<>(long.class, Test.class).byId(id);
		Classroom classroom = new Model.Finder<>(long.class, Classroom.class)
				.byId(classroomId);
		test.delete();
		flash("notcreated", "your test has not been made");
		Teacher teacher = Teacher.findTeacherByClass(classroom);
		return teacherHome(teacher.id); // test.classroom.teacher.id
	}



	public static Result beginTest(long studentId, long testId) {
		Test test = new Model.Finder<>(long.class, Test.class)
				.byId(testId);
		Student student = Student.findStudentbyId(studentId);
		student.addTestTaken(test);
		student.save();
		TestAnswer testAnswer = new TestAnswer(student, test);
		testAnswer.save();
		return ok(takeTest.render(0, testAnswer));
	}

	public static Result markQuestion(int current, int answer, long id) {

		TestAnswer testAnswer = new Model.Finder<>(long.class, TestAnswer.class)
				.byId(id);

		QuestionResponse qr = new QuestionResponse(answer);
		testAnswer.addAnswer(qr);

		if (testAnswer.test.questions.get(current).correctAnswer == answer) {
			flash("correct", "");
			testAnswer.markCorrect();
		} else {
			flash("wrong", Question.getAnswer(
					testAnswer.test.questions.get(current),
					testAnswer.test.questions.get(current).correctAnswer - 1));
		}
		if (testAnswer.test.numQuestions <= current + 1) { // no more questions
			testAnswer.calculatePercentage();
			testAnswer.save();
			return ok(testResult.render(testAnswer, testAnswer.student.id));
		}
		testAnswer.save();
		return ok(takeTest.render(++current, testAnswer));
	}

	public static Result reviewTest(long id,long teacherId) {
		Test test = new Model.Finder<>(long.class, Test.class)
				.byId(id);
		List<TestAnswer> answers = TestAnswer.getTAForTest(test);
		return ok(reviewTest.render(test,answers,teacherId));
	}
	
	public static Result reviewStudentTest(long id,long teacherId) {
		TestAnswer test = new Model.Finder<>(long.class, TestAnswer.class)
				.byId(id);		
		return ok(reviewStudentTest.render(test,test.student,teacherId));
	}
	
	public static Result retireTest(long id, long teacherId){
		Test test = new Model.Finder<>(long.class, Test.class)
				.byId(id);
		flash("notcreated", "your test has not been made");
		test.retireTest();
		test.save();
		return teacherHome(teacherId);	
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
		return ok(Json.toJson(questions));
	}

	public static Result getStudents() {
		List<Student> students = new Model.Finder<>(long.class, Student.class)
				.all();
		return ok(Json.toJson(students));
	}

	public static Result getClassrooms() {
		List<Classroom> data = new Model.Finder<>(long.class, Classroom.class)
				.all();
		return ok(Json.toJson(data));
	}

	public static Result getTeachers() {
		List<Teacher> data = new Model.Finder<>(long.class, Teacher.class)
				.all();
		return ok(Json.toJson(data));
	}

}
