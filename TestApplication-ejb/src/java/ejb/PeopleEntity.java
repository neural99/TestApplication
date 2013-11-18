package ejb;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class PeopleEntity implements Serializable {

	@Id
	String SSN;
	String passHash;
	String firstName;
	String lastName;
	String status;
	String startYear;
	String gender;
	String address;
	String tel;
	String email;
	String homepage;
        
        @ManyToMany(mappedBy="participants")
        List<CourseEntity> participantCourses;
        
        @OneToMany(mappedBy="owner")
        List<CourseEntity> ownerCourses;
        
        @OneToMany(mappedBy="student")
        List<TestedEntity> testsTaken;

    public List<TestedEntity> getTestsTaken() {
        return testsTaken;
    }

    public void setTestsTaken(List<TestedEntity> testsTaken) {
        this.testsTaken = testsTaken;
    }

    public List<CourseEntity> getOwnerCourses() {
        return ownerCourses;
    }

    public void setOwnerCourses(List<CourseEntity> ownerCourses) {
        this.ownerCourses = ownerCourses;
    }

    public List<CourseEntity> getParticipantCourses() {
        return participantCourses;
    }

    public void setParticipantCourses(List<CourseEntity> participantCourses) {
        this.participantCourses = participantCourses;
    }
        
        
	public String getSSN() {
		return SSN;
	}

	public void setSSN(String SSN) {
		this.SSN = SSN;
	}

	public String getPassHash() {
		return passHash;
	}

	public void setPassHash(String passHash) {
		this.passHash = passHash;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}
