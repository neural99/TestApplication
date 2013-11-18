package ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class QuestionEJB implements QuestionEJBLocal {

	@PersistenceContext(unitName = "TestPU", name = "em")
	private EntityManager em;
	
	@Override
	public QuestionEntity addQuestion(int index, int pointValue, String domainLable, String questionText, int difficultyClass, AnswerEntity correctAnswer, TestEntity test) {
		QuestionEntity qe = new QuestionEntity();
		
                qe.setIndex(index);
		qe.setPointValue(pointValue);
		qe.setDomainLable(domainLable);
		qe.setQuestionText(questionText);
		qe.setDifficultyClass(difficultyClass);
		qe.setCorrectAnswer(correctAnswer);
                qe.setTest(test);
		
		em.persist(qe);
                
                return qe;
	}

	@Override
	public void update(QuestionEntity qe) {
		em.merge(qe);
	}

	@Override
	public QuestionEntity findByID(Long id) {
		return (QuestionEntity) em.find(QuestionEntity.class, id);
	}

	@Override
	public List findAll() {
		return (em.createQuery("SELECT q from QuestionEntity as q")).getResultList();
	}

    @Override
    public int getNewIndex(TestEntity te) {
        Query query = em.createQuery("SELECT max(q.index) from QuestionEntity as q where q.test = :test");
        query.setParameter("test", te);
        
        Object o = query.getSingleResult();
        if (o == null) {
            return 0;
        }
        
        return (Integer)o + 1;
    }

	
}
