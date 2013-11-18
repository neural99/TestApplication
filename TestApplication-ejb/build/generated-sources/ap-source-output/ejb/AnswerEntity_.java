package ejb;

import ejb.QuestionEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-05-13T00:22:12")
@StaticMetamodel(AnswerEntity.class)
public class AnswerEntity_ { 

    public static volatile SingularAttribute<AnswerEntity, Long> id;
    public static volatile SingularAttribute<AnswerEntity, String> text;
    public static volatile SingularAttribute<AnswerEntity, Integer> index;
    public static volatile SingularAttribute<AnswerEntity, QuestionEntity> question;

}