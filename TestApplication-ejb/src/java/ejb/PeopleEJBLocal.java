package ejb;

import java.util.List;
import javax.ejb.Local;

@Local
public interface PeopleEJBLocal {

	public PeopleEntity addPeople(String SSN, String passHash, String firstName, String lastName, String status, String startYear, String gender, String address,
			String tel, String email, String homepage);

	public void update(PeopleEntity pe);

	public PeopleEntity findBySSN(String ssn);

	public java.util.List findAll();

    public void addAsParticipantToCourse(ejb.PeopleEntity pe, ejb.CourseEntity ce);

    public void setAsOwnerToCourse(ejb.PeopleEntity pe, ejb.CourseEntity ce);

    public List<PeopleEntity> findByStatus(String status1);
}
