package bean;

import ejb.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author x1x
 */
public class Facade {

    public static TestedBean beginTest(UserBean user, TestBean test) {
        Context ctx;
        try {
            ctx = new InitialContext();
            TestEJBLocal tejb = (TestEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestEJB");
            TestedEJBLocal tdejb = (TestedEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestedEJB");
			PeopleEJBLocal pejb = (PeopleEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/PeopleEJB");
			
			PeopleEntity pe = pejb.findBySSN(user.getSSN());
            TestEntity te = tejb.findByID(test.getId());
			
			TestedEntity tde = tdejb.findByPeopleEntityAndTestEntity(pe, te);
			if(tde == null){
				tde = tdejb.addTested(pe, new java.util.Date(), null, 'F', te);
			}

            if (tde != null) {
                return buildTestedBeanFromTestedEntity(tde);
            }

        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static String submitTest(UserBean user, TestedBean tb, HashMap<Long, Long> answersGiven) {
        int score = 0;
		char g = 'F';
        Context ctx;
        try {
            ctx = new InitialContext();
            QuestionEJBLocal qejb = (QuestionEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/QuestionEJB");
            TestedEJBLocal tdejb = (TestedEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestedEJB");
            PeopleEJBLocal pejb = (PeopleEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/PeopleEJB");

            TestedEntity te = tdejb.findByID(tb.getId());
            PeopleEntity pe = pejb.findBySSN(user.getSSN());

			for (long questionID : answersGiven.keySet()) {
                QuestionEntity qe = qejb.findByID(questionID);
                if (qe == null) {
                    break;
                }
                AnswerEntity ae = qe.getCorrectAnswer();
                te.getAnswersGiven().add(ae);
                if (ae.getId() == (long)answersGiven.get(questionID)) {
                    score += qe.getPointValue();
                }
            }
			
			List<GradeBean> grades = getGrades(buildTestBeanFromTestEntity(te.getTest()));
			grades = sortOnPoints(grades);
			for(GradeBean grade : grades){
				if(score >= grade.getLevelPoints()){
					g = grade.getGrade().charAt(0);
					break;
				}
			}

            te.setDateEnded(new java.util.Date());
            te.setStudent(pe);
            te.setGradeObtained(g);
			
            pe.getTestsTaken().add(te);
            pejb.update(pe);
            tdejb.update(te);

        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
        }

        return g+": "+score;
    }
	
	public static String[] getGradeAndSubmitedDate(UserBean user, TestBean test){
		String[] res = new String[]{"",""};
		Context ctx;
        try {
            ctx = new InitialContext();
            TestEJBLocal tejb = (TestEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestEJB");
            TestedEJBLocal tdejb = (TestedEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestedEJB");
			PeopleEJBLocal pejb = (PeopleEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/PeopleEJB");
			
			PeopleEntity pe = pejb.findBySSN(user.getSSN());
            TestEntity te = tejb.findByID(test.getId());
			
			TestedEntity tde = tdejb.findByPeopleEntityAndTestEntity(pe, te);
			if(tde != null){
				res[0] = tde.getGradeObtained()+"";
				if(tde.getDateEnded()!=null){
					res[1] = tde.getDateEnded()+"";
				}
			}
        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
		return res;
	}

    public static TestBean getTestFromId(long testID) {
        Context ctx;
        try {
            ctx = new InitialContext();
            TestEJBLocal tejb = (TestEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestEJB");
            TestEntity te = tejb.findByID(testID);
            if (te != null) {
                return buildTestBeanFromTestEntity(te);
            }

        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean setCorrectAnswer(UserBean creator, QuestionBean qb, long answerID) {
        if (creator == null || !(creator.getStatus().equalsIgnoreCase("admin") || creator.getStatus().equalsIgnoreCase("teacher"))) {
            return false;
        }

        try {
            Context ctx = new InitialContext();
            QuestionEJBLocal qejb = (QuestionEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/QuestionEJB");
            AnswerEJBLocal aejb = (AnswerEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/AnswerEJB");

            QuestionEntity qe = qejb.findByID(qb.getId());
            if (qe == null) {
                return false;
            }

            AnswerEntity ae = aejb.findByID(answerID);
            qe.setCorrectAnswer(ae);
            qejb.update(qe);
        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, "Naming exception", ex);
            return false;
        }
        return true;
    }

    public static boolean addAnswer(UserBean creator, AnswerBean ab, long questionID) {
        if (creator == null || !(creator.getStatus().equalsIgnoreCase("admin") || creator.getStatus().equalsIgnoreCase("teacher"))) {
            return false;
        }
        try {
            Context ctx = new InitialContext();
            QuestionEJBLocal qejb = (QuestionEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/QuestionEJB");
            AnswerEJBLocal aejb = (AnswerEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/AnswerEJB");
            QuestionEntity qe = qejb.findByID(questionID);
            if (qe == null) {
                return false;
            }

            int index = aejb.getNewIndex(qe);
            System.out.println(index);

            AnswerEntity ae = aejb.addAnswer(index, ab.getText(), qe);
            qe.getAnswers().add(ae);
            aejb.update(ae);
            qejb.update(qe);
        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, "Naming exception", ex);
            return false;
        }
        return true;
    }

    public static boolean addQuestion(UserBean creator, QuestionBean qb, long testID) {
        if (creator == null || !(creator.getStatus().equalsIgnoreCase("admin") || creator.getStatus().equalsIgnoreCase("teacher"))) {
            return false;
        }
        try {
            Context ctx = new InitialContext();
            QuestionEJBLocal qejb = (QuestionEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/QuestionEJB");
            TestEJBLocal tejb = (TestEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestEJB");

            TestEntity te = tejb.findByID(testID);
            if (te == null) {
                return false;
            }

            int index = qejb.getNewIndex(te);
            QuestionEntity qe = qejb.addQuestion(index, qb.getPointValue(), qb.getDomainLable(), qb.getQuestionText(), qb.getDifficultyClass(), null, te);
            te.getQuestions().add(qe);
            tejb.update(te);
        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, "Naming exception", ex);
            return false;
        }
        return true;
    }
	
	public static boolean addGrade(UserBean creator, GradeBean gb, long testID) {
        if (creator == null || !(creator.getStatus().equalsIgnoreCase("admin") || creator.getStatus().equalsIgnoreCase("teacher"))) {
            return false;
        }
        try {
            Context ctx = new InitialContext();
            GradesEJBLocal gejb = (GradesEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/GradesEJB");
            TestEJBLocal tejb = (TestEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestEJB");

            TestEntity te = tejb.findByID(testID);
			
			TestBean tb = buildTestBeanFromTestEntity(te);
			int maxPoint = getGradePoints(tb);
			
			if(maxPoint<gb.getLevelPoints()){
				return false;
			}
            if (te == null) {
                return false;
            }
			if(gb.getGrade().length() != 1){
				return false;
			}
			char grade = gb.getGrade().charAt(0);
			
			GradesEntity ge = gejb.findByID(grade);
			if(ge==null){
				ge = gejb.addGrade(grade);
			}
			
			TestGradeEJBLocal tgejb = (TestGradeEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestGradeEJB");
			TestGradeEntity tge = tgejb.addTestGrade(gb.getLevelPoints(), te, ge);
			
			gejb.update(ge);
			tgejb.update(tge);
			
			return true;
        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, "Naming exception", ex);
        }
        return false;
    }

    public static boolean createCourse(UserBean creator, CourseBean cb, String ownerSSN) {
        if (creator.getStatus() == null || !creator.getStatus().equalsIgnoreCase("admin")) {
            return false;
        }
        try {
            Context ctx = new InitialContext();
            CourseEJBLocal cejbl = (CourseEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/CourseEJB");
            PeopleEJBLocal pejb = (PeopleEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/PeopleEJB");

            PeopleEntity owner = pejb.findBySSN(ownerSSN);
            if (owner == null) {
                return false;
            }

            CourseEntity ce = cejbl.addCourse(cb.getName(), cb.getCourseCode(), cb.getInstitution(), owner);
            pejb.setAsOwnerToCourse(owner, ce);

            pejb.update(owner);
        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, "Naming exception", ex);
            return false;
        }
        return true;
    }
	
	public static List<UserBean> getUsers(TestBean test){
		ArrayList<UserBean> list = new ArrayList<UserBean>();
		Context ctx;
        try {
            ctx = new InitialContext();
            TestEJBLocal tejb = (TestEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestEJB");
            TestedEJBLocal tdejb = (TestedEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestedEJB");
			PeopleEJBLocal pejb = (PeopleEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/PeopleEJB");
			
//			PeopleEntity pe = pejb.findBySSN(user.getSSN());
            TestEntity te = tejb.findByID(test.getId());
			
			List<TestedEntity> tests = tdejb.findByTestEntity(te);
			
			for(TestedEntity tde : tests){
				list.add(buildUserBeanFromPeopleEntity(tde.getStudent()));
			}

        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
		return list;
	}

    public static List<UserBean> getUsersByStatus(String status1, String status2) {
        ArrayList<UserBean> list = new ArrayList<UserBean>();
        try {
            Context ctx = new InitialContext();
            PeopleEJBLocal pejb = (PeopleEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/PeopleEJB");
            List<PeopleEntity> persons1 = pejb.findByStatus(status1);
            List<PeopleEntity> persons2 = pejb.findByStatus(status2);
            for (PeopleEntity person : persons1) {
                if (person.getStatus().equals(status1) || person.getStatus().equals(status2)) {
                    list.add(buildUserBeanFromPeopleEntity(person, ""));
                }
            }
            for (PeopleEntity person : persons2) {
                if (person.getStatus().equals(status1) || person.getStatus().equals(status2)) {
                    list.add(buildUserBeanFromPeopleEntity(person, ""));
                }
            }
            return list;
        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, "Naming exception", ex);
            return null;
        }
    }

    public static UserBean login(String ssn, String passHash) {
        try {
            Context ctx = new InitialContext();
            PeopleEJBLocal pejb = (PeopleEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/PeopleEJB");
            PeopleEntity person = pejb.findBySSN(ssn);
            if (person != null && person.getPassHash().equals(hashPass(passHash))) {
                return buildUserBeanFromPeopleEntity(person, passHash);
            } else {
                return null;
            }
        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, "Naming exception", ex);
            return null;
        }
    }

    public static boolean register(UserBean creator, UserBean user, List<CourseBean> courses) {
        if (creator.getStatus() == null || !creator.getStatus().equalsIgnoreCase("admin")) {
            return false;
        }

        try {
            Context ctx = new InitialContext();
            PeopleEJBLocal pejb = (PeopleEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/PeopleEJB");
            PeopleEntity person = pejb.addPeople(user.getSSN(), hashPass(user.getPassHash()), user.getFirstName(), user.getLastName(), user.getStatus(), user.getStartYear(), user.getGender(), user.getAddress(), user.getTel(), user.getEmail(), user.getHomepage());

            CourseEJBLocal c = (CourseEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/CourseEJB");

            if (user.getStatus().equals("teacher")) {
                for (CourseBean cb : courses) {
                    CourseEntity ce = c.findByPrimaryKey(cb.getId());
                    pejb.setAsOwnerToCourse(person, ce);
                    ce.setOwner(person);
                    pejb.update(person);
                    c.update(ce);
                }
            } else {
                for (CourseBean cb : courses) {
                    CourseEntity ce = c.findByPrimaryKey(cb.getId());
                    c.addParticipant(ce, person);
                    pejb.addAsParticipantToCourse(person, ce);
                    pejb.update(person);
                    c.update(ce);
                }
            }
            return true;
        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, "Naming exception", ex);
            return false;
        }
    }

    public static boolean createTest(UserBean creator, TestBean tb, long courseID) {
        if (creator == null || !(creator.getStatus().equalsIgnoreCase("admin") || creator.getStatus().equalsIgnoreCase("teacher"))) {
            return false;
        }
        try {
            Context ctx = new InitialContext();
            CourseEJBLocal cejbl = (CourseEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/CourseEJB");
            CourseEntity ce = cejbl.findByPrimaryKey(courseID);

            PeopleEJBLocal pejb = (PeopleEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/PeopleEJB");
            PeopleEntity person = pejb.findBySSN(creator.getSSN());

			if (creator.getStatus().equalsIgnoreCase("teacher") && !isOwnerOfCourse(person, courseID)) {
                return false;
            }
            TestEJBLocal tejb = (TestEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestEJB");
			Date startDate = toDate(tb.getStartDate());
			Date stopDate = toDate(tb.getStopDate());
			if(startDate==null || stopDate==null)
				return false;
			
            TestEntity test = tejb.addTest(tb.getName(), tb.getDomainLabel(), tb.getPointsAwarded(), startDate, stopDate, tb.getDescription(), tb.getObligatory(), false, false, ce);
            ce.getTests().add(test);
            tejb.update(test);
            cejbl.update(ce);
            return true;
        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, "Naming exception", ex);
            return false;
        }
    }

    private static boolean isOwnerOfCourse(PeopleEntity person, long courseId) {
        for (CourseEntity ce2 : person.getOwnerCourses()) {
            if (ce2.getId() == courseId && ce2.getOwner().getSSN().equals(person.getSSN())) {
                return true;
            }
        }
        return false;
    }

    public static boolean removeTest(UserBean creator, long testID) {
        if (creator == null || !(creator.getStatus().equalsIgnoreCase("admin") || creator.getStatus().equalsIgnoreCase("teacher"))) {
            return false;
        }
        Context ctx;
        try {
            ctx = new InitialContext();
            TestEJBLocal tejb = (TestEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestEJB");
            CourseEJBLocal cejb = (CourseEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/CourseEJB");
            TestEntity te = tejb.findByID(testID);
            CourseEntity c = te.getCourse();
            c.getTests().remove(te);
            tejb.remove(te);
            cejb.update(c);
        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public static List<CourseBean> getCourses(UserBean user) {
        ArrayList<CourseBean> list = new ArrayList<CourseBean>();
        Context ctx;
        try {
            ctx = new InitialContext();
            PeopleEJBLocal pejb = (PeopleEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/PeopleEJB");
            PeopleEntity person = pejb.findBySSN(user.getSSN());

            if (person.getStatus().equals("student")) {
                for (CourseEntity ce : person.getParticipantCourses()) {
                    list.add(buildCourseBeanFromCourseEntity(ce));
                }
            } else if (person.getStatus().equals("teacher")) {
                for (CourseEntity ce : person.getOwnerCourses()) {
                    list.add(buildCourseBeanFromCourseEntity(ce));
                }
            } else if(person.getStatus().equals("admin")) {
                CourseEJBLocal c = (CourseEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/CourseEJB");
                List l = c.findAll();
                for (int i = 0; i < l.size(); i++) {
                    CourseEntity ce2 = (CourseEntity) l.get(i);
                    list.add(buildCourseBeanFromCourseEntity(ce2));
                }
            }
        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<TestBean> getTests(CourseBean c) {
        ArrayList<TestBean> list = new ArrayList<TestBean>();
        Context ctx;
        try {
            ctx = new InitialContext();
            CourseEJBLocal cejb = (CourseEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/CourseEJB");
            CourseEntity ce = cejb.findByPrimaryKey(c.getId());
            for (TestEntity te : ce.getTests()) {
                list.add(buildTestBeanFromTestEntity(te));
            }
        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<QuestionBean> getQuestions(TestBean test) {
        ArrayList<QuestionBean> list = new ArrayList<QuestionBean>();
        Context ctx;
        try {
            ctx = new InitialContext();
            TestEJBLocal tejb = (TestEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestEJB");
            TestEntity te = tejb.findByID(test.getId());
            if (test != null) {
                for (QuestionEntity qe : te.getQuestions()) {
                    list.add(buildQuestionBeanFromQuestionEntity(qe, qe.getAnswers()));
                }
            }
            return list;
        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, "Naming exception", ex);
            return null;
        }
    }
	
	public static List<GradeBean> getGrades(TestBean test) {
        Context ctx;
        try {
            ctx = new InitialContext();
            TestEJBLocal tejb = (TestEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestEJB");
            TestEntity te = tejb.findByID(test.getId());
			TestGradeEJBLocal tgejb = (TestGradeEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestGradeEJB");
			
            if (test != null) {
				ArrayList<GradeBean> list = new ArrayList<GradeBean>();
                for (Object o : tgejb.findByTestEntity(te)) {
					TestGradeEntity tge = (TestGradeEntity)o;
                    list.add(buildGradeBeanFromTestGradeEntityEntity(tge));
                }
				return list;
            }
        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, "Naming exception", ex);
        }
		return null;
    }

    public static QuestionBean getQuestion(TestBean test, int index) {
        Context ctx;
        try {
            if (test != null) {
				ctx = new InitialContext();
				TestEJBLocal tejb = (TestEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestEJB");
				TestEntity te = tejb.findByID(test.getId());
                for (QuestionEntity qe : te.getQuestions()) {
                    if (qe.getIndex() == index) {
                        return buildQuestionBeanFromQuestionEntity(qe, qe.getAnswers());
                    }
                }
            }
            return null;
        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, "Naming exception", ex);
            return null;
        }
    }

    public static int getNumQuestions(TestBean test) {
        Context ctx;
        try {
            ctx = new InitialContext();
            TestEJBLocal tejb = (TestEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestEJB");
            TestEntity te = tejb.findByID(test.getId());
            if (test != null) {
                return te.getQuestions().size();
            }
            return -1;
        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, "Naming exception", ex);
            return -1;
        }
    }

    private static UserBean buildUserBeanFromPeopleEntity(PeopleEntity person, String password) {
        UserBean user = new UserBean();
        user.setSSN(person.getSSN());
        user.setFirstName(person.getFirstName());
        user.setLastName(person.getLastName());
        user.setAddress(person.getAddress());
        user.setEmail(person.getEmail());
        user.setGender(person.getGender());
        user.setHomepage(person.getHomepage());
        user.setTel(person.getTel());
        user.setStatus(person.getStatus());
        user.setStartYear(person.getStartYear());
        user.setPassHash(password);
        return user;
    }
	
	private static UserBean buildUserBeanFromPeopleEntity(PeopleEntity person) {
        UserBean user = new UserBean();
        user.setSSN(person.getSSN());
        user.setFirstName(person.getFirstName());
        user.setLastName(person.getLastName());
        user.setAddress(person.getAddress());
        user.setEmail(person.getEmail());
        user.setGender(person.getGender());
        user.setHomepage(person.getHomepage());
        user.setTel(person.getTel());
        user.setStatus(person.getStatus());
        user.setStartYear(person.getStartYear());
        user.setPassHash(person.getPassHash());
        return user;
    }

    private static CourseBean buildCourseBeanFromCourseEntity(CourseEntity ce) {
        CourseBean course = new CourseBean();
        course.setCourseCode(ce.getCourseCode());
        course.setId(ce.getId());
        course.setInstitution(ce.getInstitution());
        course.setName(ce.getName());
        return course;
    }

    private static TestBean buildTestBeanFromTestEntity(TestEntity te) {
        TestBean test = new TestBean();
        test.setDescription(te.getDescription());
        test.setDomainLabel(te.getDomainLabel());
        test.setId(te.getId());
        test.setName(te.getName());
        test.setObligatory(te.isObligatory());
        test.setPointsAwarded(te.getPointsAwarded());
        test.setStartDate(toTimestampFormate(te.getStartDate()));
        test.setStopDate(toTimestampFormate(te.getStopDate()));
		test.setIsFinal(te.isIsFinal());
		test.setIsInvalid(te.isIsInvalid());
        return test;
    }
	
	public static TestBean buildTestBeanFromID(Long ID){
		Context ctx;
        try {
            ctx = new InitialContext();
            TestEJBLocal tejb = (TestEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestEJB");
            TestEntity te = tejb.findByID(ID);
			return buildTestBeanFromTestEntity(te);
        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, "Naming exception", ex);
        }
		return null;
	}

    private static QuestionBean buildQuestionBeanFromQuestionEntity(QuestionEntity qe, List<AnswerEntity> answers) {
        QuestionBean q = new QuestionBean();

        ArrayList<AnswerBean> list = new ArrayList<AnswerBean>();
        for (AnswerEntity ae : answers) {
            list.add(buildAnswerBeanFromAnswerEntity(ae));
        }

        q.setAnswers(list);

        AnswerEntity ae = qe.getCorrectAnswer();
        if (ae != null) {
            q.setCorrectAnswerId(ae.getId());
        } else {
            q.setCorrectAnswerId(-1);
        }

        q.setDifficultyClass(qe.getDifficultyClass());
        q.setDomainLable(qe.getDomainLable());
        q.setId(qe.getId());
        q.setIndex(qe.getIndex());
        q.setPointValue(qe.getPointValue());
        q.setQuestionText(qe.getQuestionText());

        return q;
    }
	
	private static GradeBean buildGradeBeanFromTestGradeEntityEntity(TestGradeEntity tge) {
        GradeBean bg = new GradeBean();
		bg.setId(tge.getId());
		bg.setLevelPoints(tge.getLevelPoints());
		bg.setGrade(tge.getGrade().getId()+"");
        return bg;
    }

    public static List<String> getGrades() {
        ArrayList<String> list = new ArrayList<String>();
        Context ctx;
        try {
            ctx = new InitialContext();
            GradesEJBLocal gejb = (GradesEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/GradesEJB");
            List l = gejb.findAll();
            for (int i = 0; i < l.size(); i++) {
                GradesEntity ge = (GradesEntity) l.get(i);
                list.add(ge.getId() + "");
            }
            return list;
        } catch (NamingException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, "Naming exception", ex);
            return null;
        }
    }

    private static AnswerBean buildAnswerBeanFromAnswerEntity(AnswerEntity ae) {
        AnswerBean ab = new AnswerBean();
        ab.setId(ae.getId());
        ab.setIndex(ae.getIndex());
        ab.setText(ae.getText());
        return ab;
    }

    private static String hashPass(String n) {
        String passHash;
        try {
            byte[] bytesOfMessage = n.getBytes("UTF-8");

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(bytesOfMessage);

            BigInteger bigInt = new BigInteger(1, digest);
            String hashtext = bigInt.toString(16);

            passHash = hashtext;
        } catch (Exception e) {
            passHash = "";
        }
        return passHash;
    }

    private static TestedBean buildTestedBeanFromTestedEntity(TestedEntity t2e) {
        TestedBean tb = new TestedBean();
        tb.setId(t2e.getId());
        tb.setDateStarted(t2e.getDateStarted());
        tb.setDateEnded(t2e.getDateEnded());
        return tb;
    }
	
	public static boolean isTestAvalible(UserBean user, TestBean tb){
		Date now = new Date();
		Context ctx;
		try {
			ctx = new InitialContext();
			TestEJBLocal tejb = (TestEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestEJB");
			TestEntity te = tejb.findByID(tb.getId());
			if(user != null && user.getStatus() != null && (user.getStatus().equalsIgnoreCase("admin") || user.getStatus().equalsIgnoreCase("teacher"))){
				if(!te.isIsFinal() && !te.isIsInvalid()){
					return true;
				}
			}else if(user != null){
				if(te.isIsFinal() && !te.isIsInvalid()){
					if(te.getStartDate() != null && te.getStopDate() != null){
						if((te.getStartDate().before(now) || te.getStartDate().equals(now)) && (te.getStopDate().after(now) || te.getStopDate().equals(now))){
							TestedEJBLocal tdejb = (TestedEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestedEJB");
							PeopleEJBLocal pejb = (PeopleEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/PeopleEJB");
							PeopleEntity pe = pejb.findBySSN(user.getSSN());
							TestedEntity tde = tdejb.findByPeopleEntityAndTestEntity(pe, te);
							if(tde == null){// Ej påbörjat test
								return true;
							}else{
								if(tde.getDateEnded() == null){// Ej submittat test
									return true;
								}
							}
						}
					}
				}
			}
		} catch (NamingException ex) {
			Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}
	
	public static boolean finalizeTest(UserBean user, TestBean tb){
		if(user != null && user.getStatus() != null && (user.getStatus().equalsIgnoreCase("admin") || user.getStatus().equalsIgnoreCase("teacher"))){
			Context ctx;
			try {
				ctx = new InitialContext();
				PeopleEJBLocal pejb = (PeopleEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/PeopleEJB");
				PeopleEntity pe = pejb.findBySSN(user.getSSN());
				TestEJBLocal tejb = (TestEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestEJB");
				TestEntity te = tejb.findByID(tb.getId());
				
				te.setPointsAwarded(getGradePoints(tb));
				tb = buildTestBeanFromTestEntity(te);
				
				if(isOwnerOfCourse(pe, te.getCourse().getId())){
//					tb.setIsFinal(true);
					te.setIsFinal(true);
					tejb.update(te);
					
					return true;
				}
			} catch (NamingException ex) {
				Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return false;
	}
	
	public static int getGradePoints(TestBean tb){
		List<QuestionBean> qs = getQuestions(tb);
		int maxPoint = 0;
		for(QuestionBean q : qs){
			maxPoint+=q.getPointValue();
		}
		return maxPoint;
	}
	
	// EX: 1999-01-08 04:05:06
	public static String toTimestampFormate(Date d){
		if(d==null)
			return "";
		String s = "";
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		s+=c.get(c.YEAR)+"-"
				+toNDigits(c.get(c.MONTH)+1, 2)+"-"
				+toNDigits(c.get(c.DATE), 2)+" "
				+toNDigits(c.get(c.HOUR_OF_DAY), 2)+":"
				+toNDigits(c.get(c.MINUTE), 2)+":"
				+toNDigits(c.get(c.SECOND), 2);
		return s;
	}
	
	private static Date toDate(String timestamp){
		if(timestamp.length() != 19)
			return null;
		String[] s = timestamp.split("[\\-\\:\\s]");
		int[] ti = new int[6];
		for(int i = 0; i < 6; i++){
			try{
				ti[i] = Integer.parseInt(s[i]);
			}catch(NumberFormatException e){
				return null;
			}
		}
		Calendar c = Calendar.getInstance();
		c.set(ti[0], ti[1]-1, ti[2], ti[3], ti[4], ti[5]);
		return c.getTime();
	}
	
	private static String toNDigits(int number, int digits){
		char[] n = (""+number).toCharArray();
		char[] c = new char[digits];
		int j = n.length;
		for(int i = digits-1; i >= 0; i--){
			j--;
			if(j>=0){
				c[i]=n[j];
			}else{
				c[i]='0';
			}
		}
		return new String(c);
	}
	
	public static List<GradeBean> sortOnPoints(List<GradeBean> lgb1){
		List<GradeBean> lgb2 = new ArrayList<GradeBean>(lgb1.size());
		while(!lgb1.isEmpty()){
			int h = 0;
			int index = 0;
			for(int i = 0; i < lgb1.size(); i++){
				int n = lgb1.get(i).getLevelPoints();
				if(n>h){
					h=n;
					index=i;
				}
			}
			lgb2.add(lgb1.remove(index));
		}
		return lgb2;
	}
}
