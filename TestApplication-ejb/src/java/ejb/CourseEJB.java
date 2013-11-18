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

@Stateless
public class CourseEJB implements CourseEJBLocal {
    @PersistenceContext(unitName="TestPU", name="em")
    private EntityManager em;
    
    @Override
    public CourseEntity addCourse(String name, String courseCode, String institution, PeopleEntity owner) {
        CourseEntity ce = new CourseEntity();
        ce.setName(name);
        ce.setCourseCode(courseCode);
        ce.setInstitution(institution);
        ce.setOwner(owner);
        em.persist(ce);
        return ce;
    }
    
    @Override
    public void addParticipant(CourseEntity ce, PeopleEntity pe) {
        ce.getParticipants().add(pe);
    }

    @Override
    public List findAll() {
        Query query = em.createQuery("SELECT c from CourseEntity as c");
        return query.getResultList();
    }

    @Override
    public CourseEntity findByPrimaryKey(long id) {
	CourseEntity ce = (CourseEntity)em.find(CourseEntity.class, id);
	return ce; 
    }

    @Override
    public void update(CourseEntity ce) {
        em.merge(ce);
        em.flush();
    }
}
