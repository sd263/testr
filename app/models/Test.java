package models;

import javax.persistence.*;
import java.util.*;
import com.avaje.ebean.Page;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

/**
 * Test entity managed by Ebean
 */
@Entity
public class Test extends Model{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	public Long testID;
	
	@Constraints.Required
	public String testName;
	

	@Constraints.Required
	public String testDesc;
	
    public static Finder<Long,Test> find = new Finder<Long,Test>(Long.class, Test.class); 
	
    public static Page<Test> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("name", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .fetch("Test")
                .findPagingList(pageSize)
                .setFetchAhead(false)
                .getPage(page);
    }


}
