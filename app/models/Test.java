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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	@Constraints.Required
	public String name;
	public String testDesc;
//	
//	@OneToMany
//	public Question[] questions;
//	
//	public int numOfQuestions;
//	
	


}
