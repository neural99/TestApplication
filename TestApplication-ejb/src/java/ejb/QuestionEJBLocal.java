package ejb;

import javax.ejb.Local;

@Local
public interface QuestionEJBLocal {
	public QuestionEntity addQuestion(int index, int pointValue, String domainLable, String questionText, int difficultyClass, AnswerEntity correctAnswer, TestEntity test);
	public void update(QuestionEntity qe);
	public QuestionEntity findByID(Long id);
	public java.util.List findAll();

    public int getNewIndex(TestEntity te);
}
