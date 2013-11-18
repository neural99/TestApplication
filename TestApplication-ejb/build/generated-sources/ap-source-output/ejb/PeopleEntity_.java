package ejb;

import ejb.CourseEntity;
import ejb.TestedEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-05-13T00:22:12")
@StaticMetamodel(PeopleEntity.class)
public class PeopleEntity_ { 

    public static volatile SingularAttribute<PeopleEntity, String> lastName;
    public static volatile SingularAttribute<PeopleEntity, String> status;
    public static volatile SingularAttribute<PeopleEntity, String> SSN;
    public static volatile SingularAttribute<PeopleEntity, String> tel;
    public static volatile ListAttribute<PeopleEntity, CourseEntity> participantCourses;
    public static volatile SingularAttribute<PeopleEntity, String> homepage;
    public static volatile ListAttribute<PeopleEntity, CourseEntity> ownerCourses;
    public static volatile SingularAttribute<PeopleEntity, String> startYear;
    public static volatile SingularAttribute<PeopleEntity, String> address;
    public static volatile SingularAttribute<PeopleEntity, String> email;
    public static volatile ListAttribute<PeopleEntity, TestedEntity> testsTaken;
    public static volatile SingularAttribute<PeopleEntity, String> gender;
    public static volatile SingularAttribute<PeopleEntity, String> passHash;
    public static volatile SingularAttribute<PeopleEntity, String> firstName;

}