<%-- 
    Document   : index
    Created on : 2012-maj-02, 12:06:51
    Author     : Mini Edvin
--%>
<%@page import="ejb.*,javax.naming.*,java.util.Properties, java.io.PrintWriter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body bgcolor="#ffffdd" link="#ffff00" vlink="#ffff00" alink="#ffff00">
        <script type="text/javascript">
			window.location = "login.jsp"
		</script>
    </body>
    <pre>
    <%
    try{
	Properties p = new Properties();
	// För att få tillgång till de JNDI-bindningar i openejb där våra EJB:er finns.
//	p.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.eclipse.persistence.jpa.PersistenceProvider");
	Context ctx = new InitialContext(p);
       
        out.println("\nPeople:\n");
       PeopleEJBLocal pejb = (PeopleEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/PeopleEJB");
//   pejb.addPeople("890302-1759", "c4ca4238a0b923820dcc509a6f75849b", "Daniel", "Lannstrom", "admin", "1209", "m", "", "", "", "");
//      pejb.addPeople("12345-123544", "c4ca4238a0b923820dcc509a6f75849b", "Derp2", "Herp2", "master of the universe", "1209", "m", "", "", "", "");
        
        PeopleEntity danne = pejb.findBySSN("890302-1759");
               

       
        CourseEJBLocal c = (CourseEJBLocal)ctx.lookup("java:global/TestApplication/TestApplication-ejb/CourseEJB");
        CourseEntity ce3 = c.addCourse("Derp 101", "derp101", "asdfasdf", danne);
    pejb.setAsOwnerToCourse(danne, ce3);
    pejb.update(danne);
    
        java.util.List l2 = pejb.findAll();
        for (int i = 0; i < l2.size(); i++) {
            PeopleEntity pe2 = (PeopleEntity) l2.get(i);
            pejb.addAsParticipantToCourse(pe2, ce3);
            c.addParticipant(ce3, pe2);
            c.update(ce3);
            pejb.update(pe2);
            out.println(pe2.getFirstName() + " " + pe2.getLastName());
        }
                   
       java.util.List l = c.findAll();
        for (int i = 0; i < l.size(); i++) {
            CourseEntity ce2 = (CourseEntity) l.get(i);
            out.print("Course id: " + ce2.getId() + " " + ce2.getName() + " ");
            if (ce2.getOwner() != null) {
                out.print("Owner name: " + ce2.getOwner().getFirstName() + " " + ce2.getOwner().getLastName());
           }
            out.println();
        } 
        
        TestEJBLocal testejb = (TestEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/TestEJB");
        TestEntity nte = testejb.addTest("Algebra 1", "derp", 100, new java.util.Date(), new java.util.Date(), "derp", true, ce3);
        nte.setCourse(ce3);
        testejb.update(nte);
        
        AnswerEJBLocal aejb = (AnswerEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/AnswerEJB");
        AnswerEntity a1 = aejb.addAnswer(0, "42");
        AnswerEntity a2 = aejb.addAnswer(1, "43");
        AnswerEntity a3 = aejb.addAnswer(2, "44");
        
        QuestionEJBLocal qejb = (QuestionEJBLocal) ctx.lookup("java:global/TestApplication/TestApplication-ejb/QuestionEJB");
        QuestionEntity nqe = qejb.addQuestion(0, 10, "derp", "what is the airspeed velocity of an unladen swallow", 3, a1, nte);
        a1.setQuestion(nqe);
        aejb.update(a1);
        a2.setQuestion(nqe);
        aejb.update(a2);
        a3.setQuestion(nqe);
        aejb.update(a3);
              
        java.util.List l5 = testejb.findAll();
        for (int i = 0; i < l5.size(); i++) {
            TestEntity te2 = (TestEntity) l5.get(i);
            out.print("Test name: " + te2.getName() + " ");
            out.print("Course id " + te2.getCourse().getId());
            out.println();
        }
        out.println("Daniel är kursdeltagare i:");
        for (CourseEntity ce : danne.getParticipantCourses()) {
             out.print("Course id: " + ce.getId() + " " + ce.getName() + " ");
             out.println();
        }
       
}
	catch(NamingException e){
		out.println(e.getMessage());
        } catch (javax.ejb.EJBException e) {
            out.println("EJBException");
            e.getCausedByException().printStackTrace(new PrintWriter(out));
        } catch(NullPointerException e) {
            out.println("Nullpoint");
            e.printStackTrace(new PrintWriter(out));
        }
    %> 
    </pre>
</html>
