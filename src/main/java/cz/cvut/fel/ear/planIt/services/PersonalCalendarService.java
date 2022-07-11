package cz.cvut.fel.ear.planIt.services;

import cz.cvut.fel.ear.planIt.dao.CalendarEntryDao;
import cz.cvut.fel.ear.planIt.dao.CalendarInstanceDao;
import cz.cvut.fel.ear.planIt.dao.CategoryDao;
import cz.cvut.fel.ear.planIt.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalCalendarService extends CalendarService{

    @Autowired
    public PersonalCalendarService(CalendarInstanceDao dao, CalendarEntryDao entryDao, CategoryDao categoryDao, UserDao userDao) {
        super(dao, entryDao, categoryDao, userDao);
    }
}
