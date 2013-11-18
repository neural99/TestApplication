package ejb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
public class TestedEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date dateStarted;
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date dateEnded;
	private char gradeObtained;
	
	
	@ManyToOne
	private TestEntity test;
	@ManyToOne
	private PeopleEntity student;
	@OneToMany
	private List<AnswerEntity> answersGiven;

	public List<AnswerEntity> getAnswersGiven() {
		return answersGiven;
	}

	public void setAnswersGiven(List<AnswerEntity> answersGiven) {
		this.answersGiven = answersGiven;
	}

	public PeopleEntity getStudent() {
		return student;
	}

	public void setStudent(PeopleEntity student) {
		this.student = student;
	}

	public TestEntity getTest() {
		return test;
	}

	public void setTest(TestEntity test) {
		this.test = test;
	}

	public Date getDateEnded() {
		return dateEnded;
	}

	public void setDateEnded(Date dateEnded) {
		this.dateEnded = dateEnded;
	}

	public Date getDateStarted() {
		return dateStarted;
	}

	public void setDateStarted(Date dateStarted) {
		this.dateStarted = dateStarted;
	}

	public char getGradeObtained() {
		return gradeObtained;
	}

	public void setGradeObtained(char gradeObtained) {
		this.gradeObtained = gradeObtained;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
