package cz.cvut.fel.ear.planIt.dao;

import cz.cvut.fel.ear.planIt.model.CalendarInstance;
import cz.cvut.fel.ear.planIt.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class CalendarInstanceDao extends BaseDao<CalendarInstance> {

    public CalendarInstanceDao() {
        super(CalendarInstance.class);
    }

    public CalendarInstance findByName(String name) {
        try {
            return em.createNamedQuery("CalendarInstance.findByName", CalendarInstance.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public CalendarInstance findByOwner(User owner) {
        try {
            return em.createNamedQuery("CalendarInstance.findByOwner", CalendarInstance.class)
                    .setParameter("owner_id", owner.getId())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
