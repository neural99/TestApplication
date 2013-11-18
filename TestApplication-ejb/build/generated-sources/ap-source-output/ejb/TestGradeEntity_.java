package ejb;

import ejb.GradesEntity;
import ejb.TestEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-05-13T00:22:12")
@StaticMetamodel(TestGradeEntity.class)
public class TestGradeEntity_ { 

    public static volatile SingularAttribute<TestGradeEntity, Long> id;
    public static volatile SingularAttribute<TestGradeEntity, TestEntity> test;
    public static volatile SingularAttribute<TestGradeEntity, GradesEntity> grade;
    public static volatile SingularAttribute<TestGradeEntity, Integer> levelPoints;

}