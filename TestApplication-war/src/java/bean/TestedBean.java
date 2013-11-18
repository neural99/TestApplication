/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.Date;
import java.util.List;

/**
 *
 * @author danne
 */
public class TestedBean {
	private Long id;
	private Date dateStarted;
	private Date dateEnded;
        
        private int[] answersGiven;

    public int[] getAnswersGiven() {
        return answersGiven;
    }

    public void setAnswersGiven(int[] answersGiven) {
        this.answersGiven = answersGiven;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
