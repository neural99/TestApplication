package ejb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
public class TestEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String domainLabel;
	private int pointsAwarded;
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date startDate;
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date stopDate;
	private String description;
	private boolean obligatory;
	private boolean isFinal;
	private boolean isInvalid;
        
        @ManyToOne
        private CourseEntity course;
        
        @OneToMany
        private List<TestGradeEntity> gradeLevels;
        
        @OneToMany(mappedBy="test")
        private List<TestedEntity> testsGiven;
        
        @OneToMany(mappedBy="test")
        private List<QuestionEntity> questions;

    public List<TestGradeEntity> getGradeLevels() {
        return gradeLevels;
    }

    public void setGradeLevels(List<TestGradeEntity> gradeLevels) {
        this.gradeLevels = gradeLevels;
    }

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
    }

    public List<TestedEntity> getTestsGiven() {
        return testsGiven;
    }

    public void setTestsGiven(List<TestedEntity> testsGiven) {
        this.testsGiven = testsGiven;
    }
        
    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }
    
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDomainLabel() {
		return domainLabel;
	}

	public void setDomainLabel(String domainLabel) {
		this.domainLabel = domainLabel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isObligatory() {
		return obligatory;
	}

	public void setObligatory(boolean obligatory) {
		this.obligatory = obligatory;
	}

	public int getPointsAwarded() {
		return pointsAwarded;
	}

	public void setPointsAwarded(int pointsAwarded) {
		this.pointsAwarded = pointsAwarded;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStopDate() {
		return stopDate;
	}

	public void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}

	public boolean isIsFinal() {
		return isFinal;
	}

	public void setIsFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	public boolean isIsInvalid() {
		return isInvalid;
	}

	public void setIsInvalid(boolean isInvalid) {
		this.isInvalid = isInvalid;
	}
	
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TestEntity other = (TestEntity) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }	
}
