package cz.cvut.fel.ear.planIt.dao;

import cz.cvut.fel.ear.planIt.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import cz.cvut.fel.ear.planIt.PlanItApplication;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackageClasses = PlanItApplication.class)
public class UserDaoTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserDao sut;

    @Test
    public void findByUsernameReturnsUserWithMatchingUsername() {
        final User user = new User("logan","Leon", "Kennedy", "dsad");
        em.persist(user);

        final User result = sut.findByUsername(user.getUsername());
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    public void findByUsernameNoSuchUserReturnsNull() {
        assertNull(sut.findByUsername("rnd_name"));
    }
}
