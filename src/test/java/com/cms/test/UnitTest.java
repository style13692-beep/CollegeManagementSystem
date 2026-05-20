package com.cms.test;

import com.cms.model.Unit;
import com.cms.controller.UnitController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTest {

    private UnitController controller;

    @BeforeEach
    void setUp() {
        controller = new UnitController();
    }

    @Test
    void testAddUnit() {
        int sizeBefore = controller.getAllUnits().size();
        controller.addUnit(new Unit("TST101", "Test Unit", 3, "A test unit", "None"));
        assertEquals(sizeBefore + 1, controller.getAllUnits().size());
    }

    @Test
    void testFindByCode() {
        controller.addUnit(new Unit("TST102", "Find Me", 3, "Description", "None"));
        Unit found = controller.findByCode("TST102");
        assertNotNull(found);
        assertEquals("Find Me", found.getUnitName());
    }

    @Test
    void testFindByCodeNotFound() {
        Unit found = controller.findByCode("FAKE999");
        assertNull(found);
    }

    @Test
    void testSearchByName() {
        controller.addUnit(new Unit("TST103", "Java Programming", 3, "Learn Java", "None"));
        Unit found = controller.searchByName("Java");
        assertNotNull(found);
    }

    @Test
    void testSortByCode() {
        controller.addUnit(new Unit("ZZZ999", "Last Unit", 3, "Desc", "None"));
        controller.addUnit(new Unit("AAA001", "First Unit", 3, "Desc", "None"));
        controller.sortByCode();
        String firstCode = controller.getAllUnits().get(0).getUnitCode();
        assertEquals("AAA001", firstCode);
    }
}