package service;

import domain.Student;
import junit.framework.TestCase;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

public class ServiceTest extends TestCase {
    Service service;
    public void setUp() throws Exception {
        super.setUp();
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "src/main/fisiere/Studenti.xml";
        String filenameTema = "src/main/fisiere/Teme.xml";
        String filenameNota = "src/main/fisiere/Note.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    public void testAddStudent() {
        Student student = new Student("1", "flaviu", 931, "a@gmail.com");
        service.addStudent(student);
        Student student2 = service.findStudent("1");
        assertEquals(student2.getGrupa(),931);
    }
    public void testAddStudentFails() {
        Exception error = new Exception();
        Student student = new Student("2", "", -1,"n@gmail.com");
        try{
            service.addStudent(student);
        }
        catch(Exception e){
            error = e;
        }
        assertEquals(error.getMessage(),"Nume incorect!");
    }
}
