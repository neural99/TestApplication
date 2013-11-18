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
public class QuestionBean {
    private Long id;
    private int pointValue;
    private String domainLable;
    private String questionText;
    private int difficultyClass;
    private int index;
    
    private long correctAnswerId;
    
    private List<AnswerBean> answers;

    public long getCorrectAnswerId() {
        return correctAnswerId;
    }

    public void setCorrectAnswerId(long correctAnswerId) {
        this.correctAnswerId = correctAnswerId;
    } 

    public List<AnswerBean> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerBean> answers) {
        this.answers = answers;
    }
    
    public int getDifficultyClass() {
        return difficultyClass;
    }

    public void setDifficultyClass(int difficultyClass) {
        this.difficultyClass = difficultyClass;
    }

    public String getDomainLable() {
        return domainLable;
    }

    public void setDomainLable(String domainLable) {
        this.domainLable = domainLable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPointValue() {
        return pointValue;
    }

    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}
