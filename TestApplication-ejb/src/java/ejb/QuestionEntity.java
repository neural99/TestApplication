package ejb;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
public class QuestionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int pointValue;
	private String domainLable;
	private String questionText;
	private int difficultyClass;
        private int index;
        @ManyToOne
        private TestEntity test;
        
        @OneToMany(mappedBy="question")
        private List<AnswerEntity> answers;
        
        @OneToOne
        private AnswerEntity correctAnswer;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<AnswerEntity> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerEntity> answers) {
        this.answers = answers;
    }

    public AnswerEntity getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(AnswerEntity correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public TestEntity getTest() {
        return test;
    }

    public void setTest(TestEntity test) {
        this.test = test;
    }
        
	public int getDifficultyClass() {
		return difficultyClass;
	}

	public void setDifficultyClass(int difficultyClass) {
		this.difficultyClass = difficultyClass;
	}

	public String getDomainLable() {
		return domainLable;
	}

	public void setDomainLable(String domainLable) {
		this.domainLable = domainLable;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPointValue() {
		return pointValue;
	}

	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
}
