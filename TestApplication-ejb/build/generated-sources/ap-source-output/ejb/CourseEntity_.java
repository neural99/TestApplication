package ejb;

import ejb.PeopleEntity;
import ejb.TestEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-05-13T00:22:12")
@StaticMetamodel(CourseEntity.class)
public class CourseEntity_ { 

    public static volatile SingularAttribute<CourseEntity, Long> id;
    public static volatile ListAttribute<CourseEntity, TestEntity> tests;
    public static volatile SingularAttribute<CourseEntity, String> name;
    public static volatile SingularAttribute<CourseEntity, PeopleEntity> owner;
    public static volatile ListAttribute<CourseEntity, PeopleEntity> participants;
    public static volatile SingularAttribute<CourseEntity, String> courseCode;
    public static volatile SingularAttribute<CourseEntity, String> institution;

}