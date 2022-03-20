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

    public void testAddStudent_Valid_Id_Name_Group_Email() {
        Student student = new Student("1", "Ion Pop", 931, "a@gmail.com");
        service.addStudent(student);
        Student student2 = service.findStudent("1");
        assertEquals(student2.getID(), "1");
        service.deleteStudent("1");
    }
    public void testAddStudent_InvalidId_EmptyId() {
        Exception error = new Exception();
        Student student = new Student("", "Flaviu", 1,"n@gmail.com");
        try{
            service.addStudent(student);
        }
        catch(Exception e){
            error = e;
        }
        assertEquals(error.getMessage(),"Id incorect!");
    }
    public void testAddStudent_InvalidId_IdIs0() {
        Exception error = new Exception();
        Student student = new Student("0", "Flaviu", 1,"n@gmail.com");
        try{
            service.addStudent(student);
        }
        catch(Exception e){
            error = e;
        }
        assertEquals(error.getMessage(),"Id incorect!");
    }

    public void testAddStudent_InvalidId_DuplicateId() {
        Exception error = new Exception();
        Student student = new Student("1003", "Flaviu", 1,"n@gmail.com");
        try{
            service.addStudent(student);
        }
        catch(Exception e){
            error = e;
        }
        assertNull(error.getMessage());
    }

    public void testAddStudent_InValidName_EmptyString() {
        Exception error = new Exception();
        Student student = new Student("1", "", 1,"n@gmail.com");
        try{
            service.addStudent(student);
        }
        catch(Exception e){
            error = e;
        }
        assertEquals(error.getMessage(), "Nume incorect!");
    }

    public void testAddStudent_InValidGroup_NegativeValue() {
        Exception error = new Exception();
        Student student = new Student("1", "Flaviu", -1,"n@gmail.com");
        try{
            service.addStudent(student);
        }
        catch(Exception e){
            error = e;
        }
        assertEquals(error.getMessage(), "Grupa incorecta!");
    }

    public void testAddStudent_InValidEmail_WrongPattern() {
        Exception error = new Exception();
        Student student = new Student("1", "Flaviu", 2,"n@gma");
        try{
            service.addStudent(student);
        }
        catch(Exception e){
            error = e;
        }
        System.out.println(error);
        assertEquals(error.getMessage(), "Format email incorect!");
    }

    public void testAddStudent_InValidEmail_EmptyString() {
        Exception error = new Exception();
        Student student = new Student("1", "Flaviu", 2,"");
        try{
            service.addStudent(student);
        }
        catch(Exception e){
            error = e;
        }
        assertEquals(error.getMessage(), "Email incorect!");
    }





}
