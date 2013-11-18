/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.List;

/**
 *
 * @author x1x
 */
public class GradeBean {
    private Long id;
    private int levelPoints;
	private String grade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getLevelPoints() {
		return levelPoints;
	}

	public void setLevelPoints(int levelPoints) {
		this.levelPoints = levelPoints;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	
}
