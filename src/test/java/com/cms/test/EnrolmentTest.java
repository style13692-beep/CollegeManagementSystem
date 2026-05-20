package com.cms.test;

import com.cms.model.Enrolment;
import com.cms.controller.EnrolmentController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class EnrolmentTest {

    private EnrolmentController controller;

    @BeforeEach
    void setUp() {
        controller = new EnrolmentController();
    }

    @Test
    void testAddEnrolment() {
        int sizeBefore = controller.getAllEnrolments().size();
        controller.addEnrolment(new Enrolment("E001", "S001", "OFF001", "2024-01-15", "Active"));
        assertEquals(sizeBefore + 1, controller.getAllEnrolments().size());
    }

    @Test
    void testFindByStudent() {
        controller.addEnrolment(new Enrolment("E002", "S010", "OFF001", "2024-01-15", "Active"));
        ArrayList<Enrolment> result = controller.findByStudent("S010");
        assertFalse(result.isEmpty());
    }

    @Test
    void testFindByOffering() {
        controller.addEnrolment(new Enrolment("E003", "S001", "OFF099", "2024-01-15", "Active"));
        ArrayList<Enrolment> result = controller.findByOffering("OFF099");
        assertFalse(result.isEmpty());
    }

    @Test
    void testFindByStudentNotFound() {
        ArrayList<Enrolment> result = controller.findByStudent("FAKE999");
        assertTrue(result.isEmpty());
    }

    @Test
    void testEnrolmentFields() {
        Enrolment e = new Enrolment("E004", "S002", "OFF002", "2024-02-20", "Active");
        assertEquals("E004", e.getEnrolmentId());
        assertEquals("S002", e.getStudentId());
        assertEquals("OFF002", e.getOfferingId());
        assertEquals("2024-02-20", e.getEnrolmentDate());
        assertEquals("Active", e.getStatus());
    }
}