/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author x1x
 */
@Entity
public class TestGradeEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private int levelPoints;
    
	@OneToOne
    private TestEntity test;
	
    @OneToOne
    private GradesEntity grade;

	public TestEntity getTest() {
		return test;
	}

	public void setTest(TestEntity test) {
		this.test = test;
	}
	
    public GradesEntity getGrade() {
        return grade;
    }

    public void setGrade(GradesEntity grade) {
        this.grade = grade;
    }

    public int getLevelPoints() {
        return levelPoints;
    }

    public void setLevelPoints(int levelPoints) {
        this.levelPoints = levelPoints;
    }
  
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TestGradeEntity)) {
            return false;
        }
        TestGradeEntity other = (TestGradeEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.TestGradeEntity[ id=" + id + " ]";
    }
    
}
