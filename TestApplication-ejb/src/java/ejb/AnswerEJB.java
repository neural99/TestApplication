package ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class AnswerEJB implements AnswerEJBLocal {

	@PersistenceContext(unitName = "TestPU", name = "em")
	private EntityManager em;
        
        @Override
	public AnswerEntity addAnswer(int index, String text, QuestionEntity qe) {
		AnswerEntity ae = new AnswerEntity();
	
                ae.setIndex(index);
		ae.setText(text);
                ae.setQuestion(qe);
		
		em.persist(ae);
                
                return ae;
	}

	@Override
	public void update(AnswerEntity ae) {
		em.merge(ae);
		em.flush();
	}

	@Override
	public AnswerEntity findByID(Long id) {
		return (AnswerEntity) em.find(AnswerEntity.class, id);
	}

	@Override
	public List findAll() {
		return (em.createQuery("SELECT a from AnswerEntity as a")).getResultList();
	}

    @Override
    public int getNewIndex(QuestionEntity qe) {
        Query query = em.createQuery("SELECT max(a.index) from AnswerEntity as a where a.question = :question");
        query.setParameter("question", qe);
        
        Object o = query.getSingleResult();
        if (o == null) {
            return 0;
        }
        return (Integer)o + 1;
    }

	
	
}
