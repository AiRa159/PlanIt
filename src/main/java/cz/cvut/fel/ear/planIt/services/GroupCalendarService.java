package cz.cvut.fel.ear.planIt.services;

import cz.cvut.fel.ear.planIt.dao.CalendarEntryDao;
import cz.cvut.fel.ear.planIt.dao.CalendarInstanceDao;
import cz.cvut.fel.ear.planIt.dao.CategoryDao;
import cz.cvut.fel.ear.planIt.dao.UserDao;
import cz.cvut.fel.ear.planIt.model.CalendarInstance;
import cz.cvut.fel.ear.planIt.model.GroupCalendar;
import cz.cvut.fel.ear.planIt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope("singleton")
public class GroupCalendarService extends CalendarService {

    @Autowired
    public GroupCalendarService(CalendarInstanceDao dao, CalendarEntryDao entryDao, CategoryDao categoryDao, UserDao userDao) {
        super(dao, entryDao, categoryDao, userDao);
    }

    @Transactional
    public boolean isUserExist(CalendarInstance ci, User user){
        return userDao.findByUsername(user.getUsername()) != null;
    }

    @Transactional
    public void addNewModerator(GroupCalendar gc, User user){
        if(!isUserExist(gc, user)){
            gc.addNewModerator(user);
            dao.update(gc);
        }
    }

    @Transactional
    public void removeModerator(GroupCalendar gc, User user){
        if(!isUserExist(gc, user)){
            gc.deleteModerator(user);
            dao.update(gc);
        }
    }
}
