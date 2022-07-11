package cz.cvut.fel.ear.planIt.dao;

import cz.cvut.fel.ear.planIt.model.CalendarEntry;
import cz.cvut.fel.ear.planIt.model.Reminder;
import org.springframework.stereotype.Repository;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class ReminderDao extends BaseDao<Reminder>{

    protected ReminderDao() {
        super(Reminder.class);
    }

    public Reminder findByName(String name){
        try {
            return em.createNamedQuery("Reminder.findByName", Reminder.class).setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Reminder> findAllRemindersByEntry(CalendarEntry ce) {
        try {
            return em.createNamedQuery("Reminder.findByEntry", Reminder.class)
                    .setParameter("ce_id", ce.getId())
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
