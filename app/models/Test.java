package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Test extends Model{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	public long testID;
	
	public String testName;
	
	public String testDesc;


}
