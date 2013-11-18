package ejb;

import ejb.AnswerEntity;
import ejb.TestEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-05-13T00:22:12")
@StaticMetamodel(QuestionEntity.class)
public class QuestionEntity_ { 

    public static volatile SingularAttribute<QuestionEntity, Long> id;
    public static volatile SingularAttribute<QuestionEntity, Integer> pointValue;
    public static volatile SingularAttribute<QuestionEntity, Integer> index;
    public static volatile SingularAttribute<QuestionEntity, AnswerEntity> correctAnswer;
    public static volatile SingularAttribute<QuestionEntity, TestEntity> test;
    public static volatile SingularAttribute<QuestionEntity, String> questionText;
    public static volatile ListAttribute<QuestionEntity, AnswerEntity> answers;
    public static volatile SingularAttribute<QuestionEntity, Integer> difficultyClass;
    public static volatile SingularAttribute<QuestionEntity, String> domainLable;

}