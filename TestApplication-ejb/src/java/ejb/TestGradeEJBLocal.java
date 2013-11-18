/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Local;

/**
 *
 * @author x1x
 */
@Local
public interface TestGradeEJBLocal {
	public TestGradeEntity addTestGrade(int points, TestEntity test, GradesEntity grade);

	public void update(TestGradeEntity te);

	public TestGradeEntity findByID(Long id);
	
	public java.util.List findByTestEntity(TestEntity te);

	public java.util.List findAll();    
}
