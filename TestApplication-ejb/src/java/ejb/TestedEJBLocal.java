/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author Edvin
 */
@Local
public interface TestedEJBLocal {

	TestedEntity addTested(PeopleEntity pe, Date dateStarted, Date dateEnded, char gradeObtained, TestEntity test);
	void update(TestedEntity pe);
	TestedEntity findByID(Long id);
	public TestedEntity findByPeopleEntityAndTestEntity(PeopleEntity pe, TestEntity test);
	public List<TestedEntity> findByTestEntity(TestEntity test);
	java.util.List findAll();
}
