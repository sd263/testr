import org.junit.*;

import play.mvc.Result;
import static org.fest.assertions.Assertions.*;
import static play.mvc.Http.Status.SEE_OTHER;
import static play.test.Helpers.callAction;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.redirectLocation;
import static play.test.Helpers.running;
import static play.test.Helpers.status;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }
    
    @Test
    public void redirectHomePage() {
        running(fakeApplication(), new Runnable() {
           public void run() {
               Result result = callAction(controllers.routes.ref.Application.loginScreen());

               assertThat(status(result)).isEqualTo(SEE_OTHER);
               assertThat(redirectLocation(result)).isEqualTo("/computers");
           }
        });
    }
    

//    @Test
//    public void createTeacher() {
//    	Teacher teacher = new Teacher();
//    	teacher.name = "date";	
//    	teacher.save();
//    	Assertions.assertThat(teacher.id).isNotNull();
//    }


}
