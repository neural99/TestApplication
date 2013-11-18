package ejb;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class GradesEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private char id;

	public char getId() {
		return id;
	}

	public void setId(char id) {
		this.id = id;
	}
	
}
