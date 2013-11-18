package ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class GradesEJB implements GradesEJBLocal {

	@PersistenceContext(unitName = "TestPU", name = "em")
	private EntityManager em;
	
	@Override
	public void update(GradesEntity ge) {
		em.merge(ge);
		em.flush();
	}

	@Override
	public GradesEntity addGrade(char grade) {
		GradesEntity ge = new GradesEntity();
		ge.setId(grade);
		
		em.persist(ge);
		return ge;
	}
	
	@Override
	public GradesEntity findByID(char id) {
		return (GradesEntity) em.find(GradesEntity.class, id);
	}
	
    @Override
    public List findAll() {
        Query query = em.createQuery("SELECT c from GradesEntity as c");
        return query.getResultList();
    }	
}
