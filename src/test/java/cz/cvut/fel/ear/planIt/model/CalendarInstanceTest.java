package cz.cvut.fel.ear.planIt.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalendarInstanceTest {

    @Test
    public void setNameWorksForExistColor() {
        CalendarInstance calendar = new CalendarInstance();
        calendar.setNameToCategory("Test_CP1", "Brown");

        assertEquals("Test_CP1", calendar.getNameOfCategoryByColor("brown"));
    }

    @Test
    public void setNameNoWorksForNoExistColor(){
        CalendarInstance calendar = new CalendarInstance();
        calendar.setNameToCategory("Test_CP1", "Magenta");

        assertEquals("This color does not exist", calendar.getNameOfCategoryByColor("magenta"));
    }
}
