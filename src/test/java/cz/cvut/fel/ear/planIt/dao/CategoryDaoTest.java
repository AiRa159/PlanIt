package cz.cvut.fel.ear.planIt.dao;

import cz.cvut.fel.ear.planIt.PlanItApplication;
import cz.cvut.fel.ear.planIt.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@ComponentScan(basePackageClasses = PlanItApplication.class)
public class CategoryDaoTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CategoryDao sut;

    @Test
    public void findByColorColorExistsReturnsCategory() {
        final Category red = new Category("IMMEDIATELY", "RED");
        final Category blue = new Category("rand_name", "BLUE");
        em.persist(red);
        em.persist(blue);

        assertEquals(red,sut.findByColor("red"));
        assertEquals(blue,sut.findByColor("blue"));
    }

    @Test
    public void findByColorColorDoesNotExistsReturnsNull() {
        assertNull(sut.findByColor("gray"));
    }

//    @Test
//    public void findAllCategoriesReturnsList() {
//        final User u = new User("Logan", "Adam", "Work", "1234");
//        final CalendarInstance ci = u.getPersonalCalendar();
//        em.persist(ci);
//        ci.getCategories().forEach(category -> em.persist(category));
//
//        assertEquals(ci.getCategories(), sut.findAllCategoriesByInstance(ci));
//    }


}
