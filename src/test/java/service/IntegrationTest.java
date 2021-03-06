package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import junit.framework.TestCase;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.time.LocalDate;

public class IntegrationTest extends TestCase {
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

    public void testAddAssignment_InValidID_EmptyString() {
        Exception error = new Exception();
        Tema tema = new Tema("","a",12,10);
        try{
            service.addTema(tema);
        }
        catch(Exception e){
            error = e;
        }
        assertEquals(error.getMessage(), "Numar tema invalid!");
    }

    public void testAddGrade_InValidValue() {
        Exception error = new Exception();
        Student student = new Student("111", "Flaviu", 1,"n@gmail.com");
        Tema tema = new Tema("111","a",12,10);
        Nota nota = new Nota("111","111","111",2, LocalDate.of(2000,10,12));
        try{
            service.addNota(nota,"ok");
        }
        catch(Exception e){
            error = e;
        }
        assertEquals(error.getMessage(), "Studentul nu exista!");
        service.deleteStudent("111");
        service.deleteTema("111");
    }

    public void testIntegration() {
        testAddStudent_InvalidId_EmptyId();
        testAddAssignment_InValidID_EmptyString();
        testAddGrade_InValidValue();
    }

    public void testAddStudent_ValidStudent() {
        Student student = new Student("1", "Ion Pop", 931, "a@gmail.com");
        service.addStudent(student);
        Student student2 = service.findStudent("1");
        assertEquals(student2.getID(), "1");
        service.deleteStudent("1");
    }

    public void testAddStudent_AddAssignment_Integration() {
        Student student = new Student("1", "Ion Pop", 931, "a@gmail.com");
        service.addStudent(student);
        Student student2 = service.findStudent("1");
        assertEquals(student2.getID(), "1");
        Tema tema = new Tema("1", "Tema integrare", 13,2);
        service.addTema(tema);
        Tema tema2 = service.findTema("1");
        assertEquals(tema2.getID(), "1");
        service.deleteStudent("1");
        service.deleteNota("1");
    }

    public void testAddStudent_AddAssignment_AddGrade_Integration() {
        Exception error = new Exception();
        Student student = new Student("1", "Ion Pop", 931, "a@gmail.com");
        service.addStudent(student);
        Student student2 = service.findStudent("1");
        assertEquals(student2.getID(), "1");

        Tema tema = new Tema("1", "Tema integrare", 5,3);
        service.addTema(tema);
        Tema tema2 = service.findTema("1");
        assertEquals(tema2.getID(), "1");

        Nota nota = new Nota("1","1", "1",10,LocalDate.of(2022,4,4));
        try{
            service.addNota(nota,"ok");
        }
        catch(Exception e){
            error = e;
        }
        assertEquals(error.getMessage(), "Studentul nu mai poate preda aceasta tema!");

        service.deleteTema("1");
        service.deleteStudent("1");
    }
}
