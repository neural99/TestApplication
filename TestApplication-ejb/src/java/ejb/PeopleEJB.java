package ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PeopleEJB implements PeopleEJBLocal {

	@PersistenceContext(unitName = "TestPU", name = "em")
	private EntityManager em;
        
    @Override
        public void addAsParticipantToCourse(PeopleEntity pe, CourseEntity ce) {
            pe.getParticipantCourses().add(ce);
        }

    @Override
    public void setAsOwnerToCourse(PeopleEntity pe, CourseEntity ce) {
        for (CourseEntity ce2 : pe.getOwnerCourses()) {
            if (ce2.getId() == ce.getId()) {
                return;
            }
        } 
        pe.getOwnerCourses().add(ce);
    }
    
    @Override
	public PeopleEntity addPeople(String SSN, String passHash, String firstName, String lastName, String status, String startYear, String gender, String address, String tel, String email, String homepage) {
		PeopleEntity pe = new PeopleEntity();

		pe.setSSN(SSN);
		pe.setPassHash(passHash);
		pe.setFirstName(firstName);
		pe.setLastName(lastName);
		pe.setStatus(status);
		pe.setStartYear(startYear);
		pe.setGender(gender);
		pe.setAddress(address);
		pe.setTel(tel);
		pe.setEmail(email);
		pe.setHomepage(homepage);

		em.persist(pe);
                
                return pe;
	}

	public void update(PeopleEntity pe) {
		em.merge(pe);
		em.flush();
	}

	public PeopleEntity findBySSN(String ssn) {
		PeopleEntity pe = (PeopleEntity) em.find(PeopleEntity.class, ssn);
		return pe;
	}

	public java.util.List findAll() {
		Query query = em.createQuery("SELECT p from PeopleEntity as p");
		return query.getResultList();
	}

    @Override
    public List<PeopleEntity> findByStatus(String status) {
		Query query = em.createQuery("SELECT p from PeopleEntity as p WHERE p.status = :status");
                query.setParameter("status", status);
		return (List<PeopleEntity>) query.getResultList();        
    }
}
