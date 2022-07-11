package cz.cvut.fel.ear.planIt.dao;

import cz.cvut.fel.ear.planIt.PlanItApplication;
import cz.cvut.fel.ear.planIt.model.CalendarInstance;
import cz.cvut.fel.ear.planIt.model.Category;
import cz.cvut.fel.ear.planIt.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@ComponentScan(basePackageClasses = PlanItApplication.class)
public class CalendarInstanceTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CalendarInstanceDao calendarInstanceDao;

    @Test
    public void findByNameCalendarExistsReturnsCalendar() {
        final User u = new User("Logan", "Adam", "Work", "1234");
        final CalendarInstance ci = new CalendarInstance(u, "Wow");
        em.persist(ci);

        assertEquals(ci, calendarInstanceDao.findByName("Wow"));
    }

    @Test
    public void findByNameUserHasPersonalCalendarReturnsCalendar() {
        final User u = new User("Logan", "Adam", "Work", "1234");
        final CalendarInstance ci = u.getPersonalCalendar();
        em.persist(ci);

        assertEquals(ci, calendarInstanceDao.findByName("Logan"));
    }

    @Test
    public void findByNameCalendarDoesNotExistReturnsNull() {
        assertNull(calendarInstanceDao.findByName("example"));
    }

    @Test
    public void findByOwnerUserExistsReturnsCalendar() {
        final User u = new User("Logan", "Adam", "Work", "1234");
        final CalendarInstance ci = u.getPersonalCalendar();
        em.persist(u);

        assertEquals(ci, calendarInstanceDao.findByOwner(u));
    }

    @Test
    public void findByOwnerUserDoesNotExistsReturnsNull() {
        assertNull(calendarInstanceDao.findByOwner( new User("Logan", "Adam", "Work", "1234")));
    }
}
