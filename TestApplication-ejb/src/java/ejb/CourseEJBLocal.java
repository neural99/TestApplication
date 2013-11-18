package ejb;

import javax.ejb.Local;

@Local
public interface CourseEJBLocal {
    public CourseEntity addCourse(String name, String courseCode, String institution, PeopleEntity owner);
    public void update(CourseEntity ce);
    public CourseEntity findByPrimaryKey(long id);
    public java.util.List findAll();

    public void addParticipant(ejb.CourseEntity ce, ejb.PeopleEntity pe);
}
