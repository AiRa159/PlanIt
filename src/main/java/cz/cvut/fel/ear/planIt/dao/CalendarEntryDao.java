package cz.cvut.fel.ear.planIt.dao;

import cz.cvut.fel.ear.planIt.model.CalendarEntry;
import cz.cvut.fel.ear.planIt.model.CalendarInstance;
import cz.cvut.fel.ear.planIt.model.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;

@Repository
public class CalendarEntryDao extends BaseDao<CalendarEntry> {

    public CalendarEntryDao() {
        super(CalendarEntry.class);
    }

    public List<CalendarEntry> findAllByCategory(Category category) {
        Objects.requireNonNull(category);
        return em.createNamedQuery("CalendarEntry.findByCategory", CalendarEntry.class)
                .setParameter("category", category)
                .getResultList();
    }

    public CalendarEntry findByName(String name) {
        try {
            return em.createNamedQuery("CalendarEntry.findByName", CalendarEntry.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<CalendarEntry> findAllEntriesByInstance(CalendarInstance ci) {
        try {
            return em.createNamedQuery("CalendarEntry.findByInstance", CalendarEntry.class)
                    .setParameter("ci_id", ci.getId())
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
