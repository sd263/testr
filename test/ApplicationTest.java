import models.Classroom;
import models.Student;
import models.Teacher;

import org.junit.*;

import play.mvc.Result;
import play.test.FakeApplication;
import play.test.Helpers;
import static org.fest.assertions.Assertions.*;
import static play.mvc.Http.Status.SEE_OTHER;
import static play.test.Helpers.callAction;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.redirectLocation;
import static play.test.Helpers.running;
import static play.test.Helpers.status;

/**
*
* Junit test of 
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {
	
    @Test
    public void createTeacher(){
    	Teacher teacher = new Teacher("TestName", "TestPassword");
    	assertThat(teacher.name).isEqualTo("TestName");
    	assertThat(teacher.password).isEqualTo("TestPassword");
    }
    
    @Test
    public void createStudent(){
    	Student student = new Student("TestName", "TestPassword");
    	assertThat(student.name).isEqualTo("TestName");
    	assertThat(student.password).isEqualTo("TestPassword");
    }

    @Test
    public void createClassroom(){
    	Classroom classroom = new Classroom("TestName");
    	assertThat(classroom.cname).isEqualTo("TestName");
    	assertThat(classroom.students).isNull();
    }     

}
