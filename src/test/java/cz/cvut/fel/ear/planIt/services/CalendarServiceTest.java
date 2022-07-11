package cz.cvut.fel.ear.planIt.services;

import cz.cvut.fel.ear.planIt.model.CalendarEntry;
import cz.cvut.fel.ear.planIt.model.CalendarInstance;
import cz.cvut.fel.ear.planIt.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class CalendarServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PersonalCalendarService sut;

    private CalendarInstance calendar;

    @BeforeEach
    public void setUp(){
        final User owner = new User("pavel_n", "Pavel", "Novakov", "novak1999");
        this.calendar = owner.getPersonalCalendar();
        em.persist(owner);
    }

    @Test
    public void createNewEntryFirstTime(){
        final CalendarEntry entry = new CalendarEntry();
        entry.setName("Test");
        entry.setStartTime("07.11.2021 22:00");
        entry.setDescription("Testing method");
        entry.setLocation("Home");
        em.persist(entry);

        sut.createNewEntry(calendar, entry);

        final CalendarInstance result = em.find(CalendarInstance.class, calendar.getId());
        assertEquals(1, result.getEntries().size());
    }

    @Test
    public void deleteEntryWithCorrectName(){
        final CalendarEntry entry = new CalendarEntry();
        entry.setName("Test");
        entry.setStartTime("07.11.2021 22:00");
        entry.setDescription("Testing method");
        entry.setLocation("Home");
        em.persist(entry);

        sut.createNewEntry(calendar, entry);
        sut.deleteEntryByName(calendar, "Test");

        final CalendarInstance result = em.find(CalendarInstance.class, calendar.getId());
        assertEquals(0, result.getEntries().size());
    }

    @Test
    public void deleteNoExistEntry(){
        final CalendarEntry entry = new CalendarEntry();
        entry.setName("Test");
        entry.setStartTime("07.11.2021 22:00");
        entry.setDescription("Testing method");
        entry.setLocation("Home");
        em.persist(entry);

        final CalendarEntry entry1 = new CalendarEntry();
        entry1.setName("TestTest");
        entry1.setStartTime("07.11.2021 23:00");
        em.persist(entry1);

        sut.createNewEntry(calendar, entry);
        sut.createNewEntry(calendar, entry1);
        sut.deleteEntryByName(calendar, "Test_Test");

        final CalendarInstance result = em.find(CalendarInstance.class, calendar.getId());
        assertEquals(2, result.getEntries().size());
    }
}
