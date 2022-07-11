package cz.cvut.fel.ear.planIt.services;

import cz.cvut.fel.ear.planIt.model.CalendarEntry;
import cz.cvut.fel.ear.planIt.model.CalendarInstance;
import cz.cvut.fel.ear.planIt.model.Category;
import cz.cvut.fel.ear.planIt.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class CalendarEntryServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CalendarEntryService sut;

    private CalendarInstance calendar;

    @BeforeEach
    public void setUp(){
        final User owner = new User("pavel_n", "Pavel", "Novakov", "novak1999");
        this.calendar = owner.getPersonalCalendar();
        em.persist(owner);
    }

    @Test
    public void setTimeToEntryStartTimeIsAfterEndTimeThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            final CalendarEntry entry = new CalendarEntry("test",calendar);
            em.persist(entry);
            sut.setTimeToEntry(calendar,entry.getName(),"07.12.2021 22:00", "07.11.2021 22:00");
        } );
    }

    @Test
    public void deleteCategoryFromEntry() {
        final CalendarEntry entry = new CalendarEntry("test", calendar);
        em.persist(entry);
        List<Category> categories = calendar.getCategories();
        entry.addCategory(categories.get(0));
        entry.addCategory(categories.get(1));

        sut.deleteCategoryFromEntry(calendar, entry.getName(), categories.get(0).getColor().toString());
        assertEquals(entry.getCategories(),Arrays.asList(categories.get(1)));
    }
}
