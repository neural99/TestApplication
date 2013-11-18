package ejb;

import ejb.CourseEntity;
import ejb.QuestionEntity;
import ejb.TestGradeEntity;
import ejb.TestedEntity;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-05-13T00:22:12")
@StaticMetamodel(TestEntity.class)
public class TestEntity_ { 

    public static volatile SingularAttribute<TestEntity, Date> startDate;
    public static volatile SingularAttribute<TestEntity, Date> stopDate;
    public static volatile SingularAttribute<TestEntity, Boolean> obligatory;
    public static volatile ListAttribute<TestEntity, QuestionEntity> questions;
    public static volatile ListAttribute<TestEntity, TestedEntity> testsGiven;
    public static volatile SingularAttribute<TestEntity, Boolean> isInvalid;
    public static volatile SingularAttribute<TestEntity, Long> id;
    public static volatile SingularAttribute<TestEntity, CourseEntity> course;
    public static volatile SingularAttribute<TestEntity, String> domainLabel;
    public static volatile ListAttribute<TestEntity, TestGradeEntity> gradeLevels;
    public static volatile SingularAttribute<TestEntity, String> description;
    public static volatile SingularAttribute<TestEntity, String> name;
    public static volatile SingularAttribute<TestEntity, Integer> pointsAwarded;
    public static volatile SingularAttribute<TestEntity, Boolean> isFinal;

}