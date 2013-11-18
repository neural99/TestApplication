package ejb;

import java.util.Date;
import javax.ejb.Local;
import javax.persistence.Temporal;

@Local
public interface TestEJBLocal {
	public TestEntity addTest(String name, String domainLabel, int pointsAwarded, Date startDate, Date stopDate, String description, boolean obligat, boolean isFinal, boolean isInvalid, CourseEntity course);

	public void update(TestEntity te);
	
	public TestEntity findByID(Long id);

	public java.util.List findAll();
	
	public void remove(TestEntity te);
}
