package ejb;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class TestEJB implements TestEJBLocal {

	@PersistenceContext(unitName = "TestPU", name = "em")
	private EntityManager em;
	
//	@Override
//	public void addTest() {
//		TestEntity te = new TestEntity();
//		
//		te.set
//	}

	@Override
	public TestEntity addTest(String name, String domainLabel, int pointsAwarded, Date startDate, Date stopDate, String description, boolean obligat, boolean isFinal, boolean isInvalid, CourseEntity course) {
		TestEntity te = new TestEntity();
		te.setName(name);
		te.setDomainLabel(domainLabel);
		te.setPointsAwarded(pointsAwarded);
		te.setStartDate(startDate);
		te.setStopDate(stopDate);
		te.setDescription(description);
		te.setObligatory(obligat);
		te.setIsFinal(isFinal);
		te.setIsInvalid(isInvalid);
        te.setCourse(course);
		
		em.persist(te);
        return te;
	}

	@Override
	public void update(TestEntity te) {
		em.merge(te);
		em.flush();
	}

	@Override
	public TestEntity findByID(Long id) {
		TestEntity te = (TestEntity) em.find(TestEntity.class, id);
		return te;
	}

	@Override
	public List findAll() {
		Query query = em.createQuery("SELECT t from TestEntity as t");
		return query.getResultList();
	}

	@Override
	public void remove(TestEntity te) {
            em.remove(te);
	}
}
