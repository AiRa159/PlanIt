package cz.cvut.fel.ear.planIt.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalendarEntryTest {

    @Test
    public void addCategoryWorksWhenAddingCategoryForFirstTime(){
        CalendarInstance calendar = new CalendarInstance();
        CalendarEntry calendarEntry = new CalendarEntry("Task1", calendar);
        calendarEntry.setDescription("Write tests");
        Category category = new Category("For_CP1", "Blue");
        calendarEntry.addCategory(category);

        assertEquals(1, calendarEntry.getCategories().size());
    }

    @Test
    public void deleteCategoryWorksFromEntry() {
        CalendarInstance calendar = new CalendarInstance();
        CalendarEntry calendarEntry = new CalendarEntry("Task1", calendar);
        calendarEntry.setDescription("Write tests");
        Category category = new Category("For_CP1", "Blue");
        Category category1 = new Category("CP1", "GREY");
        calendarEntry.addCategory(category);
        calendarEntry.addCategory(category1);

        assertEquals(2, calendarEntry.getCategories().size());

        calendarEntry.removeCategory(category1);

        assertEquals(1, calendarEntry.getCategories().size());
    }

}
