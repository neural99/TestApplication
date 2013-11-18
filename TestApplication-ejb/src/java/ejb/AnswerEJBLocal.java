package ejb;

import javax.ejb.Local;

@Local
public interface AnswerEJBLocal {
	public AnswerEntity addAnswer(int index, String text, QuestionEntity qe);
	public void update(AnswerEntity ae);
	public AnswerEntity findByID(Long id);
	public java.util.List findAll();

    public int getNewIndex(QuestionEntity qe);
}
