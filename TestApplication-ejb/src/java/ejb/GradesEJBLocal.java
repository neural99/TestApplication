package ejb;

import javax.ejb.Local;

@Local
public interface GradesEJBLocal {
	public void update(GradesEntity ge);
	public GradesEntity addGrade(char grade);
	public GradesEntity findByID(char id);
    public java.util.List findAll();
}
