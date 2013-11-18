package ejb;

import ejb.AnswerEntity;
import ejb.PeopleEntity;
import ejb.TestEntity;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-05-13T00:22:12")
@StaticMetamodel(TestedEntity.class)
public class TestedEntity_ { 

    public static volatile SingularAttribute<TestedEntity, Long> id;
    public static volatile SingularAttribute<TestedEntity, Character> gradeObtained;
    public static volatile SingularAttribute<TestedEntity, PeopleEntity> student;
    public static volatile ListAttribute<TestedEntity, AnswerEntity> answersGiven;
    public static volatile SingularAttribute<TestedEntity, TestEntity> test;
    public static volatile SingularAttribute<TestedEntity, Date> dateEnded;
    public static volatile SingularAttribute<TestedEntity, Date> dateStarted;

}