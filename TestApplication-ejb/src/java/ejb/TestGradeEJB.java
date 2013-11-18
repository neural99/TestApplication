/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author x1x
 */
@Stateless
public class TestGradeEJB implements TestGradeEJBLocal {
    @PersistenceContext(unitName = "TestPU", name = "em")
    private EntityManager em;
        
    @Override
    public TestGradeEntity addTestGrade(int points, TestEntity test, GradesEntity grade) {
        TestGradeEntity te = new TestGradeEntity();
        te.setLevelPoints(points);
		te.setTest(test);
        te.setGrade(grade);
        em.persist(te);
        
        return te;
    }

    @Override
    public void update(TestGradeEntity te) {
        em.merge(te);
	em.flush();
    }

    @Override
    public TestGradeEntity findByID(Long id) {
        TestGradeEntity pe = (TestGradeEntity) em.find(TestGradeEntity.class, id);
	return pe;
    }
	
	@Override
	public List findByTestEntity(TestEntity te) {
		Query query = em.createQuery("SELECT t from TestGradeEntity as t where t.test = :test");
        query.setParameter("test", te);
		
		return query.getResultList();
	}

    @Override
    public java.util.List findAll() {
		Query query = em.createQuery("SELECT p from TestGradeEntity as p");
		return query.getResultList();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    
}
