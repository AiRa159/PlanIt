package cz.cvut.fel.ear.planIt.dao;

import cz.cvut.fel.ear.planIt.model.CalendarInstance;
import cz.cvut.fel.ear.planIt.model.User;

import javax.persistence.NoResultException;
import java.util.List;

public class GroupCalendarDao extends  CalendarInstanceDao{


    public List<User> findAllUsersByInstance(CalendarInstance ci) {
        try {
            return em.createQuery("select from GroupCalendar gc inner join gc.users users")
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
