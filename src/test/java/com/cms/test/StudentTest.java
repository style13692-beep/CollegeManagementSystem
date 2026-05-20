package com.cms.test;

import com.cms.model.Student;
import com.cms.controller.StudentController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    private StudentController controller;

    @BeforeEach
    void setUp() {
        controller = new StudentController();
    }

    @Test
    void testAddStudent() {
        int sizeBefore = controller.getAllStudents().size();
        controller.addStudent(new Student("S001", "Alice Smith", "alice@email.com", "2024"));
        assertEquals(sizeBefore + 1, controller.getAllStudents().size());
    }

    @Test
    void testFindById() {
        controller.addStudent(new Student("S002", "Bob Jones", "bob@email.com", "2024"));
        Student found = controller.findById("S002");
        assertNotNull(found);
        assertEquals("Bob Jones", found.getName());
    }

    @Test
    void testFindByIdNotFound() {
        Student found = controller.findById("FAKE999");
        assertNull(found);
    }

    @Test
    void testSearchByName() {
        controller.addStudent(new Student("S003", "Charlie Brown", "charlie@email.com", "2023"));
        Student found = controller.searchByName("Charlie");
        assertNotNull(found);
        assertEquals("S003", found.getStudentId());
    }

    @Test
    void testSortByName() {
        controller.addStudent(new Student("S004", "Zara Ali", "zara@email.com", "2024"));
        controller.addStudent(new Student("S005", "Aaron King", "aaron@email.com", "2024"));
        controller.sortByName();
        String firstName = controller.getAllStudents().get(0).getName();
        assertEquals("Aaron King", firstName);
    }
}