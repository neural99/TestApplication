package ejb;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="Course")
public class CourseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String courseCode;
    private String institution;
    
    @ManyToMany
    private List<PeopleEntity> participants;

    @ManyToOne(targetEntity=PeopleEntity.class)
    private PeopleEntity owner;
    
    @OneToMany(mappedBy="course") 
    private List<TestEntity> tests;

    public List<TestEntity> getTests() {
        return tests;
    }

    public void setTests(List<TestEntity> tests) {
        this.tests = tests;
    }

    public PeopleEntity getOwner() {
        return owner;
    }

    public void setOwner(PeopleEntity owner) {
        this.owner = owner;
    }
    
    public List<PeopleEntity> getParticipants() {
        return participants;
    }

    public void setParticipants(List<PeopleEntity> participants) {
        this.participants = participants;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ejb.CourseEntity[ id=" + id + " ]";
    }
    
}
