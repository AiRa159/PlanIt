package cz.cvut.fel.ear.planIt.dao;

import cz.cvut.fel.ear.planIt.enums.Color;
import cz.cvut.fel.ear.planIt.model.CalendarInstance;
import cz.cvut.fel.ear.planIt.model.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class CategoryDao extends BaseDao<Category>{

    public CategoryDao() {
        super(Category.class);
    }

    public Category findByColor(String color){
        try {
            return em.createNamedQuery("Category.findByColor", Category.class)
                    .setParameter("color", Color.valueOf(color.toUpperCase()))
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }


    public List<Category> findAllCategoriesByInstance(CalendarInstance ci) {
        try {
            return em.createNamedQuery("Category.findByInstance", Category.class)
                    .setParameter("ci_id", ci.getId())
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
