package ejb;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class TestedEJB implements TestedEJBLocal {

	// Add business logic below. (Right-click in editor and choose
	// "Insert Code > Add Business Method")

	@PersistenceContext(unitName = "TestPU", name = "em")
	private EntityManager em;

	@Override
	public TestedEntity addTested(PeopleEntity pe, Date dateStarted, Date dateEnded, char gradeObtained, TestEntity test) {
		TestedEntity te = new TestedEntity();
        
		te.setStudent(pe);
		te.setDateStarted(dateStarted);
		te.setDateEnded(dateEnded);
		te.setGradeObtained(gradeObtained);
        te.setTest(test);
		
		em.persist(te);
        return te;
	}

	@Override
	public void update(TestedEntity pe) {
		em.merge(pe);
		em.flush();
	}

	@Override
	public TestedEntity findByID(Long id) {
		TestedEntity te = (TestedEntity) em.find(TestedEntity.class, id);
		return te;
	}
	
	@Override
	public TestedEntity findByPeopleEntityAndTestEntity(PeopleEntity pe, TestEntity test) {
		Query query = em.createQuery("SELECT t from TestedEntity as t where t.student = :pe and t.test = :test");
		query.setParameter("pe", pe);
		query.setParameter("test", test);
		if(query.getResultList().size() >= 1){
			return (TestedEntity)query.getResultList().get(0);
		}
		return null;
	}
	
	@Override
	public List<TestedEntity> findByTestEntity(TestEntity test) {
		Query query = em.createQuery("SELECT t from TestedEntity as t where t.test = :test");
		query.setParameter("test", test);
		return query.getResultList();
	}

	@Override
	public List findAll() {
		Query query = em.createQuery("SELECT t from TestedEntity as t");
		return query.getResultList();
	}
}
